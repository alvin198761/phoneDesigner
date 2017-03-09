package com.android.gui.dialog.select;//package com.android.gui.dialog.select;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.Rectangle;
//import java.awt.event.AdjustmentEvent;
//import java.awt.event.AdjustmentListener;
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.JFileChooser;
//import javax.swing.JScrollPane;
//import javax.swing.filechooser.FileFilter;
//import javax.swing.filechooser.FileNameExtensionFilter;
//
//import org.apache.log4j.Logger;
//
//import spasvo.tcder.gui.dialog.fileSelect.IDefaultFileSelectDialogListener;
//import spasvo.tcder.uitl.DialogUtil;
//
//import com.printer.biz.IPrinterBiz;
//import com.printer.biz.impl.PrinterBizImpl;
//import com.printer.gui.DrawPanel;
//import com.printer.model.IShape;
//
//public class TCDFileChooserDialog extends JFileChooser implements
//		IDefaultFileSelectDialogListener {
//
//	private static final Logger log = Logger
//			.getLogger(TCDFileChooserDialog.class);
//
//	/** vkd 格式 */
//	public static final String[][] VKD = new String[][] { {
//			"TCD Editor (*.vkd)", "vkd" } };
//	/** visio 格式 */
//	public static final String[][] VSD = new String[][] { {
//			"Microsoft Visio (*.vsd)", "vsd" } };
//	/** CSV 格式 */
//	public static final String[][] CSV = new String[][] { { "CSV (*.csv)",
//			"csv" } };
//	/** visio和vkd格式 */
//	public static final String[][] VKD_VSD = new String[][] {
//			{ "Microsoft Visio (*.vsd)", "vsd" },
//			{ "TCD Editor (*.vkd)", "vkd" } };
//	/** Excel格式 */
//	public static final String[][] EXCEL = new String[][] { {
//			"Microsoft Excel (*.xls)", "xls" } };
//
//	private static final long serialVersionUID = 1L;
//	private static JScrollPane panel;
//	private static ImagePreview preview;
//
//	@Override
//	public File getFinalSelectFile() {
//		File f = getSelectedFile();
//		if (f == null) {
//			return null;
//		}
//		String fn = f.getName().toLowerCase();
//		String fileType = getFileFilterType();
//		if (fileType.equals("all")) {
//			return f;
//		}
//		if (fn.endsWith("." + fileType)) {
//			return f;
//		}
//		f = new File(f.getAbsoluteFile() + "." + fileType);
//		log.debug("file = " + f.getAbsolutePath());
//		return f;
//	}
//
//	/**
//	 * 获取当前选中的过滤器的后缀类型
//	 * 
//	 * 
//	 * @return "all"则是全文件格式
//	 */
//	public String getFileFilterType() {
//		FileFilter ff = getFileFilter();
//		if (ff instanceof FileNameExtensionFilter) {
//			FileNameExtensionFilter fef = (FileNameExtensionFilter) ff;
//			return fef.getExtensions()[0];
//		} else {
//			return "all";
//		}
//	}
//
//	@Override
//	public String getFinalSelectFilePath() {
//		if (getFinalSelectFile() == null) {
//			return null;
//		}
//		return getFinalSelectFile().getAbsolutePath();
//	}
//
//	@Override
//	public String getFinalSelectFilePaths() {
//		File[] files = getFinalSelectFiles();
//		String paths = "";
//		if (files != null && files.length > 0) {
//			for (File f : files) {
//				paths += "," + f.getAbsolutePath();
//			}
//		}
//		if (paths.length() > 1) {
//			return paths.substring(1);
//		}
//		return paths;
//	}
//
//	@Override
//	public File[] getFinalSelectFiles() {
//		return getSelectedFiles();
//	}
//
//	private static TCDFileChooserDialog fileChooserDialog = new TCDFileChooserDialog();
//	static {
//		preview = new ImagePreview(fileChooserDialog);
//		panel = new JScrollPane();
//		panel.setViewportView(preview);
//		panel.getHorizontalScrollBar().addAdjustmentListener(
//				new AdjustmentListener() {
//
//					@Override
//					public void adjustmentValueChanged(AdjustmentEvent e) {
//						preview.repaint();
//					}
//				});
//		panel.getVerticalScrollBar().addAdjustmentListener(
//				new AdjustmentListener() {
//
//					@Override
//					public void adjustmentValueChanged(AdjustmentEvent e) {
//						preview.repaint();
//					}
//				});
//		panel.setPreferredSize(new Dimension(300, 300));
//	}
//
//	private TCDFileChooserDialog() {
//
//	}
//
//	/**
//	 * 返回一个公共的组件 包括 只选择文件/单选/重置过滤器/屏蔽"所有文件"过滤器/加入用户过滤器 其他功能需要使用时候 自定义.例如:多选/保存目录
//	 * 
//	 * @param filetype
//	 * @return
//	 */
//	public static TCDFileChooserDialog getPreViewInstance(String[][] filetype) {
//		fileChooserDialog.setFileSelectionMode(FILES_ONLY);
//		fileChooserDialog.setMultiSelectionEnabled(false);
//		fileChooserDialog.resetChoosableFileFilters();
//		fileChooserDialog.setAcceptAllFileFilterUsed(false);
//		fileChooserDialog.setSelectedFile(null);
//
//		fileChooserDialog.setAccessory(panel);
//		fileChooserDialog.updateUI();
//		for (String[] ff : filetype) {
//			FileNameExtensionFilter fef = new FileNameExtensionFilter(ff[0],
//					ff[1]);
//			fileChooserDialog.addChoosableFileFilter(fef);
//		}
//		return fileChooserDialog;
//	}
//
//	/**
//	 * 返回一个公共的组件 包括 只选择文件/单选/重置过滤器/屏蔽"所有文件"过滤器/加入用户过滤器 其他功能需要使用时候 自定义.例如:多选/保存目录
//	 * 
//	 * @param filetype
//	 * @return
//	 */
//	public static TCDFileChooserDialog getInstance(String[][] filetype) {
//		fileChooserDialog.setFileSelectionMode(FILES_ONLY);
//		fileChooserDialog.setMultiSelectionEnabled(false);
//		fileChooserDialog.resetChoosableFileFilters();
//		fileChooserDialog.setAcceptAllFileFilterUsed(false);
//		fileChooserDialog.setAccessory(null);
//		fileChooserDialog.setSelectedFile(null);
//		fileChooserDialog.updateUI();
//		for (String[] ff : filetype) {
//			FileNameExtensionFilter fef = new FileNameExtensionFilter(ff[0],
//					ff[1]);
//			fileChooserDialog.addChoosableFileFilter(fef);
//		}
//		return fileChooserDialog;
//	}
//
//	static class ImagePreview extends DrawPanel implements
//			PropertyChangeListener {
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//		IPrinterBiz printerBiz = new PrinterBizImpl();
//		FileFilter filter = new FileNameExtensionFilter("VDK file", "vkd",
//				"vsd");
//		private JFileChooser jfc;
//
//		public ImagePreview(TCDFileChooserDialog fileChooserDialog) {
//			super(true);
//			this.setEnabled(false);
//			this.jfc = fileChooserDialog;
//			jfc.setFileFilter(filter);
//			jfc.addPropertyChangeListener(this);
//			scale = 0.4;
//			actionFlag = false;
//		}
//
//		public void propertyChange(PropertyChangeEvent evt) {
//			try {
//				File file = jfc.getSelectedFile();
//				updateImage(file);
//			} catch (IOException ex) {
//				System.out.println(ex.getMessage() + "文件已经处在打开状态");
//			}
//		}
//
//		public void updateImage(File file) throws IOException {
//
//			if (file == null) {
//				this.setAllShapes(new ArrayList<IShape>());
//			} else {
//				String path = file.getAbsolutePath();
//				if (path.endsWith("vsd")) {
//					// if (map.containsKey(path)) {
//					// List<IShape> list = map.get(path);
//					// this.setAllShapes(list);
//					// } else {
//					// List<IShape> list = printerBiz.analyseVisioFile(file);
//					// this.setAllShapes(list);
//					// map.put(file.getAbsolutePath(), list);
//					// }
//					this.setAllShapes(new ArrayList<IShape>());
//				} else if (path.endsWith("vkd")) {
//					List<IShape> list = printerBiz.loadVKDShapesImage(file);
//					this.setAllShapes(list);
//				}
//			}
//			repaint();
//		}
//
//		public void paintComponent(Graphics g) {
//			super.paintComponent(g);
//			if (getAllShapes() == null || getAllShapes().size() <= 0) {
//				g.setColor(Color.black);
//				Rectangle visibleRect = getVisibleRect();
//				g.drawString("没有预览",
//						visibleRect.x + visibleRect.width / 2 - 20,
//						visibleRect.y + visibleRect.height / 2);
//			}
//		}
//	}
//
//	@Override
//	public void approveSelection() {
//		if (getDialogType() == SAVE_DIALOG) {
//			File fi = getSelectedFile();
//			if (fi == null) {
//				super.approveSelection();
//				return;
//			}
//			if (fi.exists()) {
//				int reuslt = DialogUtil.confirmDialog("文件\"" + fi.getName()
//						+ "\"已经存在!是否替换原文件?", this);
//				if (reuslt != DialogUtil.SELECT_YES) {
//					return;
//				}
//			}
//		} else if (getDialogType() == OPEN_DIALOG) {
//			File fi = getSelectedFile();
//			if (fi == null || (fi != null && !fi.exists())) {
//				DialogUtil.promptMessage("文件不存在!");
//				return;
//			}
//		}
//		super.approveSelection();
//	}
//
//	public static void main(String[] args) {
//		/*
//		 * 示例
//		 */
//		// MyFileChooserDialog fcd = MyFileChooserDialog.getInstance(
//		// JFileChooser.SAVE_DIALOG, "File(*.xml)", "xml");
//		// fcd.showSaveDialog(null);
//		TCDFileChooserDialog fcd = TCDFileChooserDialog.getInstance(VKD_VSD);
//		int i = fcd.showSaveDialog(null);
//		System.out.println(i);
//		File f = fcd.getFinalSelectFile();
//		FileNameExtensionFilter ff = (FileNameExtensionFilter) fcd
//				.getFileFilter();
//		System.out.println(ff.getExtensions()[0]);
//		System.out.println(f.getAbsolutePath());
//		System.exit(0);
//	}
//}