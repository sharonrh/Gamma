package view;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.gamma.R;

public class MainActivity extends Activity {

	private static final String TAB_KEY_INDEX = "tab_key";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ActionBar
		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// create new tabs and and set up the titles of the tabs
		ActionBar.Tab rekTab = actionbar.newTab().setText(
				getString(R.string.ui_tabname_rekomendasi));
		ActionBar.Tab laporanTab = actionbar.newTab().setText(
				getString(R.string.ui_tabname_laporan));
		ActionBar.Tab profilTab = actionbar.newTab().setText(
				getString(R.string.ui_tabname_profil));
		ActionBar.Tab statTab = actionbar.newTab().setText(
				getString(R.string.ui_tabname_statistik));

		Fragment rekFragmen = new RekomendasiFragment();
		Fragment laporanFragment = new LaporanFragment();
		Fragment profilFragment = new ProfilFragment();
		Fragment statFragment = new StatistikFragment();

		rekTab.setTabListener(new MyTabsListener(rekFragmen));
		laporanTab.setTabListener(new MyTabsListener(laporanFragment));
		profilTab.setTabListener(new MyTabsListener(profilFragment));
		statTab.setTabListener(new MyTabsListener(statFragment));

		actionbar.addTab(rekTab);
		actionbar.addTab(laporanTab);
		actionbar.addTab(statTab);
		actionbar.addTab(profilTab);

		// add the tabs to the action bar
		Intent i = getIntent();
		int x = i.getIntExtra("nomorFragment", 0);
		actionbar.setSelectedNavigationItem(x);

		// restore to navigation
		if (savedInstanceState != null) {
			actionbar.setSelectedNavigationItem(savedInstanceState.getInt(
					TAB_KEY_INDEX, 0));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i = new Intent(this, SettingActivity.class);
			startActivity(i);

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// onSaveInstanceState() is used to "remember" the current state when a
	// configuration change occurs such screen orientation change. This
	// is not meant for "long term persistence". We store the tab navigation

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(TAB_KEY_INDEX, getActionBar()
				.getSelectedNavigationIndex());
	}

	class MyTabsListener implements ActionBar.TabListener {
		public Fragment fragment;
		public Context context;

		public MyTabsListener(Fragment fragment) {
			this.fragment = fragment;
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft.replace(R.id.fragment_container, fragment);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.remove(fragment);
		}

	}
}
