package main.elements;
public class PTransistorElm extends TransistorElm {
	public PTransistorElm(int xx, int yy) {
		super(xx, yy, true);
	}

	@Override
	public Class<TransistorElm> getDumpClass() {
		return TransistorElm.class;
	}
}
