package designTools;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class HTMLViewer_cmdBack_mouseAdapter extends MouseAdapter {
	HTMLViewer adaptee;

	HTMLViewer_cmdBack_mouseAdapter(HTMLViewer adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.adaptee.cmdBack_mouseClicked(e);
	}
}
