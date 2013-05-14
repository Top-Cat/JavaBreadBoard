package integratedCircuits.cpu.jx_york_ac_uk.j2;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Mux2x8bit extends IntegratedCircuit {
	private int width = 8;

	public Mux2x8bit() {
		this.initialise();

		this.pins.add(new OutputPin(20, "S0"));
		this.pins.add(new OutputPin(21, "S1"));
		this.pins.add(new OutputPin(22, "S2"));
		this.pins.add(new OutputPin(23, "S3"));
		this.pins.add(new OutputPin(24, "S4"));
		this.pins.add(new OutputPin(25, "S5"));
		this.pins.add(new OutputPin(26, "S6"));
		this.pins.add(new OutputPin(27, "S7"));
	}

	public Mux2x8bit(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(20, "S0", tplh, tphl));
		this.pins.add(new OutputPin(21, "S1", tplh, tphl));
		this.pins.add(new OutputPin(22, "S2", tplh, tphl));
		this.pins.add(new OutputPin(23, "S3", tplh, tphl));
		this.pins.add(new OutputPin(24, "S4", tplh, tphl));
		this.pins.add(new OutputPin(25, "S5", tplh, tphl));
		this.pins.add(new OutputPin(26, "S6", tplh, tphl));
		this.pins.add(new OutputPin(27, "S7", tplh, tphl));
	}

	private void initialise() {
		this.description = "Two channel 8bit MUX, ENA active low, ENB active high";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "Mux2x8bit.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "ENA"));
		this.pins.add(new InputPin(2, "ENB"));
		this.pins.add(new InputPin(3, "A0"));
		this.pins.add(new InputPin(4, "A1"));
		this.pins.add(new InputPin(5, "A2"));
		this.pins.add(new InputPin(6, "A3"));
		this.pins.add(new InputPin(7, "A4"));
		this.pins.add(new InputPin(8, "A5"));
		this.pins.add(new InputPin(9, "A6"));
		this.pins.add(new InputPin(10, "A7"));
		this.pins.add(new InputPin(11, "B0"));
		this.pins.add(new InputPin(12, "B1"));
		this.pins.add(new InputPin(13, "B2"));
		this.pins.add(new PowerPin(14, "GND"));
		this.pins.add(new InputPin(15, "B3"));
		this.pins.add(new InputPin(16, "B4"));
		this.pins.add(new InputPin(17, "B5"));
		this.pins.add(new InputPin(18, "B6"));
		this.pins.add(new InputPin(19, "B7"));
		this.pins.add(new PowerPin(28, "VCC"));
	}

	private void updateOutput(String channel) throws InvalidPinException {
		for (int i = 0; i < this.width; i++) {
			String input = channel + i;
			String output = "S" + i;
			if (this.isHigh(input)) {
				this.setPin(output, Pin.PinState.HIGH);
			} else if (this.isLow(input)) {
				this.setPin(output, Pin.PinState.LOW);
			}
		}
	}

	private void updateGate() throws InvalidPinException {
		if (this.isLow("ENA") && this.isHigh("ENB")) {
			this.updateOutput("B");
		} else {
			this.updateOutput("A");
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
