package com.revstar.pullrefreshlayout;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;




public class DefaultHeaderView extends RelativeLayout implements PullRefreshLayout.UpdateHandler {

    private final ImageView mImageView;
    private final View mProgress;
    private TextView mTextView;
    private static final String TAG = "DefultHeaderView";

    public DefaultHeaderView(Context context) {
        this(context, null);
    }

    public DefaultHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.defult_header_layout, this, true);
        mTextView = (TextView) findViewById(R.id.text);
        mImageView = (ImageView) findViewById(R.id.image);
        mProgress = findViewById(R.id.progress);
    }

    //箭头是否朝上
    private boolean arrowUp;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onProgressUpdate(PullRefreshLayout layout, PullRefreshLayout.Progress progress, int status) {
        switch (status) {
            case PullRefreshLayout.STATUS_INIT:
                mImageView.setVisibility(VISIBLE);
                mImageView.setImageResource(R.drawable.arrow_down);
                mProgress.setVisibility(GONE);
                break;

            case PullRefreshLayout.STATUS_DRAGGING:
                if (progress.getCurrentY() >= progress.getRefreshY()) {
                    if (arrowUp) {
                        arrowUp = false;
                        mTextView.setText(R.string.release_to_refresh);
                        mImageView.setImageResource(R.drawable.arrow_up);
                    }

                } else {
                    if (!arrowUp) {
                        arrowUp = true;
                        mTextView.setText(R.string.pull_to_refresh);
                        mImageView.setImageResource(R.drawable.arrow_down);
                    }
                }
                break;
            case PullRefreshLayout.STATUS_RELEASE_PREPARE:
                mTextView.setText(R.string.begin_refresh);
                mImageView.setVisibility(GONE);
                break;
            case PullRefreshLayout.STATUS_REFRESHING:
                mTextView.setText(R.string.refreshing);
                mImageView.setImageResource(android.R.drawable.btn_dropdown);
                mProgress.setVisibility(VISIBLE);
                break;
            case PullRefreshLayout.STATUS_COMPLETE:
                mTextView.setText(R.string.refresh_complete);
                mProgress.setVisibility(GONE);
                mImageView.setVisibility(VISIBLE);
                mImageView.setImageResource(R.drawable.okey);
                break;
            case PullRefreshLayout.STATUS_RELEASE_CANCEL:
                mTextView.setText(R.string.cancel_refresh);
                break;
        }

    }
}
