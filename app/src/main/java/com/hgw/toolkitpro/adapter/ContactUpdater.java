package com.hgw.toolkitpro.adapter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.provider.ContactsContract;

import com.hgw.toolkitpro.model.Contact;

public class ContactUpdater {
    public static boolean updateContact(ContentResolver contentResolver, Contact updatedContact) {
        ContentValues values = new ContentValues();
        values.put(ContactsContract.Contacts.DISPLAY_NAME, updatedContact.getName());

        // 更新联系人姓名
        int updatedRows = contentResolver.update(
                ContactsContract.Contacts.CONTENT_URI.buildUpon()
                        .appendPath(String.valueOf(updatedContact.getId()))
                        .build(),
                values,
                null,
                null
        );

        if (updatedRows <= 0) {
            // 更新失败
            return false;
        }

        // 更新联系人电话号码
        values.clear();
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, updatedContact.getPhoneNumber());
        int phoneRowsUpdated = contentResolver.update(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                values,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{String.valueOf(updatedContact.getId())}
        );

        return phoneRowsUpdated > 0;
    }
}
