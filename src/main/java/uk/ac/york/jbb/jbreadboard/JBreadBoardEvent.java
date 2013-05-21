package uk.ac.york.jbb.jbreadboard;

class JBreadBoardEvent {
	public JBreadBoardEvent next;
	private Node node;
	private long time;

	public Node getNode() {
		return this.node;
	}

	public long getTime() {
		return this.time;
	}

	public JBreadBoardEvent(Node n, long t) {
		this.node = n;
		this.time = t;
	}

	public DeviceSet process(DeviceSet initial) {
		if (this.node != null) {
			Potential potential = this.node.getPotential();
			if (potential != null) {
				initial = potential.getDevicesWithInputs(initial);
			}
		}
		return initial;
	}
}
