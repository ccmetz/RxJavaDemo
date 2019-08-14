package ccmetz.rxjavademo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ccmetz.rxjavademo.database.dao.ItemDao;
import ccmetz.rxjavademo.database.entities.Item;

@Database(version = 1, entities = {Item.class})
public abstract class RxJavaDatabase extends RoomDatabase
{
  private static RxJavaDatabase instance;

  public abstract ItemDao getItemDao();

  public static synchronized RxJavaDatabase getInstance(Context context)
  {
    if (instance == null)
    {
      instance = Room.databaseBuilder(context.getApplicationContext(), RxJavaDatabase.class, "rx_java_database")
          .fallbackToDestructiveMigration()
          .build();
    }
    return instance;
  }
}
