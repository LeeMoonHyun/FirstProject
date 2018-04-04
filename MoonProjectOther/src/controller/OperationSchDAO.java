package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.OperationSchVO;

public class OperationSchDAO {

	// ���뼱��� �Է�
	public OperationSchVO getOperationSchregiste(OperationSchVO ovo) throws Exception {

		// �뼱�Է� sql��
		StringBuffer sql = new StringBuffer();
		sql.append("insert into operationschinfo ");
		sql.append("(o_number, o_departure, o_arrival, o_starttime, o_distance, o_leadtime, o_fee)");
		sql.append(" values (operationsch_seq.nextval, ?, ?, ?, ?, ?, ?)");

		Connection conn = null;
		PreparedStatement pstmt = null;
		OperationSchVO oVo = ovo;

		try {
			// DBConnector �����ͺ��̽� ����
			conn = DBConnector.getConnection();

			// �Է¹��� ������ ó��
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, oVo.getO_departure());
			pstmt.setString(2, oVo.getO_arrival());
			pstmt.setString(3, oVo.getO_starttime());
			pstmt.setDouble(4, oVo.getO_distance());
			pstmt.setString(5, oVo.getO_leadtime());
			pstmt.setInt(6, oVo.getO_fee());

			// SQL�� ó�� ��� ������
			int i = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// DB���ῡ ���� ������Ʈ ����
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {

			}
		}
		return oVo;
	}

	// ������ ��� ����
	public OperationSchVO getOperationSchUpdate(OperationSchVO ovo, int o_number) throws Exception {

		// sql�� �Է�
		StringBuffer sql = new StringBuffer();
		sql.append("update operationschinfo set ");
		sql.append(" o_departure = ?, o_arrival = ?, o_starttime = ?, o_distance = ?, o_leadtime = ?, o_fee = ? ");
		sql.append(" where o_number = ?");

		Connection con = null;
		PreparedStatement pstmt = null;
		OperationSchVO oVo = null;

		try {
			con = DBConnector.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, ovo.getO_departure());
			pstmt.setString(2, ovo.getO_arrival());
			pstmt.setString(3, ovo.getO_starttime());
			pstmt.setDouble(4, ovo.getO_distance());
			pstmt.setString(5, ovo.getO_leadtime());
			pstmt.setInt(6, ovo.getO_fee());
			pstmt.setInt(7, o_number);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��� ����");
				alert.setContentText("��� ���� �Ϸ�");

				alert.showAndWait();
				oVo = new OperationSchVO();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��� ����");
				alert.setContentText("��� ���� ����");

				alert.showAndWait();
			}

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// DB���ῡ ���� ������Ʈ ����
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}
		return oVo;
	}

	// �������� ����
	public void getOperationSchDelete(int o_number) throws Exception {

		// sql�� �Է�
		StringBuffer sql = new StringBuffer();
		sql.append("delete from operationschinfo where o_number = ?");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DB����
			con = DBConnector.getConnection();

			// SQL�� ó���� ���
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, o_number);

			// ��� ������
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("������ ����");
				alert.setContentText("������ ���� �Ϸ�");

				alert.showAndWait();

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("������ ����");
				alert.setContentText("������ ���� ����");

				alert.showAndWait();

			}

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {

			try {
				// DB���ῡ ���� ������Ʈ ����
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}

	}

	// �������� ����Ʈ
	public ArrayList<OperationSchVO> getOperationSchTotal() {

		ArrayList<OperationSchVO> list = new ArrayList<OperationSchVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * " + 
				" from operationschinfo " + 
				" order by o_number desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OperationSchVO oVo = null;

		try {
			con = DBConnector.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				oVo = new OperationSchVO();
				oVo.setO_number(rs.getInt("o_number"));
				oVo.setO_departure(rs.getString("o_departure"));
				oVo.setO_arrival(rs.getString("o_arrival"));
				oVo.setO_starttime(rs.getString("o_starttime"));
				oVo.setO_distance(rs.getDouble("o_distance"));
				oVo.setO_leadtime(rs.getString("o_leadtime"));
				oVo.setO_fee(rs.getInt("o_fee"));

				list.add(oVo);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException se) {

			}
		}
		return list;
	}

	// �������� ���̺� �÷� ����
	public ArrayList<String> getColumnName() {

		ArrayList<String> columnName = new ArrayList<String>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from operationschinfo");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ResultSetMetaData rsmd = null;

		try {
			con = DBConnector.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				columnName.add(rsmd.getColumnName(i));
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException se) {

			}
		}
		return columnName;
	}
	
	// �˻�, ��ȸ �޼ҵ�
	public OperationSchVO getOperationSchCheck(String departure, String arrival, String time) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("select * from operationschinfo where o_departure like ");
		sql.append("? and o_arrival like ? and o_starttime like ? order by o_number desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OperationSchVO oVo = null;

		try {
			con = DBConnector.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + departure + "%");
			pstmt.setString(2, "%" + arrival + "%");
			pstmt.setString(3, "%" + time + "%");

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				oVo = new OperationSchVO();
				oVo.setO_number(rs.getInt("o_number"));
				oVo.setO_departure(rs.getString("o_departure"));
				oVo.setO_arrival(rs.getString("o_arrival"));
				oVo.setO_starttime(rs.getString("o_starttime"));
				oVo.setO_distance(rs.getDouble("o_distance"));
				oVo.setO_leadtime(rs.getString("o_leadtime"));
				oVo.setO_fee(rs.getInt("o_fee"));
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}

		}
		return oVo;
	}
	
}
