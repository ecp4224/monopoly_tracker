package me.eddiep.android.monopolytracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class PlayerSpinnerAdapter extends BaseAdapter {
    public static final List<Integer> images = new ArrayList<>();
    private LayoutInflater inflater;

    static {
        images.add(R.drawable.ic_barrow);
        images.add(R.drawable.ic_battleship);
        images.add(R.drawable.ic_cat);
        images.add(R.drawable.ic_dog);
        images.add(R.drawable.ic_hat);
        images.add(R.drawable.ic_iron);
        images.add(R.drawable.ic_racecar);
        images.add(R.drawable.ic_ring);
        images.add(R.drawable.ic_robot);
        images.add(R.drawable.ic_shoe);
        images.add(R.drawable.ic_thimble);
    }

    public PlayerSpinnerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return images.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder icon;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.player_spinner_row, parent, false);

            icon = new ViewHolder();
            icon.view = (ImageView)convertView.findViewById(R.id.player_spinner_icon);

            convertView.setTag(icon);
        } else {
            icon = (ViewHolder) convertView.getTag();
        }

        icon.view.setImageResource(images.get(position));

        return convertView;
    }

    private static class ViewHolder {
        private ImageView view;
    }
}
