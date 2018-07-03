package mobile.proj.spoilers.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import mobile.proj.spoilers.R;
import mobile.proj.spoilers.model.Spoil;

import java.util.ArrayList;


public class SpoilAdapter extends ArrayAdapter<Spoil> {
    private Context context;
    private ArrayList<Spoil> spoilArrayList;

    public SpoilAdapter(Context context, ArrayList<Spoil>spoils) {
        super(context, 0,spoils);
        this.context = context;
        this.spoilArrayList=spoils;
    }

    @Override
    public int getCount() {
        return spoilArrayList.size();
    }

    @Override
    public Spoil getItem(int i) {

        return spoilArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
  @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.item_spoil, null);

        TextView spoilContent=(TextView)v.findViewById(R.id.spoilContent);

      spoilContent.setText(""+ spoilArrayList.get(i).getSpoilContent());
        v.setTag(spoilArrayList.get(i).getIdSpoil());
        return v;
    }

}
