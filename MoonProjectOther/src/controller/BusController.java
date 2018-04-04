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
	private TextField txtInputBusNum; // 버스번호 입력 tf
	@FXML
	private TextField txtInputTransCom; // 운송회사 입력 tf
	@FXML
	private ComboBox<String> cbInputBusGrade; // 버스 등급 선택 cb
	@FXML
	private TextField txtBusNote; // 버스 특이사항 입력
	@FXML
	private Button btnInputBus; // 정보 입력 버튼
	@FXML
	private Button btnEditBus; // 정보 수정 버튼
	@FXML
	private Button btnDeleteBus; // 정보 삭제 버튼
	@FXML
	private TableView<BusVO> tvBus = new TableView<>(); // 버스 tv
	@FXML
	private Button btnBusToMain; // 버스정보관리 창에서 메인으로

	ObservableList<BusVO> data = FXCollections.observableArrayList();
	ObservableList<BusVO> selectData = null;
	BusVO bus = new BusVO();

	int selectedBusIndex;

	int b_seq;

	// 버스 테이블뷰 컬럼 설정
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		TableColumn colBusSeq = new TableColumn<>("No.");
		colBusSeq.setPrefWidth(40);
		colBusSeq.setStyle("-fx-allignment : CENTER");
		colBusSeq.setCellValueFactory(new PropertyValueFactory<>("b_seq"));

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

		tvBus.getColumns().addAll(colBusSeq, colBusNum, colTransCom, colGrade, colBusNote);
		busTotalList();
		tvBus.setItems(data);

		// 버스등급 콤보박스 계수 설정
		cbInputBusGrade.setItems(FXCollections.observableArrayList("일반", "우등"));

		// 버스정보 입력 등록 이벤트
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
				alert.setTitle("버스 정보 입력");
				alert.setHeaderText("누락");
				alert.setContentText("특이사항제외 반드시 입력해 주세요");
				alert.showAndWait();
			}

			busTotalList();
			busInit();

		});

		// 수정버튼 이벤트
		btnEditBus.setOnAction(event -> {
			try {
				// 뷰 불러오기
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/BusEdit.fxml"));

				// 모달창 띄우기
				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnInputBus.getScene().getWindow());
				dialog.setTitle("버스정보 수정");

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

				// 수정정보 입력버튼
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

				// 수정창 닫기 버튼 이벤트
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

		// 삭제버튼 이벤트
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

		// 메인으로 가는 버튼
		btnBusToMain.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView1.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainViewStage = new Stage();
				mainViewStage.setTitle("고속버스 예약시스템");
				mainViewStage.setScene(scene);
				Stage operationStage = (Stage) btnBusToMain.getScene().getWindow();
				operationStage.close();
				mainViewStage.show();
			} catch (IOException e) {
				System.err.println("오류" + e);
			}
		});

	}

	// 초기화 메소드
	public void busInit() {
		txtInputBusNum.clear();
		txtInputTransCom.clear();
		cbInputBusGrade.getSelectionModel().clearSelection();
		txtBusNote.clear();
	}

	// 전체정보
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
