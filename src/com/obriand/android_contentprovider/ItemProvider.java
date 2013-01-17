package com.obriand.android_contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class ItemProvider extends ContentProvider {
	
	private static final String TAG = "com.obriand.android_contentprovider.ItemProvider";
	
	private ItemDBHelper itemDBHelper;
	
	private static final String CONTENT_PROVIDER_DB_NAME = "items.db";
	
	private static final int CONTENT_PROVIDER_DB_VERSION = 1;
	
	public static final Uri CONTENT_URI = Uri.parse("content://com.obriand.android_contentprovider.itemprovider");
	
	public static final String CONTENT_PROVIDER_MIME = "com.obriand.android_contentprovider/com.obriand.android_contentprovider.item";
	
	public static final String ITEMS_TABLE_NAME = "ITEM_TABLE";
	
	public static final String CREATE_DB_REQUEST = "CREATE TABLE " + ITEMS_TABLE_NAME + " ("
	+ Item.ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Item.ITEM_NAME + " TEXT NOT NULL, "
	+ Item.ITEM_DESC + " TEXT NOT NULL);"; 
	
	
	@Override
	//Creation of the content provider : create the database
	public boolean onCreate() {
		Log.i(TAG, "onCreate");
		itemDBHelper = new ItemDBHelper(getContext(), CONTENT_PROVIDER_DB_NAME, null, CONTENT_PROVIDER_DB_VERSION);
	    return true;
	}
	
    @Override
    //Get MIME type of the Content Provider
    public String getType(Uri uri) {
    	Log.i(TAG, "getType");
    	return CONTENT_PROVIDER_MIME;
    }
    
    //Get element Id from the URI
    private long getId(Uri uri) {
    	Log.i(TAG, "getId");
        String lastPathSegment = uri.getLastPathSegment();
        Log.i(TAG, "lastPathSegment:"+lastPathSegment);
        if (lastPathSegment != null) {
            try {
            	return Long.parseLong(lastPathSegment);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Number Format Exception : " + e);
            }
        }
        return -1;
    }
	
    /**
    * Insert a value
     */
    //Insert a value
    @Override
    public Uri insert(Uri uri, ContentValues values) {
    	Log.i(TAG, "insert");
        SQLiteDatabase db = itemDBHelper.getWritableDatabase();
        try {
        	long id = db.insertOrThrow(ITEMS_TABLE_NAME, null, values);
        	if (id == -1) {
                throw new RuntimeException(String.format("%s : Failed to insert [%s] for unknown reasons.","ItemProvider", values, uri));
            } else {
                return ContentUris.withAppendedId(uri, id);
            }
        } finally {
                db.close();
            }
    }
    
    /**
    * update a value
     */
    //Update a value
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    	Log.i(TAG, "update");
    	long id = getId(uri);
    	SQLiteDatabase db = itemDBHelper.getWritableDatabase();	 
    	try {
    	    if (id < 0)
    	    	return db.update(ITEMS_TABLE_NAME, values, selection, selectionArgs);
    	    else
    	        return db.update(ITEMS_TABLE_NAME, values, Item.ITEM_ID + "=" + id, null);
    	} finally {
    	    db.close();
    	}
    }
    
    /**
    * delete a value
     */    
    //Delete a value
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
    	Log.i(TAG, "delete");
    	long id = getId(uri);
        SQLiteDatabase db = itemDBHelper.getWritableDatabase();
        try {
            if (id < 0)
                return db.delete(ITEMS_TABLE_NAME, selection, selectionArgs);
            else
                return db.delete(ITEMS_TABLE_NAME, Item.ITEM_ID + "=" + id, selectionArgs);
        } finally {
            db.close();
        }
    }
    
    /**
    * query a value
     */
  	//Get values from the Content Provider
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    	Log.i(TAG, "query, uri:" + uri.toString());
    	long id = getId(uri);
        SQLiteDatabase db = itemDBHelper.getReadableDatabase();

	        if (id < 0) {
	        	Log.i(TAG, "id<0");
	        	return  db.query(ITEMS_TABLE_NAME, projection, selection, selectionArgs, null, null,
	        sortOrder);
	        } else {
	        	Log.i(TAG, "else");
	        	return  db.query(ITEMS_TABLE_NAME, projection, Item.ITEM_ID + "=" + id, null, null, null,
	        null);
	        }

    }
	
}
