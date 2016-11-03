package com.first.akashshrivastava.showernow;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    ViewPager mViewPager = null;
    public String gender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mViewPager = (CustomViewPager)findViewById(R.id.pager);


        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new MyAdapter(fragmentManager));
    }
    public void setNextPage(){
        mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1, true);
    }

    public void setPreviousPage(){
        mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1, true);
    }

    @Override // if back is pressed it changes fragment
    public void onBackPressed() {
        if(mViewPager.getCurrentItem() == 0){
            super.onBackPressed();
        }else {
            setPreviousPage();
        }
    }

}



class MyAdapter extends FragmentPagerAdapter {

    public MyAdapter(FragmentManager fm) {
    super(fm);

    }

    @Override
    public int getCount() {
        return 4;
    }


    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        if (position == 0){

            fragment = new fragment_A();
        }

        if (position == 1){

            fragment = new fragment_D();
        }

        if (position == 2){

            fragment = new fragment_B();
        }

        if (position == 3){

            fragment = new fragment_C();
        }

        return fragment;
    }
}

class CustomViewPager extends ViewPager {
    private boolean enabled;

     public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    } }


