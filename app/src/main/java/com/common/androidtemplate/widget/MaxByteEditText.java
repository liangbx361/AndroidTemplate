package com.common.androidtemplate.widget;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 
 * @描述：对输入的文字进行判断，不得超过指定的字节数
 * @作者：liang bao xian
 * @时间：2015年1月23日 下午5:18:07
 */
public class MaxByteEditText extends EditText {

	private int maxByte = 0;
	private MaxListener mListener;
	
	public MaxByteEditText(Context context) {
		super(context);
		init();
	}

	public MaxByteEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MaxByteEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		addTextChangedListener(mTextWatcher);
	}
	
	public void setMaxByte(int bytes) {
		maxByte = bytes;
	}
	
	private TextWatcher mTextWatcher = new TextWatcher() {

		private int count;

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			this.count = count;
//			Log.d("edittext_wacher_on", s + "|start=" + start + "|before="+ before + "|count" + count);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//			Log.d("edittext_wacher_before", s + "|start=" + start + "|count=" + count + "|after" + after);
		}

		@Override
		public void afterTextChanged(Editable s) {
			
			try {
				int inputLen = s.toString().getBytes("utf-8").length;
				if (inputLen <= maxByte) {
					int editStart = getSelectionStart();  
			        int editEnd = getSelectionEnd();  
					removeTextChangedListener(mTextWatcher);
					s.delete(editStart-count, editEnd);					
					addTextChangedListener(mTextWatcher);
				} else {
					//超过最大限制，提示用户无法输入更多
					if(mListener != null) {
						mListener.onMaxListener(maxByte);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}			
		}
	};
	
	public interface MaxListener {
		public void onMaxListener(int maxSize);
	}

	public void setMaxListener(MaxListener mListener) {
		this.mListener = mListener;
	};	
}
