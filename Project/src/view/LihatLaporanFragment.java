package view;

import view.KatalogFragment.MyPerformanceArrayAdapter;
import view.KatalogFragment.MyPerformanceArrayAdapter.ViewHolder;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gamma.R;

public class LihatLaporanFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_lihatlaporan, container,
				false);
		final ListView listview = (ListView) v.findViewById(R.id.listLaporan);
		
		final String[] values = new String[] { "7 April 2014 (awal diet)", "14 April 2014", "20 April 2014",
		        "26 April 2014", "32 April 2014" };
		
		MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(getActivity(), values);
		listview.setAdapter(adapter);

		//ubah tinggi listview katalog
		 LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (adapter.getCount()*60));
	     listview.setLayoutParams(mParam);
		
	     listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			    //you might want to use 'view' here
				  Intent intent = new Intent(getActivity().getApplicationContext(), EditLaporanActivity.class);
				  
				  //passing nama makanan dari katalog ke detail makanan
				  SharedPreferences spre = getActivity().getApplicationContext().getSharedPreferences("Your prefName",
			                Context.MODE_PRIVATE);
			        SharedPreferences.Editor prefEditor = spre.edit();
			        prefEditor.putString("key", parent.getItemAtPosition(position).toString());
			        prefEditor.commit();
				  
			      startActivity(intent);
			  }
			});
	     
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

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.pensil:
			Intent i = new Intent(getActivity().getApplicationContext(),
					IsiLaporanActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getActivity().startActivity(i);
			break;
		default:
			break;
		}
		return true;
	}
	
	class MyPerformanceArrayAdapter extends ArrayAdapter<String> {
		  private final Activity context;
		  private final String[] names;

		  class ViewHolder {
		    public TextView text;
		    public ImageView image;
		  }

		  public MyPerformanceArrayAdapter(Activity context, String[] names) {
		    super(context, R.layout.list_laporan, names);
		    this.context = context;
		    this.names = names;
		  }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    View rowView = convertView;
		    // reuse views
		    if (rowView == null) {
		      LayoutInflater inflater = context.getLayoutInflater();
		      rowView = inflater.inflate(R.layout.list_laporan, null);
		      // configure view holder
		      ViewHolder viewHolder = new ViewHolder();
		      viewHolder.text = (TextView) rowView.findViewById(R.id.tanggalListLaporan);
		      //viewHolder.image = (ImageView) rowView
		          //.findViewById(R.id.ImageView01);
		      rowView.setTag(viewHolder);
		    }

		    // fill data
		    ViewHolder holder = (ViewHolder) rowView.getTag();
		    String s = names[position];
		    holder.text.setText(s);
		    /**if (s.startsWith("Windows7") || s.startsWith("iPhone")
		        || s.startsWith("Solaris")) {
		      holder.image.setImageResource(R.drawable.no);
		    } else {
		      holder.image.setImageResource(R.drawable.ok);
		    }*/

		    return rowView;
		  }
		}
}