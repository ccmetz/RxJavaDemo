package ccmetz.rxjavademo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ccmetz.rxjavademo.database.entities.Item;

public class ItemAdapter extends ArrayAdapter<Item>
{
  private int resourceLayout;
  private Context context;

  public ItemAdapter(Context context, int resource, List<Item> items) {
    super(context, resource, items);
    this.resourceLayout = resource;
    this.context = context;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    View v = convertView;
    if (v == null)
    {
      LayoutInflater inflater = LayoutInflater.from(context);
      v = inflater.inflate(resourceLayout, null);
    }

    Item item = getItem(position);
    if (item != null)
    {
      TextView idView = v.findViewById(R.id.item_id);
      TextView nameView = v.findViewById(R.id.item_name);
      idView.setText(String.valueOf(item.getId()));
      nameView.setText(item.getName());
    }

    return v;
  }
}
