package uk.ac.york.jbb.integratedcircuits.ttl.generic;

import java.util.ArrayList;
import java.util.List;

public class LogicTableElement {
	protected List<Boolean> dataBits;
	protected int delay = 0;
	protected int size = 0;

	public LogicTableElement(int size, int data) {
		this.initialise(size, data, 1);
	}

	public LogicTableElement(int size, int data, int delay) {
		this.initialise(size, data, delay);
	}

	protected void initialise(int size, int data, int delay) {
		this.size = size;
		this.delay = delay;
		this.dataBits = new ArrayList<Boolean>(size);

		for (int i = 0; i < size; i++) {
			this.dataBits.add(Boolean.FALSE);
		}

		if (data > (int) Math.pow(2.0D, size) - 1) {
			data = (int) Math.pow(2.0D, size) - 1;
		}

		int mask = 1;
		for (int i = 0; i < size; i++) {
			if ((data & mask) != 0) {
				this.dataBits.set(i, Boolean.TRUE);
			} else {
				this.dataBits.set(i, Boolean.FALSE);
			}
			mask *= 2;
		}
	}

	public void setBit(int offset, Boolean bit) {
		this.dataBits.set(offset, bit);
	}

	public void setData(int data) {
		if (data > (int) Math.pow(2.0D, this.size) - 1) {
			data = (int) Math.pow(2.0D, this.size) - 1;
		}
		int mask = 1;
		for (int i = 0; i < this.dataBits.size(); i++) {
			if ((data & mask) != 0) {
				this.dataBits.set(i, Boolean.TRUE);
			} else {
				this.dataBits.set(i, Boolean.FALSE);
			}
			mask *= 2;
		}
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void clearData() {
		for (int i = 0; i < this.dataBits.size(); i++) {
			this.dataBits.set(i, Boolean.FALSE);
		}
	}

	public Boolean getBit(int offset) {
		return this.dataBits.get(offset);
	}

	public List<Boolean> getData() {
		return this.dataBits;
	}

	public int getDelay() {
		return this.delay;
	}

	public int getSize() {
		return this.size;
	}

	@Override
	public String toString() {
		String message = "";
		for (int i = 0; i < this.dataBits.size(); i++) {
			message.concat("    - " + this.dataBits.get(i) + "\n");
		}

		return message;
	}
}
