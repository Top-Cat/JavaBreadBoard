package jBreadBoard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class JBreadBoardActionListener implements ActionListener {
	private JBreadBoard jBreadBoard;
	private int defaultdip = 3;

	public JBreadBoardActionListener(JBreadBoard j) {
		this.jBreadBoard = j;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		Circuit circuit = this.jBreadBoard.getCircuit();

		circuit.pause();

		if (this.jBreadBoard.getMode().equals("drawingwire") && WireDrawer.current != null) {
			if (command != null && command.equals("cancelsegment")) {
				Wire wire = WireDrawer.current;
				if (wire.prev == null) {
					this.jBreadBoard.setMode("wire", "Wiring Mode. Click to Start Wire");
				} else {
					wire = wire.prev;
					WireDrawer.last = wire.prev;
					WireDrawer.current = wire;

					circuit.remove(wire.next);
					wire.next = null;
					circuit.repaint();
				}
			} else {
				this.jBreadBoard.setMode("wire", "Wiring Mode. Click to Start Wire");

				if (command != null && command.equals("delete")) {
					return;
				}
			}
		}

		if (command == null) {
			return;
		}

		if (command.equals("new")) {
			this.jBreadBoard.newcircuit();
		} else if (command.equals("load")) {
			this.jBreadBoard.load();
		} else if (command.equals("insertcircuit")) {
			this.jBreadBoard.insertCircuit();
		} else if (command.equals("save")) {
			this.jBreadBoard.save();
		} else if (command.equals("exit")) {
			this.jBreadBoard.exit();
		} else if (command.equals("select")) {
			this.jBreadBoard.setMode("move");
		} else if (command.equals("delete")) {
			if (!this.jBreadBoard.getMode().equals("sim")) {
				circuit.delete();
			} else {
				this.jBreadBoard.setMessage("You can not remove components during a simulation.");
			}

		} else if (command.equals("breadboard")) {
			BreadBoard b = new BreadBoard(circuit);
			circuit.place(b);
			this.jBreadBoard.setMode("move");
		} else if (command.equals("chip")) {
			if (circuit.BBselect != null) {
				ChipSelector.addChipDialog(this.jBreadBoard.getJFrame(), this.jBreadBoard);
				this.jBreadBoard.setMode("move");
			}
		} else if (command.equals("defaultchip")) {
			if (circuit.BBselect != null) {
				Chip chip = new Chip(circuit, ChipSelector.getDefaultModel());
				circuit.BBselect.add(chip);
				this.jBreadBoard.setMode("move");
			}
		} else if (command.equals("dip")) {
			this.addDip(0);
		} else if (command.equals("dip1")) {
			this.addDip(1);
		} else if (command.equals("dip2")) {
			this.addDip(2);
		} else if (command.equals("dip3")) {
			this.addDip(3);
		} else if (command.equals("dip4")) {
			this.addDip(4);
		} else if (command.equals("led")) {
			if (circuit.BBselect != null) {
				LED l = new LED(circuit);
				circuit.BBselect.add(l);
				this.jBreadBoard.setMode("move");
			}
		} else if (command.equals("rled")) {
			this.addLED(0);
		} else if (command.equals("gled")) {
			this.addLED(2);
		} else if (command.equals("yled")) {
			this.addLED(1);
		} else if (command.equals("wire")) {
			circuit.unhideWires();
			this.jBreadBoard.setMode("wire", "Wiring Mode. Click to Start Wire");
		} else if (command.equals("hidewires")) {
			circuit.hideWires();
		} else if (command.equals("unhidewires")) {
			circuit.unhideWires();
		} else if (command.equals("animate")) {
			circuit.run();
		} else if (command.equals("pause")) {
			circuit.pause();
		} else if (command.equals("step")) {
			circuit.stepSimulation();
		} else if (command.equals("reset")) {
			circuit.resetSimulation();
			this.jBreadBoard.setMode("move");
		} else if (command.equals("addprobe")) {
			if (circuit.BBselect != null) {
				Probe p = new Probe(circuit);
				circuit.BBselect.add(p);
				this.jBreadBoard.setMode("move");
			}
		} else if (command.equals("savetrace")) {
			circuit.updateTrace();
			FileDialogs.save(this.jBreadBoard, circuit.getTrace().getTrace());
		} else if (command.equals("toggledualdata")) {
			Trace.duplicate = !Trace.duplicate;
			circuit.resetSimulation();
		} else if (command.equals("userguide")) {
			HTMLDialog.addHTMLDialog(this.jBreadBoard.getURL("guide.html"), "User Guide");
		} else if (command.equals("loadsave")) {
			HTMLDialog.addHTMLDialog(this.jBreadBoard.getURL("loadsave.html"), "Enabling Loading and Saving");
		} else if (command.equals("about")) {
			JOptionPane.showMessageDialog(this.jBreadBoard.getJFrame(), "Java BreadBoard Simulator version 1.11 (01 July 2010)\n\nOriginally Created by Nicholas Glass 2001-2002\nfor the University of York\nComputer Science Department\nUpdated by:\nStephen Halstead\nRobert Page\nMichael Freeman\nChris Bailey", "About Java BreadBoard Simulator", 1, this.jBreadBoard.getImageIcon("images/iboard.gif"));
		} else if (command.startsWith("tool:")) {
			String s = command.substring(5, command.lastIndexOf("."));
			try {
				Class SS = Class.forName("tools." + s);
				try {
					Thread t = (Thread) SS.newInstance();
					t.start();
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
				} catch (InstantiationException ex) {
					ex.printStackTrace();
				}
			} catch (ClassNotFoundException g) {
			}
		}
	}

	private void addLED(int color) {
		Circuit circuit = this.jBreadBoard.getCircuit();

		LED.setDefaultColor(color);
		if (circuit.BBselect != null) {
			LED l = new LED(circuit);
			circuit.BBselect.add(l);
			this.jBreadBoard.setMode("move");
		}
	}

	private void addDip(int pins) {
		Circuit circuit = this.jBreadBoard.getCircuit();

		if (pins <= 0) {
			pins = this.defaultdip;
		}
		this.defaultdip = pins;
		if (this.jBreadBoard.getCircuit().BBselect != null) {
			circuit.BBselect.add(new Dipswitch(circuit, pins));
			this.jBreadBoard.setMode("move");
		}
	}
}
