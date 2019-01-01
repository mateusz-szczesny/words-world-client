package pl.politechnika.szczesny.words_world_client.adapter;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.model.User;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder>{
    List<User> friends;
    Application application;

    public FriendsListAdapter(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_friend_item, viewGroup, false);
        FriendViewHolder fvh = new FriendViewHolder(v);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder friendViewHolder, int i) {
        friendViewHolder._username.setText(friends.get(i).getUsername());
        friendViewHolder._first_name.setText(!"".equals(friends.get(i).getFirstName()) ? friends.get(i).getFirstName() : "");
        friendViewHolder._last_name.setText(!"".equals(friends.get(i).getLastName()) ? friends.get(i).getLastName() : "");
    }

    @Override
    public int getItemCount() {
        return friends == null ? 0 : friends.size();
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {
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
