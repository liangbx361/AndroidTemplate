package com.common.androidtemplate.module.function;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;

import com.common.androidtemplate.R;
import com.common.androidtemplate.activity.base.BaseBackActivity;
import com.common.tools.file.FileSizeUtil;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.Entity;
import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.facebook.crypto.util.SystemNativeCryptoLibrary;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Author liangbx
 * Date 2015/8/17.
 * DESC 使用Facebook的Canceal进行文件加解密测试
 * 参考资料：
 * https://facebook.github.io/conceal/
 * https://github.com/facebook/conceal
 */
public class Conceal extends BaseBackActivity {

    private static final String SOURCE_FILE_PATH =
            "/mnt/sdcard/Conceal/test.zip";
    private static final String ENCRY_FILE_PATH =
            "/mnt/sdcard/Conceal/test_encry.zip";
    private static final String DECRY_FILE_PATH =
            "/mnt/sdcard/Conceal/test_decry.zip";

    @Bind(R.id.result)
    TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conceal);
        ButterKnife.bind(this);
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.encryption)
    void encryption() {
        long startTime = System.currentTimeMillis();

        // Creates a new Crypto object with default implementations of
        // a key chain as well as native library.
        Crypto crypto = new Crypto(new SharedPrefsBackedKeyChain(this),
                new SystemNativeCryptoLibrary());

        // Check for whether the crypto functionality is available
        // This might fail if Android does not load libaries correctly.
        if (!crypto.isAvailable()) {
            return;
        }

        OutputStream fileStream = null;
        try {
            fileStream = new BufferedOutputStream(new FileOutputStream(new File(ENCRY_FILE_PATH)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Creates an output stream which encrypts the data as
        // it is written to it and writes it out to the file.
        OutputStream outputStream = null;
        try {
            outputStream = crypto.getCipherOutputStream(fileStream, new Entity("encry"));
        } catch (IOException | CryptoInitializationException | KeyChainException e) {
            e.printStackTrace();
        }

        // Write plaintext to it.
        try {
            if (outputStream != null) {
                outputStream.write(getBytes(SOURCE_FILE_PATH));
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("文件大小：");
        stringBuilder.append(FileSizeUtil.getAutoFileOrFilesSize(SOURCE_FILE_PATH));
        stringBuilder.append("\n");
        stringBuilder.append("加密时间：");
        stringBuilder.append(endTime - startTime);
        resultTv.setText(stringBuilder);
    }

    @OnClick(R.id.decryption)
    void decryption() {
        long startTime = System.currentTimeMillis();

        // Creates a new Crypto object with default implementations of
        // a key chain as well as native library.
        Crypto crypto = new Crypto(new SharedPrefsBackedKeyChain(this),
                new SystemNativeCryptoLibrary());

        // Check for whether the crypto functionality is available
        // This might fail if Android does not load libaries correctly.
        if (!crypto.isAvailable()) {
            return;
        }

        // Get the file to which ciphertext has been written.
        FileInputStream fileStream = null;
        try {
            fileStream = new FileInputStream(new File(ENCRY_FILE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Creates an input stream which decrypts the data as
        // it is read from it.
        InputStream inputStream = null;
        try {
            inputStream = crypto.getCipherInputStream(
                    fileStream,
                    new Entity("encry"));

            // Read into a byte array.
            int read;
            byte[] buffer = new byte[1024];

            // You must read the entire stream to completion.
            // The verification is done at the end of the stream.
            // Thus not reading till the end of the stream will cause
            // a security bug.
            OutputStream out = new FileOutputStream(new File(DECRY_FILE_PATH));
            while ((read = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            inputStream.close();
        } catch (IOException | CryptoInitializationException | KeyChainException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("文件大小：");
        stringBuilder.append(FileSizeUtil.getAutoFileOrFilesSize(ENCRY_FILE_PATH));
        stringBuilder.append("\n");
        stringBuilder.append("解密时间：");
        stringBuilder.append(endTime - startTime);
        resultTv.setText(stringBuilder);

    }

    //获得指定文件的byte数组
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
