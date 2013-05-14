package jBreadBoard;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

public abstract class Board extends JComponent implements CircuitSelection {
	private Circuit circuit;
	public LinkedList wires = new LinkedList();

	public LinkedList interwires = new LinkedList();

	public Board(Circuit c) {
		this.circuit = c;
		BoardDragListener bdl = new BoardDragListener(this);
		this.addMouseListener(bdl);
		this.addMouseMotionListener(bdl);
		BoardWireDrawer bwd = new BoardWireDrawer(this);
		this.addMouseListener(bwd);
		this.addMouseMotionListener(bwd);
	}

	@Override
	public String toString() {
		String string = new String("Board " + this.getClass().getName() + " " + this.getX() + " " + this.getY());

		Component[] components = this.getComponents();
		for (Component component2 : components) {
			string = new String(string + "\n" + component2.toString());
		}

		return string;
	}

	public void reset() {
		Component[] components = this.getComponents();
		for (Component component2 : components) {
			if (component2 instanceof Device) {
				((Device) component2).reset();
			}
		}
	}

	@Override
	public void select() {
		this.repaint();
	}

	@Override
	public void deselect() {
		this.repaint();
	}

	@Override
	public void delete() {
		if (this.getComponentCount() > 0 || this.wires.size() > 0 || this.interwires.size() > 0) {
			Object[] options = { "Yes", "No" };
			int n = JOptionPane.showOptionDialog(JOptionPane.getFrameForComponent(this), "The selected Board is not empty.\nAre sure you want to delete it?", "WARNING", 0, 2, null, options, options[1]);

			if (n == 0) {
				int maxi = this.wires.size();
				for (int i = 0; i < maxi; i++) {
					Wire wire = (Wire) this.wires.getFirst();
					wire.delete();
					this.wires.remove(wire);
				}
				maxi = this.interwires.size();
				for (int i = 0; i < maxi; i++) {
					Wire wire = (Wire) this.interwires.getFirst();
					wire.delete();
					this.interwires.remove(wire);
				}
				this.circuit.remove(this);
				this.circuit.repaint();
				this.circuit.BBselect = null;
			}
		} else {
			this.circuit.remove(this);
			this.circuit.repaint();
			this.circuit.BBselect = null;
		}
	}

	public abstract boolean isHole(int paramInt1, int paramInt2);

	public abstract Node getNodeAt(int paramInt1, int paramInt2);

	public Rectangle getInnerBounds() {
		return this.getBounds();
	}

	public Circuit getCircuit() {
		return this.circuit;
	}

	private class BoardDragListener extends MouseInputAdapter {
		private int XFudge;
		private int YFudge;
		private int GridX = 168;
		private int GridY = 168;
		private Board board;

		BoardDragListener(Board b) {
			this.board = b;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			this.board.getCircuit().select(this.board);
			this.XFudge = e.getX();
			this.YFudge = e.getY();

			Node node = this.board.getNodeAt(e.getX(), e.getY());
			if (node != null && Board.this.getCircuit().isSimulating()) {
				this.board.getCircuit().getJBreadBoard().setProbebar(node.getPotential().toTTL());
			} else {
				this.board.getCircuit().getJBreadBoard().setProbebar("");
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (Board.this.getCircuit().getJBreadBoard().getMode().equals("move")) {
				Circuit circuit = this.board.getCircuit();

				Vector boards = new Vector();
				int boardsIndex = 0;

				boards.add(this.board);

				while (boardsIndex < boards.size()) {
					Board current = (Board) boards.elementAt(boardsIndex);
					for (int i = 0; i < current.interwires.size(); i++) {
						Wire wire = (Wire) current.interwires.get(i);
						Board temp = circuit.getBoardAt(wire.getEndPiece().getX(), wire.getEndPiece().getY());
						if (!boards.contains(temp)) {
							boards.add(temp);
						}
						temp = circuit.getBoardAt(wire.getStartPiece().getX(), wire.getStartPiece().getY());
						if (!boards.contains(temp)) {
							boards.add(temp);
						}
					}
					boardsIndex++;
				}

				Point[] locs = new Point[boards.size()];
				int[] X = new int[boards.size()];
				int[] Y = new int[boards.size()];

				for (int i = 0; i < boards.size(); i++) {
					locs[i] = ((Board) boards.elementAt(i)).getLocation();
					X[i] = locs[i].x + e.getX() - this.XFudge;
					Y[i] = locs[i].y + e.getY() - this.YFudge;
				}

				boolean addboard = true;

				for (int i = 0; i < boards.size(); i++) {
					if (X[i] < 0) {
						addboard = false;
					}
					if (Y[i] < 0) {
						addboard = false;
					}

					if (X[i] % this.GridX < this.GridX / 2) {
						X[i] -= X[i] % this.GridX;
					} else {
						X[i] = X[i] + this.GridX - X[i] % this.GridX;
					}
					if (Y[i] % this.GridY < this.GridY / 2) {
						Y[i] -= Y[i] % this.GridY;
					} else {
						Y[i] = Y[i] + this.GridY - Y[i] % this.GridY;
					}

					circuit.remove((Board) boards.elementAt(i));
				}
				circuit.repaint();

				for (int i = 0; i < boards.size(); i++) {
					((Board) boards.elementAt(i)).setLocation(X[i], Y[i]);
					addboard = addboard && circuit.add((Board) boards.elementAt(i));
				}

				if (!addboard) {
					for (int i = 0; i < boards.size(); i++) {
						circuit.remove((Board) boards.elementAt(i));
					}
					circuit.repaint();
					for (int i = 0; i < boards.size(); i++) {
						((Board) boards.elementAt(i)).setLocation(locs[i]);
						circuit.add((Board) boards.elementAt(i));
					}
				}

				if (!this.board.getLocation().equals(locs[0])) {
					for (int i = 0; i < boards.size(); i++) {
						Board temp = (Board) boards.elementAt(i);
						for (int j = 0; j < temp.wires.size(); j++) {
							Wire wire = (Wire) temp.wires.get(j);
							wire.moveWire(X[i] - locs[i].x, Y[i] - locs[i].y);
						}
					}
				}
			}
		}
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: jBreadBoard.Board JD-Core Version: 0.6.2
 */
