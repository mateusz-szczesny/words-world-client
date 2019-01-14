package pl.politechnika.szczesny.words_world_client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pl.politechnika.szczesny.words_world_client.model.Statistics;
import pl.politechnika.szczesny.words_world_client.model.Card;
import pl.politechnika.szczesny.words_world_client.taboo.TabooCard;

public class TabooActivity extends AppCompatActivity {

    public static final String CARDS = "CARDS";
    private SwipePlaceHolderView mSwipeView;
    List<Card> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taboo);

        mSwipeView = findViewById(R.id.swipeView);

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.taboo_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.taboo_swipe_out_msg_view));

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

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Statistics.getInstance().pushStatistics(getApplication());
        this.finish();
    }
}
