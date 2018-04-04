package model;


public class ReservationInfoVO {

	private int t_seq; // 티켓 시퀀스 번호
	private String t_seatNumber1; // 좌석 번호1
	private String t_seatNumber2;
	private String t_seatNumber3;
	private String t_seatNumber4;
	private int t_adultNum; // 성인 인원수
	private int t_kidNum; // 아동 인원수
	private int t_adolNum; // 중고생 인원수
	private int t_totalFee; // 총요금
	private String t_name; // 예매자 이름
	private String t_birth; // 예매자 생년월일

	private int sch_seq; // 시간표 시퀀스 번호

	public ReservationInfoVO() {

	}

	public ReservationInfoVO(int t_seq, String t_seatNumber1, String t_seatNumber2, String t_seatNumber3,
			String t_seatNumber4, int t_adultNum, int t_kidNum, int t_adolNum, int t_totalFee, String t_name,
			String t_birth, int sch_seq) {
		super();
		this.t_seq = t_seq;
		this.t_seatNumber1 = t_seatNumber1;
		this.t_seatNumber2 = t_seatNumber2;
		this.t_seatNumber3 = t_seatNumber3;
		this.t_seatNumber4 = t_seatNumber4;
		this.t_adultNum = t_adultNum;
		this.t_kidNum = t_kidNum;
		this.t_adolNum = t_adolNum;
		this.t_totalFee = t_totalFee;
		this.t_name = t_name;
		this.t_birth = t_birth;
		this.sch_seq = sch_seq;
	}

	public ReservationInfoVO(String t_seatNumber1, String t_seatNumber2, String t_seatNumber3, String t_seatNumber4,
			int t_adultNum, int t_kidNum, int t_adolNum, int t_totalFee, String t_name, String t_birth, int sch_seq) {
		super();
		this.t_seatNumber1 = t_seatNumber1;
		this.t_seatNumber2 = t_seatNumber2;
		this.t_seatNumber3 = t_seatNumber3;
		this.t_seatNumber4 = t_seatNumber4;
		this.t_adultNum = t_adultNum;
		this.t_kidNum = t_kidNum;
		this.t_adolNum = t_adolNum;
		this.t_totalFee = t_totalFee;
		this.t_name = t_name;
		this.t_birth = t_birth;
		this.sch_seq = sch_seq;
	}

	public int getT_seq() {
		return t_seq;
	}

	public void setT_seq(int t_seq) {
		this.t_seq = t_seq;
	}

	public int getT_adultNum() {
		return t_adultNum;
	}

	public void setT_adultNum(int t_adultNum) {
		this.t_adultNum = t_adultNum;
	}

	public int getT_kidNum() {
		return t_kidNum;
	}

	public void setT_kidNum(int t_kidNum) {
		this.t_kidNum = t_kidNum;
	}

	public int getT_adolNum() {
		return t_adolNum;
	}

	public void setT_adolNum(int t_adolNum) {
		this.t_adolNum = t_adolNum;
	}

	public int getT_totalFee() {
		return t_totalFee;
	}

	public void setT_totalFee(int t_totalFee) {
		this.t_totalFee = t_totalFee;
	}

	public int getSch_seq() {
		return sch_seq;
	}

	public void setSch_seq(int sch_seq) {
		this.sch_seq = sch_seq;
	}

	public String getT_seatNumber1() {
		return t_seatNumber1;
	}

	public void setT_seatNumber1(String t_seatNumber1) {
		this.t_seatNumber1 = t_seatNumber1;
	}

	public String getT_seatNumber2() {
		return t_seatNumber2;
	}

	public void setT_seatNumber2(String t_seatNumber2) {
		this.t_seatNumber2 = t_seatNumber2;
	}

	public String getT_seatNumber3() {
		return t_seatNumber3;
	}

	public void setT_seatNumber3(String t_seatNumber3) {
		this.t_seatNumber3 = t_seatNumber3;
	}

	public String getT_seatNumber4() {
		return t_seatNumber4;
	}

	public void setT_seatNumber4(String t_seatNumber4) {
		this.t_seatNumber4 = t_seatNumber4;
	}

	public String getT_name() {
		return t_name;
	}

	public void setT_name(String t_name) {
		this.t_name = t_name;
	}

	public String getT_birth() {
		return t_birth;
	}

	public void setT_birth(String t_birth) {
		this.t_birth = t_birth;
	}

}
