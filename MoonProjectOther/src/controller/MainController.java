package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController implements Initializable {

	@FXML
	private Button btnAllocateInfo; // 배차정보조회 및 예매 버튼
	@FXML
	private Button btnReservation; // 예매정보조회 버튼
	@FXML
	private Button btnOperationSch; // 운행정보 입력수정삭제 버튼
	@FXML
	private Button btnBus; // 버스정보 입력수정삭제 버튼
	@FXML
	private Button btnAllocateBus; // 버스 배차 버튼
	@FXML
	private Button btnMainExit; // 메인창 종료 버튼

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// 배차정보조회 및 예매 버튼 이벤트
		btnAllocateInfo.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllocateView.fxml")); // 배차정보조회 뷰 로드
				Parent allocateView = (Parent) loader.load();
				Scene scene = new Scene(allocateView); // 배차정보조회 뷰의 씬을 생성
				Stage allocateStage = new Stage(); // 배차정보조회 뷰의 스테이지 생성
				allocateStage.setTitle("배차정보 조회"); // 타이틀 셋
				allocateStage.setScene(scene); // 씬 셋
				Stage mainViewStage = (Stage) btnAllocateInfo.getScene().getWindow();
				mainViewStage.close(); // 메인창 닫기
				allocateStage.show(); // 배차정보조회 창 보여지기
			} catch (IOException e) {
				System.err.println("오류" + e);
			}
		});

		// 예매정보 조회 버튼 이벤트
		btnReservation.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReservationInfo.fxml")); // 뷰로드
				Parent reservationInfo = (Parent) loader.load();
				Scene scene = new Scene(reservationInfo); // 로드한 뷰의 씬생성
				Stage reservationStage = new Stage(); // 스테이지 생성
				reservationStage.setTitle("예약정보 조회"); // 타이틀 네이밍
				reservationStage.setScene(scene);
				Stage mainViewStage = (Stage) btnReservation.getScene().getWindow();
				mainViewStage.close();
				reservationStage.show();
			} catch (IOException e) {
				System.err.println("오류" + e);
			}
		});
		
		// 운행정보 입력 수정 삭제 버튼 이벤트
		btnOperationSch.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OperationSchView.fxml"));
				Parent operationSchView = (Parent) loader.load();
				Scene scene = new Scene(operationSchView);
				Stage operationSchStage = new Stage();
				operationSchStage.setTitle("운행정보 관리");
				operationSchStage.setScene(scene);
				Stage mainViewStage = (Stage) btnOperationSch.getScene().getWindow();
				mainViewStage.close();
				operationSchStage.show();
			} catch (IOException e) {
				System.err.println("오류" + e);
			}
		});
		
		// 버스정보 입력 수정 삭제 버튼 이벤트
		btnBus.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BusView.fxml"));
				Parent busView = (Parent) loader.load();
				Scene scene = new Scene(busView);
				Stage busStage = new Stage();
				busStage.setTitle("버스정보 관리");
				busStage.setScene(scene);
				Stage mainViewStage = (Stage) btnBus.getScene().getWindow();
				mainViewStage.close();
				busStage.show();
			} catch (IOException e) {
				System.err.println("오류" + e);
			}
		});
		
		// 버스배차 버튼 이벤트
		btnAllocateBus.setOnAction(event -> {
			try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllocateBus.fxml"));
			Parent allocateBus = (Parent) loader.load();
			Scene scene = new Scene(allocateBus);
			Stage allocateBusStage = new Stage();
			allocateBusStage.setTitle("배차관리");
			allocateBusStage.setScene(scene);
			Stage mainViewStage = (Stage) btnAllocateBus.getScene().getWindow();
			mainViewStage.close();
			allocateBusStage.show();
			} catch (IOException e) {
				System.err.println("오류" + e);
			}
		});

		// 메인창 종료 버튼 이벤트
		btnMainExit.setOnAction(event -> Platform.exit());

	}

}
