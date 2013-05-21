package uk.ac.york.jbb.jbreadboard;

public abstract interface CircuitSelection {
	public abstract void delete();

	public abstract void select();

	public abstract void deselect();

	public abstract String getInfo();

	public abstract String getDiagram();
}
