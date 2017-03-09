package com.android.gui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.BaseAndroidButton;
import com.android.gui.comp.ui.CheckBoxListCellRenderer;
import com.android.gui.comp.ui.RadioButtonListCellRenderer;
import com.android.gui.model.ActionCMDListMode;
import com.android.gui.model.EventTreeModel;
import com.android.gui.model.ListPageModel;
import com.android.lang.Application;
import com.runner.entity.ManuEntity;
import com.runner.entity.ManualActionEntity;
import com.runner.entity.PageActionEntity;
import com.runner.entity.StringNode;

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
public class AddEventDialog extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JButton jButton1;
	private JButton jButton2;
	private JList jList1;
	private JTree jTree1;
	private int dialogStatus = 0;

	private List<ManualActionEntity> manuActionList = new ArrayList<ManualActionEntity>();
	private PageActionEntity pageAction;

	public AddEventDialog(final ProjectEntity project,
			final AndroidPageContainer page, final BaseAndroidButton component,
			final int index) {
		super(Application.mainFrame, true);
		setIconImage(Application.mainFrame.getIconImage());
		setTitle("添加事件");
		initGUI();
		getRootPane().setDefaultButton(jButton2);
		setResizable(false);
		jTree1.setModel(new EventTreeModel(project, component, index));
		jTree1.setRootVisible(false);
		jTree1.setShowsRootHandles(true);
		jTree1.getSelectionModel().addTreeSelectionListener(
				new TreeSelectionListener() {

					@Override
					public void valueChanged(TreeSelectionEvent e) {
						Object node = jTree1.getLastSelectedPathComponent();
						// System.out.println(node.getClass());
						if (node instanceof StringNode) {
							jList1.setModel(new DefaultListModel());
							jList1.setCellRenderer(new DefaultListCellRenderer());
						} else if (node instanceof ManualActionEntity) {
							jList1.setModel(new ActionCMDListMode(project,
									(ManualActionEntity) node));
							jList1.setCellRenderer(new CheckBoxListCellRenderer());
							for (int i = 0; i < jList1.getModel().getSize(); i++) {
								ManuEntity me = (ManuEntity) jList1.getModel()
										.getElementAt(i);
								ManualActionEntity actionEntity = (ManualActionEntity) node;
								int index = actionEntity.getManuList().indexOf(
										me);
								me.setSelected(index != -1);
							}
						} else if (node instanceof PageActionEntity) {
							jList1.setModel(new ListPageModel(project, page,
									(PageActionEntity) node));
							jList1.setCellRenderer(new RadioButtonListCellRenderer());
							PageActionEntity paction = (PageActionEntity) node;
							for (int i = 0; i < jList1.getModel().getSize(); i++) {
								AndroidPageContainer page = (AndroidPageContainer) jList1
										.getModel().getElementAt(i);
								if (page.getName().equals(
										paction.getForwardPage())) {
									jList1.setSelectedIndex(i);
									break;
								}
							}
						}
					}
				});
		jList1.setModel(new DefaultListModel());
		jList1.setCellRenderer(new DefaultListCellRenderer());
		jList1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// if (e.getClickCount() < 2) {
				// return;
				// }
				if (jList1.getSelectedValue() instanceof ManuEntity) {
					ManuEntity entity = (ManuEntity) jList1.getSelectedValue();
					entity.setSelected(!entity.isSelected());
					jList1.repaint();
					doChangeTreeData(entity);
				}
			}

		});
		jList1.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						if (jList1.getSelectedValue() instanceof AndroidPageContainer) {
							AndroidPageContainer page = (AndroidPageContainer) jList1
									.getSelectedValue();
							pageAction = (PageActionEntity) jTree1
									.getLastSelectedPathComponent();
							pageAction.setForwardPage(page.getName());

							PageActionEntity obj = (PageActionEntity) jTree1
									.getLastSelectedPathComponent();
							pageAction.setType(obj.getType());
							pageAction.setTitle(obj.getTitle());
							pageAction.setAnimated(obj.getAnimated());
						}
					}
				});

		jButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EventTreeModel mode = (EventTreeModel) jTree1.getModel();
				Object obj = mode.getChild(mode.getRoot(), 0);
				int size = mode.getChildCount(obj);
				for (int i = 0; i < size; i++) {
					ManualActionEntity action = (ManualActionEntity) mode
							.getChild(obj, i);
					if (action.getManuList() != null) {
						manuActionList.add(action);
					}
				}
				dialogStatus = 1;
				setVisible(false);
			}
		});

		jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialogStatus = 0;
				setVisible(false);
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dialogStatus = 0;
				setVisible(false);
				super.windowClosing(e);
			}
		});
	}

	// 添加或删除一个设备
	private void doChangeTreeData(ManuEntity entity) {
		System.out.println(jTree1.getLastSelectedPathComponent());
		ManualActionEntity node = (ManualActionEntity) jTree1
				.getLastSelectedPathComponent();
		node.getManuList().clear();
		for (int i = 0; i < jList1.getModel().getSize(); i++) {
			ManuEntity me = (ManuEntity) jList1.getModel().getElementAt(i);
			if (me.isSelected()) {
				node.getManuList().add(me);
			}
		}
	}

	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					jLabel1 = new JLabel();
					getContentPane().add(jLabel1);
					jLabel1.setText("第一步：选择动作");
					jLabel1.setBounds(24, 25, 189, 17);
				}
				{
					jLabel2 = new JLabel();
					getContentPane().add(jLabel2);
					jLabel2.setText("第二步：选择交互对象");
					jLabel2.setBounds(312, 25, 227, 17);
				}
				{
					jScrollPane1 = new JScrollPane();
					getContentPane().add(jScrollPane1);
					jScrollPane1.setBounds(24, 59, 267, 290);
					{
						jTree1 = new JTree();
						jScrollPane1.setViewportView(jTree1);
					}
				}
				{
					jScrollPane2 = new JScrollPane();
					getContentPane().add(jScrollPane2);
					jScrollPane2.setBounds(312, 54, 264, 295);
					{
						ListModel jList1Model = new DefaultComboBoxModel(
								new String[] {});
						jList1 = new JList();
						jScrollPane2.setViewportView(jList1);
						jList1.setModel(jList1Model);
						jList1.setPreferredSize(new java.awt.Dimension(157, 233));
					}
				}
				{
					jButton1 = new JButton();
					getContentPane().add(jButton1);
					jButton1.setText("取消");
					jButton1.setBounds(184, 377, 100, 24);
				}
				{
					jButton2 = new JButton();
					getContentPane().add(jButton2);
					jButton2.setText("确定");
					jButton2.setBounds(318, 377, 98, 24);
				}
			}
			this.setSize(612, 461);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getDialogStatus() {
		return this.dialogStatus;
	}

	public PageActionEntity getActionEntity() {
		return this.pageAction;
	}

	public List<ManualActionEntity> getManuAction() {
		return this.manuActionList;
	}

}
