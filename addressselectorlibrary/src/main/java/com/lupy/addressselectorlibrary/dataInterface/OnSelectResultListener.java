package com.lupy.addressselectorlibrary.dataInterface;

import com.lupy.addressselectorlibrary.bean.Area;

/**
 * Created by Lupy
 * on 2017/8/20.
 */

public interface OnSelectResultListener {
    /**
     * 选中结果
     * @param provience
     * @param city
     * @param town
     * @param country
     */
    void onSelectResult(Area provience,Area city,Area town,Area country);
}
