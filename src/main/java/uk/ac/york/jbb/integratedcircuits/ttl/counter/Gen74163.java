package uk.ac.york.jbb.integratedcircuits.ttl.counter;


import java.io.File;

import uk.ac.york.jbb.integratedcircuits.InputPin;
import uk.ac.york.jbb.integratedcircuits.IntegratedCircuit;
import uk.ac.york.jbb.integratedcircuits.InvalidPinException;
import uk.ac.york.jbb.integratedcircuits.OutputPin;
import uk.ac.york.jbb.integratedcircuits.Pin;
import uk.ac.york.jbb.integratedcircuits.PowerPin;

public class Gen74163 extends IntegratedCircuit {
	protected int count = 0;

	public Gen74163() {
		this.initialise();

		this.pins.add(new OutputPin(15, "RCO"));
		this.pins.add(new OutputPin(14, "QA"));
		this.pins.add(new OutputPin(13, "QB"));
		this.pins.add(new OutputPin(12, "QC"));
		this.pins.add(new OutputPin(11, "QD"));
	}

	public Gen74163(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(15, "RCO", tplh, tphl));
		this.pins.add(new OutputPin(14, "QA", tplh, tphl));
		this.pins.add(new OutputPin(13, "QB", tplh, tphl));
		this.pins.add(new OutputPin(12, "QC", tplh, tphl));
		this.pins.add(new OutputPin(11, "QD", tplh, tphl));
	}

	private void initialise() {
		this.description = "Synchronous 4-Bit Counter";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "74163.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "nCLR"));
		this.pins.add(new InputPin(2, "CLK"));
		this.pins.add(new InputPin(3, "A"));
		this.pins.add(new InputPin(4, "B"));
		this.pins.add(new InputPin(5, "C"));
		this.pins.add(new InputPin(6, "D"));
		this.pins.add(new InputPin(7, "ENP"));
		this.pins.add(new PowerPin(8, "GND"));
		this.pins.add(new InputPin(9, "nLOAD"));
		this.pins.add(new InputPin(10, "ENT"));
		this.pins.add(new PowerPin(16, "VCC"));
	}

	private void updateGate(String CLK, String CLR, String ENP, String ENT, String LOAD, String A, String B, String C, String D, String QA, String QB, String QC, String QD, String RCO) throws InvalidPinException {
		if (this.isRisingEdge(CLK)) {
			if (this.isLow(CLR)) {
				this.setPin(QA, Pin.PinState.LOW);
				this.setPin(QB, Pin.PinState.LOW);
				this.setPin(QC, Pin.PinState.LOW);
				this.setPin(QD, Pin.PinState.LOW);
				this.setPin(RCO, Pin.PinState.LOW);
			} else if (this.isLow(LOAD)) {
				this.setPin(QA, this.getPinState(A));
				this.setPin(QB, this.getPinState(B));
				this.setPin(QC, this.getPinState(C));
				this.setPin(QD, this.getPinState(D));

				this.count = 0;
				if (this.isHigh(A)) {
					this.count += 1;
				}
				if (this.isHigh(B)) {
					this.count += 2;
				}
				if (this.isHigh(C)) {
					this.count += 4;
				}
				if (this.isHigh(D)) {
					this.count += 8;
				}
			} else if (this.isRisingEdge(CLK) && this.isHigh(ENP)) {
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

		if (this.count == 15 && this.isHigh(ENT)) {
			this.setPin(RCO, Pin.PinState.HIGH);
		} else {
			this.setPin(RCO, Pin.PinState.LOW);
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
				this.updateGate("CLK", "nCLR", "ENP", "ENT", "nLOAD", "A", "B", "C", "D", "QA", "QB", "QC", "QD", "RCO");
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
