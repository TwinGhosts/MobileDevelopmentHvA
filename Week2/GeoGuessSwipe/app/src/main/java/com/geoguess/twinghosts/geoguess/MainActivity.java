package com.geoguess.twinghosts.geoguess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private RecyclerView geoRecyclerView;
    private GeoObjectAdapter adapter;

    private ItemTouchHelper itemTouchHelper;
    private List<GeoObject> geoObjects;

    private TextView scoreTextView;
    int score;
    int scoreMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Usual initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geoObjects = new ArrayList<>();
        scoreTextView = findViewById(R.id.score);
        createGeoObjectList();
        buildRecyclerView();

        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Get the index of the current item
                final int position = viewHolder.getAdapterPosition();

                // Check for an answer with each swipe to the left or right
                checkAnswer((direction == ItemTouchHelper.LEFT) ? true : false, position);
                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    removeItem(position);
                }
            }
        };

        // Init the scoreText
        scoreTextView.setText("Score " + score + "/" + scoreMax);

        // Add an itemTouchHelper to the recyclerView for swipe registration
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(geoRecyclerView);
    }

    /**
     * Checks the answer based on a right or left swipe and checks whether that is correct or not
     * @param swipeLeft
     * @param position
     */
    private void checkAnswer(boolean swipeLeft, int position) {
        // Get the right answer for the object at the given position
        boolean hasAnsweredCorrectly = (swipeLeft == !geoObjects.get(position).isAnswer());

        // When the user has swiped correctly, add score and show a message
        Toast.makeText(getApplicationContext(),(hasAnsweredCorrectly) ? R.string.correct_guess : R.string.wrong_guess, Toast.LENGTH_SHORT).show();
        score += (hasAnsweredCorrectly) ? 1 : 0;
        scoreTextView.setText("Score: " + score + "/" + scoreMax);

        // Finish the game when all of the geoObjects are removed from the list
        if(geoObjects.isEmpty())
            finishGame();
    }

    /**
     * Ends the game for the user and shows the score and a button to restart
     */
    private void finishGame() {
        // TODO finish game with a separate screen or an alert message
        Toast.makeText(getApplicationContext(), R.string.end_game_text + " " + score + " out of " + scoreMax, Toast.LENGTH_SHORT).show();
    }

    /**
     * Create the list of geoObjects with the answer property and set the max score based on the
     * amount of images.
     */
    private void createGeoObjectList() {
        geoObjects.add(new GeoObject(R.drawable.img1_yes_denmark, true));
        geoObjects.add(new GeoObject(R.drawable.img2_no_canada, false));
        geoObjects.add(new GeoObject(R.drawable.img3_no_bangladesh, false));
        geoObjects.add(new GeoObject(R.drawable.img4_yes_kazachstan, true));
        geoObjects.add(new GeoObject(R.drawable.img5_no_colombia, false));
        geoObjects.add(new GeoObject(R.drawable.img6_yes_poland, true));
        geoObjects.add(new GeoObject(R.drawable.img7_yes_malta, true));
        geoObjects.add(new GeoObject(R.drawable.img8_no_thailand, false));

        scoreMax = geoObjects.size();
    }

    /**
     * Create the main recyclerView with all of the images
     */
    private void buildRecyclerView() {
        geoRecyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        geoRecyclerView.setLayoutManager(layoutManager);
        adapter = new GeoObjectAdapter(this, geoObjects);
        geoRecyclerView.setAdapter(adapter);
    }

    /**
     * Removes an item from the geoObjects list and updates the adapter
     * @param position
     */
    private void removeItem(int position) {
        geoObjects.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
