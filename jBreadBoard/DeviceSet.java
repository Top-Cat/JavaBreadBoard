package jBreadBoard;

public class DeviceSet {
	private DeviceList head = null;

	public void add(Device d) {
		if (d == null) {
			return;
		}

		DeviceList newlist = new DeviceList(d);
		if (this.head == null) {
			this.head = newlist;
		} else {
			DeviceList temp = this.head;
			boolean notInSet = true;

			if (this.head.device == d) {
				notInSet = false;
			}

			while (temp.next != null && notInSet) {
				if (temp.next.device == d) {
					notInSet = false;
				}
				temp = temp.next;
			}

			if (notInSet) {
				temp.next = newlist;

			}
		}
	}

	public void remove(Device d) {
		DeviceList temp = this.head;

		if (d == null) {
			return;
		}

		if (this.head != null) {
			if (this.head.device == d) {
				this.head = this.head.next;
			} else {
				boolean removed = false;
				while (temp != null && !removed) {
					if (temp.next.device == d) {
						temp.next = temp.next.next;
						removed = true;
					}
					temp = temp.next;
				}
			}

		}
	}

	public DeviceList toList() {
		return this.head;
	}
}

/*
 * Location:
 * C:\Users\Yellow\Downloads\JavaBreadBoard1_11\JavaBreadBoard1_11\build
 * \classes\ Qualified Name: jBreadBoard.DeviceSet JD-Core Version: 0.6.2
 */
