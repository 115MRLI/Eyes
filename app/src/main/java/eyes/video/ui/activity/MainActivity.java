package eyes.video.ui.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import eyes.video.R;
import eyes.video.base.BaseActivity;
import eyes.video.model.bean.Home;
import eyes.video.model.bean.Video;
import eyes.video.presenter.IHomePresenter;
import eyes.video.presenter.impl.HomePresenterImpl;
import eyes.video.ui.adapter.MenuAdapter;
import eyes.video.ui.contract.MainActivityContract;
import eyes.video.ui.fragment.TypeFragment;
import eyes.video.utils.EysUtils;

public class MainActivity extends BaseActivity implements MainActivityContract, View.OnClickListener {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.viewpager)
    FrameLayout viewpager;
    @BindView(R.id.typestr)
    TextView typestr;
    @BindView(R.id.back_lin)
    LinearLayout backLin;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    private MenuAdapter menuAdapter;
    private IHomePresenter presenter = null;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        presenter = new HomePresenterImpl();
        presenter.attachView(this);
        presenter.getMenu();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        typestr.setText("每日推荐");
        backLin.setOnClickListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        TypeFragment fragment = new TypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url","/");
        fragment.setArguments(bundle);
        showFragment(fragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setMenu(List<Home> homes) {
        menuAdapter = new MenuAdapter(homes);
        recycler.setAdapter(menuAdapter);
        menuAdapter.setItmeClick(new MenuAdapter.ItmeClick() {
            @Override
            public void onItmeClick(Home item) {
                if (item.getMenutitle().equals("自拍图片")){
                    EysUtils.showToast("该功能暂时不开放");
                }else if (item.getMenutitle().equals("情色小说")){
                    EysUtils.showToast("该功能暂时不开放");
                }else if(item.getMenutitle().equals("明星艳照门")){
                    EysUtils.showToast("该功能暂时不开放");
                }else {
                    typestr.setText(item.getMenutitle());
                }
                TypeFragment fragment = new TypeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url",item.getMenuUrl());
                fragment.setArguments(bundle);
                showFragment(fragment);
                showDrawerLayout();
            }
        });
    }

    @Override
    public void setVideos(List<Video> videos) {

    }

    @Override
    public void showToas(String str) {
        EysUtils.showToast(str);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_lin:
                showDrawerLayout();
                break;
            default:
                break;
        }
    }

    private void showDrawerLayout() {
        if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {
            drawerlayout.openDrawer(Gravity.LEFT);
        } else {
            drawerlayout.closeDrawer(Gravity.LEFT);
        }
    }
    /**
     * 跳转fragment
     */
    private void showFragment(Fragment fragment) {
        // 获取 FragmentTransaction  对象
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //如果fragment2没有被添加过，就添加它替换当前的fragment1
        transaction.replace(R.id.viewpager, fragment).commit();
    }
}
