package com.lupy.addressselectorlibrary.dataInterface;

import com.lupy.addressselectorlibrary.bean.Area;

import java.util.List;

/**
 * 数据来源
 * Created by Lupy
 * on 2017/8/19.
 */

public interface DataResourceInterface {
    List<Area> receiverData(String areaId);
}
