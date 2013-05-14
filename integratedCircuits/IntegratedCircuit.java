/*      */package integratedCircuits;

/*      */
/*      */import jBreadBoard.ChipAccess;
import jBreadBoard.v1_00.ChipModel;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/*      */
/*      */
/*      */
/*      */
/*      */
/*      */
/*      */

/*      */
/*      */public abstract class IntegratedCircuit
/*      */implements ChipModel
/*      */{
	/*      */protected ChipAccess chip;
	protected String name = this.getClass().getSimpleName();
	/*      */protected String description;
	/*      */protected String manufacturer;
	/*      */protected String diagram;
	protected boolean wide = false;
	/*      */
	protected List<Pin> pins = new ArrayList();

	/*      */
	/*      */@Override
	public void setAccess(ChipAccess chip)
	/*      */{
		this.chip = chip;
		/*      */}

	/*      */
	/*      */@Override
	public abstract void reset();

	/*      */
	/*      */@Override
	public abstract void simulate();

	/*      */
	/*      */@Override
	public String getChipText()
	/*      */{
		return this.name;
		/*      */}

	/*      */
	/*      */@Override
	public String getDescription()
	/*      */{
		/* 105 */return this.description;
		/*      */}

	/*      */
	/*      */@Override
	public String getManufacturer()
	/*      */{
		/* 113 */return this.manufacturer;
		/*      */}

	/*      */
	/*      */public int getNumberOfPackagePins()
	/*      */{
		/* 121 */return this.pins.size();
		/*      */}

	/*      */
	/*      */@Override
	public String getDiagram()
	/*      */{
		/* 133 */return this.diagram;
		/*      */}

	/*      */
	/*      */@Override
	public boolean isWide()
	/*      */{
		/* 141 */return this.wide;
		/*      */}

	/*      */
	/*      */protected Pin getPin(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 159 */for (Pin pin : this.pins) {
			/* 160 */if (pin.getPinNumber() == pinNumber) {
				/* 161 */return pin;
				/*      */
			}
		}
		/* 163 */throw new InvalidPinException("OPPS: Pin number does not exist");
		/*      */}

	/*      */
	/*      */protected Pin getPin(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 173 */for (Pin pin : this.pins) {
			/* 174 */if (pin.getPinName().equals(pinName)) {
				/* 175 */return pin;
				/*      */
			}
		}
		/* 177 */throw new InvalidPinException("OPPS: Pin name does not exist");
		/*      */}

	/*      */
	/*      */protected Pin[] getPins(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 187 */Vector match = new Vector();
		/* 188 */for (Pin pin : this.pins) {
			/* 189 */if (pin.getPinName().equals(pinName)) {
				/* 190 */match.add(pin);
				/*      */
			}
		}
		/* 192 */Pin[] matchedPins = new Pin[match.size()];
		/* 193 */for (int i = 0; i < matchedPins.length; i++) {
			/* 194 */matchedPins[i] = (Pin) match.get(i);
			/*      */}
		/* 196 */return matchedPins;
		/*      */}

	/*      */
	/*      */public String getPinName(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 210 */for (Pin pin : this.pins) {
			/* 211 */if (pin.getPinNumber() == pinNumber) {
				/* 212 */return pin.getPinName();
				/*      */
			}
		}
		/* 214 */throw new InvalidPinException("OPPS: Pin number does not exist");
		/*      */}

	/*      */
	/*      */public int getPinNumber(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 224 */for (Pin pin : this.pins) {
			/* 225 */if (pin.getPinName().equals(pinName)) {
				/* 226 */return pin.getPinNumber();
				/*      */
			}
		}
		/* 228 */throw new InvalidPinException("OPPS: Pin name does not exist");
		/*      */}

	/*      */
	/*      */public boolean isPinInput(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 242 */Pin pin = this.getPin(pinNumber);
		/* 243 */if (pin instanceof InputPin) {
			/* 244 */return true;
			/*      */}
		/* 246 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinInput(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 256 */Pin pin = this.getPin(pinName);
		/* 257 */if (pin instanceof InputPin) {
			/* 258 */return true;
			/*      */}
		/* 260 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinOutput(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 274 */Pin pin = this.getPin(pinNumber);
		/* 275 */if (pin instanceof OutputPin) {
			/* 276 */return true;
			/*      */}
		/* 278 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinOutput(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 288 */Pin pin = this.getPin(pinName);
		/* 289 */if (pin instanceof OutputPin) {
			/* 290 */return true;
			/*      */}
		/* 292 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinInputOutput(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 306 */Pin pin = this.getPin(pinNumber);
		/* 307 */if (pin instanceof InputOutputPin) {
			/* 308 */return true;
			/*      */}
		/* 310 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinInputOutput(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 320 */Pin pin = this.getPin(pinName);
		/* 321 */if (pin instanceof InputOutputPin) {
			/* 322 */return true;
			/*      */}
		/* 324 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinPower(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 338 */Pin pin = this.getPin(pinNumber);
		/* 339 */if (pin instanceof PowerPin) {
			/* 340 */return true;
			/*      */}
		/* 342 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinPower(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 352 */Pin pin = this.getPin(pinName);
		/* 353 */if (pin instanceof PowerPin) {
			/* 354 */return true;
			/*      */}
		/* 356 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinNotConnected(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 370 */Pin pin = this.getPin(pinNumber);
		/* 371 */if (pin instanceof NotConnectedPin) {
			/* 372 */return true;
			/*      */}
		/* 374 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinNotConnected(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 384 */Pin pin = this.getPin(pinName);
		/* 385 */if (pin instanceof NotConnectedPin) {
			/* 386 */return true;
			/*      */}
		/* 388 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinOpenCollector(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 402 */Pin pin = this.getPin(pinNumber);
		/* 403 */if (pin instanceof OutputPin) {
			/* 404 */if (((OutputPin) pin).getPinDriver() == Pin.PinDriver.OPEN_COLLECTOR) {
				/* 405 */return true;
				/*      */}
			/* 407 */return false;
			/*      */}
		/*      */
		/* 410 */if (pin instanceof InputOutputPin) {
			/* 411 */if (((InputOutputPin) pin).getPinDriver() == Pin.PinDriver.OPEN_COLLECTOR) {
				/* 412 */return true;
				/*      */}
			/* 414 */return false;
			/*      */}
		/*      */
		/* 417 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinOpenCollector(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 428 */Pin pin = this.getPin(pinName);
		/* 429 */if (pin instanceof OutputPin) {
			/* 430 */if (((OutputPin) pin).getPinDriver() == Pin.PinDriver.OPEN_COLLECTOR) {
				/* 431 */return true;
				/*      */}
			/* 433 */return false;
			/*      */}
		/*      */
		/* 436 */if (pin instanceof InputOutputPin) {
			/* 437 */if (((InputOutputPin) pin).getPinDriver() == Pin.PinDriver.OPEN_COLLECTOR) {
				/* 438 */return true;
				/*      */}
			/* 440 */return false;
			/*      */}
		/*      */
		/* 443 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinTotemPole(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 458 */Pin pin = this.getPin(pinNumber);
		/* 459 */if (pin instanceof OutputPin) {
			/* 460 */if (((OutputPin) pin).getPinDriver() == Pin.PinDriver.TOTEM_POLE) {
				/* 461 */return true;
				/*      */}
			/* 463 */return false;
			/*      */}
		/*      */
		/* 466 */if (pin instanceof InputOutputPin) {
			/* 467 */if (((InputOutputPin) pin).getPinDriver() == Pin.PinDriver.TOTEM_POLE) {
				/* 468 */return true;
				/*      */}
			/* 470 */return false;
			/*      */}
		/*      */
		/* 473 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinTotemPole(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 484 */Pin pin = this.getPin(pinName);
		/* 485 */if (pin instanceof OutputPin) {
			/* 486 */if (((OutputPin) pin).getPinDriver() == Pin.PinDriver.TOTEM_POLE) {
				/* 487 */return true;
				/*      */}
			/* 489 */return false;
			/*      */}
		/*      */
		/* 492 */if (pin instanceof InputOutputPin) {
			/* 493 */if (((InputOutputPin) pin).getPinDriver() == Pin.PinDriver.TOTEM_POLE) {
				/* 494 */return true;
				/*      */}
			/* 496 */return false;
			/*      */}
		/*      */
		/* 499 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinTriState(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 514 */Pin pin = this.getPin(pinNumber);
		/* 515 */if (pin instanceof OutputPin) {
			/* 516 */if (((OutputPin) pin).getPinDriver() == Pin.PinDriver.TRI_STATE) {
				/* 517 */return true;
				/*      */}
			/* 519 */return false;
			/*      */}
		/*      */
		/* 522 */if (pin instanceof InputOutputPin) {
			/* 523 */if (((InputOutputPin) pin).getPinDriver() == Pin.PinDriver.TRI_STATE) {
				/* 524 */return true;
				/*      */}
			/* 526 */return false;
			/*      */}
		/*      */
		/* 529 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinTriState(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 540 */Pin pin = this.getPin(pinName);
		/* 541 */if (pin instanceof OutputPin) {
			/* 542 */if (((OutputPin) pin).getPinDriver() == Pin.PinDriver.TRI_STATE) {
				/* 543 */return true;
				/*      */}
			/* 545 */return false;
			/*      */}
		/*      */
		/* 548 */if (pin instanceof InputOutputPin) {
			/* 549 */if (((InputOutputPin) pin).getPinDriver() == Pin.PinDriver.TRI_STATE) {
				/* 550 */return true;
				/*      */}
			/* 552 */return false;
			/*      */}
		/*      */
		/* 555 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinDriven(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 570 */Pin pin = this.getPin(pinNumber);
		/* 571 */if (pin instanceof OutputPin || pin instanceof InputOutputPin || pin instanceof ClockOutputPin) {
			/* 572 */return true;
			/*      */}
		/* 574 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinDriven(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 584 */Pin pin = this.getPin(pinName);
		/* 585 */if (pin instanceof OutputPin || pin instanceof InputOutputPin || pin instanceof ClockOutputPin) {
			/* 586 */return true;
			/*      */}
		/* 588 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinClockOutput(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 602 */Pin pin = this.getPin(pinNumber);
		/* 603 */if (pin instanceof ClockOutputPin) {
			/* 604 */return true;
			/*      */}
		/* 606 */return false;
		/*      */}

	/*      */
	/*      */public boolean isPinClockOutput(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 616 */Pin pin = this.getPin(pinName);
		/* 617 */if (pin instanceof ClockOutputPin) {
			/* 618 */return true;
			/*      */}
		/* 620 */return false;
		/*      */}

	/*      */
	/*      */public int getPinIndex(int pinNumber)
	/*      */throws InvalidPinException
	/*      */{
		/* 639 */for (int i = 0; i < this.pins.size(); i++) {
			/* 640 */if (this.pins.get(i).getPinNumber() == pinNumber) {
				/* 641 */return i;
				/*      */
			}
		}
		/* 643 */throw new InvalidPinException("OPPS: Pin number does not exist");
		/*      */}

	/*      */
	/*      */public int getPinIndex(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 653 */for (int i = 0; i < this.pins.size(); i++) {
			/* 654 */if (this.pins.get(i).getPinName().equals(pinName)) {
				/* 655 */return i;
				/*      */
			}
		}
		/* 657 */throw new InvalidPinException("OPPS: Pin name does not exist");
		/*      */}

	/*      */
	/*      */public String getPinIndexName(int pinIndex)
	/*      */throws InvalidPinException
	/*      */{
		/* 671 */if (pinIndex >= 0 && pinIndex < this.pins.size()) {
			/* 672 */return this.pins.get(pinIndex).getPinName();
		}
		/* 673 */throw new InvalidPinException("OPPS: Pin index does not exist");
		/*      */}

	/*      */
	/*      */public int getPinIndexNumber(int pinIndex)
	/*      */throws InvalidPinException
	/*      */{
		/* 683 */if (pinIndex >= 0 && pinIndex < this.pins.size()) {
			/* 684 */return this.pins.get(pinIndex).getPinNumber();
		}
		/* 685 */throw new InvalidPinException("OPPS: Pin index does not exist");
		/*      */}

	/*      */
	/*      */public boolean isPinIndexInput(int pinIndex)
	/*      */throws InvalidPinException
	/*      */{
		/* 699 */if (pinIndex >= 0 && pinIndex < this.pins.size()) {
			/* 700 */Pin pin = this.pins.get(pinIndex);
			/* 701 */if (pin instanceof InputPin) {
				/* 702 */return true;
				/*      */}
			/* 704 */return false;
			/*      */}
		/* 706 */throw new InvalidPinException("OPPS: isPinIndexInput( int pinIndex " + pinIndex + " )");
		/*      */}

	/*      */
	/*      */public boolean isPinIndexOutput(int pinIndex)
	/*      */throws InvalidPinException
	/*      */{
		/* 720 */if (pinIndex >= 0 && pinIndex < this.pins.size()) {
			/* 721 */Pin pin = this.pins.get(pinIndex);
			/* 722 */if (pin instanceof OutputPin) {
				/* 723 */return true;
				/*      */}
			/* 725 */return false;
			/*      */}
		/* 727 */throw new InvalidPinException("OPPS: isPinIndexOutput( int pinIndex " + pinIndex + " )");
		/*      */}

	/*      */
	/*      */public boolean isPinIndexInputOutput(int pinIndex)
	/*      */throws InvalidPinException
	/*      */{
		/* 741 */if (pinIndex >= 0 && pinIndex < this.pins.size()) {
			/* 742 */Pin pin = this.pins.get(pinIndex);
			/* 743 */if (pin instanceof InputOutputPin) {
				/* 744 */return true;
				/*      */}
			/* 746 */return false;
			/*      */}
		/* 748 */throw new InvalidPinException("OPPS: isPinIndexInputOutput( int pinIndex " + pinIndex + " )");
		/*      */}

	/*      */
	/*      */public boolean isPinIndexPower(int pinIndex)
	/*      */throws InvalidPinException
	/*      */{
		/* 762 */if (pinIndex >= 0 && pinIndex < this.pins.size()) {
			/* 763 */Pin pin = this.pins.get(pinIndex);
			/* 764 */if (pin instanceof PowerPin) {
				/* 765 */return true;
				/*      */}
			/* 767 */return false;
			/*      */}
		/* 769 */throw new InvalidPinException("OPPS: isPinIndexPower( int pinIndex " + pinIndex + " )");
		/*      */}

	/*      */
	/*      */public boolean isPinIndexNotConnected(int pinIndex)
	/*      */throws InvalidPinException
	/*      */{
		/* 783 */if (pinIndex >= 0 && pinIndex < this.pins.size()) {
			/* 784 */Pin pin = this.pins.get(pinIndex);
			/* 785 */if (pin instanceof NotConnectedPin) {
				/* 786 */return true;
				/*      */}
			/* 788 */return false;
			/*      */}
		/* 790 */throw new InvalidPinException("OPPS: isPinIndexNotConnected( int pinIndex " + pinIndex + " )");
		/*      */}

	/*      */
	/*      */public boolean isPinIndexOpenCollector(int pinIndex)
	/*      */throws InvalidPinException
	/*      */{
		/* 804 */if (pinIndex >= 0 && pinIndex < this.pins.size()) {
			/* 805 */Pin pin = this.pins.get(pinIndex);
			/*      */
			/* 807 */if (pin instanceof OutputPin) {
				/* 808 */if (((OutputPin) pin).getPinDriver() == Pin.PinDriver.OPEN_COLLECTOR) {
					/* 809 */return true;
					/*      */}
				/* 811 */return false;
				/*      */}
			/*      */
			/* 814 */if (pin instanceof InputOutputPin) {
				/* 815 */if (((InputOutputPin) pin).getPinDriver() == Pin.PinDriver.OPEN_COLLECTOR) {
					/* 816 */return true;
					/*      */}
				/* 818 */return false;
				/*      */}
			/*      */
			/* 821 */return false;
			/*      */}
		/*      */
		/* 824 */throw new InvalidPinException("OPPS: isPinIndexOpenCollector( int pinIndex " + pinIndex + " )");
		/*      */}

	/*      */
	/*      */public boolean isPinIndexTotemPole(int pinIndex)
	/*      */throws InvalidPinException
	/*      */{
		/* 838 */if (pinIndex >= 0 && pinIndex < this.pins.size()) {
			/* 839 */Pin pin = this.pins.get(pinIndex);
			/*      */
			/* 841 */if (pin instanceof OutputPin) {
				/* 842 */if (((OutputPin) pin).getPinDriver() == Pin.PinDriver.TOTEM_POLE) {
					/* 843 */return true;
					/*      */}
				/* 845 */return false;
				/*      */}
			/*      */
			/* 848 */if (pin instanceof InputOutputPin) {
				/* 849 */if (((InputOutputPin) pin).getPinDriver() == Pin.PinDriver.TOTEM_POLE) {
					/* 850 */return true;
					/*      */}
				/* 852 */return false;
				/*      */}
			/*      */
			/* 855 */return false;
			/*      */}
		/*      */
		/* 858 */throw new InvalidPinException("OPPS: isPinIndexTotemPole( int pinIndex " + pinIndex + " )");
		/*      */}

	/*      */
	/*      */public boolean isPinIndexTriState(int pinIndex)
	/*      */throws InvalidPinException
	/*      */{
		/* 872 */if (pinIndex >= 0 && pinIndex < this.pins.size()) {
			/* 873 */Pin pin = this.pins.get(pinIndex);
			/*      */
			/* 875 */if (pin instanceof OutputPin) {
				/* 876 */if (((OutputPin) pin).getPinDriver() == Pin.PinDriver.TRI_STATE) {
					/* 877 */return true;
					/*      */}
				/* 879 */return false;
				/*      */}
			/*      */
			/* 882 */if (pin instanceof InputOutputPin) {
				/* 883 */if (((InputOutputPin) pin).getPinDriver() == Pin.PinDriver.TRI_STATE) {
					/* 884 */return true;
					/*      */}
				/* 886 */return false;
				/*      */}
			/*      */
			/* 889 */return false;
			/*      */}
		/*      */
		/* 892 */throw new InvalidPinException("OPPS: isPinIndexTriState( int pinIndex " + pinIndex + " )");
		/*      */}

	/*      */
	/*      */public boolean isPinIndexDriven(int pinIndex)
	/*      */throws InvalidPinException
	/*      */{
		/* 906 */if (pinIndex >= 0 && pinIndex < this.pins.size()) {
			/* 907 */Pin pin = this.pins.get(pinIndex);
			/*      */
			/* 909 */if (pin instanceof OutputPin || pin instanceof InputOutputPin || pin instanceof ClockOutputPin) {
				/* 910 */return true;
				/*      */}
			/* 912 */return false;
			/*      */}
		/* 914 */throw new InvalidPinException("OPPS: isPinIndexDriven( int pinIndex " + pinIndex + " )");
		/*      */}

	/*      */
	/*      */@Override
	public int getNumberOfPins()
	/*      */{
		/* 935 */return this.pins.size() / 2;
		/*      */}

	/*      */
	/*      */public int getPinOffset(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 949 */return this.getPinNumber(pinName) - 1;
		/*      */}

	/*      */
	/*      */public int[] getPinOffsets(String pinName)
	/*      */throws InvalidPinException
	/*      */{
		/* 959 */Pin[] pins = this.getPins(pinName);
		/* 960 */int[] pinOffsets = new int[pins.length];
		/*      */
		/* 962 */for (int i = 0; i < pins.length; i++) {
			/* 963 */if (pins[i].getPinNumber() - 1 < 0) {
				/* 964 */throw new InvalidPinException("OPPS: getPinOffsets( String pinName " + i + " )");
			}
			/* 965 */pinOffsets[i] = pins[i].getPinNumber() - 1;
			/*      */}
		/*      */
		/* 968 */return pinOffsets;
		/*      */}

	/*      */
	/*      */public boolean isPinOffsetInput(int pinOffset)
	/*      */throws InvalidPinException
	/*      */{
		/* 983 */return this.isPinInput(pinOffset + 1);
		/*      */}

	/*      */
	/*      */public boolean isPinOffsetOutput(int pinOffset)
	/*      */throws InvalidPinException
	/*      */{
		/* 997 */return this.isPinOutput(pinOffset + 1);
		/*      */}

	/*      */
	/*      */public boolean isPinOffsetInputOutput(int pinOffset)
	/*      */throws InvalidPinException
	/*      */{
		/* 1011 */return this.isPinInputOutput(pinOffset + 1);
		/*      */}

	/*      */
	/*      */public boolean isPinOffsetPower(int pinOffset)
	/*      */throws InvalidPinException
	/*      */{
		/* 1025 */return this.isPinPower(pinOffset + 1);
		/*      */}

	/*      */
	/*      */public boolean isPinOffsetNotConnected(int pinOffset)
	/*      */throws InvalidPinException
	/*      */{
		/* 1039 */return this.isPinNotConnected(pinOffset + 1);
		/*      */}

	/*      */
	/*      */public boolean isPinOffsetOpenCollector(int pinOffset)
	/*      */throws InvalidPinException
	/*      */{
		/* 1053 */return this.isPinOpenCollector(pinOffset + 1);
		/*      */}

	/*      */
	/*      */public boolean isPinOffsetClockOutput(int pinOffset)
	/*      */throws InvalidPinException
	/*      */{
		/* 1067 */return this.isPinClockOutput(pinOffset + 1);
		/*      */}

	/*      */
	/*      */public boolean isPinOffsetTotemPole(int pinOffset)
	/*      */throws InvalidPinException
	/*      */{
		/* 1081 */return this.isPinTotemPole(pinOffset + 1);
		/*      */}

	/*      */
	/*      */public boolean isPinOffsetTriState(int pinOffset)
	/*      */throws InvalidPinException
	/*      */{
		/* 1095 */return this.isPinTriState(pinOffset + 1);
		/*      */}

	/*      */
	/*      */public boolean isPinOffsetDriven(int pinOffset)
	/*      */throws InvalidPinException
	/*      */{
		/* 1109 */return this.isPinDriven(pinOffset + 1);
		/*      */}

	/*      */
	/*      */public boolean isPowered()
	/*      */throws InvalidPinException
	/*      */{
		/* 1122 */int[] vccPins = this.getPinOffsets("VCC");
		/* 1123 */int[] gndPins = this.getPinOffsets("GND");
		/* 1124 */boolean result = true;
		/*      */
		/* 1126 */for (int i = 0; i < vccPins.length; i++) {
			/* 1127 */if (!this.chip.readTTL(vccPins[i]).equals("HIGH"))
			/*      */{
				/* 1129 */result = false;
				/*      */}
			/*      */}
		/* 1132 */for (int i = 0; i < gndPins.length; i++) {
			/* 1133 */if (!this.chip.readTTL(gndPins[i]).equals("LOW"))
			/*      */{
				/* 1135 */result = false;
				/*      */}
			/*      */
			/*      */}
		/*      */
		/* 1141 */return result;
		/*      */}

	/*      */
	/*      */public boolean isHigh(String input)
	/*      */throws InvalidPinException
	/*      */{
		/* 1156 */if (this.chip.readTTL(this.getPinOffset(input)).equals("HIGH")) {
			/* 1157 */this.getPin(input).setPinState(Pin.PinState.HIGH);
			/*      */
			/* 1159 */return true;
			/*      */}
		/*      */
		/* 1162 */this.getPin(input).setPinState(Pin.PinState.LOW);
		/*      */
		/* 1164 */return false;
		/*      */}

	/*      */
	/*      */public boolean isLow(String input)
	/*      */throws InvalidPinException
	/*      */{
		/* 1179 */if (this.chip.readTTL(this.getPinOffset(input)).equals("HIGH")) {
			/* 1180 */this.getPin(input).setPinState(Pin.PinState.HIGH);
			/*      */
			/* 1182 */return false;
			/*      */}
		/*      */
		/* 1185 */this.getPin(input).setPinState(Pin.PinState.LOW);
		/*      */
		/* 1187 */return true;
		/*      */}

	/*      */
	/*      */public boolean isStateHigh(String input)
	/*      */throws InvalidPinException
	/*      */{
		/* 1202 */if (this.getPin(input).getPinState().equals(Pin.PinState.HIGH))
		/*      */{
			/* 1204 */return true;
			/*      */}
		/*      */
		/* 1208 */return false;
		/*      */}

	/*      */
	/*      */public boolean isStateLow(String input)
	/*      */throws InvalidPinException
	/*      */{
		/* 1223 */if (this.getPin(input).getPinState().equals(Pin.PinState.LOW))
		/*      */{
			/* 1225 */return true;
			/*      */}
		/*      */
		/* 1229 */return false;
		/*      */}

	/*      */
	/*      */public Pin.PinState getPinState(String input)
	/*      */throws InvalidPinException
	/*      */{
		/* 1244 */return this.getPin(input).getPinState();
		/*      */}

	/*      */
	/*      */public boolean isRisingEdge(String input)
	/*      */throws InvalidPinException
	/*      */{
		/* 1258 */Pin.PinState currentState = this.getPinState(input);
		/* 1259 */if (this.isHigh(input) && currentState.equals(Pin.PinState.LOW))
		/*      */{
			/* 1261 */return true;
			/*      */}
		/*      */
		/* 1265 */return false;
		/*      */}

	/*      */
	/*      */public boolean isFallingEdge(String input)
	/*      */throws InvalidPinException
	/*      */{
		/* 1280 */Pin.PinState currentState = this.getPinState(input);
		/* 1281 */if (this.isLow(input) && currentState.equals(Pin.PinState.HIGH))
		/*      */{
			/* 1283 */return true;
			/*      */}
		/*      */
		/* 1287 */return false;
		/*      */}

	/*      */
	/*      */protected String updateConfigFilePath(String file)
	/*      */{
		/* 1297 */URL url = ClassLoader.getSystemResource("configFiles");
		/* 1298 */String urlName = url.getFile().substring(1);
		/* 1299 */int urlIndex = urlName.indexOf("configFiles");
		/* 1300 */String urlBase = urlName.substring(0, urlIndex);
		/*      */
		/* 1303 */int fileIndex = file.indexOf("configFiles");
		/* 1304 */String fileBase = file.substring(fileIndex);
		/*      */
		/* 1307 */String newFilename = urlBase + fileBase;
		/*      */
		/* 1309 */return newFilename;
		/*      */}

	/*      */
	/*      */@Override
	public String getPinType(int pinOffset)
	/*      */{
		/*      */try
		/*      */{
			/* 1331 */if (this.isPinOffsetInput(pinOffset)) {
			}
			/*      */else
			/*      */{
				/* 1334 */if (this.isPinOffsetClockOutput(pinOffset)) {
				}
				/*      */else
				/*      */{
					/* 1337 */if (this.isPinOffsetInputOutput(pinOffset)) {
					}
					/*      */else
					/*      */{
						/* 1340 */if (this.isPinOffsetOpenCollector(pinOffset) || this.isPinOffsetTriState(pinOffset)) {
						}
						/*      */else
						/*      */{
							/* 1343 */if (this.isPinOffsetOutput(pinOffset)) {
							}
							/*      */else
							/*      */{
								/* 1346 */if (this.isPinOffsetPower(pinOffset)) {
								}
							}
							/*      */}
						/*      */}
					/*      */}
				/*      */}
			/* 1349 */return "NC";
			/*      */}
		/*      */catch (InvalidPinException e1)
		/*      */{
			/* 1355 */System.out.println("OPPS: getPinType( int pinOffset " + pinOffset + " )");
			/* 1356 */}
		return "NC";
		/*      */}

	/*      */
	/*      */@Override
	public String[] getDerivatives()
	/*      */{
		/* 1369 */List derivatives = new ArrayList();
		/*      */
		/* 1371 */String className = this.getClass().toString();
		/* 1372 */className = className.substring(6);
		/*      */
		/* 1374 */String pathName = this.getClass().toString().replace('.', '/');
		/* 1375 */pathName = pathName.substring(6, pathName.lastIndexOf('/'));
		/*      */
		/* 1377 */URL url = ClassLoader.getSystemResource(pathName);
		/*      */
		/* 1380 */String fileName = url.getFile();
		/* 1381 */File directory = new File(fileName);
		/*      */
		/* 1383 */if (directory.exists()) {
			/* 1384 */String[] files = directory.list();
			/*      */
			/* 1386 */for (String file : files) {
				/* 1389 */if (file.endsWith(".class")) {
					/*      */try {
						/* 1391 */Object thisObject = Class.forName(className);
						/*      */
						/* 1393 */String newName = (pathName + "/" + file.substring(0, file.length() - 6)).replace('/', '.');
						/*      */try {
							/* 1395 */Object newObject = Class.forName(newName);
							/*      */
							/* 1397 */if (thisObject.getClass().isInstance(newObject))
							/*      */{
								/* 1399 */derivatives.add(newObject.toString());
								/*      */}
							/*      */}
						/*      */catch (ClassNotFoundException e) {
							/* 1403 */System.out.println("New Class Not Found");
							/*      */}
						/*      */}
					/*      */catch (ClassNotFoundException e) {
						/* 1407 */System.out.println("This Class Not Found");
						/*      */}
					/*      */}
				/*      */}
			/*      */}
		/* 1412 */String[] returnData = (String[]) derivatives.toArray(new String[derivatives.size()]);
		/* 1413 */return returnData;
		/*      */}

	/*      */
	/*      */public void setPin(String output, Pin.PinState state)
	/*      */throws InvalidPinException
	/*      */{
		/* 1427 */this.getPin(output).setPinState(state);
		/* 1428 */switch (state.ordinal()) {
		/*      */case 1:
				/* 1430 */this.chip.write(this.getPinOffset(output), "HIGH", ((OutputPin) this.getPin(output)).getPinDelayTplh());
				/*      */
				/* 1432 */break;
			/*      */case 2:
				/* 1434 */this.chip.write(this.getPinOffset(output), "LOW", ((OutputPin) this.getPin(output)).getPinDelayTphl());
				/*      */
				/* 1436 */break;
			/*      */case 3:
				/* 1438 */this.chip.write(this.getPinOffset(output), "NC", 0);
				/*      */
				/* 1440 */break;
			/*      */case 4:
				/* 1442 */this.chip.write(this.getPinOffset(output), "NC", 0);
				/*      */
				/* 1444 */break;
			/*      */case 5:
				/* 1446 */break;
		/*      */}
		/*      */}

	/*      */
	/*      */public void setPin(String output, Pin.PinState state, int delay)
	/*      */throws InvalidPinException
	/*      */{
		/* 1465 */this.getPin(output).setPinState(state);
		/* 1466 */int riseFallDelay = ((OutputPin) this.getPin(output)).getPinDelayTplh();
		/* 1467 */int timeDelay = riseFallDelay + delay;
		/*      */
		/* 1469 */switch (state.ordinal()) {
		/*      */case 1:
				/* 1471 */this.chip.write(this.getPinOffset(output), "HIGH", timeDelay);
				/*      */
				/* 1473 */break;
			/*      */case 2:
				/* 1475 */this.chip.write(this.getPinOffset(output), "LOW", timeDelay);
				/*      */
				/* 1477 */break;
			/*      */case 3:
				/* 1479 */this.chip.write(this.getPinOffset(output), "NC", timeDelay);
				/*      */
				/* 1481 */break;
			/*      */case 4:
				/* 1483 */this.chip.write(this.getPinOffset(output), "NC", timeDelay);
				/*      */
				/* 1485 */break;
			/*      */case 5:
				/* 1487 */break;
		/*      */}
		/*      */}

	/*      */
	/*      */@Override
	public int getDerivative()
	/*      */{
		/* 1504 */return 0;
		/*      */}

	/*      */
	/*      */@Override
	public void setDerivative(int t)
	/*      */{
		/*      */}

	/*      */
	/*      */@Override
	public String[] getPackages()
	/*      */{
		/* 1528 */String[] dummy = { "", "" };
		/* 1529 */return dummy;
		/*      */}

	/*      */
	/*      */@Override
	public int getPackage()
	/*      */{
		/* 1541 */return 0;
		/*      */}

	/*      */
	/*      */@Override
	public void setPackage(int p)
	/*      */{
		/*      */}
	/*      */}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: integratedCircuits.IntegratedCircuit JD-Core
 * Version: 0.6.2
 */
