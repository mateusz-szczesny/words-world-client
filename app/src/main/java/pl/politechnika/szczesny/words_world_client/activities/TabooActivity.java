package pl.politechnika.szczesny.words_world_client.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.models.Statistics;
import pl.politechnika.szczesny.words_world_client.models.Card;
import pl.politechnika.szczesny.words_world_client.taboo.TabooCard;

public class TabooActivity extends AppCompatActivity {

    public static final String CARDS = "CARDS";
    @BindView(R.id.swipeView) SwipePlaceHolderView mSwipeView;
    private List<Card> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taboo);
        ButterKnife.bind(this);

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.taboo_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.taboo_swipe_out_msg_view));

        assignCards();
    }

    private void assignCards() {
        String stringCards = getIntent().getStringExtra(CARDS);
        if (stringCards != null) {
            Type type = new TypeToken<List<Card>>() {
            }.getType();
            cards = new Gson().fromJson(stringCards, type);
        } else {
            Log.d("DATA","MISSING!");
        }

        for (Card card : cards){
            mSwipeView.addView(new TabooCard(card, mSwipeView));
        }
    }

    public void accept(View view) {
        mSwipeView.doSwipe(true);
    }

    public void reject(View view) {
        mSwipeView.doSwipe(false);
    }

    @Override
    public void onBackPressed() {
        Statistics.getInstance().pushStatistics(getApplication());
        this.finish();
    }
}
