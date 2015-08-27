package com.common.androidtemplate.module.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.common.androidtemplate.R;
import com.common.androidtemplate.widget.dialog.CusDialogFragment;

/**
 * Author liangbx
 * Date 2015/8/27.
 */
public class DialogFragmentActivity extends AppCompatActivity {

    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog_fragment);

        startBtn = (Button) findViewById(R.id.start);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CusDialogFragment dialog = CusDialogFragment.newInstance();
                dialog.show(getSupportFragmentManager(), "input");
            }
        });
    }
}
