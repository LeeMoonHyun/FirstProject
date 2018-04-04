package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ReservationInfoVO;
import model.ScheduleVO;

public class AllocateDAO {
	
	// 출발지 리스트 찍기
	public ArrayList<ScheduleVO> getDepartureList() {
		ArrayList<ScheduleVO> departureList = new ArrayList<ScheduleVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct sch_departure from scheduleinfo ");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ScheduleVO sVo = null;
		
		try {
			con = DBConnector.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sVo = new ScheduleVO();
				sVo.setSch_departure(rs.getString(1));
				
				departureList.add(sVo);
			}
		} catch (SQLException e) {
			System.err.println("SQL문 오류" + e);
		} catch (Exception e) {
			System.err.println("오류" + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				
			}
		}
		return departureList;
	}
	
	// 도착지 리스트 찍기
	public ArrayList<ScheduleVO> getArrivalList() {
		ArrayList<ScheduleVO> arrivalList = new ArrayList<ScheduleVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct sch_arrival from scheduleinfo");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ScheduleVO sVo = null;
		
		try {
			con = DBConnector.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sVo = new ScheduleVO();
				sVo.setSch_arrival(rs.getString(1));
				
				arrivalList.add(sVo);
			}
		} catch (SQLException e) {
			System.err.println("SQL문 오류" + e);
		} catch (Exception e) {
			System.err.println("오류" + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				
			}
		}
		return arrivalList;
	}
	
	// 티켓입력
		public ReservationInfoVO getReservationInforegiste(ReservationInfoVO rvo) throws Exception {
			
			StringBuffer sql = new StringBuffer();
			sql.append("insert into reservationinfo ");
			sql.append(" (t_seq, t_seatNumber1, t_seatnumber2, t_seatnumber3, t_seatnumber4, t_adultNum, t_kidNum, t_adolNum, t_totalFee, t_name, t_birth, sch_seq) ");
			sql.append(" values (reservationinfo_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ReservationInfoVO rVo = rvo;
			
			try {
				con = DBConnector.getConnection();
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, rVo.getT_seatNumber1());
				pstmt.setString(2, rVo.getT_seatNumber2());
				pstmt.setString(3, rVo.getT_seatNumber3());
				pstmt.setString(4, rVo.getT_seatNumber4());
				pstmt.setInt(5, rVo.getT_adultNum());
				pstmt.setInt(6, rVo.getT_kidNum());
				pstmt.setInt(7, rVo.getT_adolNum());
				pstmt.setInt(8, rVo.getT_totalFee());
				pstmt.setString(9, rVo.getT_name());
				pstmt.setString(10, rVo.getT_birth());
				pstmt.setInt(11, rVo.getSch_seq());
				
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
			
			return rvo;
		}

}
