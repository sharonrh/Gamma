package view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.example.gamma.R;

public class TentangActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tentang);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.tentang, menu);
		return true;
	}

}
