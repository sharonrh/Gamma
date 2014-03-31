package model;

public class Achievement {
	private String nama;
	private String deskripsi;
	private double requirement;
	private boolean isGet;
	
	public Achievement(String nama, String deskripsi, double requirement,
			boolean isGet) {
		super();
		this.nama = nama;
		this.deskripsi = deskripsi;
		this.requirement = requirement;
		this.isGet = isGet;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public double getRequirement() {
		return requirement;
	}

	public void setRequirement(double requirement) {
		this.requirement = requirement;
	}

	public boolean isGet() {
		return isGet;
	}

	public void setGet(boolean isGet) {
		this.isGet = isGet;
	}
	
}