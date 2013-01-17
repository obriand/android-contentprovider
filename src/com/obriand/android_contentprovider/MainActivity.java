package com.obriand.android_contentprovider;

import com.obriand.android_contentprovider.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final String TAG = "com.obriand.android_contentprovider.MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i(TAG, "onCreate");

		// Get Items from Content Provider
		String columns[] = new String[] { Item.ITEM_ID,
				Item.ITEM_NAME, Item.ITEM_DESC };
		Uri mItems = ItemProvider.CONTENT_URI;
		Cursor cur = getContentResolver().query(mItems, columns, null, null, null);

		Toast.makeText(this, cur.getCount() + "", Toast.LENGTH_LONG).show();
	}
	
	public void btAddItem(View view) {
		Log.i(TAG, "add item");
		
		// Add Item to Content Provider
		// Defines a new Uri object that receives the result of the insertion
		Uri mNewUri;
		// Defines an object to contain the new values to insert
		ContentValues mNewValues = new ContentValues();
		//Sets the values of each column and inserts the word. The arguments to the "put"
		//method are "column name" and "value"
		mNewValues.put(Item.ITEM_NAME, "name0");
		mNewValues.put(Item.ITEM_DESC, "description0");
		mNewUri = getContentResolver().insert(
		    ItemProvider.CONTENT_URI,   // the content URI
		    mNewValues                  // the values to insert
		);

		Toast.makeText(this, "insert 0 item", Toast.LENGTH_LONG).show();
	}
	
	public void btGetCount(View view) {
		Log.i(TAG, "get count");
		
		// Get Count Items from Content Provider
		String columns[] = new String[] { Item.ITEM_ID,
				Item.ITEM_NAME, Item.ITEM_DESC };
		Uri mItems = ItemProvider.CONTENT_URI;
		Cursor cur = getContentResolver().query(mItems, columns, null, null, null);

		Toast.makeText(this, cur.getCount() + "", Toast.LENGTH_LONG).show();
	}
	
	public void btGetItem(View view) {
		Log.i(TAG, "get item");
		
		// Get Last Item from Content Provider
		String columns[] = new String[] { Item.ITEM_ID,
				Item.ITEM_NAME, Item.ITEM_DESC };
		Uri mItems = ItemProvider.CONTENT_URI;
		Cursor cur = getContentResolver().query(mItems, columns, null, null, null);		
		cur.moveToFirst();		
		int index = cur.getColumnIndex(Item.ITEM_NAME);

		Toast.makeText(this, cur.getString(index) + "", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
