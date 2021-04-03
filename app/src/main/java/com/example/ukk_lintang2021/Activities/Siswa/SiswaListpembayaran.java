package com.example.ukk_lintang2021.Activities.Siswa;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ukk_lintang2021.Adapter.PembayaranAdapter;
import com.example.ukk_lintang2021.Model.Pembayaran.PembayaranResultItem;
import com.example.ukk_lintang2021.Model.Pembayaran.ResponsePembayaran;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.Network.ApiInterface;
import com.example.ukk_lintang2021.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiswaListpembayaran  extends AppCompatActivity
        implements SearchView.OnQueryTextListener{

    //We define our instance fields
    private RecyclerView rv;
    private PembayaranAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    public ArrayList<PembayaranResultItem> allPagesPembayaranResultItem = new ArrayList();
    private List<PembayaranResultItem> currentPagePembayaranResultItem;
    private Boolean isScrolling = false;
    private int currentPembayaran, totalPembayaran, scrolledOutPembayaran;
    private ProgressBar mProgressBar;

    /**
     * We initialize our widgets
     */
    private void initializeViews() {
        mProgressBar = findViewById(R.id.mProgressBarLoad);
        mProgressBar.setIndeterminate(true);
        ApiClient.showProgressBar(mProgressBar);
        rv = findViewById(R.id.mRecyclerView);
    }

    /**
     * This method will setup oir RecyclerView
     */
    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new PembayaranAdapter(this, allPagesPembayaranResultItem);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(layoutManager);
    }
    /**
     * This method will download for us data from php mysql based on supplied query string
     * as well as pagination parameters. We are basiclally searching or selecting data
     * without seaching. However all the arriving data is paginated at the server level.
     */
    private void retrieveAndFillRecyclerView(final String action, String queryString,
                                             final String start, String limit) {

        mAdapter.searchString = queryString;
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePembayaran> retrievedData;

        if (action.equalsIgnoreCase("GET_PAGINATED")) {
            retrievedData = api.searchpembayaran("GET_PAGINATED", queryString, start, limit);
            ApiClient.showProgressBar(mProgressBar);
        } else if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
            ApiClient.showProgressBar(mProgressBar);
            retrievedData = api.searchpembayaran("GET_PAGINATED_SEARCH", queryString, start, limit);
        } else {
            ApiClient.showProgressBar(mProgressBar);
            retrievedData = api.retrievepembayaran();
        }
        retrievedData.enqueue(new Callback<ResponsePembayaran>() {
            @Override
            public void onResponse(Call<ResponsePembayaran> call, Response<ResponsePembayaran>
                    response) {
                Log.d("RETROFIT", "CODE : " + response.body().getCode());
                Log.d("RETROFIT", "MESSAGE : " + response.body().getMessage());
                Log.d("RETROFIT", "RESPONSE : " + response.body().getResult());
                currentPagePembayaranResultItem = response.body().getResult();

                if (currentPagePembayaranResultItem != null && currentPagePembayaranResultItem.size() > 0) {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagesPembayaranResultItem.clear();
                    }
                    for (int i = 0; i < currentPagePembayaranResultItem.size(); i++) {
                        allPagesPembayaranResultItem.add(currentPagePembayaranResultItem.get(i));
                    }

                } else {
                    if (action.equalsIgnoreCase("GET_PAGINATED_SEARCH")) {
                        allPagesPembayaranResultItem.clear();
                    }
                }
                mAdapter.notifyDataSetChanged();
                ApiClient.hideProgressBar(mProgressBar);
            }

            @Override
            public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                ApiClient.hideProgressBar(mProgressBar);
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
                ApiClient.showInfoDialog(SiswaListpembayaran.this, "ERROR", t.getMessage());
            }
        });
    }
    /**
     * We will listen to scroll events. This is important as we are implementing scroll to
     * load more data pagination technique
     */
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
                currentPembayaran = layoutManager.getChildCount();
                totalPembayaran = layoutManager.getItemCount();
                scrolledOutPembayaran = ((LinearLayoutManager) rv.getLayoutManager()).
                        findFirstVisibleItemPosition();

                if (isScrolling && (currentPembayaran + scrolledOutPembayaran ==
                        totalPembayaran)) {
                    isScrolling = false;

                    if (dy > 0) {
                        // Scrolling up
                        retrieveAndFillRecyclerView("GET_PAGINATED",
                                mAdapter.searchString,
                                String.valueOf(totalPembayaran), "5");

                    } else {
                        // Scrolling down
                    }
                }
            }
        });
    }
    /**
     * We inflate our menu. We show SearchView inside the toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pembayaran_page_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(true);
        searchView.setQueryHint("Search");
        return true;
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
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siswa_listpembayaran);

        initializeViews();
        this.listenToRecyclerViewScroll();
        setupRecyclerView();
        retrieveAndFillRecyclerView("GET_PAGINATED", "", "0", "5");
    }
}

//end