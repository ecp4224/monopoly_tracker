package me.eddiep.android.monopolytracker.game.snapshots;

import android.os.Parcel;
import android.os.Parcelable;

import me.eddiep.android.monopolytracker.game.GameTracker;
import me.eddiep.android.monopolytracker.game.PlayerTracker;

public class TurnSnapshot implements Parcelable {
    private PlayerSnapshot[] players;

    private TurnSnapshot() { }

    protected TurnSnapshot(Parcel in) {
        players = in.createTypedArray(PlayerSnapshot.CREATOR);
    }

    public static final Creator<TurnSnapshot> CREATOR = new Creator<TurnSnapshot>() {
        @Override
        public TurnSnapshot createFromParcel(Parcel in) {
            return new TurnSnapshot(in);
        }

        @Override
        public TurnSnapshot[] newArray(int size) {
            return new TurnSnapshot[size];
        }
    };

    public static TurnSnapshot createEvent(GameTracker g) {
        PlayerTracker tracker = g.getTracker();

        TurnSnapshot snapshot = new TurnSnapshot();

        snapshot.players = new PlayerSnapshot[tracker.getPlayerCount()];

        for (int i = 0; i < snapshot.players.length; i++) {
            snapshot.players[i] = PlayerSnapshot.createEvent(tracker.getPlayers().get(i), g);
        }

        return snapshot;
    }

    public PlayerSnapshot[] getPlayers() {
        return players;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(players, flags);
    }
}
