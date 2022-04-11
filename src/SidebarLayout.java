import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import java.awt.GridBagLayout;

//class ToolBoxLayout implements LayoutManager {
class SidebarLayout extends GridBagLayout {
	private static final long serialVersionUID = 1L;

	public SidebarLayout() {
	}

	@Override
	public void addLayoutComponent(String name, Component c) {
	}

	@Override
	public void removeLayoutComponent(Component c) {
	}

	@Override
	public Dimension preferredLayoutSize(Container target) {
		return new Dimension(100, 500);
	}

	@Override
	public Dimension minimumLayoutSize(Container target) {
		return new Dimension(100, 100);
	}
};
