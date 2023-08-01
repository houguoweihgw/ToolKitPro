package com.hgw.toolkitpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hgw.toolkitpro.R;
import com.hgw.toolkitpro.model.ToolItem;

import java.util.List;

public class ToolAdapter extends BaseAdapter {
    private Context context;
    private List<ToolItem> toolList;

    public ToolAdapter(Context context, List<ToolItem> toolList) {
        this.context = context;
        this.toolList = toolList;
    }

    @Override
    public int getCount() {
        return toolList.size();
    }

    @Override
    public Object getItem(int position) {
        return toolList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 它在适配器中负责为每个网格项创建视图，并将工具项的数据绑定到对应的视图上。
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, parent, false);
        }

        ImageView iconImageView = view.findViewById(R.id.iconImageView);
        TextView nameTextView = view.findViewById(R.id.nameTextView);

        ToolItem toolItem = toolList.get(position);
        iconImageView.setImageResource(toolItem.getIconResId());
        nameTextView.setText(toolItem.getName());

        return view;
    }

}
