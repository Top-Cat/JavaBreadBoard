package uk.ac.york.jbb.integratedcircuits.ttl.flipFlop;


import java.io.File;

import uk.ac.york.jbb.integratedcircuits.InputPin;
import uk.ac.york.jbb.integratedcircuits.IntegratedCircuit;
import uk.ac.york.jbb.integratedcircuits.InvalidPinException;
import uk.ac.york.jbb.integratedcircuits.OutputPin;
import uk.ac.york.jbb.integratedcircuits.Pin;
import uk.ac.york.jbb.integratedcircuits.PowerPin;

public class Gen7473 extends IntegratedCircuit {
	public Gen7473() {
		this.initialise();

		this.pins.add(new OutputPin(12, "1Q"));
		this.pins.add(new OutputPin(13, "1NQ"));
		this.pins.add(new OutputPin(9, "2Q"));
		this.pins.add(new OutputPin(8, "2NQ"));
	}

	public Gen7473(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(12, "1Q", tplh, tphl));
		this.pins.add(new OutputPin(13, "1NQ", tplh, tphl));
		this.pins.add(new OutputPin(9, "2Q", tplh, tphl));
		this.pins.add(new OutputPin(8, "2NQ", tplh, tphl));
	}

	private void initialise() {
		this.description = "Dual J-K Flip-Flops with clear";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "7473.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "1CLK"));
		this.pins.add(new InputPin(2, "1CLR"));
		this.pins.add(new InputPin(3, "1K"));
		this.pins.add(new PowerPin(4, "VCC"));
		this.pins.add(new InputPin(5, "2CLK"));
		this.pins.add(new InputPin(6, "2CLR"));
		this.pins.add(new InputPin(7, "2J"));
		this.pins.add(new InputPin(10, "2K"));
		this.pins.add(new PowerPin(11, "GND"));
		this.pins.add(new InputPin(14, "1J"));
	}

	private void updateGate(String CLK, String CLR, String J, String K, String Q, String NQ) throws InvalidPinException {
		if (this.isLow(CLR)) {
			this.setPin(Q, Pin.PinState.LOW);
			this.setPin(NQ, Pin.PinState.HIGH);
		} else if (this.isRisingEdge(CLK)) {
			if (this.isLow(J) && this.isHigh(K)) {
				this.setPin(Q, Pin.PinState.LOW);
				this.setPin(NQ, Pin.PinState.HIGH);
			} else if (this.isHigh(J) && this.isLow(K)) {
				this.setPin(Q, Pin.PinState.HIGH);
				this.setPin(NQ, Pin.PinState.LOW);
			} else if (this.isHigh(J) && this.isHigh(K)) {
				if (this.isStateHigh(Q)) {
					this.setPin(Q, Pin.PinState.LOW);
					this.setPin(NQ, Pin.PinState.HIGH);
				} else {
					this.setPin(Q, Pin.PinState.HIGH);
					this.setPin(NQ, Pin.PinState.LOW);
				}
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
				this.updateGate("1CLK", "1CLR", "1J", "1K", "1Q", "1NQ");
				this.updateGate("2CLK", "2CLR", "2J", "2K", "2Q", "2NQ");
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
