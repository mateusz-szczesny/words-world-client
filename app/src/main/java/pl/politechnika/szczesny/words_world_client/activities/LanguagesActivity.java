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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.adapters.LanguagesAdapter;
import pl.politechnika.szczesny.words_world_client.models.Language;
import pl.politechnika.szczesny.words_world_client.viewmodel.LanguageViewModel;

public class LanguagesActivity extends AppCompatActivity {
    @BindView(R.id.available_languages) RecyclerView _languagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);
        ButterKnife.bind(this);

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
                languageViewModel.refreshData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
