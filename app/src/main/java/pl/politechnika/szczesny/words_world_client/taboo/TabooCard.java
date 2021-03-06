package pl.politechnika.szczesny.words_world_client.taboo;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;


import androidx.cardview.widget.CardView;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.models.Card;
import pl.politechnika.szczesny.words_world_client.models.Statistics;

import static pl.politechnika.szczesny.words_world_client.utils.Utils.tabooLevel2CardColor;

@Layout(R.layout.taboo_card_view)
public class TabooCard {

    @View(R.id.taboo_card)
    private CardView cardView;

    @View(R.id.KEY_WORD)
    private TextView keyWord;

    @View(R.id.BL1)
    private TextView BL1;
    @View(R.id.BL2)
    private TextView BL2;
    @View(R.id.BL3)
    private TextView BL3;
    @View(R.id.BL4)
    private TextView BL4;
    @View(R.id.BL5)
    private TextView BL5;
    @View(R.id.owner)
    private TextView owner;

    private Card mCard;
    private SwipePlaceHolderView mSwipeView;

    public TabooCard(Card card, SwipePlaceHolderView swipeView) {
        mCard = card;
        mSwipeView = swipeView;
    }

    private void fillCardDetails() {
        keyWord.setText(mCard.getKeyWord().toUpperCase());

        BL1.setText(mCard.getBlackList()[0].toUpperCase());
        BL2.setText(mCard.getBlackList()[1].toUpperCase());
        BL3.setText(mCard.getBlackList()[2].toUpperCase());
        BL4.setText(mCard.getBlackList()[3].toUpperCase());
        BL5.setText(mCard.getBlackList()[4].toUpperCase());

        String autorSign = "Autor: " + mCard.getOwner();
        owner.setText(autorSign);

        Integer color = tabooLevel2CardColor.get(mCard.getDifficulty());
        cardView.setCardBackgroundColor(color != null ? color : Color.WHITE);
    }

    @Resolve
    private void onResolved(){
        fillCardDetails();
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        if (!"".equals(mCard.getDifficulty().trim())) {
            Statistics.getInstance().addIncorrectlySwipedCard(mCard.getId());
        }

        if (mSwipeView.getAllResolvers().size() == 1) {
            addSummaryCard();
        }
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
        if (!"".equals(mCard.getDifficulty().trim())) {
            Statistics.getInstance().addCorrectlySwipedCard(mCard.getId());
        }

        if (mSwipeView.getAllResolvers().size() == 1) {
            addSummaryCard();
        }
    }

    private void addSummaryCard() {
        mCard.setBlackList(new String[]{"", "Wynik " + Statistics.getInstance().getCurrentGameScore() + "!"});
        mCard.setKeyWord("Gratulacje!");
        mCard.setDifficulty("");
        mSwipeView.addView(this);
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}
