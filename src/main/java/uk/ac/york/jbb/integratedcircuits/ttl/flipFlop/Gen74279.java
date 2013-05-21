package uk.ac.york.jbb.integratedcircuits.ttl.flipFlop;


import java.io.File;

import uk.ac.york.jbb.integratedcircuits.InputPin;
import uk.ac.york.jbb.integratedcircuits.IntegratedCircuit;
import uk.ac.york.jbb.integratedcircuits.InvalidPinException;
import uk.ac.york.jbb.integratedcircuits.OutputPin;
import uk.ac.york.jbb.integratedcircuits.Pin;
import uk.ac.york.jbb.integratedcircuits.PowerPin;

public class Gen74279 extends IntegratedCircuit {
	public Gen74279() {
		this.initialise();

		this.pins.add(new OutputPin(4, "1Q"));
		this.pins.add(new OutputPin(7, "2Q"));
		this.pins.add(new OutputPin(9, "3Q"));
		this.pins.add(new OutputPin(13, "4Q"));
	}

	public Gen74279(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(4, "1Q", tplh, tphl));
		this.pins.add(new OutputPin(7, "2Q", tplh, tphl));
		this.pins.add(new OutputPin(9, "3Q", tplh, tphl));
		this.pins.add(new OutputPin(13, "4Q", tplh, tphl));
	}

	private void initialise() {
		this.description = "Quadruple SR Latch";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "74279.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "1nR"));
		this.pins.add(new InputPin(2, "1nS1"));
		this.pins.add(new InputPin(3, "1nS2"));
		this.pins.add(new InputPin(5, "2nR"));
		this.pins.add(new InputPin(6, "2nS"));
		this.pins.add(new PowerPin(8, "GND"));
		this.pins.add(new InputPin(10, "3nR"));
		this.pins.add(new InputPin(11, "3nS1"));
		this.pins.add(new InputPin(12, "3nS2"));
		this.pins.add(new InputPin(14, "4nR"));
		this.pins.add(new InputPin(15, "4nS"));
		this.pins.add(new PowerPin(26, "VCC"));
	}

	private void updateGate(String S, String R, String Q) throws InvalidPinException {
		if (this.isLow(R) && this.isHigh(S)) {
			this.setPin(Q, Pin.PinState.LOW);
		} else if (this.isLow(S) && this.isHigh(R)) {
			this.setPin(Q, Pin.PinState.HIGH);

		}
	}

	private void updateGate(String S1, String S2, String R, String Q) throws InvalidPinException {
		if (this.isLow(R) && this.isHigh(S1) && this.isHigh(S2)) {
			this.setPin(Q, Pin.PinState.LOW);
		} else if ((this.isLow(S1) || this.isLow(S2)) && this.isHigh(R)) {
			this.setPin(Q, Pin.PinState.HIGH);

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
				this.updateGate("1nS1", "1nS2", "1nR", "1Q");
				this.updateGate("2nS", "2nR", "2Q");
				this.updateGate("3nS1", "3nS2", "3nR", "3Q");
				this.updateGate("4nS", "4nR", "4Q");
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
