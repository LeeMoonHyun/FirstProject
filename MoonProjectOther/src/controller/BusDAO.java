package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.BusVO;

public class BusDAO {

	// �ű� ���� ���� ���
	public BusVO getBusRegiste(BusVO bvo) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("insert into businfo ");
		sql.append("(b_seq, b_number, b_transCom, b_grade, b_note)");
		sql.append(" values (businfo_seq.nextval, ?, ?, ?, ?)");

		Connection con = null;
		PreparedStatement pstmt = null;
		BusVO bVo = bvo;

		try {
			con = DBConnector.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, bVo.getB_number());
			pstmt.setString(2, bVo.getB_transCom());
			pstmt.setString(3, bVo.getB_grade());
			pstmt.setString(4, bVo.getB_note());

			int i = pstmt.executeUpdate();

		} catch (SQLException se) {
			System.out.println("e=[" + se + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �����ͺ��̽��� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}
		return bvo;
	}

	// �������� �Է�
	public BusVO getBusUpdate(BusVO bvo, int b_seq) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("update businfo set ");
		sql.append(" b_number = ?, b_transCom = ?, b_grade = ?, b_note = ? ");
		sql.append(" where b_seq = ? ");

		Connection con = null;
		PreparedStatement pstmt = null;
		BusVO bVo = null;

		try {
			con = DBConnector.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, bvo.getB_number());
			pstmt.setString(2, bvo.getB_transCom());
			pstmt.setString(3, bvo.getB_grade());
			pstmt.setString(4, bvo.getB_note());
			pstmt.setInt(5, b_seq);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("����");
				alert.setContentText("����");

				alert.showAndWait();

				bVo = new BusVO();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("����");
				alert.setContentText("����");

				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// ������ ���̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}
		return bVo;

	}

	// ����
	public void getBusDelete(int b_seq) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("delete from businfo where b_seq = ?");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBConnector.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, b_seq);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("����");
				alert.setContentText("����");

				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("����");
				alert.setContentText("����");

				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {

			try {
				// ������ ���̽��� ���ῡ ���Ǿ��� ������Ʈ�� ����
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

	// ��ü����Ʈ
	public ArrayList<BusVO> getBusTotal() {
		ArrayList<BusVO> list = new ArrayList<BusVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from businfo order by b_seq desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BusVO bVo = null;

		try {
			con = DBConnector.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bVo = new BusVO();
				bVo.setB_seq(rs.getInt("b_seq"));
				bVo.setB_number(rs.getString("b_number"));
				bVo.setB_transCom(rs.getString("b_transCom"));
				bVo.setB_grade(rs.getString("b_grade"));
				bVo.setB_note(rs.getString("b_note"));

				list.add(bVo);
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

	// ��ü �÷�
	public ArrayList<String> getColumnName() {

		ArrayList<String> columnName = new ArrayList<String>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from businfo");

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
	public BusVO getBusCheck(String transCom, String busGrade) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from businfo where b_transCom like ? and "
				+ " b_grade like ? order by b_seq desc");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BusVO bVo = null;
		
		try {
			con = DBConnector.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + transCom + "%");
			pstmt.setString(2, "%" + busGrade + "%");
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				bVo = new BusVO();
				bVo.setB_seq(rs.getInt("b_seq"));
				bVo.setB_number(rs.getString("b_number"));
				bVo.setB_transCom(rs.getString("b_transcom"));
				bVo.setB_grade(rs.getString("b_grade"));
				bVo.setB_note(rs.getString("b_note"));
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
		
		return bVo;
		
	}

}
