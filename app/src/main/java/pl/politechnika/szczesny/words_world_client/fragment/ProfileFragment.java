package pl.politechnika.szczesny.words_world_client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pl.politechnika.szczesny.words_world_client.FriendsActivity;
import pl.politechnika.szczesny.words_world_client.FriendsSearchActivity;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.SettingsActivity;
import pl.politechnika.szczesny.words_world_client.helper.SessionHelper;
import pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.service.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    public User _loggedUser;
    public TextView _username;
    public TextView _first_name;
    public TextView _last_name;
    public TextView _noOfFollowings;
    public RecyclerView _achievementsList;
    public Button _editProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        _username = view.findViewById(R.id.profile_name);
        _first_name = view.findViewById(R.id.first_name);
        _last_name = view.findViewById(R.id.last_name);
        _noOfFollowings = view.findViewById(R.id.followings_no);
        _achievementsList = view.findViewById(R.id.achievements_list);
        _editProfile = view.findViewById(R.id.edit_profile);


        init();
        return view;
    }

    private void init() {
        SessionHelper.updateUserData(getActivity().getApplication());
        fillData(SharedPrefHelper.getUserFormSP(getActivity().getApplication()));

        ApiManager.getInstance().fetchUser(SharedPrefHelper.getTokenFormSP(getActivity().getApplication()), new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                User user = response.body();
                fillData(user);

                // TODO: fill list of achievements | more: https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("API ERROR", "CANNOT FETCH USER DATA");
            }
        });

        _noOfFollowings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FriendsActivity.class);
                startActivity(intent);
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
        _first_name.setText(!"".equals(user.getFirstName()) ? user.getFirstName() : "");
        _last_name.setText(!"".equals(user.getLastName()) ? user.getLastName() : "");
        _noOfFollowings.setText(String.valueOf(user.getFollowedUsers().size()));
    }
}
