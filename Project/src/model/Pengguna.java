package model;
import java.util.ArrayList;

public class Pengguna {

	private int id;
	private String nama;
	private int umur;
	private double berat;
	private double tinggi;
	private double target;
	private char gender;
	private int gayaHidup; //1 - ringan, 2 - sedang, dst
	private boolean isAlergiKacang;
	private boolean isAlergiSeafood;
	private boolean isVegetarian;
	
	public Pengguna(int id, String nama, int umur, int berat, int tinggi,
			int target, char gender, int gayaHidup, boolean isAlergiKacang,
			boolean isAlergiTelur, boolean isAlergiSeafood, boolean isVegetarian) {
		super();
		this.id = id;
		this.nama = nama;
		this.umur = umur;
		this.berat = berat;
		this.tinggi = tinggi;
		this.target = target;
		this.gender = gender;
		this.gayaHidup = gayaHidup;
		this.isAlergiKacang = isAlergiKacang;
		this.isAlergiTelur = isAlergiTelur;
		this.isAlergiSeafood = isAlergiSeafood;
		this.isVegetarian = isVegetarian;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public int getUmur() {
		return umur;
	}

	public void setUmur(int umur) {
		this.umur = umur;
	}

	public int getBerat() {
		return berat;
	}

	public void setBerat(int berat) {
		this.berat = berat;
	}

	public int getTinggi() {
		return tinggi;
	}

	public void setTinggi(int tinggi) {
		this.tinggi = tinggi;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public int getGayaHidup() {
		return gayaHidup;
	}

	public void setGayaHidup(int gayaHidup) {
		this.gayaHidup = gayaHidup;
	}

	public boolean isAlergiKacang() {
		return isAlergiKacang;
	}

	public void setAlergiKacang(boolean isAlergiKacang) {
		this.isAlergiKacang = isAlergiKacang;
	}


	public boolean isAlergiSeafood() {
		return isAlergiSeafood;
	}

	public void setAlergiSeafood(boolean isAlergiSeafood) {
		this.isAlergiSeafood = isAlergiSeafood;
	}

	public boolean isVegetarian() {
		return isVegetarian;
	}

	public void setVegetarian(boolean isVegetarian) {
		this.isVegetarian = isVegetarian;
	}

}
