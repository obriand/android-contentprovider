package com.obriand.android_contentprovider;

import com.obriand.android_contentprovider.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final String TAG = "com.obriand.android_contentprovider.MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i(TAG, "display item detail : onCreate");

		// Get LRMessages from Content Provider
		String columns[] = new String[] { Item.ITEM_ID,
				Item.ITEM_NAME, Item.ITEM_DESC };
		Uri mItems = ItemProvider.CONTENT_URI;
		Cursor cur = getContentResolver().query(mItems, columns, null, null, null);

		Toast.makeText(this, cur.getCount() + "", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
