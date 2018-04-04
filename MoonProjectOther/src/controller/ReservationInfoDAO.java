package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ReservationInfoVO;
import model.ScheduleVO;

public class ReservationInfoDAO {

	// 검색 메소드
	public ReservationInfoVO getReservationInfoCheck(String name, String birth) {

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select * "
				+ " from reservationinfo, scheduleinfo "
				+ " where t_name like ? "
				+ " and t_birth like ? ");
		sql.append(" and scheduleinfo.sch_seq = reservationinfo.sch_seq ");
		sql.append(" order by t_seq desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservationInfoVO riVo = null;
		ScheduleVO sVo = null;

		try {
			con = DBConnector.getConnection();

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + name + "%");
			pstmt.setString(2, "%" + birth + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				riVo = new ReservationInfoVO();
				sVo = new ScheduleVO();
				riVo.setT_seq(rs.getInt("t_seq"));
				riVo.setT_seatNumber1(rs.getString("t_seatNumber1"));
				riVo.setT_seatNumber2(rs.getString("t_seatNumber2"));
				riVo.setT_seatNumber3(rs.getString("t_seatNumber3"));
				riVo.setT_seatNumber4(rs.getString("t_seatNumber4"));
				riVo.setT_adultNum(rs.getInt("t_adultNum"));
				riVo.setT_kidNum(rs.getInt("t_kidNum"));
				riVo.setT_adolNum(rs.getInt("t_adolNum"));
				riVo.setT_totalFee(rs.getInt("t_totalFee"));
				riVo.setT_name(rs.getString("t_name"));
				riVo.setT_birth(rs.getString("t_birth"));
				riVo.setSch_seq(rs.getInt("sch_seq"));
				sVo.setSch_departure(rs.getString("sch_departure"));
				sVo.setSch_arrival(rs.getString("sch_arrival"));
				sVo.setSch_starttime(rs.getString("sch_starttime"));
				sVo.setSch_grade(rs.getString("sch_grade"));
				sVo.setSch_date(rs.getString("sch_date"));
				
			}

		} catch (SQLException se) {
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
		return riVo;

	}

	// 리스트
	public ArrayList<ReservationInfoVO> getReservationInfoTotal() {

		ArrayList<ReservationInfoVO> list = new ArrayList<ReservationInfoVO>();
		ArrayList<ScheduleVO> list1 = new ArrayList<ScheduleVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from reservationinfo, scheduleinfo where scheduleinfo.sch_seq = reservationinfo.sch_seq order by t_seq desc");

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReservationInfoVO riVo = null;
		ScheduleVO sVo = null;

		try {
			con = DBConnector.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				riVo = new ReservationInfoVO();
				sVo = new ScheduleVO();
				riVo.setT_seq(rs.getInt("t_seq"));
				riVo.setT_seatNumber1(rs.getString("t_seatNumber1"));
				riVo.setT_seatNumber2(rs.getString("t_seatNumber2"));
				riVo.setT_seatNumber3(rs.getString("t_seatNumber3"));
				riVo.setT_seatNumber4(rs.getString("t_seatNumber4"));
				riVo.setT_adultNum(rs.getInt("t_adultNum"));
				riVo.setT_kidNum(rs.getInt("t_kidNum"));
				riVo.setT_adolNum(rs.getInt("t_adolNum"));
				riVo.setT_totalFee(rs.getInt("t_totalFee"));
				riVo.setT_name(rs.getString("t_name"));
				riVo.setT_birth(rs.getString("t_birth"));
				sVo.setSch_departure(rs.getString("sch_departure"));
				sVo.setSch_arrival(rs.getString("sch_arrival"));
				sVo.setSch_starttime(rs.getString("sch_starttime"));
				sVo.setSch_grade(rs.getString("sch_grade"));
				sVo.setSch_date(rs.getString("sch_date"));
				
				list.add(riVo);
				list1.add(sVo);
			}
		} catch (SQLException se) {
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
			} catch (SQLException se) {

			}
		}
		return list;
	}

}
