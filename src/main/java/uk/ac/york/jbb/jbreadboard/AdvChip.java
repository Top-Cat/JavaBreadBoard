package uk.ac.york.jbb.jbreadboard;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import uk.ac.york.jbb.integratedcircuits.ttl.generic.InvalidStateException;
import uk.ac.york.jbb.jbreadboard.v1_00.ChipModel;
import uk.ac.york.jbb.jbreadboard.v1_10.LoadSave;

public class AdvChip {
	public static String[] getInterfaces(Class<? extends ChipModel> chipModel) {
		Class<?>[] interfaces = chipModel.getInterfaces();
		String[] retVal = new String[interfaces.length];

		for (int i = 0; i < interfaces.length; i++) {
			retVal[i] = interfaces[i].getName();
		}
		return retVal;
	}

	public static Circuit insertCircuit(JFrame jframe, Circuit circuit, int xoffset, int yoffset) {
		String file = FileDialogs.load(jframe);

		if (file != null) {
			StringReader sr = new StringReader(file);
			StreamTokenizer st = new StreamTokenizer(sr);
			st.parseNumbers();
			st.wordChars(47, 47);
			st.wordChars(95, 95);
			try {
				String modelname = "";
				int xpos = 0;
				int ypos = 0;
				boolean wiring = false;

				st.nextToken();
				if (st.sval.equals("Circuit")) {
					while (st.nextToken() != -1) {
						if (st.ttype == -3) {
							if (!wiring && st.sval.equals("Board")) {
								if (st.nextToken() == -3) {
									String classname = st.sval;
									st.nextToken();
									xpos = (int) st.nval;
									st.nextToken();
									ypos = (int) st.nval;

									if (classname.equals("jBreadBoard.BreadBoard")) {
										BreadBoard bb = new BreadBoard(circuit);
										circuit.add(bb, Circuit.BoardLayer);
										bb.setLocation(xpos + xoffset, ypos + yoffset);
										circuit.select(bb);
									}
								}
							} else if (!wiring && st.sval.equals("Probe")) {
								if (st.nextToken() == 34) {
									String label = st.sval;
									st.nextToken();
									xpos = (int) st.nval;
									st.nextToken();
									ypos = (int) st.nval;

									Probe probe = new Probe(circuit);
									probe.setLabel(label);
									probe.setLocation(xpos, ypos);

									circuit.BBselect.add(probe);
									probe.updateConnections();
								}
							} else if (!wiring && st.sval.equals("Device")) {
								if (st.nextToken() == -3) {
									String classname = st.sval;

									if (st.nextToken() == -3) {
										modelname = st.sval;
										st.nextToken();
										st.nextToken();
										st.nextToken();
									}

									xpos = (int) st.nval;
									st.nextToken();
									ypos = (int) st.nval;

									if (classname.equals("jBreadBoard.Chip")) {
										ChipModel model = JBreadBoard.getChipModel(modelname);

										if (JBreadBoard.implementsInterface(model, "uk.ac.york.jbb.jbreadboard.v1_10.LoadSave")) {
											st.nextToken();
											String filename = st.sval;
											try {
												((LoadSave) model).initialiseState(filename);
											} catch (InvalidStateException e1) {
											}
										}
										Chip chip = new Chip(circuit, model);
										circuit.BBselect.add(chip);

										chip.setLocation(xpos, ypos);

										chip.updateConnections();
									} else if (classname.equals("jBreadBoard.LED")) {
										st.nextToken();
										int color = (int) st.nval;

										LED l = new LED(circuit, color);
										circuit.BBselect.add(l);
										l.setLocation(xpos, ypos);
										l.updateConnections();
									} else if (classname.equals("jBreadBoard.Dipswitch")) {
										st.nextToken();
										int pins = (int) st.nval;

										Dipswitch d = new Dipswitch(circuit, pins);
										circuit.BBselect.add(d);
										d.setLocation(xpos, ypos);
										d.updateConnections();
									}
								}
							} else if (st.sval.equals("Wire")) {
								wiring = true;

								st.nextToken();
								int red = (int) st.nval;
								st.nextToken();
								int green = (int) st.nval;
								st.nextToken();
								int blue = (int) st.nval;

								Color color = new Color(red, green, blue);

								st.nextToken();
								int x = xoffset + (int) st.nval;
								st.nextToken();
								int y = yoffset + (int) st.nval;
								st.nextToken();
								int x2 = xoffset + (int) st.nval;
								st.nextToken();
								int y2 = yoffset + (int) st.nval;

								Wire wire = circuit.drawWire(color, x, y, x2, y2);
								boolean end = false;

								while (!end) {
									x = x2;
									y = x2;
									if (st.nextToken() == -2) {
										x2 = xoffset + (int) st.nval;
										if (st.nextToken() == -2) {
											y2 = yoffset + (int) st.nval;
											wire = circuit.extendWire(wire, color, x, y, x2, y2);
										} else {
											end = true;
										}
									} else {
										end = true;
									}

								}

								st.pushBack();

								wire.fixate();
							}
						}
					}

				}

				int maxx = 0;
				int maxy = 0;
				Component[] components = circuit.getComponentsInLayer(Circuit.BoardLayer);

				for (Component component : components) {
					int x2 = component.getX() + component.getWidth();
					int y2 = component.getY() + component.getHeight();
					if (x2 > maxx) {
						maxx = x2;
					}
					if (y2 > maxy) {
						maxy = y2;
					}
				}

				circuit.setPreferredSize(new Dimension(maxx, maxy));
				circuit.revalidate();

				sr.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(jframe, "Error Reading Circuit File.\n" + e.getMessage(), "Error AdvChip.001", 0);
			}

		}

		return circuit;
	}

	static int lowestBoard(Circuit circuit) {
		int ypos = 0;

		StringReader sr = new StringReader(circuit.toString());
		StreamTokenizer st = new StreamTokenizer(sr);
		st.parseNumbers();
		try {
			while (st.nextToken() != -1) {
				if (st.ttype == -3 && st.sval.equals("Board") && st.nextToken() == -3) {
					st.nextToken();
					st.nextToken();
					int current = (int) st.nval;
					if (current > ypos) {
						ypos = current;
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Um.. something bad happened AdvChip.lowestBoard.. input:" + circuit);
		}
		return ypos + 168;
	}
}
