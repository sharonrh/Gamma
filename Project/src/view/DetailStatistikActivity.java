package view;

import controller.AchievementController;
import model.Achievement;
import model.Laporan;
import model.Pengguna;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gamma.R;

import java.util.ArrayList;

import controller.LaporanController;
import controller.ProfilController;

public class DetailStatistikActivity extends Activity {

	private TextView beratAwal, beratTarget, beratSekarang, tinggiAwal,
			tinggiSekarang, bmiSekarang, bmiAwal, kalori, progress, durasiReal,
			durasiTarget, statusBMI, achieve, artikel;

	private ProfilController con;
	private LaporanController laporanCon;
    private AchievementController achievementController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_statistik);

		con = new ProfilController(getApplicationContext());
		laporanCon = new LaporanController(getApplicationContext());
        achievementController = new AchievementController(getApplicationContext());

		beratAwal = (TextView) findViewById(R.id.beratAwalStatistik);
		beratSekarang = (TextView) findViewById(R.id.beratSekarangStatistik);
		beratTarget = (TextView) findViewById(R.id.beratTargetStatistik);
		tinggiAwal = (TextView) findViewById(R.id.tinggiAwalStatistik);
		tinggiSekarang = (TextView) findViewById(R.id.tinggiSekarangStatistik);
		progress = (TextView) findViewById(R.id.progressStatistik);
		durasiReal = (TextView) findViewById(R.id.durasiDietStatistik);
		durasiTarget = (TextView) findViewById(R.id.durasiMaksimalStatistik);
		bmiAwal = (TextView) findViewById(R.id.bmiAwalStatistik);
		bmiSekarang = (TextView) findViewById(R.id.bmiSekarangStatistik);
		kalori = (TextView) findViewById(R.id.kaloriHarianStatistik);
		statusBMI = (TextView) findViewById(R.id.statusBMIStatistik);
		achieve = (TextView) findViewById(R.id.achivementStatistik);
		artikel = (TextView) findViewById(R.id.artikelStatistik);

		Pengguna p = con.getProfil();

		beratAwal.setText(p.getBerat() + " kg");
		beratTarget.setText(p.getTarget() + " kg");
		double bmi = p.getBerat() / Math.pow(p.getTinggi() / 100, 2);
		bmiAwal.setText(String.format("%.2f", bmi));
		statusBMI.setText(getBMIStatus(bmi));
		tinggiAwal.setText(p.getTinggi() + " cm");
		kalori.setText(con.getKebutuhanKal() + " kal");

		Laporan l = laporanCon.getLaporanTerbaru();

		// handle case laporan masih kosong
		if (l != null) {
			beratSekarang.setText(l.getBeratBadan() + " kg");
			tinggiSekarang.setText(p.getTinggi() + " cm");
			bmiSekarang.setText(String.format("%.2f",
					l.getBeratBadan() / Math.pow(l.getTinggiBadan() / 100, 2)));
			String prog = String.format(
					"%.2f",
					(l.getBeratBadan() - p.getBerat())
							/ (p.getTarget() - p.getBerat()) * 100)
					+ "%";
			progress.setText(prog);
			durasiReal.setText((l.getWaktu() - p.getStartTime()) / 604800000L
					+ " minggu");		
		}
		durasiTarget.setText((p.getEndTime() - p.getStartTime())
					/ 604800000L + " minggu");
        ArrayList<Achievement> achievements = achievementController.getHandler().getAllAchievement();
        int jlhAch = 0;
        for (int i = 0; i < achievements.size(); i++) {
            if (achievements.get(i).isGet()) {
                jlhAch++;
            }
        }
        achieve.setText(jlhAch + "");
        artikel.setText(achievements.get(3).getProgress() + "");
	}

	private String getBMIStatus(double bmi) {
		if (bmi < 16) {
			return "Severely underweight";
		} else if (bmi < 18.5) {
			return "Underweight";
		} else if (bmi < 25) {
			return "Normal";
		} else if (bmi < 30) {
			return "Overweight";
		}
		return "Obese";
	}
}