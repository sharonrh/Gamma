package view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gamma.R;

public class NotifikasiActivity extends Activity {
	
	String str;
	Button tambahNotifikasiBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notifikasi);
		
		tambahNotifikasiBtn = (Button) findViewById(R.id.tmbhNtfkasiBtn);
		
		tambahNotifikasiBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				AlertDialog alertDialog = new AlertDialog.Builder(
						NotifikasiActivity.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Tambah Notifikasi");

				// Setting Dialog Message
				 LayoutInflater inflater = getLayoutInflater();

				    // Inflate and set the layout for the dialog
				    // Pass null as the parent view because its going in the dialog layout
				 View v = inflater.inflate(R.layout.atur_notifikasi_layout, null);
				 alertDialog.setView(v);

				// Setting Icon to Dialog
			//	alertDialog.setIcon(R.drawable.ic_launcher);
				 
				 final EditText tv = (EditText) v.findViewById(R.id.namaNotifikasiFields);
				 
				 
				// Setting OK Button
				 alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "batal", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							System.out.println(tv.getText().toString());
							System.out.println("fifun ganteng");
						}
					});
				alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "simpan", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						
					}
				});
				
				// Showing Alert Message
				alertDialog.show();
			
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.notifikasi, menu);
		return true;
	}

}
