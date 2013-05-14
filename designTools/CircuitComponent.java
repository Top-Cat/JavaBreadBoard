package designTools;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class CircuitComponent extends JLabel {
	static final int COMPONENT_COUNT = 8;
	static final int AND = 0;
	static final int OR = 1;
	static final int NOT = 2;
	static final int INPUT_PIN = 3;
	static final int OUTPUT_PIN = 4;
	static final int GND = 5;
	static final int VCC = 6;
	static final int LABEL = 7;
	private static final String[] imageFilenames = { "build/classes/designTools/icons/and.gif", "build/classes/designTools/icons/or.gif", "build/classes/designTools/icons/not.gif", "build/classes/designTools/icons/in.gif", "build/classes/designTools/icons/out.gif", "build/classes/designTools/icons/gnd.gif", "build/classes/designTools/icons/vcc.gif", null };

	private static final String[] menuIconFilenames = { "build/classes/designTools/icons/and_menu.gif", "build/classes/designTools/icons/or_menu.gif", "build/classes/designTools/icons/not_menu.gif", "build/classes/designTools/icons/in_menu.gif", "build/classes/designTools/icons/out_menu.gif", "build/classes/designTools/icons/gnd_menu.gif", "build/classes/designTools/icons/vcc_menu.gif", null };

	private Point[][][] pinOffsets = { { { new Point(0, 20), new Point(0, 40) }, { new Point(60, 30) } }, { { new Point(0, 20), new Point(0, 40) }, { new Point(60, 30) } }, { { new Point(0, 20) }, { new Point(60, 20) } }, { new Point[0], { new Point(60, 20) } }, { { new Point(0, 20) }, new Point[0] }, { new Point[0], { new Point(40, 20) } }, { new Point[0], { new Point(40, 20) } }, { new Point[0], new Point[0] } };
	private int ctype;
	private BufferedImage image;
	private ArrayList inList = new ArrayList();
	private boolean state = false;

	public CircuitComponent(int type) {
		this.ctype = type;
		if (type == 3 || type == 4) {
			String pinLabel = JOptionPane.showInputDialog(null, "Please give a name for this pin\n(maximum 12 characters)", "Pin Label", -1);

			if (pinLabel == null) {
				pinLabel = "";
			}

			if (pinLabel.length() > 12) {
				pinLabel = pinLabel.substring(0, 12);
			}

			this.setText(pinLabel);

			if (this.ctype == 3) {
				this.setHorizontalTextPosition(2);
			}
		}

		if (CircuitComponent.imageFilenames[type] != null) {
			try {
				URL imageURL = URLMaker.getURL(CircuitComponent.imageFilenames[type]);
				this.image = ImageIO.read(imageURL);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.setIcon(new ImageIcon(this.image));
			this.setBounds(new Rectangle(0, 0, this.image.getWidth() + this.getTextSize(), this.image.getHeight()));
		} else {
			this.setBounds(new Rectangle(0, 0, this.getTextSize(), 20));
		}

		if (type == 3) {
			this.updatePinOffset();
		}

		this.state = type == 6;
		this.setOpaque(false);
	}

	private int getTextSize() {
		int additionalWidth = 0;
		String label = this.getText();
		if (label.length() > 0) {
			int stringWidth = this.getFontMetrics(this.getFont()).stringWidth(label);
			int iconTextGap = Workspace.getGridSize() - stringWidth % Workspace.getGridSize();
			this.setIconTextGap(iconTextGap);
			additionalWidth = stringWidth + iconTextGap;
		}
		return additionalWidth;
	}

	private void updatePinOffset() {
		this.pinOffsets[3][1][0] = new Point(this.getWidth(), (int) this.pinOffsets[3][1][0].getY());
	}

	private void recalculateSize() {
		if (this.image != null) {
			this.setSize(this.image.getWidth() + this.getTextSize(), this.image.getHeight());
		} else {
			this.setSize(this.getTextSize(), 20);
		}
		if (this.ctype == 3) {
			this.updatePinOffset();
		}
	}

	public CircuitComponent(String label) {
		super(label);
		this.ctype = 7;
		this.setBounds(new Rectangle(0, 0, this.getTextSize(), 20));
	}

	public static String getImageIcon(int componentType) {
		return CircuitComponent.imageFilenames[componentType];
	}

	public static String getToolbarIcon(int componentType) {
		return CircuitComponent.menuIconFilenames[componentType];
	}

	public int getType() {
		return this.ctype;
	}

	public Point[] getInputLocations() {
		Point[] inPositions = this.pinOffsets[this.ctype][0].clone();
		for (int i = 0; i < inPositions.length; i++) {
			Point inPosition = (Point) inPositions[i].clone();
			inPosition.setLocation(inPosition.getX() + this.getX(), inPositions[i].getY() + this.getY());
			inPositions[i] = inPosition;
		}
		return inPositions;
	}

	public Point getOutputLocation() {
		Point outPosition;
		if (this.pinOffsets[this.ctype][1].length > 0) {
			outPosition = (Point) this.pinOffsets[this.ctype][1][0].clone();
			outPosition.setLocation(outPosition.getX() + this.getX(), outPosition.getY() + this.getY());
		} else {
			outPosition = null;
		}
		return outPosition;
	}

	public void connectInput(CircuitComponent inComponent) {
		this.inList.add(inComponent);
	}

	public void disconnectInput(CircuitComponent inComponent) {
		for (int i = 0; i < this.inList.size(); i++) {
			if (this.inList.get(i).equals(inComponent)) {
				this.inList.remove(i);
			}
		}
	}

	public ArrayList getConnectedInputs() {
		return this.inList;
	}

	public void setSelected(boolean selected) {
		if (selected) {
			this.setBorder(BorderFactory.createEtchedBorder());
		} else {
			this.setBorder(null);
		}
		this.repaint();
	}

	public void updateText() {
		String newText = JOptionPane.showInputDialog(null, "Insert new text", "Update Label", -1);

		if (newText != null) {
			if (this.ctype == 3 || this.ctype == 4) {
				if (newText.length() > 12) {
					newText = newText.substring(0, 12);
				}
			}

			this.setText(newText);

			this.recalculateSize();
		}
	}

	public boolean simulate() {
		boolean returnValue = true;

		switch (this.ctype) {
			case 0:
				returnValue = this.simulateAndGate();
				break;
			case 1:
				returnValue = this.simulateOrGate();
				break;
			case 2:
				returnValue = this.simulateNotGate();
				break;
			case 3:
				returnValue = this.simulateInputPin();
				break;
			case 4:
				returnValue = this.simulateOutputPin();
				break;
			case 5:
				returnValue = this.simulateGND();
				break;
			case 6:
				returnValue = this.simulateVCC();
		}

		return returnValue;
	}

	public void setState(boolean digitalValue) {
		this.state = digitalValue;
	}

	private boolean simulateInputPin() {
		return this.state;
	}

	private boolean simulateOutputPin() {
		boolean returnValue;
		if (this.inList.size() < 1) {
			returnValue = true;
		} else {
			if (this.inList.size() == 1) {
				returnValue = ((CircuitComponent) this.inList.get(0)).simulate();
			} else {
				System.out.println("Warning: Multiple wires connected to the output pin.\nOnly simulating first connection. Do not connect outputs");

				returnValue = ((CircuitComponent) this.inList.get(0)).simulate();
			}
		}
		return returnValue;
	}

	private boolean simulateAndGate() {
		boolean returnValue;
		if (this.inList.size() < 1) {
			returnValue = true;
		} else {
			if (this.inList.size() == 1) {
				returnValue = true & ((CircuitComponent) this.inList.get(0)).simulate();
			} else {
				if (this.inList.size() == 2) {
					returnValue = ((CircuitComponent) this.inList.get(0)).simulate() & ((CircuitComponent) this.inList.get(1)).simulate();
				} else {
					System.out.println("Warning: Too many input connections found to AND gate.\nOnly simulating first two connections. Do not connect outputs");

					returnValue = ((CircuitComponent) this.inList.get(0)).simulate() & ((CircuitComponent) this.inList.get(1)).simulate();
				}
			}
		}
		return returnValue;
	}

	private boolean simulateOrGate() {
		boolean returnValue;
		if (this.inList.size() < 2) {
			returnValue = true;
		} else {
			if (this.inList.size() == 2) {
				returnValue = ((CircuitComponent) this.inList.get(0)).simulate() | ((CircuitComponent) this.inList.get(1)).simulate();
			} else {
				System.out.println("Warning: Too many input connections found to OR gate.\nOnly simulating first two connections. Do not connect outputs");

				returnValue = ((CircuitComponent) this.inList.get(0)).simulate() | ((CircuitComponent) this.inList.get(1)).simulate();
			}
		}
		return returnValue;
	}

	private boolean simulateNotGate() {
		boolean returnValue;
		if (this.inList.size() < 1) {
			returnValue = false;
		} else {
			if (this.inList.size() == 1) {
				returnValue = !((CircuitComponent) this.inList.get(0)).simulate();
			} else {
				System.out.println("Warning: Multiple wires connected to the NOT gate.\nOnly simulating first connection. Do not connect outputs");

				returnValue = !((CircuitComponent) this.inList.get(0)).simulate();
			}
		}
		return returnValue;
	}

	private boolean simulateGND() {
		return false;
	}

	private boolean simulateVCC() {
		return true;
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: designTools.CircuitComponent JD-Core Version: 0.6.2
 */
