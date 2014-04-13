package view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RadioGroup;

import com.example.gamma.R;

public class TemaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tema);
		
		RadioGroup rg = (RadioGroup) findViewById(R.id.pilihTema);
		
		switch (rg.getCheckedRadioButtonId()) {
		case R.id.holoDark:
			getApplication().setTheme(R.style.HoloBlackTheme);
			break;
		case R.id.holoLight:
			getApplication().setTheme(R.style.HoloLightTheme);
			break;
		default :
			getApplication().setTheme(R.style.AppBaseTheme);
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
