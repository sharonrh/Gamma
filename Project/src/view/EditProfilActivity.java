package view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamma.R;

public class EditProfilActivity extends Activity {

	protected static final int RESULT_LOAD_IMAGE = 1;
	EditText nama, umur, beratSekarang, beratTarget, tinggi;
	TextView penjelasan;
	RadioGroup gender;
	RadioButton pria, wanita;
	CheckBox telur, vegetarian, kacang, seafood;
	Spinner spinTransport, spinTransport2, spinTransport3, 
		    spinTransport4, spinTransport5;
	Button batal;
	ImageView fotoProfil;
	String gaya, akv1, akv2, akv3, akv4;
	
	// Declaring the String Array with the Text Data for the Spinners
	String[] Languages = { "Jarang Sekali", "Sedikit Aktif", "Aktif",
	"Sangat Aktif"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profil);

		spinTransport = (Spinner) findViewById(R.id.spinnerGayaHidup);
		//spinTransport2 = (Spinner) findViewById(R.id.spinnerAktivitas1);
		//spinTransport3 = (Spinner) findViewById(R.id.spinnerAktivitas2);
		//spinTransport4 = (Spinner) findViewById(R.id.spinnerAktivitas3);
		//spinTransport5 = (Spinner) findViewById(R.id.spinnerAktivitas4);
		
		//penjelasan = (TextView) findViewById(R.id.textPenjelasanGaya);
		
		nama = (EditText) findViewById(R.id.editNama);
		umur = (EditText) findViewById(R.id.editUmur);
		//beratSekarang = (EditText) findViewById(R.id.editBeratSekarang);
		//beratTarget = (EditText) findViewById(R.id.editBeratTarget);
		tinggi = (EditText) findViewById(R.id.editTinggi);
		
		gender = (RadioGroup) findViewById(R.id.editGender);
		
		pria = (RadioButton) findViewById(R.id.editPria);
		wanita = (RadioButton) findViewById(R.id.editWanita);
		
		//telur = (CheckBox) findViewById(R.id.editTelur);
		vegetarian = (CheckBox) findViewById(R.id.editVegetarian);
		kacang = (CheckBox) findViewById(R.id.editKacang);
		seafood = (CheckBox) findViewById(R.id.editIkan);

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
		//ArrayAdapter adapter = ArrayAdapter.createFromResource(
				//getApplicationContext(), R.array.isi_gayaHidup,
				//android.R.layout.simple_spinner_dropdown_item);
		
	    CustomAdapter adapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, Languages); 
		
		ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
				getApplicationContext(), R.array.isi_aktivitas,
				android.R.layout.simple_spinner_dropdown_item);
		// Sets the layout resource to create the drop down views
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// The Adapter is used to provide the data which backs this Spinner.
		spinTransport.setAdapter(adapter);
		spinTransport2.setAdapter(adapter2);
		spinTransport3.setAdapter(adapter2);
		spinTransport4.setAdapter(adapter2);
		spinTransport5.setAdapter(adapter2);

		spinTransport.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView parent, View view,
					int position, long id) {

				// On selecting a spinner item
				String item = parent.getItemAtPosition(position).toString();

				// showing a toast on selecting an item
				Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG)
						.show();
				
				// cek isi spinner dan ubah penjelasan
				if(item.equalsIgnoreCase("jarang sekali"))
					penjelasan.setText("Jarang Sekali : Aktivitas hidup utama seperti istirahat, kerja kantoran atau menyetir. Kemungkinan melibatkan pekerjaan rumah moderat dan berdiri tetapi tidak ada latihan ringan yang dilakukan.");
				else if(item.equalsIgnoreCase("sedikit aktif"))
					penjelasan.setText("Sedikit Aktif : Disamping kegiatan sehari-hari, melakukan kegiatan yang lebih berat, seperti berdiri lebih lama atau pekerjaan rumah. Beberapa bentuk latihan dilakukan, seperti jalan pelan, bersepeda santai atau berkebun.");
				else if(item.equalsIgnoreCase("aktif"))
					penjelasan.setText("Aktif : Sedikit duduk / istirahat dan kemungkinan bekerja dilingkungan yang membutuhkan berdiri dan/atau sedikit kerja fisik. Secara teratur melakukan olahraga ringan, seperti menari, jalan cepat atau berenang.");
				else if(item.equalsIgnoreCase("sangat aktif"))
					penjelasan.setText("Sangat Aktif : Lingkungan kerja fisik intensif seperti konstruksi dan / atau melakukan kegiatan yang berat banyak hari dalam seminggu, seperti jogging, menggunakan peralatan olahraga atau berpartisipasi dalam olahraga fisik.");
				
				// simpan item yang dipilih
				gaya = item;

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
				
				//simpan item yang dipilih
				akv1 = item;

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
				
				//simpan item yang dipilih
				akv2 = item;

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
				
				//simpan item yang dipilih
				akv3 = item;

			}

			public void onNothingSelected(AdapterView arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		spinTransport5.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView parent, View view,
					int position, long id) {

				// On selecting a spinner item
				String item = parent.getItemAtPosition(position).toString();

				// showing a toast on selecting an item
				Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG)
						.show();
				
				//simpan item yang dipilih
				akv4 = item;

			}

			public void onNothingSelected(AdapterView arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		batal.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}
	
	
	class CustomAdapter extends ArrayAdapter {


		Activity context;

		String[] deer;


		public CustomAdapter(Activity context, int resource, String[] deer) {


		super(context, resource, deer);

		this.context = context;

		this.deer = deer;


		}


		@Override

		public View getDropDownView(int position, View convertView,

		ViewGroup parent) {


		View row = convertView;


		if (row == null) {


		LayoutInflater inflater = context.getLayoutInflater();

		row = inflater.inflate(R.layout.spinner_row, parent, false);


		}
		
		
		
		String current = deer[position];

		//ImageView profile = (ImageView) row.findViewById(R.id.p);

		//profile.setBackgroundResource(current.getResourceId());

		TextView name = (TextView) row.findViewById(R.id.spinnerText);

		name.setText(current);


		return row;


		}


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
