package com.nvm.imapp.Chitiet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nvm.imapp.R;
import com.nvm.imapp.model.Product;

public class ChiTietHistoryActivity extends AppCompatActivity {

    TextView name, code, KL, Sl, HangSX, MadeIn,DonGia,Time,Date,Thanhtien;
    Product productH;
    ImageView imgBackC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_history);

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
        code.setText(productH.getEtCode());
        name.setText(productH.getEtName());
        KL.setText(productH.getEtKL());
        Sl.setText(productH.getEtSL());
        HangSX.setText(productH.getEtHangSX());
        MadeIn.setText(productH.getEtMadeIn());
        Time.setText(productH.getEdTime());
        Date.setText(productH.getEdDate());
        Thanhtien.setText(String.valueOf(productH.getThanhtien()));
        DonGia.setText(String.valueOf(productH.getEtDonGia()));

    }

    private void getDataProduct() {
        if (getIntent().hasExtra("productH")){
            productH = (Product) getIntent().getSerializableExtra("productH");
        }else {
            Toast.makeText(ChiTietHistoryActivity.this,"No Data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLinkViews() {
        productH = new Product();
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
        Thanhtien=findViewById(R.id.txt_TTH);

    }
}