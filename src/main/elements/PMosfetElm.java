package main.elements;
public class PMosfetElm extends MosfetElm {
	public PMosfetElm(int xx, int yy) {
		super(xx, yy, true);
	}

	@Override
	Class<MosfetElm> getDumpClass() {
		return MosfetElm.class;
	}
}
