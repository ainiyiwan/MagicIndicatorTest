package com.zhimadai.cctvmall.magicindicatortest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.zhimadai.cctvmall.magicindicatortest.fragment.MyFragment1;
import com.zhimadai.cctvmall.magicindicatortest.fragment.MyFragment2;
import com.zhimadai.cctvmall.magicindicatortest.indicator.MagicIndicator;
import com.zhimadai.cctvmall.magicindicatortest.indicator.UIUtil;
import com.zhimadai.cctvmall.magicindicatortest.indicator.ViewPagerHelper;
import com.zhimadai.cctvmall.magicindicatortest.indicator.commonnavigator.ColorTransitionPagerTitleView;
import com.zhimadai.cctvmall.magicindicatortest.indicator.commonnavigator.CommonNavigator;
import com.zhimadai.cctvmall.magicindicatortest.indicator.commonnavigator.SimplePagerTitleView;
import com.zhimadai.cctvmall.magicindicatortest.indicator.commonnavigator.abs.CommonNavigatorAdapter;
import com.zhimadai.cctvmall.magicindicatortest.indicator.commonnavigator.abs.IPagerIndicator;
import com.zhimadai.cctvmall.magicindicatortest.indicator.commonnavigator.abs.IPagerTitleView;
import com.zhimadai.cctvmall.magicindicatortest.indicator.commonnavigator.titles.badge.BadgePagerTitleView;
import com.zhimadai.cctvmall.magicindicatortest.indicator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.magic_indicator4)
    MagicIndicator mIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private static final String[] CHANNELS = new String[]{"邀请公司", "提现明细"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private List<Fragment> fragmentList;
    private MyViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MyFragment1 myFragment1 = new MyFragment1();
        MyFragment2 myFragment2 = new MyFragment2();
        fragmentList = new ArrayList<>();
        fragmentList.add(myFragment1);
        fragmentList.add(myFragment2);

        adapter = new MyViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(adapter);

        initMagicIndicator4();
    }

    private void initMagicIndicator4() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(Color.parseColor("#838383"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#333333"));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setColors(Color.parseColor("#333333"));
                linePagerIndicator.setLineHeight(UIUtil.dip2px(MainActivity.this,3));
                linePagerIndicator.setLineWidth(UIUtil.dip2px(MainActivity.this,60));
                return linePagerIndicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(MainActivity.this, 28);
            }
        });
        ViewPagerHelper.bind(mIndicator, mViewPager);
    }

}
