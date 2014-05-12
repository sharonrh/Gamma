package controller;

import view.NotifikasiActivity;
import android.content.Context;
import android.content.Intent;

public class SettingController {

	Context c;

	public SettingController(Context c) {
		super();
		this.c = c;
	}

	public void gantiHalaman(int kode) {
		if (kode == 0) {
			Intent intent = new Intent(c, NotifikasiActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			c.startActivity(intent);
		}
	}
}
