package view;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import service.RSSParser;
import service.RSSParser.Entry;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gamma.R;

public class RSSFragment extends ListFragment {

	private static final int MAX_RETRIES = 3;

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Entry e = (Entry) l.getItemAtPosition(position);
		showAlert(e.link);
	}

	@Override
	public void onStart() {
		super.onStart();
		new RssFeedTask().execute(
				"http://www.health.com/health/diet-fitness/feed",
				"http://www.situskesehatan.com/feed");
		// new RssFeedTask().execute("http://www.situskesehatan.com/feed");
	}

	private class RssFeedTask extends AsyncTask<String, Void, List<Entry>> {

		@Override
		protected List<Entry> doInBackground(String... urls) {
			List<Entry> result = null;
			try {
				System.out.println(urls[0] + "," + urls[1]);
				String feed = getRssFeed(urls[0]);
				RSSParser parser = new RSSParser();
				result = parser.parse(feed);
				System.out.println("size= " + result.size());
				result.addAll(parser.parse(getRssFeed(urls[1])));
				System.out.println("size stelah addAll= " + result.size());

			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(List<Entry> rssFeed) {
			if (rssFeed != null) {
				setListAdapter(new RSSArrayAdapter(getActivity(), rssFeed));
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
			conn.disconnect();
		}
		return rssFeed;
	}

	private void showAlert(final String link) {
		AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
				.create();

		alertDialog.setTitle("Menuju " + link + "..");

		LayoutInflater inflater = getActivity().getLayoutInflater();

		View v = inflater.inflate(R.layout.popup_konfirmasi_rss, null);
		alertDialog.setView(v);

		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Lanjut",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent browserIntent = new Intent(Intent.ACTION_VIEW,
								Uri.parse(link));
						startActivity(browserIntent);
					}
				});
		alertDialog.show();
	}

	static class ViewHolder {
		public TextView title;
		public TextView subtitle;
	}

	class RSSArrayAdapter extends ArrayAdapter<Entry> {

		private LayoutInflater inflater;

		public RSSArrayAdapter(Context context, List<Entry> feeds) {
			super(context, R.layout.simplerow, R.id.Title, feeds);
			// Cache the LayoutInflate to avoid asking for a new one each time.
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Entry set = (Entry) this.getItem(position);

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.simplerow, null);

				ViewHolder viewHolder = new ViewHolder();
				viewHolder.title = (TextView) convertView
						.findViewById(R.id.Title);
				viewHolder.subtitle = (TextView) convertView
						.findViewById(R.id.subTitle);

				convertView.setTag(viewHolder);

			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.title.setText(set.getTitle());
			holder.subtitle.setText(set.getSummary());

			return convertView;
		}
	}
}
