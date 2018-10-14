package com.gamebacklog.twinghosts.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddGameActivity extends AppCompatActivity {

    private boolean isUpdateForm = false;
    private GameInfo gameInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView titleText = findViewById(R.id.edit_title);
        final TextView platformText = findViewById(R.id.edit_platform);
        final TextView notesText = findViewById(R.id.edit_notes);
        final Spinner statusOptions = findViewById(R.id.spinner_status);

        // Create the spinner dropdown
        String[] items = new String[]{
                MainActivity.OPTION_WANT_TO_PLAY,
                MainActivity.OPTION_PLAYING,
                MainActivity.OPTION_STALLED,
                MainActivity.OPTION_DROPPED
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        statusOptions.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.getString(getString(R.string.d_mode)) != null) {
            // When wanting to update a Game Item
            if (bundle.getString(getString(R.string.d_mode)).equals(getString(R.string.d_update))) {
                isUpdateForm = true;
                gameInfo = (GameInfo) bundle.getSerializable(getString(R.string.d_game));
                int spinnerPosition = adapter.getPosition(gameInfo.getStatus());

                titleText.setText(gameInfo.getTitle());
                platformText.setText(gameInfo.getPlatform());
                notesText.setText(gameInfo.getNotes());
                statusOptions.setSelection(spinnerPosition);
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGame(titleText.getText().toString(), platformText.getText().toString(), notesText.getText().toString(), statusOptions.getSelectedItem().toString());
            }
        });
    }

    private void addGame(String title, String platform, String notes, String option){

        Intent intent = new Intent();

        // None should be empty
        if(title.isEmpty() || platform.isEmpty() || notes.isEmpty() || option.isEmpty()){
            Toast.makeText(getBaseContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
        } else if(isUpdateForm){ // Check whether this is an update or an add
            // Set all of the info
            gameInfo.setTitle(title);
            gameInfo.setPlatform(platform);
            gameInfo.setNotes(notes);
            gameInfo.setStatus(option);

            // Prepare the intent with data
            intent.putExtra(getString(R.string.d_mode), getString(R.string.d_update));
            intent.putExtra(getString(R.string.d_game), gameInfo);

            Toast.makeText(getBaseContext(), "Updated " + title, Toast.LENGTH_SHORT).show();

            // Mark the result as OK and pass the intent as data
            setResult(RESULT_OK, intent);
            // Return to the main Activity
            finish();
        } else {
            gameInfo = new GameInfo(title, platform, notes, option);
            intent.putExtra(getString(R.string.d_mode), getString(R.string.d_create));
            intent.putExtra(getString(R.string.d_game), gameInfo);

            Toast.makeText(getBaseContext(), "Added " + title, Toast.LENGTH_SHORT).show();

            // Mark the result as OK and pass the intent as data
            setResult(RESULT_OK, intent);
            // Return to the main Activity
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
