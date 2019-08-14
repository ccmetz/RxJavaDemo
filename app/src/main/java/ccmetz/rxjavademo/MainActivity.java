package ccmetz.rxjavademo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ccmetz.rxjavademo.database.entities.Item;

public class MainActivity extends AppCompatActivity
{
  private static final String TAG = "MainActivity";

  private MainViewModel mainViewModel;
  private ListView itemList;
  private ItemAdapter itemAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    mainViewModel = new MainViewModel(this);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(view -> showAddItemDialog());

    setupListView();
    setupObservers();
  }

  private void setupListView()
  {
    itemList = findViewById(R.id.item_list);
    itemAdapter = new ItemAdapter(this, R.layout.layout_list_item, new ArrayList<>());
    itemList.setAdapter(itemAdapter);
  }

  private void setupObservers()
  {
    mainViewModel.getItemListLiveData().observe(this, items -> {
      Log.d(TAG, "Item list changed. Updating...");
      itemAdapter.clear();
      itemAdapter.addAll(items);
      itemAdapter.notifyDataSetChanged();
    });
  }

  private void showAddItemDialog()
  {
    View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null);
    AlertDialog addItemDialog = new AlertDialog.Builder(this)
        .setTitle("Add Item")
        .setView(dialogView)
        .setPositiveButton("Add", (dialog, which) -> {
          String itemName = ((EditText) dialogView.findViewById(R.id.add_item_edit_text)).getText().toString();
          mainViewModel.insertItem(new Item(itemName));
          dialog.dismiss();
          Toast.makeText(this, "Added Item to the database", Toast.LENGTH_SHORT).show();
        })
        .setNegativeButton("Cancel", (dialog, which) -> {
          dialog.dismiss();
        })
        .create();

    addItemDialog.show();
  }

  @Override
  protected void onDestroy()
  {
    super.onDestroy();
    mainViewModel.onDestroy();
  }
}
