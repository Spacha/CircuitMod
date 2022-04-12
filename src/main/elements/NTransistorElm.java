package main.elements;
public class NTransistorElm extends TransistorElm {
	public NTransistorElm(int xx, int yy) {
		super(xx, yy, false);
	}

	@Override
	public Class<TransistorElm> getDumpClass() {
		return TransistorElm.class;
	}
}
