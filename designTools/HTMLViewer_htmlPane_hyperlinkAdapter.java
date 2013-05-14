package designTools;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

class HTMLViewer_htmlPane_hyperlinkAdapter implements HyperlinkListener {
	HTMLViewer adaptee;

	HTMLViewer_htmlPane_hyperlinkAdapter(HTMLViewer adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		this.adaptee.htmlPane_hyperlinkUpdate(e);
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: designTools.HTMLViewer_htmlPane_hyperlinkAdapter
 * JD-Core Version: 0.6.2
 */
