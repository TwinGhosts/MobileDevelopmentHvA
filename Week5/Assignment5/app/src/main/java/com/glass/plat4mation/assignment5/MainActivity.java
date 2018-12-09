package com.glass.plat4mation.assignment5;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FactAdapter factAdapter;
    private ArrayList<Fact> factList = new ArrayList<>();
    private ListView factView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        factView = findViewById(R.id.fact_list);
        refreshAdapter();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestFact(new Random().nextInt(1000));
            }
        });
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

    private void requestFact(int number)
    {
        Service.NumbersApiService service = Service.NumbersApiService.retrofit.create(Service.NumbersApiService.class);

        /**
         * Make an a-synchronous call by enqueuing and definition of callbacks.
         */
        Call<Fact> call = service.getFact(number);
        call.enqueue(new Callback<Fact>() {

            @Override
            public void onResponse(Call<Fact> call, Response<Fact> response) {
                Fact fact = response.body();
                factList.add(fact);
                refreshAdapter();
            }

            @Override
            public void onFailure(Call<Fact> call, Throwable t) {
                Log.d("error",t.toString());
            }
        });
    }

    public void refreshAdapter(){
        if (factAdapter == null) {
            factAdapter = new FactAdapter(this, factList);
            factView.setAdapter(factAdapter);
        } else {
            factAdapter.notifyDataSetChanged();
        }
    }
}
