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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ReservationInfoVO;

public class ReservationinfoController implements Initializable {

	@FXML
	private TextField txtReserInfoName; // 조회할 예매자 이름
	@FXML
	private TextField txtReserInfoBirth; // 조회할 예매자 생년월일
	@FXML
	private Button btnReserInfoSearch; // 조회 버튼
	@FXML
	private Button btnReserToMain; // 메인으로 돌아가는 버튼
	@FXML
	private TableView<ReservationInfoVO> tvReservationInfo; // 조회정보 출력 테이블뷰

	ObservableList<ReservationInfoVO> reserinfoData = FXCollections.observableArrayList();
	ObservableList<ReservationInfoVO> reserinfoSelect = null;
	ReservationInfoVO reservationInfo = new ReservationInfoVO();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// 생년월일란에 숫자만 입력하고 자릿수 제한
		DecimalFormat format = new DecimalFormat("######");
		
		txtReserInfoBirth.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 7) {
				return null;
			} else {
				return event;
			}
		}));

		// 테이블뷰 컬럼 설정
		TableColumn colreserinfoNo = new TableColumn("No.");
		colreserinfoNo.setPrefWidth(40);
		colreserinfoNo.setStyle("-fx-allignment : CENTER");
		colreserinfoNo.setCellValueFactory(new PropertyValueFactory<>("t_seq"));

		TableColumn colreserinfoName = new TableColumn("이름");
		colreserinfoName.setPrefWidth(60);
		colreserinfoName.setStyle("-fx-allignment : CENTER");
		colreserinfoName.setCellValueFactory(new PropertyValueFactory<>("t_name"));

		TableColumn colreserinfoBirth = new TableColumn("생년월일");
		colreserinfoBirth.setPrefWidth(60);
		colreserinfoBirth.setStyle("-fx-allignment : CENTER");
		colreserinfoBirth.setCellValueFactory(new PropertyValueFactory<>("t_birth"));

		TableColumn colreserinfoSeat1 = new TableColumn("좌석1");
		colreserinfoSeat1.setPrefWidth(50);
		colreserinfoSeat1.setStyle("-fx-allignment : CENTER");
		colreserinfoSeat1.setCellValueFactory(new PropertyValueFactory<>("t_seatNumber1"));

		TableColumn colreserinfoSeat2 = new TableColumn("좌석2");
		colreserinfoSeat2.setPrefWidth(50);
		colreserinfoSeat2.setStyle("-fx-allignment : CENTER");
		colreserinfoSeat2.setCellValueFactory(new PropertyValueFactory<>("t_seatNumber2"));

		TableColumn colreserinfoSeat3 = new TableColumn("좌석3");
		colreserinfoSeat3.setPrefWidth(50);
		colreserinfoSeat3.setStyle("-fx-allignment : CENTER");
		colreserinfoSeat3.setCellValueFactory(new PropertyValueFactory<>("t_seatNumber3"));

		TableColumn colreserinfoSeat4 = new TableColumn("좌석4");
		colreserinfoSeat4.setPrefWidth(50);
		colreserinfoSeat4.setStyle("-fx-allignment : CENTER");
		colreserinfoSeat4.setCellValueFactory(new PropertyValueFactory<>("t_seatNumber4"));

		TableColumn colreserinfoAdultNum = new TableColumn("성인");
		colreserinfoAdultNum.setPrefWidth(50);
		colreserinfoAdultNum.setStyle("-fx-allignment : CENTER");
		colreserinfoAdultNum.setCellValueFactory(new PropertyValueFactory<>("t_adultNum"));

		TableColumn colreserinfoKidNum = new TableColumn("아동");
		colreserinfoKidNum.setPrefWidth(50);
		colreserinfoKidNum.setStyle("-fx-allignment : CENTER");
		colreserinfoKidNum.setCellValueFactory(new PropertyValueFactory<>("t_kidNum"));

		TableColumn colreserinfoAdolNum = new TableColumn("중고생");
		colreserinfoAdolNum.setPrefWidth(50);
		colreserinfoAdolNum.setStyle("-fx-allignment : CENTER");
		colreserinfoAdolNum.setCellValueFactory(new PropertyValueFactory<>("t_adolNum"));

		TableColumn colreserinfoTotalFee = new TableColumn("총요금");
		colreserinfoTotalFee.setPrefWidth(100);
		colreserinfoTotalFee.setStyle("-fx-allignment : CENTER");
		colreserinfoTotalFee.setCellValueFactory(new PropertyValueFactory<>("t_totalFee"));

		// 테이블뷰에 컬럼설정
		tvReservationInfo.getColumns().addAll(colreserinfoNo, colreserinfoName, colreserinfoBirth, colreserinfoSeat1, colreserinfoSeat2, colreserinfoSeat3,
				colreserinfoSeat4, colreserinfoAdultNum, colreserinfoKidNum, colreserinfoAdolNum, colreserinfoTotalFee);
		
		tvReservationInfo.setItems(reserinfoData);
		
		// 조회버튼 이벤트
		btnReserInfoSearch.setOnAction(event -> {
			
			ReservationInfoVO riVo = new ReservationInfoVO();
			ReservationInfoDAO riDao = null;
			
			String searchReserName = "";
			String searchReserBirth = "";
			boolean searchResult = false;
			
			try {
				searchReserName = txtReserInfoName.getText().trim();
				searchReserBirth = txtReserInfoBirth.getText().trim();
				
				riDao = new ReservationInfoDAO();
				riVo = riDao.getReservationInfoCheck(searchReserName, searchReserBirth);
				
				if(searchReserName.trim().isEmpty() || searchReserBirth.trim().isEmpty()) {
					searchResult = true;
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("경고");
					alert.setHeaderText("정보입력");
					alert.setContentText("이름과 생년월일을 모두 입력해 주세요");
					alert.showAndWait();
				}
				
				if(!(searchReserName.equals("") && searchReserBirth.equals("")) && (riVo != null)) {
					
					ArrayList<ReservationInfoVO> list;
					
					list = riDao.getReservationInfoTotal();
					
					int rowCount = list.size();
					
					if(riVo.getT_name().equals(searchReserName) && riVo.getT_birth().equals(searchReserBirth)) {
						reserinfoData.removeAll(reserinfoData);
						
						for(int index = 0; index < rowCount; index++) {
							riVo = list.get(index);
							if(riVo.getT_name().equals(searchReserName) && riVo.getT_birth().equals(searchReserBirth)) {
								reserinfoData.add(riVo);
								searchResult = true;
							}
						}
					}
				}
				if (!searchResult) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("정보 검색");
					alert.setHeaderText(searchReserName + " " + searchReserBirth + " 이 리스트에 없습니다");
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
		
		// 메인으로 돌아가는 버튼 이벤트
		btnReserToMain.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView1.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainViewStage = new Stage();
				mainViewStage.setTitle("고속버스 예약시스템");
				mainViewStage.setScene(scene);
				Stage reserInfoStage = (Stage) btnReserToMain.getScene().getWindow();
				reserInfoStage.close();
				mainViewStage.show();
			} catch (IOException e) {
				System.err.println("오류" + e);
			}
		});

	}

}
