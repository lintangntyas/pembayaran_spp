package com.example.ukk_lintang2021.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ukk_lintang2021.Activities.Admin.DetailkelasActivity;
import com.example.ukk_lintang2021.Model.Kelas.KelasResultItem;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class KelasAdapter extends RecyclerView.Adapter<com.example.ukk_lintang2021.Adapter.KelasAdapter.ViewHolder> {

    private Context c;
    private int[] mMaterialColors;
    private List<KelasResultItem> kelasResultItems;
    public String searchString = "";

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nama_kelasTxt, kompetensi_kelasTxt;
        private MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;
        /**
         * We reference our widgets
         */
        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.mMaterialLetterIcon);
            nama_kelasTxt = itemView.findViewById(R.id.mNamakelasTxt);
            kompetensi_kelasTxt = itemView.findViewById(R.id.mKompetensiTxt);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }

    /**
     * Our MyAdapter's costructor
     */
    public KelasAdapter(Context mContext, ArrayList<KelasResultItem> kelasResultItems) {
        this.c = mContext;
        this.kelasResultItems = kelasResultItems;
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
    }
    /**
     * We override the onCreateViewHolder. Here is where we inflate our model.xml
     * layout into a view object and set it's background color
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.modelkelas, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    /**
     * Our onBindViewHolder method
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get current scientist
        final KelasResultItem k = kelasResultItems.get(position);

        //bind data to widgets
        holder.nama_kelasTxt.setText(k.getNamaKelas());
        holder.kompetensi_kelasTxt.setText(k.getKompetensiKeahlian());

        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(2);
        holder.mIcon.setLetterSize(25);
        holder.mIcon.setShapeColor(mMaterialColors[new Random().nextInt(
                mMaterialColors.length)]);
        holder.mIcon.setLetter(k.getNamaKelas());

        //get name and galaxy
        String nama_kelas = k.getNamaKelas().toLowerCase(Locale.getDefault());
        String kompetensi_keahlian = k.getKompetensiKeahlian().toLowerCase(Locale.getDefault());

        //highlight name text while searching
        if (nama_kelas.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = nama_kelas.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.nama_kelasTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nama_kelasTxt.setText(spanString);
        } else {
            //Utils.show(ctx, "Search string empty");
        }

        //highligh galaxy text while searching
        if (kompetensi_keahlian.contains(searchString) && !(searchString.isEmpty())) {

            int startPos = kompetensi_keahlian.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.kompetensi_kelasTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.kompetensi_kelasTxt.setText(spanString);
        }
        //open detailactivity when clicked
        holder.setItemClickListener(pos -> ApiClient.sendKelasToActivity(c, k,
                DetailkelasActivity.class));
    }
    @Override
    public int getItemCount() {
        return kelasResultItems.size();
    }
    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
//end