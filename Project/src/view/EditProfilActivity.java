package view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gamma.R;

public class EditProfilActivity extends Activity {

	protected static final int RESULT_LOAD_IMAGE = 1;
	Spinner spinTransport;
	Spinner spinTransport2;
	Spinner spinTransport3;
	Spinner spinTransport4;
	Button batal;
	ImageView fotoProfil;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profil);

		spinTransport = (Spinner) findViewById(R.id.spinnerGayaHidup);
		spinTransport2 = (Spinner) findViewById(R.id.spinnerAktivitas1);
		spinTransport3 = (Spinner) findViewById(R.id.spinnerAktivitas2);
		spinTransport4 = (Spinner) findViewById(R.id.spinnerAktivitas3);

		batal = (Button) findViewById(R.id.batalProfilBtn);
		fotoProfil = (ImageView) findViewById(R.id.editFoto);
		
		fotoProfil.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		
		// Adapter for spinner
		ArrayAdapter adapter = ArrayAdapter.createFromResource(
				getApplicationContext(), R.array.isi_gayaHidup,
				android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
				getApplicationContext(), R.array.isi_aktivitas,
				android.R.layout.simple_spinner_dropdown_item);
		// Sets the layout resource to create the drop down views
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// The Adapter is used to provide the data which backs this Spinner.
		spinTransport.setAdapter(adapter);
		spinTransport2.setAdapter(adapter2);
		spinTransport3.setAdapter(adapter2);
		spinTransport4.setAdapter(adapter2);

		spinTransport.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView parent, View view,
					int position, long id) {

				// On selecting a spinner item
				String item = parent.getItemAtPosition(position).toString();

				// showing a toast on selecting an item
				Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG)
						.show();

			}

			public void onNothingSelected(AdapterView arg0) {
				// TODO Auto-generated method stub

			}
		});

		spinTransport2.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView parent, View view,
					int position, long id) {

				// On selecting a spinner item
				String item = parent.getItemAtPosition(position).toString();

				// showing a toast on selecting an item
				Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG)
						.show();

			}

			public void onNothingSelected(AdapterView arg0) {
				// TODO Auto-generated method stub

			}
		});

		spinTransport3.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView parent, View view,
					int position, long id) {

				// On selecting a spinner item
				String item = parent.getItemAtPosition(position).toString();

				// showing a toast on selecting an item
				Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG)
						.show();

			}

			public void onNothingSelected(AdapterView arg0) {
				// TODO Auto-generated method stub

			}
		});

		spinTransport4.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView parent, View view,
					int position, long id) {

				// On selecting a spinner item
				String item = parent.getItemAtPosition(position).toString();

				// showing a toast on selecting an item
				Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG)
						.show();

			}

			public void onNothingSelected(AdapterView arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		batal.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				i.putExtra("nomorFragment", 1);

				startActivity(i);
			}
		});

	}
	
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			
			fotoProfil.setImageBitmap(BitmapFactory.decodeFile(picturePath));
		
		}
    
    
    }
}
