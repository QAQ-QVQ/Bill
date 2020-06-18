package com.yu.bill.View;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;

import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.yu.bill.Model.CommodityBean;
import com.yu.bill.R;
import com.yu.bill.utils.BaseDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CREATED BY DY ON 2020/1/15.
 * TIME BY 10:31.
 **/
public class AddDialog extends Dialog {
    private Context context;
    private DismissListener dismissListener;
    private AddListener addListener;
    private String time, type;
    private CommodityBean commodityBean;
    private ViewHolder viewHolder;

    public AddDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void EditDialog(CommodityBean commodityBean) {
        this.commodityBean = commodityBean;
        setTitle(context.getResources().getString(R.string.header_title_edit));
        viewHolder.time_text.setText(commodityBean.getCommodityTime());
        setCanceledOnTouchOutside(true);//控制返回键是否dismiss
        viewHolder.button_add.setText(context.getResources().getString(R.string.button_bottom_edit));
        viewHolder.trade_name.setText(commodityBean.getCommodityName());
        viewHolder.trade_price.setText(commodityBean.getCommodityMoney());
    }

    public void AddDialog() {
        setTitle(context.getResources().getString(R.string.header_title_add));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        time = simpleDateFormat.format(date);
        viewHolder.time_text.setText(time);
        setCanceledOnTouchOutside(false);//控制返回键是否dismiss
        viewHolder.button_add.setText(context.getResources().getString(R.string.button_bottom_add));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(context).inflate(R.layout.content, null);
        viewHolder = new ViewHolder(view);
        setContentView(view);//这行一定要写在前面
        setCancelable(false);//点击外部不可dismiss
        viewHolder.button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dismissListener != null) dismissListener.OnDismiss();
                dismiss();
            }
        });
        viewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewHolder.button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addListener != null) {
                    if (!(viewHolder.trade_name.getText().toString().isEmpty() || viewHolder.trade_price.getText().toString().isEmpty() || type.isEmpty()))
                        addListener.OnAdd(viewHolder.trade_name.getText().toString(), time, viewHolder.trade_price.getText().toString(), type);
                }
            }
        });
    }


    private class ViewHolder {
        EditText trade_name, trade_price;
        Button button_close, button_add;
        TextView time_text;
        Spinner spinner;

        public ViewHolder(View view) {
            trade_name = view.findViewById(R.id.trade_name);
            trade_price = view.findViewById(R.id.trade_price);
            button_close = view.findViewById(R.id.button_close);
            button_add = view.findViewById(R.id.button_add);
            time_text = view.findViewById(R.id.time);
            spinner = view.findViewById(R.id.bill_type);
        }
    }

    public DismissListener getDismissListener() {
        return dismissListener;
    }

    public AddListener getAddListener() {
        return addListener;
    }

    public void setOnDismissListener(DismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    public void setAddListener(AddListener addListener) {
        this.addListener = addListener;
    }

    public interface DismissListener {
        void OnDismiss();
    }

    public interface AddListener {
        /**
         * @param Name  名称
         * @param time  时间
         * @param Price 价格
         * @param type  种类
         */
        void OnAdd(String Name, String time, String Price, String type);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.commodityBean = null;
    }
}
