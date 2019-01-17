package pl.politechnika.szczesny.words_world_client.fragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import pl.politechnika.szczesny.words_world_client.activities.FriendsActivity;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.activities.SettingsActivity;
import pl.politechnika.szczesny.words_world_client.adapters.AchievementsGridAdapter;
import pl.politechnika.szczesny.words_world_client.models.User;
import pl.politechnika.szczesny.words_world_client.viewmodel.SessionUserViewModel;

public class ProfileFragment extends Fragment {
    private TextView _username;
    private TextView _overallScore;
    private TextView _noOfFollowings;
    private RecyclerView _achievementsList;
    private Button _editProfile;

    private SessionUserViewModel sessionUserViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        _username = view.findViewById(R.id.profile_name);
        _noOfFollowings = view.findViewById(R.id.followings_no);
        _achievementsList = view.findViewById(R.id.achievements_list);
        _editProfile = view.findViewById(R.id.edit_profile);
        _overallScore = view.findViewById(R.id.overall_score);

        init();

        return view;
    }

    private void init() {
        sessionUserViewModel = ViewModelProviders.of(this).get(SessionUserViewModel.class);
        sessionUserViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    fillData(user);
                    assignAchievements(user);
                }
            }
        });

        _noOfFollowings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer noOfFollowings = Objects.requireNonNull(sessionUserViewModel.getUser().getValue()).getFollowedUsers().size();
                if (noOfFollowings > 0) {
                    Intent intent = new Intent(getContext(), FriendsActivity.class);
                    startActivity(intent);
                }
            }
        });

        _editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fillData(User user) {
        _username.setText(!"".equals(user.getUsername()) ? user.getUsername() : "");
        _noOfFollowings.setText(String.valueOf(user.getFollowedUsers().size()));
        _overallScore.setText(user.getOverallScore().getScore() != null ?
                String.valueOf(user.getOverallScore().getScore()) : "0");
    }

    private void assignAchievements(User user) {
        GridLayoutManager glm = new GridLayoutManager(getContext(), 3);
        Activity activity = getActivity();
        if (activity != null) {
            final AchievementsGridAdapter adapter = new AchievementsGridAdapter(getActivity().getApplication());

            _achievementsList.setLayoutManager(glm);
            _achievementsList.setAdapter(adapter);
            _achievementsList.setItemAnimator(new DefaultItemAnimator());
            adapter.setAchievements(user.getAchievements());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sessionUserViewModel.refreshData();
    }
}
