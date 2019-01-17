package pl.politechnika.szczesny.words_world_client.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.adapters.FriendsListAdapter;
import pl.politechnika.szczesny.words_world_client.models.User;
import pl.politechnika.szczesny.words_world_client.viewmodel.FriendViewModel;

public class FriendsActivity extends AppCompatActivity {
    private RecyclerView _friendsList;
    FriendsListAdapter adapter;
    FriendViewModel friendViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        _friendsList = findViewById(R.id.friends_list);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        adapter = new FriendsListAdapter(getApplication());

        _friendsList.setLayoutManager(llm);
        _friendsList.setAdapter(adapter);
        _friendsList.setItemAnimator(new DefaultItemAnimator());

        friendViewModel = ViewModelProviders.of(this).get(FriendViewModel.class);
        friendViewModel.getFriends().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> friends) {
                adapter.setFriends(friends);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        friendViewModel.refreshData(getApplication());
    }
}
