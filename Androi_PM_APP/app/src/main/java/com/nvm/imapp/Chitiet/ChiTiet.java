package com.nvm.imapp.Chitiet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nvm.imapp.R;
import com.nvm.imapp.model.Product;

public class ChiTiet extends AppCompatActivity {

    TextView name, code, KL, Sl, HangSX, MadeIn,DonGia, sdt;
    Product product;
    ImageView imgBackC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        getLinkViews();

        getDataProduct();

        setDataViews();

        getControls();

        //// gọi điện//////
        sdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone= product.getSdt();
                //su dung intent khong tuong minh de thuc hien cuoc goi
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                //start activity de thuc hien cuoc goi
                startActivity(intent);
            }
        });

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
        code.setText(product.getEtCode());
        name.setText(product.getEtName());
        KL.setText(product.getEtKL());
//        if (staff.getGender()){
//            gerden.setText("Man");
//        }else {
//            gerden.setText("Woman");
//        }
        Sl.setText(product.getEtSL());
        HangSX.setText(product.getEtHangSX());
        MadeIn.setText(product.getEtMadeIn());
        DonGia.setText(String.valueOf(product.getEtDonGia()));
        sdt.setText(product.getSdt());

    }

    private void getDataProduct() {
        if (getIntent().hasExtra("pr")){
            product = (Product) getIntent().getSerializableExtra("pr");
        }else {
            Toast.makeText(ChiTiet.this,"No Data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLinkViews() {
        product = new Product();
        code = findViewById(R.id.txt_codeP);
        name = findViewById(R.id.txt_nameP);
        KL = findViewById(R.id.txt_KLP);
        Sl = findViewById(R.id.txt_SLP);
        HangSX = findViewById(R.id.txt_HangP);
        MadeIn= findViewById(R.id.txt_MadeInP);
        DonGia = findViewById(R.id.txt_DonGiaP);
        imgBackC = findViewById(R.id.backC);
        sdt = findViewById(R.id.txt_sdt);

    }
}