package me.eddiep.android.monopolytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import me.eddiep.android.monopolytracker.game.GameTracker;
import me.eddiep.android.monopolytracker.game.Player;

public class PlayerFragment extends Fragment {

    private TextView rank;
    private TextView prank;
    private TextView pscore;
    private Player p;
    private GameTracker game;

    public PlayerFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlayerFragment newInstance(int player, GameTracker game) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putInt("player", player);
        args.putParcelable("game", game);
        fragment.setArguments(args);
        return fragment;
    }

    private void update() {
        game.getTracker().updateRanks();
        rank.setText("Rank: " + game.getTracker().getRank(p));
        prank.setText("Potential Rank: " + game.getTracker().getPotentalRank(p));
        pscore.setText("Gain/Loss Score: " + (Math.round(game.getTracker().calculatePotentalScore(p) * 100.0) / 100.0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        game = getArguments().getParcelable("game");
        if (game != null) {
            p = game.getTracker().getPlayer(getArguments().getInt("player"));

            if (p != null) {
                p.setImage((CircleImageView) rootView.findViewById(R.id.player_icon));

                rank = (TextView) rootView.findViewById(R.id.player_rank);
                prank = (TextView) rootView.findViewById(R.id.player_prank);
                pscore = (TextView) rootView.findViewById(R.id.player_pscore);

                update();

                EditText money = (EditText) rootView.findViewById(R.id.money);
                EditText properties = (EditText) rootView.findViewById(R.id.properties);
                EditText houses = (EditText) rootView.findViewById(R.id.houses);

                money.setText("" + p.getMoney());
                properties.setText("" + p.getProperties());
                houses.setText("" + p.getHouses());

                money.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0)
                            return;
                        try {
                            p.setMoney(Integer.parseInt(s.toString()));
                        } catch (Throwable ignored) {

                        }
                        update();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                properties.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0)
                            return;
                        try {
                            p.setProperties(Integer.parseInt(s.toString()));
                        } catch (Throwable ignored) {

                        }
                        update();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                houses.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0)
                            return;
                        try {
                            p.setHouses(Integer.parseInt(s.toString()));
                        } catch (Throwable ignored) {

                        }
                        update();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        }

        return rootView;
    }
}
