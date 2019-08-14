package ccmetz.rxjavademo;

import android.content.Context;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ccmetz.rxjavademo.database.entities.Item;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel
{
  private static final String TAG = "MainViewModel";

  private ItemRepository itemRepository;
  private Disposable itemListDisposable;
  private MutableLiveData<List<Item>> itemListLiveData;

  public MainViewModel(Context context)
  {
    itemRepository = new ItemRepository(context);
    itemListLiveData = new MutableLiveData<>();
    itemListDisposable = itemRepository.getItems()
        .doOnError(e -> System.out.println(e.getMessage()))
        .subscribeOn(Schedulers.io())
        .subscribe(items -> {
          Log.d(TAG, "Detected change in Items in DB. Posting LiveData.");
          itemListLiveData.postValue(items);
        });
  }

  public LiveData<List<Item>> getItemListLiveData()
  {
    return itemListLiveData;
  }

  public void insertItem(Item item)
  {
    itemRepository.insertItem(item);
  }

  public void onDestroy()
  {
    if (itemListDisposable != null)
    {
      itemListDisposable.dispose();
    }
    AsyncExecutor.dispose();
  }
}
