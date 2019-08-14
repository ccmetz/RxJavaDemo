package ccmetz.rxjavademo;

import android.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AsyncExecutor
{
  private static final String TAG = "AsyncExecutor";
  private static CompositeDisposable compositeDisposable = new CompositeDisposable();

  public static void execute(Callable task)
  {
    compositeDisposable.add(Observable.just(task)
        .doOnError(e -> Log.e(TAG, e.getMessage()))
        .subscribeOn(Schedulers.io())
        .subscribe(Callable::call));
  }

  public static void dispose()
  {
    compositeDisposable.dispose();
  }
}
