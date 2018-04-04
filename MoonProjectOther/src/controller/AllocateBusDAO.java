package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ScheduleVO;

public class AllocateBusDAO {

	// 일정 입력
	public ScheduleVO getScheduleregiste(ScheduleVO svo) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append("insert into scheduleinfo ");
		sql.append(
				" (sch_seq, sch_departure, sch_arrival, sch_starttime, sch_distance, sch_leadtime, sch_fee, sch_number, sch_transCom, sch_grade, sch_note, sch_date, o_number, b_seq) ");
		sql.append(" values (scheduleinfo_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		Connection con = null;
		PreparedStatement pstmt = null;
		ScheduleVO sVo = svo;

		try {
			con = DBConnector.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, sVo.getSch_departure());
			pstmt.setString(2, sVo.getSch_arrival());
			pstmt.setString(3, sVo.getSch_starttime());
			pstmt.setDouble(4, sVo.getSch_distance());
			pstmt.setString(5, sVo.getSch_leadtime());
			pstmt.setInt(6, sVo.getSch_fee());
			pstmt.setString(7, sVo.getSch_number());
			pstmt.setString(8, sVo.getSch_transCom());
			pstmt.setString(9, sVo.getSch_grade());
			pstmt.setString(10, sVo.getSch_note());
			pstmt.setString(11, sVo.getSch_date());
			pstmt.setInt(12, sVo.getO_number());
			pstmt.setInt(13, sVo.getB_seq());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// DB연결에 사용된 오브젝트 해제
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {

			}
		}
		return sVo;
	}

	// 리스트
	public ArrayList<ScheduleVO> getScheduleTotal() {

		ArrayList<ScheduleVO> list = new ArrayList<ScheduleVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from scheduleinfo order by sch_seq desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ScheduleVO sVo = null;

		try {
			con = DBConnector.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sVo = new ScheduleVO();
				sVo.setSch_seq(rs.getInt("sch_seq"));
				sVo.setSch_departure(rs.getString("sch_departure"));
				sVo.setSch_arrival(rs.getString("sch_arrival"));
				sVo.setSch_starttime(rs.getString("sch_starttime"));
				sVo.setSch_distance(rs.getDouble("sch_distance"));
				sVo.setSch_leadtime(rs.getString("sch_leadtime"));
				sVo.setSch_fee(rs.getInt("sch_fee"));
				
				sVo.setSch_number(rs.getString("sch_number"));
				sVo.setSch_transCom(rs.getString("sch_transCom"));
				sVo.setSch_grade(rs.getString("sch_grade"));
				sVo.setSch_note(rs.getString("sch_note"));
				
				sVo.setSch_date(rs.getString("sch_date"));

				sVo.setO_number(rs.getInt("o_number"));
				sVo.setB_seq(rs.getInt("b_seq"));

				list.add(sVo);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
				se.printStackTrace();
			}
		}
		return list;
	}

	public ArrayList<String> getColumnName() {

		ArrayList<String> columnName = new ArrayList<String>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from scheduleinfo");

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

	// 검색, 조회 메소드
	public ScheduleVO getScheduleCheck(String departure, String arrival, String time, String date) {

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select * from scheduleinfo where sch_departure like ? and sch_arrival like ? and sch_starttime like ? and to_char(sch_date, 'yyyy-mm-dd') like ? order by sch_seq desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ScheduleVO sVo = null;

		try {  
			con = DBConnector.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + departure + "%");
			pstmt.setString(2, "%" + arrival + "%");
			pstmt.setString(3, "%" + time + "%");
			pstmt.setString(4, "%" + date + "%");
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				sVo = new ScheduleVO();
				sVo.setSch_seq(rs.getInt("sch_seq"));
				sVo.setSch_departure(rs.getString("sch_departure"));
				sVo.setSch_arrival(rs.getString("sch_arrival"));
				sVo.setSch_starttime(rs.getString("sch_starttime"));
				sVo.setSch_distance(rs.getDouble("sch_distance"));
				sVo.setSch_leadtime(rs.getString("sch_leadtime"));
				sVo.setSch_fee(rs.getInt("sch_fee"));
				sVo.setSch_number(rs.getString("sch_number"));
				sVo.setSch_transCom(rs.getString("sch_transCom"));
				sVo.setSch_grade(rs.getString("sch_grade"));
				sVo.setSch_note(rs.getString("sch_note"));
				sVo.setSch_date(rs.getString("sch_date"));
				sVo.setO_number(rs.getInt("o_number"));
				sVo.setB_seq(rs.getInt("b_seq"));

			}
		} catch (SQLException se) {
			System.out.println(se);
			se.printStackTrace();
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
		return sVo;
	}

}
