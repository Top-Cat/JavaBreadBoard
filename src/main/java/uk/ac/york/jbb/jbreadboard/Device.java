package uk.ac.york.jbb.jbreadboard;

public abstract interface Device {
	public abstract void simulate();

	public abstract void reset();

	public abstract void updateConnections();
}
