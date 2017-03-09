package com.runner.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.BaseDataShape;
import com.android.lang.Application;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class RunnerDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RootRunPane rootRunPane;

	public RunnerDialog() {
		setIconImage(Application.mainFrame.getIconImage());
	}

	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
//			this.setResizable(false);
			getContentPane().setLayout(thisLayout);

			getContentPane().add(new JScrollPane(rootRunPane),
					BorderLayout.CENTER);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run(ProjectEntity project, final ProjectEntity sourceProject,
			double scale) {
		project.setMode(BaseDataShape.MODE_RUN);
		rootRunPane = new RootRunPane(project);
		rootRunPane.setPreferredSize(new Dimension(project.getRes().getWidth(),
				project.getRes().getHeight()));
		initGUI();
		this.setSize((int) (project.getRes().getWidth() * scale) + 20,
				(int) (project.getRes().getHeight() * scale) + 100);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				sourceProject.firePropertyChange("stopRunning", true, false);
			}
		});
		setLocationRelativeTo(null);

	}
}
