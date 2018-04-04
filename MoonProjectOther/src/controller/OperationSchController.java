package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
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
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.OperationSchVO;

public class OperationSchController implements Initializable {

	@FXML
	private TextField txtInputDeparture; // ����� �Է�ĭ
	@FXML
	private TextField txtInputArrival; // ������ �Է�ĭ
	@FXML
	private ComboBox<String> cbInputStartTime; // ��߽ð� �Է�ĭ
	@FXML
	private TextField txtInputDistance; // �Ÿ� �Է�ĭ
	@FXML
	private TextField txtInputLeadTime; // �ҿ�ð� �Է�ĭ
	@FXML
	private TextField txtInputFee; // ��� �Է�ĭ
	@FXML
	private Button btnInputOperation; // ������ ���� ��ư
	@FXML
	private Button btnEditOperation; // ������ ���� ��ư
	@FXML
	private Button btnDeleteOperation; // ������ ���� ��ư
	@FXML
	private TableView<OperationSchVO> tvOperationSch = new TableView<>(); // ��������tv
	@FXML
	private Button btnOperationToMain; // �������� ���ư��� ��ư

	ObservableList<OperationSchVO> data = FXCollections.observableArrayList(); // ����
	ObservableList<OperationSchVO> selectData = null;
	OperationSchVO operation = new OperationSchVO();

	int selectedOperationIndex; // �ε��� ����

	int o_number;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// �Ÿ�, ��ݶ��� ���ڸ� �Է��ϰ� �ڸ��� ����
		DecimalFormat format1 = new DecimalFormat("###.##");

		txtInputDistance.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format1.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 7) {
				return null;
			} else {
				return event;
			}
		}));

		DecimalFormat format2 = new DecimalFormat("##,###");

		txtInputFee.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format2.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 6) {
				return null;
			} else {
				return event;
			}
		}));

		tvOperationSch.setEditable(false);

		// ���̺�� �÷� ����
		TableColumn colNo = new TableColumn("No.");
		colNo.setPrefWidth(30);
		colNo.setMaxWidth(50);
		colNo.setStyle("-fx-allignment : CENTER");
		colNo.setCellValueFactory(new PropertyValueFactory<>("o_number"));

		TableColumn colDeparture = new TableColumn("�����");
		colDeparture.setPrefWidth(120);
		colDeparture.setMaxWidth(180);
		colDeparture.setStyle("-fx-allignment : CENTER");
		colDeparture.setCellValueFactory(new PropertyValueFactory<>("o_departure"));

		TableColumn colArrival = new TableColumn("������");
		colArrival.setPrefWidth(120);
		colArrival.setMaxWidth(180);
		colArrival.setStyle("-fx-allignment : CENTER");
		colArrival.setCellValueFactory(new PropertyValueFactory<>("o_arrival"));

		TableColumn colStartTime = new TableColumn("��߽ð�");
		colStartTime.setPrefWidth(90);
		colStartTime.setMaxWidth(90);
		colStartTime.setStyle("-fx-allignment : CENTER");
		colStartTime.setCellValueFactory(new PropertyValueFactory<>("o_starttime"));

		TableColumn colDistance = new TableColumn("�Ÿ�");
		colDistance.setPrefWidth(110);
		colDistance.setMaxWidth(180);
		colDistance.setStyle("-fx-allignment : CENTER");
		colDistance.setCellValueFactory(new PropertyValueFactory<>("o_distance"));

		TableColumn colLeadTime = new TableColumn("�ҿ�ð�");
		colLeadTime.setPrefWidth(90);
		colLeadTime.setMaxWidth(90);
		colLeadTime.setStyle("-fx-allignment : CENTER");
		colLeadTime.setCellValueFactory(new PropertyValueFactory<>("o_leadtime"));

		TableColumn colFee = new TableColumn("���");
		colFee.setPrefWidth(129);
		colFee.setMaxWidth(220);
		colFee.setStyle("-fx-allignment : CENTER");
		colFee.setCellValueFactory(new PropertyValueFactory<>("o_fee"));

		tvOperationSch.getColumns().addAll(colNo, colDeparture, colArrival, colStartTime, colDistance, colLeadTime,
				colFee);

		operationSchTotalList();
		tvOperationSch.setItems(data);

		cbInputStartTime.setItems(FXCollections.observableArrayList("00:00", "01:00", "02:00", "03:00", "04:00",
				"05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00",
				"16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"));

		// �������� ��� ���� �̺�Ʈ
		btnInputOperation.setOnAction(event -> {

			try {
				data.removeAll(data);
				OperationSchVO oVo = null;
				OperationSchDAO oDao = new OperationSchDAO();

				if (event.getSource().equals(btnInputOperation)) {
					oVo = new OperationSchVO(txtInputDeparture.getText(), txtInputArrival.getText(),
							cbInputStartTime.getSelectionModel().getSelectedItem(),
							Double.parseDouble(txtInputDistance.getText().trim()), txtInputLeadTime.getText(),
							Integer.parseInt(txtInputFee.getText().trim()));
					oDao = new OperationSchDAO();
					oDao.getOperationSchregiste(oVo);
				}

				// ���ڼ� �ʰ��� ���â
				if (txtInputDistance.getText().length() > 6 || txtInputLeadTime.getText().length() > 5) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("������ �Է�");
					alert.setHeaderText("���ڼ� �ʰ�");
					alert.setContentText("���ڼ��� �����ּ���");
					alert.showAndWait();
				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("������ �Է�");
				alert.setHeaderText("����");
				alert.setContentText("�ϳ��� �������� �Է��� �ּ���");
				alert.showAndWait();
			}
			operationSchTotalList();
			operationSchInit();
		});

		// �������� ���� ��ư �̺�Ʈ
		btnEditOperation.setOnAction(event -> {
			try {
				// �� �ҷ�����
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/EditOperationSch.fxml"));

				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnInputOperation.getScene().getWindow());
				dialog.setTitle("�������� ����");

				Parent parentEdit = (Parent) loader.load();
				OperationSchVO operationEdit = tvOperationSch.getSelectionModel().getSelectedItem();
				selectedOperationIndex = tvOperationSch.getSelectionModel().getSelectedIndex();

				TextField editNo = (TextField) parentEdit.lookup("#txtEditNo");
				TextField editDeparture = (TextField) parentEdit.lookup("#txtEditDeparture");
				TextField editArrival = (TextField) parentEdit.lookup("#txtEditArrival");
				TextField editStartTime = (TextField) parentEdit.lookup("#txtEditStartTime");
				TextField editDistance = (TextField) parentEdit.lookup("#txtEditDistance");
				TextField editLeadTime = (TextField) parentEdit.lookup("#txtEditLeadTime");
				TextField editFee = (TextField) parentEdit.lookup("#txtEditFee");

				editNo.setDisable(true);

				editNo.setText(operationEdit.getO_number() + "");
				editDeparture.setText(operationEdit.getO_departure());
				editArrival.setText(operationEdit.getO_arrival());
				editStartTime.setText(operationEdit.getO_starttime() + "");
				editDistance.setText(operationEdit.getO_distance() + "");
				editLeadTime.setText(operationEdit.getO_leadtime() + "");
				editFee.setText(operationEdit.getO_fee() + "");

				Button btnEditInput = (Button) parentEdit.lookup("#btnEditInput");
				Button btnEditCancel = (Button) parentEdit.lookup("#btnEditCancel");

				// ����â �����Է¹�ư �̺�Ʈ
				btnEditInput.setOnAction(e -> {
					OperationSchVO oVo = null;
					OperationSchDAO oDao = null;

					TextField txtNo = (TextField) parentEdit.lookup("#txtEditNo");
					TextField txtDeparture = (TextField) parentEdit.lookup("#txtEditDeparture");
					TextField txtArrival = (TextField) parentEdit.lookup("#txtEditArrival");
					TextField txtStartTime = (TextField) parentEdit.lookup("#txtEditStartTime");
					TextField txtDistance = (TextField) parentEdit.lookup("#txtEditDistance");
					TextField txtLeadTime = (TextField) parentEdit.lookup("#txtEditLeadTime");
					TextField txtFee = (TextField) parentEdit.lookup("#txtEditFee");

					data.remove(selectedOperationIndex);

					try {
						oVo = new OperationSchVO(Integer.parseInt(txtNo.getText().trim()), txtDeparture.getText(),
								txtArrival.getText(), txtStartTime.getText(),
								Double.parseDouble(txtDistance.getText().trim()), txtLeadTime.getText(),
								Integer.parseInt(txtFee.getText().trim()));

						dialog.close();

						oDao = new OperationSchDAO();
						oDao.getOperationSchUpdate(oVo, oVo.getO_number());

						data.removeAll(data);
						operationSchTotalList();

					} catch (Exception ee) {
						ee.printStackTrace();
					}

				});

				// ����â ��ҹ�ư �̺�Ʈ
				btnEditCancel.setOnAction(e -> {
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

		// �������� ���� ��ư �̺�Ʈ
		btnDeleteOperation.setOnAction(event -> {

			OperationSchDAO oDao = null;
			oDao = new OperationSchDAO();

			selectData = tvOperationSch.getSelectionModel().getSelectedItems();
			o_number = selectData.get(0).getO_number();

			try {
				oDao.getOperationSchDelete(o_number);
				data.removeAll(data);
				operationSchTotalList();
			} catch (Exception e) {
				e.printStackTrace();
			}

		});

		// �������� ���� ��ư �̺�Ʈ
		btnOperationToMain.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView1.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainViewStage = new Stage();
				mainViewStage.setTitle("��ӹ��� ����ý���");
				mainViewStage.setScene(scene);
				Stage operationStage = (Stage) btnOperationToMain.getScene().getWindow();
				operationStage.close();
				mainViewStage.show();
			} catch (IOException e) {
				System.err.println("����" + e);
			}
		});

	}

	// ��ü����Ʈ
	public void operationSchTotalList() {
		Object[][] totalData;

		OperationSchVO oVo = new OperationSchVO();
		OperationSchDAO oDao = new OperationSchDAO();

		ArrayList<String> title;
		ArrayList<OperationSchVO> list;

		title = oDao.getColumnName();
		int columnCount = title.size();

		list = oDao.getOperationSchTotal();
		int rowCount = list.size();

		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			oVo = list.get(index);
			data.add(oVo);
		}
	}

	// �ʱ�ȭ �޼ҵ�
	public void operationSchInit() {
		txtInputDeparture.clear();
		txtInputArrival.clear();
		cbInputStartTime.getSelectionModel().clearSelection();
		txtInputDistance.clear();
		txtInputLeadTime.clear();
		txtInputFee.clear();
	}

}
