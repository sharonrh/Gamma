package model;

public class Makanan {
	
    private String nama;
    private int kalori;
    private double protein;
    private double karbohidrat;
    private double lemak;
    private double natrium;
    private String porsi;
    private int bobot;
    private float rating;
    private String jenisMakanan;
    private boolean isHewani;
    private boolean isSeafood;
    private boolean isKacang;
    private long terakhirDipilih;
    private String pathFoto;

    public Makanan() {
    }

    public Makanan(String nama, int kalori, double protein, double karbohidrat, double lemak, double natrium, String porsi, int bobot, float rating,
                   String jenisMakanan, boolean isHewani, boolean isSeafood, boolean isKacang, long terakhirDipilih, String pathFoto) {
        this.nama = nama;
        this.kalori = kalori;
        this.protein = protein;
        this.karbohidrat = karbohidrat;
        this.lemak = lemak;
        this.natrium = natrium;
        this.porsi = porsi;
        this.bobot = bobot;
        this.rating = rating;
        this.jenisMakanan = jenisMakanan;
        this.isHewani = isHewani;
        this.isSeafood = isSeafood;
        this.isKacang = isKacang;
        this.terakhirDipilih = terakhirDipilih;
        this.pathFoto = pathFoto;
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

    public double getNatrium() {
        return natrium;
    }

    public void setNatrium(double natrium) {
        this.natrium = natrium;
    }

    public String getPorsi() {
        return porsi;
    }

    public void setPorsi(String porsi) {
        this.porsi = porsi;
    }

    public int getBobot() {
        return bobot;
    }

    public void setBobot(int bobot) {
        this.bobot = bobot;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getJenisMakanan() {
        return jenisMakanan;
    }

    public void setJenisMakanan(String jenisMakanan) {
        this.jenisMakanan = jenisMakanan;
    }

    public boolean isHewani() {
        return isHewani;
    }

    public void setHewani(boolean isHewani) {
        this.isHewani = isHewani;
    }

    public boolean isSeafood() {
        return isSeafood;
    }

    public void setSeafood(boolean isSeafood) {
        this.isSeafood = isSeafood;
    }

    public boolean isKacang() {
        return isKacang;
    }

    public void setKacang(boolean isKacang) {
        this.isKacang = isKacang;
    }

    public long getTerakhirDipilih() {
        return terakhirDipilih;
    }

    public void setTerakhirDipilih(long terakhirDipilih) {
        this.terakhirDipilih = terakhirDipilih;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }
}

