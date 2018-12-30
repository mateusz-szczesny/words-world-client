package pl.politechnika.szczesny.words_world_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Objects;

import pl.politechnika.szczesny.words_world_client.R;

import static pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper.flushSP;

public class AppBaseActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    private FrameLayout view_stub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_app_base);
        view_stub = findViewById(R.id.view_stub);
        NavigationView navigation_view = findViewById(R.id.navigation_view);

        Menu drawerMenu = navigation_view.getMenu();
        for(int i = 0; i < drawerMenu.size(); i++) {
            drawerMenu.getItem(i).setOnMenuItemClickListener(this);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (view_stub != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = Objects.requireNonNull(inflater).inflate(layoutResID, view_stub, false);
            view_stub.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub != null) {
            view_stub.addView(view, params);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.nav_home:
                intent = new Intent(getBaseContext(), MainActivity.class);
                break;
            case R.id.nav_about:
                intent = new Intent(getBaseContext(), AboutActivity.class);
                break;
            case R.id.nav_settings:
                intent = new Intent(getBaseContext(), SettingsActivity.class);
                break;
            case R.id.nav_friends:
                intent = new Intent(getBaseContext(), FriendsSearchActivity.class);
            case R.id.nav_taboo:
//                intent = new Intent(getBaseContext(), AddFriendActivity.class);
                break;
        }

        getBaseContext().startActivity(intent);
        return false;
    }

    public void logout(View view) {
        flushSP(getApplication());

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
