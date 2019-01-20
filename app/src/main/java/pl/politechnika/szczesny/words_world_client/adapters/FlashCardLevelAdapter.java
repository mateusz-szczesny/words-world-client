package pl.politechnika.szczesny.words_world_client.adapters;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import pl.politechnika.szczesny.words_world_client.R;
import pl.politechnika.szczesny.words_world_client.activities.FlashCardActivity;
import pl.politechnika.szczesny.words_world_client.models.Card;
import pl.politechnika.szczesny.words_world_client.models.FlashCard;
import pl.politechnika.szczesny.words_world_client.models.FlashCardLevel;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
import pl.politechnika.szczesny.words_world_client.utils.SessionUtils;
import pl.politechnika.szczesny.words_world_client.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlashCardLevelAdapter extends RecyclerView.Adapter<FlashCardLevelAdapter.FlashCardLevelViewHolder> {
    private List<FlashCardLevel> levels;
    private Application application;

    public FlashCardLevelAdapter(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public FlashCardLevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_flashcard_level_item, parent, false);
        return new FlashCardLevelAdapter.FlashCardLevelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashCardLevelViewHolder holder, int position) {
        final FlashCardLevel flashCardLevel = levels.get(position);
        holder._level.setText(flashCardLevel.getLevel());
        holder._flagEmoji.setText(Utils.returnFlagEmojiForLanguage(flashCardLevel.getLanguage()));

        holder._cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Uruchamianie...");
                progressDialog.show();

                String token = SessionUtils.getToken(application);
                ApiManager.getInstance().getFlashCards(token, flashCardLevel.getLanguage(), Utils.flashCardLabel2DifficultyLevel.get(flashCardLevel.getLevel()), 15, new Callback<List<FlashCard>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<FlashCard>> call, @NonNull Response<List<FlashCard>> response) {
                        if (response.isSuccessful()) {
                            List<FlashCard> cards = response.body();
                            if (cards != null && !cards.isEmpty()) {
                                Type type = new TypeToken<List<Card>>() {}.getType();
                                String json = new Gson().toJson(cards, type);

                                Intent intent = new Intent(view.getContext(), FlashCardActivity.class);
                                intent.putExtra(FlashCardActivity.CARDS, json);
                                progressDialog.cancel();
                                view.getContext().startActivity(intent);
                            } else {
                                Toast.makeText(view.getContext(), "Brak kart, spróbuj innym razem :(", Toast.LENGTH_LONG).show();
                                progressDialog.cancel();
                            }
                        } else {
                            Toast.makeText(view.getContext(), "Błąd połączenia!", Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<FlashCard>> call, @NonNull Throwable t) {
                        Log.d("API ERROR", t.getMessage());
                        progressDialog.cancel();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return levels == null ? 0 : levels.size();
    }

    public void setLevels(List<FlashCardLevel> levels) {
        this.levels = levels;
        notifyDataSetChanged();
    }

    static class FlashCardLevelViewHolder extends RecyclerView.ViewHolder {
        CardView _cv;
        TextView _level;
        TextView _flagEmoji;

        FlashCardLevelViewHolder(View itemView) {
            super(itemView);
            _cv = itemView.findViewById(R.id.cv);
            _level = itemView.findViewById(R.id.level);
            _flagEmoji = itemView.findViewById(R.id.language_emoji);

        }
    }
}
