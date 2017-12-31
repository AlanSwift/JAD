package com.ui.menuBarListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.tools.myListener;
import com.constant.constants;

public class chooseListener implements ActionListener {

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        myListener.setType(constants.CHOOSE);
    }
}
