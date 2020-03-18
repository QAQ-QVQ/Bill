package com.yu.bill.utils;

import android.app.AlertDialog;
import android.content.Context;

/**
 * CREATED BY DY ON 2020/3/13.
 * TIME BY 18:21.
 **/
public class BaseDialog extends AlertDialog {
    protected DismissListener dismissListener;
    protected AddListener addListener;

    protected DismissListener getDismissListener() {
        return dismissListener;
    }

    protected AddListener getAddListener() {
        return addListener;
    }

    protected BaseDialog(Context context) {
        super(context);
    }
    protected void setOnDismissListener(DismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    protected void setAddListener(AddListener addListener) {
        this.addListener = addListener;
    }

    protected interface DismissListener {
        void OnDismiss();
    }

    protected interface AddListener {
        void OnAdd(String tradeName, String tradePrice);
    }

}
