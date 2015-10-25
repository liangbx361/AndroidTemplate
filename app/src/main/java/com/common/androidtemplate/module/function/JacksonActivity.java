package com.common.androidtemplate.module.function;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseActivity;
import com.common.androidtemplate.bean.Jackson;
import com.common.androidtemplate.bean.JacksonChild;
import com.common.androidtemplate.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Author liangbx
 * Date 2015/10/22.
 */
public class JacksonActivity extends BaseActivity {


    @Override
    public void getIntentData() {
        test();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_jackson;
    }

    @Override
    public void initView() {

    }

    private void test() {
        Jackson jackson = new Jackson();
        jackson.mJacksonChild = new JacksonChild("a");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String json = JsonUtils.toJson(jackson);
        System.out.println(json);
    }


}
