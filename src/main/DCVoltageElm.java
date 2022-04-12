package main;
class DCVoltageElm extends VoltageElm {
	public DCVoltageElm(int xx, int yy) {
		super(xx, yy, WF_DC);
	}

	@Override
	Class<VoltageElm> getDumpClass() {
		return VoltageElm.class;
	}
}
