package uk.ac.york.jbb.jbreadboard;

public class Node {
	private Potential potential;
	private Device device = null;

	public Node() {
		this.potential = new Potential(this, null);
	}

	public void setDevice(Device d) {
		this.device = d;
	}

	public Device getDevice() {
		return this.device;
	}

	public Potential getPotential() {
		if (this.potential != null) {
			return this.potential.getPotential();
		}
		return null;
	}

	public void setPotential(String ttl_level, long currenttime) {
		this.potential.setValue(ttl_level, currenttime);
	}

	public String getType() {
		return this.getPotential().getType();
	}

	public void setType(String s) {
		if (s != null) {
			this.potential.setType(s);
		} else {
			this.potential.setType("NC");

		}
	}
}
