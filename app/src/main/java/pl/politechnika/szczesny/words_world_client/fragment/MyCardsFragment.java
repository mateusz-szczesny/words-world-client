package pl.politechnika.szczesny.words_world_client.fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.adapter.MyCardsAdapter;
import pl.politechnika.szczesny.words_world_client.model.Card;
import pl.politechnika.szczesny.words_world_client.viewmodel.MyCardsViewModel;

public class MyCardsFragment extends Fragment {

    MyCardsViewModel myCardsViewModel;
    RecyclerView _myCards;
    SwipeRefreshLayout _swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycards, container, false);

        _myCards = view.findViewById(R.id.mycards_list);
        _swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init() {
        myCardsViewModel = ViewModelProviders.of(this).get(MyCardsViewModel.class);
        myCardsViewModel.getCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                if (cards != null) {
                    assignCards(cards);
                }
            }
        });

        _swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myCardsViewModel.refreshData();
                _swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void assignCards(List<Card> cards) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        Activity activity = getActivity();
        if (activity != null) {
            final MyCardsAdapter adapter = new MyCardsAdapter(getActivity().getApplication());

            _myCards.setLayoutManager(llm);
            _myCards.setAdapter(adapter);
            _myCards.setItemAnimator(new DefaultItemAnimator());
            adapter.setCards(cards);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        myCardsViewModel.refreshData();
    }
}
