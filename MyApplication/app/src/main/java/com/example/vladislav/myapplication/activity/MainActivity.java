package com.example.vladislav.myapplication.activity;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.vladislav.myapplication.Interfaces.view.MainMvpView;
import com.example.vladislav.myapplication.ItemListAdapter.MainPageAdapter;
import com.example.vladislav.myapplication.R;
import com.example.vladislav.myapplication.app.App;
import com.example.vladislav.myapplication.app.BaseActivity;
import com.example.vladislav.myapplication.fragment.ItemListFragment;
import com.example.vladislav.myapplication.presenter.AMainPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.vladislav.myapplication.Support.Constants.TYPE_BALANCE;
import static com.example.vladislav.myapplication.Support.Constants.TYPE_EXPENSE;
import static com.example.vladislav.myapplication.Support.Constants.TYPE_INCOME;
import static com.example.vladislav.myapplication.Support.Constants.TYPE_KEY;

public class MainActivity extends BaseActivity implements MainMvpView, ViewPager.OnPageChangeListener {

    public static final String TAG = "MainActivity";

    @Inject
    AMainPresenter presenter;

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @OnClick(R.id.fab)
    public void onClickFab() {
        Intent intent = new Intent(MainActivity.this,AddItemActivity.class);
        intent.putExtra(TYPE_KEY,typeFragment);
        startActivityForResult(intent,ADD_ITEM_REQUEST);
    }

    private static String typeFragment = TYPE_EXPENSE;
    private static final int ADD_ITEM_REQUEST = 123;
    private boolean initFragmentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SignInActivity.account == null) {
            Intent checksignIn = new Intent(this, SignInActivity.class);
            startActivity(checksignIn);
        }else initFragments();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
        if (ItemListFragment.inActionMode())ItemListFragment.getActionMode().finish();
        if (position == 0) {
            fab.show();
            typeFragment = TYPE_EXPENSE;
        }
        else if (position == 1){
            fab.show();
            typeFragment = TYPE_INCOME;
        }
        else if (position == 2){
            fab.hide();
            typeFragment = TYPE_BALANCE;
        }
//        switch (position){      : НЕ КОРРЕКТНО РАБОТАЕТ
//            case 0:{
//                fab.show();
//                typeFragment = MainPageAdapter.TYPE_EXPENSE;
//            }
//            case 1:{
//                fab.show();
//                typeFragment = MainPageAdapter.TYPE_INCOME;
//            }
//            case 2:{
//                fab.hide();
//                typeFragment = MainPageAdapter.TYPE_UNKNOWN;
//            }
//        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }
    private void initFragments() {
        if (((App)getApplication()).isLogin() && !initFragmentStatus) {
            MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager());
            pager.setAdapter(adapter);
            initFragmentStatus = true;
        }
    }
}
