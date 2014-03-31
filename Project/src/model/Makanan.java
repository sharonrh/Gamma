package model;

public class Makanan {

	private String nama;
	private int kalori;
	private int protein;
	private int karbohidrat;
	private int lemak;
	private String jenisMakanan;
	
	public Makanan(String nama, int kalori, int protein, int karbohidrat,
			int lemak, String jenisMakanan) {
		super();
		this.nama = nama;
		this.kalori = kalori;
		this.protein = protein;
		this.karbohidrat = karbohidrat;
		this.lemak = lemak;
		this.jenisMakanan = jenisMakanan;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public int getKalori() {
		return kalori;
	}

	public void setKalori(int kalori) {
		this.kalori = kalori;
	}

	public int getProtein() {
		return protein;
	}

	public void setProtein(int protein) {
		this.protein = protein;
	}

	public int getKarbohidrat() {
		return karbohidrat;
	}

	public void setKarbohidrat(int karbohidrat) {
		this.karbohidrat = karbohidrat;
	}

	public int getLemak() {
		return lemak;
	}

	public void setLemak(int lemak) {
		this.lemak = lemak;
	}

	public String getJenisMakanan() {
		return jenisMakanan;
	}

	public void setJenisMakanan(String jenisMakanan) {
		this.jenisMakanan = jenisMakanan;
	}

	
	
}
