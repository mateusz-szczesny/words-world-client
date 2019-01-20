package pl.politechnika.szczesny.words_world_client.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.utils.Utils;
import pl.politechnika.szczesny.words_world_client.models.Card;

public class MyCardsAdapter extends RecyclerView.Adapter<MyCardsAdapter.MyCardsViewHolder> {

    private List<Card> cards;

    public MyCardsAdapter() {
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
        String blackList = "[ " + TextUtils.join(",", card.getBlackList()) + " ]";
        achievementViewHolder._blackList.setText(blackList.toUpperCase());
        achievementViewHolder._timesPlayed.setText(String.valueOf(card.getTimesPlayed()));
        achievementViewHolder._language.setText(Utils.returnFlagEmojiForLanguage(card.getLanguage()));
        String effPercent = (int)(card.getCardEfficiency() * 100.0) + "%";
        achievementViewHolder._efficiency.setText(effPercent);
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
