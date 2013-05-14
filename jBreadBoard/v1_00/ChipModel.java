package jBreadBoard.v1_00;

import jBreadBoard.ChipAccess;

public abstract interface ChipModel
{
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

/* Location:           C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build\classes\
 * Qualified Name:     jBreadBoard.v1_00.ChipModel
 * JD-Core Version:    0.6.2
 */