package uk.ac.york.jbb.designtools;

import java.net.URL;

class URLMaker {
	private static URL cBase;

	public URLMaker(URL codeBase) {
		URLMaker.cBase = codeBase;
	}

	public static URL getURL(String s) {
		URL url = null;
		try {
			if (URLMaker.cBase == null) {
				url = new URL("file:" + s);
			} else {
				url = new URL(URLMaker.cBase + s);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
}
