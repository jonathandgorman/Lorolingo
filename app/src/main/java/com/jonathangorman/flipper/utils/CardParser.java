package com.jonathangorman.flipper.utils;

import android.content.Context;
import android.util.Log;

import com.jonathangorman.flipper.R;
import com.jonathangorman.flipper.cards.Card;
import com.jonathangorman.flipper.cards.CardList;
import com.jonathangorman.flipper.cards.FoodCard;
import static com.jonathangorman.flipper.utils.Constants.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CardParser {
    private static final String TAG = "CardParser";

    private static final int LANGUAGE_POS = 0;
    private static final int CATEGORY_POS = 1;
    private static final int ID_POS = 2;
    private static final int AUDIO_POS = 3;
    private static final int IMAGE_POS = 4;

    private Context context = null;
    private Card currCard = null;
    private String parseLang = "";
    private String parseCat = "";
    private CardList cardList = new CardList();

    // Returns the cardList
    public CardList getCardList() {
        if (cardList.isEmpty()) {
            Log.i(TAG, "The card list is empty. Must call start() first.");
            return null;
        }
        return cardList;
    }

    // starts the parsing process
    public int start()
    {
        String language;
        String category;
        String currLine;

        // Check that the config file exists
        InputStream is = context.getResources().openRawResource(R.raw.config);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192);

        try {
            while ((currLine = br.readLine()) != null) {

                currCard = new Card();
                // parse the input line to keywords based on ";"
                String[] lineSplit;
                lineSplit = currLine.split(";");

                // If the language and category of the line do not comply, we check the next line
                language = lineSplit[LANGUAGE_POS];
                if (!language.equalsIgnoreCase(parseLang)) {
                    continue;
                }
                category = lineSplit[CATEGORY_POS];
                if (!category.equalsIgnoreCase(parseCat)) {
                    continue;
                }

                // Add details to the card
                currCard.setLanguage(language);
                currCard.setCategory(category);
                currCard.setName(lineSplit[ID_POS]);
                currCard.setAudio(lineSplit[AUDIO_POS]);
                currCard.setImageName(lineSplit[IMAGE_POS]);

                // Take a line and convert it to a card, then add it to a cardList
                addCardToCardList(currCard);
            }
        } catch (Exception e) {
            return FALSE;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    return FALSE;
                }
            }
        }
        return TRUE;
    }


    // Adds a card to the list
    void addCardToCardList(Card card)
    {
        cardList.add(card);
    }

    public void setParserLang(String inputLang)
    {
        this.parseLang = inputLang;
    }

    public void setParserCategory(String inputCat)
    {
        this.parseCat = inputCat;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }
}
