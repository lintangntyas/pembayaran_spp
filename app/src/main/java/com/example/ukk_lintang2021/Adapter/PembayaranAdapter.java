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

import com.example.ukk_lintang2021.Activities.Admin.DetailpembayaranActivity;
import com.example.ukk_lintang2021.Model.Pembayaran.PembayaranResultItem;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PembayaranAdapter extends RecyclerView.Adapter<com.example.ukk_lintang2021.Adapter.PembayaranAdapter.ViewHolder> {
    private Context c;
    private int[] mMaterialColors;
    private List<PembayaranResultItem> pembayaranResultItems;
    public String searchString = "";

    /**
     * Our ViewHolder class. It's responsibilities include:
     * 1. Hold all the widgets which will be recycled and reference them.
     * 2. Implement click event.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        private TextView id_petugasTxt, nisnTxt, id_sppTxt, jumlahTxt;
        private MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;
        /**
         * We reference our widgets
         */
        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.mMaterialLetterIcon);
            id_petugasTxt = itemView.findViewById(R.id.mIdpetugasTxt);
            nisnTxt = itemView.findViewById(R.id.mIdnisnTxt);
            id_sppTxt = itemView.findViewById(R.id.mIdsppTxt);
            jumlahTxt = itemView.findViewById(R.id.mJumlahTxt);
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
    public PembayaranAdapter(Context mContext, ArrayList<PembayaranResultItem> pembayaranResultItems) {
        this.c = mContext;
        this.pembayaranResultItems = pembayaranResultItems;
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
    }
    /**
     * We override the onCreateViewHolder. Here is where we inflate our model.xml
     * layout into a view object and set it's background color
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.modelpembayaran, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    /**
     * Our onBindViewHolder method
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get current scientist
        final PembayaranResultItem p = pembayaranResultItems.get(position);

        //bind data to widgets
        holder.id_petugasTxt.setText(p.getIdPetugas());
        holder.nisnTxt.setText(p.getNisn());
        holder.id_sppTxt.setText(p.getIdSpp());
        holder.jumlahTxt.setText(p.getJumlahBayar());
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(2);
        holder.mIcon.setLetterSize(25);
        holder.mIcon.setShapeColor(mMaterialColors[new Random().nextInt(
                mMaterialColors.length)]);
        holder.mIcon.setLetter(p.getNisn());

        //get name and galaxy
        String id_petugasTxt = p.getIdPetugas().toLowerCase(Locale.getDefault());
        String nisn = p.getNisn().toLowerCase(Locale.getDefault());

        //highlight name text while searching
        if (id_petugasTxt.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = id_petugasTxt.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.id_petugasTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.id_petugasTxt.setText(spanString);
        } else {
            //Utils.show(ctx, "Search string empty");
        }

        //highligh galaxy text while searching
        if (nisn.contains(searchString) && !(searchString.isEmpty())) {

            int startPos = nisn.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.nisnTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nisnTxt.setText(spanString);
        }
        //open detailactivity when clicked
        holder.setItemClickListener(pos -> ApiClient.sendPembayaranToActivity(c, p,
                DetailpembayaranActivity.class));
    }
    @Override
    public int getItemCount() {
        return pembayaranResultItems.size();
    }
    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
//end