package com.example.user.ownread.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.user.ownread.R;
import com.example.user.ownread.Service.MusicService;
import com.example.user.ownread.adapter.MyFragPagerAdapter;
import com.example.user.ownread.fragment.BaseFragment;
import com.example.user.ownread.fragment.ContentFragment;
import com.example.user.ownread.fragment.ControlFragment;
import com.example.user.ownread.fragment.UserFragment;
import com.example.user.ownread.utils.BroadCastValues;
import com.example.user.ownread.view.HorViewPager;
import com.example.user.ownread.view.MyViewPagerAnimator;
import com.example.user.ownread.view.MyViewPagerScroller;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    public HorViewPager controlView;
    private ArrayList<Fragment> fragmentList;
    public BaseFragment controlFragment, contentFragment, userFragment;
    private MyFragPagerAdapter adapter;
    public RequestQueue requestQueue;
    private UIBroadCastReceiver uiBroadCastReceiver;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        initView();
        setData();
        controlView.setCurrentItem(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, MusicService.class));
        registerReceiver();
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastValues.OWNREAD_MUSIC_SEND_BROADCAST);
        uiBroadCastReceiver = new UIBroadCastReceiver();
        registerReceiver(uiBroadCastReceiver, filter);
    }

    private void initView() {
        controlView = (HorViewPager) findViewById(R.id.main_control);
        controlView.setPageTransformer(true, new MyViewPagerAnimator());
    }

    private void setData() {
        contentFragment = new ContentFragment();
        controlFragment = new ControlFragment();
        userFragment = new UserFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(controlFragment);
        fragmentList.add(contentFragment);
        fragmentList.add(userFragment);
        adapter = new MyFragPagerAdapter(getSupportFragmentManager(), fragmentList);
        controlView.setAdapter(adapter);
        controlView.setOffscreenPageLimit(3);
        setViewPagerScroller();
    }

    private void setViewPagerScroller() {
        MyViewPagerScroller scroller = new MyViewPagerScroller(this);
        scroller.setScrollDuration(1000);
        scroller.initViewPagerScroll(controlView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent stopIntent = new Intent();
        stopIntent.setAction(BroadCastValues.OWNREAD_MUSIC_BROADCAST);
        stopIntent.putExtra("type", BroadCastValues.MEDIA_STOP);
        sendBroadcast(stopIntent);
        this.stopService(new Intent(this, MusicService.class));
        unregisterReceiver(uiBroadCastReceiver);
    }

    public class UIBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int serviceType = intent.getIntExtra("serviceType", -1);
            switch (serviceType) {
                case BroadCastValues.SERVICE_MEDIA_PLAY:
                    break;
                case BroadCastValues.SERVICEMEDIA_PAUSE:
                    break;
                case BroadCastValues.SERVICE_MEDIA_STOP:
                    break;
            }
        }
    }

    /**
     * 拦截返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (controlView.getCurrentItem() != 1) {
                controlView.setCurrentItem(1);
            } else {
                exit();
            }
        }
        return false;
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            onDestroy();
            System.exit(0);
        }
    }
}
