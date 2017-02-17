package com.example.user.ownread.fragment;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.ownread.R;
import com.example.user.ownread.activity.MainActivity;

/**
 * Created by user on 2016/7/26.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {

    private Button mBackBtn;
    private Button mSettingBtn;
    private Button mLoginBtn;
    private TextView mMessageTv;
    private TextView mCollectionTv;
    private TextView mOfflineTv;
    private TextView mNoteTv;
    private Typeface typeface;

    @Override
    public View initView() {
        View userFragView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_user, null);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/PMingLiU.ttf");
        mBackBtn = (Button) userFragView.findViewById(R.id.user_frag_close_btn);
        mSettingBtn = (Button) userFragView.findViewById(R.id.user_frag_setting_btn);
        mLoginBtn = (Button) userFragView.findViewById(R.id.user_frag_login_btn);
        mMessageTv = (TextView) userFragView.findViewById(R.id.user_frag_message_tv);
        mMessageTv.setTypeface(typeface);
        mCollectionTv = (TextView) userFragView.findViewById(R.id.user_frag_collection_tv);
        mCollectionTv.setTypeface(typeface);
        mOfflineTv = (TextView) userFragView.findViewById(R.id.user_frag_offline_tv);
        mOfflineTv.setTypeface(typeface);
        mNoteTv = (TextView) userFragView.findViewById(R.id.frag_user_note_tv);
        mNoteTv.setTypeface(typeface);
        setOnClick();
        return userFragView;
    }

    private void setOnClick() {
        mBackBtn.setOnClickListener(this);
        mSettingBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mCollectionTv.setOnClickListener(this);
        mMessageTv.setOnClickListener(this);
        mOfflineTv.setOnClickListener(this);
        mNoteTv.setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_frag_close_btn:
                ((MainActivity) getActivity()).controlView.setCurrentItem(1);
                break;
            case R.id.user_frag_setting_btn:
                break;
            case R.id.user_frag_login_btn:
                break;
            case R.id.user_frag_message_tv:
                break;
            case R.id.user_frag_offline_tv:
                break;
            case R.id.user_frag_collection_tv:
                break;
            case R.id.frag_user_note_tv:
                break;
        }
    }
}
