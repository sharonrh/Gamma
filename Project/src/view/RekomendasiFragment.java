package view;

import java.util.List;

import model.Makanan;
import model.Pengguna;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gamma.R;

import controller.ProfilController;
import controller.RekomendasiController;

public class RekomendasiFragment extends ListFragment {

	private List<Makanan> listMakanan;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		RekomendasiController con = new RekomendasiController(getActivity()
				.getApplicationContext());
		ProfilController conProfil = new ProfilController(getActivity()
				.getApplicationContext());
		Pengguna u = conProfil.getProfil();
		
		listMakanan = con.getRekomendasi(conProfil.getKebutuhanKal(),
				u.isVegetarian(), u.isAlergiSeafood(), u.isAlergiKacang());

		MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(
				getActivity(), listMakanan, con.getCount(), con.getHeader());
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity().getApplicationContext(),
				DetailMakananActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("nama", listMakanan.get(position).getNama());
		startActivity(intent);
	}
}

class MyPerformanceArrayAdapter extends ArrayAdapter<Makanan> {
	private final Activity context;
	private final List<Makanan> rekList;
	private final boolean[] count;
	private final String[] headers;

	static class ViewHolder {
		public TextView section;
		public TextView text;
		public TextView subtitle;
	}

	public MyPerformanceArrayAdapter(Activity context, List<Makanan> names,
			boolean[] ct, String[] headers) {
		super(context, R.layout.list_rekomendasi, names);
		this.context = context;
		this.rekList = names;
		this.count = ct;
		this.headers = headers;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// reuse views
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.list_rekomendasi, null);
			// configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.section = (TextView) rowView
					.findViewById(R.id.separator);
			viewHolder.text = (TextView) rowView.findViewById(R.id.title);
			viewHolder.subtitle = (TextView) rowView
					.findViewById(R.id.subtitle);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		Makanan m = rekList.get(position);
		holder.text.setText("1 " + m.getPorsi() + " "
				+ m.getNama().toLowerCase() + " (" + m.getBobot() + " gram)");
		holder.subtitle.setText("" + m.getKalori() + " kal");

		if (count[position]) {
			holder.section.setText(headers[position]);
			holder.section.setVisibility(View.VISIBLE);
		} else {
			holder.section.setVisibility(View.GONE);
		}

		return rowView;
	}
}