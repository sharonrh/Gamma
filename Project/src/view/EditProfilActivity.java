package view;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import controller.ProfilController;

public class EditProfilActivity extends Activity {

	protected static final int RESULT_LOAD_IMAGE = 1;

	private EditText namaField, umurField, beratField, tinggiField,
			targetField;
	private RadioGroup genderPick;
	private RadioButton selected;
	private CheckBox vegetarian, kacang, seafood;
	private Spinner spinTransport, spinTransport2;
	private Button batal, simpan;
	private ImageView fotoProfil;
	private String gaya, akv1, akv2, akv3, akv4;
	private TextView penjelasan;
	private ProfilController con;
	private String str = "";

	// Declaring the String Array with the Text Data for the Spinners
	private String[] languages = { "Jarang Sekali", "Sedikit Aktif", "Aktif",
			"Sangat Aktif" };
	private String[] durasi = { "1 Minggu", "2 Minggu", "4 Minggu",
	"8 Minggu" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setThemeToActivity(this);
		setContentView(R.layout.activity_edit_profil);
		
		Intent i = getIntent();
		spinTransport = (Spinner) findViewById(R.id.spinnerGayaHidup);
		spinTransport2 = (Spinner) findViewById(R.id.spinnerLamaDiet);

		namaField = (EditText) findViewById(R.id.editNama);
		umurField = (EditText) findViewById(R.id.editUmur);
		beratField = (EditText) findViewById(R.id.editBeratSekarang);
		targetField = (EditText) findViewById(R.id.editBeratTarget);
		tinggiField = (EditText) findViewById(R.id.editTinggi);

		genderPick = (RadioGroup) findViewById(R.id.editGender);
		vegetarian = (CheckBox) findViewById(R.id.editVegetarian);
		kacang = (CheckBox) findViewById(R.id.editKacang);
		seafood = (CheckBox) findViewById(R.id.editIkan);
		fotoProfil = (ImageView) findViewById(R.id.editFoto);
//		i.putExtra("nama", nama.getText());
//		i.putExtra("umur", umur.getText());
//		i.putExtra("beratSkrg", beratSekarang.getText());
//		i.putExtra("beratTarget", beratTarget.getText());
//		i.putExtra("tinggi", tinggi.getText());
//		i.putExtra("jeKel", profil.getGender());
//		i.putExtra("foto", profil.getFoto());
//		i.putExtra("sayur", profil.isVegetarian());
//		i.putExtra("gayaHidup", profil.getGayaHidup());
//		i.putExtra("ikan", profil.isAlergiSeafood());
//		i.putExtra("kacang", profil.isAlergiKacang());
		
		if(i.getStringExtra("nama").length()!=0){
			namaField.setText(i.getStringExtra("nama"));
			umurField.setText(i.getStringExtra("umur"));
			beratField.setText(i.getStringExtra("beratSkrg"));
			targetField.setText(i.getStringExtra("beratTarget"));
			tinggiField.setText(i.getStringExtra("tinggi"));
			
			
			byte[] decodedString = Base64.decode(i.getStringExtra("foto"), Base64.DEFAULT);
			Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
					decodedString.length);

			fotoProfil.setImageBitmap(decodedByte);
			
			
			String gender = i.getStringExtra("jeKel");
			
			if(gender.equalsIgnoreCase("P")){
				genderPick.check(R.id.editPria);
			}
			else if(gender.equalsIgnoreCase("L")){
				genderPick.check(R.id.editWanita);
			}
			else {
				
			}
			
			//genderPick = (RadioGroup) findViewById(R.id.editGender);
			
			String isVege = i.getStringExtra("sayur");
			if(isVege.equalsIgnoreCase("true")){
				vegetarian.setChecked(true);
			}
			else {
			}
			
			String isKacang = i.getStringExtra("sayur");
			if(isKacang.equalsIgnoreCase("true")){
				kacang.setChecked(true);
			}
			else {
			}
			
			String isSea = i.getStringExtra("sayur");
			if(isSea.equalsIgnoreCase("true")){
				seafood.setChecked(true);
			}
			else {
			}
		}
			
		penjelasan = (TextView) findViewById(R.id.textPenjelasanGaya);

		// beratSekarang = (EditText) findViewById(R.id.editBeratSekarang);
		// beratTarget = (EditText) findViewById(R.id.editBeratTarget);

		batal = (Button) findViewById(R.id.batalProfilBtn);
		simpan = (Button) findViewById(R.id.simpanProfilBtn);

		

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
		CustomAdapter adapter = new CustomAdapter(this,
				android.R.layout.simple_spinner_item, languages);
		CustomAdapter adapter2 = new CustomAdapter(this,
				android.R.layout.simple_spinner_item, durasi);

		// The Adapter is used to provide the data which backs this Spinner.
		spinTransport.setAdapter(adapter);
		spinTransport2.setAdapter(adapter2);

		spinTransport.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView parent, View view,
					int position, long id) {

				// On selecting a spinner item
				String item = parent.getItemAtPosition(position).toString();

				// cek isi spinner dan ubah penjelasan
				if (item.equalsIgnoreCase("jarang sekali"))
					penjelasan
							.setText("Jarang Sekali : Aktivitas hidup utama seperti istirahat, kerja kantoran atau menyetir. Kemungkinan melibatkan pekerjaan rumah moderat dan berdiri tetapi tidak ada latihan ringan yang dilakukan.");
				else if (item.equalsIgnoreCase("sedikit aktif"))
					penjelasan
							.setText("Sedikit Aktif : Disamping kegiatan sehari-hari, melakukan kegiatan yang lebih berat, seperti berdiri lebih lama atau pekerjaan rumah. Beberapa bentuk latihan dilakukan, seperti jalan pelan, bersepeda santai atau berkebun.");
				else if (item.equalsIgnoreCase("aktif"))
					penjelasan
							.setText("Aktif : Sedikit duduk / istirahat dan kemungkinan bekerja dilingkungan yang membutuhkan berdiri dan/atau sedikit kerja fisik. Secara teratur melakukan olahraga ringan, seperti menari, jalan cepat atau berenang.");
				else if (item.equalsIgnoreCase("sangat aktif"))
					penjelasan
							.setText("Sangat Aktif : Lingkungan kerja fisik intensif seperti konstruksi dan / atau melakukan kegiatan yang berat banyak hari dalam seminggu, seperti jogging, menggunakan peralatan olahraga atau berpartisipasi dalam olahraga fisik.");

				// simpan item yang dipilih
				gaya = item;

			}

			public void onNothingSelected(AdapterView arg0) {
			}
		});

		batal.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				i.putExtra("nomorFragment", 3);
				startActivity(i);
			}
		});

		simpan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String nama = namaField.getText().toString();
				String umur = umurField.getText().toString();
				String berat = beratField.getText().toString();
				String target = targetField.getText().toString();
				String tinggi = tinggiField.getText().toString();
				int selectedId = genderPick.getCheckedRadioButtonId();
				int gayaHidup = spinTransport.getSelectedItemPosition();

				if (selectedId != -1) {
					selected = (RadioButton) findViewById(selectedId);
					String gender = selected.getText().toString();
					// P =pria,W=wanita

					if (nama.length() != 0 && umur.length() != 0
							&& berat.length() != 0 && tinggi.length() != 0) {
						con = new ProfilController(getApplicationContext());

						if (con.updateProfil(nama, Integer.parseInt(umur),
								Double.parseDouble(berat),
								Double.parseDouble(tinggi),
								Double.parseDouble(target), gender.charAt(0),
								gayaHidup, kacang.isChecked(),
								seafood.isChecked(), vegetarian.isChecked(),
								str, 0, 0)) {

							Toast.makeText(getApplicationContext(),
									"Profil sudah diperbaharui",
									Toast.LENGTH_LONG).show();
							finish();
							Intent i = new Intent(getApplicationContext(),
									MainActivity.class);
							i.putExtra("nomorFragment", 3);

							startActivity(i);
						} else {
							Toast.makeText(getApplicationContext(),
									"Gagal. Cek ulang isian Anda",
									Toast.LENGTH_LONG).show();
						}
					}

					else {
						Toast.makeText(getApplicationContext(),
								"Ada field yang masih kosong",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Ada field yang masih kosong", Toast.LENGTH_LONG)
							.show();
				}
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

			// ImageView profile = (ImageView) row.findViewById(R.id.p);

			// profile.setBackgroundResource(current.getResourceId());

			TextView name = (TextView) row.findViewById(R.id.spinnerText);

			name.setText(current);

			return row;

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			Bitmap b = BitmapFactory.decodeFile(picturePath);
			fotoProfil.setImageBitmap(b);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			b.compress(Bitmap.CompressFormat.JPEG, 100, baos);

			byte[] a = baos.toByteArray();
			byte[] c = Base64.encode(a, Base64.DEFAULT);

			try {

				// string gambar
				str = new String(c, "UTF-8");

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
