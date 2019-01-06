package pl.politechnika.szczesny.words_world_client;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import pl.politechnika.szczesny.words_world_client.adapter.FriendsListAdapter;
import pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.viewmodel.UserViewModel;

public class FriendsSearchActivity extends AppCompatActivity {
    RecyclerView _friendsList;
    ImageButton _search;
    EditText _searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_search);
        _friendsList = findViewById(R.id.friends_list);
        _search = findViewById(R.id.search_button);
        _searchText = findViewById(R.id.search_text);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        final FriendsListAdapter adapter = new FriendsListAdapter(getApplication());

        _friendsList.setLayoutManager(llm);
        _friendsList.setAdapter(adapter);
        _friendsList.setItemAnimator(new DefaultItemAnimator());

        final UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                adapter.setFriends(users);
            }
        });

        _search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = _searchText.getText().toString();
                userViewModel.refreshData(getApplication(), searchText);
            }
        });
    }
}
