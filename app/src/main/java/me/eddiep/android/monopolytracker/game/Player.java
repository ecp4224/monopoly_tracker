package me.eddiep.android.monopolytracker.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class Player implements Comparable<Player>, Parcelable {
    private int money = 1500;
    private int properties;
    private int houses;
    private int iconRes;
    private int turnNumber;

    public Player() { }

    protected Player(Parcel in) {
        money = in.readInt();
        properties = in.readInt();
        houses = in.readInt();
        iconRes = in.readInt();
        turnNumber = in.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getProperties() {
        return properties;
    }

    public void setProperties(int properties) {
        this.properties = properties;
    }

    public int getHouses() {
        return houses;
    }

    public void setHouses(int houses) {
        this.houses = houses;
    }

    void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public void setImage(CircleImageView view) {
        view.setImageResource(iconRes);
    }

    public int getIconRes() {
        return iconRes;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public boolean hasMonopoly() {
        return houses > 0; //You can only have houses if you have properties
    }

    void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    @Override
    public int compareTo(@NonNull Player another) {
        return another.money - this.money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return money == player.money && properties == player.properties && houses == player.houses && iconRes == player.iconRes && turnNumber == player.turnNumber;

    }

    @Override
    public int hashCode() {
        int result = money;
        result = 31 * result + properties;
        result = 31 * result + houses;
        result = 31 * result + iconRes;
        result = 31 * result + turnNumber;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(money);
        dest.writeInt(properties);
        dest.writeInt(houses);
        dest.writeInt(iconRes);
        dest.writeInt(turnNumber);
    }

    public void setIcon(Integer icon) {
        this.iconRes = icon;
    }
}
