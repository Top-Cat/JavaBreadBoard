package integratedCircuits.cpu.jx_york_ac_uk.j3;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Stack8x8bit extends IntegratedCircuit {
	private int width = 8;
	private int depth = 8;
	private int[] stack = new int[this.depth];
	private int data_in = 0;

	public Stack8x8bit() {
		this.initialise();

		this.pins.add(new OutputPin(30, "ZERO"));
		this.pins.add(new OutputPin(29, "QB7"));
		this.pins.add(new OutputPin(28, "QB6"));
		this.pins.add(new OutputPin(27, "QB5"));
		this.pins.add(new OutputPin(26, "QB4"));
		this.pins.add(new OutputPin(25, "QB3"));
		this.pins.add(new OutputPin(24, "QB2"));
		this.pins.add(new OutputPin(23, "QB1"));
		this.pins.add(new OutputPin(22, "QB0"));
		this.pins.add(new OutputPin(21, "QA7"));
		this.pins.add(new OutputPin(20, "QA6"));
		this.pins.add(new OutputPin(19, "QA5"));
		this.pins.add(new OutputPin(18, "QA4"));
		this.pins.add(new OutputPin(17, "QA3"));
		this.pins.add(new OutputPin(15, "QA2"));
		this.pins.add(new OutputPin(14, "QA1"));
		this.pins.add(new OutputPin(13, "QA0"));
	}

	public Stack8x8bit(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(30, "ZERO", tplh, tphl));
		this.pins.add(new OutputPin(29, "QB7", tplh, tphl));
		this.pins.add(new OutputPin(28, "QB6", tplh, tphl));
		this.pins.add(new OutputPin(27, "QB5", tplh, tphl));
		this.pins.add(new OutputPin(26, "QB4", tplh, tphl));
		this.pins.add(new OutputPin(25, "QB3", tplh, tphl));
		this.pins.add(new OutputPin(24, "QB2", tplh, tphl));
		this.pins.add(new OutputPin(23, "QB1", tplh, tphl));
		this.pins.add(new OutputPin(22, "QB0", tplh, tphl));
		this.pins.add(new OutputPin(21, "QA7", tplh, tphl));
		this.pins.add(new OutputPin(20, "QA6", tplh, tphl));
		this.pins.add(new OutputPin(19, "QA5", tplh, tphl));
		this.pins.add(new OutputPin(18, "QA4", tplh, tphl));
		this.pins.add(new OutputPin(17, "QA3", tplh, tphl));
		this.pins.add(new OutputPin(15, "QA2", tplh, tphl));
		this.pins.add(new OutputPin(14, "QA1", tplh, tphl));
		this.pins.add(new OutputPin(13, "QA0", tplh, tphl));
	}

	private void initialise() {
		this.description = "Eight bit,  eight entry statck with En, Rst and Zero";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "Stack8x8bit.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "CLK"));
		this.pins.add(new InputPin(2, "WR"));
		this.pins.add(new InputPin(3, "PSH"));
		this.pins.add(new InputPin(4, "POP"));
		this.pins.add(new InputPin(5, "D0"));
		this.pins.add(new InputPin(6, "D1"));
		this.pins.add(new InputPin(7, "D2"));
		this.pins.add(new InputPin(8, "D3"));
		this.pins.add(new InputPin(9, "D4"));
		this.pins.add(new InputPin(10, "D5"));
		this.pins.add(new InputPin(11, "D6"));
		this.pins.add(new InputPin(12, "D7"));
		this.pins.add(new PowerPin(16, "GND"));
		this.pins.add(new InputPin(31, "RST"));
		this.pins.add(new PowerPin(32, "VCC"));
	}

	private void updateGate() throws InvalidPinException {
		if (this.isHigh("RST")) {
			for (int i = 0; i < this.width; i++) {
				String output = "Q" + i;
				this.setPin(output, Pin.PinState.LOW);
			}
			for (int i = 0; i < this.depth; i++) {
				this.stack[i] = 0;
			}

		} else if (this.isRisingEdge("CLK")) {
			boolean zero = true;
			this.data_in = 0;

			for (int i = 0; i < this.width; i++) {
				String input = "D" + i;
				if (this.isHigh(input)) {
					this.data_in += (int) Math.pow(2.0D, i);
				}

			}

			if (this.isHigh("PSH")) {
				for (int i = 0; i < this.depth - 1; i++) {
					this.stack[this.depth - 1 - i] = this.stack[this.depth - 2 - i];
				}

			} else if (this.isHigh("POP")) {
				for (int i = 0; i < this.depth - 1; i++) {
					this.stack[i] = this.stack[i + 1];
				}
				this.stack[this.depth - 1] = 0;
			}

			if (this.isHigh("WR")) {
				this.stack[0] = this.data_in;
			}

			for (int i = 0; i < this.width; i++) {
				String output = "QA" + i;
				if (((int) Math.pow(2.0D, i) & this.stack[0]) != 0) {
					this.setPin(output, Pin.PinState.HIGH);
					zero = false;
				} else {
					this.setPin(output, Pin.PinState.LOW);
				}
			}

			for (int i = 0; i < this.width; i++) {
				String output = "QB" + i;
				if (((int) Math.pow(2.0D, i) & this.stack[1]) != 0) {
					this.setPin(output, Pin.PinState.HIGH);
				} else {
					this.setPin(output, Pin.PinState.LOW);
				}
			}

			if (zero) {
				this.setPin("ZERO", Pin.PinState.HIGH);
			} else {
				this.setPin("ZERO", Pin.PinState.LOW);
			}
			System.out.print("Stack ");
			if (this.isHigh("PSH")) {
				System.out.print("PSH ");
			}
			if (this.isHigh("POP")) {
				System.out.print("POP ");
			}
			if (this.isHigh("WR")) {
				System.out.print("WR ");
			}
			System.out.print(": ");

			for (int i = 0; i < this.depth; i++) {
				System.out.print(this.stack[i] + " ");
			}
			System.out.println(" : ");
		}
	}

	@Override
	public void reset() {
		try {
			for (Pin pin : this.pins) {
				if (this.isPinDriven(pin.getPinName())) {
					this.setPin(pin.getPinName(), Pin.PinState.LOW);
				}
			}
			for (int i = 0; i < this.depth; i++) {
				this.stack[i] = 0;
			}
		} catch (InvalidPinException e1) {
			System.out.println("OPPS: InvalidPinException " + e1);
		}
	}

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
			System.out.println("OPPS: InvalidPinException " + e1);
		}
	}
}
