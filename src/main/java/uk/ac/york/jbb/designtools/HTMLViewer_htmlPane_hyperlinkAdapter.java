package uk.ac.york.jbb.designtools;

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
