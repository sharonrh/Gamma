package view;

import model.Laporan;
import model.Pengguna;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gamma.R;

import controller.LaporanController;
import controller.ProfilController;

public class DetailStatistikActivity extends Activity {

	private TextView beratAwal, beratTarget, beratSekarang, tinggiAwal,
			tinggiSekarang, bmiSekarang, bmiAwal, progress, durasiReal,
			durasiTarget, statusBMI, achieve, artikel;

	private ProfilController con;
	private LaporanController laporanCon;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_statistik);

		con = new ProfilController(getApplicationContext());
		laporanCon = new LaporanController(getApplicationContext());

		beratAwal = (TextView) findViewById(R.id.beratAwalStatistik);
		beratTarget = (TextView) findViewById(R.id.beratTargetStatistik);
		progress = (TextView) findViewById(R.id.progressStatistik);
		durasiReal = (TextView) findViewById(R.id.durasiDietStatistik);
		durasiTarget = (TextView) findViewById(R.id.durasiMaksimalStatistik);
		statusBMI = (TextView) findViewById(R.id.statusBMIStatistik);
		achieve = (TextView) findViewById(R.id.achivementStatistik);
		artikel = (TextView) findViewById(R.id.artikelStatistik);

		Pengguna p = con.getProfil();
		Laporan l = laporanCon.getLaporanTerbaru();

		beratAwal.setText(p.getBerat() + " kg");
		beratTarget.setText(p.getTarget() + " kg");
		String prog = String.format("%.2f", (l.getBeratBadan() - p.getBerat())
				/ (p.getTarget() - p.getBerat()) * 100)
				+ "%";
		progress.setText(prog);
		durasiReal.setText((l.getWaktu() - p.getStartTime()) / 604800000
				+ " minggu");
		durasiTarget.setText((p.getEndTime() - p.getStartTime()) / 604800000
				+ " minggu");
		double bmi = p.getBerat() / Math.pow(p.getTinggi() / 100, 2);
		statusBMI.setText(getBMIStatus(bmi));
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