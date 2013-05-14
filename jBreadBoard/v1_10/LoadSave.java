package jBreadBoard.v1_10;

import integratedCircuits.ttl.generic.InvalidStateException;

public abstract interface LoadSave {
	public abstract String getFileName();

	public abstract void setFileName(String paramString);

	public abstract String getFileExtension();

	public abstract boolean instantiateBlank();

	public abstract void initialiseState(String paramString) throws InvalidStateException;
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: jBreadBoard.v1_10.LoadSave JD-Core Version: 0.6.2
 */
