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

import com.example.ukk_lintang2021.Adapter.SiswaAdapter;
import com.example.ukk_lintang2021.Model.Siswa.ResponseSiswa;
import com.example.ukk_lintang2021.Model.Siswa.SiswaResultItem;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.Network.ApiInterface;
import com.example.ukk_lintang2021.Adapter.PetugasAdapter;
import com.example.ukk_lintang2021.Model.Petugas.PetugasResultItem;
import com.example.ukk_lintang2021.Model.Petugas.ResponsePetugas;
import com.example.ukk_lintang2021.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListsiswaActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener,MenuItem.OnActionExpandListener {

    private RecyclerView rv;
    private SiswaAdapter sAdapter;
    private LinearLayoutManager layoutManager;
    public ArrayList<SiswaResultItem> allPagesSiswaResultItem = new ArrayList();
    private List<SiswaResultItem> currentPageSiswaResultItem;
    private Boolean isScrolling = false;
    private int currentSiswaResultItem, totalSiswaResultItem, scrolledOutSiswaResultItem;
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
        sAdapter = new SiswaAdapter(this, allPagesSiswaResultItem);
        rv.setAdapter(sAdapter);
        rv.setLayoutManager(layoutManager);
    }

    private void retrieveAndFillRecyclerView(final String action, String queryString,
                                             final String start, String limit) {

        sAdapter.searchString = queryString;
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseSiswa> retrievedData;

        if (action.equalsIgnoreCase("GET_PAGINATED")) {
            retrievedData = api.searchSiswa("GET_PAGINATED", queryString, start, limit);
            ApiClient.showProgressBar(mProgressBar);
        } else if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
            ApiClient.showProgressBar(mProgressBar);
            retrievedData = api.searchSiswa("GET_PAGINATED_SEARCH", queryString, start, limit);
        } else {
            ApiClient.showProgressBar(mProgressBar);
            retrievedData = api.retrieveSiswa();
        }
        retrievedData.enqueue(new Callback<ResponseSiswa>() {
            @Override
            public void onResponse(Call<ResponseSiswa> call, Response<ResponseSiswa>
                    response) {
                Log.d("RETROFIT", "CODE : " + response.body().getCode());
                Log.d("RETROFIT", "MESSAGE : " + response.body().getMessage());
                Log.d("RETROFIT", "RESPONSE : " + response.body().getResult());
                currentPageSiswaResultItem = response.body().getResult();

                if (currentPageSiswaResultItem != null && currentPageSiswaResultItem.size() > 0) {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagesSiswaResultItem.clear();
                    }
                    for (int i = 0; i < currentPageSiswaResultItem.size(); i++) {
                        allPagesSiswaResultItem.add(currentPageSiswaResultItem.get(i));
                    }

                } else {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagesSiswaResultItem.clear();
                    }
                }
                sAdapter.notifyDataSetChanged();
                ApiClient.hideProgressBar(mProgressBar);
            }

            @Override
            public void onFailure(Call<ResponseSiswa> call, Throwable t) {
                ApiClient.hideProgressBar(mProgressBar);
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
                ApiClient.showInfoDialog(ListsiswaActivity.this, "ERROR", t.getMessage());
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
                currentSiswaResultItem = layoutManager.getChildCount();
                totalSiswaResultItem = layoutManager.getItemCount();
                scrolledOutSiswaResultItem = ((LinearLayoutManager) rv.getLayoutManager()).
                        findFirstVisibleItemPosition();

                if (isScrolling && (currentSiswaResultItem + scrolledOutSiswaResultItem ==
                        totalSiswaResultItem)) {
                    isScrolling = false;

                    if (dy > 0) {
                        // Scrolling up
                        retrieveAndFillRecyclerView("GET_PAGINATED",
                                sAdapter.searchString,
                                String.valueOf(totalSiswaResultItem), "5");

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
        inflater.inflate(R.menu.siswa_page_menu, menu);
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
                ApiClient.openActivity(this, SiswaActivity.class);
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
        setContentView(R.layout.activity_listsiswa);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Data Siswa");

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