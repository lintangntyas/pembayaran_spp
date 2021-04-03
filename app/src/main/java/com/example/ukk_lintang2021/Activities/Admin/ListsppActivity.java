package com.example.ukk_lintang2021.Activities.Admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.Network.ApiInterface;
import com.example.ukk_lintang2021.Adapter.SppAdapter;
import com.example.ukk_lintang2021.Model.Spp.ResponseSpp;
import com.example.ukk_lintang2021.Model.Spp.SppResultItem;
import com.example.ukk_lintang2021.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListsppActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    //We define our instance fields
    private RecyclerView rv;
    private SppAdapter sAdapter;
    private LinearLayoutManager layoutManager;
    public ArrayList<SppResultItem> allPagesSppResultItem = new ArrayList();
    private List<SppResultItem> currentPageSppResultItem;
    private Boolean isScrolling = false;
    private int currentSppResultItem, totalSppResultItem, scrolledOutSppResultItem;
    private ProgressBar mProgressBar;
    ActionBar actionBar;

    private void initializeViews() {
        mProgressBar = findViewById(R.id.mProgressBarLoad);
        mProgressBar.setIndeterminate(true);
        ApiClient.showProgressBar(mProgressBar);
        rv = findViewById(R.id.mRecyclerView);
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        sAdapter = new SppAdapter(this, allPagesSppResultItem);
        rv.setAdapter(sAdapter);
        rv.setLayoutManager(layoutManager);
    }

    private void retrieveAndFillRecyclerView(final String action, String queryString,
                                             final String start, String limit) {

        sAdapter.searchString = queryString;
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseSpp> retrievedData;

        if (action.equalsIgnoreCase("GET_PAGINATED")) {
            retrievedData = api.searchSpp("GET_PAGINATED", queryString, start, limit);
            ApiClient.showProgressBar(mProgressBar);
        } else if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
            ApiClient.showProgressBar(mProgressBar);
            retrievedData = api.searchSpp("GET_PAGINATED_SEARCH", queryString, start, limit);
        } else {
            ApiClient.showProgressBar(mProgressBar);
            retrievedData = api.retrievespp();
        }
        retrievedData.enqueue(new Callback<ResponseSpp>() {
            @Override
            public void onResponse(Call<ResponseSpp> call, Response<ResponseSpp>
                    response) {
                Log.d("RETROFIT", "CODE : " + response.body().getCode());
                Log.d("RETROFIT", "MESSAGE : " + response.body().getMessage());
                Log.d("RETROFIT", "RESPONSE : " + response.body().getResult());
                currentPageSppResultItem = response.body().getResult();

                if (currentPageSppResultItem != null && currentPageSppResultItem.size() > 0) {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        currentPageSppResultItem.clear();
                    }
                    for (int i = 0; i < currentPageSppResultItem.size(); i++) {
                        allPagesSppResultItem.add(currentPageSppResultItem.get(i));
                    }

                } else {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagesSppResultItem.clear();
                    }
                }
                sAdapter.notifyDataSetChanged();
                ApiClient.hideProgressBar(mProgressBar);
            }

            @Override
            public void onFailure(Call<ResponseSpp> call, Throwable t) {
                ApiClient.hideProgressBar(mProgressBar);
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
                ApiClient.showInfoDialog(ListsppActivity.this, "ERROR", t.getMessage());
            }
        });
    }

    private void listenToRecyclerViewScroll() {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView rv, int newState) {
                //when scrolling starts
                super.onScrollStateChanged(rv, newState);
                //check for scroll state
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                // When the scrolling has stopped
                super.onScrolled(rv, dx, dy);
                currentSppResultItem = layoutManager.getChildCount();
                totalSppResultItem = layoutManager.getItemCount();
                scrolledOutSppResultItem = ((LinearLayoutManager) rv.getLayoutManager()).
                        findFirstVisibleItemPosition();

                if (isScrolling && (currentSppResultItem + scrolledOutSppResultItem ==
                        totalSppResultItem)) {
                    isScrolling = false;

                    if (dy > 0) {
                        // Scrolling up
                        retrieveAndFillRecyclerView("GET_PAGINATED",
                                sAdapter.searchString,
                                String.valueOf(totalSppResultItem), "5");

                    } else {
                        // Scrolling down
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.spp_page_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(true);
        searchView.setQueryHint("Search");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                ApiClient.openActivity(this, SppActivity.class);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        retrieveAndFillRecyclerView("GET_PAGINATED_SEARCH", query, "0", "5");
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listspp);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Data Spp");

        initializeViews();
        this.listenToRecyclerViewScroll();
        setupRecyclerView();
        retrieveAndFillRecyclerView("GET_PAGINATED", "", "0", "5");
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

}