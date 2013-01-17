package com.obriand.android_contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class ItemDBHelper  extends SQLiteOpenHelper {
	 
		private static final String TAG = "com.obriand.android_contentprovider.ItemDBHelper";	
	 
		public ItemDBHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
	 
		@Override
		public void onCreate(SQLiteDatabase db) {
			//Creation of the table from the request written in the CREATE_DB_REQUEST
			Log.i(TAG, "create DB");
			db.execSQL(ItemProvider.CREATE_DB_REQUEST);
			Log.i(TAG, "DB created");
		}
	 
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//We can do whatever we want here : here we will erase the database
			//Like that IDs will begin from 0 after version upgrade of the application
			db.execSQL("DROP TABLE " + ItemProvider.ITEMS_TABLE_NAME + ";");
			onCreate(db);
		}

}
