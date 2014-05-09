package view;

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
import android.text.format.Time;
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
		List<Laporan> listLaporan = con.getListLaporan();

		ProfilController profilCon = new ProfilController(getActivity()
				.getApplicationContext());

		Pengguna p = profilCon.getProfil();

		double target = p.getTarget();
		double awal = p.getBerat();

		TimeSeries targetSeries = new TimeSeries("Target");
		TimeSeries realSeries = new TimeSeries("Realized");

		int count = listLaporan.size();
		Date[] x = new Date[count + 1]; // +1 data awal

		GregorianCalendar start = new GregorianCalendar();
		start.setTimeInMillis(p.getStartTime());

		GregorianCalendar end = new GregorianCalendar();
		end.setTimeInMillis(p.getEndTime());
		// untuk grafik target cuma pake data awal + akhir
		targetSeries.add(start.getTime(), awal);
		targetSeries.add(end.getTime(), target);

		// masukin data awal
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(p.getStartTime());
		x[0] = gc.getTime();

		// masukin tiap laporan untuk grafik realized
		for (int i = 0; i < count; i++) {
			Laporan l = listLaporan.get(i);
			gc.setTimeInMillis(l.getWaktu());
			x[i] = gc.getTime();
			realSeries.add(x[i], l.getBeratBadan());
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(realSeries);
		dataset.addSeries(targetSeries);

		XYSeriesRenderer realizedRenderer = new XYSeriesRenderer();
		realizedRenderer.setColor(Color.rgb(127, 129, 250));
		realizedRenderer.setPointStyle(PointStyle.CIRCLE);
		realizedRenderer.setFillPoints(true);
		realizedRenderer.setLineWidth(3);
		realizedRenderer.setDisplayChartValues(true);
		realizedRenderer.setDisplayChartValuesDistance(1);

		XYSeriesRenderer targetRenderer = new XYSeriesRenderer();
		targetRenderer.setColor(Color.rgb(138, 237, 172));
		targetRenderer.setFillPoints(true);
		targetRenderer.setLineWidth(3);
		targetRenderer.setDisplayChartValues(true);
		realizedRenderer.setDisplayChartValuesDistance(1);

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setChartTitle("Grafik berat badan");
		multiRenderer.setXTitle("Waktu");
		multiRenderer.setYTitle("Berat badan");
		multiRenderer.setXRoundedLabels(false);
		multiRenderer.setZoomButtonsVisible(true);
		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer should be same
		multiRenderer.addSeriesRenderer(realizedRenderer);
		multiRenderer.addSeriesRenderer(targetRenderer);
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
		inflater.inflate(R.menu.detail, menu);
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
		case R.id.detail:
			Intent i = new Intent(getActivity().getApplicationContext(),
					DetailStatistikActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			getActivity().startActivity(i);
			break;
		default:
			break;
		}

		return true;
	}
}