package uk.ac.york.jbb.jbreadboard;

class JBreadBoardChangeEvent extends JBreadBoardEvent {
	private String value;

	public JBreadBoardChangeEvent(Node n, long t, String v) {
		super(n, t);
		this.value = v;
	}

	@Override
	public DeviceSet process(DeviceSet initial) {
		Node node = this.getNode();
		if (node != null) {
			Potential potential = node.getPotential();
			if (potential != null) {
				String oldvalue = potential.toTTL();
				node.setPotential(this.value, this.getTime());

				if (!oldvalue.equals(potential.toTTL())) {
					initial = potential.getDevicesWithInputs(initial);
				}
			}
		}
		return initial;
	}
}
