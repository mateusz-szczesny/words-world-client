package pl.politechnika.szczesny.words_world_client.adapters;

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
import pl.politechnika.szczesny.words_world_client.utils.Utils;
import pl.politechnika.szczesny.words_world_client.models.Language;

public class LanguagesMiniAdapter extends RecyclerView.Adapter<LanguagesMiniAdapter.LanguageViewHolder> {
    private List<Language> languages;
    private Application application;

    public LanguagesMiniAdapter(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public LanguagesMiniAdapter.LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mini_lang_item, viewGroup, false);
        return new LanguageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final LanguagesMiniAdapter.LanguageViewHolder languageViewHolder, final int i) {
        final Language lang = languages.get(i);
        languageViewHolder.name.setText(Utils.returnFlagEmojiForLanguage(lang));
    }

    @Override
    public int getItemCount() {
        return languages == null ? 0 : languages.size();
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class LanguageViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;

        LanguageViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            name = itemView.findViewById(R.id.name);
        }
    }
}
