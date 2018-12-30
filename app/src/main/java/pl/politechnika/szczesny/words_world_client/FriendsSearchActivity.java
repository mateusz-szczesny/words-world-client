package pl.politechnika.szczesny.words_world_client;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.politechnika.szczesny.words_world_client.adapter.FriendsListAdapter;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.service.ApiManager;
import pl.politechnika.szczesny.words_world_client.viewmodel.UsersViewModel;

public class FriendsSearchActivity extends AppBaseActivity {
    RecyclerView _friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_search);
        _friendsList = findViewById(R.id.friends_list);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        final FriendsListAdapter adapter = new FriendsListAdapter(getApplication());
        UsersViewModel uvm = new UsersViewModel(getApplication());

        _friendsList.setLayoutManager(llm);
        _friendsList.setAdapter(adapter);
        _friendsList.setItemAnimator(new DefaultItemAnimator());

        final UsersViewModel userRelationViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        userRelationViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                adapter.setUsers(users);
            }
        });
    }
}
