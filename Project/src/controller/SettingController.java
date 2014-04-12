package controller;

import com.example.gamma.NotifikasiActivity;
import com.example.gamma.SettingFragment;
import com.example.gamma.TemaActivity;
import com.example.gamma.TentangActivity;

import android.content.Context;
import android.content.Intent;

public class SettingController {
	
	Context c;
	
	public SettingController(Context c) {
		super();
		this.c = c;
	}

	public void gantiHalaman(int kode){
		if(kode == 0){
			Intent intent = new Intent(c, NotifikasiActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			c.startActivity(intent);
		}
		else if(kode == 1){

		}
		else if(kode == 2){
			Intent intent = new Intent(c, TentangActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			c.startActivity(intent);
		}
		else {
			
		}
	}
}
