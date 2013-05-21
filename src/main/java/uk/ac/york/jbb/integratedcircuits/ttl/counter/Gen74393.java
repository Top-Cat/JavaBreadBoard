package uk.ac.york.jbb.integratedcircuits.ttl.counter;


import java.io.File;

import uk.ac.york.jbb.integratedcircuits.InputPin;
import uk.ac.york.jbb.integratedcircuits.IntegratedCircuit;
import uk.ac.york.jbb.integratedcircuits.InvalidPinException;
import uk.ac.york.jbb.integratedcircuits.OutputPin;
import uk.ac.york.jbb.integratedcircuits.Pin;
import uk.ac.york.jbb.integratedcircuits.PowerPin;

public class Gen74393 extends IntegratedCircuit {
	protected int count = 0;

	public Gen74393() {
		this.initialise();

		this.pins.add(new OutputPin(3, "1QA"));
		this.pins.add(new OutputPin(4, "1QB"));
		this.pins.add(new OutputPin(5, "1QC"));
		this.pins.add(new OutputPin(6, "1QD"));
		this.pins.add(new OutputPin(11, "2QA"));
		this.pins.add(new OutputPin(10, "2QB"));
		this.pins.add(new OutputPin(9, "2QC"));
		this.pins.add(new OutputPin(8, "2QD"));
	}

	public Gen74393(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(3, "1QA", tplh, tphl));
		this.pins.add(new OutputPin(4, "1QB", tplh, tphl));
		this.pins.add(new OutputPin(5, "1QC", tplh, tphl));
		this.pins.add(new OutputPin(6, "1QD", tplh, tphl));
		this.pins.add(new OutputPin(11, "2QA", tplh, tphl));
		this.pins.add(new OutputPin(10, "2QB", tplh, tphl));
		this.pins.add(new OutputPin(9, "2QC", tplh, tphl));
		this.pins.add(new OutputPin(8, "2QD", tplh, tphl));
	}

	private void initialise() {
		this.description = "Dual 4-Bit Binary Counter";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "74393.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "1A"));
		this.pins.add(new InputPin(2, "1CLR"));
		this.pins.add(new PowerPin(7, "GND"));
		this.pins.add(new InputPin(13, "2A"));
		this.pins.add(new InputPin(12, "2CLR"));
		this.pins.add(new PowerPin(14, "VCC"));
	}

	private void updateGate(String A, String CLR, String QA, String QB, String QC, String QD) throws InvalidPinException {
		if (this.isHigh(CLR)) {
			this.setPin(QA, Pin.PinState.LOW);
			this.setPin(QB, Pin.PinState.LOW);
			this.setPin(QC, Pin.PinState.LOW);
			this.setPin(QD, Pin.PinState.LOW);
		} else if (this.isFallingEdge(A)) {
			if (this.count == 16) {
				this.count = 0;
			} else {
				this.count += 1;
			}
			switch (this.count) {
				case 0:
					this.setPin(QA, Pin.PinState.LOW);
					this.setPin(QB, Pin.PinState.LOW);
					this.setPin(QC, Pin.PinState.LOW);
					this.setPin(QD, Pin.PinState.LOW);
					break;
				case 1:
					this.setPin(QA, Pin.PinState.HIGH);
					this.setPin(QB, Pin.PinState.LOW);
					this.setPin(QC, Pin.PinState.LOW);
					this.setPin(QD, Pin.PinState.LOW);
					break;
				case 2:
					this.setPin(QA, Pin.PinState.LOW);
					this.setPin(QB, Pin.PinState.HIGH);
					this.setPin(QC, Pin.PinState.LOW);
					this.setPin(QD, Pin.PinState.LOW);
					break;
				case 3:
					this.setPin(QA, Pin.PinState.HIGH);
					this.setPin(QB, Pin.PinState.HIGH);
					this.setPin(QC, Pin.PinState.LOW);
					this.setPin(QD, Pin.PinState.LOW);
					break;
				case 4:
					this.setPin(QA, Pin.PinState.LOW);
					this.setPin(QB, Pin.PinState.LOW);
					this.setPin(QC, Pin.PinState.HIGH);
					this.setPin(QD, Pin.PinState.LOW);
					break;
				case 5:
					this.setPin(QA, Pin.PinState.HIGH);
					this.setPin(QB, Pin.PinState.LOW);
					this.setPin(QC, Pin.PinState.HIGH);
					this.setPin(QD, Pin.PinState.LOW);
					break;
				case 6:
					this.setPin(QA, Pin.PinState.LOW);
					this.setPin(QB, Pin.PinState.HIGH);
					this.setPin(QC, Pin.PinState.HIGH);
					this.setPin(QD, Pin.PinState.LOW);
					break;
				case 7:
					this.setPin(QA, Pin.PinState.HIGH);
					this.setPin(QB, Pin.PinState.HIGH);
					this.setPin(QC, Pin.PinState.HIGH);
					this.setPin(QD, Pin.PinState.LOW);
					break;
				case 8:
					this.setPin(QA, Pin.PinState.LOW);
					this.setPin(QB, Pin.PinState.LOW);
					this.setPin(QC, Pin.PinState.LOW);
					this.setPin(QD, Pin.PinState.HIGH);
					break;
				case 9:
					this.setPin(QA, Pin.PinState.HIGH);
					this.setPin(QB, Pin.PinState.LOW);
					this.setPin(QC, Pin.PinState.LOW);
					this.setPin(QD, Pin.PinState.HIGH);
					break;
				case 10:
					this.setPin(QA, Pin.PinState.LOW);
					this.setPin(QB, Pin.PinState.HIGH);
					this.setPin(QC, Pin.PinState.LOW);
					this.setPin(QD, Pin.PinState.HIGH);
					break;
				case 11:
					this.setPin(QA, Pin.PinState.HIGH);
					this.setPin(QB, Pin.PinState.HIGH);
					this.setPin(QC, Pin.PinState.LOW);
					this.setPin(QD, Pin.PinState.HIGH);
					break;
				case 12:
					this.setPin(QA, Pin.PinState.LOW);
					this.setPin(QB, Pin.PinState.LOW);
					this.setPin(QC, Pin.PinState.HIGH);
					this.setPin(QD, Pin.PinState.HIGH);
					break;
				case 13:
					this.setPin(QA, Pin.PinState.HIGH);
					this.setPin(QB, Pin.PinState.LOW);
					this.setPin(QC, Pin.PinState.HIGH);
					this.setPin(QD, Pin.PinState.HIGH);
					break;
				case 14:
					this.setPin(QA, Pin.PinState.LOW);
					this.setPin(QB, Pin.PinState.HIGH);
					this.setPin(QC, Pin.PinState.HIGH);
					this.setPin(QD, Pin.PinState.HIGH);
					break;
				case 15:
					this.setPin(QA, Pin.PinState.HIGH);
					this.setPin(QB, Pin.PinState.HIGH);
					this.setPin(QC, Pin.PinState.HIGH);
					this.setPin(QD, Pin.PinState.HIGH);
					break;
				default:
					this.setPin(QA, Pin.PinState.LOW);
					this.setPin(QB, Pin.PinState.LOW);
					this.setPin(QC, Pin.PinState.LOW);
					this.setPin(QD, Pin.PinState.LOW);
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
				this.updateGate("1A", "1CLR", "1QA", "1QB", "1QC", "1QD");
				this.updateGate("2A", "2CLR", "2QA", "2QB", "2QC", "2QD");
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
