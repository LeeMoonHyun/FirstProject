package model;


public class OperationSchVO {

	private int o_number; // ������ ��ȣ
	private String o_departure; // �����
	private String o_arrival; // ������
	private String o_starttime; // ��߽ð�
	private double o_distance; // �Ÿ�
	private String o_leadtime; // �ҿ�ð�
	private int o_fee; // ���

	// default ������
	public OperationSchVO() {

	}

	public OperationSchVO(int o_number, String o_departure, String o_arrival, String o_starttime, double o_distance,
			String o_leadtime, int o_fee) {
		super();
		this.o_number = o_number;
		this.o_departure = o_departure;
		this.o_arrival = o_arrival;
		this.o_starttime = o_starttime;
		this.o_distance = o_distance;
		this.o_leadtime = o_leadtime;
		this.o_fee = o_fee;
	}

	public OperationSchVO(String o_departure, String o_arrival, String o_starttime, double o_distance,
			String o_leadtime, int o_fee) {
		super();
		this.o_departure = o_departure;
		this.o_arrival = o_arrival;
		this.o_starttime = o_starttime;
		this.o_distance = o_distance;
		this.o_leadtime = o_leadtime;
		this.o_fee = o_fee;
	}

	public int getO_number() {
		return o_number;
	}

	public void setO_number(int o_number) {
		this.o_number = o_number;
	}

	public String getO_departure() {
		return o_departure;
	}

	public void setO_departure(String o_departure) {
		this.o_departure = o_departure;
	}

	public String getO_arrival() {
		return o_arrival;
	}

	public void setO_arrival(String o_arrival) {
		this.o_arrival = o_arrival;
	}

	public String getO_starttime() {
		return o_starttime;
	}

	public void setO_starttime(String o_starttime) {
		this.o_starttime = o_starttime;
	}

	public double getO_distance() {
		return o_distance;
	}

	public void setO_distance(double o_distance) {
		this.o_distance = o_distance;
	}

	public String getO_leadtime() {
		return o_leadtime;
	}

	public void setO_leadtime(String o_leadtime) {
		this.o_leadtime = o_leadtime;
	}

	public int getO_fee() {
		return o_fee;
	}

	public void setO_fee(int o_fee) {
		this.o_fee = o_fee;
	}

}
