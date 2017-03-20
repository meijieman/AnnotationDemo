package com.major.invocationhandler;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/3/16 17:16
 */
public class Button {
    private OnClickListener mListener;

    public void setOnClickListener(OnClickListener listener){
        mListener = listener;
    }

    public void click(){
        if (mListener != null) {
            mListener.onClick();
        }
    }
}
