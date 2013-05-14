package integratedCircuits.ttl.flipFlop;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Gen74273 extends IntegratedCircuit {
	public Gen74273() {
		this.initialise();

		this.pins.add(new OutputPin(2, "1Q"));
		this.pins.add(new OutputPin(5, "2Q"));
		this.pins.add(new OutputPin(6, "3Q"));
		this.pins.add(new OutputPin(9, "4Q"));
		this.pins.add(new OutputPin(12, "5Q"));
		this.pins.add(new OutputPin(15, "6Q"));
		this.pins.add(new OutputPin(16, "7Q"));
		this.pins.add(new OutputPin(19, "8Q"));
	}

	public Gen74273(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(2, "1Q", tplh, tphl));
		this.pins.add(new OutputPin(5, "2Q", tplh, tphl));
		this.pins.add(new OutputPin(6, "3Q", tplh, tphl));
		this.pins.add(new OutputPin(9, "4Q", tplh, tphl));
		this.pins.add(new OutputPin(12, "5Q", tplh, tphl));
		this.pins.add(new OutputPin(15, "6Q", tplh, tphl));
		this.pins.add(new OutputPin(16, "7Q", tplh, tphl));
		this.pins.add(new OutputPin(19, "8Q", tplh, tphl));
	}

	private void initialise() {
		this.description = "Octal D-Type Flip-Flops with clear";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "74273.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "CLR"));
		this.pins.add(new InputPin(3, "1D"));
		this.pins.add(new InputPin(4, "2D"));
		this.pins.add(new InputPin(7, "3D"));
		this.pins.add(new InputPin(8, "4D"));
		this.pins.add(new PowerPin(10, "GND"));
		this.pins.add(new InputPin(11, "CLK"));
		this.pins.add(new InputPin(13, "5D"));
		this.pins.add(new InputPin(14, "6D"));
		this.pins.add(new InputPin(17, "7D"));
		this.pins.add(new InputPin(18, "8D"));
		this.pins.add(new PowerPin(20, "VCC"));
	}

	private void updateGate(String CLK, String CLR, String D, String Q) throws InvalidPinException {
		if (this.isLow(CLR)) {
			this.setPin(Q, Pin.PinState.LOW);
		} else if (this.isRisingEdge(CLK)) {
			if (this.isLow(D)) {
				this.setPin(Q, Pin.PinState.LOW);
			} else {
				this.setPin(Q, Pin.PinState.HIGH);

			}
		}
	}

	@Override
	public void reset() {
		try {
			for (Pin pin : this.pins) {
				if (this.isPinDriven(pin.getPinName())) {
					this.setPin(pin.getPinName(), Pin.PinState.NOT_CONNECTED);

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
				this.updateGate("CLK", "CLR", "1D", "1Q");
				this.updateGate("CLK", "CLR", "2D", "2Q");
				this.updateGate("CLK", "CLR", "3D", "3Q");
				this.updateGate("CLK", "CLR", "4D", "4Q");
				this.updateGate("CLK", "CLR", "5D", "5Q");
				this.updateGate("CLK", "CLR", "6D", "6Q");
				this.updateGate("CLK", "CLR", "7D", "7Q");
				this.updateGate("CLK", "CLR", "8D", "8Q");
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
 * \classes\ Qualified Name: integratedCircuits.ttl.flipFlop.Gen74273 JD-Core
 * Version: 0.6.2
 */
