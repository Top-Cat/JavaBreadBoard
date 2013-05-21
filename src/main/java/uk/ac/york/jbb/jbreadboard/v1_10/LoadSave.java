package uk.ac.york.jbb.jbreadboard.v1_10;

import uk.ac.york.jbb.integratedcircuits.ttl.generic.InvalidStateException;

public abstract interface LoadSave {
	public abstract String getFileName();

	public abstract void setFileName(String paramString);

	public abstract String getFileExtension();

	public abstract boolean instantiateBlank();

	public abstract void initialiseState(String paramString) throws InvalidStateException;
}
