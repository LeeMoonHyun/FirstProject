package model;


public class BusVO {

	private int b_seq; // 버스 시퀀스번호
	private String b_number; // 버스 번호(번호판)
	private String b_transCom; // 운송 회사 이름
	private String b_grade; // 버스 등급 - 일반, 우등으로 구분
	private String b_note; // 버스 특이사항

	// default 생성자
	public BusVO() {

	}

	// 전체 생성자
	public BusVO(int b_seq, String b_number, String b_transCom, String b_grade, String b_note) {
		super();
		this.b_seq = b_seq;
		this.b_number = b_number;
		this.b_transCom = b_transCom;
		this.b_grade = b_grade;
		this.b_note = b_note;
	}

	public BusVO(String b_number, String b_transCom, String b_grade, String b_note) {
		super();
		this.b_number = b_number;
		this.b_transCom = b_transCom;
		this.b_grade = b_grade;
		this.b_note = b_note;
	}

	public BusVO(int b_seq, String b_number, String b_transCom, String b_grade) {
		super();
		this.b_seq = b_seq;
		this.b_number = b_number;
		this.b_transCom = b_transCom;
		this.b_grade = b_grade;
	}

	public BusVO(String b_number, String b_transCom, String b_grade) {
		super();
		this.b_number = b_number;
		this.b_transCom = b_transCom;
		this.b_grade = b_grade;
	}

	public int getB_seq() {
		return b_seq;
	}

	public void setB_seq(int b_seq) {
		this.b_seq = b_seq;
	}

	public String getB_number() {
		return b_number;
	}

	public void setB_number(String b_number) {
		this.b_number = b_number;
	}

	public String getB_transCom() {
		return b_transCom;
	}

	public void setB_transCom(String b_transCom) {
		this.b_transCom = b_transCom;
	}

	public String getB_grade() {
		return b_grade;
	}

	public void setB_grade(String b_grade) {
		this.b_grade = b_grade;
	}

	public String getB_note() {
		return b_note;
	}

	public void setB_note(String b_note) {
		this.b_note = b_note;
	}

}
