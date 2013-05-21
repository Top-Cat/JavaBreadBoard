package uk.ac.york.jbb.integratedcircuits;

public class ClockOutputPin extends OutputPin {
	public ClockOutputPin(int pinNumber, String pinName) {
		super(pinNumber, pinName);
	}

	public ClockOutputPin(int pinNumber, String pinName, int pinDelayTplh, int pinDelayTphl) {
		super(pinNumber, pinName);
		this.tplh = pinDelayTplh;
		this.tphl = pinDelayTphl;
	}
}
