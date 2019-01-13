package pl.politechnika.szczesny.words_world_client;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.io.IOException;

import pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper;
import pl.politechnika.szczesny.words_world_client.model.Statistics;
import pl.politechnika.szczesny.words_world_client.model.Card;
import pl.politechnika.szczesny.words_world_client.taboo.TabooCard;

public class TabooActivity extends AppCompatActivity {

    private SwipePlaceHolderView mSwipeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taboo);

        SharedPrefHelper.flushTabooScore(getApplication());

        mSwipeView = findViewById(R.id.swipeView);
        Context mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.taboo_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.taboo_swipe_out_msg_view));

        // TODO: call for cards for selected settings from previous screen
//        try {
//            for(Object card : TabooManager.getInstance(getApplicationContext()).getCards()){
//                mSwipeView.addView(new TabooCard(mContext, (Card)card, mSwipeView));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
