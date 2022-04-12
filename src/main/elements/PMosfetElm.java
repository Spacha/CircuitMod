package main.elements;
public class PMosfetElm extends MosfetElm {
	public PMosfetElm(int xx, int yy) {
		super(xx, yy, true);
	}

	@Override
	public Class<MosfetElm> getDumpClass() {
		return MosfetElm.class;
	}
}
