package com.android.languagetranslator;
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
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.Locale;
@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    private com.google.android.material.textfield.TextInputEditText editText;
    private TextView result;
    private final String[] language_1={"From","English","Afrikaans","Arabic","Belarusian","Bulgarian","Bengali","Catalan","Czech","Chinees",
    "Hindi","Urdu"};
    private final String[] language_2={"To","English","Afrikaans","Arabic","Belarusian","Bulgarian","Bengali","Catalan","Czech","Chinees",
            "Hindi","Urdu"};
    private static final int REQUEST_PERMISSION_CODE=123;
    private String from_language_code=null;
    private String to_language_code=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner start_language = findViewById(R.id.start_language);
        Spinner target_language = findViewById(R.id.to_language);
        editText=findViewById(R.id.source);
        ImageView microphone = findViewById(R.id.microphone);
        com.google.android.material.button.MaterialButton translate = findViewById(R.id.translate_language);
        result=findViewById(R.id.translated_text);
        start_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        start_language.setAdapter(fromAdapter);
        target_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                to_language_code=get_language_code(language_2[i]);
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
            else if (from_language_code==null){
                Toast.makeText(MainActivity.this, "Please select source language", Toast.LENGTH_SHORT).show();
            }
            else if (to_language_code==null){
                Toast.makeText(MainActivity.this, "Please select target language", Toast.LENGTH_SHORT).show();
            }
            else if(from_language_code == to_language_code){
                Toast.makeText(MainActivity.this, "Same language can not be translated!", Toast.LENGTH_SHORT).show();
            }
            else{
                Translate_language(from_language_code,to_language_code,editText.getText().toString());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_PERMISSION_CODE){
            if (resultCode==RESULT_OK && data != null){
                ArrayList<String> arrayList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                editText.setText(arrayList.get(0));
            }
        }
    }
    private void Translate_language(String l1, String l2, String text) {
        result.setText("Downloading Model...");
        TranslatorOptions options=new TranslatorOptions.Builder()
                .setSourceLanguage(String.valueOf(l1))
                .setTargetLanguage(String.valueOf(l2))
                .build();
        final Translator translator = Translation.getClient(options);
            DownloadConditions conditions = new DownloadConditions.Builder().build();
            translator.downloadModelIfNeeded(conditions).addOnSuccessListener(unused -> {
                result.setText("Translating language...");
                translator.translate(text).addOnSuccessListener(s -> {
                    result.setText(s);
                    translator.close();
                }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Error due to : - " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Can not able to download due to :- " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private String get_language_code(String s) {
        String languageCode=null;
        switch (s){
            case "English":
                languageCode= TranslateLanguage.ENGLISH;
                break;
            case "Afrikaans":
                languageCode= TranslateLanguage.AFRIKAANS;
                break;
            case "Arabic":
                languageCode= TranslateLanguage.ARABIC;
                break;
                case "Belarusian":
                languageCode= TranslateLanguage.BELARUSIAN;
                break;
                case "Bulgarian":
                languageCode= TranslateLanguage.BULGARIAN;
                break;
                case "Bengali":
                languageCode= TranslateLanguage.BENGALI;
                break;
                case "Catalan":
                languageCode= TranslateLanguage.CATALAN;
                break;
                case "Czech":
                languageCode= TranslateLanguage.CZECH;
                break;
                case "Chinees":
                languageCode= TranslateLanguage.CHINESE;
                break;
                case "Hindi":
                languageCode= TranslateLanguage.HINDI;
                break;
                case "Urdu":
                languageCode= TranslateLanguage.URDU;
                break;
            default:
                languageCode=null;
        }
        return languageCode;
    }
}