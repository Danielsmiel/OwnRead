package com.example.user.ownread.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.ownread.R;
import com.example.user.ownread.activity.ArticleActivity;
import com.example.user.ownread.activity.MainActivity;
import com.example.user.ownread.activity.ModelActivity;
import com.example.user.ownread.activity.SearchActivity;

/**
 * Created by user on 2016/7/26.
 */
public class ControlFragment extends BaseFragment implements View.OnClickListener {

    private Button mFragCloseButton;
    private Button mFragFindButton;
    private TextView mFragFristpageTextView;
    private TextView mFragTextTextView;
    private TextView mFragSoundTextView;
    private TextView mFragMeidaTextView;
    private TextView mFragDailyTextView;
    private int type;

    @Override
    public View initView() {
        View controlFragView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_control, null);
        mFragCloseButton = (Button) controlFragView.findViewById(R.id.control_frag_close);
        mFragFindButton = (Button) controlFragView.findViewById(R.id.control_frag_find);
        mFragFristpageTextView = (TextView) controlFragView.findViewById(R.id.control_frag_fristpage);
        mFragTextTextView = (TextView) controlFragView.findViewById(R.id.control_frag_text);
        mFragSoundTextView = (TextView) controlFragView.findViewById(R.id.control_frag_sound);
        mFragMeidaTextView = (TextView) controlFragView.findViewById(R.id.control_frag_meida);
        mFragDailyTextView = (TextView) controlFragView.findViewById(R.id.control_frag_daily);
        mFragCloseButton.setOnClickListener(this);
        mFragFindButton.setOnClickListener(this);
        mFragFristpageTextView.setOnClickListener(this);
        mFragTextTextView.setOnClickListener(this);
        mFragSoundTextView.setOnClickListener(this);
        mFragMeidaTextView.setOnClickListener(this);
        mFragDailyTextView.setOnClickListener(this);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/PMingLiU.ttf");
        mFragFristpageTextView.setTypeface(typeface);
        mFragTextTextView.setTypeface(typeface);
        mFragSoundTextView.setTypeface(typeface);
        mFragMeidaTextView.setTypeface(typeface);
        mFragDailyTextView.setTypeface(typeface);
        return controlFragView;
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.control_frag_close:
                ((MainActivity) getActivity()).controlView.setCurrentItem(1);
                break;
            case R.id.control_frag_find:
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.control_frag_fristpage:
                ((MainActivity) getActivity()).controlView.setCurrentItem(1);
                break;
            case R.id.control_frag_text:
                startActivity(1);
                break;
            case R.id.control_frag_sound:
                startActivity(3);
                break;
            case R.id.control_frag_meida:
                startActivity(2);
                break;
            case R.id.control_frag_daily:
                getActivity().startActivity(new Intent(getActivity(), ArticleActivity.class));
                break;
        }

    }

    private void startActivity(int type) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ModelActivity.class);
        intent.putExtra("type", type);
        getActivity().startActivity(intent);
    }
}
