package com.common.androidtemplate.module.function.net;

import android.view.View;
import android.widget.Button;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseActivity;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.Date;


/**
 * Author liangbx
 * Date 2015/10/20.
 */
public class DnsActivity extends BaseActivity {


    @butterknife.Bind(R.id.btn_get_dns)
    Button mBtnGetDns;

    @Override
    public void getIntentData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dns;
    }

    @Override
    public void initView() {

        mBtnGetDns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDns();
            }
        });
    }

    private void getDns() {
        final String url = buildCcihUrlForDns();
        new Thread() {
            @Override
            public void run() {
                super.run();
                runHttp(url);
            }
        }.start();
    }

    public void runHttp(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                String body = response.body().string();
                System.out.println(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *  生成用于获取DNS信息的接口方法
     * @return
     */
    public String buildCcihUrlForDns(){
        StringBuilder sb = new StringBuilder();
        String clar4 = "";
        int staticKey = "a".charAt(0);
        long currentTime = (new Date()).getTime();
        for(int i = 0; i < 4; i++) {
            int randKey = (int)Math.floor(Math.random() * 26);
            char t = (char)(randKey + staticKey);
            clar4 += String.valueOf(t);
        }
        String sign = clar4 + currentTime;
        sb.append("http://").append(sign).append(".trace.term.chinacache.com/getdns.php?randhost=").append(sign).append(".trace.term.chinacache.com");
        return sb.toString();
    }

}
