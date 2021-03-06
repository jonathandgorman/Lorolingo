package com.jonathangorman.lorlingo.adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.jonathangorman.lorlingo.R;
import com.jonathangorman.lorlingo.domain.CardItem;

import java.util.ArrayList;
import java.util.Locale;

// adapts individual items to main container layout
public class CardChoiceAdapter extends RecyclerView.Adapter<CardChoiceAdapter.ViewHolder>{

    private static final String TAG = "CardChoiceAdapter";
    private Context context;
    private ArrayList<CardItem> cardItemList;
    private String speechText = "";
    private Locale currLocale;

    public CardChoiceAdapter(Context context, ArrayList<CardItem> cardItemList) {
        this.context = context;
        this.cardItemList = cardItemList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, TextToSpeech.OnInitListener {

        ImageView imageView;
        ConstraintLayout constraintLayout;
        TextToSpeech tts = null;

        // holds widgets in memory
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_image);
            constraintLayout = itemView.findViewById(R.id.card_parent_layout);
        }

        @Override
        public void onClick(View v) {
            // if the view is clicked the name should be returned and spoken via TTS
            speechText = cardItemList.get(getAdapterPosition()).getAudioString();
            tts = new TextToSpeech(context, this);
            tts.setSpeechRate(context.getSharedPreferences("SPEECH_PREFERENCES", ContextWrapper.MODE_PRIVATE).getFloat("SPEECH_RATE", 1.0f));
            Toast.makeText(context, speechText, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onInit(int status) {
            if(status == TextToSpeech.SUCCESS) {
                if (!tts.isSpeaking()) {
                    if (currLocale == null) currLocale = Locale.getDefault();
                    tts.setLanguage(currLocale);
                    tts.speak(speechText, TextToSpeech.QUEUE_ADD, null);
                }
            }
        }
    }

    // Responsible for inflating the view
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    // Sets the holder values
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        //Log.d(TAG,"New item added at position: " + i);
        viewHolder.imageView.setImageResource(Integer.valueOf(cardItemList.get(i).getImageId()));
        viewHolder.constraintLayout.setOnClickListener(viewHolder);
    }

    // Returns the number of items
    @Override
    public int getItemCount() {
        return cardItemList.size();
    }
    public void setCurrLocale(Locale currLocale) {
        this.currLocale = currLocale;
    }
    public Locale getCurrLocale() {
       return this.currLocale;
    }
}
