package com.reminderdemo.twinghosts.reminderdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView shoppingListView;
    private ArrayAdapter<String> shoppingListArrayAdapter;
    private TextInputEditText shoppingListInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shoppingListView = findViewById(R.id.list_view);
        shoppingListArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        shoppingListInput = findViewById(R.id.input_text);

        shoppingListInput.setInputType(InputType.TYPE_NULL);

        shoppingListInput.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                shoppingListInput.setInputType(InputType.TYPE_CLASS_TEXT);
                shoppingListInput.onTouchEvent(event); // call native handler
                return true; // consume touch even
            }
        });

        shoppingListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteItem(position, view);
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Item added to the shopping list", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                addItem();
            }
        });

        // Add items to the ArrayList
        shoppingListArrayAdapter.add("Pizza");
        shoppingListArrayAdapter.add("Garlic");
        updateUI();

        // Dont make the keyboard popup
        hideSoftKeyboard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Checking the item id of our menu item.
        if (item.getItemId() == R.id.action_delete_item) {
            // Deleting all items and notifying our list adapter of the changes.
            shoppingListArrayAdapter.clear();
            updateUI();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Updates the UI of the application and in this case the shoppingListView
     */
    private void updateUI(){
        shoppingListView.setAdapter(shoppingListArrayAdapter);
        hideSoftKeyboard();
    }

    /**
     * Add an item to the shopping list and refreshes the UI
     */
    private void addItem(){
        shoppingListArrayAdapter.add(shoppingListInput.getText().toString());
        shoppingListInput.setText("");
        updateUI();
    }

    private void deleteItem(int position, View view){
        shoppingListArrayAdapter.remove(shoppingListArrayAdapter.getItem(position));
        shoppingListArrayAdapter.notifyDataSetChanged();
        Snackbar.make(view, "Item removed from the shopping list", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    /**
     * Hides the keyboard of the user
     */
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
