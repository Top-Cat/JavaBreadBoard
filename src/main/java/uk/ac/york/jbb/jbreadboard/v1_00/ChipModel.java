package uk.ac.york.jbb.jbreadboard.v1_00;

import uk.ac.york.jbb.jbreadboard.ChipAccess;

public abstract interface ChipModel {
	public abstract void setAccess(ChipAccess paramChipAccess);

	public abstract void simulate();

	public abstract void reset();

	public abstract String getChipText();

	public abstract String getDescription();

	public abstract String getManufacturer();

	public abstract String getDiagram();

	public abstract int getNumberOfPins();

	public abstract boolean isWide();

	public abstract String getPinType(int paramInt);

	public abstract String[] getDerivatives();

	public abstract int getDerivative();

	public abstract String[] getPackages();

	public abstract int getPackage();

	public abstract void setDerivative(int paramInt);

	public abstract void setPackage(int paramInt);
}
