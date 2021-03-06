package uk.ac.york.jbb.integratedcircuits.ttl.generic;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import uk.ac.york.jbb.integratedcircuits.InputPin;
import uk.ac.york.jbb.integratedcircuits.IntegratedCircuit;
import uk.ac.york.jbb.integratedcircuits.InvalidPinException;
import uk.ac.york.jbb.integratedcircuits.NotConnectedPin;
import uk.ac.york.jbb.integratedcircuits.OutputPin;
import uk.ac.york.jbb.integratedcircuits.Pin;
import uk.ac.york.jbb.integratedcircuits.PowerPin;
import uk.ac.york.jbb.jbreadboard.v1_10.LoadSave;

public class StateMachine extends IntegratedCircuit implements LoadSave {
	private String chipText = "";
	private String chipDescription = "";
	private String chipManufacturer = "";
	private String chipFilename = "";

	private String chipExtension = "chp";
	private boolean chipInstantiateBlank = false;

	private int numberOfAddressBits = 0;
	private int numberOfDataBits = 0;
	private int numberOfPins = 0;
	private int numberOfStates = 0;
	private int initialState = 0;

	private int presentState = 0;
	private int nextState = 0;

	private Boolean chipWide = Boolean.valueOf(false);
	private List<String> chipInputs = new ArrayList<String>();
	private List<String> chipOutputs = new ArrayList<String>();
	private List<List<StateTableElement>> stateTable;
	private Boolean mode = Boolean.valueOf(true);
	private int lookUpTableRows = 0;
	private int pinOffset = 0;

	public StateMachine() {
		this.description = "General purpose state machine chip configured via .chp file";
		this.manufacturer = "Generic";
		this.diagram = "images" + File.separator + "statemachine.gif";
		this.wide = false;
	}

	public StateMachine(String filename) throws InvalidStateException {
		this.chipFilename = filename;
		this.initialise();
	}

	@Override
	public String getFileName() {
		return '"' + this.chipFilename + '"';
	}

	@Override
	public void setFileName(String filename) {
		this.chipFilename = filename;
	}

	@Override
	public String getFileExtension() {
		return this.chipExtension;
	}

	@Override
	public boolean instantiateBlank() {
		return this.chipInstantiateBlank;
	}

	@Override
	public void initialiseState(String filename) throws InvalidStateException {
		if (filename.isEmpty() || filename.equalsIgnoreCase("null")) {
			System.out.println("NULL File Name");
		} else {
			this.chipFilename = filename;
			this.initialise();
		}
	}

	private void initialise() throws InvalidStateException {
		String newFilename = this.updateConfigFilePath(this.chipFilename);
		Scanner fileScanner = null;
		Scanner lineScanner = null;
		try {
			FileInputStream istream = new FileInputStream(newFilename);
			fileScanner = new Scanner(istream);

			while (this.mode.booleanValue() && fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();

				lineScanner = new Scanner(line);
				lineScanner.useDelimiter("=");

				if (lineScanner.findInLine("Chip Text=") != null) {
					try {
						this.chipText = lineScanner.next();
					} catch (NoSuchElementException e1) {
					}
				} else if (lineScanner.findInLine("Description=") != null) {
					try {
						this.chipDescription = lineScanner.next();
					} catch (NoSuchElementException e1) {
					}
				} else if (lineScanner.findInLine("Manufacturer=") != null) {
					try {
						this.chipManufacturer = lineScanner.next();
					} catch (NoSuchElementException e1) {
					}
				} else if (lineScanner.findInLine("Wide Chip=") != null) {
					try {
						String chipWideText = lineScanner.next();
						if (chipWideText.equals("False")) {
							this.chipWide = Boolean.valueOf(false);
						} else {
							this.chipWide = Boolean.valueOf(true);
						}
					} catch (NoSuchElementException e1) {
					}
				} else if (lineScanner.findInLine("Clocked Chip=") != null) {
					try {
						String chipClockedText = lineScanner.next();
						if (chipClockedText.equals("False")) {
							throw new InvalidStateException("Chip is not clocked exit");
						}
					} catch (NoSuchElementException e1) {
					}
				} else {
					if (lineScanner.findInLine("Input Pins=") != null) {
						lineScanner.useDelimiter(";");
						while (lineScanner.hasNext()) {
							try {
								this.chipInputs.add(lineScanner.next());
							} catch (NoSuchElementException e1) {
							}
						}
					}
					if (lineScanner.findInLine("Output Pins=") != null) {
						lineScanner.useDelimiter(";");
						while (lineScanner.hasNext()) {
							try {
								this.chipOutputs.add(lineScanner.next());
							} catch (NoSuchElementException e1) {
							}
						}
						if (this.chipOutputs.size() == 0) {
							throw new InvalidStateException("Chip has no outputs exit");
						}
					} else if (lineScanner.findInLine("Number of States=") != null) {
						try {
							this.numberOfStates = Integer.parseInt(lineScanner.next(), 10);
						} catch (NoSuchElementException e1) {
						}
					} else if (lineScanner.findInLine("Initial State=") != null) {
						try {
							this.initialState = Integer.parseInt(lineScanner.next(), 10);
							this.mode = Boolean.valueOf(false);
						} catch (NoSuchElementException e1) {
						}
					}
				}
				lineScanner.close();
			}
			if (this.mode.booleanValue()) {
				throw new InvalidStateException("No Initial State detected exit");
			}
			this.numberOfAddressBits = this.chipInputs.size();
			this.numberOfDataBits = this.chipOutputs.size();
			this.stateTable = new ArrayList<List<StateTableElement>>(this.numberOfStates);

			for (int j = 0; j < this.numberOfStates; j++) {
				this.stateTable.add(new ArrayList<StateTableElement>((int) Math.pow(2.0D, this.numberOfAddressBits)));
				for (int i = 0; i < (int) Math.pow(2.0D, this.numberOfAddressBits); i++) {
					this.stateTable.get(j).add(new StateTableElement(this.numberOfDataBits, 10));
				}
			}

			while (!this.mode.booleanValue() && fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();

				lineScanner = new Scanner(line);
				try {
					if (lineScanner.hasNext(Pattern.compile("^[01].*"))) {
						lineScanner.useDelimiter(";");
						int address = Integer.parseInt(lineScanner.next(), 2);
						int presentState = Integer.parseInt(lineScanner.next(), 10);
						int data = Integer.parseInt(lineScanner.next(), 2);
						int nextState = Integer.parseInt(lineScanner.next(), 10);
						int delay = Integer.parseInt(lineScanner.next(), 10);

						this.stateTable.get(presentState).get(address).setData(data);
						this.stateTable.get(presentState).get(address).setDelay(delay);
						this.stateTable.get(presentState).get(address).setState(nextState);

						this.lookUpTableRows += 1;
					}

				} catch (NoSuchElementException e1) {
				} finally {
					lineScanner.close();
				}

			}

		} catch (FileNotFoundException e1) {
			System.out.print(e1);
			throw new InvalidStateException("No File found exit");
		} finally {
			if (fileScanner != null) {
				fileScanner.close();
			}
			if (lineScanner != null) {
				lineScanner.close();
			}
		}

		if (this.lookUpTableRows == 0) {
			throw new InvalidStateException("No truth table data exit");
		}
		this.numberOfPins = 1;

		this.pins.add(new InputPin(this.numberOfPins, "CLK"));
		this.numberOfPins += 1;

		this.pins.add(new InputPin(this.numberOfPins, "CLR"));
		this.numberOfPins += 1;

		this.pinOffset = this.numberOfPins;
		for (int i = 0; i < this.numberOfAddressBits; i++) {
			this.pins.add(new InputPin(i + this.pinOffset, this.chipInputs.get(i)));

			this.numberOfPins += 1;
		}

		if (this.numberOfDataBits - (this.numberOfAddressBits + 2) > 0) {
			this.pinOffset = this.numberOfPins;
			for (int i = 0; i < this.numberOfDataBits - (this.numberOfAddressBits + 2); i++) {
				this.pins.add(new NotConnectedPin(i + this.pinOffset, "NC"));

				this.numberOfPins += 1;
			}

		}

		this.pins.add(new PowerPin(this.numberOfPins, "GND"));
		this.numberOfPins += 1;

		this.pinOffset = this.numberOfPins;
		for (int i = 0; i < this.numberOfDataBits; i++) {
			this.pins.add(new OutputPin(i + this.pinOffset, this.chipOutputs.get(i), 0));

			this.numberOfPins += 1;
		}

		if (this.numberOfDataBits - (this.numberOfAddressBits + 2) < 0) {
			this.pinOffset = this.numberOfPins;
			for (int i = 0; i < Math.abs(this.numberOfDataBits - (this.numberOfAddressBits + 2)); i++) {
				this.pins.add(new NotConnectedPin(i + this.pinOffset, "NC"));

				this.numberOfPins += 1;
			}

		}

		this.pins.add(new PowerPin(this.numberOfPins, "VCC"));

		this.name = this.chipText;

		if (this.chipDescription.isEmpty()) {
			this.description = "State Machine \n";
		} else {
			this.description = this.chipDescription + "\n";
		}
		this.description = this.description.concat(this.chipInputs.toString() + "\n");
		this.description = this.description.concat(this.chipOutputs.toString());

		this.manufacturer = this.chipManufacturer;
		this.diagram = "images" + File.separator + "statemachine.gif";
		this.wide = this.chipWide.booleanValue();
	}

	private void updateGate() throws InvalidPinException {
		int address = 0;
		int offset = 1;
		for (int i = 0; i < this.chipInputs.size(); i++) {
			if (this.isHigh(this.chipInputs.get(i))) {
				address += offset;
			}
			offset *= 2;
		}

		if (this.isRisingEdge("CLK")) {
			if (this.isHigh("CLR")) {
				this.presentState = this.initialState;
			}
			this.nextState = this.stateTable.get(this.presentState).get(address).getState();

			List<Boolean> data = this.stateTable.get(this.presentState).get(address).getData();
			int delay = this.stateTable.get(this.presentState).get(address).getDelay();

			for (int i = 0; i < this.chipOutputs.size(); i++) {
				if (data.get(i).booleanValue()) {
					this.setPin(this.chipOutputs.get(i), Pin.PinState.HIGH, delay);
				} else {
					this.setPin(this.chipOutputs.get(i), Pin.PinState.LOW, delay);
				}
			}
			this.presentState = this.nextState;
		}
	}

	@Override
	public void reset() {
		this.presentState = this.initialState;
	}

	@Override
	public void simulate() {
		try {
			if (this.isPowered()) {
				this.updateGate();
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
