package com.example.vladislav.myapplication;

import android.support.annotation.Nullable;
<<<<<<< HEAD
import android.support.annotation.StringRes;
=======
import android.support.design.widget.FloatingActionButton;
>>>>>>> exercise_5
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladislav.myapplication.Data.DataList;
import com.example.vladislav.myapplication.ItemListAdapter.ItemListAdapter;
import com.example.vladislav.myapplication.ItemListAdapter.MainPageAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemListFragment extends Fragment {
<<<<<<< HEAD
    RecyclerView recyclerView;
    private static final String TYPE_KEY = "type";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        ItemListAdapter.setData();
        ItemListAdapter adapter = new ItemListAdapter();
=======
    private static final String TAG = "ItemListFragment";
    private RecyclerView recyclerView;
    public Api api;
    private String type;
    ItemListAdapter adapter = new ItemListAdapter();
    SwipeRefreshLayout refresh;
    FloatingActionButton fab;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = App.getApi();
>>>>>>> exercise_5
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list,container,false);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.fab);
        if (type == MainPageAdapter.TYPE_UNKNOWN)fab.hide();
        refresh = view.findViewById(R.id.refresh);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        dataInsert();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dataInsert();
            }
        });
    }
    public static ItemListFragment createItemsFragment(String type){
        ItemListFragment fragment = new ItemListFragment();
        fragment.type = type;
        return fragment;
    }
<<<<<<< HEAD
    public static ItemListFragment setInstance(){
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_KEY,"Доходы");
        ItemListFragment itemListActivity = new ItemListFragment();
        itemListActivity.setArguments(bundle);
        return itemListActivity;
=======
    public void dataInsert() {
        Call<DataList>call = api.getItems(type);
        call.enqueue(new Callback<DataList>() {
            @Override
            public void onResponse(Call<DataList> call, Response<DataList> response) {
                adapter.setData(response.body());
                refresh.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<DataList> call, Throwable t) {
            }
        });
>>>>>>> exercise_5
    }
//    public void addData(){
//        Data da = new Data();
//        da.setName("My");
//        da.setPrice(500);
//        da.setType("income");
//        Log.d(TAG, "addData: +100500");
//        Call<Data>call = api.addItems(da);
//        Log.d(TAG, "addData: " + call.request());
//    }
}
