package com.hgw.toolkitpro.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hgw.toolkitpro.R;
import com.hgw.toolkitpro.activity.EditContactActivity;
import com.hgw.toolkitpro.model.Contact;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> contactList;

    // 构造函数，接收联系人数据列表
    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    // 创建ViewHolder，在此处加载联系人项的布局
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item_layout, parent, false);
        return new ContactViewHolder(view);
    }

    // 绑定数据到ViewHolder，此处设置姓名和电话号码
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.phoneTextView.setText(contact.getPhoneNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取被点击联系人的电话号码
                String phoneNumber = contact.getPhoneNumber();
                // 启动拨号页面并传递电话号码
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + phoneNumber));
                view.getContext().startActivity(dialIntent);
            }
        });

        // 给设置图片添加点击事件
        holder.settingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 启动自定义的联系人信息设置界面
                Context context = view.getContext();
                Intent intent = new Intent(context, EditContactActivity.class);
                // 传递联系人信息到设置界面，
                intent.putExtra("contact_id", contact.getId());
                intent.putExtra("contact_name", contact.getName());
                intent.putExtra("contact_phone", contact.getPhoneNumber());
                context.startActivity(intent);
            }
        });
    }

    // 返回联系人数据列表的大小，告知RecyclerView一共有多少项需要显示
    @Override
    public int getItemCount() {
        return contactList.size();
    }

    // 内部类，表示联系人信息的ViewHolder
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView phoneTextView;
        public ImageView settingImageView;

        // ViewHolder的构造函数，在此处初始化TextView的引用
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            phoneTextView = itemView.findViewById(R.id.textViewPhoneNumber);
            settingImageView = itemView.findViewById(R.id.imageViewSettings);
        }
    }
}
