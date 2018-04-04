package model;


public class ScheduleVO {

	private int sch_seq; // �ð�ǥ ������

	private String sch_departure; // �����
	private String sch_arrival; // ������
	private String sch_starttime; // ��߽ð�
	private double sch_distance; // �Ÿ�
	private String sch_leadtime; // �ҿ�ð�
	private int sch_fee; // ���

	private String sch_number; // ���� ��ȣ(��ȣ��)
	private String sch_transCom; // ��� ȸ�� �̸�
	private String sch_grade; // ���� ��� - �Ϲ�, ������� ����
	private String sch_note; // ���� Ư�̻���

	private String sch_date; // ��¥

	private int o_number; // fk
	private int b_seq; // fk

	// default ������
	public ScheduleVO() {

	}

	public ScheduleVO(int sch_seq, String sch_departure, String sch_arrival, String sch_starttime, double sch_distance,
			String sch_leadtime, int sch_fee, String sch_number, String sch_transCom, String sch_grade, String sch_note,
			String sch_date, int o_number, int b_seq) {
		super();
		this.sch_seq = sch_seq;
		this.sch_departure = sch_departure;
		this.sch_arrival = sch_arrival;
		this.sch_starttime = sch_starttime;
		this.sch_distance = sch_distance;
		this.sch_leadtime = sch_leadtime;
		this.sch_fee = sch_fee;
		this.sch_number = sch_number;
		this.sch_transCom = sch_transCom;
		this.sch_grade = sch_grade;
		this.sch_note = sch_note;
		this.sch_date = sch_date;
		this.o_number = o_number;
		this.b_seq = b_seq;
	}

	public ScheduleVO(String sch_departure, String sch_arrival, String sch_starttime, double sch_distance,
			String sch_leadtime, int sch_fee, String sch_number, String sch_transCom, String sch_grade, String sch_note,
			String sch_date, int o_number, int b_seq) {
		super();
		this.sch_departure = sch_departure;
		this.sch_arrival = sch_arrival;
		this.sch_starttime = sch_starttime;
		this.sch_distance = sch_distance;
		this.sch_leadtime = sch_leadtime;
		this.sch_fee = sch_fee;
		this.sch_number = sch_number;
		this.sch_transCom = sch_transCom;
		this.sch_grade = sch_grade;
		this.sch_note = sch_note;
		this.sch_date = sch_date;
		this.o_number = o_number;
		this.b_seq = b_seq;
	}

	public int getSch_seq() {
		return sch_seq;
	}

	public void setSch_seq(int sch_seq) {
		this.sch_seq = sch_seq;
	}

	public String getSch_departure() {
		return sch_departure;
	}

	public void setSch_departure(String sch_departure) {
		this.sch_departure = sch_departure;
	}

	public String getSch_arrival() {
		return sch_arrival;
	}

	public void setSch_arrival(String sch_arrival) {
		this.sch_arrival = sch_arrival;
	}

	public String getSch_starttime() {
		return sch_starttime;
	}

	public void setSch_starttime(String sch_starttime) {
		this.sch_starttime = sch_starttime;
	}

	public double getSch_distance() {
		return sch_distance;
	}

	public void setSch_distance(double sch_distance) {
		this.sch_distance = sch_distance;
	}

	public String getSch_leadtime() {
		return sch_leadtime;
	}

	public void setSch_leadtime(String sch_leadtime) {
		this.sch_leadtime = sch_leadtime;
	}

	public int getSch_fee() {
		return sch_fee;
	}

	public void setSch_fee(int sch_fee) {
		this.sch_fee = sch_fee;
	}

	public String getSch_number() {
		return sch_number;
	}

	public void setSch_number(String sch_number) {
		this.sch_number = sch_number;
	}

	public String getSch_transCom() {
		return sch_transCom;
	}

	public void setSch_transCom(String sch_transCom) {
		this.sch_transCom = sch_transCom;
	}

	public String getSch_grade() {
		return sch_grade;
	}

	public void setSch_grade(String sch_grade) {
		this.sch_grade = sch_grade;
	}

	public String getSch_note() {
		return sch_note;
	}

	public void setSch_note(String sch_note) {
		this.sch_note = sch_note;
	}

	public String getSch_date() {
		return sch_date;
	}

	public void setSch_date(String sch_date) {
		this.sch_date = sch_date;
	}

	public int getO_number() {
		return o_number;
	}

	public void setO_number(int o_number) {
		this.o_number = o_number;
	}

	public int getB_seq() {
		return b_seq;
	}

	public void setB_seq(int b_seq) {
		this.b_seq = b_seq;
	}

}
