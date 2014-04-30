package view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import service.RSSParser.Entry;
import android.app.ListFragment;
import android.content.Context;
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

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Entry e = (Entry) l.getItemAtPosition(position);
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(e
				.getLink()));
		startActivity(browserIntent);
	}

	@Override
	public void onStart() {
		super.onStart();
		new GetAndroidPitRssFeedTask().execute();
	}

	private String getAndroidPitRssFeed() throws IOException {
		InputStream in = null;
		String rssFeed = null;
		try {
			URL url = new URL("http://www.health.com/health/diet-fitness/feed");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
		}
		return rssFeed;
	}

	private class GetAndroidPitRssFeedTask extends
			AsyncTask<Void, Void, List<Entry>> {

		@Override
		protected List<Entry> doInBackground(Void... voids) {
			List<Entry> result = null;
			try {
				String feed = getAndroidPitRssFeed();
				result = parse(feed);
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		private List<Entry> parse(String rssFeed)
				throws XmlPullParserException, IOException {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(new StringReader(rssFeed));
			xpp.nextTag();
			return readRss(xpp);
		}

		private List<Entry> readRss(XmlPullParser parser)
				throws XmlPullParserException, IOException {
			List<Entry> items = new ArrayList<Entry>();
			parser.require(XmlPullParser.START_TAG, null, "rss");
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				if (name.equals("channel")) {
					items.addAll(readChannel(parser));
				} else {
					skip(parser);
				}
			}
			return items;
		}

		private List<Entry> readChannel(XmlPullParser parser)
				throws IOException, XmlPullParserException {
			List<Entry> items = new ArrayList<Entry>();
			parser.require(XmlPullParser.START_TAG, null, "channel");
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				if (name.equals("item")) {
					items.add(readItem(parser));
				} else {
					skip(parser);
				}
			}
			return items;
		}

		private Entry readItem(XmlPullParser parser)
				throws XmlPullParserException, IOException {
			String title = null;
			String summary = null;
			String link = null;
			parser.require(XmlPullParser.START_TAG, null, "item");
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				if (name.equals("title")) {
					title = readTitle(parser);
				} else if (name.equals("pubDate")) {
					summary = readSummary(parser);
				} else if (name.equals("link")) {
					link = readLink(parser);
				} else {
					skip(parser);
				}

			}
			return new Entry(title, summary, link);
		}

		// Processes title tags in the feed.
		private String readTitle(XmlPullParser parser) throws IOException,
				XmlPullParserException {
			parser.require(XmlPullParser.START_TAG, null, "title");
			String title = readText(parser);
			parser.require(XmlPullParser.END_TAG, null, "title");
			return title;
		}

		// Processes link tags in the feed.
		private String readLink(XmlPullParser parser) throws IOException,
				XmlPullParserException {
			parser.require(XmlPullParser.START_TAG, null, "link");
			String link = readText(parser);
			parser.require(XmlPullParser.END_TAG, null, "link");
			return link;
		}

		// Processes summary tags in the feed.
		private String readSummary(XmlPullParser parser) throws IOException,
				XmlPullParserException {
			parser.require(XmlPullParser.START_TAG, null, "pubDate");
			String summary = readText(parser);
			parser.require(XmlPullParser.END_TAG, null, "pubDate");
			return summary;
		}

		private String readText(XmlPullParser parser) throws IOException,
				XmlPullParserException {
			String result = "";
			if (parser.next() == XmlPullParser.TEXT) {
				result = parser.getText();
				parser.nextTag();
			}
			return result;
		}

		private void skip(XmlPullParser parser) throws XmlPullParserException,
				IOException {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				throw new IllegalStateException();
			}
			int depth = 1;
			while (depth != 0) {
				switch (parser.next()) {
				case XmlPullParser.END_TAG:
					depth--;
					break;
				case XmlPullParser.START_TAG:
					depth++;
					break;
				}
			}
		}

		@Override
		protected void onPostExecute(List<Entry> rssFeed) {
			if (rssFeed != null) {
				setListAdapter(new RSSArrayAdapter(getActivity(), rssFeed));
			}
		}
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
