package view;

import com.example.gamma.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DetailStatistikActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setThemeToActivity(this);
		setContentView(R.layout.activity_detail_statistik);
		Utils.setThemeToActivity(this);
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.profil, menu);
        return true;
    }
 

 @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    // action with ID pensil was selected
    case R.id.pensil:
		Intent i = new Intent(this.getApplicationContext(),
				MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		//i.putExtra("nama", nama.getText());
		//i.putExtra("umur", umur.getText());
		//i.putExtra("beratSkrg", beratSekarang.getText());
		////i.putExtra("beratTarget", beratTarget.getText());
		//i.putExtra("tinggi", tinggi.getText());
		//i.putExtra("jeKel", profil.getGender());
		//i.putExtra("foto", profil.getFoto());
		//i.putExtra("sayur", profil.isVegetarian());
		//i.putExtra("gayaHidup", profil.getGayaHidup());
		//i.putExtra("ikan", profil.isAlergiSeafood());
		//i.putExtra("kacang", profil.isAlergiKacang());

		this.startActivity(i);
      break;
    default:
      break;
    }

    return true;
  }
}