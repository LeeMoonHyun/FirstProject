package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.bind.annotation.XmlIDREF;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.BusVO;
import model.OperationSchVO;
import model.ScheduleVO;

public class AllocateBusController implements Initializable {

	@FXML
	private TextField txtAllocateSearchDeparture; // ������ ��ΰ˻� �����
	@FXML
	private TextField txtAllocateSearchArrival; // ������ ��ΰ˻� ������
	@FXML
	private ComboBox<String> cbAllocateSearchStartTime; // ������ ��ΰ˻� ��߽ð�
	@FXML
	private Button btnAllocateOpeSearch; // ��ΰ˻� ��ư
	@FXML
	private TableView<OperationSchVO> tvAllocateOpeSearch; // ������� �˻� ��� ���̺��

	@FXML
	private TextField txtAllocateSearchTransCom; // ������ ���� ���ȸ��
	@FXML
	private ComboBox<String> cbAllocateSearchBusGrade; // ������ ���� ���
	@FXML
	private Button btnAllocateBusSearch; // �����˻� ��ư
	@FXML
	private TableView<BusVO> tvAllocateBusSearch; // �������� �˻� ��� ���̺��

	@FXML
	private TextField txtAllocateBusDeparture; // ������ ��� �����
	@FXML
	private TextField txtAllocateBusArrival; // ������ ��� ������
	@FXML
	private TextField txtAllocateBusStartTime; // ������ ��� ��߽ð�
	@FXML
	private TextField txtAllocateBusDistance; // ������ ��� �Ÿ�
	@FXML
	private TextField txtAllocateBusLeadTime; // ������ ��� �ҿ�ð�
	@FXML
	private TextField txtAllocateBusFee; // ������ ��� ���
	@FXML
	private TextField txtAllocateBusNumber; // ������ ������ȣ
	@FXML
	private TextField txtAllocateBusTransCom; // ������ ���� ���ȸ��
	@FXML
	private TextField txtAllocateBusGrade; // ������ ���� ���
	@FXML
	private DatePicker dpAllocateBusDate; // ������ ��¥
	@FXML
	private Button btnAllocateBusOk; // �ð�ǥ ���� ��ư

	@FXML
	private Button btnAllocateBusToMain; // �������� ���ư��� ��ư

	ObservableList<OperationSchVO> opeData = FXCollections.observableArrayList();
	ObservableList<OperationSchVO> opeSelectSch = null;
	OperationSchVO operationSch = new OperationSchVO();

	ObservableList<BusVO> busData = FXCollections.observableArrayList();
	ObservableList<BusVO> busSelect = null;
	BusVO bus = new BusVO();

	ObservableList<ScheduleVO> schData = FXCollections.observableArrayList();

	int opeNo;
	int busNo;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// ��� �˻� ���̺�� �÷�����
		TableColumn colOpeNo = new TableColumn("No.");
		colOpeNo.setPrefWidth(50);
		colOpeNo.setStyle("-fx-allignment : CENTER");
		colOpeNo.setCellValueFactory(new PropertyValueFactory<>("o_number"));

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

		tvAllocateOpeSearch.getColumns().addAll(colOpeNo, colDeparture, colArrival, colStartTime, colDistance,
				colLeadTime, colFee);

		operationSchTotalList();
		tvAllocateOpeSearch.setItems(opeData);

		cbAllocateSearchStartTime.setItems(FXCollections.observableArrayList("00:00", "01:00", "02:00", "03:00",
				"04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
				"15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"));

		// �������� �˻� ���̺�� �÷�����
		TableColumn colBusNo = new TableColumn<>("No.");
		colBusNo.setPrefWidth(50);
		colBusNo.setStyle("-fx-allignment : CENTER");
		colBusNo.setCellValueFactory(new PropertyValueFactory<>("b_seq"));

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

		tvAllocateBusSearch.getColumns().addAll(colBusNo, colBusNum, colTransCom, colGrade, colBusNote);
		tvAllocateBusSearch.setItems(busData);

		busTotalList();
		cbAllocateSearchBusGrade.setItems(FXCollections.observableArrayList("�Ϲ�", "���"));

		// ��ΰ˻� ��ȸ��ư �̺�Ʈ
		btnAllocateOpeSearch.setOnAction(event -> {

			OperationSchVO oVo = new OperationSchVO();
			OperationSchDAO oDao = null;

			Object[][] operationschTotalData = null;

			String searchDeparture = "";
			String searchArrival = "";
			String searchStartTime = "";
			boolean searchResult = false;

			try {
				searchDeparture = txtAllocateSearchDeparture.getText().trim();
				searchArrival = txtAllocateSearchArrival.getText().trim();
				searchStartTime = cbAllocateSearchStartTime.getSelectionModel().getSelectedItem();

				oDao = new OperationSchDAO();

				oVo = oDao.getOperationSchCheck(searchDeparture, searchArrival, searchStartTime);

				if (searchDeparture.equals("") || searchArrival.equals("") || searchStartTime.equals("") || (oVo == null)) {
					searchResult = true;
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("���� �˻�");
					alert.setHeaderText("������ ��� �Է��� �ּ���");
					alert.setContentText("����");
					alert.showAndWait();
				}

				if (!(searchDeparture.equals("") && searchArrival.equals("") && searchStartTime.equals(""))
						&& (oVo != null)) {

					ArrayList<String> title;
					ArrayList<OperationSchVO> list;

					title = oDao.getColumnName();
					int columnCount = title.size();

					list = oDao.getOperationSchTotal();
					int rowCount = list.size();

					operationschTotalData = new Object[rowCount][columnCount];

					if (oVo.getO_departure().equals(searchDeparture) && oVo.getO_arrival().equals(searchArrival)
							&& oVo.getO_starttime().equals(searchStartTime)) {

						txtAllocateSearchDeparture.clear();
						txtAllocateSearchArrival.clear();
						cbAllocateSearchStartTime.getSelectionModel().clearSelection();
						opeData.removeAll(opeData);

						for (int index = 0; index < rowCount; index++) {
							oVo = list.get(index);
							if (oVo.getO_departure().equals(searchDeparture) && oVo.getO_arrival().equals(searchArrival)
									&& oVo.getO_starttime().equals(searchStartTime)) {
								opeData.add(oVo);
								searchResult = true;
							}
						}

					}

				}

				if (!searchResult) {
					txtAllocateSearchDeparture.clear();
					txtAllocateSearchArrival.clear();
					cbAllocateSearchStartTime.getSelectionModel().clearSelection();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("���� �˻�");
					alert.setHeaderText(searchDeparture + " " + searchArrival + " " + searchStartTime + " �� ����Ʈ�� �����ϴ�");
					alert.setContentText("�ٽ� �˻��ϼ���");
					alert.showAndWait();
				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("���� �˻� ����");
				alert.setHeaderText("���� �˻��� ������ �߻��Ͽ����ϴ�.");
				alert.setContentText("�ٽ� �ϼ���");
			}

		});

		// �������� �˻� ��ư �̺�Ʈ
		btnAllocateBusSearch.setOnAction(event -> {

			BusVO bVo = new BusVO();
			BusDAO bDao = null;

			Object[][] busTotalData = null;

			String searchTransCom = "";
			String searchBusGrade = "";
			boolean searchResult = false;

			try {
				searchTransCom = txtAllocateSearchTransCom.getText().trim();
				searchBusGrade = cbAllocateSearchBusGrade.getSelectionModel().getSelectedItem().toString();

				bDao = new BusDAO();
				bVo = bDao.getBusCheck(searchTransCom, searchBusGrade);

				if (searchTransCom.equals("") || searchBusGrade.equals("")) {
					searchResult = true;
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("���� �˻�");
					alert.setHeaderText("������ ��� �Է��� �ּ���");
					alert.setContentText("����");
					alert.showAndWait();
				}

				if (!(searchTransCom.equals("") && searchBusGrade.equals("")) && (bVo != null)) {

					ArrayList<String> title;
					ArrayList<BusVO> list;

					title = bDao.getColumnName();
					int columnCount = title.size();

					list = bDao.getBusTotal();
					int rowCount = list.size();

					busTotalData = new Object[rowCount][columnCount];

					if (bVo.getB_transCom().equals(searchTransCom) && bVo.getB_grade().equals(searchBusGrade)) {
						txtAllocateSearchTransCom.clear();
						cbAllocateSearchBusGrade.getSelectionModel().clearSelection();
						busData.removeAll(busData);

						for (int index = 0; index < rowCount; index++) {
							bVo = list.get(index);
							if (bVo.getB_transCom().equals(searchTransCom) && bVo.getB_grade().equals(searchBusGrade)) {
								busData.add(bVo);
								searchResult = true;
							}
						}
					}
				}

				if (!searchResult) {
					txtAllocateSearchTransCom.clear();
					cbAllocateSearchBusGrade.getSelectionModel().clearSelection();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("���� �˻�");
					alert.setHeaderText(searchTransCom + " " + searchBusGrade + " �� ����Ʈ�� �����ϴ�");
					alert.setContentText("�ٽ� �˻��ϼ���");
					alert.showAndWait();
				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("���� �˻� ����");
				alert.setHeaderText("���� �˻��� ������ �߻��Ͽ����ϴ�.");
				alert.setContentText("�ٽ� �ϼ���");
			}

		});

		// �뼱���� Ŭ�� �̺�Ʈ
		tvAllocateOpeSearch.setOnMousePressed(event -> {

			opeSelectSch = tvAllocateOpeSearch.getSelectionModel().getSelectedItems();
			try {
				txtAllocateBusDeparture.setText(opeSelectSch.get(0).getO_departure());
				txtAllocateBusArrival.setText(opeSelectSch.get(0).getO_arrival());
				txtAllocateBusStartTime.setText(opeSelectSch.get(0).getO_starttime());
				txtAllocateBusDistance.setText(opeSelectSch.get(0).getO_distance() + "");
				txtAllocateBusLeadTime.setText(opeSelectSch.get(0).getO_leadtime());
				txtAllocateBusFee.setText(opeSelectSch.get(0).getO_fee() + "");
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("");
				alert.setHeaderText("");
				alert.setContentText("");
				alert.showAndWait();
			}
		});

		// �������� Ŭ�� �̺�Ʈ
		tvAllocateBusSearch.setOnMousePressed(event -> {

			busSelect = tvAllocateBusSearch.getSelectionModel().getSelectedItems();

			try {
				txtAllocateBusNumber.setText(busSelect.get(0).getB_number());
				txtAllocateBusTransCom.setText(busSelect.get(0).getB_transCom());
				txtAllocateBusGrade.setText(busSelect.get(0).getB_grade());
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("");
				alert.setHeaderText("");
				alert.setContentText("");
				alert.showAndWait();
			}

		});

		// �������� ���� ��ư �̺�Ʈ
		btnAllocateBusToMain.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView1.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainViewStage = new Stage();
				mainViewStage.setTitle("��ӹ��� ����ý���");
				mainViewStage.setScene(scene);
				Stage operationStage = (Stage) btnAllocateBusToMain.getScene().getWindow();
				operationStage.close();
				mainViewStage.show();
			} catch (IOException e) {
				System.err.println("����" + e);
			}
		});

		// �ð�ǥ�� �Է��ϴ� ��ư �̺�Ʈ
		btnAllocateBusOk.setOnAction(event -> {

			try {
				schData.removeAll(schData);
				ScheduleVO sVo = null;
				AllocateBusDAO abDao = new AllocateBusDAO();

				opeSelectSch = tvAllocateOpeSearch.getSelectionModel().getSelectedItems();
				busSelect = tvAllocateBusSearch.getSelectionModel().getSelectedItems();

				LocalDate pickDate = dpAllocateBusDate.getValue();

				if (event.getSource().equals(btnAllocateBusOk)) {
					sVo = new ScheduleVO(opeSelectSch.get(0).getO_departure(), opeSelectSch.get(0).getO_arrival(),
							opeSelectSch.get(0).getO_starttime(), opeSelectSch.get(0).getO_distance(),
							opeSelectSch.get(0).getO_leadtime(), opeSelectSch.get(0).getO_fee(),
							busSelect.get(0).getB_number(), busSelect.get(0).getB_transCom(),
							busSelect.get(0).getB_grade(), busSelect.get(0).getB_note(), pickDate.toString(),
							opeSelectSch.get(0).getO_number(), busSelect.get(0).getB_seq());
					abDao = new AllocateBusDAO();
					abDao.getScheduleregiste(sVo);
					
					if (abDao != null) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("�ð�ǥ�Է�");
						alert.setHeaderText("������");
						alert.setContentText("������?");
						alert.showAndWait();
					}
				}

				txtAllocateBusDeparture.clear();
				txtAllocateBusArrival.clear();
				txtAllocateBusStartTime.clear();
				txtAllocateBusDistance.clear();
				txtAllocateBusLeadTime.clear();
				txtAllocateBusFee.clear();
				txtAllocateBusNumber.clear();
				txtAllocateBusTransCom.clear();
				txtAllocateBusGrade.clear();
				dpAllocateBusDate.getEditor().clear();

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("�ð�ǥ");
				alert.setHeaderText("����");
				alert.setContentText("����!");
				alert.showAndWait();
			}

		});

	}

	// ��� ��ü����Ʈ
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
			opeData.add(oVo);
		}
	}

	// ���� ��ü ����Ʈ
	public void busTotalList() {
		Object[][] totalData;

		BusVO bVo = new BusVO();
		BusDAO bDao = new BusDAO();

		ArrayList<String> title;
		ArrayList<BusVO> list;

		title = bDao.getColumnName();
		int columnCount = title.size();

		list = bDao.getBusTotal();
		int rowCount = list.size();

		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			bVo = list.get(index);
			busData.add(bVo);
		}
	}

}
