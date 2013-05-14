package integratedCircuits.memory.ram;

import integratedCircuits.InputPin;
import integratedCircuits.InvalidPinException;
import integratedCircuits.Pin;
import integratedCircuits.memory.Memory;

import java.io.File;

public class Ram extends Memory {
	public Ram() {
		this.setup();
	}

	public Ram(int addrBits) {
		this.addrBits = addrBits;
		this.setup();
	}

	private void setup() {
		this.description = "Programmable Memory";
		this.manufacturer = "Generic RAM 256x8";
		this.diagram = "images" + File.separator + "ram.gif";
		this.wide = true;

		this.pins.add(new InputPin(1, "CS"));
		this.pins.add(new InputPin(2, "OE"));
		this.pins.add(new InputPin(3, "WE"));

		this.cntlBits = 3;
		this.initialise();
	}

	@Override
	protected void updateGate() throws InvalidPinException {
		int addr = 0;
		int data = 0;

		boolean addrValid = true;

		for (int i = 0; i < this.addrBits; i++) {
			String name = "A" + i;
			if (this.isHigh(name)) {
				addr += (int) Math.pow(2.0D, i);
			} else if (!this.isLow(name)) {
				addrValid = false;
			}

		}

		for (int i = 0; i < this.dataBits; i++) {
			String name = "D" + i;
			if (this.isHigh(name)) {
				data += (int) Math.pow(2.0D, i);
			} else if (!this.isLow(name)) {
			}

		}

		if (this.isHigh("CS")) {
			if (this.isHigh("OE")) {
				if (addrValid) {
					int memoryLocationData = this.memory.getDatum(addr);
					for (int i = 0; i < this.dataBits; i++) {
						String name = "D" + i;
						if ((memoryLocationData & (int) Math.pow(2.0D, i)) != 0) {
							this.setPin(name, Pin.PinState.HIGH);
						} else {
							this.setPin(name, Pin.PinState.LOW);
						}
					}
				} else {
					for (int i = 0; i < this.dataBits; i++) {
						String name = "D" + i;
						this.setPin(name, Pin.PinState.LOW);
					}
				}
			} else {
				for (int i = 0; i < this.dataBits; i++) {
					String name = "D" + i;
					this.setPin(name, Pin.PinState.HIGH_IMPEDANCE);
				}
				if (this.isHigh("WE")) {
					this.memory.setDatum(addr, data);

				}
			}
		}
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: integratedCircuits.memory.ram.Ram JD-Core Version:
 * 0.6.2
 */
