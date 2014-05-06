package view;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import model.Laporan;
import model.Pengguna;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gamma.R;

import controller.LaporanController;
import controller.ProfilController;

public class StatistikFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_statistik, container, false);
	}

	public void onResume() {
		super.onResume();
		drawChart();
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void drawChart() {
		LaporanController con = new LaporanController(getActivity()
				.getApplicationContext());
		List<Laporan> list = con.getListLaporan();

		ProfilController profilCon = new ProfilController(getActivity()
				.getApplicationContext());

		Pengguna p = profilCon.getProfil();

		GregorianCalendar start = new GregorianCalendar();
		start.setTimeInMillis(p.getStartTime());

		// Creating an XYSeries for Income
		// TimeSeries targetSeries = new TimeSeries("Target");
		// Creating an XYSeries for Income
		TimeSeries realSeries = new TimeSeries("Realized");

		int count = list.size();
		Date[] x = new Date[count];
		Date[] y = new Date[count];

		for (int i = 0; i < count; i++) {
			Laporan l = list.get(i);
			// targetSeries.add(x[i], target[i]);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(l.getWaktu());
			x[i] = gc.getTime();
			realSeries.add(x[i], l.getBeratBadan());
			// targetSeries.add(y[i], l.getBeratBadan());

		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// Adding Income Series to the dataset
		// dataset.addSeries(targetSeries);
		// Adding Expense Series to dataset
		dataset.addSeries(realSeries);

		// Creating XYSeriesRenderer to customize incomeSeries
		XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
		incomeRenderer.setColor(Color.rgb(138, 237, 172));
		incomeRenderer.setPointStyle(PointStyle.CIRCLE);
		incomeRenderer.setFillPoints(true);
		incomeRenderer.setLineWidth(2);
		incomeRenderer.setDisplayChartValues(true);

		// Creating XYSeriesRenderer to customize expenseSeries
		XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
		expenseRenderer.setColor(Color.rgb(127, 129, 250));
		expenseRenderer.setPointStyle(PointStyle.CIRCLE);
		expenseRenderer.setFillPoints(true);
		expenseRenderer.setLineWidth(3);
		expenseRenderer.setDisplayChartValues(true);

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setChartTitle("Grafik berat badan");
		multiRenderer.setXTitle("Waktu");
		multiRenderer.setYTitle("Berat badan");
		multiRenderer.setXRoundedLabels(false);

		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer
		// should be same

		// multiRenderer.addSeriesRenderer(incomeRenderer);
		multiRenderer.addSeriesRenderer(expenseRenderer);
		multiRenderer.setApplyBackgroundColor(true);
		multiRenderer.setBackgroundColor(Color.BLACK);
		// multiRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		LinearLayout layout = (LinearLayout) getActivity().findViewById(
				R.id.chart_layout);
		// Creating an intent to plot line chart using dataset and
		// multipleRenderer

		GraphicalView mChart = (GraphicalView) ChartFactory.getTimeChartView(
				getActivity(), dataset, multiRenderer, "dd/MM");
		layout.addView(mChart);

	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profil, menu);
    }
 
 @Override

	public void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    setHasOptionsMenu(true);
	}
 @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    // action with ID pensil was selected
    case R.id.pensil:
    	getActivity().finish();
		Intent i = new Intent(getActivity().getApplicationContext(),
				DetailStatistikActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		//i.putExtra("nama", nama.getText());
		//i.putExtra("umur", umur.getText());
		//i.putExtra("beratSkrg", beratSekarang.getText());
		////i.putExtra("beratTarget", beratTarget.getText());
		//i.putExtra("tinggi", tinggi.getText());
		//i.putExtra("jeKel", profil.getGender());
		//i.putExtra("foto", profil.getFoto());
		//i.putExtra("sayur", profil.isVegetarian());
		//i.putExtra("gayaHidup", profil.getGayaHidup());
		//i.putExtra("ikan", profil.isAlergiSeafood());
		//i.putExtra("kacang", profil.isAlergiKacang());

		getActivity().startActivity(i);
      break;
    default:
      break;
    }

    return true;
  }
}