package com.yu.bill.View;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yu.bill.Adapter;
import com.yu.bill.BillDBHelper;
import com.yu.bill.BillOpenHelper;
import com.yu.bill.Model.BillBean;
import com.yu.bill.Model.CommodityBean;
import com.yu.bill.R;
import com.yu.bill.utils.FileUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    public static List<CommodityBean> commodityBeanList;
    private TextView addInButton, editButton, bill_sizeText, settingButton, outText, inText, billNameText;
    private Adapter adapter;
    private RelativeLayout background;
    private AddDialog addDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        initUI();
        initData(savedInstanceState);
    }

    private void initData(Bundle savedInstanceState) {

//        addDialog.show();
//        addDialog.dismiss();
        BillOpenHelper billOpenHelper = new BillOpenHelper(this);
        commodityBeanList = new ArrayList<>();
        commodityBeanList = BillDBHelper.getInstance().getAllBills();
        adapter = new Adapter(this);
        adapter.setEditListener(new Adapter.EditListener() {
            @Override
            public void OnEdit(final int posion) {
                ShowDialog();
                addDialog.EditDialog(commodityBeanList.get(posion));
                addDialog.setAddListener(new AddDialog.AddListener() {
                    @Override
                    public void OnAdd(String Name, String time, String Price, String type) {
                        adapter.editTrade(posion, Name, Price, type);
                        RefreshUI();
                        addDialog.dismiss();
                    }
                });
            }
        });
        recyclerView.setAdapter(adapter);
        RefreshUI();
    }

    private void RefreshUI() {
        float priceIn = 0, priceOut = 0;
        for (CommodityBean commodityBean : commodityBeanList) {
            float price = Float.parseFloat(commodityBean.getCommodityMoney());
            if (commodityBean.getCommodityType().equals("工资收入")) {
                priceIn += price;
            } else {
                priceOut += price;
            }
        }
        outText.setText(priceOut + "");
        inText.setText(priceIn + "");
        if (BillDBHelper.getInstance().getBillCount() != 0){
            bill_sizeText.setText("共" + (BillDBHelper.getInstance().getBillCount() - 1 ) + "条");
        }else {
            bill_sizeText.setText("共 0 条");
        }

    }

    private void initUI() {
        addInButton = findViewById(R.id.add_in);
        editButton = findViewById(R.id.edit);
        bill_sizeText = findViewById(R.id.bill_size);
        settingButton = findViewById(R.id.setting);
        outText = findViewById(R.id.expenditure_money);
        inText = findViewById(R.id.income_money);
        billNameText = findViewById(R.id.BillName);
        background = findViewById(R.id.relativelayout);
        addInButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        bill_sizeText.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_in:
                ShowDialog();
                addDialog.AddDialog();
                addDialog.setAddListener(new AddDialog.AddListener() {
                    @Override
                    public void OnAdd(String Name, String time, String Price, String type) {
                        adapter.addTrade(Name, Price, time, type);
                        RefreshUI();
                        addDialog.dismiss();
                    }
                });
                break;
            case R.id.edit:
                adapter.ShowRemove(new Adapter.Removelistener() {
                    @Override
                    public void OnSusses() {
                        RefreshUI();
                    }
                });
                break;
            case R.id.bill_size:
                if (FileUtils.copy("/storage/emulated/0/Android/data/com.yu.bill/db/bill.db", "/data/data/com.yu.bill/databases/bill.db", new FileUtils.OnReplaceListener() {
                    @Override
                    public boolean onReplace(File srcFile, File destFile) {

                        return false;
                    }
                })) {
                    Toast.makeText(this, "导入成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "导入失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.setting:
                if (FileUtils.copy("/data/data/com.yu.bill/databases/bill.db", "/storage/emulated/0/Android/data/com.yu.bill/db/bill.db", new FileUtils.OnReplaceListener() {
                    @Override
                    public boolean onReplace(File srcFile, File destFile) {

                        return false;
                    }
                })) {
                    Toast.makeText(this, "导出成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "导出失败", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }

    private void ShowDialog() {
        addDialog = new AddDialog(MainActivity.this);
        addDialog.show();
        Window window = addDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
//将宽度值dp转px
        lp.width = this.getResources().getDimensionPixelOffset(R.dimen.dp_310);
//高度自适应(也可设置为固定值,同宽度设置方法)
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        addDialog.getWindow().setAttributes(lp);
    }
}
