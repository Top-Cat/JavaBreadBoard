package jBreadBoard;

public class NodeType {
	private String type;

	public NodeType() {
		this.setType("NC");
	}

	public NodeType(String s) {
		this.setType(s);
	}

	public void setType(String s) {
		if (s.equals("IN") || s.equals("OCO") || s.equals("OUT") || s.equals("IO") || s.equals("CLO")) {
			this.type = s;
		} else {
			this.type = "NC";

		}
	}

	public String getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return this.type;
	}

	public static boolean conflicts(String s1, String s2) {
		if (s1.equals("OCO") && s2.equals("OCO")) {
			return false;
		}
		if ((s1.equals("OCO") || s1.equals("OUT") || s1.equals("CLO")) && (s2.equals("OCO") || s2.equals("OUT") || s2.equals("CLO"))) {
			return true;
		}
		return false;
	}

	public static boolean conflicts(NodeType t1, NodeType t2) {
		return NodeType.conflicts(t1.getType(), t2.getType());
	}
}
