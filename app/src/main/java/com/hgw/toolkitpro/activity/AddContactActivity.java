package com.hgw.toolkitpro.activity;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hgw.toolkitpro.R;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextPhoneNumber;
    private Button btnAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // 初始化视图
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        btnAddContact = findViewById(R.id.btnAddContact);

        // 设置添加按钮的点击事件
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取用户输入的姓名和电话号码
                String name = editTextName.getText().toString().trim();
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                // 在此处执行添加联系人的操作
                boolean success = insertContact(name, phoneNumber);

                if (success) {
                    Toast.makeText(AddContactActivity.this, "联系人添加成功", Toast.LENGTH_SHORT).show();
                    // 添加成功后返回上一页（联系人列表页面）
                    finish();
                } else {
                    Toast.makeText(AddContactActivity.this, "联系人添加失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    // 插入联系人数据到系统联系人中
    private boolean insertContact(String name, String phoneNumber) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        int rawContactInsertIndex = ops.size();

        // 添加到raw_contacts表
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        // 添加到data表：姓名
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                .build());

        // 添加到data表：电话号码
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());

        // 执行插入操作
        try {
            ContentResolver resolver = getContentResolver();
            resolver.applyBatch(ContactsContract.AUTHORITY, ops);
            return true;
        } catch (RemoteException | OperationApplicationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
