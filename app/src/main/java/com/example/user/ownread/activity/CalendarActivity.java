package com.example.user.ownread.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.user.ownread.R;
import com.example.user.ownread.view.VerticalViewPager;

public class CalendarActivity extends Activity {


    private TextView mTopTvTextView;
    private VerticalViewPager mCalendarVvp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initView();
    }

    private void initView() {
        mCalendarVvp = (VerticalViewPager) findViewById(R.id.calendar_vvp);
    }


}
