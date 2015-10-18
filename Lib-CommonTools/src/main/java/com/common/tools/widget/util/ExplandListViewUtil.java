package com.common.tools.widget.util;

import android.widget.ExpandableListView;

/**
 * Author liangbx
 * Date 2015/9/22.
 */
public class ExplandListViewUtil {

    /**
     * 展开全部
     * @param expandableListView
     */
    public void expandAll(ExpandableListView expandableListView) {
        for(int i=0; i<expandableListView.getExpandableListAdapter().getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
    }

}
