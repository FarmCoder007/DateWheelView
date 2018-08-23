package com.example.xu.mywheelview;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.xu.mywheelview.wheelview.OnWheelChangedListener;
import com.example.xu.mywheelview.wheelview.OnWheelScrollListener;
import com.example.xu.mywheelview.wheelview.WheelView;
import com.example.xu.mywheelview.wheelview.adapter.AbstractWheelTextAdapter1;


/**
 * Author: Chen.yuan
 * Email: hubeiqiyuan2010@163.com
 * Date: 2016/7/28 17:37
 * Description:日期选择window
 */
public class ChangeSexPopwindow extends PopupWindow implements View.OnClickListener {

  private Context context;
  private WheelView dateWheelView;

  private TextView btnSure;
  private TextView btnCancel;

  private ArrayList<String> arry_date = new ArrayList<String>();
  public CalendarTextAdapter dateAdapter;

  private int maxTextSize = 30;
  private int minTextSize = 18;

  private String selectMonth;

  private OnBirthListener onBirthListener;

  public ChangeSexPopwindow(final Context context, ArrayList<String> skuDates) {
    super(context);
    this.context = context;
    this.arry_date = skuDates;
    View view = View.inflate(context, R.layout.dialog_myinfo_changebirth, null);
    dateWheelView = (WheelView) view.findViewById(R.id.wv_birth_month);
    btnSure = (TextView) view.findViewById(R.id.btn_myinfo_sure);
    btnCancel = (TextView) view.findViewById(R.id.btn_myinfo_cancel);

    // 设置SelectPicPopupWindow的View
    this.setContentView(view);
    // 设置SelectPicPopupWindow弹出窗体的宽
    this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    // 设置SelectPicPopupWindow弹出窗体的高
    this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    // 设置SelectPicPopupWindow弹出窗体可点击
    this.setFocusable(true);
    // 设置SelectPicPopupWindow弹出窗体动画效果
    // this.setAnimationStyle(R.style.AnimBottom);
    // 实例化一个ColorDrawable颜色为半透明
    ColorDrawable dw = new ColorDrawable(0xb0000000);
    // 设置SelectPicPopupWindow弹出窗体的背景
    this.setBackgroundDrawable(dw);

    btnSure.setOnClickListener(this);
    btnCancel.setOnClickListener(this);

    dateWheelView.addChangingListener(new OnWheelChangedListener() {
      @Override
      public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        String currentText = (String) dateAdapter.getItemText(wheel.getCurrentItem());
        selectMonth = currentText;
        setTextviewSize(currentText, dateAdapter);
      }
    });

    dateWheelView.addScrollingListener(new OnWheelScrollListener() {

      @Override
      public void onScrollingStarted(WheelView wheel) {
        // TODO Auto-generated method stub
      }

      @Override
      public void onScrollingFinished(WheelView wheel) {
        // TODO Auto-generated method stub
        String currentText = (String) dateAdapter.getItemText(wheel.getCurrentItem());
        setTextviewSize(currentText, dateAdapter);
      }
    });
  }

  public void setCurrentItem(int currentPos) {
    dateAdapter = new CalendarTextAdapter(context, arry_date, currentPos,
        maxTextSize, minTextSize);
    dateWheelView.setVisibleItems(5);
    dateWheelView.setViewAdapter(dateAdapter);
    dateWheelView.setCurrentItem(currentPos);
  }

  private class CalendarTextAdapter extends AbstractWheelTextAdapter1 {
    ArrayList<String> list;

    protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem,
        int maxsize, int minsize) {
      super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
      this.list = list;
      setItemTextResource(R.id.tempValue);
    }

    @Override
    public View getItem(int index, View cachedView, ViewGroup parent) {
      View view = super.getItem(index, cachedView, parent);
      return view;
    }

    @Override
    public int getItemsCount() {
      return list.size();
    }

    @Override
    protected CharSequence getItemText(int index) {
      return list.get(index) + "";
    }
  }

  public void setBirthdayListener(OnBirthListener onBirthListener) {
    this.onBirthListener = onBirthListener;
  }

  @Override
  public void onClick(View v) {
    if (v == btnSure) {
      if (onBirthListener != null) {
        onBirthListener.onClick(selectMonth);
      }
    } else {
      dismiss();
    }
    dismiss();
  }

  public interface OnBirthListener {
    public void onClick(String date);
  }

  /**
   * 设置字体大小
   * 
   * @param curriteItemText
   * @param adapter
   */
  public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
    if (TextUtils.isEmpty(curriteItemText) || adapter == null) return;
    ArrayList<View> arrayList = adapter.getTestViews();
    int size = arrayList.size();
    String currentText;
    for (int i = 0; i < size; i++) {
      TextView textView = (TextView) arrayList.get(i);
      currentText = textView.getText().toString();
      if (curriteItemText.equals(currentText)) {
        textView.setTextSize(maxTextSize);
      } else {
        textView.setTextSize(minTextSize);
      }
    }
  }

}
