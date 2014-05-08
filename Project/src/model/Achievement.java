package model;

public class Achievement {
	private String nama;
    private boolean terkunci;
	private String deskripsi;
	private double progress;
	private boolean isGet;
    private String pathLogo;
	
	public Achievement(String nama, boolean isGet, String deskripsi, double progress,
			String pathLogo) {
		super();
		this.nama = nama;
		this.deskripsi = deskripsi;
		this.progress = progress;
		this.isGet = isGet;
        this.pathLogo = pathLogo;
	}

    public Achievement() {
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

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public boolean isGet() {
		return isGet;
	}

	public void setGet(boolean isGet) {
		this.isGet = isGet;
	}

    public String getPathLogo() {
        return pathLogo;
    }

    public void setPathLogo(String pathLogo) {
        this.pathLogo = pathLogo;
    }
}