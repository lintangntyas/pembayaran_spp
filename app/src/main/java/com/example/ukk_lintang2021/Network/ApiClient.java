package com.example.ukk_lintang2021.Network;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ukk_lintang2021.Model.Kelas.KelasResultItem;
import com.example.ukk_lintang2021.Model.Pembayaran.PembayaranResultItem;
import com.example.ukk_lintang2021.Model.Petugas.PetugasResultItem;
import com.example.ukk_lintang2021.Model.Siswa.SiswaResultItem;
import com.example.ukk_lintang2021.Model.Spp.SppResultItem;
import com.example.ukk_lintang2021.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.1.3/praukk_lintang/";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if(retrofit == null){

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

    public static void show(Context c, String message){
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static void clearEditTexts(MaterialAutoCompleteTextView... materialAutoCompleteTextViews){
        for (MaterialAutoCompleteTextView materialAutoCompleteTextView:materialAutoCompleteTextViews) {
            materialAutoCompleteTextView.setText("");
        }
    }

    public static void openActivity(Context c,Class clazz){
        Intent intent = new Intent(c, clazz);
        c.startActivity(intent);
    }

    public static void showInfoDialog(final AppCompatActivity activity, String title,
                                      String message) {
        new LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.indigo)
                .setButtonsColorRes(R.color.darkDeepOrange)
                .setIcon(R.drawable.m_info)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Relax", v -> {})
                .setNegativeButton("Go Back", v -> activity.finish())
                .show();
    }

    public static Date giveMeDate(String stringDate){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT);
            return sdf.parse(stringDate);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void showProgressBar(ProgressBar pb){
        pb.setVisibility(View.VISIBLE);
    }

    public static void hideProgressBar(ProgressBar pb){
        pb.setVisibility(View.GONE);
    }


    public static boolean validate(MaterialAutoCompleteTextView... materialAutoCompleteTextViews){
        MaterialAutoCompleteTextView usernameTxt = materialAutoCompleteTextViews[0];
        MaterialAutoCompleteTextView passwordTxt = materialAutoCompleteTextViews[1];
        MaterialAutoCompleteTextView nama_petugasTxt = materialAutoCompleteTextViews[2];

        if(usernameTxt.getText() == null || usernameTxt.getText().toString().isEmpty()){
            usernameTxt.setError("Username is Required Please!");
            return false;
        }
        if(passwordTxt.getText() == null || passwordTxt.getText().toString().isEmpty()){
            passwordTxt.setError("Password is Required Please!");
            return false;
        }
        if(nama_petugasTxt.getText() == null || nama_petugasTxt.getText().toString().isEmpty()){
            nama_petugasTxt.setError("Nama petugas is Required Please!");
            return false;
        }
        return true;

    }

    public static boolean validatesiswa(MaterialAutoCompleteTextView... materialAutoCompleteTextViews){
        MaterialAutoCompleteTextView nisnTxt = materialAutoCompleteTextViews[0];
        MaterialAutoCompleteTextView nisTxt = materialAutoCompleteTextViews[1];
        MaterialAutoCompleteTextView namaTxt = materialAutoCompleteTextViews[2];
        MaterialAutoCompleteTextView idkelasTxt = materialAutoCompleteTextViews[3];
        MaterialAutoCompleteTextView alamatTxt = materialAutoCompleteTextViews[4];
        MaterialAutoCompleteTextView notelpTxt = materialAutoCompleteTextViews[5];
        MaterialAutoCompleteTextView idsppTxt = materialAutoCompleteTextViews[6];

        if(nisnTxt.getText() == null || nisnTxt.getText().toString().isEmpty()){
            nisnTxt.setError("Nisn is Required Please!");
            return false;
        }
        if(nisTxt.getText() == null || nisTxt.getText().toString().isEmpty()){
            nisTxt.setError("Nis is Required Please!");
            return false;
        }
        if(namaTxt.getText() == null || namaTxt.getText().toString().isEmpty()){
            namaTxt.setError("Nama is Required Please!");
            return false;
        }
        if(idkelasTxt.getText() == null || idkelasTxt.getText().toString().isEmpty()){
            idkelasTxt.setError("ID Kelas is Required Please!");
            return false;
        }
        if(alamatTxt.getText() == null || alamatTxt.getText().toString().isEmpty()){
            alamatTxt.setError("Alamat is Required Please!");
            return false;
        }
        if(notelpTxt.getText() == null || notelpTxt.getText().toString().isEmpty()){
            notelpTxt.setError("No. Telp is Required Please!");
            return false;
        }
        if(idsppTxt.getText() == null || idsppTxt.getText().toString().isEmpty()){
            idsppTxt.setError("ID Spp is Required Please!");
            return false;
        }
        return true;

    }

    public static boolean validatekelas(MaterialAutoCompleteTextView... materialAutoCompleteTextViews){
        MaterialAutoCompleteTextView nama_kelasTxt = materialAutoCompleteTextViews[0];
        MaterialAutoCompleteTextView kompetensi_keahlianTxt = materialAutoCompleteTextViews[1];

        if(nama_kelasTxt.getText() == null || nama_kelasTxt.getText().toString().isEmpty()){
            nama_kelasTxt.setError("Kelas is Required Please!");
            return false;
        }
        if(kompetensi_keahlianTxt.getText() == null || kompetensi_keahlianTxt.getText().toString().isEmpty()){
            kompetensi_keahlianTxt.setError("Kompetensi Keahlian is Required Please!");
            return false;
        }
        return true;

    }

    public static boolean validatepembayaran(MaterialAutoCompleteTextView... materialAutoCompleteTextViews){

        MaterialAutoCompleteTextView id_siswaTxt = materialAutoCompleteTextViews[1];
        MaterialAutoCompleteTextView id_tanggalTxt = materialAutoCompleteTextViews[2];
        MaterialAutoCompleteTextView id_bulanTxt = materialAutoCompleteTextViews[3];
        MaterialAutoCompleteTextView id_tahunTxt = materialAutoCompleteTextViews[4];
        MaterialAutoCompleteTextView id_sppTxt = materialAutoCompleteTextViews[5];
        MaterialAutoCompleteTextView nominalTxt = materialAutoCompleteTextViews[6];

        if(id_siswaTxt.getText() == null || id_siswaTxt.getText().toString().isEmpty()){
            id_siswaTxt.setError("ID Siswa is Required Please!");
            return false;
        }

        if(id_tanggalTxt.getText() == null || id_tanggalTxt.getText().toString().isEmpty()){
            id_tanggalTxt.setError("Tanggal is Required Please!");
            return false;
        }

        if(id_bulanTxt.getText() == null || id_bulanTxt.getText().toString().isEmpty()){
            id_bulanTxt.setError("Bulan is Required Please!");
            return false;
        }

        if(id_tahunTxt.getText() == null || id_tahunTxt.getText().toString().isEmpty()){
            id_tahunTxt.setError("Tahun is Required Please!");
            return false;
        }

        if(id_sppTxt.getText() == null || id_sppTxt.getText().toString().isEmpty()){
            id_sppTxt.setError("ID SPP is Required Please!");
            return false;
        }

        if(nominalTxt.getText() == null || nominalTxt.getText().toString().isEmpty()){
            nominalTxt.setError("Nominal is Required Please!");
            return false;
        }
        return true;

    }

    public static boolean validatespp(MaterialAutoCompleteTextView... materialAutoCompleteTextViews){
        MaterialAutoCompleteTextView tahunTxt = materialAutoCompleteTextViews[0];
        MaterialAutoCompleteTextView nominalTxt = materialAutoCompleteTextViews[1];

        if(tahunTxt.getText() == null || tahunTxt.getText().toString().isEmpty()){
            tahunTxt.setError("Tahun is Required Please!");
            return false;
        }
        if(nominalTxt.getText() == null || nominalTxt.getText().toString().isEmpty()){
            nominalTxt.setError("Nominal is Required Please!");
            return false;
        }
        return true;

    }


    public static void selectrole(Context c,final EditText editText){
        String[] role ={"Admin","Petugas","Siswa"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                role);
        new LovelyChoiceDialog(c)
                .setTopColorRes(R.color.darkGreen)
                .setTitle("Pilih Level")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("Select the level.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> editText.setText(item))
                .show();
    }

    public static void selectlevel(Context c,final MaterialAutoCompleteTextView materialAutoCompleteTextView){
        String[] stars ={"Admin","Petugas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                stars);
        new LovelyChoiceDialog(c)
                .setTopColorRes(R.color.darkGreen)
                .setTitle("Pilih Level")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("Select the level.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> materialAutoCompleteTextView.setText(item))
                .show();
    }

    public static void selectkelas(Context c,final MaterialAutoCompleteTextView materialAutoCompleteTextView){
        String[] stars ={"Rekayasa perangkat lunak","Multimedia","Teknik komputer dan jaringan", "Bisnis daring dan pemasaran", "Otomatisasi tata kelola dan perkantoran"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                stars);
        new LovelyChoiceDialog(c)
                .setTopColorRes(R.color.darkGreen)
                .setTitle("Pilih Kelas")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("Select the skill competence.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> materialAutoCompleteTextView.setText(item))
                .show();
    }

    public static void select_bulan(Context c,final MaterialAutoCompleteTextView materialAutoCompleteTextView){
        String[] bulan ={"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                bulan);
        new LovelyChoiceDialog(c)
                .setTopColorRes(R.color.darkGreen)
                .setTitle("Pilih Tahun")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("Select year.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> materialAutoCompleteTextView.setText(item))
                .show();
    }

    public static void select_tahun(Context c,final MaterialAutoCompleteTextView materialAutoCompleteTextView){
        String[] tahun ={"2020","2021","2022","2023","2024","2025"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
                android.R.layout.simple_list_item_1,
                tahun);
        new LovelyChoiceDialog(c)
                .setTopColorRes(R.color.darkGreen)
                .setTitle("Pilih Tahun")
                .setTitleGravity(Gravity.CENTER_HORIZONTAL)
                .setIcon(R.drawable.person)
                .setMessage("Select year.")
                .setMessageGravity(Gravity.CENTER_HORIZONTAL)
                .setItems(adapter, (position, item) -> materialAutoCompleteTextView.setText(item))
                .show();
    }

    public static void sendPetugasToActivity(Context c, PetugasResultItem petugasResultItem,
                                             Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("Petugas Data", petugasResultItem);
        c.startActivity(i);
    }

    public  static PetugasResultItem receivePetugas(Intent intent, Context c){
        try {
            PetugasResultItem petugasResultItem= (PetugasResultItem) intent.getSerializableExtra("Petugas Data");
            return petugasResultItem;
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-PETUGAS ERROR: "+e.getMessage());
        }
        return null;
    }

    public static void sendSiswaToActivity(Context c, SiswaResultItem siswaResultItem,
                                           Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("Siswa Data", siswaResultItem);
        c.startActivity(i);
    }

    public  static SiswaResultItem receiveSiswa(Intent intent, Context c){
        try {
            SiswaResultItem siswaResultItem = (SiswaResultItem) intent.getSerializableExtra("Siswa Data");
            return siswaResultItem;
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-SISWA ERROR: "+e.getMessage());
        }
        return null;
    }

    public static void sendKelasToActivity(Context c, KelasResultItem kelasResultItem,
                                               Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("Kelas Data", kelasResultItem);
        c.startActivity(i);
    }

    public  static KelasResultItem receiveKelas(Intent intent,Context c){
        try {
            KelasResultItem kelasResultItem= (KelasResultItem) intent.getSerializableExtra("Kelas Data");
            return kelasResultItem;
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-KELAS ERROR: "+e.getMessage());
        }
        return null;
    }

    public static void sendSppToActivity(Context c, SppResultItem sppResultItem,
                                           Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("Spp Data", sppResultItem);
        c.startActivity(i);
    }

    public  static SppResultItem receiveSpp(Intent intent,Context c){
        try {
            SppResultItem sppResultItem = (SppResultItem) intent.getSerializableExtra("Spp Data");
            return sppResultItem;
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-SPP ERROR: "+e.getMessage());
        }
        return null;
    }


    public static void sendPembayaranToActivity(Context c, PembayaranResultItem pembayaranResultItem,
                                         Class clazz){
        Intent i=new Intent(c,clazz);
        i.putExtra("Pembayaran Data", pembayaranResultItem);
        c.startActivity(i);
    }

    public  static PembayaranResultItem receivePembayaran(Intent intent,Context c){
        try {
            PembayaranResultItem pembayaranResultItem= (PembayaranResultItem) intent.getSerializableExtra("Pembayaran Data");
            return pembayaranResultItem;
        }catch (Exception e){
            e.printStackTrace();
            show(c,"RECEIVING-PEMBAYARAN ERROR: "+e.getMessage());
        }
        return null;
    }
}
//end
