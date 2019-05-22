package com.revstar.pullrefreshlayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;




public class HeaderView extends RelativeLayout implements PullRefreshLayout.UpdateHandler {


    private TextView mStatusTv;
    private AnimationDrawable mAnimationDrawable;

    private ImageView igProgress;

    private Context mContext;

    public HeaderView(Context context) {
        this(context, null);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        LayoutInflater.from(context).inflate(R.layout.header_item, this, true);

        mStatusTv = (TextView) findViewById(R.id.tv_status);
        igProgress = findViewById(R.id.ig_progress);
    }


    @Override
    public void onProgressUpdate(PullRefreshLayout layout, PullRefreshLayout.Progress progress, int status) {
        switch (status) {
            case PullRefreshLayout.STATUS_INIT:

                mStatusTv.setText(R.string.pull_to_refresh);
                if (mAnimationDrawable != null) {
                    mAnimationDrawable.stop();
                    mAnimationDrawable = null;
                }

                break;
            case PullRefreshLayout.STATUS_DRAGGING:
                float currentY = progress.getCurrentY();
                float refreshY = progress.getRefreshY();
                float percent = Math.min(1.0f, currentY / refreshY);
                if (percent >= 1.0f) {
                    mStatusTv.setText(R.string.release_to_refresh);
                    igProgress.setRotation(-90);
                } else {
                    mStatusTv.setText(R.string.pull_to_refresh);
                }
                break;
            case PullRefreshLayout.STATUS_RELEASE_PREPARE:
                break;
            case PullRefreshLayout.STATUS_REFRESHING:
                Animation circle_anim = AnimationUtils.loadAnimation(mContext, R.anim.ddqb_anim_round_rotate);
                LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
                circle_anim.setInterpolator(interpolator);
                    igProgress.startAnimation(circle_anim);  //开始动画
                mStatusTv.setText(R.string.refreshing);

                break;
            case PullRefreshLayout.STATUS_COMPLETE:
                igProgress.setBackgroundResource(0);
                mStatusTv.setText(R.string.refresh_complete);
                igProgress.clearAnimation();
                break;
        }
    }
}
