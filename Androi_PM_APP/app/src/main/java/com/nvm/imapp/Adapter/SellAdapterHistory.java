package com.nvm.imapp.Adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;
import com.nvm.imapp.Chitiet.ChiTietHistoryActivity;
import com.nvm.imapp.Chitiet.ChiTietSell;
import com.nvm.imapp.R;
import com.nvm.imapp.model.Product;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SellAdapterHistory extends RecyclerView.Adapter<SellAdapterHistory.MyViewHoler> {

    List<Product> sanphamH = new ArrayList<>();
    Context context;

    AGConnectUser user;
    String userID;

    public void addListProductH(List<Product> ListProduct) {
        sanphamH.clear();
        sanphamH.addAll(ListProduct);
        notifyDataSetChanged();
    }
    public SellAdapterHistory(Context context) {
        this.context = context;
    }

    @Override //1
    public SellAdapterHistory.MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View p = inflater.inflate(R.layout.activity_sell_adapter_history,parent,false);

        return new SellAdapterHistory.MyViewHoler(p);
    }
    @NonNull
    @NotNull

    @Override //3
    public void onBindViewHolder(SellAdapterHistory.MyViewHoler holder, int position) {
        Product pdH = sanphamH.get(position);
//        holder.imageNV.setImageBitmap(new ImageConvert().toBitmap(nv.getPhoto()));

        holder.txtName.setText(pdH.getEtName());
        holder.txtCode.setText(pdH.getEtCode());
        holder.txtTime.setText(pdH.getEdTime());
        holder.txtDate.setText(pdH.getEdDate());
        holder.txtTongThanhTienBan.setText(String.valueOf(pdH.getThanhtien()));
        holder.txtSoLuongMua.setText(pdH.getEtSL());
        holder.itemH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSell.class);
                intent.putExtra("productS",pdH);
                context.startActivity(intent);
            }
        });

        //////////////////////////////////////////////////////////////////////////////
        holder.delesell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int code3 =pdH.getKey();
                DialogDelete(code3);
            }
        });
        //////////////////////////////////////////////////////////////////////////////
    }

    @Override //4
    public int getItemCount() {
        return sanphamH.size();
    }


    // 2
    public class MyViewHoler extends RecyclerView.ViewHolder {
        LinearLayout itemH;
        TextView txtName,txtCode, txtSoLuong, txtKL, txtMadeIn, txtDonGia, txtHangSX,txtTime,txtDate,txtTongThanhTienBan,txtSoLuongMua;
        //////////////////////////////////////////////////////////////////////////////
        Button delesell;
        //////////////////////////////////////////////////////////////////////////////
        public MyViewHoler(View itemViewH) {
            super(itemViewH);

            itemH = itemViewH.findViewById(R.id.ProductS);
            txtName = itemViewH.findViewById(R.id.txtName);
            txtCode=itemViewH.findViewById(R.id.txtCode);
            txtSoLuong=itemViewH.findViewById(R.id.txtSoLuong);
            txtKL = itemViewH.findViewById(R.id.etKL);
            txtMadeIn=itemViewH.findViewById(R.id.etMadeIn);
            txtDonGia=itemViewH.findViewById(R.id.etDonGia);
            txtHangSX = itemViewH.findViewById(R.id.etHangSx);
            txtTime =itemViewH.findViewById(R.id.enterTime);
            txtDate = itemViewH.findViewById(R.id.enterDate);
            txtTongThanhTienBan=itemViewH.findViewById(R.id.txtTongThanhTienBan);
            txtSoLuongMua=itemViewH.findViewById(R.id.txtSoLuongMua);
            ////////////////////////////////////////////////////
            delesell=itemViewH.findViewById(R.id.deletesell);
            //////////////////////////////////////////////////////////////////////////////
        }
    }
    //////////////////////////////////////////////////////////////////////////////
    private void DialogDelete(int code) {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete);
        dialog.setCanceledOnTouchOutside(false);

        //ánh xạ
        Button btnXoa = dialog.findViewById(R.id.btnXoa);
        Button btnThoat = dialog.findViewById(R.id.btnThoat);

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference();
                user = AGConnectAuth.getInstance().getCurrentUser();
                userID = user.getUid();
                reference.child(userID).child("HistorySell").child(String.valueOf(code)).removeValue();
                dialog.dismiss();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    ////////////////////////////////////////////////////
}
