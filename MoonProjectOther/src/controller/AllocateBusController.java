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
	private TextField txtAllocateSearchDeparture; // 배차할 경로검색 출발지
	@FXML
	private TextField txtAllocateSearchArrival; // 배차할 경로검색 도착지
	@FXML
	private ComboBox<String> cbAllocateSearchStartTime; // 배차할 경로검색 출발시간
	@FXML
	private Button btnAllocateOpeSearch; // 경로검색 버튼
	@FXML
	private TableView<OperationSchVO> tvAllocateOpeSearch; // 경로정보 검색 출력 테이블뷰

	@FXML
	private TextField txtAllocateSearchTransCom; // 배차할 버스 운송회사
	@FXML
	private ComboBox<String> cbAllocateSearchBusGrade; // 배차할 버스 등급
	@FXML
	private Button btnAllocateBusSearch; // 버스검색 버튼
	@FXML
	private TableView<BusVO> tvAllocateBusSearch; // 버스정보 검색 출력 테이블뷰

	@FXML
	private TextField txtAllocateBusDeparture; // 선택한 경로 출발지
	@FXML
	private TextField txtAllocateBusArrival; // 선택한 경로 도착지
	@FXML
	private TextField txtAllocateBusStartTime; // 선택한 경로 출발시간
	@FXML
	private TextField txtAllocateBusDistance; // 선택한 경로 거리
	@FXML
	private TextField txtAllocateBusLeadTime; // 선택한 경로 소요시간
	@FXML
	private TextField txtAllocateBusFee; // 선택한 경로 요금
	@FXML
	private TextField txtAllocateBusNumber; // 선택한 버스번호
	@FXML
	private TextField txtAllocateBusTransCom; // 선택한 버스 운송회사
	@FXML
	private TextField txtAllocateBusGrade; // 선택한 버스 등급
	@FXML
	private DatePicker dpAllocateBusDate; // 선택할 날짜
	@FXML
	private Button btnAllocateBusOk; // 시간표 생성 버튼

	@FXML
	private Button btnAllocateBusToMain; // 메인으로 돌아가는 버튼

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

		// 경로 검색 테이블뷰 컬럼설정
		TableColumn colOpeNo = new TableColumn("No.");
		colOpeNo.setPrefWidth(50);
		colOpeNo.setStyle("-fx-allignment : CENTER");
		colOpeNo.setCellValueFactory(new PropertyValueFactory<>("o_number"));

		TableColumn colDeparture = new TableColumn("출발지");
		colDeparture.setPrefWidth(120);
		colDeparture.setMaxWidth(180);
		colDeparture.setStyle("-fx-allignment : CENTER");
		colDeparture.setCellValueFactory(new PropertyValueFactory<>("o_departure"));

		TableColumn colArrival = new TableColumn("도착지");
		colArrival.setPrefWidth(120);
		colArrival.setMaxWidth(180);
		colArrival.setStyle("-fx-allignment : CENTER");
		colArrival.setCellValueFactory(new PropertyValueFactory<>("o_arrival"));

		TableColumn colStartTime = new TableColumn("출발시간");
		colStartTime.setPrefWidth(90);
		colStartTime.setMaxWidth(90);
		colStartTime.setStyle("-fx-allignment : CENTER");
		colStartTime.setCellValueFactory(new PropertyValueFactory<>("o_starttime"));

		TableColumn colDistance = new TableColumn("거리");
		colDistance.setPrefWidth(110);
		colDistance.setMaxWidth(180);
		colDistance.setStyle("-fx-allignment : CENTER");
		colDistance.setCellValueFactory(new PropertyValueFactory<>("o_distance"));

		TableColumn colLeadTime = new TableColumn("소요시간");
		colLeadTime.setPrefWidth(90);
		colLeadTime.setMaxWidth(90);
		colLeadTime.setStyle("-fx-allignment : CENTER");
		colLeadTime.setCellValueFactory(new PropertyValueFactory<>("o_leadtime"));

		TableColumn colFee = new TableColumn("요금");
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

		// 버스정보 검색 테이블뷰 컬럼설정
		TableColumn colBusNo = new TableColumn<>("No.");
		colBusNo.setPrefWidth(50);
		colBusNo.setStyle("-fx-allignment : CENTER");
		colBusNo.setCellValueFactory(new PropertyValueFactory<>("b_seq"));

		TableColumn colBusNum = new TableColumn<>("버스 번호");
		colBusNum.setPrefWidth(100);
		colBusNum.setStyle("-fx-allignment : CENTER");
		colBusNum.setCellValueFactory(new PropertyValueFactory<>("b_number"));

		TableColumn colTransCom = new TableColumn<>("운송 회사");
		colTransCom.setPrefWidth(150);
		colTransCom.setMaxWidth(200);
		colTransCom.setStyle("-fx-allignment : CENTER");
		colTransCom.setCellValueFactory(new PropertyValueFactory<>("b_transCom"));

		TableColumn colGrade = new TableColumn<>("버스 등급");
		colGrade.setPrefWidth(80);
		colGrade.setStyle("-fx-allignment : CENTER");
		colGrade.setCellValueFactory(new PropertyValueFactory<>("b_grade"));

		TableColumn colBusNote = new TableColumn<>("비고(특이사항)");
		colBusNote.setPrefWidth(200);
		colBusNote.setMaxWidth(300);
		colBusNote.setStyle("-fx-allignment : CENTER");
		colBusNote.setCellValueFactory(new PropertyValueFactory<>("b_note"));

		tvAllocateBusSearch.getColumns().addAll(colBusNo, colBusNum, colTransCom, colGrade, colBusNote);
		tvAllocateBusSearch.setItems(busData);

		busTotalList();
		cbAllocateSearchBusGrade.setItems(FXCollections.observableArrayList("일반", "우등"));

		// 경로검색 조회버튼 이벤트
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
					alert.setTitle("정보 검색");
					alert.setHeaderText("정보를 모두 입력해 주세요");
					alert.setContentText("주의");
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
					alert.setTitle("정보 검색");
					alert.setHeaderText(searchDeparture + " " + searchArrival + " " + searchStartTime + " 이 리스트에 없습니다");
					alert.setContentText("다시 검색하세요");
					alert.showAndWait();
				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("정보 검색 오류");
				alert.setHeaderText("정보 검색에 오류가 발생하였습니다.");
				alert.setContentText("다시 하세요");
			}

		});

		// 버스정보 검색 버튼 이벤트
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
					alert.setTitle("정보 검색");
					alert.setHeaderText("정보를 모두 입력해 주세요");
					alert.setContentText("주의");
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
					alert.setTitle("정보 검색");
					alert.setHeaderText(searchTransCom + " " + searchBusGrade + " 이 리스트에 없습니다");
					alert.setContentText("다시 검색하세요");
					alert.showAndWait();
				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("정보 검색 오류");
				alert.setHeaderText("정보 검색에 오류가 발생하였습니다.");
				alert.setContentText("다시 하세요");
			}

		});

		// 노선정보 클릭 이벤트
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

		// 버스정보 클릭 이벤트
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

		// 메인으로 가는 버튼 이벤트
		btnAllocateBusToMain.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView1.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainViewStage = new Stage();
				mainViewStage.setTitle("고속버스 예약시스템");
				mainViewStage.setScene(scene);
				Stage operationStage = (Stage) btnAllocateBusToMain.getScene().getWindow();
				operationStage.close();
				mainViewStage.show();
			} catch (IOException e) {
				System.err.println("오류" + e);
			}
		});

		// 시간표에 입력하는 버튼 이벤트
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
						alert.setTitle("시간표입력");
						alert.setHeaderText("성공적");
						alert.setContentText("다음은?");
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
				alert.setTitle("시간표");
				alert.setHeaderText("실패");
				alert.setContentText("실패!");
				alert.showAndWait();
			}

		});

	}

	// 경로 전체리스트
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

	// 버스 전체 리스트
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
