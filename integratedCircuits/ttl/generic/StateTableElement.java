package integratedCircuits.ttl.generic;

public class StateTableElement extends LogicTableElement {
	private int nextState = 0;

	public StateTableElement(int size, int data) {
		super(size, data, 1);
		this.nextState = 0;
	}

	public StateTableElement(int size, int data, int delay) {
		super(size, data, delay);
		this.nextState = 0;
	}

	public StateTableElement(int size, int data, int delay, int state) {
		super(size, data, delay);
		this.nextState = state;
	}

	public void setState(int state) {
		this.nextState = state;
	}

	public int getState() {
		return this.nextState;
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: integratedCircuits.ttl.generic.StateTableElement
 * JD-Core Version: 0.6.2
 */
