package view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.gamma.R;

public class DetailMakananActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setThemeToActivity(this);
		setContentView(R.layout.activity_detail_makanan);
		Utils.setThemeToActivity(this);
		
		SharedPreferences spre = this.getSharedPreferences("Your prefName",
                Context.MODE_PRIVATE);
         String mystring=  spre.getString("key","");
         
         
	      TextView makanan = (TextView) findViewById(R.id.haha);
	      makanan.setText(mystring);
	}
}