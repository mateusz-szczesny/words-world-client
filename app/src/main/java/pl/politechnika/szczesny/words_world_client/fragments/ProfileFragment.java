package pl.politechnika.szczesny.words_world_client.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

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
import pl.politechnika.szczesny.words_world_client.activities.FriendsActivity;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.activities.SettingsActivity;
import pl.politechnika.szczesny.words_world_client.adapters.AchievementsGridAdapter;
import pl.politechnika.szczesny.words_world_client.models.User;
import pl.politechnika.szczesny.words_world_client.viewmodel.SessionUserViewModel;

public class ProfileFragment extends Fragment {
    @BindView(R.id.profile_name) TextView _username;
    @BindView(R.id.overall_score) TextView _overallScore;
    @BindView(R.id.followings_no) TextView _noOfFollowings;
    @BindView(R.id.achievements_list) RecyclerView _achievementsList;
    @BindView(R.id.edit_profile) Button _editProfile;

    private AchievementsGridAdapter adapter;
    private SessionUserViewModel sessionUserViewModel;
    private User _userData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        _editProfile.setOnClickListener(editProfile);
        _noOfFollowings.setOnClickListener(viewFriends);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridLayoutManager glm = new GridLayoutManager(getContext(), 3);
        Activity activity = getActivity();
        if (activity != null) {
            adapter = new AchievementsGridAdapter(getActivity().getApplication());

            _achievementsList.setLayoutManager(glm);
            _achievementsList.setAdapter(adapter);
            _achievementsList.setItemAnimator(new DefaultItemAnimator());
        }

        sessionUserViewModel = ViewModelProviders.of(this).get(SessionUserViewModel.class);
        sessionUserViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                _userData = user;
                fillData();
                adapter.setAchievements(_userData.getAchievements());
            }
        });
    }

    private android.view.View.OnClickListener editProfile = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View view) {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
        }
    };

    private android.view.View.OnClickListener viewFriends = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View view) {
            int noOfFollowings = Objects.requireNonNull(sessionUserViewModel.getUser().getValue()).getFollowedUsers().size();
            if (noOfFollowings > 0) {
                Intent intent = new Intent(getContext(), FriendsActivity.class);
                startActivity(intent);
            }
        }
    };

    private void fillData() {
        _username.setText(!"".equals(_userData.getUsername()) ? _userData.getUsername() : "");
        _noOfFollowings.setText(String.valueOf(_userData.getFollowedUsers().size()));
        _overallScore.setText(_userData.getOverallScore().getScore() != null ?
                String.valueOf(_userData.getOverallScore().getScore()) : "0");
    }

    private void assignAchievements() {

    }

    @Override
    public void onResume() {
        super.onResume();
        sessionUserViewModel.refreshData();
    }
}
