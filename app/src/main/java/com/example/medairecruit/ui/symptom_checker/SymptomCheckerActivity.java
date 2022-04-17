package com.example.medairecruit.ui.symptom_checker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.example.medairecruit.R;
import com.example.medairecruit.databinding.ActivityHomeBinding;
import com.example.medairecruit.databinding.ActivitySymptomCheckerBinding;
import com.example.medairecruit.ui.MainActivity;
import com.example.medairecruit.ui.home.HomeActivity;
import com.example.medairecruit.ui.profile.ProfileActivity;
import com.example.medairecruit.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class SymptomCheckerActivity extends AppCompatActivity {

    private ActivitySymptomCheckerBinding binding;
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREFS = "shared_prefs";

    private SymptomCheckerSuggestionAdapter dataAdapter = new SymptomCheckerSuggestionAdapter();

    List<String> suggestedWordList = new ArrayList<>();
    String selectedSuggestions = "";

    List<String> searchedWordList = new ArrayList<>();
    String selectedSearched = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySymptomCheckerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        initSelectedRecyclerView();
        initSearchedRecyclerView();
        initData();
        initClickListener();
    }

    private void initClickListener() {

        binding.closeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            finishAffinity();
            startActivity(intent);
        });

        binding.submitBtn.setOnClickListener(view -> {
            showCustomDialog();
        });
    }

    private void initData() {
        suggestedWordList.add("Fever");
        suggestedWordList.add("Cough");
        suggestedWordList.add("Cramping");
        suggestedWordList.add("Weight loss");
        suggestedWordList.add("Tenderness");

        searchedWordList.add("Stomach pain");
        searchedWordList.add("Stomach ache");
        searchedWordList.add("Dull stomach pain");
    }

    private void initSearchedRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recyclerViewSearched);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        dataAdapter = new SymptomCheckerSuggestionAdapter(this, searchedWordList);
        dataAdapter.setClickListener((view, position) ->
                //Helper.toast(getApplicationContext(),"clicked items position is "+position+" -> "+suggestedWordList.get(position))
                selectedSearched = searchedWordList.get(position)
        );
        recyclerView.setAdapter(dataAdapter);
    }

    private void initSelectedRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recyclerViewSuggested);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false));
        dataAdapter = new SymptomCheckerSuggestionAdapter(this, suggestedWordList);
        dataAdapter.setClickListener((view, position) ->
                //Helper.toast(getApplicationContext(),"clicked items position is "+position+" -> "+suggestedWordList.get(position))
                selectedSuggestions = suggestedWordList.get(position)
        );
        recyclerView.setAdapter(dataAdapter);
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.custom_dialog);

        //Initializing the views of the dialog.
        Button submitButton = dialog.findViewById(R.id.submitBtn);
        Button startOverButton = dialog.findViewById(R.id.startOverBtn);

        startOverButton.setOnClickListener(view -> {
            dialog.dismiss();
            finishAffinity();
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });

        submitButton.setOnClickListener(view -> {
            dialog.dismiss();
            finishAffinity();
            SessionManager.setIsSignedIn(getApplicationContext(), false);
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        });

        dialog.show();
    }
}