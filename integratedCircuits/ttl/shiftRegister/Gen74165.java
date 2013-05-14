package integratedCircuits.ttl.shiftRegister;

import integratedCircuits.InputPin;
import integratedCircuits.IntegratedCircuit;
import integratedCircuits.InvalidPinException;
import integratedCircuits.OutputPin;
import integratedCircuits.Pin;
import integratedCircuits.PowerPin;

import java.io.File;

public class Gen74165 extends IntegratedCircuit {
	protected Pin.PinState a = Pin.PinState.LOW;
	protected Pin.PinState b = Pin.PinState.LOW;
	protected Pin.PinState c = Pin.PinState.LOW;
	protected Pin.PinState d = Pin.PinState.LOW;
	protected Pin.PinState e = Pin.PinState.LOW;
	protected Pin.PinState f = Pin.PinState.LOW;
	protected Pin.PinState g = Pin.PinState.LOW;

	public Gen74165() {
		this.initialise();

		this.pins.add(new OutputPin(9, "QH"));
		this.pins.add(new OutputPin(7, "nQH"));
	}

	public Gen74165(int tplh, int tphl) {
		this.initialise();

		this.pins.add(new OutputPin(9, "QH", tplh, tphl));
		this.pins.add(new OutputPin(7, "nQH", tplh, tphl));
	}

	private void initialise() {
		this.description = "Parallel-load 8-Bit Shift Register";
		this.manufacturer = "Generic TTL gate";
		this.diagram = "images" + File.separator + "74165.gif";
		this.wide = false;

		this.pins.add(new InputPin(1, "SH/nLD"));
		this.pins.add(new InputPin(2, "CLK"));
		this.pins.add(new InputPin(3, "E"));
		this.pins.add(new InputPin(4, "F"));
		this.pins.add(new InputPin(5, "G"));
		this.pins.add(new InputPin(6, "H"));
		this.pins.add(new PowerPin(8, "GND"));
		this.pins.add(new InputPin(10, "SER"));
		this.pins.add(new InputPin(11, "A"));
		this.pins.add(new InputPin(12, "B"));
		this.pins.add(new InputPin(13, "C"));
		this.pins.add(new InputPin(14, "D"));
		this.pins.add(new InputPin(15, "CLKINH"));
		this.pins.add(new PowerPin(16, "VCC"));
	}

	private void updateGate(String CLK, String SHLD, String CLKINH, String SER, String A, String B, String C, String D, String E, String F, String G, String H, String QH, String nQH) throws InvalidPinException {
		if (this.isLow(SHLD)) {
			this.a = this.getPinState(A);
			this.b = this.getPinState(B);
			this.c = this.getPinState(C);
			this.d = this.getPinState(D);
			this.e = this.getPinState(E);
			this.f = this.getPinState(F);
			this.g = this.getPinState(G);

			if (this.isHigh(H)) {
				this.setPin(nQH, Pin.PinState.LOW);
				this.setPin(QH, Pin.PinState.HIGH);
			} else {
				this.setPin(nQH, Pin.PinState.HIGH);
				this.setPin(QH, Pin.PinState.LOW);
			}

		} else if (this.isRisingEdge(CLK) && this.isLow(CLKINH)) {
			if (this.g.equals(Pin.PinState.HIGH)) {
				this.setPin(QH, Pin.PinState.HIGH);
				this.setPin(nQH, Pin.PinState.LOW);
			} else {
				this.setPin(QH, Pin.PinState.LOW);
				this.setPin(nQH, Pin.PinState.HIGH);
			}

			this.g = this.f;
			this.f = this.e;
			this.e = this.d;
			this.d = this.c;
			this.c = this.b;
			this.b = this.a;
			this.a = this.getPinState(SER);
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
				this.updateGate("CLK", "SH/nLD", "CLKINH", "SER", "A", "B", "C", "D", "E", "F", "G", "H", "QH", "nQH");
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
