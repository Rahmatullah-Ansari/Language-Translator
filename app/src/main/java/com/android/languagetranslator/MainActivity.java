package com.android.languagetranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;

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