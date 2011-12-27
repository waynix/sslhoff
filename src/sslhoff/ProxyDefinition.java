package sslhoff;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyDefinition {
	private InetSocketAddress address;
	private Proxy.Type type;
	
	ProxyDefinition(InetSocketAddress address, Proxy.Type type)
	{
		this.address = address;
		this.type = type;
	}

	public InetSocketAddress getAddress() {
		return address;
	}

	public void setAddress(InetSocketAddress address) {
		this.address = address;
	}

	public Proxy.Type getType() {
		return type;
	}

	public void setType(Proxy.Type type) {
		this.type = type;
	}
	
	public String toString() {
		return address.toString() + type.toString();
	}
}
