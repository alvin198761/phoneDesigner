package com.android.gui.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import com.android.gui.dialog.select.SelectWorkSpaceDialog;
import com.android.gui.dialog.select.WorkSpaceBean;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;
import com.android.utils.DialogUtil;

public class WorkSpaceManegerMentDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger
			.getLogger(WorkSpaceManegerMentDialog.class);

	private final JPanel contentPanel = new JPanel();
	private JList workSpaceList = new JList();
	private JFileChooser workSpaceDirectionChooser = new JFileChooser();

	private JScrollPane scrollPane;

	/**
	 * Create the dialog.
	 */
	public WorkSpaceManegerMentDialog() {
		initCompent();
		loadWorkSpace();
		setLocationRelativeTo(Application.mainFrame);
	}

	private void loadWorkSpace() {
		DefaultListModel mode = new DefaultListModel();
		List<WorkSpaceBean> wsbList = SelectWorkSpaceDialog.load();
		WorkSpaceBean w;
		mode.removeAllElements();
		for (int i = 0; i < wsbList.size(); i++) {
			w = wsbList.get(i);
			mode.add(i, w);
		}
		scrollPane.setViewportView(workSpaceList);
		workSpaceList.setModel(mode);
	}

	private void initCompent() {
		setTitle("工作目录管理");
		setIconImage(ResourceUtil.logo_img);
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 345, 274);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 319, 218);
		contentPanel.add(scrollPane);

		JButton addButton = new JButton();
		// addButton.setIcon(Utils.getImageIcon("workspace_add.png"));
		addButton.setText("添加");
		addButton.setToolTipText("添加工作目录");
		addButton.setBounds(10, 84, 95, 25);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addButtonClick();
			}
		});

		JButton delButton = new JButton();
		// delButton.setIcon(Utils.getImageIcon("workspace_delete.png"));
		delButton.setText("删除");
		delButton.setToolTipText("删除工作目录");
		delButton.setBounds(10, 144, 95, 25);
		delButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delButtonClick();
			}
		});

		JToolBar toolbar = new JToolBar();
		toolbar.setOrientation(JToolBar.VERTICAL);
		toolbar.setFloatable(false);
		toolbar.add(addButton);
		toolbar.add(delButton);
		scrollPane.setRowHeaderView(toolbar);

		workSpaceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * 添加按钮事件
	 */
	private void addButtonClick() {
		log.debug("点击 添加工作目录 按钮");
		// 选择一个路径 添加到list中去
		workSpaceDirectionChooser.setSelectedFile(null);
		workSpaceDirectionChooser.updateUI();
		workSpaceDirectionChooser
				.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = workSpaceDirectionChooser.showOpenDialog(this);
		if (result != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File selectFile = workSpaceDirectionChooser.getSelectedFile();
		if (selectFile == null) {
			return;
		}
		List<WorkSpaceBean> wsbList = SelectWorkSpaceDialog.load();
		for (WorkSpaceBean w : wsbList) {
			if (w.getPath().equalsIgnoreCase(selectFile.getAbsolutePath())) {
				DialogUtil.promptError("已存在该工作目录!");
				return;
			}
		}
		boolean createOk = false;
		if (!selectFile.exists()) {
			createOk = selectFile.mkdirs();
			if (!createOk) {
				DialogUtil.promptError("该目录不能创建文件夹!");
				return;
			}
		}
		// 往配置文件上添加
		WorkSpaceBean w = new WorkSpaceBean();
		w.setPath(selectFile.getAbsolutePath());
		w.setStatus(false);
		w.setTop(false);
		wsbList.get(0).setTop(false);
		wsbList.add(w);
		// 保存到文件中
		boolean isOk = SelectWorkSpaceDialog.save(wsbList);
		if (!isOk) {
			DialogUtil.promptError("创建目录失败!");
			return;
		}
		// TCDMenu menu = WorkflowControler.getInstance().getDesktopMain()
		// .getResetWorkSpaceMenu();
		// WorkflowControler.loadWorkSpaceMap(menu);
		wsbList = SelectWorkSpaceDialog.load();
		DefaultListModel mode = (DefaultListModel) workSpaceList.getModel();
		mode.removeAllElements();
		for (int i = 0; i < wsbList.size(); i++) {
			mode.add(i, wsbList.get(i));
		}
		DialogUtil.promptMessage("添加成功!");
	}

	/**
	 * 删除按钮事件
	 */
	private void delButtonClick() {
		log.debug("点击 删除工作目录 按钮");
		DefaultListModel mode = (DefaultListModel) workSpaceList.getModel();
		int index = workSpaceList.getSelectedIndex();
		if (index == -1) {
			DialogUtil.promptMessage("您没有选择工作目录!", this);
			return;
		}
		String item = mode.getElementAt(index) + "";
		String workSpace = Application.workSpace.getAbsolutePath();
		if (item.equalsIgnoreCase(workSpace)) {
			DialogUtil.promptMessage("不能删除当前工作目录", this);
			return;
		}
		if (DialogUtil.confirmDialog("你确定要删除所选工作目录吗?", this) == DialogUtil.SELECT_YES) {
			List<WorkSpaceBean> wsbList = SelectWorkSpaceDialog.load();
			WorkSpaceBean temp = null;
			for (WorkSpaceBean w : wsbList) {
				if (w.getPath().equalsIgnoreCase(item)) {
					temp = w;
					break;
				}
			}
			if (temp != null) {
				wsbList.remove(temp);
				boolean result = SelectWorkSpaceDialog.save(wsbList);
				if (!result) {
					DialogUtil.promptWarning("不能删除当前工作目录", this);
					return;
				}
				// TCDMenu menu =
				// WorkflowControler.getInstance().getDesktopMain()
				// .getResetWorkSpaceMenu();
				// WorkflowControler.loadWorkSpaceMap(menu);
			}
			// 重新加载
			wsbList = SelectWorkSpaceDialog.load();
			mode.removeAllElements();
			for (int i = 0; i < wsbList.size(); i++) {
				mode.add(i, wsbList.get(i));
			}
			DialogUtil.promptMessage("删除成功!");
		}
	}
}
