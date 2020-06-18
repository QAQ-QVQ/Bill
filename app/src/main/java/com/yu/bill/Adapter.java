package com.yu.bill;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.yu.bill.Model.BillBean;
import com.yu.bill.Model.CommodityBean;
import com.yu.bill.View.MainActivity;

import java.util.List;

/**
 * CREATED BY DY ON 2020/1/14.
 * TIME BY 15:07.
 **/
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private boolean IsShowRemove = false;
    private Context context;
    private Removelistener removelistener;
    private EditListener editListener;

    public Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.CommodityName.setText(MainActivity.commodityBeanList.get(position).getCommodityName());
        holder.CommodityTime.setText(MainActivity.commodityBeanList.get(position).getCommodityTime());
        holder.CommodityType.setText(MainActivity.commodityBeanList.get(position).getCommodityType());
        if (IsShowRemove) {
            holder.Remove.setVisibility(View.VISIBLE);
            holder.Remove.setImageDrawable(context.getDrawable(R.drawable.remove));
        } else {
            holder.Remove.setVisibility(View.GONE);
        }
        holder.Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.commodityBeanList.remove(position);
                BillDBHelper.getInstance().deleteBillByTime(holder.CommodityTime.getText().toString());
                notifyDataSetChanged();
                if (removelistener != null){
                    removelistener.OnSusses();
                }
            }
        });
        if (MainActivity.commodityBeanList.get(position).getCommodityType().equals("工资收入")) {
            String priceString = "+" + MainActivity.commodityBeanList.get(position).getCommodityMoney();
            holder.CommodityMoney.setTextColor(context.getResources().getColor(R.color.green));
            holder.CommodityMoney.setText(priceString);
        } else {
            String priceString = "-" + MainActivity.commodityBeanList.get(position).getCommodityMoney();
            holder.CommodityMoney.setTextColor(context.getResources().getColor(R.color.red));
            holder.CommodityMoney.setText(priceString);
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editListener != null) {
                    editListener.OnEdit(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.commodityBeanList.size();
    }

    //② 创建ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView CommodityName;
        public TextView CommodityMoney;
        public TextView CommodityTime;
        public ImageView Remove;
        public TextView CommodityType;
        public ConstraintLayout item;

        public ViewHolder(View v) {
            super(v);
            CommodityName = v.findViewById(R.id.CommodityName);
            CommodityMoney = v.findViewById(R.id.CommodityMoney);
            CommodityTime = v.findViewById(R.id.CommodityTime);
            Remove = v.findViewById(R.id.remove_image);
            CommodityType = v.findViewById(R.id.CommodityType);
            item = v.findViewById(R.id.recyclerView_item);
        }
    }

    public void addTrade(CommodityBean commodityBean) {
        MainActivity.commodityBeanList.add(commodityBean);
        BillDBHelper.getInstance().insertBill(commodityBean);
        notifyDataSetChanged();
    }

    public void addTrade(String Name, String Price, String time, String type) {
        if (Name.isEmpty() && Price.isEmpty()) {
            Toast.makeText(context, "内容不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            addTrade(new CommodityBean(Name, time, Price, type));
        }
    }

    public void editTrade(int posion, String Name, String Price, String type) {
        MainActivity.commodityBeanList.get(posion).setCommodityName(Name);
        MainActivity.commodityBeanList.get(posion).setCommodityMoney(Price);
        MainActivity.commodityBeanList.get(posion).setCommodityType(type);
        BillDBHelper.getInstance().updateBill(MainActivity.commodityBeanList.get(posion));
        notifyDataSetChanged();
    }

    public void ShowRemove(Removelistener removelistener) {
        IsShowRemove = !this.IsShowRemove;
        this.removelistener = removelistener;
        notifyDataSetChanged();
    }

    public EditListener getEditListener() {
        return editListener;
    }

    public void setEditListener(EditListener editListener) {
        this.editListener = editListener;
    }

    public interface Removelistener {
        void OnSusses();
    }

    public interface EditListener {
//        void OnEdit(String name, String price, String time, String type);

        void OnEdit(int posion);
    }
}
