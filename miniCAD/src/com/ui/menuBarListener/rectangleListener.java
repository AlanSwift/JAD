package com.ui.menuBarListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.tools.apply;
import com.tools.myListener;
import com.constant.constants;
import com.tools.shapes.rectangleShape;
import com.miniCAD;

public class rectangleListener implements ActionListener {

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        myListener.setType(constants.RECTANGLE);
        rectangleShape tRectangleShape=new rectangleShape();
        tRectangleShape.setColor(miniCAD.getColor());
        apply tApply=new apply(tRectangleShape);
        myListener.setMyApply(tApply);
        myListener.setOperOnApply(tApply);
    }
}
