package com.android.languagetranslator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.TranslateRemoteModel;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    public Context context;
    public ArrayList<Holder> arrayList;
    public Adapter(Context context, ArrayList<Holder> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.model_item,parent,false));
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String name=arrayList.get(position).getName();
        switch (name) {
            case "ar":
                holder.Name.setText("Arabic");
                break;
            case "hi":
                holder.Name.setText("Hindi");
                break;
            case "en":
                holder.Name.setText("English");
                break;
            case "bn":
                holder.Name.setText("Bengali");
                break;
            case "fr":
                holder.Name.setText("French");
                break;
            case "ur":
                holder.Name.setText("Urdu");
                break;
            case "zh":
                holder.Name.setText("Chinese");
                break;
            case "cs":
                holder.Name.setText("Czech");
                break;
            case "ca":
                holder.Name.setText("Catalan");
                break;
            case "bg":
                holder.Name.setText("Bulgarian");
                break;
            case "be":
                holder.Name.setText("Belarusian");
                break;
            case "af":
                holder.Name.setText("Afrikaans");
                break;
            case "sq":
                holder.Name.setText("Albanian");
                break;
            case "hr":
                holder.Name.setText("Croatian");
                break;
            case "da":
                holder.Name.setText("Danish");
                break;
            case "nl":
                holder.Name.setText("Dutch");
                break;
            case "eo":
                holder.Name.setText("Esperanto");
                break;
            case "fi":
                holder.Name.setText("Finnish");
                break;
            case "gl":
                holder.Name.setText("Galician");
                break;
            case "ka":
                holder.Name.setText("Georgian");
                break;
            case "de":
                holder.Name.setText("German");
                break;
            case "el":
                holder.Name.setText("Greek");
                break;
            case "gu":
                holder.Name.setText("Gujarati");
                break;
            case "ht":
                holder.Name.setText("Haitian_creole");
                break;
            case "he":
                holder.Name.setText("Hebrew");
                break;
            case "hu":
                holder.Name.setText("Hungarian");
                break;
            case "is":
                holder.Name.setText("Icelandic");
                break;
            case "id":
                holder.Name.setText("Indonesian");
                break;
            case "ga":
                holder.Name.setText("Irish");
                break;
            case "it":
                holder.Name.setText("Italian");
                break;
            case "ja":
                holder.Name.setText("Japanese");
                break;
            case "kn":
                holder.Name.setText("Kannada");
                break;
            case "ko":
                holder.Name.setText("Korean");
                break;
            case "lv":
                holder.Name.setText("Latvian");
                break;
            case "lt":
                holder.Name.setText("Lithuanian");
                break;
            case "mk":
                holder.Name.setText("Macedonian");
                break;
            case "ms":
                holder.Name.setText("Malay");
                break;
            case "mt":
                holder.Name.setText("Maltese");
                break;
            case "mr":
                holder.Name.setText("Marathi");
                break;
            case "no":
                holder.Name.setText("Norwegian");
                break;
            case "fa":
                holder.Name.setText("Persian");
                break;
            case "pl":
                holder.Name.setText("Polish");
                break;
            case "pt":
                holder.Name.setText("Portuguese");
                break;
            case "ro":
                holder.Name.setText("Romanian");
                break;
            case "ru":
                holder.Name.setText("Russian");
                break;
            case "sk":
                holder.Name.setText("Slovak");
                break;
            case "sl":
                holder.Name.setText("Slovenian");
                break;
            case "es":
                holder.Name.setText("Spanish");
                break;
            case "sw":
                holder.Name.setText("Swahili");
                break;
            case "sv":
                holder.Name.setText("Swedish");
                break;
            case "tl":
                holder.Name.setText("Tagalog");
                break;
            case "ta":
                holder.Name.setText("Tamil");
                break;
            case "te":
                holder.Name.setText("Telugu");
                break;
            case "th":
                holder.Name.setText("Thai");
                break;
            case "tr":
                holder.Name.setText("Turkish");
                break;
            case "uk":
                holder.Name.setText("Ukrainian");
                break;
            case "vi":
                holder.Name.setText("Vietnamese");
                break;
            case "cy":
                holder.Name.setText("Welsh");
                break;
        }
        holder.Delete.setOnClickListener(view -> {
            String name1=holder.Name.getText().toString();
            switch (name1) {
                case "Arabic":
                    deleteModel(TranslateLanguage.ARABIC, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "French":
                    deleteModel(TranslateLanguage.FRENCH, position);
                    break;
                case "Bengali":
                    deleteModel(TranslateLanguage.BENGALI, position);
                    break;
                case "Slovenian":
                    deleteModel(TranslateLanguage.SLOVENIAN, position);
                    break;
                case "Urdu":
                    deleteModel(TranslateLanguage.URDU, position);
                    break;
                case "Chinese":
                    deleteModel(TranslateLanguage.CHINESE, position);
                    break;
                case "Czech":
                    deleteModel(TranslateLanguage.CZECH, position);
                    break;
                case "Catalan":
                    deleteModel(TranslateLanguage.CATALAN, position);
                    break;
                case "Bulgarian":
                    deleteModel(TranslateLanguage.BULGARIAN, position);
                    break;
                case "Belarusian":
                    deleteModel(TranslateLanguage.BELARUSIAN, position);
                    break;
                case "Afrikaans":
                    deleteModel(TranslateLanguage.AFRIKAANS, position);
                    break;
                case "Albanian":
                    deleteModel(TranslateLanguage.ALBANIAN, position);
                    break;
                case "Croatian":
                    deleteModel(TranslateLanguage.CROATIAN, position);
                    break;
                case "Danish":
                    deleteModel(TranslateLanguage.DANISH, position);
                    break;
                case "Dutch":
                    deleteModel(TranslateLanguage.DUTCH, position);
                    break;
                case "Esperanto":
                    deleteModel(TranslateLanguage.ESPERANTO, position);
                    break;
                case "Finnish":
                    deleteModel(TranslateLanguage.FINNISH, position);
                    break;
                case "Galician":
                    deleteModel(TranslateLanguage.GALICIAN, position);
                    break;
                case "Georgian":
                    deleteModel(TranslateLanguage.GEORGIAN, position);
                    break;
                case "German":
                    deleteModel(TranslateLanguage.GERMAN, position);
                    break;
                case "Greek":
                    deleteModel(TranslateLanguage.GREEK, position);
                    break;
                case "Gujarati":
                    deleteModel(TranslateLanguage.GUJARATI, position);
                    break;
                case "Haitian_creole":
                    deleteModel(TranslateLanguage.HAITIAN_CREOLE, position);
                    break;
                case "Hebrew":
                    deleteModel(TranslateLanguage.HEBREW, position);
                    break;
                case "Hungarian":
                    deleteModel(TranslateLanguage.HUNGARIAN, position);
                    break;
                case "Icelandic":
                    deleteModel(TranslateLanguage.ICELANDIC, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
                case "Hindi":
                    deleteModel(TranslateLanguage.HINDI, position);
                    break;
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void deleteModel(String l1,int pos) {
        RemoteModelManager modelManager = RemoteModelManager.getInstance();
        TranslateRemoteModel germanModel =
                new TranslateRemoteModel.Builder(l1).build();
        modelManager.deleteDownloadedModel(germanModel)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();
                    arrayList.remove(pos);
                    notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Unable to delete due to : -"+e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        public TextView Name;
        public ImageView Delete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.model_name);
            Delete=itemView.findViewById(R.id.delete_model);
        }
    }
}
