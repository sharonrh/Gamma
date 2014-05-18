package view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import controller.AchievementController;
import model.Achievement;
import service.RSSParser;
import service.RSSParser.Entry;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamma.R;

public class RSSFragment extends Fragment {

	private static ListView list;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View v = inflater.inflate(R.layout.fragment_artikel, container, false);
		list = (ListView) v.findViewById(R.id.listArtikel);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// you might want to use 'view' here
				Entry e = (Entry) parent.getItemAtPosition(position);
				showAlert(e.link);
			}
		});
		return v;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (isOnline()) {
			new RssFeedTask().execute(
					"http://www.health.com/health/diet-fitness/feed",
					"http://detik.feedsportal.com/c/33613/f/656109/index.rss");
		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
					.create();

			alertDialog.setTitle("Info");
			alertDialog
					.setMessage("Tidak ada koneksi internet, cek koneksi Anda dan coba lagi.");
			// alertDialog.setIcon(R.drawable.alerticon);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			alertDialog.show();
		}
	}

	public boolean isOnline() {
		ConnectivityManager conMgr = (ConnectivityManager) getActivity()
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

		if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
			return false;
		}
		return true;
	}

	private class RssFeedTask extends AsyncTask<String, Void, List<Entry>> {

		private ProgressDialog dialog = new ProgressDialog(getActivity());

		protected void onPreExecute() {
			dialog.setMessage("Mengambil data..");
			dialog.show();
		}

		@Override
		protected List<Entry> doInBackground(String... urls) {
			List<Entry> result = null;
			try {
				System.out.println(urls[0] + "," + urls[1]);
				String feed = getRssFeed(urls[0]);
				RSSParser parser = new RSSParser();
				result = parser.parse(feed,0);
				result.addAll(parser.parse(getRssFeed(urls[1]),1));
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(List<Entry> rssFeed) {
			dialog.dismiss();
			if (rssFeed != null && getActivity()!=null) {
				list.setAdapter(new RSSArrayAdapter(getActivity(), rssFeed));
			}
		}
	}

	private String getRssFeed(String targetUrl) throws IOException {
		InputStream in = null;
		String rssFeed = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(targetUrl);
			conn = (HttpURLConnection) url.openConnection();
			in = conn.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for (int count; (count = in.read(buffer)) != -1;) {
				out.write(buffer, 0, count);
			}
			byte[] response = out.toByteArray();
			rssFeed = new String(response, "UTF-8");
		} finally {
			if (in != null) {
				in.close();
			}
			System.out.println("dc-ed");
			conn.disconnect(); // liat ntar mesti diapus ga
		}
		return rssFeed;
	}

	private void showAlert(final String link) {
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		alertDialog.setTitle("Menuju " + link + "..");

		LayoutInflater inflater = getActivity().getLayoutInflater();

		final View v = inflater.inflate(R.layout.popup_konfirmasi_rss, null);
		alertDialog.setView(v);

		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Lanjut",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
                        // increment setiap baca artikel
                        AchievementController ac = new AchievementController(v.getContext());
                        Achievement achievement = ac.getHandler().getAchievement("Bookworm");
                        boolean cek = achievement.isGet();
                        achievement.addProgress();
                        ac.getHandler().updateAchievement(achievement);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
								Uri.parse(link));
						startActivity(browserIntent);
                        if (achievement.isGet() == true && cek == false) {
                            Toast.makeText(v.getContext(), "Selamat! Anda telah mendapat achievement Bookworm!", Toast.LENGTH_LONG).show();
                        }
					}
				});
		alertDialog.show();
	}

	static class ViewHolder {
		public TextView title;
		public TextView date;
		public TextView desc;

	}

	class RSSArrayAdapter extends ArrayAdapter<Entry> {

		private LayoutInflater inflater;

		public RSSArrayAdapter(Context context, List<Entry> feeds) {
			super(context, R.layout.list_artikel, R.id.Title, feeds);
			// Cache the LayoutInflate to avoid asking for a new one each time.
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Entry set = (Entry) this.getItem(position);

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_artikel, null);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.title = (TextView) convertView
						.findViewById(R.id.judulArtikel);
				viewHolder.date = (TextView) convertView
						.findViewById(R.id.dateArtikel);
				viewHolder.desc = (TextView) convertView
						.findViewById(R.id.kutipan);

				convertView.setTag(viewHolder);

			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.title.setText(set.getTitle());
			holder.date.setText(set.getDate());
			holder.desc.setText(set.getDesc());
			return convertView;
		}
	}
}
