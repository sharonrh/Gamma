package model;

public class Laporan {

	private int tinggiBadan;
	private int beratBadan;
	private long waktu;

	public Laporan(int tinggiBadan, int beratBadan, long waktu) {
		super();
		this.tinggiBadan = tinggiBadan;
		this.beratBadan = beratBadan;
		this.waktu = waktu;
	}

	public int getTinggiBadan() {
		return tinggiBadan;
	}

	public void setTinggiBadan(int tinggiBadan) {
		this.tinggiBadan = tinggiBadan;
	}

	public int getBeratBadan() {
		return beratBadan;
	}

	public void setBeratBadan(int beratBadan) {
		this.beratBadan = beratBadan;
	}

	public long getWaktu() {
		return waktu;
	}

	public void setWaktu(long waktu) {
		this.waktu = waktu;
	}

}
