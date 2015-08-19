package com.common.androidtemplate.module.function;

import android.os.Bundle;
import android.util.Log;
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
public class ConcealActivity extends BaseBackActivity {

    private static final String SOURCE_FILE_PATH =
            "/mnt/sdcard/Conceal/test.zip";
    private static final String ENCRY_FILE_PATH =
            "/mnt/sdcard/Conceal/test_encry.zip";
    private static final String DECRY_FILE_PATH =
            "/mnt/sdcard/Conceal/test_decry.zip";

    @Bind(R.id.result)
    TextView resultTv;

    private Crypto mCrypto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conceal);
        ButterKnife.bind(this);

        mCrypto = new Crypto(new SharedPrefsBackedKeyChain(this),
                new SystemNativeCryptoLibrary());

        // Check for whether the mCrypto functionality is available
        // This might fail if Android does not load libaries correctly.
        if (!mCrypto.isAvailable()) {
            Log.d("ConcealActivity", "加密不可用");
        }
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.encryption)
    void doEncry() {
        long startTime = System.currentTimeMillis();
        doEncryptionFiles("/mnt/sdcard/Conceal/test", "/mnt/sdcard/Conceal/test1", false);
        long endTime = System.currentTimeMillis();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("文件大小：");
        stringBuilder.append(FileSizeUtil.getAutoFileOrFilesSize("/mnt/sdcard/Conceal/test"));
        stringBuilder.append("\n");
        stringBuilder.append("加密时间：");
        stringBuilder.append(endTime - startTime);
        resultTv.setText(stringBuilder);
    }

    void encryption() {
        long startTime = System.currentTimeMillis();

        // Creates a new Crypto object with default implementations of
        // a key chain as well as native library.
        Crypto crypto = new Crypto(new SharedPrefsBackedKeyChain(this),
                new SystemNativeCryptoLibrary());

        // Check for whether the mCrypto functionality is available
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

    /**
     * 加密指定文件目录中的数据，生成解密后的数据
     * @param srcDir 加密前的源文件目录
     * @param destDir 加密后放置的文件目录
     */
    private void doEncryptionFiles(String srcDir, String destDir, Boolean delSrcDir) {
        File srcFile = new File(srcDir);
        if(!srcFile.exists()) {
            return;
        }

        File destFile = new File(destDir);
        if(!destFile.exists()) {
            destFile.mkdirs();
        }

        if(!srcFile.isDirectory() && !destFile.isDirectory()) {
            return;
        }

        findEncryFile(srcFile, destFile, delSrcDir);
    }

    /**
     *
     * @param srcFile
     * @param destFile
     * @param delSrcFile
     */
    private void findEncryFile(File srcFile, File destFile, Boolean delSrcFile) {
        if(!srcFile.exists()) {
            return;
        }
        if(srcFile.isDirectory() && !destFile.exists()) {
            destFile.mkdirs();
        }
        if(srcFile.isFile()) {
            doEncryFile(srcFile, destFile, delSrcFile);
        } else {
            //此处可添加过滤器，对文件格式进行筛选
            File[] files = srcFile.listFiles();
            for(int i=0; i<files.length; i++) {
                File subDestFile = new File(destFile, files[i].getName());
                findEncryFile(files[i], subDestFile, delSrcFile);
            }
        }
    }

    /**
     * 对单个文件进行加密处理
     * @param srcFile
     * @param destFile
     * @param delSrcFile
     */
    private void doEncryFile(File srcFile, File destFile, Boolean delSrcFile) {
        if (!mCrypto.isAvailable()) {
            return;
        }

        try {
            OutputStream fileStream = new BufferedOutputStream(new FileOutputStream(destFile));
            OutputStream outputStream = mCrypto.getCipherOutputStream(fileStream, new Entity("encry"));
            if (outputStream != null) {
                writeToStream(outputStream, srcFile);
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToStream(OutputStream outputStream, File srcFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(srcFile);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                outputStream.write(b);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.decryption)
    void decryption() {
        long startTime = System.currentTimeMillis();

        // Creates a new Crypto object with default implementations of
        // a key chain as well as native library.
        Crypto crypto = new Crypto(new SharedPrefsBackedKeyChain(this),
                new SystemNativeCryptoLibrary());

        // Check for whether the mCrypto functionality is available
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
