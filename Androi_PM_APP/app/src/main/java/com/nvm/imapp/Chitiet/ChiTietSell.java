package com.nvm.imapp.Chitiet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nvm.imapp.R;
import com.nvm.imapp.model.Product;

public class ChiTietSell extends AppCompatActivity {

    TextView name, code, KL, Sl, HangSX, MadeIn,DonGia,Time,Date,Nguoinhan,Thanhtien;
    Product productS;
    ImageView imgBackC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sell);

        getLinkViews();

        getDataProduct();

        setDataViews();

        getControls();

    }

    private void getControls() {
        imgBackC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setDataViews() {
        code.setText(productS.getEtCode());
        name.setText(productS.getEtName());
        KL.setText(productS.getEtKL());
        Sl.setText(productS.getEtSL());
        HangSX.setText(productS.getEtHangSX());
        MadeIn.setText(productS.getEtMadeIn());
        Time.setText(productS.getEdTime());
        Date.setText(productS.getEdDate());
        Nguoinhan.setText(productS.getNguoinhan());
        Thanhtien.setText(String.valueOf(productS.getThanhtien()));
        DonGia.setText(String.valueOf(productS.getEtDonGia()));

    }

    private void getDataProduct() {
        if (getIntent().hasExtra("productS")){
            productS = (Product) getIntent().getSerializableExtra("productS");
        }else {
            Toast.makeText(ChiTietSell.this,"No Data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLinkViews() {
        productS = new Product();
        code = findViewById(R.id.txt_codeH);
        name = findViewById(R.id.txt_nameH);
        KL = findViewById(R.id.txt_KLH);
        Sl = findViewById(R.id.txt_SLH);
        HangSX = findViewById(R.id.txt_HangH);
        MadeIn= findViewById(R.id.txt_MadeInH);
        DonGia = findViewById(R.id.txt_DonGiaH);
        imgBackC = findViewById(R.id.backH);
        Time =findViewById(R.id.txt_TimeH);
        Date =findViewById(R.id.txt_DateH);
        Nguoinhan=findViewById(R.id.txt_nguoinhan);
        Thanhtien=findViewById(R.id.txt_TTX);

    }
}