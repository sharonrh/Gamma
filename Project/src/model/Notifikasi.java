package model;

public class Notifikasi {
	private String nama;
	private long waktu;
	private String pesan;

	public Notifikasi(String nama, long waktu, String pesan) {
		this.nama = nama;
		this.waktu = waktu;
		this.pesan = pesan;
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

}