package main.elements;

public class ACVoltageElm extends VoltageElm {
	public ACVoltageElm(int xx, int yy) {
		super(xx, yy, WF_AC);
	}

	@Override
	Class<VoltageElm> getDumpClass() {
		return VoltageElm.class;
	}
}
