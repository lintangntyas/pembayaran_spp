package com.example.ukk_lintang2021.Activities.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.Model.Petugas.PetugasResultItem;
import com.example.ukk_lintang2021.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class DetailpetugasActivity extends AppCompatActivity implements View.OnClickListener {

    //Let's define our instance fields
    private TextView usernameTV, passwordTV, nama_petugasTV, levelTV;
    private FloatingActionButton editFAB;
    private PetugasResultItem receivedPetugasResultItem;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    /**
     * Let's initialize our widgets
     */
    private void initializeWidgets(){
        usernameTV= findViewById(R.id.usernameTV);
        passwordTV= findViewById(R.id.passwordTV);
        nama_petugasTV= findViewById(R.id.nama_petugasTV);
        levelTV= findViewById(R.id.levelTV);
        editFAB=findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
    }

    /**
     * We will now receive and show our data to their appropriate views.
     */
    private void receiveAndShowData(){
        receivedPetugasResultItem= ApiClient.receivePetugas(getIntent(),DetailpetugasActivity.this);

        if(receivedPetugasResultItem != null){
            usernameTV.setText(receivedPetugasResultItem.getUsername());
            passwordTV.setText(receivedPetugasResultItem.getPassword());
            nama_petugasTV.setText(receivedPetugasResultItem.getNamaPetugas());
            levelTV.setText(receivedPetugasResultItem.getLevel());

            mCollapsingToolbarLayout.setTitle(receivedPetugasResultItem.getUsername());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                    getColor(R.color.white));
        }
    }
    /**
     * Let's inflate our menu for the detail page
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailpetugas_page_menu, menu);
        return true;
    }

    /**
     * When a menu item is selected we want to navigate to the appropriate page
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                ApiClient.sendPetugasToActivity(this,receivedPetugasResultItem, PetugasActivity.class);
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * When FAB button is clicked we want to go to the editing page
     */
    @Override
    public void onClick(View v) {
        int id =v.getId();
        if(id == R.id.editFAB){
            ApiClient.sendPetugasToActivity(this,receivedPetugasResultItem, PetugasActivity.class);
            finish();
        }
    }
    /**
     * Let's once again override the attachBaseContext. We do this for our
     * Calligraphy library
     */
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
//    }

    /**
     * Let's finish the current activity when back button is pressed
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    /**
     * Our onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpetugas);

        initializeWidgets();
        receiveAndShowData();
    }

}
//end