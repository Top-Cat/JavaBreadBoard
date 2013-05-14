package jBreadBoard;

public class Potential {
	private Node node1;
	private Node node2;
	private NodeType type = new NodeType();
	private Potential parent1;
	private Potential parent2;
	private Potential child;
	private String value = "NC";
	private long modifiedtime = -1L;

	public Potential(Node n1, Node n2) {
		this.node1 = n1;
		this.node2 = n2;
		this.update();
	}

	public Node getNode1() {
		return this.node1;
	}

	public Node getNode2() {
		return this.node2;
	}

	public void update() {
		if (this.parent1 != null) {
			this.parent1.child = null;
		}
		if (this.parent2 != null) {
			this.parent2.child = null;
		}

		if (this.node1 != null) {
			this.parent1 = this.node1.getPotential();
		}
		if (this.node2 != null) {
			this.parent2 = this.node2.getPotential();
		}

		if (this.parent1 == this) {
			this.parent1 = null;
		}
		if (this.parent2 == this) {
			this.parent2 = null;
		}

		if (this.parent1 != null) {
			this.parent1.child = this;
		}
		if (this.parent2 != null) {
			this.parent2.child = this;
		}

		Potential oldchild = this.child;
		this.child = null;

		this.setType();

		this.setValue("NC", this.modifiedtime);

		if (oldchild != null) {
			oldchild.update();
		}
	}

	public void delete() {
		this.parent1.child = null;
		this.parent2.child = null;
		if (this.child != null) {
			this.child.update();
		}
	}

	public DeviceSet getDevicesWithInputs(DeviceSet initial) {
		if (initial == null) {
			initial = new DeviceSet();
		}

		if (this.parent1 == null && this.parent2 == null) {
			if (this.node1 != null && (this.getType().equals("IN") || this.getType().equals("IO") || this.getType().equals("CLO"))) {
				initial.add(this.node1.getDevice());
			}

			if (this.node2 != null && (this.getType().equals("IN") || this.getType().equals("IO") || this.getType().equals("CLO"))) {
				initial.add(this.node2.getDevice());
			}
		} else {
			if (this.parent1 != null) {
				initial = this.parent1.getDevicesWithInputs(initial);
			}
			if (this.parent2 != null) {
				initial = this.parent2.getDevicesWithInputs(initial);
			}
		}
		return initial;
	}

	public Potential getPotential() {
		if (this.child == null) {
			return this;
		}
		return this.child.getPotential();
	}

	public String getType() {
		return this.type.getType();
	}

	public void setType() {
		String type1 = "";
		String type2 = "";

		if (this.parent1 != null || this.parent2 != null) {
			if (this.parent1 != null) {
				type1 = this.parent1.getType();
			}
			if (this.parent2 != null) {
				type2 = this.parent2.getType();
			}

			if (type1.equals("OUT") || type2.equals("OUT")) {
				this.type.setType("OUT");
			} else if (type1.equals("CLO") || type2.equals("CLO")) {
				this.type.setType("CLO");
			} else if (type1.equals("OCO") || type2.equals("OCO")) {
				this.type.setType("OCO");
			} else if (type1.equals("IO") || type2.equals("IO")) {
				this.type.setType("IO");
			} else if (type1.equals("IN") || type2.equals("IN")) {
				this.type.setType("IN");
			} else {
				this.type.setType("NC");
			}
		} else {
			this.type.setType("NC");
		}

		if (this.child != null) {
			this.child.setType();
		}
	}

	public void setType(String s) {
		if (this.parent1 == null && this.parent2 == null) {
			this.type.setType(s);
		} else {
			this.setType();
		}

		if (this.child != null) {
			this.child.setType();
		}
	}

	public String toTTL() {
		return this.value;
	}

	public void setValue(String v, long time) {
		if (!v.equals("HIGH") && !v.equals("LOW") && !v.equals("NC")) {
			return;
		}

		this.modifiedtime = time;

		if (this.parent1 != null && this.parent2 != null) {
			String p1type = this.parent1.getType();
			this.parent2.getType();

			if (this.parent1.modifiedtime > this.modifiedtime) {
				this.modifiedtime = this.parent1.modifiedtime;
			}
			if (this.parent2.modifiedtime > this.modifiedtime) {
				this.modifiedtime = this.parent2.modifiedtime;
			}

			if (this.getType().equals("NC") || this.getType().equals("IN")) {
				this.value = "NC";
			} else if (this.parent1.toTTL().equals("NC") && this.parent2.toTTL().equals("NC")) {
				this.value = "NC";
			} else if (this.getType().equals("OCO")) {
				if (this.parent1.toTTL().equals("HIGH")) {
					this.value = "HIGH";
				} else if (this.parent2.toTTL().equals("HIGH")) {
					this.value = "HIGH";
				} else {
					this.value = "LOW";
				}

			} else if (this.getType().equals("IO")) {
				if (this.parent1.modifiedtime > this.parent2.modifiedtime) {
					this.value = this.parent1.toTTL();
				} else {
					this.value = this.parent2.toTTL();
				}

			} else if (this.getType().equals("OUT") || this.getType().equals("CLO")) {
				if (p1type.equals("OUT") || p1type.equals("CLO")) {
					this.value = this.parent1.toTTL();
				} else {
					this.value = this.parent2.toTTL();
				}
			}
		} else if (this.parent1 != null) {
			this.value = this.parent1.toTTL();
			if (this.parent1.modifiedtime > this.modifiedtime) {
				this.modifiedtime = this.parent1.modifiedtime;
			}
		} else if (this.parent2 != null) {
			this.value = this.parent2.toTTL();
			if (this.parent2.modifiedtime > this.modifiedtime) {
				this.modifiedtime = this.parent2.modifiedtime;
			}
		} else {
			this.value = v;
		}

		if (this.child != null) {
			this.child.setValue(this.value, this.modifiedtime);
		}
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: jBreadBoard.Potential JD-Core Version: 0.6.2
 */
