package view;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import model.Laporan;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gamma.R;

import controller.LaporanController;

public class StatistikFragment extends Fragment {

	LaporanController con = new LaporanController(getActivity());

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
		int count = 5;
		Date[] x = new Date[count];
		for (int i = 0; i < count; i++) {
			GregorianCalendar gc = new GregorianCalendar(2012, 8, (1 + i) * 7);
			x[i] = gc.getTime();
		}
		int[] target = { 40, 41, 42, 43, 44 };
		ArrayList<Laporan> list = con.getListLaporan();
		// Creating an XYSeries for Income
		TimeSeries targetSeries = new TimeSeries("Target");
		// Creating an XYSeries for Income
		TimeSeries realSeries = new TimeSeries("Realized");
		// Adding data to Income and Expense Series
		for (int i = 0; i < x.length; i++) {
			Laporan l = list.get(i);
			targetSeries.add(x[i], target[i]);
			realSeries.add(x[i], l.getBeratBadan()); // masih null gara2 database kosong
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// Adding Income Series to the dataset
		dataset.addSeries(targetSeries);
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
		multiRenderer.setTextTypeface(Typeface.SANS_SERIF);
		multiRenderer.setChartTitle("Grafik berat badan");
		multiRenderer.setXTitle("Waktu");
		multiRenderer.setYTitle("Berat badan");

		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer
		// should be same
		multiRenderer.addSeriesRenderer(incomeRenderer);
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
}