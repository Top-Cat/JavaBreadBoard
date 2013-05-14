package jBreadBoard;

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

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: jBreadBoard.JBreadBoardEvent JD-Core Version: 0.6.2
 */
