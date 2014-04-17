package view;

import android.app.Activity;
import android.util.Log;

import com.example.gamma.R;

public class Utils {
	
	public static String SIZE="";
	 public static boolean settingChanged=false;
	 public static String THEME="";

	public static void setThemeToActivity(Activity act )
	 {
	  
	  try { 
			 if(Utils.THEME.equalsIgnoreCase("defaultTheme"))
			 {
				 act.setTheme(R.style.gray_gradient);
			 }


			 if(Utils.THEME.equalsIgnoreCase("Gray"))
			 {
				 act.setTheme(R.style.radial_gradient);
			 }

			 if(Utils.THEME.equalsIgnoreCase("Radial"))
			 {
				act.setTheme(R.style.AppBaseTheme);
			 }
	     
	  
	  }
	  catch (Exception e) {
		e.printStackTrace();
	}
	  
	 }
}