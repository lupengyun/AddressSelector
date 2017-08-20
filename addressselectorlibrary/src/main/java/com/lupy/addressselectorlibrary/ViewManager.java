package com.lupy.addressselectorlibrary;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lupy.addressselectorlibrary.bean.Area;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lupy
 * on 2017/8/19.
 */

public class ViewManager {
    private Context context;
    private View view;
    private ListView addressList;
    private TabLayout tabLayout;
    private FrameLayout progressLayout;
    private TextView dialogTitle;
    private AddSelector addSelector;

    private String[] titleArray = new String[4];

    //缓存数据
    private ArrayList<Area> provienceData = new ArrayList<Area>();
    private ArrayList<Area> cityData = new ArrayList<Area>();
    private ArrayList<Area> townData = new ArrayList<Area>();
    private ArrayList<Area> countyData = new ArrayList<Area>();
    //选中位置
    private int[] areaSelectPos = new int[4];

    private ArrayList<Area> showData = new ArrayList<Area>();//listview数据源


    public static final int PROVIENCE_LEVEL = 0;//省
    public static final int CITY_LEVEL = 1;//市
    public static final int TOWN_LEVEL = 2;//县镇
    public static final int COUNTY_LEVEL = 3;//村
    private AreaAdapter areaAdapter;


    public ViewManager(Context context, AddSelector addSelector) {
        this.context = context;
        this.addSelector = addSelector;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.address_selector_layout, null);
        addressList = (ListView) view.findViewById(R.id.listview);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_title);
        progressLayout = (FrameLayout) view.findViewById(R.id.progress_layout);
        dialogTitle = (TextView) view.findViewById(R.id.dialog_title);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                showData.clear();
                switch (position) {
                    case PROVIENCE_LEVEL:
                        showData.addAll(provienceData);
                        break;
                    case CITY_LEVEL:
                        showData.addAll(cityData);
                        break;
                    case TOWN_LEVEL:
                        showData.addAll(townData);
                        break;
                    case COUNTY_LEVEL:
                        showData.addAll(countyData);
                        break;
                }
                areaAdapter.notifyDataSetChanged();
                addressList.setSelection(areaSelectPos[position] == -1 ? 0 : areaSelectPos[position]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        areaAdapter = new AreaAdapter(showData, context);
        addressList.setAdapter(areaAdapter);
        addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectPos = tabLayout.getSelectedTabPosition();
                requestData(showData.get(position).id, selectPos + 1);
                titleArray[selectPos] = showData.get(position).name;
                for (int i = selectPos; i < 4; i++) {
                    areaSelectPos[i] = -1;
                }
                areaSelectPos[selectPos] = position;
            }
        });
    }

    public View getView() {
        return view;
    }

    public void initProvience() {
        requestData("", PROVIENCE_LEVEL);
    }

    /**
     * 通知请求数据
     *
     * @param areaId
     * @param areaLevel
     */
    private void requestData(final String areaId, final int areaLevel) {
        progressLayout.setVisibility(View.VISIBLE);
        addSelector.getData(areaId, areaLevel);
    }

    /**
     * 接受数据后更新
     *
     * @param areas
     * @param areaLevel
     */
    public void updataAreaList(List<Area> areas, int areaLevel) {
        progressLayout.setVisibility(View.GONE);
        if (areaLevel == 4 || areas == null) {
            addSelector.dismiss();
            addSelector.onSelectResult(
                    areaSelectPos[0] == -1 ? null : provienceData.get(areaSelectPos[0]),
                    areaSelectPos[1] == -1 ? null : cityData.get(areaSelectPos[1]),
                    areaSelectPos[2] == -1 ? null : townData.get(areaSelectPos[2]),
                    areaSelectPos[3] == -1 ? null : countyData.get(areaSelectPos[3])
            );
            return;
        }
        switch (areaLevel) {
            case PROVIENCE_LEVEL:
                provienceData.clear();
                provienceData.addAll(areas);
                break;
            case CITY_LEVEL:
                cityData.clear();
                cityData.addAll(areas);
                break;
            case TOWN_LEVEL:
                townData.clear();
                townData.addAll(areas);
                break;
            case COUNTY_LEVEL:
                countyData.clear();
                countyData.addAll(areas);
                break;
        }

        showData.clear();
        showData.addAll(areas);
        areaAdapter.notifyDataSetChanged();
        updataTabTitle(areaLevel);
    }

    /**
     * 更新tabtitle
     */
    private void updataTabTitle(int selcetPos) {
        tabLayout.removeAllTabs();
        for (int i = 0; i < selcetPos; i++) {
            String titleStr = titleArray[i];
            if (!TextUtils.isEmpty(titleStr)) {
                tabLayout.addTab(tabLayout.newTab().setText(titleStr));
            }
        }
        tabLayout.addTab(tabLayout.newTab().setText("请选择"), true);
    }

}
