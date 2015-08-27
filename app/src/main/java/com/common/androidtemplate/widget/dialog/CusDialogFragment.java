package com.common.androidtemplate.widget.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.androidtemplate.R;

/**
 * Author liangbx
 * Date 2015/8/27.
 */
public class CusDialogFragment extends DialogFragment {

    public static CusDialogFragment newInstance() {
        CusDialogFragment f = new CusDialogFragment();

        //set bundle
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_input, null);
        return view;
    }
}
