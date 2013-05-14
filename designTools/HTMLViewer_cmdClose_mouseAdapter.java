package designTools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class HTMLViewer_cmdClose_mouseAdapter extends MouseAdapter {
	HTMLViewer adaptee;

	HTMLViewer_cmdClose_mouseAdapter(HTMLViewer adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.adaptee.cmdClose_mouseClicked(e);
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: designTools.HTMLViewer_cmdClose_mouseAdapter
 * JD-Core Version: 0.6.2
 */
