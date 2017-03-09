package com.android.gui.action.project;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.android.bean.entity.ProjectEntity;
import com.android.gui.action.app.BaseAction;
import com.android.lang.Application;
/**
 * 编译项目
 * @author Administrator
 *
 */
public class CompilerProjectAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProjectEntity project;

	public CompilerProjectAction(ProjectEntity project) {
		super("编译项目");
		this.project = project;
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		new Thread(){
			public void run() {
				setEnabled(false);
				firePropertyChange();
				try {
					Thread.sleep(2000L);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				///----
				Logger.getLogger(this.getClass().getName()).info("点击 编译项目 动作");
				if (!project.isSaved()) {
					project.doSave();
				}
				Application.compiler.compiler(project);
				project.setSaved(true);
				///----
				try {
					Thread.sleep(2000L);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				Logger.getLogger(this.getClass().getName()).info("项目编译完成");
				setEnabled(true);
			};
		}.start();
	}

}
