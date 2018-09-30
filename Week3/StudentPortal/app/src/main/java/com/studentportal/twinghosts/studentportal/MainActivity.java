package com.studentportal.twinghosts.studentportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int ADD_PORTAL_REQUEST = 6969;

    private ListView portalEntriesView;
    private ArrayAdapter<String> portalEntryAdapter;
    private ArrayList<String> portalUrlList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(R.string.main_activity_title);

        // Setting up the adapter for the list
        portalEntriesView = findViewById(R.id.portal_list_view);
        portalEntryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        // Floating Action Button actions
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {

            // When clicking on the floating + button, go to the other activity to add info
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPortalActivity.class);
                startActivityForResult(intent, ADD_PORTAL_REQUEST);
            }
        });

        // On clicking on an item in the listView go to the WebView
        portalEntriesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(MainActivity.this, ShowPortalActivity.class);
                intent.putExtra("web_url", portalUrlList.get(position));
                startActivity(intent);
            }
        });

        portalEntriesView.setAdapter(portalEntryAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ADD_PORTAL_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data != null) {
                // get the bundle data from the intent
                Bundle bundle = data.getExtras();

                // add the data from the intent to the lists
                portalEntryAdapter.add(bundle.getString("title"));
                portalUrlList.add(bundle.getString("url"));

                // update the listView
                portalEntryAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * Getters and setters
     */
    public ListView getPortalEntryInfoListView(){
        return portalEntriesView;
    }
}
