package jBreadBoard;

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

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: jBreadBoard.JBreadBoardChangeEvent JD-Core Version:
 * 0.6.2
 */
