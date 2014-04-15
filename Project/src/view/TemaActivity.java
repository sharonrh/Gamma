package view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RadioGroup;

//import com.example.com.exampe.changetheme.CustomThemeActivity;
import view.SettingActivity;
import view.Utils;
import com.example.gamma.R;

public class TemaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tema);
		
		RadioGroup rg = (RadioGroup) findViewById(R.id.pilihTema);
		
		switch (rg.getCheckedRadioButtonId()) {
		case R.id.holoDark:
			Utils.THEME="DEFAULT";
		    Utils.settingChanged=true;
		    startActivity(new Intent(this,  SettingActivity.class));
			break;
		case R.id.holoLight:
			Utils.THEME="Gray";
		    Utils.settingChanged=true;;
		    startActivity(new Intent( this,  SettingActivity.class));
			break;
		default :
			Utils.THEME="Radial";
		    Utils.settingChanged=true;
		    startActivity(new Intent( this,  SettingActivity.class)); 
			break;
			
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.tema, menu);
		return true;
	}

}
