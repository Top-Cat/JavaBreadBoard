package integratedCircuits;

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

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: integratedCircuits.ClockOutputPin JD-Core Version:
 * 0.6.2
 */
