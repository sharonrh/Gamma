package view;

import model.Pengguna;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.gamma.R;

import controller.ProfilController;

public class DetailStatistikActivity extends Activity {

	private TextView beratAwal, beratTarget, progress, durasiReal,
			durasiTarget, statusBMI, achieve, artikel;

	ProfilController con;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_statistik);

		con = new ProfilController(getApplicationContext());

		beratAwal = (TextView) findViewById(R.id.beratAwalStatistik);
		beratTarget = (TextView) findViewById(R.id.beratTargetStatistik);
		progress = (TextView) findViewById(R.id.progressStatistik);
		durasiReal = (TextView) findViewById(R.id.durasiDietStatistik);
		durasiTarget = (TextView) findViewById(R.id.durasiMaksimalStatistik);
		statusBMI = (TextView) findViewById(R.id.statusBMIStatistik);
		achieve = (TextView) findViewById(R.id.achivementStatistik);
		artikel = (TextView) findViewById(R.id.artikelStatistik);

		Pengguna p = con.getProfil();

		beratAwal.setText(p.getBerat() + " kg");
		beratTarget.setText(p.getTarget() + " kg");
		progress.setText(p.getTarget() - p.getBerat() + " kg");

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