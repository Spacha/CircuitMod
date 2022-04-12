package main.elements;

public class ACRailElm extends RailElm {
	public ACRailElm(int xx, int yy) {
		super(xx, yy, WF_AC);
	}

	@Override
	public Class<RailElm> getDumpClass() {
		return RailElm.class;
	}
}
