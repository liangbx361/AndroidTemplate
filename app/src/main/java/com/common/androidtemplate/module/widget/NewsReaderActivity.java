package com.common.androidtemplate.module.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseBackActivity;
import com.common.tools.date.FormatDateTime;
import com.common.tools.file.FileSizeUtil;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.Entity;
import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.facebook.crypto.util.SystemNativeCryptoLibrary;

import net.tsz.afinal.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @描述：新闻浏览，采用html模板为显示模板
 * @作者：liang bao xian
 * @时间：2014年8月19日 上午9:16:55
 */
public class NewsReaderActivity extends BaseBackActivity {

    public static final String TAG = "Webview_Decry";

    private WebView webView;
    private File mTempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getIntentData();
        initView();
        initWebView();

        mTempFile = Utils.getDiskCacheDir(this, "webTemp");
        if(!mTempFile.exists()) {
            mTempFile.mkdirs();
        }
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initView() {
        webView = (WebView) findViewById(R.id.webview);
//		webView.loadUrl("file:///android_asset/news/newspage.html");
//		webView.loadUrl("http://www.163.com/");
//        webView.loadUrl("file:///mnt/sdcard/.MrBook/bookview_87111/index.html");
        webView.loadUrl("file:///mnt/sdcard/Conceal/test1/index.html");
//        webView.loadUrl("file:///mnt/sdcard/Conceal/test/index.html");
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行JavaScript脚本
        webSettings.setJavaScriptEnabled(true);
        //如果要播放Flash，需要加上这一句  
        webSettings.setPluginState(PluginState.ON);

        String databasePath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabaseEnabled(false);
        webSettings.setDatabasePath(databasePath);

        webSettings.setDomStorageEnabled(true);

        //在本页面中响应链接
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("webview_url", "shouldInterceptRequest=" + url);

                String key = "file:///";

                if (url.contains(key)) {
                    InputStream is = null;
                    String filePath = url.substring(key.length() - 1);
                    int index = filePath.lastIndexOf("?");
                    if (index != -1) {
                        filePath = filePath.substring(0, index);
                    }
                    if (url.contains(".mp4")) {
//                        is = decryFile(filePath, 1, mTempFile);
                    } else {
                        is = decryFile(filePath, 0, null);
                    }
                    if (is != null) {
                        return new WebResourceResponse(getMimeType(url), "utf-8", is);
                    }

                }

                return null;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("webview_url", "=============================================");
                Log.d("webview_url", "onPageStarted=" + url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Log.d("webview_url", "shouldInterceptRequest2");
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.d("webview_url", "onLoadResource=" + url);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.d("webview_url", "shouldOverrideUrlLoading=" + url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("webview_url", "onPageFinished=" + url);
            }


        });
    }

    public final class NewsScriptInterface {

        @JavascriptInterface
        public String getContent() {
            return null;
        }
    }

    /**
     * 解密html相关资料文件
     * @param srcFilePath
     * @param type 0:字节流 1:文件输入流
     * @return
     */
    private InputStream decryFile(String srcFilePath, int type, File tempDirFile) {
        File srcFile = new File (srcFilePath);
        if(!srcFile.exists() || !srcFile.isFile()) {
            return null;
        }

        long startTime = System.currentTimeMillis();

        // Creates a new Crypto object with default implementations of
        // a key chain as well as native library.
        Crypto crypto = new Crypto(new SharedPrefsBackedKeyChain(this),
                new SystemNativeCryptoLibrary());

        // Check for whether the mCrypto functionality is available
        // This might fail if Android does not load libaries correctly.
        if (!crypto.isAvailable()) {
            return null;
        }

        // Get the file to which ciphertext has been written.
        FileInputStream fileStream = null;
        try {
            fileStream = new FileInputStream(new File(srcFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Creates an input stream which decrypts the data as
        // it is read from it.
        InputStream inputStream = null;
        try {
            inputStream = crypto.getCipherInputStream(fileStream, new Entity("encry"));
//             Read into a byte array.
            int read;
            byte[] buffer = new byte[1024];

            // You must read the entire stream to completion.
            // The verification is done at the end of the stream.
            // Thus not reading till the end of the stream will cause
            // a security bug.
            InputStream destInputStream;
            if(type == 0) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
                while ((read = inputStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, read);
                }

                inputStream.close();
                bos.close();
                destInputStream = new ByteArrayInputStream(bos.toByteArray());
            } else {
                File tempFile = new File(tempDirFile, System.currentTimeMillis()+".mp4");
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                while ((read = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, read);
                }
                outputStream.close();
                destInputStream = new FileInputStream(tempFile);
                inputStream.close();
            }

            long endTime = System.currentTimeMillis();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("文件大小：");
            stringBuilder.append(FileSizeUtil.getAutoFileOrFilesSize(srcFilePath));
            stringBuilder.append("\n");
            stringBuilder.append("解密时间：");
            stringBuilder.append(endTime - startTime);
            Log.d(TAG, stringBuilder.toString());

            return destInputStream;
        } catch (IOException | CryptoInitializationException | KeyChainException e) {
            e.printStackTrace();
        }



        return null;
    }

    private String getMimeType(String url) {
        String mimeType = "*/*";
        if(url.contains(".html")) {
            mimeType = "text/html";
        } else if(url.contains(".css")) {
            mimeType = "text/css";
        } else if(url.contains(".png")) {
            mimeType = "image/webp";
        } else if(url.contains(".jpg")) {
            mimeType = "image/webp";
        } else if(url.contains(".js")) {
            mimeType = "text/javascript";
        }

        Log.d("webview_url", mimeType);

        return mimeType;
    }
}
