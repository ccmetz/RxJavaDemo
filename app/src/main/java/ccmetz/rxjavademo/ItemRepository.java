package ccmetz.rxjavademo;

import android.content.Context;
import android.util.Log;

import java.util.List;

import ccmetz.rxjavademo.database.RxJavaDatabase;
import ccmetz.rxjavademo.database.dao.ItemDao;
import ccmetz.rxjavademo.database.entities.Item;
import io.reactivex.Flowable;

public class ItemRepository
{
  private static final String TAG = "ItemRepository";

  private ItemDao dao;
  public ItemRepository(Context context)
  {
    dao = RxJavaDatabase.getInstance(context).getItemDao();
  }

  public void insertItem(Item item)
  {
    Log.d(TAG, "Inserting new item: " + item.getName());
    AsyncExecutor.execute(() -> dao.insert(item));
  }

  public Flowable<List<Item>> getItems()
  {
    return dao.getItems();
  }
}
