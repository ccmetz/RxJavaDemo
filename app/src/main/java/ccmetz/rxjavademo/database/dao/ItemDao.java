package ccmetz.rxjavademo.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import ccmetz.rxjavademo.database.entities.Item;
import io.reactivex.Flowable;

@Dao
public interface ItemDao
{
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  long insert(Item item);

  @Query("SELECT * FROM items")
  Flowable<List<Item>> getItems();
}
