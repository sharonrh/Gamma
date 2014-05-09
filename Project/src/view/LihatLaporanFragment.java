package view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import model.Laporan;

import android.annotation.SuppressLint;
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

import controller.LaporanController;

public class LihatLaporanFragment extends Fragment {
	
	LaporanController kon;
	Context konteks;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		konteks = getActivity().getApplicationContext();
		
		kon = new LaporanController(konteks);
		
		View v = inflater.inflate(R.layout.fragment_lihatlaporan, container,
				false);
		ListView listview = (ListView) v.findViewById(R.id.listLaporan);
		TextView tv = (TextView) v.findViewById(R.id.jumlahLaporan);
		List<Laporan> data = kon.getListLaporan();
		System.out.println(tv);
		tv.setText("" + data.size());
		
		MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(getActivity(), data);
		listview.setAdapter(adapter);

		//ubah tinggi listview katalog
//		 LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (adapter.getCount()*60));
//	     listview.setLayoutParams(mParam);
		
	     listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			    //you might want to use 'view' here
				  Intent intent = new Intent(getActivity().getApplicationContext(), IsiLaporanActivity.class);
				  
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
	
	class MyPerformanceArrayAdapter extends ArrayAdapter<Laporan> {
		  private final Activity context;
		  private List<Laporan> list;

		  class ViewHolder {
		    public TextView tanggal;
		    public TextView berat;
		    public TextView tinggi;
		  }

		  public MyPerformanceArrayAdapter(Activity context, List<Laporan> list) {
		    super(context, R.layout.list_laporan, list);
		    this.context = context;
		    this.list = list;
		  }

		  @SuppressLint("SimpleDateFormat")
		@Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    View rowView = convertView;
		    ViewHolder viewHolder;
		    // reuse views
		    if (rowView == null) {
		      LayoutInflater inflater = context.getLayoutInflater();
		      rowView = inflater.inflate(R.layout.list_laporan, null);
		      // configure view holder
		      viewHolder = new ViewHolder();
		      viewHolder.tanggal = (TextView) rowView.findViewById(R.id.tanggalListLaporan);
		      viewHolder.berat = (TextView) rowView.findViewById(R.id.beratListLaporan);
		      viewHolder.tinggi = (TextView) rowView.findViewById(R.id.tinggiListLaporan);
		      rowView.setTag(viewHolder);
		    }
		    else {

		    // fill data
			    viewHolder = (ViewHolder) rowView.getTag();
		    }
		    
		    Laporan l = list.get(list.size()- (position+1));
		    
		    String iberat = ""+l.getBeratBadan();
		    String itinggi = ""+l.getTinggiBadan();
		    long waktu = l.getWaktu();
		    Date date = new Date(waktu);
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");	
			String itanggal = format.format(date);
			
			System.out.println(iberat);
			System.out.println(itinggi);
			System.out.println(itanggal);
			
			viewHolder.tanggal.setText(itanggal);
			viewHolder.berat.setText(iberat + " kg");
			viewHolder.tinggi.setText(itinggi + " cm");
		    
		    return rowView;
		  }
		}
}