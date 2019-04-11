package com.example.aftbhadk.test.Views.Albums;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.aftbhadk.test.Adapters.AlbumAdapter;
import com.example.aftbhadk.test.Common.NetworkUtils;
import com.example.aftbhadk.test.Constants;
import com.example.aftbhadk.test.Models.Albums;
import com.example.aftbhadk.test.MyApplication;
import com.example.aftbhadk.test.Views.Photos.PhotoActivity;
import com.example.aftbhadk.test.R;
import com.example.aftbhadk.test.ViewModels.AlbumViewModel;
import com.example.aftbhadk.test.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements AlbumInterface {

    ActivityMainBinding activityMainBinding;
    int mstartPage = 0;
    int mLimit = 20;
    int mTotal = 100;
    AlbumViewModel albumViewModel;
    private LinearLayoutManager mLayoutManager;
    private boolean mBottomReached = false;
    private boolean isRecyclerViewLoading;
    AlbumAdapter albumAdapter;
    List<Albums> albumsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        albumViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        albumAdapter = new AlbumAdapter(albumsList, MainActivity.this, this);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        activityMainBinding.albumList.addOnScrollListener(recyclerViewOnScrollListener);
        activityMainBinding.albumList.setLayoutManager(mLayoutManager);
        activityMainBinding.albumList.setAdapter(albumAdapter);

        if (NetworkUtils.isNetworkConnected(this)) {
            getAlbumList();
        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
        }

        albumViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    isRecyclerViewLoading = true;
                    activityMainBinding.progressBar.setVisibility(View.VISIBLE);
                } else {
                    isRecyclerViewLoading = false;
                    activityMainBinding.progressBar.setVisibility(View.GONE);
                }
            }
        });


    }

    private void getAlbumList() {
        albumViewModel.getAlbumdata(mstartPage, mLimit).observe(this, new Observer<List<Albums>>() {
            @Override
            public void onChanged(@Nullable List<Albums> albums) {
                for (int i = 0; i < albums.size(); i++) {
                    albumsList.add(albums.get(i));
                    albumAdapter.notifyDataSetChanged();
                }
            }
        });
        mstartPage = mstartPage + mLimit;
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            View view = (View) recyclerView.getChildAt(recyclerView.getChildCount() - 1);
            int diff = (view.getBottom() - (recyclerView.getHeight() + recyclerView.getScrollY()));
            mBottomReached = diff <= 0;

            if (dy > 0) {
                int mVisibleItemCount = mLayoutManager.getChildCount();
                int mTotalItemCount = mLayoutManager.getItemCount();
                int mFirstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                if (!isRecyclerViewLoading) {
                    if (((mVisibleItemCount + mFirstVisibleItemPosition) >= mTotalItemCount) && mBottomReached && (mTotalItemCount < mTotal)) {
                        albumViewModel.loadAlbum(mstartPage, mLimit);
                        mstartPage = mstartPage + mLimit;
                    }
                }
            }

            super.onScrolled(recyclerView, dx, dy);
        }
    };


    @Override
    public void onClickAlbum(int position) {
        Albums albums = albumsList.get(position);
        Intent photoIntent = new Intent(MainActivity.this, PhotoActivity.class);
        photoIntent.putExtra(Constants.ID, albums.getUserId());
        startActivity(photoIntent);
    }
}
