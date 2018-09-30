package com.studentportal.twinghosts.studentportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPortalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Basic init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_portal_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Custom actions
        final EditText inputUrl = findViewById(R.id.url_input);
        final EditText inputTitle = findViewById(R.id.title_input);

        Button addButton = findViewById(R.id.button_add_portal);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Both fields shouldn't be empty
                if(!inputTitle.getText().toString().isEmpty() && !inputUrl.getText().toString().isEmpty() && URLUtil.isValidUrl(inputUrl.getText().toString())) {
                    // Add the new portal to the list
                    addNewPortal(inputTitle.getText().toString(), inputUrl.getText().toString());
                } else if(!URLUtil.isValidUrl(inputUrl.getText().toString())) {
                    Toast.makeText(getBaseContext(), "The url has to be valid!", Toast.LENGTH_SHORT).show();
                } else { // Else show error
                    Toast.makeText(getBaseContext(), R.string.input_add_empty_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_portal, menu); //.xml file name
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Adds a new entry to the portal list and saves it to the user prefs using GSON
     * @param portalTitle
     * @param portalUrl
     */
    private void addNewPortal(String portalTitle, String portalUrl){
        Toast.makeText(getBaseContext(), "Added new portal: " + portalTitle, Toast.LENGTH_SHORT).show();

        // Pack the info to send to the main activity
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("title", portalTitle);
        bundle.putString("url", portalUrl);
        intent.putExtras(bundle);

        // Mark the result as OK and pass the intent as data
        setResult(RESULT_OK, intent);

        // Return to the main Activity
        finish();
    }
}
