package com.nvm.imapp.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;
import com.nvm.imapp.Adapter.ProductAdapter;
import com.nvm.imapp.R;
import com.nvm.imapp.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductAvailableActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Product> list;
    ProductAdapter productAdapter;
    ImageView imageView;
    AGConnectUser user;
    String userID;
    EditText edtSearch, edtND;
    TextView tb;
    Button delete;
    String ID;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    int li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_available);
        user = AGConnectAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        getLinkViews();
        getDatabase();
        getControls();
        TimKiem();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase fb = FirebaseDatabase.getInstance();
                DatabaseReference db = fb.getReference(userID).child("ProductAvailable");

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snaspshot) {
                        //Product nv = snaspshot.getValue(Product.class);
                        for (DataSnapshot dataSnapshot : snaspshot.getChildren()) {
                            Product pdd = dataSnapshot.getValue(Product.class);
                            if(pdd.getEtMadeIn().equals(edtND.getText().toString())){
//                            if(Integer.valueOf(pd.getEtSL())>Integer.valueOf(edtND.getText().toString()))
                                String code= pdd.getEtCode();
                                reference.child(userID).child("ProductAvailable").child(code).removeValue();
                                Toast.makeText(ProductAvailableActivity.this, "Good chóp", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    private void getControls() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getLinkViews() {
        list = new ArrayList<>();
        delete =findViewById(R.id.delete);
        imageView = findViewById(R.id.img_back);
        edtSearch = findViewById(R.id.edt_search);
        tb=findViewById(R.id.tb);
        recyclerView = findViewById(R.id.recyclerview);
        productAdapter = new ProductAdapter(ProductAvailableActivity.this);
        recyclerView.setAdapter(productAdapter);
        edtND= findViewById(R.id.edtND);
        ID = AGConnectAuth.getInstance().getCurrentUser().getUid();
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductAvailableActivity.this));
    }

    private void getDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(userID).child("ProductAvailable");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snaspshot) {
                //Product nv = snaspshot.getValue(Product.class);
                for (DataSnapshot dataSnapshot : snaspshot.getChildren()) {
                    Product pd = dataSnapshot.getValue(Product.class);
                    list.add(pd);
                    li=list.size();
                    checkSp();
                }
                Collections.reverse(list);
                productAdapter.addListProduct(list);
                list.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivityAdmin", "Tải dữ liệu lỗi : " + error.toString());
            }
        });
    }
    private void checkSp(){
        if(li!=0){
            tb.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            tb.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
    private void TimKiem() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                productAdapter.getFilter().filter(s.toString());
            }
        });
    }
}