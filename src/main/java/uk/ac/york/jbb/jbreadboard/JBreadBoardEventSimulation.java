package uk.ac.york.jbb.jbreadboard;

public class JBreadBoardEventSimulation {
	private Circuit circuit;
	private JBreadBoardEvent head = null;
	private long currentTime = 0L;

	public JBreadBoardEventSimulation(Circuit c) {
		this.circuit = c;
	}

	public long getCurrentTime() {
		return this.currentTime;
	}

	private void queueEvent(JBreadBoardEvent e) {
		if (this.head != null) {
			while (this.head != null && this.head.getNode() == e.getNode() && this.head.getTime() >= e.getTime()) {
				this.head = this.head.next;
			}

			JBreadBoardEvent temp = this.head;

			while (temp != null) {
				while (temp.next != null && temp.next.getNode() == e.getNode() && temp.next.getTime() >= e.getTime()) {
					temp.next = temp.next.next;
				}
				temp = temp.next;
			}

		}

		if (this.head == null) {
			this.head = e;
		} else if (e.getTime() < this.head.getTime()) {
			e.next = this.head;
			this.head = e;
		} else {
			JBreadBoardEvent temp = this.head;

			while (temp.next != null && temp.next.getTime() < e.getTime()) {
				temp = temp.next;
			}
			e.next = temp.next;
			temp.next = e;
		}
	}

	public void addEvent(Node n, int delay) {
		if (n != null && delay >= 0) {
			this.queueEvent(new JBreadBoardEvent(n, this.currentTime + delay));
		}
	}

	public void addEvent(Node n, int delay, String value) {
		if (n != null && delay >= 0) {
			this.queueEvent(new JBreadBoardChangeEvent(n, this.currentTime + delay, value));
		}
	}

	private JBreadBoardEvent takeEvent() {
		JBreadBoardEvent e = this.head;

		if (this.head != null) {
			this.head = this.head.next;
		}
		return e;
	}

	public void runFor(int duration) {
		boolean interrupt = false;
		long newtime = this.currentTime + duration;

		long starttime = System.currentTimeMillis();

		while (!interrupt && this.head != null && this.head.getTime() <= newtime) {
			this.stepSimulation();
			if (System.currentTimeMillis() - starttime > 1L) {
				interrupt = true;
			}
		}

		if (!interrupt) {
			this.currentTime = newtime;
		}
	}

	public void stepSimulation() {
		DeviceSet set = null;
		DeviceList list = null;

		if (this.head != null) {
			if (this.head.getTime() != this.currentTime) {
				this.currentTime = this.head.getTime();
			}

			do {
				JBreadBoardEvent e = this.takeEvent();
				set = e.process(set);
			} while (this.head != null && this.head.getTime() <= this.currentTime);

			if (set != null) {
				list = set.toList();
			}
			while (list != null) {
				list.device.simulate();
				list = list.next;
			}
		}
		this.currentTime += 1L;
	}
}
