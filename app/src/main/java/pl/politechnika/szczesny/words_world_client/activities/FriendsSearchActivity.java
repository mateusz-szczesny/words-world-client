package pl.politechnika.szczesny.words_world_client.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.EditText;

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
import pl.politechnika.szczesny.words_world_client.utils.SharedPreferencesUtils;
import pl.politechnika.szczesny.words_world_client.models.User;
import pl.politechnika.szczesny.words_world_client.viewmodel.UsersViewModel;

public class FriendsSearchActivity extends AppCompatActivity {
    @BindView(R.id.friends_list) RecyclerView _friendsList;
    @BindView(R.id.search_text) EditText _searchText;

    private UsersViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_search);
        ButterKnife.bind(this);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        final FriendsListAdapter adapter = new FriendsListAdapter(getApplication());

        _friendsList.setLayoutManager(llm);
        _friendsList.setAdapter(adapter);
        _friendsList.setItemAnimator(new DefaultItemAnimator());

        userViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                if (users != null) {
                    users.remove(SharedPreferencesUtils.getUserFromSP(getApplication()));
                }
                adapter.setFriends(users);
            }
        });
    }

    public void search(View view) {
        String searchText = _searchText.getText().toString();
        userViewModel.refreshData(searchText);
    }
}
