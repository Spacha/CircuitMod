package main.elements;
public class NMosfetElm extends MosfetElm {
	public NMosfetElm(int xx, int yy) {
		super(xx, yy, false);
	}

	@Override
	public Class<MosfetElm> getDumpClass() {
		return MosfetElm.class;
	}
}
