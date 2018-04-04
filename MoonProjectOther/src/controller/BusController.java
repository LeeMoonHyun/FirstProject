package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.BusVO;

public class BusController implements Initializable {

	@FXML
	private TextField txtInputBusNum; // ������ȣ �Է� tf
	@FXML
	private TextField txtInputTransCom; // ���ȸ�� �Է� tf
	@FXML
	private ComboBox<String> cbInputBusGrade; // ���� ��� ���� cb
	@FXML
	private TextField txtBusNote; // ���� Ư�̻��� �Է�
	@FXML
	private Button btnInputBus; // ���� �Է� ��ư
	@FXML
	private Button btnEditBus; // ���� ���� ��ư
	@FXML
	private Button btnDeleteBus; // ���� ���� ��ư
	@FXML
	private TableView<BusVO> tvBus = new TableView<>(); // ���� tv
	@FXML
	private Button btnBusToMain; // ������������ â���� ��������

	ObservableList<BusVO> data = FXCollections.observableArrayList();
	ObservableList<BusVO> selectData = null;
	BusVO bus = new BusVO();

	int selectedBusIndex;

	int b_seq;

	// ���� ���̺�� �÷� ����
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		TableColumn colBusSeq = new TableColumn<>("No.");
		colBusSeq.setPrefWidth(40);
		colBusSeq.setStyle("-fx-allignment : CENTER");
		colBusSeq.setCellValueFactory(new PropertyValueFactory<>("b_seq"));

		TableColumn colBusNum = new TableColumn<>("���� ��ȣ");
		colBusNum.setPrefWidth(100);
		colBusNum.setStyle("-fx-allignment : CENTER");
		colBusNum.setCellValueFactory(new PropertyValueFactory<>("b_number"));

		TableColumn colTransCom = new TableColumn<>("��� ȸ��");
		colTransCom.setPrefWidth(150);
		colTransCom.setMaxWidth(200);
		colTransCom.setStyle("-fx-allignment : CENTER");
		colTransCom.setCellValueFactory(new PropertyValueFactory<>("b_transCom"));

		TableColumn colGrade = new TableColumn<>("���� ���");
		colGrade.setPrefWidth(80);
		colGrade.setStyle("-fx-allignment : CENTER");
		colGrade.setCellValueFactory(new PropertyValueFactory<>("b_grade"));

		TableColumn colBusNote = new TableColumn<>("���(Ư�̻���)");
		colBusNote.setPrefWidth(200);
		colBusNote.setMaxWidth(300);
		colBusNote.setStyle("-fx-allignment : CENTER");
		colBusNote.setCellValueFactory(new PropertyValueFactory<>("b_note"));

		tvBus.getColumns().addAll(colBusSeq, colBusNum, colTransCom, colGrade, colBusNote);
		busTotalList();
		tvBus.setItems(data);

		// ������� �޺��ڽ� ��� ����
		cbInputBusGrade.setItems(FXCollections.observableArrayList("�Ϲ�", "���"));

		// �������� �Է� ��� �̺�Ʈ
		btnInputBus.setOnAction(event -> {

			try {
				data.removeAll(data);
				BusVO bVo = null;
				BusDAO bDao = new BusDAO();

				if (event.getSource().equals(btnInputBus)) {
					bVo = new BusVO(txtInputBusNum.getText(), txtInputTransCom.getText(),
							cbInputBusGrade.getSelectionModel().getSelectedItem(), txtBusNote.getText());
					bDao = new BusDAO();
					bDao.getBusRegiste(bVo);
				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("���� ���� �Է�");
				alert.setHeaderText("����");
				alert.setContentText("Ư�̻������� �ݵ�� �Է��� �ּ���");
				alert.showAndWait();
			}

			busTotalList();
			busInit();

		});

		// ������ư �̺�Ʈ
		btnEditBus.setOnAction(event -> {
			try {
				// �� �ҷ�����
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/BusEdit.fxml"));

				// ���â ����
				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnInputBus.getScene().getWindow());
				dialog.setTitle("�������� ����");

				Parent parentEdit = (Parent) loader.load();
				BusVO busEdit = tvBus.getSelectionModel().getSelectedItem();
				selectedBusIndex = tvBus.getSelectionModel().getSelectedIndex();

				TextField editBusSeq = (TextField) parentEdit.lookup("#txtEditBusSeq");
				TextField editNumber = (TextField) parentEdit.lookup("#txtEditBusNum");
				TextField editTransCom = (TextField) parentEdit.lookup("#txtEditTransCom");
				TextField editGrade = (TextField) parentEdit.lookup("#txtEditBusGrade");
				TextField editNote = (TextField) parentEdit.lookup("#txtEditBusNote");

				editBusSeq.setDisable(true);
				editGrade.setDisable(true);

				editBusSeq.setText(busEdit.getB_seq() + "");
				editNumber.setText(busEdit.getB_number());
				editTransCom.setText(busEdit.getB_transCom());
				editGrade.setText(busEdit.getB_grade());
				editNote.setText(busEdit.getB_note());

				Button btnBusEditInput = (Button) parentEdit.lookup("#btnBusEditInput");
				Button btnCancelBusInput = (Button) parentEdit.lookup("#btnCancelBusInput");

				// �������� �Է¹�ư
				btnBusEditInput.setOnAction(e -> {

					BusVO bVo = null;
					BusDAO bDao = null;

					TextField txtBusSeq = (TextField) parentEdit.lookup("#txtEditBusSeq");
					TextField txtBusNumber = (TextField) parentEdit.lookup("#txtEditBusNum");
					TextField txtBusTransCom = (TextField) parentEdit.lookup("#txtEditTransCom");
					TextField txtBusGrade = (TextField) parentEdit.lookup("#txtEditBusGrade");
					TextField txtBusNote = (TextField) parentEdit.lookup("#txtEditBusNote");

					data.remove(selectedBusIndex);

					try {
						bVo = new BusVO(Integer.parseInt(txtBusSeq.getText().trim()), txtBusNumber.getText(),
								txtBusTransCom.getText(), txtBusGrade.getText(), txtBusNote.getText());

						dialog.close();

						bDao = new BusDAO();
						bDao.getBusUpdate(bVo, bVo.getB_seq());

						data.removeAll(data);
						busTotalList();

					} catch (Exception e1) {
						e1.printStackTrace();
					}

				});

				// ����â �ݱ� ��ư �̺�Ʈ
				btnCancelBusInput.setOnAction(e -> {
					dialog.close();
				});

				Scene scene = new Scene(parentEdit);
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();

			} catch (IOException e) {
				System.out.println(e.toString());
			}
		});

		// ������ư �̺�Ʈ
		btnDeleteBus.setOnAction(event -> {

			BusDAO bDao = null;
			bDao = new BusDAO();

			selectData = tvBus.getSelectionModel().getSelectedItems();
			b_seq = selectData.get(0).getB_seq();

			try {
				bDao.getBusDelete(b_seq);
				data.removeAll(data);

				busTotalList();

			} catch (Exception e) {
				e.printStackTrace();
			}

		});

		// �������� ���� ��ư
		btnBusToMain.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView1.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainViewStage = new Stage();
				mainViewStage.setTitle("��ӹ��� ����ý���");
				mainViewStage.setScene(scene);
				Stage operationStage = (Stage) btnBusToMain.getScene().getWindow();
				operationStage.close();
				mainViewStage.show();
			} catch (IOException e) {
				System.err.println("����" + e);
			}
		});

	}

	// �ʱ�ȭ �޼ҵ�
	public void busInit() {
		txtInputBusNum.clear();
		txtInputTransCom.clear();
		cbInputBusGrade.getSelectionModel().clearSelection();
		txtBusNote.clear();
	}

	// ��ü����
	public void busTotalList() {
		Object[][] totalData;

		BusDAO bDao = new BusDAO();
		BusVO bVo = new BusVO();
		ArrayList<String> title;
		ArrayList<BusVO> list;

		title = bDao.getColumnName();
		int columnCount = title.size();

		list = bDao.getBusTotal();
		int rowCount = list.size();

		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			bVo = list.get(index);
			data.add(bVo);
		}
	}

}
