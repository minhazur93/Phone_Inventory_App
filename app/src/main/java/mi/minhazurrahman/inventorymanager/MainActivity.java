package mi.minhazurrahman.inventorymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import mi.minhazurrahman.inventorymanager.data.PhoneDbHelper;
import mi.minhazurrahman.inventorymanager.data.StockItem;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getCanonicalName();
    PhoneDbHelper dbHelper;
    PhoneCursorAdapter adapter;
    int lastItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new PhoneDbHelper(this);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

        final ListView listView = (ListView) findViewById(R.id.list_view);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        Cursor cursor = dbHelper.readStock();

        adapter = new PhoneCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == 0) return;
                    final int currentItem = view.getFirstVisiblePosition();
                    if (currentItem > lastItem) {
                        fab.show();
                    } else if (currentItem < lastItem) {
                        fab.hide();
                    }
                lastItem = currentItem;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.swapCursor(dbHelper.readStock());
    }

    public void clickOnViewItem(long id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("itemId", id);
        startActivity(intent);
    }

    public void clickOnSale(long id, int quantity) {
        dbHelper.sellOneItem(id, quantity);
        adapter.swapCursor(dbHelper.readStock());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_dummy_data:
                // add dummy data for testing
                addDummyData();
                adapter.swapCursor(dbHelper.readStock());
        }
        return super.onOptionsItemSelected(item);
    }


    private void addDummyData() {
        StockItem iphone = new StockItem("iPhone 7", "$700", 45, "Apple", "000 000 0000", "support@apple.com", "android.resource://eu.laramartin.inventorymanager/mipmap/iphone");
        dbHelper.insertItem(iphone);

        StockItem galaxy = new StockItem("Galaxy s7 Edge", "$650", 24, "Samsung", "000 000 0000", "support@samsung.com", "android.resource://eu.laramartin.inventorymanager/mipmap/galaxys7edge");
        dbHelper.insertItem(galaxy);

        StockItem pixel = new StockItem("Google Pixel XL", "$450", 74, "Google", "000 000 0000", "support@google.com", "android.resource://eu.laramartin.inventorymanager/mipmap/googlepixelxl");
        dbHelper.insertItem(pixel);

        StockItem one_plus = new StockItem("OnePlus 3t", "$400", 44, "OnePlus", "000 000 0000", "support@oneplus.com", "android.resource://eu.laramartin.inventorymanager/mipmap/oneplus3t");
        dbHelper.insertItem(one_plus);

    }
}
