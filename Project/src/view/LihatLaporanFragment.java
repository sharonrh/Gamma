package view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import model.Laporan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

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
		
		if(kon.selesai()){
			showPopupSelesai();
		}
		
		View v = inflater.inflate(R.layout.fragment_lihatlaporan, container,
				false);
		ListView listview = (ListView) v.findViewById(R.id.listLaporan);
		TextView tv = (TextView) v.findViewById(R.id.jumlahLaporan);
		TextView tv2 = (TextView) v.findViewById(R.id.durasiDiet);
		List<Laporan> data = kon.getListLaporan();
		System.out.println(tv);
		tv.setText("" + data.size());
		tv2.setText("" + kon.getDurasiHari());
		
		MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(getActivity(), data);
		listview.setAdapter(adapter);
		
	     listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			    //you might want to use 'view' here
				  
				  if(position == 0){
					  
					  Laporan lastLaporan = kon.getLaporanTerbaru();
					  Intent intent = new Intent(getActivity().getApplicationContext(), IsiLaporanActivity.class);
					  intent.putExtra("berat", lastLaporan.getBeratBadan());
					  intent.putExtra("tinggi", lastLaporan.getTinggiBadan());
					  startActivity(intent);
				  }
				  else {
					  Toast.makeText(getActivity().getApplicationContext(), "Kamu hanya bisa mengubah laporan terakhir.", Toast.LENGTH_SHORT).show();
					  
				  }	  
			      
			  }
			});
	     
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.laporan, menu);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.tambah:
			
			if(kon.cekDurasiIsiLaporan()){
				Intent i = new Intent(getActivity().getApplicationContext(),
						IsiLaporanActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getActivity().startActivity(i);
			}
			else {
				Toast.makeText(getActivity().getApplicationContext(),"Kamu baru bisa mengisi "+ kon.getSisaHari() + " hari lagi.", Toast.LENGTH_SHORT).show();
			}
			
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
	
	public void showPopupSelesai(){


		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		alertDialog.setTitle("Selesai!");
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		alertDialog.setView(inflater.inflate(R.layout.activity_selesai, null));

		// Setting Icon to Dialog
		// alertDialog.setIcon(R.drawable.ic_launcher);

		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		alertDialog.show();

	
	}
	
}