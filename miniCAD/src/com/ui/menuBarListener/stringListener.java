package com.ui.menuBarListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tools.apply;
import com.tools.myListener;
import com.constant.constants;
import com.tools.shapes.stringShape;
import com.miniCAD;

public class stringListener implements ActionListener {

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        myListener.setType(constants.TEXT);
        stringShape tStringShape=new stringShape();
        tStringShape.setColor(miniCAD.getColor());
        apply tApply=new apply(tStringShape);
        myListener.setMyApply(tApply);
        myListener.setOperOnApply(tApply);
    }
}
