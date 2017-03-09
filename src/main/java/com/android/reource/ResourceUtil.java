package com.android.reource;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.android.utils.Log;

/**
 * 图片资源的加载和分配
 *
 * @author issuser
 */
public class ResourceUtil {

    private ResourceUtil() {
    }

    public static Image right_img;
    public static Image wrong_img;
    public static Image myface_img;
    public static Icon new_icon;
    public static Icon line_icon;
    public static Icon open_icon;
    public static Icon save_Icon;
    public static Icon saveAs_Icon;
    public static Icon copy_icon;
    public static Icon cut_icon;
    public static Icon paste_icon;
    public static Icon del_icon;
    public static Icon redo_icon;
    public static Icon selectAll_icon;
    public static Icon undo_icon;
    public static Icon help_icon;
    public static Icon property_icon;
    public static Icon defaultCurs_icon;
    public static Icon zoomIn_icon;
    public static Icon zoomOut_icon;
    public static Icon zoomReset_icon;

    public static Image logo_img = getImage("img/logo.gif");
    public static Icon bg_icon = getIcon("img/bg.gif");
    //
    public static Icon proj_icon;
    public static Icon comp_icon;
    public static Icon page_icon;
    public static Icon run_icon;
    public static Icon stop_icon;
    public static Icon close_icon;

    // 鼠标
    public static Cursor rightCursor;
    public static Cursor wrongCursor;
    public static ImageIcon defaultImage_Icon;
    //
    public static Icon button_icon;
    public static Icon label_icon;
    public static Icon imageView_icon;
    public static Icon osShapeButton_icon;

    public static void loadResource() {
        Log.info("资源加载中……");
        // cursor
        right_img = getImage("img/right.gif");
        wrong_img = getImage("img/wrong.gif");

        rightCursor = Toolkit.getDefaultToolkit().createCustomCursor(right_img,
                new Point(0, 0), "move_right");
        wrongCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                ResourceUtil.wrong_img, new Point(0, 0), "move_wrong");
        // about
        myface_img = getImage("img/myface.bmp");
        // file menu
        new_icon = getIcon("img/new.gif");
        open_icon = getIcon("img/open.gif");
        save_Icon = getIcon("img/save.gif");
        saveAs_Icon = getIcon("img/saveas.gif");
        // toolbar button
        line_icon = getIcon("img/line.gif");
        defaultCurs_icon = getIcon("img/mouse.gif");
        // edit menu
        copy_icon = getIcon("img/copy.gif");
        cut_icon = getIcon("img/cut.gif");
        paste_icon = getIcon("img/paste.gif");
        del_icon = getIcon("img/delete.gif");
        redo_icon = getIcon("img/redo.gif");
        selectAll_icon = getIcon("img/selectAll.gif");
        undo_icon = getIcon("img/undo.gif");

        zoomIn_icon = getIcon("img/zoomin.png");
        zoomOut_icon = getIcon("img/zoomout.png");
        zoomReset_icon = getIcon("img/zoomreset.png");
        // help menu
        help_icon = getIcon("img/help.gif");
        property_icon = getIcon("img/property.gif");
        //
        proj_icon = getIcon("img/project.gif");
        comp_icon = getIcon("img/component.gif");
        page_icon = getIcon("img/page.gif");
        run_icon = getIcon("img/run.gif");
        stop_icon = getIcon("img/stop.gif");
        close_icon = getIcon("img/close.gif");
        //
        defaultImage_Icon = (ImageIcon) getIcon("img/defaultImageIcon.png");
        //
        button_icon = getIcon("img/contrl_Button.png");
        label_icon = getIcon("img/contrl_labell.png");
        imageView_icon = getIcon("img/contrl_imageView.png");
        osShapeButton_icon = getIcon("img/contrl_osShapeButton.png");
        Log.info("资源加载完毕");
    }

    private static Image getImage(String name) {
         System.out.println(name);
        return Toolkit.getDefaultToolkit().getImage(
                ResourceUtil.class.getResource("/" + name));
    }

    private static Icon getIcon(String fileName) {
        return new ImageIcon(getImage(fileName));
    }
}
