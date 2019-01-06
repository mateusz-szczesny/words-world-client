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
import pl.politechnika.szczesny.words_world_client.helper.FontManager;
import pl.politechnika.szczesny.words_world_client.helper.Utils;
import pl.politechnika.szczesny.words_world_client.model.Achievement;

public class AchievementsGridAdapter extends RecyclerView.Adapter<AchievementsGridAdapter.AchievementViewHolder> {

    private List<Achievement> achievements;
    private final Application application;

    public AchievementsGridAdapter(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.achievement_item, viewGroup, false);
        return new AchievementsGridAdapter.AchievementViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder achievementViewHolder, int i) {
        Achievement achievement = achievements.get(i);
        achievementViewHolder._fa_icon.setTypeface(FontManager.getTypeface(application.getApplicationContext(), FontManager.FONTAWESOME));
        String iconCode = application.getApplicationContext().getResources().getString(application.getApplicationContext().getResources()
                .getIdentifier(achievement.getBadgeIcon(), "string", application.getPackageName()));
        achievementViewHolder._fa_icon.setText(iconCode);
        achievementViewHolder._fa_icon.setTextColor(Utils.achievementLevel2Color.get(achievement.getLevel()));
        achievementViewHolder._name.setText(achievement.getName());
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return achievements == null ? 0 : achievements.size();
    }

    static class AchievementViewHolder extends RecyclerView.ViewHolder {
        CardView _cv;
        TextView _fa_icon;
        TextView _name;

        AchievementViewHolder(View itemView) {
            super(itemView);
            _cv = itemView.findViewById(R.id.cv);
            _fa_icon = itemView.findViewById(R.id.fa_icon);
            _name = itemView.findViewById(R.id.name);

        }
    }
}
