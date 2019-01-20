package pl.politechnika.szczesny.words_world_client.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.TimeZone;

import androidx.fragment.app.Fragment;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.fragments.MyCardsFragment;
import pl.politechnika.szczesny.words_world_client.fragments.FlashcardsFragment;
import pl.politechnika.szczesny.words_world_client.fragments.ProfileFragment;
import pl.politechnika.szczesny.words_world_client.receiver.AlarmReceiver;

import static pl.politechnika.szczesny.words_world_client.utils.SessionUtils.isSessionActive;

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

        loadFragment(new ProfileFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_profile);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        scheduleNotification(getApplicationContext());
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void scheduleNotification(Context context) {

        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getTimeZone("GMT"));
        updateTime.set(Calendar.HOUR_OF_DAY, 16);
        updateTime.set(Calendar.MINUTE, 45);

        Intent notification = new Intent(context, AlarmReceiver.class);

        PendingIntent recurringNotification = PendingIntent.getBroadcast(context,
                0, notification, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarms = (AlarmManager) getSystemService(
                Context.ALARM_SERVICE);

        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                updateTime.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, recurringNotification);
    }
}
