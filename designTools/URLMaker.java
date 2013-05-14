package designTools;

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

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: designTools.URLMaker JD-Core Version: 0.6.2
 */
