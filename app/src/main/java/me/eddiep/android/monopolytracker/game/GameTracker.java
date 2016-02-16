package me.eddiep.android.monopolytracker.game;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import me.eddiep.android.monopolytracker.R;
import me.eddiep.android.monopolytracker.game.snapshots.TurnSnapshot;

public class GameTracker implements Parcelable {
    private final Gson GSON = new Gson();
    private PlayerTracker tracker = new PlayerTracker();
    private List<TurnSnapshot> turns = new ArrayList<>();

    public GameTracker() {

    }

    protected GameTracker(Parcel in) {
        tracker = in.readParcelable(PlayerTracker.class.getClassLoader());
        turns = in.createTypedArrayList(TurnSnapshot.CREATOR);
    }

    public static final Creator<GameTracker> CREATOR = new Creator<GameTracker>() {
        @Override
        public GameTracker createFromParcel(Parcel in) {
            return new GameTracker(in);
        }

        @Override
        public GameTracker[] newArray(int size) {
            return new GameTracker[size];
        }
    };

    public PlayerTracker getTracker() {
        return tracker;
    }

    public TurnSnapshot saveTurn() {
        tracker.updateRanks();
        TurnSnapshot snapshot = TurnSnapshot.createEvent(this);
        turns.add(snapshot);
        return snapshot;
    }

    public boolean save(Context context) {
        String json = GSON.toJson(this);

        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput("game.json", Context.MODE_PRIVATE);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public TurnSnapshot getTurn(int turnNumber) {
        return turns.get(turnNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(tracker, flags);
        dest.writeTypedList(turns);
    }

    public boolean isNewGame() {
        return turns.size() == 0;
    }
}
