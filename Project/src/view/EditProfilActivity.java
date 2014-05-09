package view;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import model.Pengguna;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
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
	private RadioButton selected;
	private CheckBox vegetarian, kacang, seafood;
	private Spinner gayaHidupSpinner, spinTransport2, genderSpinner;
	private Button batal, simpan;
	private ImageView fotoProfil;
	private String gaya, akv1, akv2, akv3, akv4;
	private TextView penjelasan;
	private ProfilController con;
	private String str = "";
	private String pesan = "Terjadi Kesalahan pada: ";
	private static Bitmap result;
	private static Canvas canvas;
	private static int color, roundPx;
	private static Paint paint;
	private static Rect rect;
	private static RectF rectF;

	// Declaring the String Array with the Text Data for the Spinners
	private String[] languages = { "Pilih Jenis Gaya Hidup", "Jarang Sekali",
			"Sedikit Aktif", "Aktif", "Sangat Aktif" };
	private String[] durasi = { "1 Minggu", "2 Minggu", "4 Minggu", "8 Minggu" };
	private String[] kelamin = { "Pilih Jenis Kelamin", "Pria", "Wanita" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setThemeToActivity(this);
		setContentView(R.layout.activity_edit_profil);
		Utils.setThemeToActivity(this);

		gayaHidupSpinner = (Spinner) findViewById(R.id.spinnerGayaHidup);
		// spinTransport2 = (Spinner) findViewById(R.id.spinnerLamaDiet);
		genderSpinner = (Spinner) findViewById(R.id.spinnerKelamin);

		namaField = (EditText) findViewById(R.id.editNama);
		umurField = (EditText) findViewById(R.id.editUmur);
		beratField = (EditText) findViewById(R.id.editBeratSekarang);
		targetField = (EditText) findViewById(R.id.editBeratTarget);
		tinggiField = (EditText) findViewById(R.id.editTinggi);

		vegetarian = (CheckBox) findViewById(R.id.editVegetarian);
		kacang = (CheckBox) findViewById(R.id.editKacang);
		seafood = (CheckBox) findViewById(R.id.editIkan);
		fotoProfil = (ImageView) findViewById(R.id.editFoto);

		con = new ProfilController(getApplicationContext());
		Pengguna user = con.getProfil();

		// case user udah pernah isi nama
		if (user.getNama().length() != 0) {
			namaField.setText(user.getNama());
			umurField.setText("" + user.getUmur());
			beratField.setText("" + user.getBerat());
			targetField.setText("" + user.getTarget());
			tinggiField.setText("" + user.getTinggi());
			gayaHidupSpinner.setSelection(user.getGayaHidup());

			int gender = user.getGender() == 'W' ? 1 : 0;
			genderSpinner.setSelection(gender);

			if (user.getFoto() != null) {
				if (user.getFoto().length() != 0) {
					byte[] decodedString = Base64.decode(user.getFoto(),
							Base64.DEFAULT);
					Bitmap decodedByte = BitmapFactory.decodeByteArray(
							decodedString, 0, decodedString.length);
					// fotoProfil.setImageBitmap(decodedByte);
					Bitmap resized = Bitmap.createScaledBitmap(decodedByte,
							100, 100, true);
					Bitmap conv_bm = getRoundedRectBitmap(resized, 100);
					fotoProfil.setImageBitmap(conv_bm);
				}
			}

			if (user.isVegetarian()) {
				vegetarian.setChecked(true);
			}
			if (user.isAlergiKacang()) {
				kacang.setChecked(true);
			}
			if (user.isAlergiSeafood()) {
				seafood.setChecked(true);
			}
		}

		penjelasan = (TextView) findViewById(R.id.textPenjelasanGaya);
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
		// CustomAdapter adapter2 = new CustomAdapter(this,
		// android.R.layout.simple_spinner_item, durasi);
		CustomAdapter adapter3 = new CustomAdapter(this,
				android.R.layout.simple_spinner_item, kelamin);

		gayaHidupSpinner.setAdapter(adapter);
		// spinTransport2.setAdapter(adapter2);
		genderSpinner.setAdapter(adapter3);

		gayaHidupSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView parent, View view,
							int position, long id) {

						// On selecting a spinner item
						String item = parent.getItemAtPosition(position)
								.toString();

						// cek isi spinner dan ubah penjelasan
						if (item.equalsIgnoreCase("Pilih Jenis Gaya Hidup"))
							penjelasan
									.setText("Pilih salah satu jenis gaya hidup untuk melihat detailnya disini");
						else if (item.equalsIgnoreCase("jarang sekali"))
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
				int genderSelected = genderSpinner.getSelectedItemPosition();
				int gayaHidup = gayaHidupSpinner.getSelectedItemPosition();

				if (genderSelected != -1 && nama.length() != 0
						&& umur.length() != 0 && berat.length() != 0
						&& tinggi.length() != 0) {

					// P =pria,W=wanita

					if (validasiInput(nama, umur, genderSelected, berat,
							target, tinggi)) {

						char gch = genderSpinner.getSelectedItem().toString().charAt(0);
						if (con.updateProfil(nama, Integer.parseInt(umur),
								Double.parseDouble(berat),
								Double.parseDouble(tinggi),
								Double.parseDouble(target), gch,
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
						Toast.makeText(getApplicationContext(), pesan + ".",
								Toast.LENGTH_LONG).show();

						pesan = "Terjadi Kesalahan pada: ";
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
			// fotoProfil.setImageBitmap(b);
			Bitmap resized = Bitmap.createScaledBitmap(b, 100, 100, true);
			Bitmap conv_bm = getRoundedRectBitmap(resized, 100);
			fotoProfil.setImageBitmap(conv_bm);

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

	public boolean validasiInput(String nama, String umur, int gender,
			String berat, String tinggi, String target) {

		String pesan1 = "";
		ArrayList<String> list = new ArrayList<String>();
		if (!nama
				.matches("^[[A-Za-z]+('[A-Za-z]+)*([. ][A-Za-z]*)*('){0,1}]{3,70}$"))
			list.add("nama");
		if (!umur.matches("^[0-9]{1,3}"))
			list.add("umur");
		if (!berat.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$"))
			list.add("berat");
		if (!tinggi.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$"))
			list.add("tinggi");
		if (!target.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$"))
			list.add("target");
		if ((gender == -1))
			list.add("kamu belum memilih jenis kelamin");

		for (int ii = 0; ii < list.size(); ii++) {
			pesan = pesan + list.get(ii);
			if (ii < list.size() - 2) {
				pesan = pesan + ", ";
			}

			if (ii == list.size() - 2) {
				pesan = pesan + " dan ";
			}

		}

		return nama
				.matches("^[[A-Za-z]+('[A-Za-z]+)*([. ][A-Za-z]*)*('){0,1}]{3,70}$")
				&& umur.matches("^[0-9]{1,3}")
				&& gender != -1
				&& berat.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$")
				&& tinggi.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$")
				&& target.matches("^[0-9]{1,3}(\\.[0-9][0-9]?)?$");
	}

	public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
		try {
			result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
					Bitmap.Config.ARGB_8888);
			canvas = new Canvas(result);

			color = 0xff424242;
			paint = new Paint();
			rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			rectF = new RectF(rect);
			roundPx = pixels;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		} catch (NullPointerException e) {
			// return bitmap;
		} catch (OutOfMemoryError o) {
		}
		return result;
	}

}
