package com.yu.bill;

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
        holder.CommodityMoney.setText(MainActivity.commodityBeanList.get(position).getCommodityMoney());
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
                if (removelistener!=null) removelistener.OnSusses();
            }
        });
        if (Integer.parseInt(MainActivity.commodityBeanList.get(position).getCommodityMoney()) <= 0){
            holder.CommodityMoney.setTextColor(context.getResources().getColor(R.color.red));
        }else {
            holder.CommodityMoney.setTextColor(context.getResources().getColor(R.color.green));
        }
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

        public ViewHolder(View v) {
            super(v);
            CommodityName = v.findViewById(R.id.CommodityName);
            CommodityMoney = v.findViewById(R.id.CommodityMoney);
            CommodityTime = v.findViewById(R.id.CommodityTime);
            Remove = v.findViewById(R.id.remove_image);
            CommodityType = v.findViewById(R.id.CommodityType);

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

    public void ShowRemove(Removelistener removelistener) {
        IsShowRemove = !this.IsShowRemove;
        this.removelistener = removelistener;
        notifyDataSetChanged();
    }
    public interface Removelistener{
        void OnSusses();
    }
}
