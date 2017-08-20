package com.lupy.addressselectorlibrary;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lupy.addressselectorlibrary.bean.Area;
import com.lupy.addressselectorlibrary.dataInterface.DataResourceInterface;
import com.lupy.addressselectorlibrary.dataInterface.OnSelectResultListener;

import java.util.List;

/**
 * Created by Lupy
 * on 2017/8/19.
 */

public class AddSelector extends Dialog {

    private ViewManager vm;
    private DataResourceInterface dataResourceInterface;
    private OnSelectResultListener onSelectResultListener;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null && msg.what == 110) {
                vm.updataAreaList((List<Area>) msg.obj, msg.arg1);
            }
        }
    };

    public AddSelector(@NonNull Context context) {
        super(context, R.style.bottom_dialog);
        init(context);
    }

    public AddSelector(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.bottom_dialog);
        init(context);
    }

    /**
     * 初始化参数
     */
    private void init(Context context) {
        vm = new ViewManager(context, this);
        setContentView(vm.getView());

        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = 512;
        window.setAttributes(attributes);

        window.setGravity(Gravity.BOTTOM);
    }


    public void setDataResourceInterface(DataResourceInterface dataResourceInterface) {
        this.dataResourceInterface = dataResourceInterface;

    }

    public void setOnSelectResultListener(OnSelectResultListener onSelectResultListener) {
        this.onSelectResultListener = onSelectResultListener;
    }

    public void onSelectResult(Area provience,Area city,Area town,Area country) {
        if (onSelectResultListener != null) {
            onSelectResultListener.onSelectResult(provience,city,town,country);
        }
    }

    public void getData(final String areaId, final int areaLevel) {
        // TODO: 2017/8/19 优化线程
        new Thread() {
            @Override
            public void run() {
                List<Area> areas = dataResourceInterface.receiverData(areaId);
                Message message = handler.obtainMessage();
                message.obj = areas;
                message.arg1 = areaLevel;
                message.what = 110;
                handler.sendMessage(message);
            }
        }.start();
    }


    @Override
    public void show() {
        if (dataResourceInterface == null) {
            throw new RuntimeException("DataResourceInterface数据源不能为空，请调用setDataResourceInterface()");
        }
        super.show();
        vm.initProvience();
    }
}
