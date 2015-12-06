package utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class ImageTextView extends TextView {
	private Bitmap bitmap;
	private String text;
	Drawable d;

	public ImageTextView(Context context) {
		super(context);
		text = this.getText().toString().substring(0, 1);
		bitmap = BitmapUtils.getIndustry(context, text);
		d = BitmapUtils.bitmapTodrawable(bitmap);
		this.setCompoundDrawables(d, null, null, null);
	}

	public ImageTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		text = this.getText().toString().substring(0, 1);
		bitmap = BitmapUtils.getIndustry(context, text);
		d = BitmapUtils.bitmapTodrawable(bitmap);
		this.setCompoundDrawables(d, null, null, null);
	}

	public ImageTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		text = this.getText().toString().substring(0, 1);
		bitmap = BitmapUtils.getIndustry(context, text);
		d = BitmapUtils.bitmapTodrawable(bitmap);
		this.setCompoundDrawables(d, null, null, null);
	}

}
