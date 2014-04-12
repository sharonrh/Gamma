package view;

import java.util.ArrayList;
import java.util.List;

import model.Makanan;
import model.Rekomendasi;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gamma.R;

import controller.RekomendasiController;

public class RekomendasiFragment extends ListFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		RekomendasiController con = new RekomendasiController();
		// List<Rekomendasi> values = con.getList();

		// ------------------ dummy data --------------------

		List<Makanan> listMakanan = new ArrayList<Makanan>();
		Makanan m = new Makanan();
		m.setNama("Roti selai");
		m.setBerat(100);
		m.setKalori(200);
		listMakanan.add(m);
		m = new Makanan();
		m.setNama("Susu Sapi");
		m.setBerat(200);
		m.setKalori(150);
		listMakanan.add(m);

		m = new Makanan();
		m.setNama("Pisang");
		m.setBerat(20);
		m.setKalori(25);
		listMakanan.add(m);

		m = new Makanan();
		m.setNama("Nasi Putih");
		m.setBerat(200);
		m.setKalori(250);
		listMakanan.add(m);

		m = new Makanan();
		m.setNama("Sayur Lodeh");
		m.setBerat(50);
		m.setKalori(50);
		listMakanan.add(m);

		m = new Makanan();
		m.setNama("Tempe Goreng");
		m.setBerat(100);
		m.setKalori(100);
		listMakanan.add(m);

		m = new Makanan();
		m.setNama("Nasi Putih");
		m.setBerat(200);
		m.setKalori(250);
		listMakanan.add(m);
		// ------------------ dummy end ----------------------

		MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(
				getActivity(), listMakanan, con.getCount(), con.getHeader());
		setListAdapter(adapter);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// do something with the data
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
		super(context, R.layout.sectioned_list_item, names);
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
			rowView = inflater.inflate(R.layout.sectioned_list_item, null);
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
		holder.text.setText(m.getNama() + " (" + m.getBerat() + " mg)");
		holder.subtitle.setText("" + m.getKalori() + " kkal");

		if (count[position]) {
			holder.section.setText(headers[position]);
			holder.section.setVisibility(View.VISIBLE);
		} else {
			holder.section.setVisibility(View.GONE);
		}

		return rowView;
	}
}