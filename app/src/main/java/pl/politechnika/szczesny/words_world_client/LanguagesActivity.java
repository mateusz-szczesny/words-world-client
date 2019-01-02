package pl.politechnika.szczesny.words_world_client;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import pl.politechnika.szczesny.words_world_client.adapter.LanguagesAdapter;
import pl.politechnika.szczesny.words_world_client.helper.SessionHelper;
import pl.politechnika.szczesny.words_world_client.model.Language;
import pl.politechnika.szczesny.words_world_client.viewmodel.LanguageViewModel;

public class LanguagesActivity extends AppBaseActivity {
    RecyclerView _languagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);
        _languagesList = findViewById(R.id.languages_list);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        final LanguageViewModel languageViewModel = ViewModelProviders.of(this).get(LanguageViewModel.class);
        final LanguagesAdapter adapter = new LanguagesAdapter(getApplication(), languageViewModel);

        _languagesList.setLayoutManager(llm);
        _languagesList.setAdapter(adapter);
        _languagesList.setItemAnimator(new DefaultItemAnimator());

        languageViewModel.getLanguages().observe(this, new Observer<List<Language>>() {
            @Override
            public void onChanged(@Nullable List<Language> languages) {
                adapter.setLanguages(languages);
            }
        });

        final SwipeRefreshLayout mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutFriend);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                languageViewModel.refreshData(getApplication());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SessionHelper.updateUserData(getApplication());
    }
}
