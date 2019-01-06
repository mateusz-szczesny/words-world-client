package pl.politechnika.szczesny.words_world_client.taboo;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
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


import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.helper.Utils;
import pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper;

import static pl.politechnika.szczesny.words_world_client.helper.Utils.TabooLevel2CardColor;

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


    private Card mCard;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public TabooCard(Context context, Card card, SwipePlaceHolderView swipeView) {
        mContext = context;
        mCard = card;
        mSwipeView = swipeView;
    }

    private void fillCardDetails() {
        keyWord.setText(mCard.getKeyWord());

        BL1.setText(mCard.getBlackList()[0]);
        BL2.setText(mCard.getBlackList()[1]);
        BL3.setText(mCard.getBlackList()[2]);
        BL4.setText(mCard.getBlackList()[3]);
        BL5.setText(mCard.getBlackList()[4]);

        Integer color = TabooLevel2CardColor.get(mCard.getDifficulty());
        cardView.setCardBackgroundColor(color != null ? color : Color.WHITE);
    }

    @Resolve
    private void onResolved(){
        fillCardDetails();
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        Integer points = Utils.TabooLevel2Reward.get(mCard.getDifficulty());
        SharedPrefHelper.incrementTabooScore(
                (Application)mContext.getApplicationContext(),
                -1 * (points != null ? points : 0)
        );
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
        Integer points = Utils.TabooLevel2Reward.get(mCard.getDifficulty());
        SharedPrefHelper.incrementTabooScore(
                (Application)mContext.getApplicationContext(),
                points != null ? points : 0
        );

        if (!"".equals(mCard.getDifficulty().trim())) {
            SharedPrefHelper.incrementTabooStats(
                    (Application)mContext.getApplicationContext(),
                    Utils.ONE
            );
        }

        if (mSwipeView.getAllResolvers().size() == 1) {
            addSummaryCard();
        }
    }

    private void addSummaryCard() {
        mCard.setBlackList(new String[]{"", "Wynik " + SharedPrefHelper.getTabooScore((Application)mContext.getApplicationContext()) + "!"});
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
