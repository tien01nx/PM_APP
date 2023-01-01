package com.nvm.imapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.nvm.imapp.R;
import com.nvm.imapp.home.AddActivity;
import com.nvm.imapp.home.HistoryAddActivity;
import com.nvm.imapp.home.ProductAvailableActivity;
import com.nvm.imapp.home.SellActivity;
import com.nvm.imapp.home.SellHistoryActivity;
import com.nvm.imapp.login.LoginActivity;
import com.nvm.imapp.login.PrivacyPolicyActivity;
import com.nvm.imapp.model.Contact;

public class HomeFragment extends Fragment {
    private void getViews(View root){
        imButtonAdd= root.findViewById(R.id.imButtonAdd);
        imgBtnPV= root.findViewById(R.id.imgBtnPA);
        imgBTSell= root.findViewById(R.id.imgBTSell);
        imgHistoryAdd= root.findViewById(R.id.imgHistoryAdd);
        imgSellHistory=root.findViewById(R.id.imgSellHistory);
        ID = AGConnectAuth.getInstance().getCurrentUser().getUid();
        txtEmail = root.findViewById(R.id.txtEmail);
        txtNameUser = root.findViewById(R.id.txtNameUser);

    }
    ImageButton imButtonAdd, imgBtnPV, imgBTSell,imgHistoryAdd, imgSellHistory;
    String ID;
    TextView txtNameUser, txtEmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        getViews(root);
        Bundle bundle= getArguments();
        if(bundle != null){
            txtNameUser.setText(bundle.getString("newname"));
        }
        readFB("users", ID);
        imButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(getActivity().getApplication(), AddActivity.class);
                startActivity(itent);
            }
        });
        imgBtnPV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ient = new Intent(getActivity().getApplication(), ProductAvailableActivity.class);
                startActivity(ient);
            }
        });

        imgBTSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent innt = new Intent(getActivity().getApplication(), SellActivity.class);
                startActivity(innt);
            }
        });

        imgHistoryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent innntent = new Intent(getActivity().getApplication(), HistoryAddActivity.class);
                startActivity(innntent);
            }
        });
        imgSellHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itttt = new Intent(getActivity().getApplication(), SellHistoryActivity.class);
                startActivity(itttt);
            }
        });

        return root;

    }
    public void readFB(String DB, String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(DB).child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Contact contact = snapshot.getValue(Contact.class);
                            txtEmail.setText(contact.getEmail());
                            txtNameUser.setText(contact.getFullName());
                        } else {
                            Toast.makeText(getContext(), "Tải dữ liệu lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(getContext(), "Tải dữ liệu lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}