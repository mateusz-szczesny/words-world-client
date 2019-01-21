package pl.politechnika.szczesny.words_world_client.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.politechnika.szczesny.words_world_client.activities.AddTabooCardActivity;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.adapters.MyCardsAdapter;
import pl.politechnika.szczesny.words_world_client.models.Card;
import pl.politechnika.szczesny.words_world_client.viewmodel.MyCardViewModel;

public class MyCardsFragment extends Fragment {

    private MyCardViewModel myCardViewModel;
    @BindView(R.id.mycards_list) RecyclerView _myCards;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout _swipeRefreshLayout;
    @BindView(R.id.new_card) FloatingActionButton _addCard;

    private MyCardsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycards, container, false);
        ButterKnife.bind(this, view);

        _addCard.setOnClickListener(addCard);
        _swipeRefreshLayout.setOnRefreshListener(refresh);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        Activity activity = getActivity();
        if (activity != null) {
            adapter = new MyCardsAdapter();
            _myCards.setLayoutManager(llm);
            _myCards.setAdapter(adapter);
            _myCards.setItemAnimator(new DefaultItemAnimator());
        }

        myCardViewModel = ViewModelProviders.of(this).get(MyCardViewModel.class);
        myCardViewModel.getCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable List<Card> cards) {
                adapter.setCards(cards);
            }
        });
    }

    private SwipeRefreshLayout.OnRefreshListener refresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            myCardViewModel.refreshData();
            _swipeRefreshLayout.setRefreshing(false);
        }
    };

    private android.view.View.OnClickListener addCard = new android.view.View.OnClickListener() {
        @Override
        public void onClick(android.view.View view) {
            Intent intent = new Intent(getContext(), AddTabooCardActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        myCardViewModel.refreshData();
    }
}
