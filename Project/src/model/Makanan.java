package model;

public class Makanan {

	private String nama;
	private double kalori;
	private double protein;
	private double karbohidrat;
	private double lemak;
	private double kalsium;
	private int persentase;
	private int rating;
	private String jenisMakanan;
	private int berat;
	
	public Makanan(){}
	
	public Makanan(String nama, double kalori, double protein, double karbohidrat,
			double lemak, double kalsium, int persentase, int rating, String jenisMakanan, int berat) {
		super();
		this.nama = nama;
		this.kalori = kalori;
		this.protein = protein;
		this.karbohidrat = karbohidrat;
		this.lemak = lemak;
		this.kalsium = kalsium;
		this.persentase = persentase;
		this.rating = rating;
		this.jenisMakanan = jenisMakanan;
		this.berat = berat;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public double getKalori() {
		return kalori;
	}

	public void setKalori(double kalori) {
		this.kalori = kalori;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getKarbohidrat() {
		return karbohidrat;
	}

	public void setKarbohidrat(double karbohidrat) {
		this.karbohidrat = karbohidrat;
	}

	public double getLemak() {
		return lemak;
	}

	public void setLemak(double lemak) {
		this.lemak = lemak;
	}

	public double getKalsium() {
		return kalsium;
	}

	public void setKalsium(double kalsium) {
		this.kalsium = kalsium;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getPersentase() {
		return persentase;
	}

	public void setPersentase(int persentase) {
		this.persentase = persentase;
	}

	public String getJenisMakanan() {
		return jenisMakanan;
	}

	public void setJenisMakanan(String jenisMakanan) {
		this.jenisMakanan = jenisMakanan;
	}

	public int getBerat() {
		return berat;
	}

	public void setBerat(int berat) {
		this.berat = berat;
	}

	
	
}
