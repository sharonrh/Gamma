package model;

import android.util.Log;

public class Achievement {
    private String nama;
    private String deskripsi;
    private int progress;
    private int requirement;
    private boolean isGet;
    private String pathLogo;

    public Achievement(String nama, boolean isGet, String deskripsi, int progress, int requirement,
                       String pathLogo) {
        super();
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.progress = progress;
        this.requirement = requirement;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (this.progress >= this.requirement) {
            this.setGet(true);
            if (this.requirement == 10) {
                this.setPathLogo("logo/bookworm.png");
            } else if (this.requirement == 25) {
                this.setPathLogo("logo/starter.png");
            } else if (this.requirement == 50) {
                this.setPathLogo("logo/halfway.png");
            } else if (this.requirement == 100) {
                this.setPathLogo("logo/finisher.png");
            }
        }
    }

    public void addProgress() {
        this.progress++;
        if (this.requirement == 2 && this.progress == this.requirement) {
            this.isGet = true;
            this.setPathLogo("logo/bookworm.png");
            Log.i("Done", "Logo harusnya berubah");
        }
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

    public int getRequirement() {
        return requirement;
    }

    public void setRequirement(int requirement) {
        this.requirement = requirement;
    }
}