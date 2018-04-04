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
	private Button btnAllocateInfo; // ����������ȸ �� ���� ��ư
	@FXML
	private Button btnReservation; // ����������ȸ ��ư
	@FXML
	private Button btnOperationSch; // �������� �Է¼������� ��ư
	@FXML
	private Button btnBus; // �������� �Է¼������� ��ư
	@FXML
	private Button btnAllocateBus; // ���� ���� ��ư
	@FXML
	private Button btnMainExit; // ����â ���� ��ư

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// ����������ȸ �� ���� ��ư �̺�Ʈ
		btnAllocateInfo.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllocateView.fxml")); // ����������ȸ �� �ε�
				Parent allocateView = (Parent) loader.load();
				Scene scene = new Scene(allocateView); // ����������ȸ ���� ���� ����
				Stage allocateStage = new Stage(); // ����������ȸ ���� �������� ����
				allocateStage.setTitle("�������� ��ȸ"); // Ÿ��Ʋ ��
				allocateStage.setScene(scene); // �� ��
				Stage mainViewStage = (Stage) btnAllocateInfo.getScene().getWindow();
				mainViewStage.close(); // ����â �ݱ�
				allocateStage.show(); // ����������ȸ â ��������
			} catch (IOException e) {
				System.err.println("����" + e);
			}
		});

		// �������� ��ȸ ��ư �̺�Ʈ
		btnReservation.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReservationInfo.fxml")); // ��ε�
				Parent reservationInfo = (Parent) loader.load();
				Scene scene = new Scene(reservationInfo); // �ε��� ���� ������
				Stage reservationStage = new Stage(); // �������� ����
				reservationStage.setTitle("�������� ��ȸ"); // Ÿ��Ʋ ���̹�
				reservationStage.setScene(scene);
				Stage mainViewStage = (Stage) btnReservation.getScene().getWindow();
				mainViewStage.close();
				reservationStage.show();
			} catch (IOException e) {
				System.err.println("����" + e);
			}
		});
		
		// �������� �Է� ���� ���� ��ư �̺�Ʈ
		btnOperationSch.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OperationSchView.fxml"));
				Parent operationSchView = (Parent) loader.load();
				Scene scene = new Scene(operationSchView);
				Stage operationSchStage = new Stage();
				operationSchStage.setTitle("�������� ����");
				operationSchStage.setScene(scene);
				Stage mainViewStage = (Stage) btnOperationSch.getScene().getWindow();
				mainViewStage.close();
				operationSchStage.show();
			} catch (IOException e) {
				System.err.println("����" + e);
			}
		});
		
		// �������� �Է� ���� ���� ��ư �̺�Ʈ
		btnBus.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BusView.fxml"));
				Parent busView = (Parent) loader.load();
				Scene scene = new Scene(busView);
				Stage busStage = new Stage();
				busStage.setTitle("�������� ����");
				busStage.setScene(scene);
				Stage mainViewStage = (Stage) btnBus.getScene().getWindow();
				mainViewStage.close();
				busStage.show();
			} catch (IOException e) {
				System.err.println("����" + e);
			}
		});
		
		// �������� ��ư �̺�Ʈ
		btnAllocateBus.setOnAction(event -> {
			try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllocateBus.fxml"));
			Parent allocateBus = (Parent) loader.load();
			Scene scene = new Scene(allocateBus);
			Stage allocateBusStage = new Stage();
			allocateBusStage.setTitle("��������");
			allocateBusStage.setScene(scene);
			Stage mainViewStage = (Stage) btnAllocateBus.getScene().getWindow();
			mainViewStage.close();
			allocateBusStage.show();
			} catch (IOException e) {
				System.err.println("����" + e);
			}
		});

		// ����â ���� ��ư �̺�Ʈ
		btnMainExit.setOnAction(event -> Platform.exit());

	}

}
