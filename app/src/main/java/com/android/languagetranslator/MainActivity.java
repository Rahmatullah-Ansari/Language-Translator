package com.android.languagetranslator;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.TranslateRemoteModel;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    private com.google.android.material.textfield.TextInputEditText editText;
    private TextView result;
    private final String[] language_1={"From","English","Afrikaans","Arabic","Belarusian","Bulgarian","Bengali","Catalan","Czech","Chinese",
    "Hindi","Urdu","French","Slovenian","Albanian","Croatian","Danish","Dutch","Esperanto","Finnish","Galician","Georgian","German","Greek",
            "Gujarati","Haitian_creole","Hebrew","Hungarian","Icelandic","Indonesian","Irish","Italian","Japanese","Kannada","Korean","Latvian",
            "Lithuanian","Macedonian","Malay","Maltese","Marathi","Norwegian","Persian","Polish","Portuguese","Romanian","Russian","Slovak","Spanish",
            "Swahili","Swedish","Tagalog","Tamil","Telugu","Thai","Turkish","Ukrainian","Vietnamese","Welsh"};
    private final String[] language_2={"From","English","Afrikaans","Arabic","Belarusian","Bulgarian","Bengali","Catalan","Czech","Chinese",
            "Hindi","Urdu","French","Slovenian","Albanian","Croatian","Danish","Dutch","Esperanto","Finnish","Galician","Georgian","German","Greek",
            "Gujarati","Haitian_creole","Hebrew","Hungarian","Icelandic","Indonesian","Irish","Italian","Japanese","Kannada","Korean","Latvian",
            "Lithuanian","Macedonian","Malay","Maltese","Marathi","Norwegian","Persian","Polish","Portuguese","Romanian","Russian","Slovak","Spanish",
            "Swahili","Swedish","Tagalog","Tamil","Telugu","Thai","Turkish","Ukrainian","Vietnamese","Welsh"};
    private static final int REQUEST_PERMISSION_CODE=123;
    private String from_language_code=null;
    private String to_language_code=null;
    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private ArrayList<Holder> arrayList;
    private Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList=new ArrayList<>();
        adapter=new Adapter(MainActivity.this,arrayList);
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
        ArrayAdapter fromAdapter=new ArrayAdapter(this,R.layout.spinner_item,language_1);
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
        ArrayAdapter toAdapter=new ArrayAdapter(this, R.layout.spinner_item,language_2);
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
        result.setTextIsSelectable(false);
        result.setText("Downloading Model...");
        TranslatorOptions options=new TranslatorOptions.Builder()
                .setSourceLanguage(l1)
                .setTargetLanguage(l2)
                .build();
        final Translator translator = Translation.getClient(options);
            DownloadConditions conditions = new DownloadConditions.Builder().build();
            translator.downloadModelIfNeeded(conditions).addOnSuccessListener(unused -> {
                result.setText("Translating language...");
                translator.translate(text).addOnSuccessListener(s -> {
                    result.setText(s);
                    translator.close();
                    result.setTextIsSelectable(true);
                }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Error due to : - " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Can not able to download due to :- " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
//private final String[] language_1={"From","English","Afrikaans","Arabic","Belarusian","Bulgarian","Bengali","Catalan","Czech","Chinese",
//    "Hindi","Urdu","French","Slovenian","Albanian","Croatian","Danish","Dutch","Esperanto","Finnish","Galician","Georgian","German","Greek",
//            "Gujarati","Haitian_creole","Hebrew","Hungarian","Icelandic","Indonesian","Irish","Italian","Japanese","Kannada","Korean","Latvian",
//            "Lithuanian","Macedonian","Malay","Maltese","Marathi","Norwegian","Persian","Polish","Portuguese","Romanian","Russian","Slovak","Spanish",
//            "Swahili","Swedish","Tagalog","Tamil","Telugu","Thai","Turkish","Ukrainian","Vietnamese","Welsh"};
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
                case "Chinese":
                languageCode= TranslateLanguage.CHINESE;
                break;
                case "Hindi":
                languageCode= TranslateLanguage.HINDI;
                break;
                case "Urdu":
                languageCode= TranslateLanguage.URDU;
                break;
            case "French":
                languageCode= TranslateLanguage.FRENCH;
                break;
            case "Slovenian":
                languageCode= TranslateLanguage.SLOVENIAN;
                break;
            case "Albanian":
                languageCode= TranslateLanguage.ALBANIAN;
                break;
            case "Croatian":
                languageCode= TranslateLanguage.CROATIAN;
                break;
            case "Danish":
                languageCode= TranslateLanguage.DANISH;
                break;
            case "Dutch":
                languageCode= TranslateLanguage.DUTCH;
                break;
            case "Esperanto":
                languageCode= TranslateLanguage.ESPERANTO;
                break;
            case "Finnish":
                languageCode= TranslateLanguage.FINNISH;
                break;
            case "Galician":
                languageCode= TranslateLanguage.GALICIAN;
                break;
            case "Georgian":
                languageCode= TranslateLanguage.GEORGIAN;
                break;
            case "German":
                languageCode= TranslateLanguage.GERMAN;
                break;
            case "Greek":
                languageCode= TranslateLanguage.GREEK;
                break;
            case "Gujarati":
                languageCode= TranslateLanguage.GUJARATI;
                break;
            case "Haitian_creole":
                languageCode= TranslateLanguage.HAITIAN_CREOLE;
                break;
            case "Hebrew":
                languageCode= TranslateLanguage.HEBREW;
                break;
            case "Hungarian":
                languageCode= TranslateLanguage.HUNGARIAN;
                break;
            case "Icelandic":
                languageCode= TranslateLanguage.ICELANDIC;
                break;
            case "Indonesian":
                languageCode= TranslateLanguage.INDONESIAN;
                break;
            case "Irish":
                languageCode= TranslateLanguage.IRISH;
                break;
            case "Italian":
                languageCode= TranslateLanguage.ITALIAN;
                break;
            case "Japanese":
                languageCode= TranslateLanguage.JAPANESE;
                break;
            case "Kannada":
                languageCode= TranslateLanguage.KANNADA;
                break;
            case "Korean":
                languageCode= TranslateLanguage.KOREAN;
                break;
            case "Latvian":
                languageCode= TranslateLanguage.LATVIAN;
                break;
            case "Lithuanian":
                languageCode= TranslateLanguage.LITHUANIAN;
                break;
            case "Macedonian":
                languageCode= TranslateLanguage.MACEDONIAN;
                break;
            case "Malay":
                languageCode= TranslateLanguage.MALAY;
                break;
            case "Maltese":
                languageCode= TranslateLanguage.MALTESE;
                break;
            case "Marathi":
                languageCode= TranslateLanguage.MARATHI;
                break;
            case "Norwegian":
                languageCode= TranslateLanguage.NORWEGIAN;
                break;
            case "Persian":
                languageCode= TranslateLanguage.PERSIAN;
                break;
            case "Polish":
                languageCode= TranslateLanguage.POLISH;
                break;
            case "Portuguese":
                languageCode= TranslateLanguage.PORTUGUESE;
                break;
            case "Romanian":
                languageCode= TranslateLanguage.ROMANIAN;
                break;
            case "Russian":
                languageCode= TranslateLanguage.RUSSIAN;
                break;
            case "Slovak":
                languageCode= TranslateLanguage.SLOVAK;
                break;
            case "Spanish":
                languageCode= TranslateLanguage.SPANISH;
                break;
            case "Swahili":
                languageCode= TranslateLanguage.SWAHILI;
                break;
            case "Swedish":
                languageCode= TranslateLanguage.SWEDISH;
                break;
            case "Tagalog":
                languageCode= TranslateLanguage.TAGALOG;
                break;
            case "Tamil":
                languageCode= TranslateLanguage.TAMIL;
                break;
            case "Telugu":
                languageCode= TranslateLanguage.TELUGU;
                break;
            case "Thai":
                languageCode= TranslateLanguage.THAI;
                break;
            case "Turkish":
                languageCode= TranslateLanguage.TURKISH;
                break;
            case "Ukrainian":
                languageCode= TranslateLanguage.UKRAINIAN;
                break;
            case "Vietnamese":
                languageCode= TranslateLanguage.VIETNAMESE;
                break;
            case "Welsh":
                languageCode= TranslateLanguage.WELSH;
                break;
            default:
                languageCode=null;
        }
        return languageCode;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.share_result:
                if (!result.getText().toString().isEmpty()){
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT,result.getText().toString());
                    startActivity(Intent.createChooser(intent,"Share Via"));
                }
                else {
                    Toast.makeText(MainActivity.this, "Nothing to share!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.manage_model:
                getdownloadedModel();
                break;
        }
        return true;
    }
    private void getdownloadedModel() {
        RemoteModelManager modelManager = RemoteModelManager.getInstance();
        modelManager.getDownloadedModels(TranslateRemoteModel.class)
                .addOnSuccessListener(new OnSuccessListener<Set<TranslateRemoteModel>>() {
                    @Override
                    public void onSuccess(Set<TranslateRemoteModel> models) {
                        arrayList.clear();
                        for (TranslateRemoteModel t:models){
                            Holder holder=new Holder(t.getLanguage());
                            arrayList.add(holder);
                        }
                        adapter.notifyDataSetChanged();
                        showModels();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Unable to get model due to : -"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showModels() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.models,null);
        builder.setView(view);
        builder.show();
        recyclerView=view.findViewById(R.id.model_item_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }
}