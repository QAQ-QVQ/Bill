package com.yu.bill.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.yu.bill.Adapter;
import com.yu.bill.BillDBHelper;
import com.yu.bill.BillOpenHelper;
import com.yu.bill.Model.BillBean;
import com.yu.bill.Model.CommodityBean;
import com.yu.bill.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    public static List<CommodityBean> commodityBeanList;
    private TextView addInButton, editButton, bill_sizeText, settingButton, outText, inText, billNameText;
    private Adapter adapter;
    private RelativeLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        initUI();
        initData();
    }

    private void initData() {
        BillOpenHelper billOpenHelper = new BillOpenHelper(this);
        commodityBeanList = new ArrayList<>();
        commodityBeanList = BillDBHelper.getInstance().getAllBills();
        adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
        RefreshUI();
    }

    private void RefreshUI() {
        int priceIn = 0, priceOut = 0;
        for (CommodityBean commodityBean : commodityBeanList) {
            int price = Integer.parseInt(commodityBean.getCommodityMoney());
            if (price > 0) {
                priceIn += price;
            } else {
                priceOut += price;
            }
        }
        outText.setText(priceOut + "");
        inText.setText(priceIn + "");
        if (BillDBHelper.getInstance().getBillCount() != 0){
            bill_sizeText.setText("共" + (BillDBHelper.getInstance().getBillCount() - 1) + "条");
        }else {
            bill_sizeText.setText("共" + 0 + "条");
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
                final AddDialog addDialog = new AddDialog(this);
                addDialog.setAddListener(new AddDialog.AddListener() {
                    @Override
                    public void OnAdd(String Name, String time, String Price, String type) {
                        adapter.addTrade(Name, Price, time, type);
                        RefreshUI();
                        addDialog.dismiss();
                    }
                });
                addDialog.show();
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

                break;
            case R.id.setting:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
