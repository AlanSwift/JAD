package com.ui.menuBarListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.tools.apply;
import com.tools.myListener;
import com.constant.constants;
import com.tools.shapes.ovalShape;
import com.miniCAD;
public class ovalListener implements ActionListener {

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        myListener.setType(constants.OVAL);
        ovalShape tOvalShape=new ovalShape();
        tOvalShape.setColor(miniCAD.getColor());
        apply tApply=new apply(tOvalShape);
        myListener.setMyApply(tApply);
        myListener.setOperOnApply(tApply);
    }
}
