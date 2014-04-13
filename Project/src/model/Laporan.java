package model;

public class Laporan {

	private double tinggiBadan;
	private double beratBadan;
	private long waktu;

	public Laporan() {
	}

	public Laporan(long waktu, double beratBadan, double tinggiBadan) {
		this.tinggiBadan = tinggiBadan;
		this.beratBadan = beratBadan;
		this.waktu = waktu;
	}

	public double getTinggiBadan() {
		return tinggiBadan;
	}

	public void setTinggiBadan(double tinggiBadan) {
		this.tinggiBadan = tinggiBadan;
	}

	public double getBeratBadan() {
		return beratBadan;
	}

	public void setBeratBadan(double beratBadan) {
		this.beratBadan = beratBadan;
	}

	public long getWaktu() {
		return waktu;
	}

	public void setWaktu(long waktu) {
		this.waktu = waktu;
	}

}
