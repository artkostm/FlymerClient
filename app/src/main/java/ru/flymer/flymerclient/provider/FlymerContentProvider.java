package ru.flymer.flymerclient.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by Artsiom on 1/21/2015.
 */
public class FlymerContentProvider extends ContentProvider {

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DatabaseMetadata.AUTHORITY, DatabaseMetadata.USER_PATH, DatabaseMetadata.URI_USER);
        uriMatcher.addURI(DatabaseMetadata.AUTHORITY, DatabaseMetadata.USER_PATH + "/#", DatabaseMetadata.URI_USER_ID);
    }

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case DatabaseMetadata.URI_USER:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = DatabaseMetadata.USER_EMAIL + " ASC";
                }
                break;
            case DatabaseMetadata.URI_USER_ID: // Uri с ID
                String email = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = DatabaseMetadata.USER_EMAIL + " like '" + email + "'";
                } else {
                    selection = selection + " AND " + DatabaseMetadata.USER_EMAIL + " like '" + email + ";";
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DatabaseMetadata.USER_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                DatabaseMetadata.USER_CONTENT_URI);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case DatabaseMetadata.URI_USER:
                return DatabaseMetadata.USER_CONTENT_TYPE;
            case DatabaseMetadata.URI_USER_ID:
                return DatabaseMetadata.USER_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (uriMatcher.match(uri) != DatabaseMetadata.URI_USER) {
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(DatabaseMetadata.USER_TABLE, null, contentValues);
        Uri resultUri = ContentUris.withAppendedId(DatabaseMetadata.USER_CONTENT_URI, rowID);
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case DatabaseMetadata.URI_USER:
                break;
            case DatabaseMetadata.URI_USER_ID:
//                String email = uri.getLastPathSegment();
//                if (TextUtils.isEmpty(selection)) {
//                    selection = DatabaseMetadata.USER_EMAIL + " like '" + email + "'";
//                } else {
//                    selection = selection + " AND " + DatabaseMetadata.USER_EMAIL + " like '" + email + "'";
//                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        selection = " 3 > 1";
        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(DatabaseMetadata.USER_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case DatabaseMetadata.URI_USER:
                break;
            case DatabaseMetadata.URI_USER_ID:
                String email = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = DatabaseMetadata.USER_EMAIL + " like '" + email + "'";
                } else {
                    selection = selection + " AND " + DatabaseMetadata.USER_EMAIL + " like '" + email + "'";
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.update(DatabaseMetadata.USER_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }
}
