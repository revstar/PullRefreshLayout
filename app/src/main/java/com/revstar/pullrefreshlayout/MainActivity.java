package com.revstar.pullrefreshlayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView rvData;

    private List<String>rvDataList;
    private RvDataAdapter mRvDataAdapter;

    PullRefreshLayout pullRefreshLayout;

    HeaderView mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initRv();
    }

    private void initView() {
        rvData=findViewById(R.id.rv_data);
        pullRefreshLayout=findViewById(R.id.pull_refresh_layout);

        mHeaderView=new HeaderView(this);
        mHeaderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        pullRefreshLayout.setHeaderView(mHeaderView);
        pullRefreshLayout.setBackgroundColor(Color.WHITE);
        pullRefreshLayout.setAutoRefreshDuration(400);

        pullRefreshLayout.addOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvDataList.set(0,"刷新的数据");
                        mRvDataAdapter.notifyDataSetChanged();
                        pullRefreshLayout.refreshComplete();
                    }
                },2000);
            }
        });


    }

    private void initData() {
        rvDataList=new ArrayList<>();
        for (int i=0;i<20;i++){
            rvDataList.add(i+"");
        }
    }
    private void initRv() {
        rvData.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        mRvDataAdapter=new RvDataAdapter(R.layout.rv_item,rvDataList);
        rvData.setAdapter(mRvDataAdapter);
    }
}
