package pl.politechnika.szczesny.words_world_client.adapter;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.model.User;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder>{
    List<User> users;
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
        friendViewHolder.username.setText(users.get(i).getUsername());
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView username;

        FriendViewHolder(View itemView) {
            super(itemView);
            username = (TextView)itemView.findViewById(R.id.username);
        }
    }
}
