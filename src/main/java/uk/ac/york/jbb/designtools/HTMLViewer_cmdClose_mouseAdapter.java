package uk.ac.york.jbb.designtools;

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
