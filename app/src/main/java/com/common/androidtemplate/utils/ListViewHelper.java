package com.common.androidtemplate.utils;

/**
 * Author liangbx
 * Date 2015/9/25.
 */
public class ListViewHelper {

    //页大小
    private int pageSize;
    //页号
    private int pageNo;

    public interface RefreshListener {
        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();
    }

}
