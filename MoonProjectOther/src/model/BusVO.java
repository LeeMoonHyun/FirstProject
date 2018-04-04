package model;


public class BusVO {

	private int b_seq; // ���� ��������ȣ
	private String b_number; // ���� ��ȣ(��ȣ��)
	private String b_transCom; // ��� ȸ�� �̸�
	private String b_grade; // ���� ��� - �Ϲ�, ������� ����
	private String b_note; // ���� Ư�̻���

	// default ������
	public BusVO() {

	}

	// ��ü ������
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
