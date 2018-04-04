package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ReservationInfoVO;
import model.ScheduleVO;

public class AllocateController implements Initializable {

	@FXML
	private ComboBox<String> cbDeparture; // 검색할 출발지
	@FXML
	private ComboBox<String> cbArrival; // 검색할 도작치
	@FXML
	private DatePicker dpStartDate; // 검색할 날짜
	@FXML
	private ComboBox<String> cbStartTime; // 검색할 시간
	@FXML
	private Button btnSchSearch; // 검색, 조회 버튼
	@FXML
	private TableView<ScheduleVO> tvAllocate;// 검색결과 테이블뷰

	@FXML
	private Button btnTicketing; // 티켓팅 버튼
	@FXML
	private Button btnAlloToMain; // 메인으로 가는 버튼

	ObservableList<ScheduleVO> schData = FXCollections.observableArrayList();
	ObservableList<ScheduleVO> schSelect = null;
	ScheduleVO schedule = new ScheduleVO();

	int selectedIndex;
	int no;

	ArrayList<ScheduleVO> departureList;
	ArrayList<ScheduleVO> arrivalList;

	String[] departure;
	String[] arrival;

	AllocateDAO aDao = null;

	static int seat = 0; // 인원수
	// static String[] seatNumber = new String[4];
	static String seatNumberSelect; // 좌석번호
	static int seatCount = 0; // 좌석선택수

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		TableColumn colDeparture = new TableColumn("출발지");
		colDeparture.setPrefWidth(100);
		colDeparture.setStyle("-fx-allignment : CENTER");
		colDeparture.setCellValueFactory(new PropertyValueFactory<>("sch_departure"));

		TableColumn colArrival = new TableColumn("도착지");
		colArrival.setPrefWidth(100);
		colArrival.setStyle("-fx-allignment : CENTER");
		colArrival.setCellValueFactory(new PropertyValueFactory<>("sch_arrival"));

		TableColumn colStartTime = new TableColumn("출발시간");
		colStartTime.setPrefWidth(100);
		colStartTime.setStyle("-fx-allignment : CENTER");
		colStartTime.setCellValueFactory(new PropertyValueFactory<>("sch_starttime"));

		TableColumn colDistance = new TableColumn("거리");
		colDistance.setPrefWidth(100);
		colDistance.setStyle("-fx-allignment : CENTER");
		colDistance.setCellValueFactory(new PropertyValueFactory<>("sch_distance"));

		TableColumn colLeadTime = new TableColumn("소요시간");
		colLeadTime.setPrefWidth(100);
		colLeadTime.setStyle("-fx-allignment : CENTER");
		colLeadTime.setCellValueFactory(new PropertyValueFactory<>("sch_leadtime"));

		TableColumn colFee = new TableColumn("요금");
		colFee.setPrefWidth(100);
		colFee.setStyle("-fx-allignment : CENTER");
		colFee.setCellValueFactory(new PropertyValueFactory<>("sch_fee"));

		TableColumn colTransCom = new TableColumn("운송회사");
		colTransCom.setPrefWidth(100);
		colTransCom.setStyle("-fx-allignment : CENTER");
		colTransCom.setCellValueFactory(new PropertyValueFactory<>("sch_transCom"));

		TableColumn colGrade = new TableColumn("버스등급");
		colGrade.setPrefWidth(100);
		colGrade.setStyle("-fx-allignment : CENTER");
		colGrade.setCellValueFactory(new PropertyValueFactory<>("sch_grade"));

		TableColumn colDate = new TableColumn("날짜");
		colDate.setPrefWidth(100);
		colDate.setStyle("-fx-allignment : CENTER");
		colDate.setCellValueFactory(new PropertyValueFactory<>("sch_date"));

		// scheduleTotalList();
		tvAllocate.getColumns().addAll(colDeparture, colArrival, colStartTime, colDistance, colLeadTime, colFee,
				colTransCom, colGrade, colDate);
		tvAllocate.setItems(schData);

		btnTicketing.setDisable(false);

		// 시간 계수설정
		cbStartTime.setItems(FXCollections.observableArrayList("00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
				"06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
				"17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"));

		// 출발지 컬럼가져오기
		aDao = new AllocateDAO();

		departureList = aDao.getDepartureList();
		departure = new String[departureList.size()];
		for (int i = 0; i < departureList.size(); i++) {
			departure[i] = departureList.get(i).getSch_departure();
		}

		// setting
		cbDeparture.setItems(FXCollections.observableArrayList(departure));

		// 도착지 컬럼가져오기
		arrivalList = aDao.getArrivalList();
		arrival = new String[arrivalList.size()];
		for (int k = 0; k < arrivalList.size(); k++) {
			arrival[k] = arrivalList.get(k).getSch_arrival();
		}

		// setting
		cbArrival.setItems(FXCollections.observableArrayList(arrival));

		btnTicketing.setDisable(true);

		// 조회버튼 이벤트
		btnSchSearch.setOnAction(event -> {

			ScheduleVO sVo = new ScheduleVO();
			AllocateBusDAO abDao = new AllocateBusDAO();
			String searchDeparture = "";
			String searchArrival = "";
			String searchStartTime = "";
			String searchDate = "";
			boolean searchResult = false;
			try {
				searchDeparture = cbDeparture.getSelectionModel().getSelectedItem().trim();
				searchArrival = cbArrival.getSelectionModel().getSelectedItem().trim();
				searchStartTime = cbStartTime.getSelectionModel().getSelectedItem().trim();
				LocalDate pickDate = dpStartDate.getValue();
				searchDate = pickDate + "";
				abDao = new AllocateBusDAO();
				sVo = abDao.getScheduleCheck(searchDeparture, searchArrival, searchStartTime, searchDate);

				if (searchDeparture.equals("") || searchArrival.equals("") || searchStartTime.equals("")
						|| searchDate.equals("")) {
					searchResult = true;
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("정보 검색");
					alert.setHeaderText("정보를 모두 입력해 주세요");
					alert.setContentText("주의");
					alert.showAndWait();
				}
				/*
				 * if (!(searchDeparture.equals("") && searchArrival.equals("") &&
				 * searchStartTime.equals("") && searchDate.equals("")) && (sVo != null) ) {
				 */
				if (sVo != null) {
					ArrayList<ScheduleVO> list;
					list = abDao.getScheduleTotal();
					int rowCount = list.size();

					schData.removeAll(schData);
					for (int index = 0; index < rowCount; index++) {
						sVo = list.get(index);
						if (sVo.getSch_departure().equals(searchDeparture) && sVo.getSch_arrival().equals(searchArrival)
								&& sVo.getSch_starttime().equals(searchStartTime)) {
							schData.add(sVo);
							searchResult = true;
						}
					}

				}

				if (!searchResult) {
					schData.removeAll(schData);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("정보 검색");
					alert.setHeaderText(searchDeparture + " " + searchArrival + " " + searchStartTime + " " + searchDate
							+ " 이 리스트에 없습니다");
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

		// 열 클릭이벤트
		tvAllocate.setOnMousePressed(event ->

		{
			try {
				schSelect = tvAllocate.getSelectionModel().getSelectedItems();
				no = schSelect.get(0).getB_seq();
				btnTicketing.setDisable(false);
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("시간표 선택");
				alert.setHeaderText("선택한 사항이 없습니다");
				alert.setContentText("선택하세요");
				alert.showAndWait();
			}
		});

		// 좌석선택 버튼 이벤트
		btnTicketing.setOnAction(event -> {

			schSelect = tvAllocate.getSelectionModel().getSelectedItems();

			if (schSelect.get(0).getSch_grade().equals("일반")) {

				/*
				 * try { FXMLLoader loader = new
				 * FXMLLoader(getClass().getResource("/view/ReservationNormal.fxml")); Parent
				 * ReservationNormal = (Parent) loader.load(); Scene scene = new
				 * Scene(ReservationNormal); Stage reservationNormalStage = new Stage();
				 * reservationNormalStage.setTitle("일반버스 좌석선택");
				 * reservationNormalStage.setScene(scene); Stage allocateStage = (Stage)
				 * btnAlloToMain.getScene().getWindow(); allocateStage.close();
				 * reservationNormalStage.show(); } catch (IOException e) {
				 * System.err.println("오류" + e); }
				 */

				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/ReservationNormal.fxml"));

					Stage dialog = new Stage(StageStyle.UTILITY);
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initOwner(btnSchSearch.getScene().getWindow());
					dialog.setTitle("좌석선택");

					Parent parentNormal = (Parent) loader.load();
					// ScheduleVO selectLine = tvAllocate.getSelectionModel().getSelectedItem();

					ComboBox<Integer> cbAdult = (ComboBox<Integer>) parentNormal.lookup("#cbAdult");
					cbAdult.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4));
					ComboBox<Integer> cbKid = (ComboBox<Integer>) parentNormal.lookup("#cbKid");
					cbKid.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4));
					ComboBox<Integer> cbAdolescent = (ComboBox<Integer>) parentNormal.lookup("#cbAdolescent");
					cbAdolescent.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4));

					cbAdult.setValue(0);
					cbKid.setValue(0);
					cbAdolescent.setValue(0);

					TextField txtAdultNum = (TextField) parentNormal.lookup("#txtAdultNum");
					TextField txtAdultFee = (TextField) parentNormal.lookup("#txtAdultFee");
					TextField txtKidNum = (TextField) parentNormal.lookup("#txtKidNum");
					TextField txtKidFee = (TextField) parentNormal.lookup("#txtKidFee");
					TextField txtAdoleNum = (TextField) parentNormal.lookup("#txtAdoleNum");
					TextField txtAdoleFee = (TextField) parentNormal.lookup("#txtAdoleFee");
					TextField txtTotalFee = (TextField) parentNormal.lookup("#txtTotalFee");

					TextField txtReserName = (TextField) parentNormal.lookup("#txtReserName");
					TextField txtReserBirth = (TextField) parentNormal.lookup("#txtReserBirth");

					TextField txtSeatNumber1 = (TextField) parentNormal.lookup("#txtSeatNumber1");
					TextField txtSeatNumber2 = (TextField) parentNormal.lookup("#txtSeatNumber2");
					TextField txtSeatNumber3 = (TextField) parentNormal.lookup("#txtSeatNumber3");
					TextField txtSeatNumber4 = (TextField) parentNormal.lookup("#txtSeatNumber4");

					txtAdultNum.setEditable(false);
					txtAdultFee.setEditable(false);
					txtKidNum.setEditable(false);
					txtKidFee.setEditable(false);
					txtAdoleNum.setEditable(false);
					txtAdoleFee.setEditable(false);
					txtTotalFee.setEditable(false);
					
					DecimalFormat format = new DecimalFormat("######");
					
					txtReserBirth.setTextFormatter(new TextFormatter<>(e -> {
						if (e.getControlNewText().isEmpty()) {
							return e;
						}
						ParsePosition parsePosition = new ParsePosition(0);
						Object object = format.parse(e.getControlNewText(), parsePosition);

						if (object == null || parsePosition.getIndex() < e.getControlNewText().length()
								|| e.getControlNewText().length() == 7) {
							return null;
						} else {
							return e;
						}
					}));

					Button btnClear = (Button) parentNormal.lookup("#btnClear");
					Button btnInputNumber = (Button) parentNormal.lookup("#btnInputNumber");
					Button btnInputFee = (Button) parentNormal.lookup("#btnInputFee");
					Button btnInputTotal = (Button) parentNormal.lookup("#btnInputTotal");
					Button btnSeatSelect = (Button) parentNormal.lookup("#btnSeatSelect");
					Button btnSeatCancel = (Button) parentNormal.lookup("#btnSeatCancel");

					Button btnSeatReset = (Button) parentNormal.lookup("#btnSeatReset");

					btnInputFee.setDisable(true);
					btnInputTotal.setDisable(true);
					btnSeatSelect.setDisable(true);

					CheckBox check1 = (CheckBox) parentNormal.lookup("#check1");
					CheckBox check2 = (CheckBox) parentNormal.lookup("#check2");
					CheckBox check3 = (CheckBox) parentNormal.lookup("#check3");
					CheckBox check4 = (CheckBox) parentNormal.lookup("#check4");
					CheckBox check5 = (CheckBox) parentNormal.lookup("#check5");
					CheckBox check6 = (CheckBox) parentNormal.lookup("#check6");
					CheckBox check7 = (CheckBox) parentNormal.lookup("#check7");
					CheckBox check8 = (CheckBox) parentNormal.lookup("#check8");
					CheckBox check9 = (CheckBox) parentNormal.lookup("#check9");
					CheckBox check10 = (CheckBox) parentNormal.lookup("#check10");
					CheckBox check11 = (CheckBox) parentNormal.lookup("#check11");
					CheckBox check12 = (CheckBox) parentNormal.lookup("#check12");
					CheckBox check13 = (CheckBox) parentNormal.lookup("#check13");
					CheckBox check14 = (CheckBox) parentNormal.lookup("#check14");
					CheckBox check15 = (CheckBox) parentNormal.lookup("#check15");
					CheckBox check16 = (CheckBox) parentNormal.lookup("#check16");
					CheckBox check17 = (CheckBox) parentNormal.lookup("#check17");
					CheckBox check18 = (CheckBox) parentNormal.lookup("#check18");
					CheckBox check19 = (CheckBox) parentNormal.lookup("#check19");
					CheckBox check20 = (CheckBox) parentNormal.lookup("#check20");
					CheckBox check21 = (CheckBox) parentNormal.lookup("#check21");
					CheckBox check22 = (CheckBox) parentNormal.lookup("#check22");
					CheckBox check23 = (CheckBox) parentNormal.lookup("#check23");
					CheckBox check24 = (CheckBox) parentNormal.lookup("#check24");
					CheckBox check25 = (CheckBox) parentNormal.lookup("#check25");
					CheckBox check26 = (CheckBox) parentNormal.lookup("#check26");
					CheckBox check27 = (CheckBox) parentNormal.lookup("#check27");
					CheckBox check28 = (CheckBox) parentNormal.lookup("#check28");
					CheckBox check29 = (CheckBox) parentNormal.lookup("#check29");
					CheckBox check30 = (CheckBox) parentNormal.lookup("#check30");
					CheckBox check31 = (CheckBox) parentNormal.lookup("#check31");
					CheckBox check32 = (CheckBox) parentNormal.lookup("#check32");
					CheckBox check33 = (CheckBox) parentNormal.lookup("#check33");
					CheckBox check34 = (CheckBox) parentNormal.lookup("#check34");
					CheckBox check35 = (CheckBox) parentNormal.lookup("#check35");
					CheckBox check36 = (CheckBox) parentNormal.lookup("#check36");
					CheckBox check37 = (CheckBox) parentNormal.lookup("#check37");
					CheckBox check38 = (CheckBox) parentNormal.lookup("#check38");
					CheckBox check39 = (CheckBox) parentNormal.lookup("#check39");
					CheckBox check40 = (CheckBox) parentNormal.lookup("#check40");
					
					check1.setDisable(true);
					check2.setDisable(true);
					check3.setDisable(true);
					check4.setDisable(true);
					check5.setDisable(true);
					check6.setDisable(true);
					check7.setDisable(true);
					check8.setDisable(true);
					check9.setDisable(true);
					check10.setDisable(true);
					check11.setDisable(true);
					check12.setDisable(true);
					check13.setDisable(true);
					check14.setDisable(true);
					check15.setDisable(true);
					check16.setDisable(true);
					check17.setDisable(true);
					check18.setDisable(true);
					check19.setDisable(true);
					check20.setDisable(true);
					check21.setDisable(true);
					check22.setDisable(true);
					check23.setDisable(true);
					check24.setDisable(true);
					check25.setDisable(true);
					check26.setDisable(true);
					check27.setDisable(true);
					check28.setDisable(true);
					check29.setDisable(true);
					check30.setDisable(true);
					check31.setDisable(true);
					check32.setDisable(true);
					check33.setDisable(true);
					check34.setDisable(true);
					check35.setDisable(true);
					check36.setDisable(true);
					check37.setDisable(true);
					check38.setDisable(true);
					check39.setDisable(true);
					check40.setDisable(true);
					
					btnSeatReset.setDisable(true);

					check1.setOnAction(e -> {

						seatNumberSelect = "1";

						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check1.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check2.setOnAction(e -> {

						seatNumberSelect = "2";

						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check2.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check3.setOnAction(e -> {
						seatNumberSelect = "3";

						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check3.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check4.setOnAction(e -> {
						seatNumberSelect = "4";

						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check4.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check5.setOnAction(e -> {
						seatNumberSelect = "5";

						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check5.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check6.setOnAction(e -> {
						seatNumberSelect = "6";

						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check6.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check7.setOnAction(e -> {
						seatNumberSelect = "7";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check7.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check8.setOnAction(e -> {
						seatNumberSelect = "8";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check8.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check9.setOnAction(e -> {
						seatNumberSelect = "9";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check9.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check10.setOnAction(e -> {
						seatNumberSelect = "10";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check10.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check11.setOnAction(e -> {
						seatNumberSelect = "11";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check11.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check12.setOnAction(e -> {
						seatNumberSelect = "12";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check12.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check13.setOnAction(e -> {
						seatNumberSelect = "13";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check13.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check14.setOnAction(e -> {
						seatNumberSelect = "14";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check14.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check15.setOnAction(e -> {
						seatNumberSelect = "15";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check15.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check16.setOnAction(e -> {
						seatNumberSelect = "16";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check16.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check17.setOnAction(e -> {
						seatNumberSelect = "17";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check17.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check18.setOnAction(e -> {
						seatNumberSelect = "18";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check18.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check19.setOnAction(e -> {
						seatNumberSelect = "19";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check19.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check20.setOnAction(e -> {
						seatNumberSelect = "20";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check20.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check21.setOnAction(e -> {
						seatNumberSelect = "21";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check21.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check22.setOnAction(e -> {
						seatNumberSelect = "22";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check22.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check23.setOnAction(e -> {
						seatNumberSelect = "23";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check23.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check24.setOnAction(e -> {
						seatNumberSelect = "24";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check24.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check25.setOnAction(e -> {
						seatNumberSelect = "25";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check25.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check26.setOnAction(e -> {
						seatNumberSelect = "26";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check26.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check27.setOnAction(e -> {
						seatNumberSelect = "27";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check27.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check28.setOnAction(e -> {
						seatNumberSelect = "28";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check28.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check29.setOnAction(e -> {
						seatNumberSelect = "29";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check29.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check30.setOnAction(e -> {
						seatNumberSelect = "30";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check30.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check31.setOnAction(e -> {
						seatNumberSelect = "31";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check31.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check32.setOnAction(e -> {
						seatNumberSelect = "32";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check32.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check33.setOnAction(e -> {
						seatNumberSelect = "33";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check33.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check34.setOnAction(e -> {
						seatNumberSelect = "34";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check34.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check35.setOnAction(e -> {
						seatNumberSelect = "35";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check35.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check36.setOnAction(e -> {
						seatNumberSelect = "36";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check36.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check37.setOnAction(e -> {
						seatNumberSelect = "37";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check37.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check38.setOnAction(e -> {
						seatNumberSelect = "38";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check38.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check39.setOnAction(e -> {
						seatNumberSelect = "39";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check39.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					check40.setOnAction(e -> {
						seatNumberSelect = "40";
						if (seat > seatCount) {
							if (txtSeatNumber1.getText().isEmpty()) {
								txtSeatNumber1.setText(seatNumberSelect);
							} else if (txtSeatNumber2.getText().isEmpty()) {
								txtSeatNumber2.setText(seatNumberSelect);
							} else if (txtSeatNumber3.getText().isEmpty()) {
								txtSeatNumber3.setText(seatNumberSelect);
							} else if (txtSeatNumber4.getText().isEmpty()) {
								txtSeatNumber4.setText(seatNumberSelect);
							}
							seatCount++;
							check40.setDisable(true);
						}
						if (seat <= seatCount) {

							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("좌석선택 완료");
							alert.setContentText("좌석이 모두 선택되었습니다.");

							alert.showAndWait();

							check1.setDisable(true);
							check2.setDisable(true);
							check3.setDisable(true);
							check4.setDisable(true);
							check5.setDisable(true);
							check6.setDisable(true);
							check7.setDisable(true);
							check8.setDisable(true);
							check9.setDisable(true);
							check10.setDisable(true);
							check11.setDisable(true);
							check12.setDisable(true);
							check13.setDisable(true);
							check14.setDisable(true);
							check15.setDisable(true);
							check16.setDisable(true);
							check17.setDisable(true);
							check18.setDisable(true);
							check19.setDisable(true);
							check20.setDisable(true);
							check21.setDisable(true);
							check22.setDisable(true);
							check23.setDisable(true);
							check24.setDisable(true);
							check25.setDisable(true);
							check26.setDisable(true);
							check27.setDisable(true);
							check28.setDisable(true);
							check29.setDisable(true);
							check30.setDisable(true);
							check31.setDisable(true);
							check32.setDisable(true);
							check33.setDisable(true);
							check34.setDisable(true);
							check35.setDisable(true);
							check36.setDisable(true);
							check37.setDisable(true);
							check38.setDisable(true);
							check39.setDisable(true);
							check40.setDisable(true);

							btnSeatSelect.setDisable(false);
						}
					});

					// 초기화 버튼
					btnClear.setOnAction(e -> {
						seat = 0;
						seatCount = 0;
						cbAdult.setValue(0);
						cbKid.setValue(0);
						cbAdolescent.setValue(0);
						cbAdult.setDisable(false);
						cbKid.setDisable(false);
						cbAdolescent.setDisable(false);

						btnInputNumber.setDisable(false);

						txtAdultNum.clear();
						txtAdultFee.clear();
						txtKidNum.clear();
						txtKidFee.clear();
						txtAdoleNum.clear();
						txtAdoleFee.clear();
						txtTotalFee.clear();

						txtSeatNumber1.clear();
						txtSeatNumber2.clear();
						txtSeatNumber3.clear();
						txtSeatNumber4.clear();

						btnInputNumber.setDisable(false);
						btnInputFee.setDisable(true);
						btnInputTotal.setDisable(true);
						btnSeatSelect.setDisable(true);

						check1.setSelected(false);
						check2.setSelected(false);
						check3.setSelected(false);
						check4.setSelected(false);
						check5.setSelected(false);
						check6.setSelected(false);
						check7.setSelected(false);
						check8.setSelected(false);
						check9.setSelected(false);
						check10.setSelected(false);
						check11.setSelected(false);
						check12.setSelected(false);
						check13.setSelected(false);
						check14.setSelected(false);
						check15.setSelected(false);
						check16.setSelected(false);
						check17.setSelected(false);
						check18.setSelected(false);
						check19.setSelected(false);
						check20.setSelected(false);
						check21.setSelected(false);
						check22.setSelected(false);
						check23.setSelected(false);
						check24.setSelected(false);
						check25.setSelected(false);
						check26.setSelected(false);
						check27.setSelected(false);
						check28.setSelected(false);
						check29.setSelected(false);
						check30.setSelected(false);
						check31.setSelected(false);
						check32.setSelected(false);
						check33.setSelected(false);
						check34.setSelected(false);
						check35.setSelected(false);
						check36.setSelected(false);
						check37.setSelected(false);
						check38.setSelected(false);
						check39.setSelected(false);
						check40.setSelected(false);
						
						check1.setDisable(true);
						check2.setDisable(true);
						check3.setDisable(true);
						check4.setDisable(true);
						check5.setDisable(true);
						check6.setDisable(true);
						check7.setDisable(true);
						check8.setDisable(true);
						check9.setDisable(true);
						check10.setDisable(true);
						check11.setDisable(true);
						check12.setDisable(true);
						check13.setDisable(true);
						check14.setDisable(true);
						check15.setDisable(true);
						check16.setDisable(true);
						check17.setDisable(true);
						check18.setDisable(true);
						check19.setDisable(true);
						check20.setDisable(true);
						check21.setDisable(true);
						check22.setDisable(true);
						check23.setDisable(true);
						check24.setDisable(true);
						check25.setDisable(true);
						check26.setDisable(true);
						check27.setDisable(true);
						check28.setDisable(true);
						check29.setDisable(true);
						check30.setDisable(true);
						check31.setDisable(true);
						check32.setDisable(true);
						check33.setDisable(true);
						check34.setDisable(true);
						check35.setDisable(true);
						check36.setDisable(true);
						check37.setDisable(true);
						check38.setDisable(true);
						check39.setDisable(true);
						check40.setDisable(true);
						
						btnSeatReset.setDisable(true);

					});

					// 인원입력 버튼 이벤트
					btnInputNumber.setOnAction(e -> {

						seat = cbAdult.getSelectionModel().getSelectedItem()
								+ cbKid.getSelectionModel().getSelectedItem()
								+ cbAdolescent.getSelectionModel().getSelectedItem();

						if (seat > 0 && seat < 5) {
							cbAdult.setDisable(true);
							cbKid.setDisable(true);
							cbAdolescent.setDisable(true);

							txtAdultNum.setText(cbAdult.getSelectionModel().getSelectedItem() + "");
							txtKidNum.setText(cbKid.getSelectionModel().getSelectedItem() + "");
							txtAdoleNum.setText(cbAdolescent.getSelectionModel().getSelectedItem() + "");

							cbAdult.setDisable(true);
							cbKid.setDisable(true);
							cbAdolescent.setDisable(true);
							btnInputNumber.setDisable(true);
							btnInputFee.setDisable(false);
							btnInputTotal.setDisable(true);

						} else {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("경고");
							alert.setHeaderText("인원선택 오류");
							alert.setContentText("선택인원은 0명일수 없고 최대 4명입니다.");
							alert.showAndWait();
						}
					});

					// 요금 계산 버튼 이벤트
					btnInputFee.setOnAction(e -> {

						txtAdultFee.setText(
								(Integer.parseInt(txtAdultNum.getText().trim()) * schSelect.get(0).getSch_fee()) + "");
						txtKidFee.setText((Math.round(
								Integer.parseInt(txtKidNum.getText().trim()) * schSelect.get(0).getSch_fee() * 0.005)
								* 100) + "");
						txtAdoleFee.setText((Math.round(
								Integer.parseInt(txtAdoleNum.getText().trim()) * schSelect.get(0).getSch_fee() * 0.007)
								* 100) + "");

						btnInputTotal.setDisable(false);
						btnInputFee.setDisable(true);
					});

					// 총요금 계산 버튼 이벤트
					btnInputTotal.setOnAction(e -> {
						
						check1.setDisable(false);
						check2.setDisable(false);
						check3.setDisable(false);
						check4.setDisable(false);
						check5.setDisable(false);
						check6.setDisable(false);
						check7.setDisable(false);
						check8.setDisable(false);
						check9.setDisable(false);
						check10.setDisable(false);
						check11.setDisable(false);
						check12.setDisable(false);
						check13.setDisable(false);
						check14.setDisable(false);
						check15.setDisable(false);
						check16.setDisable(false);
						check17.setDisable(false);
						check18.setDisable(false);
						check19.setDisable(false);
						check20.setDisable(false);
						check21.setDisable(false);
						check22.setDisable(false);
						check23.setDisable(false);
						check24.setDisable(false);
						check25.setDisable(false);
						check26.setDisable(false);
						check27.setDisable(false);
						check28.setDisable(false);
						check29.setDisable(false);
						check30.setDisable(false);
						check31.setDisable(false);
						check32.setDisable(false);
						check33.setDisable(false);
						check34.setDisable(false);
						check35.setDisable(false);
						check36.setDisable(false);
						check37.setDisable(false);
						check38.setDisable(false);
						check39.setDisable(false);
						check40.setDisable(false);
						
						btnSeatReset.setDisable(false);

						txtTotalFee.setText(Integer.parseInt(txtAdultFee.getText().trim())
								+ Integer.parseInt(txtKidFee.getText().trim())
								+ Integer.parseInt(txtAdoleFee.getText().trim()) + "");

						btnInputTotal.setDisable(true);

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("예매");
						alert.setContentText("좌석을 선택해 주세요");

						alert.showAndWait();
					});

					// 좌석선택 완료 버튼 이벤트
					btnSeatSelect.setOnAction(e -> {

						if (!(txtReserName.getText().isEmpty() || txtReserBirth.getText().isEmpty())
								&& seat == seatCount) {
							try {
								ReservationInfoVO rVo = null;
								AllocateDAO aDao = new AllocateDAO();

								if (e.getSource().equals(btnSeatSelect)) {
									rVo = new ReservationInfoVO(txtSeatNumber1.getText(), txtSeatNumber2.getText(),
											txtSeatNumber3.getText(), txtSeatNumber4.getText(),
											Integer.parseInt(txtAdultNum.getText()),
											Integer.parseInt(txtKidNum.getText()),
											Integer.parseInt(txtAdoleNum.getText()),
											Integer.parseInt(txtTotalFee.getText()), txtReserName.getText(),
											txtReserBirth.getText(), schSelect.get(0).getSch_seq());
									aDao = new AllocateDAO();
									aDao.getReservationInforegiste(rVo);

									if (aDao != null) {
										Alert alert = new Alert(AlertType.INFORMATION);
										alert.setTitle("티켓정보 입력");
										alert.setHeaderText("정보입력 완료");
										alert.showAndWait();
									}
								}
							} catch (Exception e1) {
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("티켓");
								alert.setHeaderText("실패");
								alert.setContentText("실패!");
								alert.showAndWait();
							}

							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("예매");
							alert.setContentText("좌석 예매 완료");

							ButtonType buttonTypeOk = new ButtonType("시 간 표 조 회 창 으 로 . . .");

							alert.getButtonTypes().setAll(buttonTypeOk);

							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == buttonTypeOk) {
								dialog.close();
							}
						} else {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("경고");
							alert.setHeaderText("정보 오류");
							alert.setContentText("예매자 정보 미입력 또는 좌석선택 미완료");
							alert.showAndWait();
						}

					});

					// 좌석선택 취소 버튼
					btnSeatCancel.setOnAction(e -> {
						dialog.close();
					});

					// 좌석선택 초기화 버튼
					btnSeatReset.setOnAction(e -> {

						seatCount = 0;

						check1.setSelected(false);
						check2.setSelected(false);
						check3.setSelected(false);
						check4.setSelected(false);
						check5.setSelected(false);
						check6.setSelected(false);
						check7.setSelected(false);
						check8.setSelected(false);
						check9.setSelected(false);
						check10.setSelected(false);
						check11.setSelected(false);
						check12.setSelected(false);
						check13.setSelected(false);
						check14.setSelected(false);
						check15.setSelected(false);
						check16.setSelected(false);
						check17.setSelected(false);
						check18.setSelected(false);
						check19.setSelected(false);
						check20.setSelected(false);
						check21.setSelected(false);
						check22.setSelected(false);
						check23.setSelected(false);
						check24.setSelected(false);
						check25.setSelected(false);
						check26.setSelected(false);
						check27.setSelected(false);
						check28.setSelected(false);
						check29.setSelected(false);
						check30.setSelected(false);
						check31.setSelected(false);
						check32.setSelected(false);
						check33.setSelected(false);
						check34.setSelected(false);
						check35.setSelected(false);
						check36.setSelected(false);
						check37.setSelected(false);
						check38.setSelected(false);
						check39.setSelected(false);
						check40.setSelected(false);

						check1.setDisable(false);
						check2.setDisable(false);
						check3.setDisable(false);
						check4.setDisable(false);
						check5.setDisable(false);
						check6.setDisable(false);
						check7.setDisable(false);
						check8.setDisable(false);
						check9.setDisable(false);
						check10.setDisable(false);
						check11.setDisable(false);
						check12.setDisable(false);
						check13.setDisable(false);
						check14.setDisable(false);
						check15.setDisable(false);
						check16.setDisable(false);
						check17.setDisable(false);
						check18.setDisable(false);
						check19.setDisable(false);
						check20.setDisable(false);
						check21.setDisable(false);
						check22.setDisable(false);
						check23.setDisable(false);
						check24.setDisable(false);
						check25.setDisable(false);
						check26.setDisable(false);
						check27.setDisable(false);
						check28.setDisable(false);
						check29.setDisable(false);
						check30.setDisable(false);
						check31.setDisable(false);
						check32.setDisable(false);
						check33.setDisable(false);
						check34.setDisable(false);
						check35.setDisable(false);
						check36.setDisable(false);
						check37.setDisable(false);
						check38.setDisable(false);
						check39.setDisable(false);
						check40.setDisable(false);
						
						btnSeatSelect.setDisable(true);

						txtSeatNumber1.clear();
						txtSeatNumber2.clear();
						txtSeatNumber3.clear();
						txtSeatNumber4.clear();

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("좌석선택 초기화");
						alert.setHeaderText("초기화");
						alert.setContentText("좌석선택이 초기화 되었습니다.");

						alert.showAndWait();
					});

					Scene scene = new Scene(parentNormal);
					dialog.setScene(scene);
					dialog.setResizable(false);
					dialog.show();

				} catch (IOException e) {
					System.out.println(e.toString());
				}

			}

		});

		// 메인으로 돌아가는 버튼 이벤트
		btnAlloToMain.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView1.fxml"));
				Parent mainView1 = (Parent) loader.load();
				Scene scene = new Scene(mainView1);
				Stage mainViewStage = new Stage();
				mainViewStage.setTitle("고속버스 예약시스템");
				mainViewStage.setScene(scene);
				Stage allocateStage = (Stage) btnAlloToMain.getScene().getWindow();
				allocateStage.close();
				mainViewStage.show();
			} catch (IOException e) {
				System.err.println("오류" + e);
			}
		});

	}

	// 시간표 토탈리스트
	public void scheduleTotalList() {
		Object[][] totalData;

		ScheduleVO sVo = new ScheduleVO();
		AllocateBusDAO abDao = new AllocateBusDAO();

		ArrayList<String> title;
		ArrayList<ScheduleVO> list;

		title = abDao.getColumnName();
		int columnCount = title.size();

		list = abDao.getScheduleTotal();
		int rowCount = list.size();

		totalData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			sVo = list.get(index);
			schData.add(sVo);
		}

	}

}
