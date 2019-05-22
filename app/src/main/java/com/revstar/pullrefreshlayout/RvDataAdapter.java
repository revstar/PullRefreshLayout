package com.revstar.pullrefreshlayout;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Create on 2019/5/22 10:56
 * author revstar
 * Email 1967919189@qq.com
 */
public class RvDataAdapter  extends BaseQuickAdapter<String,BaseViewHolder> {
    public RvDataAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item,item);
    }
}
