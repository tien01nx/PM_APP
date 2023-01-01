package com.nvm.imapp.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;
import com.nvm.imapp.Chitiet.ChiTiet;
import com.nvm.imapp.R;
import com.nvm.imapp.model.Product;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHoler> implements Filterable {

    List<Product> sanpham = new ArrayList<>();
    List<Product> sanphamold = new ArrayList<>();
    Context context;

    AGConnectUser user;
    String userID;


    public void addListProduct(List<Product> ListProduct) {
        sanpham.clear();
        sanpham.addAll(ListProduct);
        sanphamold.addAll(ListProduct);
        notifyDataSetChanged();
    }

    public ProductAdapter(Context context) {
        this.context = context;
    }

    @Override //1
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_product_adapter,parent,false);

        return new MyViewHoler(view);
    }
    @NonNull
    @NotNull

    @Override //3
    public void onBindViewHolder(MyViewHoler holder, int position) {
          Product pd = sanpham.get(position);
//        holder.imageNV.setImageBitmap(new ImageConvert().toBitmap(nv.getPhoto()));

        holder.txtName.setText(pd.getEtName());
        holder.txtCode.setText(pd.getEtCode());
        holder.txtSoLuong.setText(pd.getEtSL());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTiet.class);
                intent.putExtra("pr",pd);
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code1 = pd.getEtCode();
                DialogDelete(code1);
            }
        });
    }

    @Override //4
    public int getItemCount() {
        return sanpham.size();
    }


    // 2
    public class MyViewHoler extends RecyclerView.ViewHolder {
        LinearLayout item;
        TextView txtName,txtCode, txtSoLuong, txtKL, txtMadeIn, txtDonGia, txtHangSX, txtSDT;
        Button delete;
        public MyViewHoler(View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.Product);
            txtName = itemView.findViewById(R.id.txtName);
            txtCode=itemView.findViewById(R.id.txtCode);
            txtSoLuong=itemView.findViewById(R.id.txtSoLuong);
            txtKL = itemView.findViewById(R.id.etKL);
            txtMadeIn=itemView.findViewById(R.id.etMadeIn);
            txtDonGia=itemView.findViewById(R.id.etDonGia);
            txtHangSX = itemView.findViewById(R.id.etHangSx);
            txtSDT =itemView.findViewById(R.id.txt_sdt);
            delete=itemView.findViewById(R.id.delete);

        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    sanpham = sanphamold;
                }else {
                    List<Product> list = new ArrayList<>();
                    for (Product product: sanphamold){
                        if (product.getEtName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(product);
                        }
                        if (product.getEtCode().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(product);
                        }
//                        if (product.getEtHangSX().toLowerCase().contains(strSearch.toLowerCase())){
//                            list.add(product);
//                        }
//                        if (product.getEtMadeIn().toLowerCase().contains(strSearch.toLowerCase())){
//                            list.add(product);
//                        }
//                        if (product.getEtDonGia().toLowerCase().contains(strSearch.toLowerCase())){
//                            list.add(product);
//                        }
                        if (product.getEtSL().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(product);
                        }
                    }
                    sanpham = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = sanpham;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sanpham = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    private void DialogDelete(String code) {

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

                reference.child(userID).child("ProductAvailable").child(code).removeValue();
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
}
