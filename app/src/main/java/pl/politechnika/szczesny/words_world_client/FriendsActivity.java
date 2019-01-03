package pl.politechnika.szczesny.words_world_client;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import pl.politechnika.szczesny.words_world_client.adapter.FriendsListAdapter;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.viewmodel.FriendsViewModel;

public class FriendsActivity extends AppCompatActivity {
    RecyclerView _friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        _friendsList = findViewById(R.id.friends_list);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        final FriendsListAdapter adapter = new FriendsListAdapter(getApplication());

        _friendsList.setLayoutManager(llm);
        _friendsList.setAdapter(adapter);
        _friendsList.setItemAnimator(new DefaultItemAnimator());

        final FriendsViewModel friendsViewModel = ViewModelProviders.of(this).get(FriendsViewModel.class);
        friendsViewModel.getFriends().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> friends) {
                adapter.setFriends(friends);
            }
        });
    }
}
