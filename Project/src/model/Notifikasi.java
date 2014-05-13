package model;

public class Notifikasi {
	private String nama;
	private long waktu;
	private String pesan;
	private boolean isSelected;

	

	public Notifikasi() {}

	public Notifikasi(String nama, long waktu, String pesan, boolean isSelected) {
		this.nama = nama;
		this.waktu = waktu;
		this.pesan = pesan;
		this.isSelected = isSelected;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public long getWaktu() {
		return waktu;
	}

	public void setWaktu(long waktu) {
		this.waktu = waktu;
	}

	public String getPesan() {
		return pesan;
	}

	public void setPesan(String pesan) {
		this.pesan = pesan;
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

}