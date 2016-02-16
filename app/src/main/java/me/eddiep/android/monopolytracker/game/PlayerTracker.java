package me.eddiep.android.monopolytracker.game;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class PlayerTracker implements Parcelable {
    private List<Player> players = new ArrayList<>();
    private List<Player> potentalRanks = new ArrayList<>();

    public PlayerTracker() { }

    protected PlayerTracker(Parcel in) {
        players = in.createTypedArrayList(Player.CREATOR);
        for (Player p : players) {
            potentalRanks.add(p);
        }
        updateRanks();
    }

    public static final Creator<PlayerTracker> CREATOR = new Creator<PlayerTracker>() {
        @Override
        public PlayerTracker createFromParcel(Parcel in) {
            return new PlayerTracker(in);
        }

        @Override
        public PlayerTracker[] newArray(int size) {
            return new PlayerTracker[size];
        }
    };

    public Player createPlayer(int icon) {
        Player p = new Player();
        p.setIconRes(icon);
        p.setTurnNumber(players.size() + 1);

        players.add(p);

        return p;
    }

    public Player getPlayer(int index) {
        int turn = index + 1;
        for (Player p : players) {
            if (p.getTurnNumber() == turn)
                return p;
        }

        return null;
    }

    public void updateRanks() {
        Collections.sort(players);

        final HashMap<Player, Double> scores = new HashMap<>();

        for (Player p : players) {
            double score = calculatePotentalScore(p);
            scores.put(p, score);
        }

        Collections.sort(potentalRanks, new Comparator<Player>() {
            @Override
            public int compare(Player lhs, Player rhs) {
                double lhs_score = scores.get(lhs);
                double rhs_score = scores.get(rhs);

                double pot_money_lhs = lhs.getMoney() + (lhs.getMoney() * lhs_score);
                double pot_money_rhs = rhs.getMoney() + (rhs.getMoney() * rhs_score);

                if (pot_money_lhs < pot_money_rhs)
                    return 1;
                else if (pot_money_lhs > pot_money_rhs)
                    return -1;
                else
                    return 0;
            }
        });
    }

    public int getRank(Player player) {
        return players.indexOf(player) + 1;
    }

    public int getPotentalRank(Player player) {
        return potentalRanks.indexOf(player) + 1;
    }

    public double calculatePotentalScore(Player player) {
        double currentMoney = player.getMoney();
        double potentalGain = player.getProperties() * 203.214285714;
        potentalGain += potentalGain * (player.getHouses() / 4.0);

        double gainPercent = (((potentalGain + currentMoney)/currentMoney)-1.0);

        double potentalLoss = 0.0;
        for (Player p : players) {
            if (p == player)
                continue;

            double theirGain = p.getProperties() * 203.214285714;
            theirGain += potentalGain * (p.getHouses() / 4.0);

            potentalLoss += (theirGain * (p.getProperties() / 40.0));
        }

        double lossPercent = (((currentMoney - potentalLoss)/currentMoney)-1.0);

        if (gainPercent == lossPercent)
            return 0.0;

        return (gainPercent + lossPercent) / 2.0;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(players);
    }
}
