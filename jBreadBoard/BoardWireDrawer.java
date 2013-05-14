package jBreadBoard;

import java.awt.event.MouseEvent;

public class BoardWireDrawer extends WireDrawer {
	private Board board;

	public BoardWireDrawer(Board b) {
		super(b.getCircuit());
		this.board = b;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (this.getCircuit().getJBreadBoard().getMode().equals("wire")) {
			int x = e.getX() - e.getX() % 8;
			int y = e.getY() - e.getY() % 8;

			if (e.getX() % 8 < 4) {
				x -= 4;
			} else {
				x += 4;
			}

			if (this.board.isHole(x, y)) {
				Circuit circuit = this.getCircuit();
				x += this.board.getX();
				y += this.board.getY();
				WireDrawer.startx = x;
				WireDrawer.starty = y;
				WireDrawer.current = null;
				WireDrawer.last = null;
				circuit.getJBreadBoard().setMode("drawingwire", "Click to Bend. Double to Finish. Esc to Cancel Segment");
			}
		} else if (this.getCircuit().getJBreadBoard().getMode().equals("drawingwire")) {
			if (WireDrawer.current != null && this.getCircuit() != WireDrawer.current.getCircuit()) {
				WireDrawer.current.getCircuit().getJBreadBoard().setMode("wire", "Wire Removed. Wiring Mode. Click to Start Wire");
				return;
			}

			if (e.getClickCount() == 1 && WireDrawer.current != null) {
				WireDrawer.last = WireDrawer.current;
				WireDrawer.current = null;
			} else if (WireDrawer.last != null && e.getClickCount() == 2) {
				if (WireDrawer.current != null) {
					WireDrawer.last = WireDrawer.current;
				}
				WireDrawer.current = null;

				if (!this.board.isHole(WireDrawer.last.getEndX() - this.board.getX(), WireDrawer.last.getEndY() - this.board.getY())) {
					return;
				}

				if (WireDrawer.last.prev == null && WireDrawer.last.getStartX() == WireDrawer.last.getEndX() && WireDrawer.last.getStartY() == WireDrawer.last.getEndX()) {
					WireDrawer.last.delete();
					this.getCircuit().getJBreadBoard().setMessage("Wire Removed. Wiring Mode. Click to Start Wire");
				} else {
					WireDrawer.last.fixate();
				}

				String message = "Wiring Mode. Click to start Wire";

				if (WireDrawer.last.getParent() == null) {
					message = this.getCircuit().getJBreadBoard().getMessage();
				}
				WireDrawer.last = null;
				this.getCircuit().getJBreadBoard().setMode("wire", message);
			}
		}
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: jBreadBoard.BoardWireDrawer JD-Core Version: 0.6.2
 */
