package main;
import java.util.Vector;

public class CircuitNode {
	int x, y;
	Vector<CircuitNodeLink> links;
	boolean internal;

	CircuitNode() {
		links = new Vector<CircuitNodeLink>();
	}
	
	public int linkCount() {
		return links.size();
	}
	
	public Vector<CircuitNodeLink> getLinks() {
		return links;
	}
}
