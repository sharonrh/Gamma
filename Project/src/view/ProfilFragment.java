package view;

import model.Pengguna;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamma.R;

import controller.ProfilController;

public class ProfilFragment extends Fragment {

	private TextView nama, umur, gender, gayaHidup, vegetarian, kacang,
			seafood;
	private ImageView foto;
	private ProfilController con;
	private static Bitmap result;
	private static Canvas canvas;
	private static int color, roundPx;
	private static Paint paint;
	private static Rect rect;
	private static RectF rectF;

	Pengguna profil;

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

		} catch (OutOfMemoryError o) {
			System.out.println("Memori habis untuk gambar");
		}
		return result;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_lihat_profil, container,
				false);

		con = new ProfilController(getActivity().getApplicationContext());
		profil = con.getProfil();

		nama = (TextView) v.findViewById(R.id.namaProfilTv);
		umur = (TextView) v.findViewById(R.id.tahunProfilTv);
		foto = (ImageView) v.findViewById(R.id.avatarProfilTv);
		gender = (TextView) v.findViewById(R.id.kelaminProfilTv);
		gayaHidup = (TextView) v.findViewById(R.id.gayaProfilTv);
		vegetarian = (TextView) v.findViewById(R.id.vegeProfilTv);
		kacang = (TextView) v.findViewById(R.id.kacangProfilTv);
		seafood = (TextView) v.findViewById(R.id.seafoodProfilTv);

		nama.setText(profil.getNama());
		umur.setText(profil.getUmur() + " tahun");

		gayaHidup.setText(con.gayaHidup[profil.getGayaHidup()]);
		vegetarian.setText(profil.isVegetarian() ? "Ya" : "Tidak");
		kacang.setText(profil.isAlergiKacang() ? "Ya" : "Tidak");
		seafood.setText(profil.isAlergiSeafood() ? "Ya" : "Tidak");

		String g = profil.getGender() == 'P' ? "Pria" : "Wanita";
		gender.setText(g);
		System.out.println(profil.getFoto());
		byte[] decodedString = Base64.decode(profil.getFoto(), Base64.DEFAULT);
		System.out.println(decodedString);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);

		if(!profil.getFoto().equalsIgnoreCase(""))
			foto.setBackgroundColor(getResources().getColor(R.color.abu));
		foto.setImageBitmap(getRoundedRectBitmap(decodedByte, 100));
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.profil, menu);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.pensil:
			Intent i = new Intent(getActivity().getApplicationContext(),
					EditProfilActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			break;
		default:
			break;
		}

		return true;
	}
}