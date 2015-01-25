package com.arctouch.floripabus.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.arctouch.floripabus.R;
import com.arctouch.floripabus.activities.fragments.ExitDialog;
import com.arctouch.floripabus.common.DialogUtil;
import com.arctouch.floripabus.model.Route;
import com.arctouch.floripabus.model.Street;
import com.arctouch.floripabus.model.StreetsResult;
import com.arctouch.floripabus.tasks.SearchRouteTask;
import com.arctouch.floripabus.tasks.SearchStreetsByRouteTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements Receiver<List<Route>> {

    private final int GET_STREET_NAME = 1;
    private EditText streetNameTxt;
    private ListView routesListView;
    private Integer selectedRouteId;
    private Map<String, String> selectedRouteMap;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViewComponents();
        final ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit_the_app:
                DialogFragment dialog = new ExitDialog();
                dialog.show(getFragmentManager(), "dialog");
                return true;
            case R.id.search_by_location:

                Intent mapIntent = new Intent(this, GoogleMapActivity.class);
                startActivityForResult(mapIntent, GET_STREET_NAME);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_STREET_NAME) {
            if (resultCode == RESULT_OK) {
                String streetName = data.getStringExtra("streetName");
                streetNameTxt.setText(streetName);
            }
        }
    }

    private void initializeViewComponents() {
        this.streetNameTxt = (EditText) findViewById(R.id.txtRouteName);
        this.streetNameTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchRoute(textView);
                    handled = true;
                }
                return handled;

            }
        });
        this.routesListView = (ListView) findViewById(R.id.routesListView);
        TextView emptyView = new TextView(getApplicationContext());
        emptyView.setText("No results");
        this.routesListView.setEmptyView(emptyView);
        final Receiver<List<Street>> receiver = new StreetsReceiver(this);
        this.routesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> item = (Map<String, String>) getRoutesListView().getItemAtPosition(position);
                setSelectedRouteId(Integer.valueOf(item.get("id")));
                setSelectedRouteMap(item);
                showSearchingDialog();
                new SearchStreetsByRouteTask(receiver).execute(getSelectedRouteId());
            }
        });
    }

    public void searchRoute(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.streetNameTxt.getWindowToken(), 0);
        showSearchingDialog();
        new SearchRouteTask(this).execute(getSearch());
    }

    private void showSearchingDialog() {
        DialogUtil.showProgressDialog("Wait a moment...", "Searching", this);
    }

    public Map<String, String> getSelectedRouteMap() {
        return selectedRouteMap;
    }

    public void setSelectedRouteMap(Map<String, String> selectedRouteMap) {
        this.selectedRouteMap = selectedRouteMap;
    }

    public String getSearch() {
        return String.valueOf(this.streetNameTxt.getText());
    }

    @Override
    public void onReceive(List<Route> result) {
        if (result == null || result.isEmpty()) {
            this.hideListView();
        } else {
            this.showRoutes(result);
        }
        DialogUtil.closeProgressDialog();
    }

    private void showRoutes(List<Route> routes) {
        if (routes == null || routes.isEmpty()) {
            hideListView();
        }

        List<Map<String, String>> dataList = parseToMap(routes);
        String[] from = new String[]{"longName", "shortName"};
        int[] to = new int[]{R.id.longNameTextView, R.id.shortNameTextView};

        routesListView.setAdapter(new SimpleAdapter(this, dataList, R.layout.route_row_layout, from, to));
        routesListView.setVisibility(View.VISIBLE);
    }

    private List<Map<String, String>> parseToMap(List<Route> routes) {
        List<Map<String, String>> dataList = new ArrayList<>();

        for (Route route : routes) {
            Map<String, String> routeMap = new HashMap<>();
            routeMap.put("id", String.valueOf(route.getId()));
            routeMap.put("longName", route.getLongName());
            routeMap.put("shortName", route.getShortName());
            dataList.add(routeMap);
        }

        return dataList;
    }

    private void hideListView() {
        this.routesListView.setAdapter(null);
        this.routesListView.setVisibility(View.GONE);
    }

    public Integer getSelectedRouteId() {
        return selectedRouteId;
    }

    public void setSelectedRouteId(Integer selectedRouteId) {
        this.selectedRouteId = selectedRouteId;
    }

    public ListView getRoutesListView() {
        return routesListView;
    }

    @Override
    protected void onPause() {
        DialogUtil.closeProgressDialog();
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        DialogUtil.closeProgressDialog();
        super.onResume();
    }

    public class StreetsReceiver implements Receiver<List<Street>> {

        private final MainActivity activity;

        public StreetsReceiver(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onReceive(List<Street> result) {
            StreetsResult stopResult = new StreetsResult();
            stopResult.setStreets(result);
            stopResult.setSelectedRoute(this.activity.getSelectedRouteMap());

            Intent intent = new Intent(this.activity, DetailsActivity.class);
            intent.putExtra("stopResult", stopResult);
            activity.startActivity(intent);
        }
    }
}