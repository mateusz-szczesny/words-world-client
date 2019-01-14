package pl.politechnika.szczesny.words_world_client.adapter;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.helper.FontManager;
import pl.politechnika.szczesny.words_world_client.helper.Utils;
import pl.politechnika.szczesny.words_world_client.model.Achievement;
import pl.politechnika.szczesny.words_world_client.model.Card;

public class MyCardsAdapter extends RecyclerView.Adapter<MyCardsAdapter.MyCardsViewHolder> {

    private List<Card> cards;
    private final Application application;

    public MyCardsAdapter(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public MyCardsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item, viewGroup, false);
        return new MyCardsAdapter.MyCardsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCardsViewHolder achievementViewHolder, int i) {
        Card card = cards.get(i);

        achievementViewHolder._keyWord.setText(card.getKeyWord());
        achievementViewHolder._blackList.setText("[ " + TextUtils.join(",", card.getBlackList()) + " ]");
        achievementViewHolder._timesPlayed.setText(String.valueOf(card.getTimesPlayed()));
        achievementViewHolder._language.setText(Utils.returnFlagEmojiForLanguage(card.getLanguage()));
        achievementViewHolder._efficiency.setText((int)(card.getCardEfficiency() * 100.0) + "%");
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cards == null ? 0 : cards.size();
    }

    static class MyCardsViewHolder extends RecyclerView.ViewHolder {
        CardView _cv;
        TextView _keyWord;
        TextView _blackList;
        TextView _timesPlayed;
        TextView _efficiency;
        TextView _language;

        MyCardsViewHolder(View itemView) {
            super(itemView);
            _cv = itemView.findViewById(R.id.cv);
            _keyWord = itemView.findViewById(R.id.key_word);
            _blackList = itemView.findViewById(R.id.black_list);
            _timesPlayed = itemView.findViewById(R.id.times_played);
            _efficiency = itemView.findViewById(R.id.efficiency);
            _language = itemView.findViewById(R.id.lang);
        }
    }
}
