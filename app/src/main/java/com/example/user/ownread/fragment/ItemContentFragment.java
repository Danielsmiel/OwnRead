package com.example.user.ownread.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.user.ownread.R;
import com.example.user.ownread.bean.ContentItemBean;
import com.example.user.ownread.utils.BroadCastValues;
import com.example.user.ownread.utils.FormatUtils;
import com.example.user.ownread.utils.ImageLoaderUtils;

/**
 * Created by user on 2016/8/1.
 */
public class ItemContentFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "ItemContentFragment";
    private ImageView mImageContentIv;
    public ImageView mSurfaceIv;
    private TextView mTypeTv;
    private TextView mTitleTv;
    private TextView mContentTv;
    private TextView mAuthorTv;
    private TextView mStartMusicTv;
    private TextView mEndMusicTv;
    private Button mCommentBtn;
    private Button mLikeBtn;
    public Button mPlayMusicBtn;
    private Button mBackMusicBtn;
    private Button mForwardMusicBtn;
    private TextView mPageViewsTv;
    public RelativeLayout mMusicRl;
    private SeekBar mMusicSb;
    private ContentItemBean.Datas itemData;
    private int model;
    private String videoPath;
    private String voicePath;

    public ItemContentFragment(ContentItemBean.Datas itemData) {
        this.itemData = itemData;
    }

    /**
     * 改变滚动条
     **/
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 0x007) {
                mMusicSb.setMax(BroadCastValues.MEDIA_MAX);
                mMusicSb.setProgress(BroadCastValues.MEDIA_PROGRASS);
                mStartMusicTv.setText(FormatUtils.formatTime(BroadCastValues.MEDIA_PROGRASS));
                mEndMusicTv.setText(FormatUtils.formatTime(BroadCastValues.MEDIA_MAX));
                handler.sendEmptyMessageDelayed(0x007, 1000);
            }
            return false;
        }
    });

    @Override
    public View initView() {
        View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_item_content, null);
        mImageContentIv = (ImageView) itemView.findViewById(R.id.item_content_thumbnail_iv);
        mSurfaceIv = (ImageView) itemView.findViewById(R.id.frag_content_surface_iv);
        mTypeTv = (TextView) itemView.findViewById(R.id.item_content_type);
        mTitleTv = (TextView) itemView.findViewById(R.id.item_content_title);
        mContentTv = (TextView) itemView.findViewById(R.id.item_content_content);
        mAuthorTv = (TextView) itemView.findViewById(R.id.item_content_author);
        mStartMusicTv = (TextView) itemView.findViewById(R.id.frag_content_start_time);
        mEndMusicTv = (TextView) itemView.findViewById(R.id.frag_content_end_time);
        mCommentBtn = (Button) itemView.findViewById(R.id.item_content_comment);
        mLikeBtn = (Button) itemView.findViewById(R.id.item_content_like);
        mBackMusicBtn = (Button) itemView.findViewById(R.id.frag_content_back_btn);
        mPlayMusicBtn = (Button) itemView.findViewById(R.id.frag_content_play_btn);
        mForwardMusicBtn = (Button) itemView.findViewById(R.id.frag_content_forward_btn);
        mPageViewsTv = (TextView) itemView.findViewById(R.id.item_content_view);
        mMusicRl = (RelativeLayout) itemView.findViewById(R.id.frag_content_music_rl);
        mMusicSb = (SeekBar) itemView.findViewById(R.id.frag_content_music_sb);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/PMingLiU.ttf");
        mTypeTv.setTypeface(typeface);
        mTitleTv.setTypeface(typeface);
        mContentTv.setTypeface(typeface);
        mCommentBtn.setTypeface(typeface);
        mLikeBtn.setTypeface(typeface);
        mPageViewsTv.setTypeface(typeface);
        mAuthorTv.setTypeface(typeface);
        setVisibility();
        setOnClick();
        return itemView;
    }

    //声明一下版本,适用于5.0以上(这个不太好啊，回头解决一下)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setVisibility() {
        model = Integer.parseInt(itemData.modle);
        switch (model) {
            case 2:
                videoPath = itemData.video;
                mSurfaceIv.setVisibility(View.VISIBLE);
                mSurfaceIv.setScaleType(ImageView.ScaleType.CENTER);
                mSurfaceIv.setImageDrawable(getActivity().getDrawable(R.drawable.library_video_play_symbol));
                break;
            case 3:
                voicePath = itemData.fm;
                mSurfaceIv.setVisibility(View.VISIBLE);
                mSurfaceIv.setScaleType(ImageView.ScaleType.CENTER);
                mSurfaceIv.setImageDrawable(getActivity().getDrawable(R.drawable.library_voice_play_symbol));
                break;
        }
    }

    private void setOnClick() {
        mLikeBtn.setOnClickListener(this);
        mSurfaceIv.setOnClickListener(this);
        mImageContentIv.setOnClickListener(this);
        mPlayMusicBtn.setOnClickListener(this);
        mMusicSb.setOnSeekBarChangeListener(seekBarListener);
    }

    @Override
    public void setData() {
        ImageLoaderUtils.getImageByLoader(itemData.thumbnail, mImageContentIv);
        mTypeTv.setText(itemData.category);
        mTitleTv.setText(itemData.title);
        mContentTv.setText(itemData.excerpt);
        mAuthorTv.setText(itemData.author);
        mCommentBtn.setText(" " + itemData.comment);
        mLikeBtn.setText(" " + itemData.good);
        mPageViewsTv.setText("阅读量：" + itemData.view);
    }

    SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (b) {
                BroadCastValues.MEDIA_PROGRASS = i;
                Intent intent = new Intent();
                intent.setAction(BroadCastValues.OWNREAD_MUSIC_BROADCAST);
                intent.putExtra("type", BroadCastValues.MEDIA_SEEK);
                getActivity().sendBroadcast(intent);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_content_like:
                break;
            case R.id.item_content_thumbnail_iv:
                //缩略图的点击事件
                break;
            case R.id.frag_content_surface_iv:
                //音视频图层的点击事件
                if (model == 3) {
                    mSurfaceIv.setVisibility(View.GONE);
                    mMusicRl.setVisibility(View.VISIBLE);
                    handler.sendEmptyMessage(0x007);
                    BroadCastValues.ITEM_ID = itemData.id;
                    Intent intent = new Intent();
                    intent.setAction(BroadCastValues.OWNREAD_MUSIC_BROADCAST);
                    intent.putExtra("type", BroadCastValues.MEDIA_PLAY);
                    intent.putExtra("voicePath", voicePath);
                    getActivity().sendBroadcast(intent);
                    mPlayMusicBtn.setBackgroundResource(R.drawable.music_pause);
                }
                break;
            case R.id.frag_content_play_btn:
                if (BroadCastValues.IS_PLAYING) {
                    Intent intent = new Intent();
                    intent.setAction(BroadCastValues.OWNREAD_MUSIC_BROADCAST);
                    intent.putExtra("type", BroadCastValues.MEDIA_PAUSE);
                    intent.putExtra("voicePath", voicePath);
                    getActivity().sendBroadcast(intent);
                    mPlayMusicBtn.setBackgroundResource(R.drawable.music_play);
                } else {
                    Intent intent = new Intent();
                    intent.setAction(BroadCastValues.OWNREAD_MUSIC_BROADCAST);
                    intent.putExtra("type", BroadCastValues.MEDIA_PLAY);
                    intent.putExtra("isFromPause", true);
                    intent.putExtra("voicePath", voicePath);
                    getActivity().sendBroadcast(intent);
                    mPlayMusicBtn.setBackgroundResource(R.drawable.music_pause);
                }
                break;
        }
    }
}
