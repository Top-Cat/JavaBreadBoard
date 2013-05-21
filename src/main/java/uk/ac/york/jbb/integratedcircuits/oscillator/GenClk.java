package uk.ac.york.jbb.integratedcircuits.oscillator;


import java.io.File;

import uk.ac.york.jbb.integratedcircuits.ClockOutputPin;
import uk.ac.york.jbb.integratedcircuits.IntegratedCircuit;
import uk.ac.york.jbb.integratedcircuits.InvalidPinException;
import uk.ac.york.jbb.integratedcircuits.NotConnectedPin;
import uk.ac.york.jbb.integratedcircuits.Pin;
import uk.ac.york.jbb.integratedcircuits.PowerPin;

public class GenClk extends IntegratedCircuit {
	protected int period = 1000000000;

	public GenClk() {
		this.initialise();

		this.pins.add(new ClockOutputPin(5, "C"));
	}

	public GenClk(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new ClockOutputPin(5, "C", tplh, tphl));
	}

	private void initialise() {
		this.description = "Clock oscilator";
		this.manufacturer = "Generic clock";
		this.diagram = "images" + File.separator + "clk.gif";
		this.wide = false;

		this.pins.add(new NotConnectedPin(1, "NC"));
		this.pins.add(new NotConnectedPin(2, "NC"));
		this.pins.add(new NotConnectedPin(3, "NC"));
		this.pins.add(new PowerPin(4, "GND"));
		this.pins.add(new NotConnectedPin(6, "NC"));
		this.pins.add(new NotConnectedPin(7, "NC"));
		this.pins.add(new PowerPin(8, "VCC"));
	}

	private void updateGate(String C) throws InvalidPinException {
		if (this.isHigh(C)) {
			this.setPin(C, Pin.PinState.LOW, this.period / 2);
		} else {
			this.setPin(C, Pin.PinState.HIGH, this.period / 2);

		}
	}

	@Override
	public void reset() {
		try {
			for (Pin pin : this.pins) {
				if (this.isPinDriven(pin.getPinName())) {
					this.setPin(pin.getPinName(), Pin.PinState.LOW, 0);

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
				this.updateGate("C");
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
