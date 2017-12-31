package com.tools.extendLib;

public class pair<E extends Object, F extends Object> {
    public E getFirst() {
        return mFirst;
    }

    public void setFirst(E first) {
        mFirst = first;
    }

    public F getSecond() {
        return mSecond;
    }

    public void setSecond(F second) {
        mSecond = second;
    }

    private E mFirst;
    private F mSecond;
    public pair()
    {

    }

}
