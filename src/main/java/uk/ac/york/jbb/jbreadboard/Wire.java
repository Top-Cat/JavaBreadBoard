package uk.ac.york.jbb.jbreadboard;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

public class Wire extends JComponent implements CircuitSelection {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean locset = Boolean.valueOf(false);
	private int xloc;
	private int yloc;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int width;
	private int height;
	public Wire next = null;

	public Wire prev = null;

	private boolean Horizontal = true;

	private Color color = Color.red;
	private Color selectedcolor = Color.cyan;
	private Color maincolor = this.color;
	private Color outlinecolor = Color.black;
	private Color tipcolor = Color.yellow;
	private Circuit circuit;
	private Potential potential;
	static final MouseInputListener wireselect = new MouseInputAdapter() {
		@Override
		public void mouseMoved(MouseEvent e) {
			Wire wire = (Wire) e.getComponent();

			if (wire.getCircuit().getJBreadBoard().getMode().equals("drawingwire")) {
				WireDrawer wd = new WireDrawer(wire.getCircuit());
				wd.mouseMoved(e);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Wire wire = (Wire) e.getComponent();
			String mode = wire.getCircuit().getJBreadBoard().getMode();
			int clickyend;
			int clickxend;
			if (wire.Horizontal) {
				clickxend = e.getX() + wire.getX();
				clickxend = clickxend - clickxend % 8 - 4;
				clickyend = wire.getY();
			} else {
				clickyend = e.getY() + wire.getY();
				clickyend -= clickyend % 8;
				clickxend = wire.getX();
			}

			if (!mode.equals("sim") && !mode.equals("drawingwire") && wire.getStartPiece() == wire && e.getClickCount() == 2 && clickxend == wire.x1 && clickyend == wire.y1) {
				Wire start = wire;
				Wire end = wire.getEndPiece();

				wire.deselect();

				while (wire != null) {
					wire.removeMouseListener(Wire.wireselect);
					wire = wire.next;
				}

				wire = start;

				while (wire != null) {
					Wire temp = wire.next;
					wire.reverse();
					wire = temp;
				}

				if (end.potential != null) {
					end.potential.delete();
					end.getCircuit().getSimulation().addEvent(end.potential.getNode1(), 0);
					end.getCircuit().getSimulation().addEvent(end.potential.getNode2(), 0);
				}

				Board board1 = start.getCircuit().getBoardAt(start.getStartX(), start.getStartY());
				Board board2 = end.getCircuit().getBoardAt(end.getEndX(), end.getEndY());

				if (board1 != board2) {
					board1.interwires.remove(end);
					board2.interwires.remove(end);
				}

				board2.wires.remove(end);

				Wire temp = start;
				start = end;
				end = temp;

				WireDrawer.color = start.color;
				WireDrawer.startx = start.x1;
				WireDrawer.starty = start.y1;
				WireDrawer.last = end.prev;
				WireDrawer.current = end;
				start.getCircuit().getJBreadBoard().setMode("drawingwire", "Click to Bend. Double to Finish. Esc to Cancel Segment");
			} else if (!mode.equals("sim") && !mode.equals("drawingwire") && wire.next == null && e.getClickCount() == 2 && clickxend == wire.x2 && clickyend == wire.y2) {
				Wire start = wire.getStartPiece();
				Wire end = wire.getEndPiece();

				wire = start;
				wire.deselect();

				while (wire != null) {
					wire.removeMouseListener(Wire.wireselect);
					wire.removeMouseMotionListener(Wire.wireselect);
					wire = wire.next;
				}

				if (end.potential != null) {
					end.potential.delete();
					end.getCircuit().getSimulation().addEvent(end.potential.getNode1(), 0);
					end.getCircuit().getSimulation().addEvent(end.potential.getNode2(), 0);
				}

				Board board1 = start.getCircuit().getBoardAt(start.getStartX(), start.getStartY());
				Board board2 = end.getCircuit().getBoardAt(end.getEndX(), end.getEndY());

				if (board1 != board2) {
					board1.interwires.remove(end);
					board2.interwires.remove(end);
				}

				board2.wires.remove(end);

				WireDrawer.color = start.color;
				WireDrawer.startx = start.x1;
				WireDrawer.starty = start.y1;
				WireDrawer.last = end.prev;
				WireDrawer.current = end;
				start.getCircuit().getJBreadBoard().setMode("drawingwire", "Click to Bend. Double to Finish. Esc to Cancel Segment");
			} else {
				wire.getCircuit().select(wire);
			}
		}
	};

	public int getStartX() {
		if (this.locset.booleanValue()) {
			return this.x1;
		}
		return this.getX();
	}

	public int getEndX() {
		if (this.locset.booleanValue()) {
			return this.x2;
		}
		return this.getX();
	}

	public int getStartY() {
		if (this.locset.booleanValue()) {
			return this.y1;
		}
		return this.getY();
	}

	public int getEndY() {
		if (this.locset.booleanValue()) {
			return this.y2;
		}
		return this.getY();
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

	@Override
	public void setVisible(boolean b) {
		if (!b) {
			this.setSize(0, 0);
		} else {
			this.setSize(this.width, this.height);
		}
	}

	public Circuit getCircuit() {
		return this.circuit;
	}

	@Override
	public String getInfo() {
		return "";
	}

	@Override
	public String getDiagram() {
		return null;
	}

	@Override
	public void select() {
		Wire wire = this.getStartPiece();
		while (wire != null) {
			wire.paintinverse();
			wire = wire.next;
		}
		this.getParent().repaint();
	}

	@Override
	public void deselect() {
		Wire wire = this.getStartPiece();
		while (wire != null) {
			wire.paintnormal();
			wire = wire.next;
		}
		Component parent = this.getParent();
		if (parent != null) {
			parent.repaint();
		}
	}

	@Override
	public void delete() {
		Wire start = this.getStartPiece();
		Wire end = this.getEndPiece();

		if (end.potential != null) {
			end.potential.delete();
			this.circuit.getSimulation().addEvent(end.potential.getNode1(), 0);
			this.circuit.getSimulation().addEvent(end.potential.getNode2(), 0);
		}

		Board board1 = this.circuit.getBoardAt(start.getStartX(), start.getStartY());
		Board board2 = this.circuit.getBoardAt(end.getEndX(), end.getEndY());

		if (board1 != board2) {
			if (board1 != null) {
				board1.interwires.remove(end);
			}
			if (board2 != null) {
				board2.interwires.remove(end);
			}
		}

		if (board2 != null) {
			board2.wires.remove(end);
		}

		while (start != null) {
			this.circuit.remove(start);
			start = start.next;
		}
		this.circuit.repaint();
	}

	private void reverse() {
		Wire tempwire = this.next;
		this.next = this.prev;
		this.prev = tempwire;

		int t = this.x1;
		this.x1 = this.x2;
		this.x2 = t;
		t = this.y1;
		this.y1 = this.y2;
		this.y2 = t;
	}

	public void fixate() {
		Wire first = this.getStartPiece();
		Wire last = this.getEndPiece();

		Board startboard = this.circuit.getBoardAt(first.getStartX(), first.getStartY());
		Board endboard = this.circuit.getBoardAt(last.getEndX(), last.getEndY());

		Node startnode = startboard.getNodeAt(first.getStartX() - startboard.getX(), first.getStartY() - startboard.getY());

		Node endnode = endboard.getNodeAt(last.getEndX() - endboard.getX(), last.getEndY() - endboard.getY());

		boolean ok = true;

		if (NodeType.conflicts(startnode.getType(), endnode.getType())) {
			ok = false;
			this.circuit.getJBreadBoard().setMessage("Wire Removed. That wire created a potential short circuit.");
		}

		if (startnode.getPotential() == endnode.getPotential()) {
			ok = false;
			this.circuit.getJBreadBoard().setMessage("Wire Removed. That wire created a connection that was already made.");
		}

		if (ok) {
			last.potential = new Potential(startnode, endnode);
			this.circuit.getSimulation().addEvent(startnode, 0);

			endboard.wires.add(last);

			if (endboard != startboard) {
				endboard.interwires.add(last);
				startboard.interwires.add(last);
			}

			Wire temp = last;
			while (temp != null) {
				temp.addMouseListener(Wire.wireselect);
				temp.addMouseMotionListener(Wire.wireselect);
				temp = temp.prev;
			}
		} else {
			last.delete();
		}
	}

	public Wire getStartPiece() {
		Wire wire = this;
		while (wire.prev != null) {
			wire = wire.prev;
		}
		return wire;
	}

	public Wire getEndPiece() {
		Wire wire = this;
		while (wire.next != null) {
			wire = wire.next;
		}
		return wire;
	}

	public int getLength() {
		int count = 0;
		Wire wire = this.getStartPiece();
		while (wire != null) {
			wire = wire.next;
			count++;
		}
		return count;
	}

	@Override
	public String toString() {
		String string = "Wire";
		Wire wire = this.getStartPiece();
		string = string + " " + this.color.getRed();
		string = string + " " + this.color.getGreen();
		string = string + " " + this.color.getBlue();
		string = string + " " + wire.x1;
		string = string + " " + wire.y1;

		while (wire != null) {
			string = string + " " + wire.x2 + " " + wire.y2;
			wire = wire.next;
		}
		return string;
	}

	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);

		if (this.locset.booleanValue()) {
			int dx = x - this.xloc;
			int dy = y - this.yloc;

			this.x1 += dx;
			this.x2 += dx;
			this.y1 += dy;
			this.y2 += dy;
		} else {
			this.x1 = x;
			this.x2 = x;
			this.y1 = y;
			this.y2 = y;
			this.locset = Boolean.valueOf(true);
		}
		this.xloc = x;
		this.yloc = y;
	}

	public void moveWire(int dx, int dy) {
		Wire wire = this.getStartPiece();
		while (wire != null) {
			wire.setLocation(wire.getX() + dx, wire.getY() + dy);
			wire = wire.next;
		}
	}

	public void paintinverse() {
		this.maincolor = this.selectedcolor;
		this.outlinecolor = Color.white;
		this.tipcolor = Color.blue;
	}

	public void paintnormal() {
		this.maincolor = this.color;
		this.outlinecolor = Color.black;
		this.tipcolor = Color.yellow;
	}

	@Override
	public void paintComponent(Graphics g) {
		int middlestart = 8;
		int middleend = 8;
		super.paintComponent(g);

		if (this.circuit.hiddenwires) {
			return;
		}

		if (this.width == 8 && this.height == 8) {
			return;
		}

		if (this.prev == null) {
			this.paintStart(g);
		} else if (this.prev.Horizontal != this.Horizontal) {
			this.paintCorner(g);
		} else if (this.x2 < this.x1 || this.y2 < this.y1) {
			middleend = 0;
		} else {
			middlestart = 0;
		}

		if (this.Horizontal) {
			if (this.width > middlestart + middleend) {
				g.setColor(this.outlinecolor);
				g.drawLine(middlestart, 1, this.width - middleend - 1, 1);
				g.drawLine(middlestart, 5, this.width - middleend - 1, 5);
				g.setColor(this.maincolor);
				g.fillRect(middlestart, 2, this.width - middleend - middlestart, 3);
			}
		} else if (this.height > middlestart + middleend) {
			g.setColor(this.outlinecolor);
			g.drawLine(1, middlestart, 1, this.height - middleend - 1);
			g.drawLine(5, middlestart, 5, this.height - middleend - 1);
			g.setColor(this.maincolor);
			g.fillRect(2, middlestart, 3, this.height - middleend - middlestart);
		}

		if (this.next == null) {
			this.paintEnd(g);
		}
	}

	private void paintStart(Graphics g) {
		int relx = this.x1 - this.xloc;
		int rely = this.y1 - this.yloc;

		if (this.Horizontal) {
			if (this.x2 > this.x1) {
				g.setColor(this.outlinecolor);
				g.drawRect(relx + 2, rely + 2, 2, 2);
				g.drawRect(relx + 4, rely + 1, 3, 4);
				g.setColor(this.tipcolor);
				g.drawLine(relx + 3, rely + 3, relx + 4, rely + 3);
				g.setColor(this.maincolor);
				g.fillRect(relx + 5, rely + 2, 3, 3);
			} else {
				g.setColor(this.outlinecolor);
				g.drawRect(relx + 3, rely + 2, 2, 2);
				g.drawRect(relx + 0, rely + 1, 3, 4);
				g.setColor(this.tipcolor);
				g.drawLine(relx + 3, rely + 3, relx + 4, rely + 3);
				g.setColor(this.maincolor);
				g.fillRect(relx + 0, rely + 2, 3, 3);
			}

		} else if (this.y2 < this.y1) {
			g.setColor(this.outlinecolor);
			g.drawRect(relx + 2, rely + 2, 2, 2);
			g.drawRect(relx + 1, rely + 0, 4, 2);
			g.setColor(this.tipcolor);
			g.drawLine(relx + 3, rely + 2, relx + 3, rely + 3);
			g.setColor(this.maincolor);
			g.fillRect(relx + 2, rely + 0, 3, 2);
		} else {
			g.setColor(this.outlinecolor);
			g.drawRect(relx + 2, rely + 2, 2, 2);
			g.drawRect(relx + 1, rely + 4, 4, 3);
			g.setColor(this.tipcolor);
			g.drawLine(relx + 3, rely + 3, relx + 3, rely + 4);
			g.setColor(this.maincolor);
			g.fillRect(relx + 2, rely + 5, 3, 3);
		}
	}

	private void paintEnd(Graphics g) {
		int relx = this.x2 - this.xloc;
		int rely = this.y2 - this.yloc;

		if (this.Horizontal) {
			if (this.x2 < this.x1) {
				g.setColor(this.outlinecolor);
				g.drawRect(relx + 2, rely + 2, 2, 2);
				g.drawRect(relx + 4, rely + 1, 3, 4);
				g.setColor(this.tipcolor);
				g.drawLine(relx + 3, rely + 3, relx + 4, rely + 3);
				g.setColor(this.maincolor);
				g.fillRect(relx + 5, rely + 2, 3, 3);
			} else {
				g.setColor(this.outlinecolor);
				g.drawRect(relx + 3, rely + 2, 2, 2);
				g.drawRect(relx + 0, rely + 1, 3, 4);
				g.setColor(this.tipcolor);
				g.drawLine(relx + 3, rely + 3, relx + 4, rely + 3);
				g.setColor(this.maincolor);
				g.fillRect(relx + 0, rely + 2, 3, 3);
			}

		} else if (this.y2 > this.y1) {
			g.setColor(this.outlinecolor);
			g.drawRect(relx + 2, rely + 2, 2, 2);
			g.drawRect(relx + 1, rely + 0, 4, 2);
			g.setColor(this.tipcolor);
			g.drawLine(relx + 3, rely + 2, relx + 3, rely + 3);
			g.setColor(this.maincolor);
			g.fillRect(relx + 2, rely + 0, 3, 2);
		} else {
			g.setColor(this.outlinecolor);
			g.drawRect(relx + 2, rely + 2, 2, 2);
			g.drawRect(relx + 1, rely + 4, 4, 3);
			g.setColor(this.tipcolor);
			g.drawLine(relx + 3, rely + 3, relx + 3, rely + 4);
			g.setColor(this.maincolor);
			g.fillRect(relx + 2, rely + 5, 3, 3);
		}
	}

	private void paintCorner(Graphics g) {
		int relx = this.x1 - this.xloc;
		int rely = this.y1 - this.yloc;
		int corner;
		if (this.Horizontal) {
			if (this.getEndX() > this.getStartX()) {
				if (this.prev.getEndY() > this.prev.getStartY()) {
					corner = 3;
				} else {
					corner = 1;
				}
			} else {
				if (this.prev.getEndY() > this.prev.getStartY()) {
					corner = 4;
				} else {
					corner = 2;
				}
			}
		} else {
			if (this.getEndY() > this.getStartY()) {
				if (this.prev.getEndX() > this.prev.getStartX()) {
					corner = 2;
				} else {
					corner = 1;
				}
			} else {
				if (this.prev.getEndX() > this.prev.getStartX()) {
					corner = 4;
				} else {
					corner = 3;
				}
			}
		}
		switch (corner) {
			case 1:
				g.setColor(this.maincolor);
				g.fillRect(relx + 3, rely + 2, 5, 4);
				g.fillRect(relx + 2, rely + 3, 3, 5);
				g.setColor(this.outlinecolor);
				g.drawLine(relx + 3, rely + 1, relx + 7, rely + 1);
				g.drawLine(relx + 2, rely + 2, relx + 2, rely + 2);
				g.drawLine(relx + 1, rely + 3, relx + 1, rely + 7);
				g.drawLine(relx + 6, rely + 5, relx + 7, rely + 5);
				g.drawLine(relx + 5, rely + 6, relx + 5, rely + 7);
				break;
			case 2:
				g.setColor(this.maincolor);
				g.fillRect(relx + 0, rely + 2, 4, 4);
				g.fillRect(relx + 2, rely + 3, 3, 5);
				g.setColor(this.outlinecolor);
				g.drawLine(relx + 0, rely + 1, relx + 3, rely + 1);
				g.drawLine(relx + 4, rely + 2, relx + 4, rely + 2);
				g.drawLine(relx + 5, rely + 3, relx + 5, rely + 7);
				g.drawLine(relx + 0, rely + 5, relx + 0, rely + 5);
				g.drawLine(relx + 1, rely + 6, relx + 1, rely + 7);
				break;
			case 3:
				g.setColor(this.maincolor);
				g.fillRect(relx + 2, rely + 0, 4, 4);
				g.fillRect(relx + 3, rely + 2, 5, 3);
				g.setColor(this.outlinecolor);
				g.drawLine(relx + 1, rely + 0, relx + 1, rely + 3);
				g.drawLine(relx + 5, rely + 0, relx + 5, rely + 0);
				g.drawLine(relx + 6, rely + 1, relx + 7, rely + 1);
				g.drawLine(relx + 2, rely + 4, relx + 2, rely + 4);
				g.drawLine(relx + 3, rely + 5, relx + 7, rely + 5);
				break;
			case 4:
				g.setColor(this.maincolor);
				g.fillRect(relx + 1, rely + 0, 4, 4);
				g.fillRect(relx + 0, rely + 2, 4, 3);
				g.setColor(this.outlinecolor);
				g.drawLine(relx + 1, rely + 0, relx + 1, rely + 0);
				g.drawLine(relx + 5, rely + 0, relx + 5, rely + 3);
				g.drawLine(relx + 0, rely + 1, relx + 0, rely + 1);
				g.drawLine(relx + 4, rely + 4, relx + 4, rely + 4);
				g.drawLine(relx + 0, rely + 5, relx + 3, rely + 5);
		}
	}

	public Wire(Circuit c, Color col, boolean orientation, int px1, int py1, int px2, int py2, Wire previous) {
		this.circuit = c;

		this.prev = previous;
		if (this.prev != null) {
			this.color = this.prev.color;
			px1 = this.prev.x2;
			py1 = this.prev.y2;
			this.prev.next = this;
		} else {
			this.color = col;
		}

		this.Horizontal = orientation;
		if (this.Horizontal) {
			py2 = py1;
			this.height = 8;
			this.yloc = py1;

			if (px2 > px1) {
				this.width = 8 + px2 - px1;
				this.xloc = px1;
			} else {
				this.width = 8 + px1 - px2;
				this.xloc = px2;
			}
		} else {
			px2 = px1;
			this.width = 8;
			this.xloc = px1;

			if (py2 > py1) {
				this.height = 8 + py2 - py1;
				this.yloc = py1;
			} else {
				this.height = 8 + py1 - py2;
				this.yloc = py2;
			}
		}

		this.setLocation(this.xloc, this.yloc);
		this.x1 = px1;
		this.x2 = px2;
		this.y1 = py1;
		this.y2 = py2;
		this.setSize(this.width, this.height);

		if (this.color != null) {
			this.maincolor = this.color;
			this.selectedcolor = new Color(255 - this.color.getRed(), 255 - this.color.getGreen(), 255 - this.color.getBlue());
		}
	}
}
