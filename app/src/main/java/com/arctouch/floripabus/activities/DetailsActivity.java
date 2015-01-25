package com.arctouch.floripabus.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.arctouch.floripabus.R;
import com.arctouch.floripabus.adapters.DetailsAdapter;
import com.arctouch.floripabus.common.DialogUtil;
import com.arctouch.floripabus.model.StreetsResult;

import java.util.Map;

public class DetailsActivity extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private Integer selectedRouteId;
    private DetailsAdapter detailsAdapter;
    private StreetsResult stopResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);
        DialogUtil.closeProgressDialog();
        initializeViewComponents();

        Intent activityMain = getIntent();

        this.stopResult = (StreetsResult) activityMain.getExtras().getSerializable("stopResult");
        Map<String, String> selectedRoute = this.stopResult.getSelectedRoute();

        this.selectedRouteId = Integer.valueOf(selectedRoute.get("id"));
        this.setTitle(selectedRoute.get("longName"));

        final ActionBar actionBar = getActionBar();
        if(actionBar != null) {

            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.setDisplayHomeAsUpEnabled(true);

            for (int i = 0; i < this.detailsAdapter.getCount(); i++) {
                actionBar.addTab(actionBar.newTab().setText(this.detailsAdapter.getPageTitle(i)).setTabListener(this));
            }
        }

    }

    private void initializeViewComponents() {
        this.viewPager = (ViewPager) findViewById(R.id.pager);
        this.detailsAdapter = new DetailsAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(this.detailsAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
            }
        });
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public Integer getSelectedRouteId() {
        return selectedRouteId;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public StreetsResult getStreetsResult() {
        return stopResult;
    }
}
