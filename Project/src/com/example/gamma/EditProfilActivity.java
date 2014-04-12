package com.example.gamma;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class EditProfilActivity extends Activity {

	Spinner spinTransport;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_edit_profil);
	 
	  spinTransport = (Spinner) findViewById(R.id.spinnerGayaHidup);
	   
	  // Adapter for spinner
	        ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),  R.array.isi_gayaHidup, android.R.layout.simple_spinner_dropdown_item);
	        // Sets the layout resource to create the drop down views
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  
	        //The Adapter is used to provide the data which backs this Spinner.
	        spinTransport.setAdapter(adapter);
	        spinTransport.setOnItemSelectedListener(new OnItemSelectedListener(){
	         public void onItemSelected(AdapterView parent, View view, int position,
	           long id) {
	 
	          // On selecting a spinner item
	          String item = parent.getItemAtPosition(position).toString();
	 
	          // showing a toast on selecting an item
	          Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
	 
	         }
	 
	   public void onNothingSelected(AdapterView arg0) {
	    // TODO Auto-generated method stub
	     
	   }
	        });
	 }
	 
	 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profil);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
<<<<<<< HEAD
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
=======
		 switch (item.getItemId())
	        {
	        case R.id.action_settings:
	        	Intent i = new Intent(this, SettingFragment.class);
	        	startActivity(i);
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	}
>>>>>>> e18207d845dfc1d93c5a8ddc840ba5424a8ded82

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_edit_profil,
					container, false);
			return rootView;
		}
	}

}
