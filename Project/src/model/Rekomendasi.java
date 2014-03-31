package model;

import java.util.List;

public class Rekomendasi {
	private int id;
	private String isi;
	private long waktu;
	private List<Makanan> makananObj;
	
	public Rekomendasi(int id, String isi, long waktu, List<Makanan> makananObj) {
		this.id = id;
		this.isi = isi;
		this.waktu = waktu;
		this.makananObj = makananObj;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsi() {
		return isi;
	}

	public void setIsi(String isi) {
		this.isi = isi;
	}

	public long getWaktu() {
		return waktu;
	}

	public void setWaktu(long waktu) {
		this.waktu = waktu;
	}

	public List<Makanan> getMakananObj() {
		return makananObj;
	}

	public void setMakananObj(List<Makanan> makananObj) {
		this.makananObj = makananObj;
	}

	
}