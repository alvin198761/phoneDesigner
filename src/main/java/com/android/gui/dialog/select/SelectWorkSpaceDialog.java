package com.android.gui.dialog.select;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.android.lang.Application;
import com.android.reource.ResourceUtil;

@SuppressWarnings("serial")
public class SelectWorkSpaceDialog extends JDialog implements
		IDefaultFileSelectDialogListener {
	private static final Logger log = Logger
			.getLogger(SelectWorkSpaceDialog.class);
	/** 工作目录配置文件路径 */
	private static String workspaceconfigerpath = "WorkSpaceConfig.xml";

	private JFileChooser workSpaceDirectChooser;
	private JComboBox selectFileComboBox;
	private JPanel buttonPane;
	private JPanel workSpaceInfoPanel;
	private boolean directIsCrete = true;

	private String lastTimeChooser = "";

	public SelectWorkSpaceDialog() {
		initDefaultInfo();
		initComponent();
		initButtons();
		initOthers();
		invalidate();
		lastTimeChooser = "";
		initByXML();
		// selectFileComboBox.setUI(new DefaultComboBoxUI());
		// ((JTextField) (selectFileComboBox.getEditor().getEditorComponent()))
		// .selectAll();
	}

	/**
	 * 根据xml初始化
	 */
	private void initByXML() {
		PathComboBoxModel model = (PathComboBoxModel) selectFileComboBox
				.getModel();
		List<WorkSpaceBean> list = load();
		// 重新从xml中读取出来 显示在下拉框中
		model.removeAllElements();
		for (WorkSpaceBean w : list) {
			model.addElement(w);
		}
		WorkSpaceBean wsb;
		if (model.getSize() == 0) {
			wsb = new WorkSpaceBean();
			wsb.setPath("C:\\workSpace");
			wsb.setStatus(false);
			wsb.setTop(true);
			model.addElement(wsb);
			// selectFileComboBox.setModel(model);
			selectFileComboBox.setSelectedIndex(0);
			// selectFileComboBox.updateUI();
			return;
		}
		// selectFileComboBox.setModel(model);
		boolean isSelect = false;
		for (int i = 0; i < list.size(); i++) {
			WorkSpaceBean w = list.get(i);
			if (w == null) {
				return;
			}
			if (w.isTop()) {
				selectFileComboBox.setSelectedIndex(i);
				isSelect = true;
				// selectFileComboBox.updateUI();
				break;
			}
		}
		if (!isSelect) {
			selectFileComboBox.setSelectedIndex(0);
		}
	}

	// OK的事件
	private ActionListener okBtnListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 进入程序
				buttonClickLog(e);
				okbtnClick();
			}
		};
	}

	// 取消的事件
	private ActionListener cancelBtnListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClickLog(e);
				cancelClick();
			}
		};
	}

	// 选择文件的按钮事件
	private ActionListener browserBtnActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonClickLog(e);
				browserAction();
			}
		};
	}

	/**
	 * 选择文件的按钮事件 的方法
	 */
	private void browserAction() {
		workSpaceDirectChooser.setSelectedFile(null);
		workSpaceDirectChooser.updateUI();
		log.debug("打开文件选择器");
		int result = this.workSpaceDirectChooser.showOpenDialog(this);
		if (result != JFileChooser.APPROVE_OPTION) {
			log.debug("没有选择文件");
			return;
		}
		File selectFile = workSpaceDirectChooser.getSelectedFile();
		if (selectFile != null) {
			log.debug("选择文件 = " + selectFile.getAbsolutePath());
			String path = selectFile.getAbsolutePath();
			if (path == null || path.equals("")) {
				return;
			}
			PathComboBoxModel model = (PathComboBoxModel) selectFileComboBox
					.getModel();
			// 判断当前选择的文件在工作目录是否存在
			WorkSpaceBean w;
			int size = model.getSize();
			for (int i = 0; i < size; i++) {
				w = model.getElementAt(i);
				if (w.getPath().equalsIgnoreCase(path)) {
					// 该目录已经存在
					selectFileComboBox.setSelectedIndex(i);
					// selectFileComboBox.updateUI();
					return;
				}
			}
			// 该目录还不存在
			// 删掉没有 使用的目录
			if (!lastTimeChooser.equals("")) {
				for (int i = 0; i < selectFileComboBox.getItemCount(); i++) {
					String item = selectFileComboBox.getItemAt(i).toString();
					if (item.equalsIgnoreCase(lastTimeChooser)) {
						initByXML();
						break;
					}
				}
			}
			// 添加当前选择的路径
			w = new WorkSpaceBean();
			w.setPath(path);
			w.setStatus(false);
			w.setTop(true);
			model.addElement(w);
			selectFileComboBox.setSelectedIndex(selectFileComboBox
					.getItemCount() - 1);
			// 保存当前选择的路径
			lastTimeChooser = path;
			selectFileComboBox.updateUI();
		}
	}

	/**
	 * 点击取消的方法
	 */
	private void cancelClick() {
		if (Application.workSpace != null) {
			selectFileComboBox.setSelectedItem(Application.workSpace
					.getAbsolutePath());
			setVisible(false);
		} else {
			System.exit(0);
		}
	}

	/**
	 * 确定按钮事件的方法
	 */
	private void okbtnClick() {
		// 记住选择的工作空间
		String path = selectFileComboBox.getSelectedItem().toString();
		if (path.endsWith("/") || path.endsWith("\\")) {
			JOptionPane.showMessageDialog(this, "您输入具体的文件夹名!", "提示",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		// 得到所有的工作目录
		List<WorkSpaceBean> wsbList = load();
		boolean exist = false;
		for (WorkSpaceBean w : wsbList) {
			if (w.getPath().equalsIgnoreCase(path.trim())) {
				// 如果该目录存在 设为最后选择的工作目录
				exist = true;
				w.setTop(true);
			} else {
				// 其他目录全部设为不是最后选择的工作目录
				w.setTop(false);
			}
		}
		// 工作目录不存在的话就创建一个添加进去
		boolean iscreate = false;
		if (!exist) {
			if (!path.matches("^[^*?\"<>|]+$")) {
				JOptionPane.showMessageDialog(this, "文件夹名不能包含 *?\"<>| 等特殊字符!",
						"提示", JOptionPane.WARNING_MESSAGE);
				wsbList.get(0).setTop(true);
				return;
			}
			// 根据当前选择的目录创建文件夹
			File selectFile = new File(path);
			if (!selectFile.exists()) {
				iscreate = selectFile.mkdirs();
				if (!iscreate) {
					JOptionPane.showMessageDialog(this, "该目录不能创建文件夹!", "提示",
							JOptionPane.WARNING_MESSAGE);
					wsbList.get(0).setTop(true);
					return;
				}
			}
			WorkSpaceBean w = new WorkSpaceBean();
			w.setPath(path);
			w.setStatus(false);
			w.setTop(true);
			wsbList.add(0, w);
		}
		// 保证只有10个
		while (wsbList.size() > 10) {
			wsbList.remove(wsbList.size() - 1);
		}
		// 保存到xml中去
		boolean result = save(wsbList);
		if (!result) {
			JOptionPane.showMessageDialog(this, "创建目录失败!", "提示",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		lastTimeChooser = "";
		Application.workSpace = new File(path);
		// 隐藏
		this.setVisible(false);
	}

	@Override
	public File getFinalSelectFile() {
		return new File(this.selectFileComboBox.getSelectedItem().toString()
				+ File.separator);
	}

	@Override
	public String getFinalSelectFilePath() {
		return this.selectFileComboBox.getSelectedItem().toString()
				+ File.separator;
	}

	@Override
	public String getFinalSelectFilePaths() {
		return this.selectFileComboBox.getSelectedItem().toString()
				+ File.separator;
	}

	@Override
	public File[] getFinalSelectFiles() {
		return new File[] { new File(this.selectFileComboBox.getSelectedItem()
				.toString() + File.separator) };
	}

	public void initButtons() {

		JLabel labelTitel = new JLabel("选择工作目录");
		labelTitel.setFont(new java.awt.Font("Dialog", 1, 15));
		labelTitel.setBounds(2, 0, this.getSize().width, 30);
		labelTitel.setBackground(Color.white);
		workSpaceInfoPanel.add(labelTitel);

		JLabel lblCompanyInfo = new JLabel("工作目录是保存了Android Designer项目内容的文件夹");
		lblCompanyInfo.setBounds(12, 29, 268, 15);
		lblCompanyInfo.setBackground(Color.white);
		workSpaceInfoPanel.add(lblCompanyInfo);

		JLabel lblChoosePath = new JLabel("请选择工作目录");
		lblChoosePath.setBounds(12, 47, 268, 15);
		lblChoosePath.setBackground(Color.white);
		workSpaceInfoPanel.add(lblChoosePath);

		selectFileComboBox = new JComboBox(new PathComboBoxModel()) {
			// public void updateUI() {
			// super.setUI(new SubstanceComboBoxUI() {
			// public void installListeners() {
			// if (lafWidgets != null)
			// lafWidgets.clear();
			// super.installListeners();
			// }
			//
			// public void installComponents() {
			// if (lafWidgets != null)
			// lafWidgets.clear();
			// super.installComponents();
			// }
			//
			// public void installUI(JComponent com) {
			// if (lafWidgets != null)
			// lafWidgets.clear();
			// super.installUI(com);
			// }
			// });
			// ListCellRenderer renderer = getRenderer();
			// if (renderer instanceof Component) {
			// SwingUtilities.updateComponentTreeUI((Component) renderer);
			// }
			// }
		};

		selectFileComboBox.setBounds(78, 85, 257, 24);
		// if (workSpaceMap.size() == 0) {
		// selectFileComboBox.addItem("D:\\workspace");
		// }
		selectFileComboBox.setEditable(true);
		getContentPane().add(selectFileComboBox);

		JButton browserBtn = new JButton("选 择");
		browserBtn.addActionListener(browserBtnActionListener());
		browserBtn.setBounds(347, 85, 95, 25);
		getContentPane().add(browserBtn);

		JLabel label = new JLabel("工作目录 : ");
		label.setBounds(9, 90, 68, 15);
		getContentPane().add(label);

		// 操作按钮
		JButton okButton = new JButton("确 定");
		okButton.setBounds(277, 5, 73, 21);
		okButton.addActionListener(okBtnListener());
		buttonPane.setLayout(null);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("取 消");
		cancelButton.setBounds(362, 5, 73, 21);
		cancelButton.addActionListener(cancelBtnListener());
		buttonPane.add(cancelButton);

		// 选择框 暂时不添加
		// JCheckBox checkBox = new JCheckBox(
		// "User this as the default and not ask again");
		// checkBox.setBounds(10, 199, this.getSize().width, 23);
		// getContentPane().add(checkBox);
	}

	public void initComponent() {

		workSpaceDirectChooser = new JFileChooser();
		workSpaceDirectChooser
				.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		workSpaceInfoPanel = new JPanel();
		workSpaceInfoPanel.setBounds(-1, 10, 459, 68);
		workSpaceInfoPanel.setBackground(Color.white);
		getContentPane().add(workSpaceInfoPanel);
		workSpaceInfoPanel.setLayout(null);

		getContentPane().setLayout(null);
		buttonPane = new JPanel();
		buttonPane.setBounds(6, 125, 452, 35);
		getContentPane().add(buttonPane);

	}

	public void initComponentByFlowType(int flowType) {
		// 不实现
	}

	public void initDefaultInfo() {
		setTitle("启动工作目录");
		setIconImage(ResourceUtil.logo_img);
		setSize(464, 203);
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// 关闭后退出程序
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (Application.workSpace != null) {
					setVisible(false);
				} else {
					System.exit(0);
				}
			}
		});
	}

	public void initOthers() {

	}

	/**
	 * 工作目录是否创建成功
	 * 
	 * @return
	 */
	public boolean isDirectIsCrete() {
		return directIsCrete;
	}

	/**
	 * 加载工作目录
	 */
	@SuppressWarnings("unchecked")
	public static List<WorkSpaceBean> load() {
		Document doc = getDocument();
		Element root = doc.getRootElement();
		List<Element> subElements = root.elements("directory");
		List<WorkSpaceBean> workSpaceList = new ArrayList<WorkSpaceBean>();
		WorkSpaceBean wsb;
		int size = 0;
		for (Element el : subElements) {
			size++;
			wsb = new WorkSpaceBean();
			String path = el.attributeValue("path");
			if (path == null || "".equals(path.trim())) {
				continue;
			}
			String status = el.attributeValue("status");
			String top = el.attributeValue("top");
			wsb.setPath(path);
			try {
				if (status != null) {
					wsb.setStatus(Boolean.parseBoolean(status));
				}
			} catch (Exception e) {
				wsb.setStatus(false);
			}
			try {
				if (top != null) {
					wsb.setTop(Boolean.parseBoolean(top));
				}
			} catch (Exception e) {
				wsb.setTop(false);
			}
			workSpaceList.add(wsb);
			// 只显示前10个
			if (size == 10) {
				break;
			}
		}
		if (workSpaceList.size() == 1) {
			return workSpaceList;
		}
		// 将默认选中的排到第一个
		WorkSpaceBean temp = null;
		for (WorkSpaceBean w : workSpaceList) {
			if (w.isTop()) {
				temp = w;
				break;
			}
		}
		// 最近使用的一个
		if (temp != null) {
			workSpaceList.remove(temp);
			workSpaceList.add(0, temp);
		}

		return workSpaceList;
	}

	/**
	 * 保存
	 */
	@SuppressWarnings("unchecked")
	public static boolean save(List<WorkSpaceBean> list) {
		Document doc = getDocument();
		Element root = doc.getRootElement();
		List<Element> eList = root.elements();
		eList.clear();
		Element node;
		for (WorkSpaceBean wsb : list) {
			node = root.addElement("directory");
			node.addAttribute("path", wsb.getPath());
			node.addAttribute("status", wsb.isStatus() + "");
			node.addAttribute("top", wsb.isTop() + "");
		}
		return writeDocument(doc);
	}

	/**
	 * 得到文档对象
	 * 
	 * @return
	 */
	public static Document getDocument() {
		Document doc = null;
		File f = new File(workspaceconfigerpath);
		if (!f.exists()) {
			// 创建一个xml
			doc = DocumentHelper.createDocument();
			Element root = doc.addElement("workSpace");
			Element defaultElement = root.addElement("directory");
			defaultElement.addAttribute("path", "C:\\workSpace");
			defaultElement.addAttribute("status", "false");
			defaultElement.addAttribute("top", "true");
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter output;
			try {
				format.setEncoding("utf-8");
				output = new XMLWriter(new FileWriter(f), format);
				output.write(doc);
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		SAXReader reader = new SAXReader();
		try {
			doc = reader.read(f);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 写入文档对象
	 */
	public static boolean writeDocument(Document doc) {
		File f = new File(workspaceconfigerpath);
		// 如果文件不存在
		try {
			FileOutputStream fos = new FileOutputStream(f);
			OutputStreamWriter os = new OutputStreamWriter(fos, "utf-8");
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter xw = new XMLWriter(os, format);
			xw.write(doc);
			xw.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 按钮点击事件的log
	 * 
	 * @param ev
	 */
	protected void buttonClickLog(ActionEvent ev) {
		AbstractButton btn = (AbstractButton) ev.getSource();
		String text = btn.getText();
		if (text == null || "".equals(text)) {
			text = btn.getToolTipText();
		}
		log.debug("Srouce = ".concat(text).concat(",Operate = click"));
	}

}

/**
 * 下拉框的model
 * 
 * @author
 * 
 */
class PathComboBoxModel extends DefaultComboBoxModel {
	private static final long serialVersionUID = 1L;

	@Override
	public WorkSpaceBean getElementAt(int index) {
		return (WorkSpaceBean) super.getElementAt(index);
	}
}
