package com.android.languagetranslator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Spinner Start_language,target_language;
    private com.google.android.material.textfield.TextInputEditText editText;
    private ImageView microphone;
    private com.google.android.material.button.MaterialButton translate;
    private TextView result;
    private String[] language_1={"From","English","Afrikaans","Arabic","Belarusian","Bulgarian","Bengali","Catalan","Czech","Welsh",
    "Hindi","Urdu"};
    private String[] language_2={"To","English","Afrikaans","Arabic","Belarusian","Bulgarian","Bengali","Catalan","Czech","Welsh",
            "Hindi","Urdu"};
    private static final int REQUEST_PERMISSION_CODE=123;
    private int language_code,from_language_code,to_language_code=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Start_language=findViewById(R.id.start_language);
        target_language=findViewById(R.id.to_language);
        editText=findViewById(R.id.source);
        microphone=findViewById(R.id.microphone);
        translate=findViewById(R.id.translate_language);
        result=findViewById(R.id.translated_text);
        Start_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                from_language_code=get_language_code(language_1[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter fromAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,language_1);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Start_language.setAdapter(fromAdapter);
        target_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                from_language_code=get_language_code(language_1[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter toAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,language_2);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        target_language.setAdapter(toAdapter);
        microphone.setOnClickListener(view -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak to convert");
            try {
                startActivityForResult(intent,REQUEST_PERMISSION_CODE);
            }catch (Exception e){
                Toast.makeText(MainActivity.this, "Error : -"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        translate.setOnClickListener(view -> {
            if (editText.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "Please enter something to translate", Toast.LENGTH_SHORT).show();
            }
            else if (from_language_code==0){
                Toast.makeText(MainActivity.this, "Please select source language", Toast.LENGTH_SHORT).show();
            }
            else if (to_language_code==0){
                Toast.makeText(MainActivity.this, "Please select target language", Toast.LENGTH_SHORT).show();
            }
            else{
                Translate_language(from_language_code,to_language_code,editText.getText().toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_PERMISSION_CODE){
            if (resultCode==RESULT_OK && data != null){
                ArrayList<String> arrayList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                editText.setText(arrayList.get(0));
            }
        }
    }
    private void Translate_language(int from_language_code, int to_language_code, String text) {
        result.setText("Downloading Model...");
        FirebaseTranslatorOptions options=new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(from_language_code)
                .setTargetLanguage(to_language_code)
                .build();
        FirebaseTranslator translator= FirebaseNaturalLanguage.getInstance()
                .getTranslator(options);
        FirebaseModelDownloadConditions conditions=new FirebaseModelDownloadConditions.Builder().build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                result.setText("Translating language...");
                translator.translate(text).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        result.setText(s);
                    }
                }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Error due to : - "+e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Can not able to download due to :- "+e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private int get_language_code(String s) {
        int languageCode=0;
        switch (s){
            case "English":
                languageCode= FirebaseTranslateLanguage.EN;
                break;
            case "Afrikaans":
                languageCode= FirebaseTranslateLanguage.AF;
                break;
            case "Arabic":
                languageCode= FirebaseTranslateLanguage.AR;
                break;
                case "Belarusian":
                languageCode= FirebaseTranslateLanguage.BE;
                break;
                case "Bulgarian":
                languageCode= FirebaseTranslateLanguage.BG;
                break;
                case "Bengali":
                languageCode= FirebaseTranslateLanguage.BN;
                break;
                case "Catalan":
                languageCode= FirebaseTranslateLanguage.CA;
                break;
                case "Czech":
                languageCode= FirebaseTranslateLanguage.CS;
                break;
                case "Welsh":
                languageCode= FirebaseTranslateLanguage.CY;
                break;
                case "Hindi":
                languageCode= FirebaseTranslateLanguage.HI;
                break;
                case "Urdu":
                languageCode= FirebaseTranslateLanguage.UR;
                break;
            default:
                languageCode=0;
        }
        return languageCode;
    }
}