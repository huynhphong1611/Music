package com.bkav.android.music.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.bkav.android.music.db.SongDataBaseHelper;

import static com.bkav.android.music.provider.SongContact.*;

public class SongProvider extends ContentProvider {
    // Định nghĩa các code sẽ trả về dối với các URI tương ứng
    static final int NOTES = 1; // Khi là tất cả
    static final int NOTE_ID = 2; // khi uri là 1 note cụ thể
    // Tao 1 đối tương UriMatcher lop này sẽ giúp ta tìm xem uri đưa vào sẽ là loại nào
    static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, CONTENT_PATH, NOTES); // nhớ path bên lớp contracts cho all note ?
        sUriMatcher.addURI(AUTHORITY, CONTENT_PATH + "/#", NOTE_ID); // path cho 1 note cụ thể
    }


    private SQLiteDatabase mSqLiteDatabase;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        // chúng ta khởi tạo 1 lớp open Helper
        SongDataBaseHelper noteDatabaseHelper = new SongDataBaseHelper(context);
        mSqLiteDatabase = noteDatabaseHelper.getWritableDatabase();
        return mSqLiteDatabase == null ? false : true;
    }


    // CHúng ta ghi đè lại ham query
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder(); // LOp này là lớp tiện ích thôi. giúp chúng tay xây dựng câu truy vấn Sql
        qb.setTables(NAME_TABLE); // Set ten bang
        // check xem Uri đưa vào thuọc loại gì
        switch (sUriMatcher.match(uri)) {
            case NOTES:
                break;
            case NOTE_ID: // Neu no là 1 note cụ thể thì ta sex lấy ic của nó cho thằng where
                qb.appendWhere( ID+ "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor cursor = qb.query(mSqLiteDatabase,  projection,    selection, selectionArgs,null, null, sortOrder);
        return cursor;
    }


    @Override
    public String getType(Uri uri) {
        // Trả về type ứng với các uri
        switch (sUriMatcher.match(uri)){
            case NOTES:
                return MULTIPLE_NOTES_MIME_TYPE;
            case NOTE_ID:
                return SINGLE_NOTE_MIME_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported UnRI: " + uri);
        }
    }

    // Ghi đè hàm insert
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // chung ta them 1 record dùng hàm đã biết từ bài trước
        long rowId = mSqLiteDatabase.insert(NAME_TABLE, null, values);

        if (rowId > 0) {
            // nghia la chung ta da ghi log thanh cong
            // chung ta lay uri cua note moi do
            Uri newNoteUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            // nofify cho nhung ai dang ky lang nghe su thay doi
            getContext().getContentResolver().notifyChange(newNoteUri, null);
            return newNoteUri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    // Tuong tu nhu trên
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (sUriMatcher.match(uri)){
            case NOTES:
                // Truong hop xoa toan bo notes
                count = mSqLiteDatabase.delete(NAME_TABLE, selection, selectionArgs);
                break;

            case NOTE_ID:
                // Truong hop xoa 1 note
                String id = uri.getPathSegments().get(1);
                count = mSqLiteDatabase.delete( NAME_TABLE, ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        // Notify cho cac thanh phan lang nghe
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    // Tuong tu nhu trên
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        switch (sUriMatcher.match(uri)){
            case NOTES:
                count = mSqLiteDatabase.update(NAME_TABLE, values, selection, selectionArgs);
                break;

            case NOTE_ID:
                String id = uri.getPathSegments().get(1);
                count = mSqLiteDatabase.update(NAME_TABLE, values, ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
