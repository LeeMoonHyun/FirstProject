package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ScheduleVO;

public class ReservationNormalController implements Initializable {

	@FXML
	private ComboBox<Integer> cbAdult; // 성인인원 콤보박스
	@FXML
	private ComboBox<Integer> cbKid; // 아동인원 콤보박스
	@FXML
	private ComboBox<Integer> cbAdolescent; // 중고생 인원 콤보박스
	@FXML
	private TextField txtAdultNum; // 성인인원
	@FXML
	private TextField txtAdultFee; // 성인표 총 가격
	@FXML
	private TextField txtKidNum; // 아동 인원
	@FXML
	private TextField txtKidFee; // 아동표 총 가격
	@FXML
	private TextField txtAdoleNum; // 중고생 인원
	@FXML
	private TextField txtAdoleFee; // 중고생표 총 가격
	@FXML
	private TextField txtTotalFee; // 총인원 총 가격
	@FXML
	private TextField txtSeatNumber1; // 첫번째 선택한 좌석 번호
	@FXML
	private TextField txtSeatNumber2; // 두번째 선택한 좌석 번호
	@FXML
	private TextField txtSeatNumber3; // 세번째 선택한 좌석 번호
	@FXML
	private TextField txtSeatNumber4; // 네번째 선택한 좌석 번호
	@FXML
	private Button btnClear; // 초기화 버튼
	@FXML
	private Button btnInputNumber; // 인원수 입력 버튼
	@FXML
	private Button btnInputFee; // 요금 계산 버튼
	@FXML
	private Button btnInputTotal; // 총 요금 계산 버튼
	@FXML
	private Button btnSeatSelect; // 좌석선택 완료 버튼
	@FXML
	private Button btnSeatCancel; // 좌석선택 취소 버튼
	@FXML
	private Button btnSeatReset; // 좌석선택 초기화 버튼
	@FXML
	private CheckBox check1;
	@FXML
	private CheckBox check2;
	@FXML
	private CheckBox check3;
	@FXML
	private CheckBox check4;
	@FXML
	private CheckBox check5;
	@FXML
	private CheckBox check6;
	@FXML
	private CheckBox check7;
	@FXML
	private CheckBox check8;
	@FXML
	private CheckBox check9;
	@FXML
	private CheckBox check10;
	@FXML
	private CheckBox check11;
	@FXML
	private CheckBox check12;
	@FXML
	private CheckBox check13;
	@FXML
	private CheckBox check14;
	@FXML
	private CheckBox check15;
	@FXML
	private CheckBox check16;
	@FXML
	private CheckBox check17;
	@FXML
	private CheckBox check18;
	@FXML
	private CheckBox check19;
	@FXML
	private CheckBox check20;
	@FXML
	private CheckBox check21;
	@FXML
	private CheckBox check22;
	@FXML
	private CheckBox check23;
	@FXML
	private CheckBox check24;
	@FXML
	private CheckBox check25;
	@FXML
	private CheckBox check26;
	@FXML
	private CheckBox check27;
	@FXML
	private CheckBox check28;
	@FXML
	private CheckBox check29;
	@FXML
	private CheckBox check30;
	@FXML
	private CheckBox check31;
	@FXML
	private CheckBox check32;
	@FXML
	private CheckBox check33;
	@FXML
	private CheckBox check34;
	@FXML
	private CheckBox check35;
	@FXML
	private CheckBox check36;
	@FXML
	private CheckBox check37;
	@FXML
	private CheckBox check38;
	@FXML
	private CheckBox check39;
	@FXML
	private CheckBox check40;

	ObservableList<ScheduleVO> schSelect = null;

	static int seat = 0; // 인원수
	static int seatCount = 0; // 선택한 좌석수
	static String seatNumberSelect; // 좌석번호

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		cbAdult.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4));
		cbKid.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4));
		cbAdolescent.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4));

		cbAdult.setValue(1);
		cbKid.setValue(0);
		cbAdolescent.setValue(0);

		txtAdultNum.setEditable(false);
		txtAdultFee.setEditable(false);
		txtKidNum.setEditable(false);
		txtKidFee.setEditable(false);
		txtAdoleNum.setEditable(false);
		txtAdoleFee.setEditable(false);
		txtTotalFee.setEditable(false);

		btnInputFee.setDisable(true);
		btnInputTotal.setDisable(true);
		btnSeatSelect.setDisable(true);

		// 어른콤보박스 이벤트
		cbAdult.setOnAction(event -> {
			seat += cbAdult.getSelectionModel().getSelectedItem();
			if (seat >= 5) {
				cbAdult.setDisable(true);
				cbKid.setDisable(true);
				cbAdolescent.setDisable(true);
				btnInputNumber.setDisable(true);
				btnSeatSelect.setDisable(true);

				Alert alertSeat = new Alert(AlertType.WARNING);
				alertSeat.setTitle("좌석선택");
				alertSeat.setHeaderText("초기화 하세요");
				alertSeat.setContentText("좌석선택은 최대 4명 입니다.");
				alertSeat.showAndWait();
			}
		});

		// 아이 콤보박스 이벤트
		cbKid.setOnAction(event -> {
			seat += cbKid.getSelectionModel().getSelectedItem();
			if (seat >= 5) {
				cbAdult.setDisable(true);
				cbKid.setDisable(true);
				cbAdolescent.setDisable(true);
				btnInputNumber.setDisable(true);
				btnSeatSelect.setDisable(true);

				Alert alertSeat = new Alert(AlertType.WARNING);
				alertSeat.setTitle("좌석선택");
				alertSeat.setHeaderText("초기화 하세요");
				alertSeat.setContentText("좌석선택은 최대 4명 입니다.");
				alertSeat.showAndWait();
			}
		});

		// 중고생 콤보박스 이벤트
		cbAdolescent.setOnAction(event -> {
			seat += cbAdolescent.getSelectionModel().getSelectedItem();
			if (seat >= 5) {
				cbAdult.setDisable(true);
				cbKid.setDisable(true);
				cbAdolescent.setDisable(true);
				btnInputNumber.setDisable(true);
				btnSeatSelect.setDisable(true);

				Alert alertSeat = new Alert(AlertType.WARNING);
				alertSeat.setTitle("좌석선택");
				alertSeat.setHeaderText("초기화 하세요");
				alertSeat.setContentText("좌석선택은 최대 4명 입니다.");
				alertSeat.showAndWait();
			}
		});

		// 인원입력 버튼 이벤트
		btnInputNumber.setOnAction(event -> {

			txtAdultNum.setText(cbAdult.getSelectionModel().getSelectedItem() + "");
			txtKidNum.setText(cbKid.getSelectionModel().getSelectedItem() + "");
			txtAdoleNum.setText(cbAdolescent.getSelectionModel().getSelectedItem() + "");

			cbAdult.setDisable(true);
			cbKid.setDisable(true);
			cbAdolescent.setDisable(true);
			btnInputNumber.setDisable(true);
			btnInputFee.setDisable(false);
			btnInputTotal.setDisable(true);
		});

		// 요금 계산 버튼 이벤트
		btnInputFee.setOnAction(event -> {

			txtAdultFee.setText((Integer.parseInt(txtAdultNum.getText().trim()) * schSelect.get(0).getSch_fee()) + "");
			txtKidFee.setText(
					(Math.round(Integer.parseInt(txtKidNum.getText().trim()) * schSelect.get(0).getSch_fee() * 0.005)
							* 100) + "");
			txtAdoleFee.setText(
					(Math.round(Integer.parseInt(txtAdoleNum.getText().trim()) * schSelect.get(0).getSch_fee() * 0.007)
							* 100) + "");

			btnInputTotal.setDisable(false);
			btnInputFee.setDisable(true);
		});

		// 총요금 계산 버튼 이벤트
		btnInputTotal.setOnAction(event -> {

			txtTotalFee.setText(
					Integer.parseInt(txtAdultFee.getText().trim()) + Integer.parseInt(txtKidFee.getText().trim())
							+ Integer.parseInt(txtAdoleFee.getText().trim()) + "");

			btnInputTotal.setDisable(true);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("예매");
			alert.setContentText("좌석을 선택해 주세요");

			alert.showAndWait();
		});

		// 체크박스 이벤트
		check1.setOnAction(event -> {

			seatNumberSelect = "1";

			if (seat > seatCount) {
				inputTextField();
				seatCount++;
				check1.setDisable(true);
			}
			if (seat <= seatCount) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("좌석선택 완료");
				alert.setContentText("좌석이 모두 선택되었습니다.");

				alert.showAndWait();

				checkBoxInactivation();

				btnSeatSelect.setDisable(false);
			}
		});

		// 좌석선택 완료 버튼 이벤트
		btnSeatSelect.setOnAction(event -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("예매");
			alert.setContentText("좌석 예매 완료");

			ButtonType buttonTypeOk = new ButtonType("시 간 표 조 회 창 으 로 . . .");

			alert.getButtonTypes().setAll(buttonTypeOk);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOk) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllocateView.fxml")); // 배차정보조회 뷰
																											// 로드
					Parent allocateView = (Parent) loader.load();
					Scene scene = new Scene(allocateView); // 배차정보조회 뷰의 씬을 생성
					Stage allocateStage = new Stage(); // 배차정보조회 뷰의 스테이지 생성
					allocateStage.setTitle("배차정보 조회"); // 타이틀 셋
					allocateStage.setScene(scene); // 씬 셋
					Stage normalViewStage = (Stage) btnSeatCancel.getScene().getWindow();
					normalViewStage.close(); // 메인창 닫기
					allocateStage.show(); // 배차정보조회 창 보여지기
				} catch (IOException e) {
					System.err.println("오류" + e);
				}
			}
		});

		// 좌석선택 취소 버튼 - 시간표 조회창으로
		btnSeatCancel.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllocateView.fxml")); // 배차정보조회 뷰 로드
				Parent allocateView = (Parent) loader.load();
				Scene scene = new Scene(allocateView); // 배차정보조회 뷰의 씬을 생성
				Stage allocateStage = new Stage(); // 배차정보조회 뷰의 스테이지 생성
				allocateStage.setTitle("배차정보 조회"); // 타이틀 셋
				allocateStage.setScene(scene); // 씬 셋
				Stage normalViewStage = (Stage) btnSeatCancel.getScene().getWindow();
				normalViewStage.close(); // 메인창 닫기
				allocateStage.show(); // 배차정보조회 창 보여지기
			} catch (IOException e) {
				System.err.println("오류" + e);
			}
		});

		// 좌석선택 초기화 버튼
		btnSeatReset.setOnAction(event -> {

			seatCount = 0;

			checkBoxUnselected();

			checkBoxActivation();

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

	}

	// 선택된 좌석번호 텍스트필드에 입력 메소드
	public void inputTextField() {
		if (txtSeatNumber1.getText().isEmpty()) {
			txtSeatNumber1.setText(seatNumberSelect);
		} else if (txtSeatNumber2.getText().isEmpty()) {
			txtSeatNumber2.setText(seatNumberSelect);
		} else if (txtSeatNumber3.getText().isEmpty()) {
			txtSeatNumber3.setText(seatNumberSelect);
		} else if (txtSeatNumber4.getText().isEmpty()) {
			txtSeatNumber4.setText(seatNumberSelect);
		}
	}
	
	// 체크박스 활성화 메소드
	public void checkBoxActivation() {
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
	}

	// 체크박스 비활성화 메소드
	public void checkBoxInactivation() {
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
	}

	// 체크박스 전체 선택 해제 매소드
	public void checkBoxUnselected() {
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
	}

}
