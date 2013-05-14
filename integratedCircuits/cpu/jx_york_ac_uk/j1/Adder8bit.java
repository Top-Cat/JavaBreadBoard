package integratedCircuits.cpu.jx_york_ac_uk.j1;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Adder8bit extends IntegratedCircuit {
	private int width = 8;

	public Adder8bit() {
		this.initialise();

		this.pins.add(new OutputPin(27, "COUT"));
		this.pins.add(new OutputPin(26, "S7"));
		this.pins.add(new OutputPin(25, "S6"));
		this.pins.add(new OutputPin(24, "S5"));
		this.pins.add(new OutputPin(23, "S4"));
		this.pins.add(new OutputPin(22, "S3"));
		this.pins.add(new OutputPin(21, "S2"));
		this.pins.add(new OutputPin(20, "S1"));
		this.pins.add(new OutputPin(19, "S0"));
	}

	public Adder8bit(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(27, "COUT", tplh, tphl));
		this.pins.add(new OutputPin(26, "S7", tplh, tphl));
		this.pins.add(new OutputPin(25, "S6", tplh, tphl));
		this.pins.add(new OutputPin(24, "S5", tplh, tphl));
		this.pins.add(new OutputPin(23, "S4", tplh, tphl));
		this.pins.add(new OutputPin(22, "S3", tplh, tphl));
		this.pins.add(new OutputPin(21, "S2", tplh, tphl));
		this.pins.add(new OutputPin(20, "S1", tplh, tphl));
		this.pins.add(new OutputPin(19, "S0", tplh, tphl));
	}

	private void initialise() {
		this.description = "Eight bit Adder with Cin and Cout";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "Adder8bit.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "CIN"));
		this.pins.add(new InputPin(2, "A0"));
		this.pins.add(new InputPin(3, "A1"));
		this.pins.add(new InputPin(4, "A2"));
		this.pins.add(new InputPin(5, "A3"));
		this.pins.add(new InputPin(6, "A4"));
		this.pins.add(new InputPin(7, "A5"));
		this.pins.add(new InputPin(8, "A6"));
		this.pins.add(new InputPin(9, "A7"));
		this.pins.add(new InputPin(10, "B0"));
		this.pins.add(new InputPin(11, "B1"));
		this.pins.add(new InputPin(12, "B2"));
		this.pins.add(new InputPin(13, "B3"));
		this.pins.add(new PowerPin(14, "GND"));
		this.pins.add(new InputPin(15, "B4"));
		this.pins.add(new InputPin(16, "B5"));
		this.pins.add(new InputPin(17, "B6"));
		this.pins.add(new InputPin(18, "B7"));
		this.pins.add(new PowerPin(28, "VCC"));
	}

	private void updateGate() throws InvalidPinException {
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
		if (dataAValid && dataBValid) {
			total = dataA + dataB;
			if (this.isHigh("CIN")) {
				total++;
			}
			sum = total & (int) Math.pow(2.0D, this.width) - 1;

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
