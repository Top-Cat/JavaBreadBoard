package uk.ac.york.jbb.integratedcircuits;

public class OutputPin extends Pin {
	protected int tplh = 1;
	protected int tphl = 1;
	protected Pin.PinDriver pinDriver = Pin.PinDriver.TOTEM_POLE;

	public OutputPin(int pinNumber, String pinName, Pin.PinDriver pinDriver, int pinDelayTplh, int pinDelayTphl) {
		super(pinNumber, pinName);
		this.pinDriver = pinDriver;
		this.tplh = pinDelayTplh;
		this.tphl = pinDelayTphl;
	}

	public OutputPin(int pinNumber, String pinName, Pin.PinDriver pinDriver, int pinDelay) {
		this(pinNumber, pinName, pinDriver, pinDelay, pinDelay);
	}

	public OutputPin(int pinNumber, String pinName, int pinDelayTplh, int pinDelayTphl) {
		this(pinNumber, pinName, Pin.PinDriver.TOTEM_POLE, pinDelayTplh, pinDelayTphl);
	}

	public OutputPin(int pinNumber, String pinName, int pinDelay) {
		this(pinNumber, pinName, Pin.PinDriver.TOTEM_POLE, pinDelay, pinDelay);
	}

	public OutputPin(int pinNumber, String pinName) {
		this(pinNumber, pinName, Pin.PinDriver.TOTEM_POLE, 1, 1);
	}

	public Pin.PinDriver getPinDriver() {
		return this.pinDriver;
	}

	public int getPinDelayTphl() {
		return this.tphl;
	}

	public int getPinDelayTplh() {
		return this.tplh;
	}

	public void setPinDriver(Pin.PinDriver pinDriver) {
		this.pinDriver = pinDriver;
	}

	public void setPinDelayTphl(int tphl) {
		this.tphl = tphl;
	}

	public void setPinDelayTplh(int tplh) {
		this.tplh = tplh;
	}

	public void setPinDelay(int tplh, int tphl) {
		this.tplh = tplh;
		this.tphl = tphl;
	}
}
