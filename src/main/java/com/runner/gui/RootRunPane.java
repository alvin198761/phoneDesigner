package com.runner.gui;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.runner.gui.drawpane.PagePane;

public class RootRunPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProjectEntity project;
	// private PageUndoableEditManager undoableEditManager = new
	// PageUndoableEditManager();
	private PagePane currentPage;
	private Map<String, PagePane> compMap = new HashMap<String, PagePane>();

	public RootRunPane(ProjectEntity project) {
		this.project = project;
		// CardLayout layout = new CardLayout();
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		//
		System.out.println("root:" + project.getRootPage());
		addCurrentPage(project.getRootPage());
	}

	private void addCurrentPage(AndroidPageContainer page) {
		if (compMap.containsKey(page.getName())) {
			this.currentPage = compMap.get(page.getName());
		} else {
			this.currentPage = new PagePane(page, project, this);
			this.compMap.put(page.getName(), this.currentPage);
		}

		this.removeAll();
		this.add(currentPage, BorderLayout.CENTER);
		this.updateUI();
		// this.undoableEditManager.post(new PageUndoEdit(currentPage, this));
	}

	public void hidePage(String pageURL, AndroidPageContainer page) {
		backPage(pageURL, page);
		// this.undoableEditManager.reset();
	}

	public void showPage(String pageURL, AndroidPageContainer page) {
		AndroidPageContainer operPage = null;
		for (int i = 0; i < project.getViewCount(); i++) {
			if (project.getViewAt(i).getName().equals(pageURL)) {
				operPage = project.getViewAt(i);
				break;
			}
		}
		if (operPage != null) {
			addCurrentPage(operPage);
		}
	}

	public void forwardPage(String pageURL, AndroidPageContainer page) {
		showPage(pageURL, page);
	}

	public void nextPage(String pageURL, AndroidPageContainer page) {
		// this.undoableEditManager.redo();
		showPage(pageURL, page);
	}

	public void backPage(String pageURL, AndroidPageContainer page) {
		// this.undoableEditManager.undo();
		showPage(pageURL, page);
	}

	public PagePane getCurrentPage() {
		return currentPage;
	}

}
