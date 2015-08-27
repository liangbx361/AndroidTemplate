package com.common.androidtemplate.module.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.common.androidtemplate.R;
import com.common.androidtemplate.module.layout.NavigationDrawerActivity;
import com.common.androidtemplate.widget.dialog.CusDialogFragment;

/**
 * Author liangbx
 * Date 2015/8/27.
 */
public class DialogFragmentActivity extends AppCompatActivity {

    private Button startBtn;
    private CusDialogFragment dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog_fragment);

        startBtn = (Button) findViewById(R.id.start);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDialog();
                Intent intent = new Intent(DialogFragmentActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDialog();
    }

    private void showDialog() {
//        if(dialog != null) {
//            dialog.dismissAllowingStateLoss();
//        }
        dialog = CusDialogFragment.newInstance();
        dialog.show(getSupportFragmentManager(), "input");
    }

    private void hideDialog() {
        if(dialog != null) {
            dialog.dismissAllowingStateLoss();
        }
    }
}
