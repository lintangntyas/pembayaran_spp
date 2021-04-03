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

import com.example.ukk_lintang2021.Activities.Admin.DetailpetugasActivity;
import com.example.ukk_lintang2021.Model.Petugas.PetugasResultItem;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PetugasAdapter extends RecyclerView.Adapter<com.example.ukk_lintang2021.Adapter.PetugasAdapter.ViewHolder> {

    private Context c;
    private int[] mMaterialColors;
    private List<PetugasResultItem> petugasResultItems;
    public String searchString = "";

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView usernameTxt, passwordTxt, namapetugasTxt;
        private MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.mMaterialLetterIcon);
            usernameTxt = itemView.findViewById(R.id.mUsernameTxt);
            passwordTxt = itemView.findViewById(R.id.mPasswordTxt);
            namapetugasTxt = itemView.findViewById(R.id.mNamapetugasTxt);
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

    public PetugasAdapter(Context mContext, ArrayList<PetugasResultItem> petugasResultItems) {
        this.c = mContext;
        this.petugasResultItems = petugasResultItems;
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.modelpetugas, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get current scientist
        final PetugasResultItem p = petugasResultItems.get(position);

        //bind data to widgets
        holder.usernameTxt.setText(p.getUsername());
        holder.passwordTxt.setText(p.getPassword());
        holder.namapetugasTxt.setText(p.getNamaPetugas());
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(2);
        holder.mIcon.setLetterSize(25);
        holder.mIcon.setShapeColor(mMaterialColors[new Random().nextInt(
                mMaterialColors.length)]);
        holder.mIcon.setLetter(p.getUsername());

        //get name and galaxy
        String username = p.getUsername().toLowerCase(Locale.getDefault());
        String nama_petugas = p.getNamaPetugas().toLowerCase(Locale.getDefault());

        //highlight name text while searching
        if (username.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = username.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.usernameTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.usernameTxt.setText(spanString);
        } else {
            //Utils.show(ctx, "Search string empty");
        }

        //highligh galaxy text while searching
        if (nama_petugas.contains(searchString) && !(searchString.isEmpty())) {

            int startPos = nama_petugas.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.namapetugasTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.namapetugasTxt.setText(spanString);
        }
        //open detailactivity when clicked
        holder.setItemClickListener(pos -> ApiClient.sendPetugasToActivity(c, p,
                DetailpetugasActivity.class));
    }
    @Override
    public int getItemCount() {
        return petugasResultItems.size();
    }
    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
//end