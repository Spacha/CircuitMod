package main;
class NTransistorElm extends TransistorElm {
	public NTransistorElm(int xx, int yy) {
		super(xx, yy, false);
	}

	@Override
	Class<TransistorElm> getDumpClass() {
		return TransistorElm.class;
	}
}
