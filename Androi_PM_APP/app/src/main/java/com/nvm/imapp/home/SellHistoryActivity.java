package com.nvm.imapp.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;
import com.nvm.imapp.Adapter.ProductHistoryActivity;
import com.nvm.imapp.Adapter.SellAdapterHistory;
import com.nvm.imapp.R;
import com.nvm.imapp.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SellHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerViewH;
    List<Product> listH;
    SellAdapterHistory productAdapterH;
    ImageView imageViewH;
    AGConnectUser user;
    String userID;
    int li;
    TextView tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_history);
        user = AGConnectAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        getLinkViews();
        getDatabase();
        getControls();

    }

    private void getControls() {
        imageViewH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getLinkViews() {
        listH = new ArrayList<>();
        imageViewH = findViewById(R.id.img_backH);
        recyclerViewH = findViewById(R.id.recyclerviewS);
        productAdapterH = new SellAdapterHistory(SellHistoryActivity.this);
        recyclerViewH.setAdapter(productAdapterH);
        tb=findViewById(R.id.tb);
        recyclerViewH.setLayoutManager(new LinearLayoutManager(SellHistoryActivity.this));
    }

    private void checkSp(){
        if(li!=0){
            tb.setVisibility(View.GONE);
            recyclerViewH.setVisibility(View.VISIBLE);
        }else {
            tb.setVisibility(View.VISIBLE);
            recyclerViewH.setVisibility(View.GONE);
        }
    }

    private void getDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(userID).child("HistorySell");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snaspshot) {
                for (DataSnapshot dataSnapshot : snaspshot.getChildren()) {
                    Product pdH = dataSnapshot.getValue(Product.class);
                    listH.add(pdH);
                    li=listH.size();
                    checkSp();
                }
                Collections.reverse(listH);
                productAdapterH.addListProductH(listH);
                listH.clear();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivityAdmin", "Tải dữ liệu lỗi : " + error.toString());
            }
        });
    }
}