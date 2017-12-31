package com.ui.menuBarListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tools.apply;
import com.tools.myListener;
import com.constant.constants;
import com.tools.shapes.lineShape;
import com.miniCAD;

public class lineListener implements ActionListener {
    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        myListener.setType(constants.LINE);
        lineShape tLineShape=new lineShape();
        tLineShape.setColor(miniCAD.getColor());
        apply tApply=new apply(tLineShape);
        myListener.setMyApply(tApply);
        myListener.setOperOnApply(tApply);
    }
}
