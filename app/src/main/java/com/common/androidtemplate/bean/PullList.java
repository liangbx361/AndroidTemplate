package com.common.androidtemplate.bean;

import java.util.List;

public class PullList {
	
	public int totalNum;
	public boolean hasNextPage;
	public List<Item> itemList;
	
	public class Item {
		public String name;
	}
}
