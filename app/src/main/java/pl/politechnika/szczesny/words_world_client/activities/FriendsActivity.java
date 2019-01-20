package pl.politechnika.szczesny.words_world_client.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.adapters.FriendsListAdapter;
import pl.politechnika.szczesny.words_world_client.models.User;
import pl.politechnika.szczesny.words_world_client.viewmodel.FriendViewModel;

public class FriendsActivity extends AppCompatActivity {
    @BindView(R.id.friends_list) RecyclerView _friendsList;
    private FriendsListAdapter adapter;
    private FriendViewModel friendViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ButterKnife.bind(this);

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
        friendViewModel.refreshData();
    }
}
