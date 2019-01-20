package pl.politechnika.szczesny.words_world_client.adapters;

import android.app.Application;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import pl.politechnika.szczesny.words_world_client.activities.OtherUserActivity;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.models.User;

import static pl.politechnika.szczesny.words_world_client.utils.Utils.USER__ID;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder>{
    private List<User> friends;
    private Application application;
    private View view;

    public FriendsListAdapter(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_friend_item, viewGroup, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder friendViewHolder, int i) {
        final User currentUser = friends.get(i);
        friendViewHolder._username.setText(currentUser.getUsername());
        friendViewHolder._first_name.setText(!"".equals(currentUser.getFirstName()) ? currentUser.getFirstName() : "");
        friendViewHolder._last_name.setText(!"".equals(currentUser.getLastName()) ? currentUser.getLastName() : "");

        friendViewHolder._cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(application.getApplicationContext(), OtherUserActivity.class);
                intent.putExtra(USER__ID, currentUser.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends == null ? 0 : friends.size();
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }

    static class FriendViewHolder extends RecyclerView.ViewHolder {
        CardView _cv;
        TextView _username;
        TextView _first_name;
        TextView _last_name;

        FriendViewHolder(View itemView) {
            super(itemView);
            _cv = itemView.findViewById(R.id.cv);
            _username = itemView.findViewById(R.id.username);
            _first_name = itemView.findViewById(R.id.first_name);
            _last_name = itemView.findViewById(R.id.last_name);

        }
    }
}
