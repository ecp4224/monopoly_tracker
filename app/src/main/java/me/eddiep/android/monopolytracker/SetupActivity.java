package me.eddiep.android.monopolytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import me.eddiep.android.monopolytracker.game.GameTracker;
import me.eddiep.android.monopolytracker.game.Player;

public class SetupActivity extends AppCompatActivity {
    private GameTracker game = new GameTracker();
    private LinearLayout players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_setup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Setup");
        setSupportActionBar(toolbar);

        players = (LinearLayout) findViewById(R.id.player_view);
        addPlayer();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.new_player);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlayer();
            }
        });
    }

    private void addPlayer() {
        final Player p = game.getTracker().createPlayer(R.drawable.ic_barrow);

        View v = getLayoutInflater().inflate(R.layout.player_setup_item, players, false);

        TextView text = (TextView) v.findViewById(R.id.player_name);
        text.setText("Player " + p.getTurnNumber());

        Spinner spinner = (Spinner) v.findViewById(R.id.player_images);
        spinner.setAdapter(new PlayerSpinnerAdapter(this));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                p.setIcon(PlayerSpinnerAdapter.images.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                p.setIcon(PlayerSpinnerAdapter.images.get(0));
            }
        });

        players.addView(v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.setup_done) {
            game.saveTurn();
            Intent newIntent = new Intent(this, MainActivity.class);
            newIntent.putExtra("game", game);
            startActivity(newIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
