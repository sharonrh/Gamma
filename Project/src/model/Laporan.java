package model;

public class Laporan {
	private int id;
	private long waktu;
	private String input;
	
	public Laporan(int id, long waktu, String input) {
		super();
		this.id = id;
		this.waktu = waktu;
		this.input = input;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public long getWaktu() {
		return waktu;
	}
	
	public void setWaktu(long waktu) {
		this.waktu = waktu;
	}
	
	public String getInput() {
		return input;
	}
	
	public void setInput(String input) {
		this.input = input;
	}
	
}
