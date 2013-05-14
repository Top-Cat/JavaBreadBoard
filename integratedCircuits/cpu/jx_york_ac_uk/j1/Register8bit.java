package integratedCircuits.cpu.jx_york_ac_uk.j1;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Register8bit extends IntegratedCircuit {
	private int width = 8;

	public Register8bit() {
		this.initialise();

		this.pins.add(new OutputPin(20, "ZERO"));
		this.pins.add(new OutputPin(19, "Q0"));
		this.pins.add(new OutputPin(18, "Q1"));
		this.pins.add(new OutputPin(17, "Q2"));
		this.pins.add(new OutputPin(16, "Q3"));
		this.pins.add(new OutputPin(15, "Q4"));
		this.pins.add(new OutputPin(14, "Q5"));
		this.pins.add(new OutputPin(13, "Q6"));
		this.pins.add(new OutputPin(12, "Q7"));
	}

	public Register8bit(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(20, "ZERO", tplh, tphl));
		this.pins.add(new OutputPin(19, "Q0", tplh, tphl));
		this.pins.add(new OutputPin(18, "Q1", tplh, tphl));
		this.pins.add(new OutputPin(17, "Q2", tplh, tphl));
		this.pins.add(new OutputPin(16, "Q3", tplh, tphl));
		this.pins.add(new OutputPin(15, "Q4", tplh, tphl));
		this.pins.add(new OutputPin(14, "Q5", tplh, tphl));
		this.pins.add(new OutputPin(13, "Q6", tplh, tphl));
		this.pins.add(new OutputPin(12, "Q7", tplh, tphl));
	}

	private void initialise() {
		this.description = "Eight bit Register with En, Clr and Zero";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "Register8bit.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "CLK"));
		this.pins.add(new InputPin(2, "EN"));
		this.pins.add(new InputPin(3, "D0"));
		this.pins.add(new InputPin(4, "D1"));
		this.pins.add(new InputPin(5, "D2"));
		this.pins.add(new InputPin(6, "D3"));
		this.pins.add(new InputPin(7, "D4"));
		this.pins.add(new InputPin(8, "D5"));
		this.pins.add(new InputPin(9, "D6"));
		this.pins.add(new InputPin(10, "D7"));
		this.pins.add(new PowerPin(11, "GND"));
		this.pins.add(new InputPin(21, "CLR"));
		this.pins.add(new PowerPin(22, "VCC"));
	}

	private void updateGate() throws InvalidPinException {
		if (this.isHigh("CLR")) {
			for (int i = 0; i < this.width; i++) {
				String output = "Q" + i;
				this.setPin(output, Pin.PinState.LOW);
			}

		} else if (this.isRisingEdge("CLK") && this.isHigh("EN")) {
			boolean zero = true;

			for (int i = 0; i < this.width; i++) {
				String input = "D" + i;
				String output = "Q" + i;
				if (this.isHigh(input)) {
					this.setPin(output, Pin.PinState.HIGH);
					zero = false;
				} else if (this.isLow(input)) {
					this.setPin(output, Pin.PinState.LOW);
				} else {
					System.out.println("Input not HIGH or LOW");
				}
			}

			if (zero) {
				this.setPin("ZERO", Pin.PinState.HIGH);
			} else {
				this.setPin("ZERO", Pin.PinState.LOW);
			}
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
		} catch (InvalidPinException e1) {
			System.out.println("OPPS: InvalidPinException");
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
			System.out.println("OPPS: InvalidPinException");
		}
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name:
 * integratedCircuits.cpu.jx_york_ac_uk.j1.Register8bit JD-Core Version: 0.6.2
 */
