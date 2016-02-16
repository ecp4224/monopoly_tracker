package me.eddiep.android.monopolytracker.game.snapshots;

import android.os.Parcel;
import android.os.Parcelable;

import me.eddiep.android.monopolytracker.game.GameTracker;
import me.eddiep.android.monopolytracker.game.Player;

public class PlayerSnapshot implements Parcelable {
    private int money;
    private int rank;
    private int potentialRank;
    private int playerTurn;

    private PlayerSnapshot() { }

    protected PlayerSnapshot(Parcel in) {
        money = in.readInt();
        rank = in.readInt();
        potentialRank = in.readInt();
        playerTurn = in.readInt();
    }

    public static final Creator<PlayerSnapshot> CREATOR = new Creator<PlayerSnapshot>() {
        @Override
        public PlayerSnapshot createFromParcel(Parcel in) {
            return new PlayerSnapshot(in);
        }

        @Override
        public PlayerSnapshot[] newArray(int size) {
            return new PlayerSnapshot[size];
        }
    };

    public static PlayerSnapshot createEvent(Player p, GameTracker g) {
        PlayerSnapshot snapshot = new PlayerSnapshot();

        snapshot.money = p.getMoney();
        snapshot.rank = g.getTracker().getRank(p);
        snapshot.potentialRank = g.getTracker().getPotentalRank(p);
        snapshot.playerTurn = p.getTurnNumber();

        return snapshot;
    }

    public int getMoney() {
        return money;
    }

    public int getRank() {
        return rank;
    }

    public int getPotentialRank() {
        return potentialRank;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(money);
        dest.writeInt(rank);
        dest.writeInt(potentialRank);
        dest.writeInt(playerTurn);
    }
}
