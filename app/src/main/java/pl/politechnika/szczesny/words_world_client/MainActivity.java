package pl.politechnika.szczesny.words_world_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import pl.politechnika.szczesny.words_world_client.fragment.MyCardsFragment;
import pl.politechnika.szczesny.words_world_client.fragment.FlashcardsFragment;
import pl.politechnika.szczesny.words_world_client.fragment.ProfileFragment;

import static pl.politechnika.szczesny.words_world_client.helper.SessionHelper.isSessionActive;

public class MainActivity extends AppBaseActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_cards:
                    fragment = new MyCardsFragment();
                    break;
                case R.id.navigation_flashcards:
                    fragment = new FlashcardsFragment();
                    break;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    break;
            }

            return loadFragment(fragment);
        }
    };

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isSessionActive(getApplication())) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        loadFragment(new MyCardsFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
