package service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class RSSParser {

	public List<Entry> parse(String rssFeed) throws XmlPullParserException,
			IOException {
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

	private List<Entry> readChannel(XmlPullParser parser) throws IOException,
			XmlPullParserException {
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

	private Entry readItem(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		String title = null;
		String date = null;
		String link = null;
		String desc = null;
		parser.require(XmlPullParser.START_TAG, null, "item");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("title")) {
				title = readTitle(parser);
			} else if (name.equals("pubDate")) {
				date = readDate(parser);
			} else if (name.equals("link")) {
				link = readLink(parser);
			} else if (name.equals("description")) {
				desc = readDesc(parser);
			} else {
				skip(parser);
			}

		}
		return new Entry(title, date, link, desc);
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
	private String readDate(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "pubDate");
		String date = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "pubDate");
		return date;
	}

	// Processes summary tags in the feed.
	private String readDesc(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "description");
		String desc = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "description");
		return desc;
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

	public static class Entry {
		public final String title;
		public final String link;
		public final String date;
		public final String desc;

		public Entry(String title, String date, String link, String desc) {
			this.title = title;
			this.date = date;
			this.link = link;
			this.desc = desc;
		}

		public String getTitle() {
			return title;
		}

		public String getDate() {
			return date;
		}

		public String getLink() {
			return link;
		}

		public String getDesc() {
			return desc;
		}
	}
}
