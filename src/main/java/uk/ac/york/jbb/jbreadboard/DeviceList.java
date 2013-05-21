package uk.ac.york.jbb.jbreadboard;

public class DeviceList {
	public Device device;
	public DeviceList next;

	public DeviceList(Device d) {
		this.device = d;
	}
}
