package main;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

class ImportDialogLayout implements LayoutManager {
	public ImportDialogLayout() {
	}

	@Override
	public void addLayoutComponent(String name, Component c) {
	}

	@Override
	public void removeLayoutComponent(Component c) {
	}

	@Override
	public Dimension preferredLayoutSize(Container target) {
		return new Dimension(500, 500);
	}

	@Override
	public Dimension minimumLayoutSize(Container target) {
		return new Dimension(100, 100);
	}

	@Override
	public void layoutContainer(Container target) {
		Insets insets = target.getInsets();
		//int targetw = target.getSize().width - insets.left - insets.right;
		//int targeth = target.getSize().height - (insets.top + insets.bottom);
		int i;
		//int pw = 300;
		if (target.getComponentCount() == 0)
			return;
		Component cl = target.getComponent(target.getComponentCount() - 1);
		Dimension dl = cl.getPreferredSize();
		target.getComponent(0).setLocation(insets.left, insets.top);
		int cw = target.getSize().width - insets.left - insets.right;
		int ch = target.getSize().height - insets.top - insets.bottom - dl.height;
		target.getComponent(0).setSize(cw, ch);
		int h = ch + insets.top;
		int x = 0;
		for (i = 1; i < target.getComponentCount(); i++) {
			Component m = target.getComponent(i);
			if (m.isVisible()) {
				Dimension d = m.getPreferredSize();
				m.setLocation(insets.left + x, h);
				m.setSize(d.width, d.height);
				x += d.width;
			}
		}
	}
};
