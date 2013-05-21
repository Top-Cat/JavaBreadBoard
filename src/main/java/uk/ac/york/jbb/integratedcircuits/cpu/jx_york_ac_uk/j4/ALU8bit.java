package uk.ac.york.jbb.integratedcircuits.cpu.jx_york_ac_uk.j4;


import java.io.File;

import uk.ac.york.jbb.integratedcircuits.InputPin;
import uk.ac.york.jbb.integratedcircuits.IntegratedCircuit;
import uk.ac.york.jbb.integratedcircuits.InvalidPinException;
import uk.ac.york.jbb.integratedcircuits.OutputPin;
import uk.ac.york.jbb.integratedcircuits.Pin;
import uk.ac.york.jbb.integratedcircuits.PowerPin;

public class ALU8bit extends IntegratedCircuit {
	private int width = 8;

	public ALU8bit() {
		this.initialise();

		this.pins.add(new OutputPin(31, "COUT"));
		this.pins.add(new OutputPin(30, "ZERO"));
		this.pins.add(new OutputPin(29, "S7"));
		this.pins.add(new OutputPin(28, "S6"));
		this.pins.add(new OutputPin(27, "S5"));
		this.pins.add(new OutputPin(26, "S4"));
		this.pins.add(new OutputPin(25, "S3"));
		this.pins.add(new OutputPin(24, "S2"));
		this.pins.add(new OutputPin(23, "S1"));
		this.pins.add(new OutputPin(22, "S0"));
	}

	public ALU8bit(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(31, "COUT", tplh, tphl));
		this.pins.add(new OutputPin(30, "ZERO", tplh, tphl));
		this.pins.add(new OutputPin(29, "S7", tplh, tphl));
		this.pins.add(new OutputPin(28, "S6", tplh, tphl));
		this.pins.add(new OutputPin(27, "S5", tplh, tphl));
		this.pins.add(new OutputPin(26, "S4", tplh, tphl));
		this.pins.add(new OutputPin(25, "S3", tplh, tphl));
		this.pins.add(new OutputPin(24, "S2", tplh, tphl));
		this.pins.add(new OutputPin(23, "S1", tplh, tphl));
		this.pins.add(new OutputPin(22, "S0", tplh, tphl));
	}

	private void initialise() {
		this.description = "Eight bit Adder with Cin and Cout";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "ALU8bit.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "X0"));
		this.pins.add(new InputPin(2, "X1"));
		this.pins.add(new InputPin(3, "X2"));
		this.pins.add(new InputPin(4, "X3"));
		this.pins.add(new InputPin(5, "A0"));
		this.pins.add(new InputPin(6, "A1"));
		this.pins.add(new InputPin(7, "A2"));
		this.pins.add(new InputPin(8, "A3"));
		this.pins.add(new InputPin(9, "A4"));
		this.pins.add(new InputPin(10, "A5"));
		this.pins.add(new InputPin(11, "A6"));
		this.pins.add(new InputPin(12, "A7"));
		this.pins.add(new InputPin(13, "B0"));
		this.pins.add(new InputPin(14, "B1"));
		this.pins.add(new InputPin(15, "B2"));
		this.pins.add(new PowerPin(16, "GND"));
		this.pins.add(new InputPin(17, "B3"));
		this.pins.add(new InputPin(18, "B4"));
		this.pins.add(new InputPin(19, "B5"));
		this.pins.add(new InputPin(20, "B6"));
		this.pins.add(new InputPin(21, "B7"));
		this.pins.add(new PowerPin(32, "VCC"));
	}

	private void updateGate() throws InvalidPinException {
		int command = 0;
		int dataA = 0;
		int dataB = 0;
		int total = 0;
		int sum = 0;

		boolean dataAValid = true;
		boolean dataBValid = true;

		for (int i = 0; i < this.width; i++) {
			String name = "A" + i;
			if (this.isHigh(name)) {
				dataA += (int) Math.pow(2.0D, i);
			} else if (!this.isLow(name)) {
				dataAValid = false;
			}

		}

		for (int i = 0; i < this.width; i++) {
			String name = "B" + i;
			if (this.isHigh(name)) {
				dataB += (int) Math.pow(2.0D, i);
			} else if (!this.isLow(name)) {
				dataBValid = false;
			}

		}

		for (int i = 0; i < 4; i++) {
			String name = "X" + i;
			if (this.isHigh(name)) {
				command += (int) Math.pow(2.0D, i);
			} else if (!this.isLow(name)) {
				dataBValid = false;
			}

		}

		switch (command) {
			case 0:
				if (dataAValid && dataBValid) {
					total = dataA + dataB;
					sum = total & (int) Math.pow(2.0D, this.width) - 1;

					System.out.println("ADD " + dataA + " " + dataB + " " + total + "  " + sum);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((sum & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
					if (total > (int) Math.pow(2.0D, this.width) - 1) {
						this.setPin("COUT", Pin.PinState.HIGH);
					} else {
						this.setPin("COUT", Pin.PinState.LOW);
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 1:
				if (dataAValid && dataBValid) {
					total = dataA - dataB;

					sum = total & (int) Math.pow(2.0D, this.width) - 1;

					System.out.println("SUB " + dataA + " " + dataB + " " + total + "  " + sum);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((sum & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
					if (total > (int) Math.pow(2.0D, this.width) - 1) {
						this.setPin("COUT", Pin.PinState.HIGH);
					} else {
						this.setPin("COUT", Pin.PinState.LOW);
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 2:
				if (dataAValid) {
					total = dataA + 1;
					sum = total & (int) Math.pow(2.0D, this.width) - 1;

					System.out.println("INC " + dataA + " " + dataB + " " + total + "  " + sum);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((sum & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
					if (total > (int) Math.pow(2.0D, this.width) - 1) {
						this.setPin("COUT", Pin.PinState.HIGH);
					} else {
						this.setPin("COUT", Pin.PinState.LOW);
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 3:
				if (dataAValid) {
					total = dataA - 1;
					sum = total & (int) Math.pow(2.0D, this.width) - 1;

					System.out.println("DEC " + dataA + " " + dataB + " " + total + "  " + sum);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((sum & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
					if (total > (int) Math.pow(2.0D, this.width) - 1) {
						this.setPin("COUT", Pin.PinState.HIGH);
					} else {
						this.setPin("COUT", Pin.PinState.LOW);
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 4:
				if (dataAValid && dataBValid) {
					total = dataA & dataB;

					System.out.println("AND " + dataA + " " + dataB + " " + total);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((total & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 5:
				if (dataAValid && dataBValid) {
					total = dataA | dataB;

					System.out.println("OR " + dataA + " " + dataB + " " + total);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((total & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 6:
				if (dataAValid && dataBValid) {
					total = dataA ^ dataB;

					System.out.println("XOR " + dataA + " " + dataB + " " + total);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((total & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 7:
				if (dataAValid && dataBValid) {
					total = dataA & dataB ^ 0xFFFFFFFF;

					System.out.println("NAND " + dataA + " " + dataB + " " + total);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((total & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 8:
				if (dataAValid && dataBValid) {
					total = (dataA | dataB) ^ 0xFFFFFFFF;

					System.out.println("NOR " + dataA + " " + dataB + " " + total);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((total & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 9:
				if (dataAValid && dataBValid) {
					total = dataA ^ dataB ^ 0xFFFFFFFF;

					System.out.println("XOR " + dataA + " " + dataB + " " + total);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((total & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 10:
				if (dataAValid) {
					total = dataA ^ 0xFFFFFFFF;

					System.out.println("NOT " + dataA + " " + total);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((total & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 11:
				if (dataAValid) {
					total = dataA >> 1;

					System.out.println("SR0 " + dataA + " " + total);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((total & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
					if ((dataA & 0x1) == 1) {
						this.setPin("COUT", Pin.PinState.HIGH);
					} else {
						this.setPin("COUT", Pin.PinState.LOW);
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 12:
				if (dataAValid) {
					total = dataA >> 1 | 0x80;

					System.out.println("SR1 " + dataA + " " + total);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((total & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
					if ((dataA & 0x1) == 1) {
						this.setPin("COUT", Pin.PinState.HIGH);
					} else {
						this.setPin("COUT", Pin.PinState.LOW);
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 13:
				if (dataAValid) {
					total = dataA << 1;

					System.out.println("SL0 " + dataA + " " + total);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((total & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
					if ((dataA & 0x80) == 1) {
						this.setPin("COUT", Pin.PinState.HIGH);
					} else {
						this.setPin("COUT", Pin.PinState.LOW);
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
			case 14:
				if (dataAValid) {
					total = dataA << 1 | 0x1;

					System.out.println("SL1 " + dataA + " " + total);

					for (int i = 0; i < this.width; i++) {
						String name = "S" + i;
						if ((total & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
					if ((dataA & 0x80) == 1) {
						this.setPin("COUT", Pin.PinState.HIGH);
					} else {
						this.setPin("COUT", Pin.PinState.LOW);
					}
				} else {
					System.out.println("Invalid data");
				}
				break;
		}
	}

	@Override
	public void reset() {}

	@Override
	public void simulate() {
		try {
			if (this.isPowered()) {
				this.updateGate();
			} else {
				for (Pin pin : this.pins) {
					if (this.isPinDriven(pin.getPinName())) {
						this.setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);
					}
				}
			}
		} catch (InvalidPinException e1) {
			System.out.println("OPPS: InvalidPinException");
		}
	}
}
