package ru.flymer.flymerclient.provider;

import android.content.UriMatcher;
import android.net.Uri;

/**
 * Created by Artsiom on 1/21/2015.
 */
public class DatabaseMetadata {

    public static final String AUTHORITY = "ru.flymer.flymerclient.UserList";
    public static final String USER_PATH = "user";

    public static final String DB_NAME = "flymer";
    public static final int DB_VERSION = 1;

    public static final String USER_TABLE = "user";

    public static final String USER_ID = "_id";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FKEY = "fkey";
    public static final String USER_AC = "ac";
    public static final String USER_DKEY = "dkey";//!find by some formula
    public static final String USER_SID = "sid";
    public static final String USER_COOKIE = "cookie";


    public static final StringBuilder DB_CREATE = new StringBuilder()
            .append("CREATE TABLE ")
            .append(USER_TABLE).append("(")
            .append(USER_ID).append(" integer primary key autoincrement, ")
            .append(USER_EMAIL).append(" text, ")
            .append(USER_PASSWORD).append(" text, ")
            .append(USER_FKEY).append(" text, ")
            .append(USER_AC).append(" text, ")
            .append(USER_SID).append(" text, ")
            .append(USER_COOKIE).append(" text")
            .append(");");

    public static final Uri USER_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + USER_PATH);

    // Типы данных
    // набор строк
    static final String USER_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + USER_PATH;

    // одна строка
    static final String USER_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + USER_PATH;

    //// UriMatcher
    // общий Uri
    static final int URI_USER = 1;

    // Uri с указанным ID
    static final int URI_USER_ID = 2;

}
