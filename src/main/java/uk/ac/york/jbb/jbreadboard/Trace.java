package uk.ac.york.jbb.jbreadboard;

public class Trace {
	private Circuit circuit;
	private long time = -1L;
	private StringBuffer trace = new StringBuffer("Time");
	public static boolean duplicate = false;
	private boolean currentduplicate = Trace.duplicate;

	private DeviceSet probeset = new DeviceSet();

	public void addProbe(Probe p) {
		this.probeset.add(p);
	}

	public void removeProbe(Probe p) {
		this.probeset.remove(p);
	}

	public String getTrace() {
		return this.trace.toString();
	}

	@Override
	public String toString() {
		return this.trace.toString();
	}

	public Trace(Circuit c) {
		if (c != null) {
			this.circuit = c;

		}
	}

	public void reset() {
		DeviceList probelist = this.probeset.toList();
		this.trace = new StringBuffer("Time");

		while (probelist != null) {
			Probe probe = (Probe) probelist.device;
			this.trace.append("," + probe.getLabel());
			probelist = probelist.next;
		}

		this.time = -1L;
		this.currentduplicate = Trace.duplicate;
	}

	public void simulate() {
		if (this.circuit.getSimTime() != this.time) {
			DeviceList probelist = this.probeset.toList();

			this.time = this.circuit.getSimTime();
			this.trace.append(",\n" + this.time);

			if (this.currentduplicate) {
				while (probelist != null) {
					Probe probe = (Probe) probelist.device;
					probe.simulate();
					this.trace.append("," + probe.getOldValue());
					probelist = probelist.next;
				}
				this.trace.append(",\n" + this.time);
				probelist = this.probeset.toList();
			}

			while (probelist != null) {
				Probe probe = (Probe) probelist.device;
				probe.simulate();
				this.trace.append("," + probe.getValue());
				probelist = probelist.next;
			}
		}
	}
}
