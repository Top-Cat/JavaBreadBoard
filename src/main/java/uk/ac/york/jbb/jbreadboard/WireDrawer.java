package uk.ac.york.jbb.jbreadboard;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

public class WireDrawer extends MouseInputAdapter {
	static Color color = Color.red;

	static int startx = 0;
	static int starty = 0;

	static Wire last = null;

	static Wire current = null;
	private Circuit circuit;

	WireDrawer(Circuit c) {
		this.circuit = c;
	}

	public Circuit getCircuit() {
		return this.circuit;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (this.circuit.getJBreadBoard().getMode().equals("drawingwire")) {
			if (WireDrawer.current != null && this.circuit != WireDrawer.current.getCircuit()) {
				WireDrawer.current.getCircuit().getJBreadBoard().setMode("wire", "Wire Removed. Wiring Mode. Click to Start Wire");
			} else if (WireDrawer.last != null && WireDrawer.current != null) {
				WireDrawer.last = WireDrawer.current;
				WireDrawer.current = null;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (this.circuit.getJBreadBoard().getMode().equals("drawingwire")) {
			if (WireDrawer.current != null && this.circuit != WireDrawer.current.getCircuit()) {
				WireDrawer.current.getCircuit().getJBreadBoard().setMode("wire", "Wire Removed. Wiring Mode. Click to Start Wire");
				return;
			}

			if (WireDrawer.current != null) {
				JComponent component = (JComponent) WireDrawer.current.getParent();
				component.remove(WireDrawer.current);
				WireDrawer.current = null;
			}
			int x0;
			int y0;
			if (WireDrawer.last != null) {
				x0 = WireDrawer.last.getEndX();
				y0 = WireDrawer.last.getEndY();

				WireDrawer.last.next = null;
			} else {
				x0 = WireDrawer.startx;
				y0 = WireDrawer.starty;
			}
			int y;
			int x;
			if (e.getComponent() instanceof Circuit) {
				x = e.getX();
				y = e.getY();
			} else {
				x = e.getX() + e.getComponent().getX();
				y = e.getY() + e.getComponent().getY();
			}

			WireDrawer.current = this.circuit.extendWire(WireDrawer.last, WireDrawer.color, x0, y0, x, y);
		}
	}
}
