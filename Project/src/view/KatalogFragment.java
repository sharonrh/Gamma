package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.gamma.R;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class KatalogFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		View v = inflater.inflate(R.layout.fragment_katalog, container, false);
		final ListView listview = (ListView) v.findViewById(R.id.listKatalog);
		
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
		        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
		        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
		        "Android", "iPhone", "WindowsMobile" };
		
		 MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getActivity(), values);
		 listview.setAdapter(adapter);
		
		return v;
	}
	
	
	class MySimpleArrayAdapter extends ArrayAdapter<String> {
		  private final Context context;
		  private final String[] values;

		  public MySimpleArrayAdapter(Context context, String[] values) {
		    super(context, R.layout.list_katalog, values);
		    this.context = context;
		    this.values = values;
		  }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.list_katalog, parent, false);
		    TextView textView = (TextView) rowView.findViewById(R.id.makananKatalog);
		    //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		    textView.setText(values[position]);
		    // Change the icon for Windows and iPhone
		    String s = values[position];
		    /**if (s.startsWith("Windows7") || s.startsWith("iPhone")
		        || s.startsWith("Solaris")) {
		      imageView.setImageResource(R.drawable.no);
		    } else {
		      imageView.setImageResource(R.drawable.ok);
		    }*/

		    return rowView;
		  }
		} 
	
}