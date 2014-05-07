package view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamma.R;

public class DetailMakananActivity extends Activity{
	
	private ImageView bintang1, bintang2, bintang3, bintang4, bintang5;
	private Button rate;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.setThemeToActivity(this);
		setContentView(R.layout.activity_detail_makanan);
		Utils.setThemeToActivity(this);
		
		bintang1 = (ImageView) findViewById(R.id.bintang1Detail);
		bintang2 = (ImageView) findViewById(R.id.bintang2Detail);
		bintang3 = (ImageView) findViewById(R.id.bintang3Detail);
		bintang4 = (ImageView) findViewById(R.id.bintang4Detail);
		bintang5 = (ImageView) findViewById(R.id.bintang5Detail);
		
		rate = (Button) findViewById(R.id.rateBtn);
		
		SharedPreferences spre = this.getSharedPreferences("Your prefName",
                Context.MODE_PRIVATE);
         String mystring=  spre.getString("key","");
         
         
	      TextView makanan = (TextView) findViewById(R.id.namaDetailMakanan);
	      makanan.setText(mystring);
         
         bintang1.setOnClickListener(new View.OnClickListener() {
        	    @Override
        	    public void onClick(View v) {
        	        bintang2.setImageDrawable(getResources().getDrawable(R.drawable.alergi_seafood));
        	        bintang3.setImageDrawable(getResources().getDrawable(R.drawable.alergi_seafood));
        	        bintang4.setImageDrawable(getResources().getDrawable(R.drawable.alergi_seafood));
        	        bintang5.setImageDrawable(getResources().getDrawable(R.drawable.alergi_seafood));
        	    }
        	});
         
         bintang2.setOnClickListener(new View.OnClickListener() {
     	    @Override
     	    public void onClick(View v) {
     	        bintang2.setImageDrawable(getResources().getDrawable(R.drawable.achievement));
     	        bintang3.setImageDrawable(getResources().getDrawable(R.drawable.alergi_seafood));
     	        bintang4.setImageDrawable(getResources().getDrawable(R.drawable.alergi_seafood));
     	        bintang5.setImageDrawable(getResources().getDrawable(R.drawable.alergi_seafood));
     	    }
     	});
         
         bintang3.setOnClickListener(new View.OnClickListener() {
     	    @Override
     	    public void onClick(View v) {
     	        bintang2.setImageDrawable(getResources().getDrawable(R.drawable.achievement));
     	        bintang3.setImageDrawable(getResources().getDrawable(R.drawable.achievement));
     	        bintang4.setImageDrawable(getResources().getDrawable(R.drawable.alergi_seafood));
     	        bintang5.setImageDrawable(getResources().getDrawable(R.drawable.alergi_seafood));
     	    }
     	});
         
         bintang4.setOnClickListener(new View.OnClickListener() {
      	    @Override
      	    public void onClick(View v) {
      	        bintang2.setImageDrawable(getResources().getDrawable(R.drawable.achievement));
      	        bintang3.setImageDrawable(getResources().getDrawable(R.drawable.achievement));
      	        bintang4.setImageDrawable(getResources().getDrawable(R.drawable.achievement));
      	        bintang5.setImageDrawable(getResources().getDrawable(R.drawable.alergi_seafood));
      	    }
      	});
         
         bintang5.setOnClickListener(new View.OnClickListener() {
      	    @Override
      	    public void onClick(View v) {
      	        bintang2.setImageDrawable(getResources().getDrawable(R.drawable.achievement));
      	        bintang3.setImageDrawable(getResources().getDrawable(R.drawable.achievement));
      	        bintang4.setImageDrawable(getResources().getDrawable(R.drawable.achievement));
      	        bintang5.setImageDrawable(getResources().getDrawable(R.drawable.achievement));
      	    }
      	});
         
         rate.setOnClickListener(new View.OnClickListener() {

 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub
 				finish();
 			}
 		});
	}
}