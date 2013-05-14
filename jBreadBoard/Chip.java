package jBreadBoard;

import jBreadBoard.v1_00.ChipModel;
import jBreadBoard.v1_10.Clickable;
import jBreadBoard.v1_10.DblClick;
import jBreadBoard.v1_10.Graphical;
import jBreadBoard.v1_10.LoadSave;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

public class Chip extends JComponent implements Device, CircuitSelection {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Circuit circuit;
	private ChipModel model;
	private boolean dblClickable;
	private boolean loadSaveable;
	private boolean graphical;
	private boolean clickable;
	private boolean sized;
	private Node[] connections;
	private int textX = 0;
	private int textY = 0;

	private MouseInputListener mia = new MouseInputAdapter() {
		int XFudge = 0;
		boolean moved = false;

		@Override
		public void mousePressed(MouseEvent e) {
			int clicks = e.getClickCount();

			if (clicks == 1) {
				Chip.this.circuit.select((Chip) e.getComponent());
			}
			if (clicks == 1 && Chip.this.clickable) {
				((Clickable) Chip.this.model).Clicked();
			}
			if (clicks == 2 && Chip.this.dblClickable) {
				((DblClick) Chip.this.model).DblClicked();
			}
			this.XFudge = e.getX();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Chip chip = (Chip) e.getComponent();
			BreadBoard origb = (BreadBoard) chip.getParent();

			Circuit pane = (Circuit) origb.getParent();
			int origx = chip.getX();

			if (pane.getJBreadBoard().getMode().equals("move")) {
				chip.delete();

				this.moved = true;

				Board board = pane.getBoardAt(e.getX() + chip.getX() + origb.getX() - this.XFudge, e.getY() + chip.getY() + origb.getY());
				BreadBoard newb;
				if (board != null && board instanceof BreadBoard) {
					newb = (BreadBoard) board;
				} else {
					newb = origb;
				}

				if (!newb.add(chip, e.getX() + chip.getX() + origb.getX() - newb.getX() - this.XFudge)) {
					boolean success = false;

					for (int x = e.getX(); !success && x > 0; x -= 8) {
						success = newb.add(chip, x + chip.getX() + origb.getX() - newb.getX() - this.XFudge);
					}

					if (!success) {
						origb.add(chip, origx);
					}
				}
			}
		}
	};

	public Chip(Circuit c, ChipModel m) {
		this.circuit = c;
		if (m == null) {
			this.model = JBreadBoard.getChipModel("integratedCircuits.ttl.logic.Gen7400");
		} else {
			this.model = m;
		}
		this.model.setAccess(new ChipAccess(this));
		this.connections = new Node[this.getNumberOfPins() * 2];

		this.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		this.sizeText();
		this.addMouseListener(this.mia);
		this.addMouseMotionListener(this.mia);

		this.dblClickable = JBreadBoard.implementsInterface(this.model, "jBreadBoard.v1_10.DblClick");
		this.loadSaveable = JBreadBoard.implementsInterface(this.model, "jBreadBoard.v1_10.LoadSave");
		this.graphical = JBreadBoard.implementsInterface(this.model, "jBreadBoard.v1_10.Graphical");
		this.clickable = JBreadBoard.implementsInterface(this.model, "jBreadBoard.v1_10.Clickable");
		this.sized = JBreadBoard.implementsInterface(this.model, "jBreadBoard.v1_10.Sized");
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
		Board board = this.getBoard();
		board.remove(this);
		board.repaint();
		for (int i = 0; i < this.getNumberOfPins() * 2; i++) {
			if (this.connections[i] != null) {
				this.connections[i].setType("NC");
				this.connections[i].setDevice(null);
			}
		}
	}

	@Override
	public String getInfo() {
		return "Component Name: \n" + this.getChipText() + "\n\n" + "Description: \n" + this.getDescription() + "\n\n" + "Manufacturer: \n" + this.getManufacturer() + "\n\n" + "JBreadBoard library name: \n" + this.getModelName().substring(this.getModelName().lastIndexOf('.') + 1) + ".class";
	}

	@Override
	public String getDiagram() {
		String fileName = this.model.toString().substring(0, this.model.toString().lastIndexOf('.')).replace('.', '/');
		fileName = "build/classes/" + fileName + '/' + this.model.getDiagram();

		return fileName;
	}

	@Override
	public void updateConnections() {
		this.tryupdateConnections();
	}

	public boolean tryupdateConnections() {
		boolean conflicts = false;

		for (int i = 0; i < this.getNumberOfPins() * 2; i++) {
			if (this.connections[i] != null) {
				this.connections[i].setType("NC");
				this.connections[i].setDevice(null);
			}

		}

		for (int i = 0; !conflicts && i < this.getNumberOfPins(); i++) {
			this.connections[i] = this.getBoard().getNodeAt(this.getX() + i * 8, this.getY() + this.getHeight() - 8);

			conflicts = NodeType.conflicts(this.connections[i].getType(), this.getPinType(i));
			this.connections[i].setType(this.getPinType(i));
			this.connections[i].setDevice(this);
		}

		for (int i = this.getNumberOfPins(); !conflicts && i < this.getNumberOfPins() * 2; i++) {
			this.connections[i] = this.getBoard().getNodeAt(this.getX() + (this.getNumberOfPins() * 2 - i - 1) * 8, this.getY());
			conflicts = NodeType.conflicts(this.connections[i].getType(), this.getPinType(i));
			this.connections[i].setType(this.getPinType(i));
			this.connections[i].setDevice(this);
		}

		if (conflicts) {
			for (int i = 0; i < this.getNumberOfPins() * 2; i++) {
				if (this.connections[i] != null) {
					this.connections[i].setType("NC");
					this.connections[i].setDevice(null);
				}
			}
		}

		return !conflicts;
	}

	public void sizeText() {
		Font biggestFont = new Font("SansSerif", 0, 13);
		boolean fontFits = false;
		Font currentFont = biggestFont;
		FontMetrics currentMetrics = this.getFontMetrics(currentFont);
		int size = currentFont.getSize();
		String name = currentFont.getName();
		int style = currentFont.getStyle();

		while (!fontFits) {
			if (currentMetrics.getHeight() <= this.getHeight() && currentMetrics.stringWidth(this.getChipText()) <= this.getWidth() - 10) {
				fontFits = true;
			} else {
				currentFont = new Font(name, style, --size);
				currentMetrics = this.getFontMetrics(currentFont);
			}
		}
		this.setFont(currentFont);
		this.textX = this.getWidth() / 2 - currentMetrics.stringWidth(this.getChipText()) / 2 + 2;
		this.textY = this.getHeight() / 2 + currentMetrics.getAscent() / 2 - 3;
	}

	public Board getBoard() {
		return (Board) this.getParent();
	}

	public Circuit getCircuit() {
		return this.circuit;
	}

	@Override
	public void paintComponent(Graphics g) {
		if (this.graphical) {
			((Graphical) this.model).paintComponent(g, this.circuit.selected == this);
		} else {
			Color chipcolor = Color.black;
			Color notchcolor = Color.darkGray;
			Color pincolor = Color.lightGray;
			Color textcolor = Color.white;

			if (this.circuit.selected == this) {
				chipcolor = Color.yellow;
				notchcolor = Color.orange;
				pincolor = Color.orange;
				textcolor = Color.black;
			}

			g.setColor(chipcolor);
			g.fillRect(0, 4, this.getWidth() - 1, this.getHeight() - 9);

			g.setColor(notchcolor);
			g.fillArc(-6, this.getHeight() / 2 - 6, 12, 10, -90, 180);

			g.setColor(pincolor);
			for (int i = 0; i < this.getNumberOfPins(); i++) {
				g.fillRect(i * 8 + 1, 2, 5, 2);
				g.fillRect(i * 8 + 1, this.getHeight() - 5, 5, 2);
			}

			g.setColor(textcolor);
			g.setFont(this.getFont());
			g.drawString(this.getChipText(), this.textX, this.textY);
		}
		super.paintComponent(g);
	}

	public Node[] getConnections() {
		return this.connections;
	}

	@Override
	public void reset() {
		this.model.reset();
	}

	@Override
	public void simulate() {
		this.model.simulate();
	}

	public int getNumberOfPins() {
		return this.model.getNumberOfPins();
	}

	public String getPinType(int i) {
		return this.model.getPinType(i);
	}

	public String getDescription() {
		return this.model.getDescription();
	}

	public String getChipText() {
		return this.model.getChipText();
	}

	public String getManufacturer() {
		return this.model.getManufacturer();
	}

	public String getModelName() {
		return this.model.getClass().getName();
	}

	@Override
	public String toString() {
		String filename = "";
		if (this.loadSaveable) {
			filename = " " + ((LoadSave) this.model).getFileName();
		}

		return new String("Device " + this.getClass().getName() + " " + this.getModelName() + " 0" + " 0" + " " + this.getX() + " " + this.getY() + filename);
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

	@Override
	public int getWidth() {
		return this.model.getNumberOfPins() * 8;
	}

	@Override
	public int getHeight() {
		if (this.model.isWide()) {
			return 48;
		}
		return 32;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public boolean implementsLoadSave() {
		return this.loadSaveable;
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: jBreadBoard.Chip JD-Core Version: 0.6.2
 */
