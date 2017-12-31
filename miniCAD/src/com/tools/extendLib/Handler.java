package com.tools.extendLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface Handler {
    /**
     *
     * @param msg key:"return" is the message class, others are values
     */
    void sendMessage(HashMap<Object,Object> msg);
}
