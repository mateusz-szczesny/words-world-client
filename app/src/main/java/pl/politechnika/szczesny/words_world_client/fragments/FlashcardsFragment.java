package pl.politechnika.szczesny.words_world_client.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.adapters.FlashCardLevelAdapter;
import pl.politechnika.szczesny.words_world_client.models.FlashCardLevel;
import pl.politechnika.szczesny.words_world_client.models.Language;
import pl.politechnika.szczesny.words_world_client.utils.Utils;
import pl.politechnika.szczesny.words_world_client.viewmodel.LanguageViewModel;

public class FlashcardsFragment extends Fragment {

    @BindView(R.id.flash_card_levels) RecyclerView _flashCardLevels;
    private FlashCardLevelAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashcards, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
        Activity activity = getActivity();
        if (activity != null) {
            adapter = new FlashCardLevelAdapter(getActivity().getApplication());
            _flashCardLevels.setLayoutManager(glm);
            _flashCardLevels.setAdapter(adapter);
            _flashCardLevels.setItemAnimator(new DefaultItemAnimator());
        }

        final List<FlashCardLevel> levels = new ArrayList<>();

        LanguageViewModel languageViewModel = ViewModelProviders.of(this).get(LanguageViewModel.class);
        languageViewModel.getLanguages().observe(this, new Observer<List<Language>>() {
            @Override
            public void onChanged(List<Language> languages) {
                levels.clear();
                for (Language language : languages) {
                    if ("pl".equals(language.getLanguageCode())) {
                        continue;
                    }
                    for (String level : Utils.flashCardLabel2DifficultyLevel.keySet()) {
                        levels.add(new FlashCardLevel(level, language));
                    }
                }
                adapter.setLevels(levels);
            }
        });

    }
}
