package com.gamebacklog.twinghosts.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameItemClickListener {

    public final static int ADD_GAME_REQUEST_CODE = 10315;
    public final static int TASK_GET_ALL_GAMES = 0;
    public final static int TASK_DELETE_GAMES = 1;
    public final static int TASK_UPDATE_GAMES = 2;
    public final static int TASK_INSERT_GAMES = 3;

    public final static String OPTION_WANT_TO_PLAY = "Want to play";
    public final static String OPTION_PLAYING = "Playing";
    public final static String OPTION_STALLED = "Stalled";
    public final static String OPTION_DROPPED = "Dropped";

    private List<GameInfo> gameInfos;
    private GameInfoAdapter gameInfoAdapter;
    private RecyclerView gameInfoRecyclerView;
    public static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);

        new GameInfoAsyncTask(TASK_GET_ALL_GAMES).execute();
        gameInfos = db.gameDao().getAllGames();

        if(gameInfos.size() > 0)
            Toast.makeText(this, gameInfos.get(0).getTitle(), Toast.LENGTH_SHORT).show();

        gameInfoRecyclerView = findViewById(R.id.recyclerView);
        gameInfoRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        refreshAdapter();

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int position = viewHolder.getAdapterPosition();
                        new GameInfoAsyncTask(TASK_DELETE_GAMES).execute(gameInfos.get(position));
                        gameInfoAdapter.removeItem(position);
                        Toast.makeText( MainActivity.this, R.string.game_deleted, Toast.LENGTH_SHORT).show();
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(gameInfoRecyclerView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
                startActivityForResult(intent, ADD_GAME_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_GAME_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Toast.makeText(this, "ASFEGJWQNGJKWEGNK", Toast.LENGTH_SHORT).show();
                Bundle bundle = data.getExtras();
                if (bundle != null && bundle.getString(getString(R.string.d_mode)) != null) {
                    if (bundle.getString(getString(R.string.d_mode)).equals(getString(R.string.d_create))) {
                        new GameInfoAsyncTask(TASK_INSERT_GAMES).execute(
                                (GameInfo) bundle.getSerializable(getString(R.string.d_game))
                        );
                    } else if (bundle.getString(getString(R.string.d_mode)).equals(getString(R.string.d_update))) {
                        new GameInfoAsyncTask(TASK_UPDATE_GAMES).execute(
                                (GameInfo) bundle.getSerializable(getString(R.string.d_game))
                        );
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onGameDbUpdated(List list) {
        gameInfos = list;
        refreshAdapter();
    }

    @Override
    public void onItemClick(int position){
        Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
        intent.putExtra(getString(R.string.d_mode), getString(R.string.d_update));
        intent.putExtra(getString(R.string.d_game), gameInfos.get(position));
        startActivityForResult(intent, ADD_GAME_REQUEST_CODE);
    }

    public void refreshAdapter(){
        if (gameInfoAdapter == null) {
            gameInfoAdapter = new GameInfoAdapter(this, gameInfos, this);
            gameInfoRecyclerView.setAdapter(gameInfoAdapter);
        } else {
            gameInfoAdapter.swapList(gameInfos);
        }
    }

    public class GameInfoAsyncTask extends AsyncTask<GameInfo, Void, List> {

        private int taskCode;

        public GameInfoAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        @Override
        protected List doInBackground(GameInfo... gameInfos) {
            switch (taskCode) {
                case MainActivity.TASK_DELETE_GAMES:
                    MainActivity.db.gameDao().deleteGames(gameInfos[0]);
                    break;

                case MainActivity.TASK_UPDATE_GAMES:
                    MainActivity.db.gameDao().updateGames(gameInfos[0]);
                    break;

                case MainActivity.TASK_INSERT_GAMES:
                    MainActivity.db.gameDao().insertGames(gameInfos[0]);
                    break;
            }

            //To return a new list with the updated data, we get all the data from the database again.
            return MainActivity.db.gameDao().getAllGames();
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            onGameDbUpdated(list);
        }
    }
}