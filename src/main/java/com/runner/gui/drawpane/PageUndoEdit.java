package com.runner.gui.drawpane;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import com.runner.gui.RootRunPane;

public class PageUndoEdit extends AbstractUndoableEdit {

	private static final long serialVersionUID = 1L;
	private Component currentComponent;
	private Component backUpComponent;
	private RootRunPane rootRunPane;

	public PageUndoEdit(Component component, RootRunPane rootRunPane) {
		this.currentComponent = component;
		this.rootRunPane = rootRunPane;
	}

	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		// backUpComponent = activeComponent;
		// setActiveComponent(currentComponent, false);
		// refreshActions();
		backUpComponent = this.rootRunPane.getCurrentPage();
		this.rootRunPane.removeAll();
		this.rootRunPane.add(currentComponent, BorderLayout.CENTER);
		System.out.println("undo:" + currentComponent);
		this.rootRunPane.updateUI();
	}

	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		// setActiveComponent(backUpComponent, false);
		// refreshActions();
		this.rootRunPane.removeAll();
		this.rootRunPane.add(backUpComponent, BorderLayout.CENTER);
		this.rootRunPane.updateUI();
	}
}
