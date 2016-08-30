package com.appheader.DingQuPostman.ui.main.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.appheader.DingQuPostman.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class SlideShowView extends FrameLayout {
    //轮播图图片数量
    private final static int IMAGE_COUNT = 5;
    //自动轮播的时间间隔
    private final static int TIME_INTERVAL = 5;
    //自动轮播启用开关
    private final static boolean isAutoPlay = true;
    //自定义轮播图的资源ID
    private int[] imagesResIds;
    //放轮播图片的ImageView的list
    private List<ImageView> imageViewsList;
    //放圆点的View的list
    private List<View> dotViewsList;
    private ViewPager viewPager;
    //当前轮播页
    private int currentItem = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;
    //Handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //TODO Auto -generated method stub
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }
    };
    //Code中实例化一个View会调用
    public SlideShowView(Context context) {
        this(context, null);
        //TODO Auto -generated constructor stub
    }
    //xml中定义会调用第二个构造函数
    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        //TODO Auto -generated constructor stub

    }


    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //TODO Auto -generated constructor stub
        initData();
        initUI(context);
        if (isAutoPlay) {
            startPlay();
        }
    }

    /**
     * <p/>
     * 开始轮播图切换
     * 085.
     */
    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4,
                TimeUnit.SECONDS);
    }

    /**
     * <p/>
     * 停止轮播图切换
     */

    private void stopPlay() {
        scheduledExecutorService.shutdown();
    }

    /**
     * 097.
     * <p/>
     * 初始化相关Data
     * 098.
     */
    public void initData() {
        //loadImageUrl();
        imagesResIds = new int[]{R.mipmap.ic_page_one, R.mipmap.ic_page_two, R.mipmap.ic_page_three};
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();
    }

    /**
     * <p/>
     * 初始化Views等UI
     * 114.
     */
    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
        for (int imageID : imagesResIds) {
            ImageView view = new ImageView(context);
            view.setImageResource(imageID);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewsList.add(view);
        }
        dotViewsList.add(findViewById(R.id.v_dot1));
        dotViewsList.add(findViewById(R.id.v_dot2));
        dotViewsList.add(findViewById(R.id.v_dot3));
        /*dotViewsList.add(findViewById(R.id.v_dot4));
        dotViewsList.add(findViewById(R.id.v_dot5));*/
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(View container, int position, Object object) {
            //TODO Auto -generated method stub
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager) container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            //TODO Auto -generated method stub
            ((ViewPager) container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
//TODO Auto -generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
//TODO Auto -generated method stub
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
//TODO Auto -generated method stub
        }

        @Override
        public Parcelable saveState() {
//TODO Auto -generated method stub
            return null;

        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto -generated method stub
        }

        @Override
        public void finishUpdate(View arg0) {
            //TODO Auto -generated method stub
        }

    }


    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {

//TODO Auto -generated method stub

            switch (arg0) {
                case 1://手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2://界面切换中
                    isAutoPlay = true;
                    break;
                case 0:
                    //滑动结束，即切换完毕或者加载完毕
                    //当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                    }
                    //当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                    }
                    break;

            }
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            //TODO Auto -generated method stub
        }
        @Override
        public void onPageSelected(int pos) {
            //TODO Auto -generated method stub
            currentItem = pos;
            for (int i = 0; i < dotViewsList.size(); i++) {
                if (i == pos) {
                    ((View) dotViewsList.get(pos)).setBackgroundResource(R.drawable.dot_black);
                } else {
                    ((View) dotViewsList.get(i)).setBackgroundResource(R.drawable.dot_white);
                }
            }
        }
        /**
         * 263.
         * <p/>
         * 销毁ImageView资源，回收内存
         */
        private void destoryBitmaps() {
            for (int i = 0; i < IMAGE_COUNT; i++) {
                ImageView imageView = imageViewsList.get(i);
                Drawable drawable = imageView.getDrawable();
                if (drawable != null) {
                //解除drawable对view的引用
                    drawable.setCallback(null);
                }

            }

        }


    }

    /**
     *
     * 执行轮播图切换任务
     *
     *
     * @author caizhiming
     *
     */

    private class SlideShowTask implements Runnable {
        @Override
        public void run() {
            //TODO Auto -generated method stub
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }

}