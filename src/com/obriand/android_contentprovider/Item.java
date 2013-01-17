package com.obriand.android_contentprovider;

import android.provider.BaseColumns;

public class Item implements BaseColumns {
	
    private Item() {}
    
    public static final String ITEM_ID = "ITEM_ID";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_DESC = "ITEM_DESC";

}
