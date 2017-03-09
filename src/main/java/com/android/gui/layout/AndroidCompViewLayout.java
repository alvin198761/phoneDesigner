package com.android.gui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import com.android.gui.comp.ShapeButton;
/**
 * 安卓控件视图列表
 * @author Administrator
 *
 */
public class AndroidCompViewLayout implements LayoutManager {

	@Override
	public void addLayoutComponent(String name, Component comp) {
		
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return parent.getPreferredSize();
	}

	@Override
	public void layoutContainer(Container parent) {
		int hgp = 10;
		int vgp = 10;
		int x = 0;
		int y = vgp;
		int w = hgp*2 +  ShapeButton.icon_width*2;
		int h =0;
		for(int i =0 ;i < parent.getComponentCount();i++){
			if(i %2==0){
				x = hgp;
				Component comp = parent.getComponent(i);
				comp.setBounds(x, y, ShapeButton.icon_width, ShapeButton.icon_width);
			h +=  ShapeButton.icon_width+hgp;
			}else{
				x += hgp + ShapeButton.icon_width;
				Component comp = parent.getComponent(i);
				comp.setBounds(x, y, ShapeButton.icon_width, ShapeButton.icon_width);
			
				y += vgp +  ShapeButton.icon_width;
			}
		}
		parent.setPreferredSize(new Dimension(w,h));
	}

}
