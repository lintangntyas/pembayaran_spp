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

import com.example.ukk_lintang2021.Activities.Admin.DetailsiswaActivity;
import com.example.ukk_lintang2021.Model.Siswa.SiswaResultItem;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SiswaAdapter extends RecyclerView.Adapter<com.example.ukk_lintang2021.Adapter.SiswaAdapter.ViewHolder> {

    private Context c;
    private int[] mMaterialColors;
    private List<SiswaResultItem> siswaResultItems;
    public String searchString = "";

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nisTxt, namaTxt, idkelasTxt, idsppTxt;
        private MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;
        /**
         * We reference our widgets
         */
        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.mMaterialLetterIcon);
            nisTxt = itemView.findViewById(R.id.mNisTxt);
            namaTxt = itemView.findViewById(R.id.mNamaTxt);
            idkelasTxt = itemView.findViewById(R.id.mIdkelasTxt);
            idsppTxt = itemView.findViewById(R.id.mIdsppTxt);
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
    public SiswaAdapter(Context mContext, ArrayList<SiswaResultItem> siswaResultItems) {
        this.c = mContext;
        this.siswaResultItems = siswaResultItems;
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
    }
    /**
     * We override the onCreateViewHolder. Here is where we inflate our model.xml
     * layout into a view object and set it's background color
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.modelsiswa, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    /**
     * Our onBindViewHolder method
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get current scientist
        final SiswaResultItem s = siswaResultItems.get(position);

        //bind data to widgets
        holder.nisTxt.setText(s.getNis());
        holder.namaTxt.setText(s.getNama());
        holder.idkelasTxt.setText(s.getIdKelas());
        holder.idsppTxt.setText(s.getIdSpp());

        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(2);
        holder.mIcon.setLetterSize(25);
        holder.mIcon.setShapeColor(mMaterialColors[new Random().nextInt(
                mMaterialColors.length)]);
        holder.mIcon.setLetter(s.getNama());

        //get name and galaxy
        String nama = s.getNama().toLowerCase(Locale.getDefault());
        String nis = s.getNis().toLowerCase(Locale.getDefault());

        //highlight name text while searching
        if (nama.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = nama.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.namaTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.namaTxt.setText(spanString);
        } else {
            //Utils.show(ctx, "Search string empty");
        }

        //highligh galaxy text while searching
        if (nis.contains(searchString) && !(searchString.isEmpty())) {

            int startPos = nis.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.nisTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nisTxt.setText(spanString);
        }
        //open detailactivity when clicked
        holder.setItemClickListener(pos -> ApiClient.sendSiswaToActivity(c, s,
                DetailsiswaActivity.class));
    }
    @Override
    public int getItemCount() {
        return siswaResultItems.size();
    }
    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
//end