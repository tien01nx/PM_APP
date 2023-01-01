package com.nvm.imapp.home;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.nvm.imapp.R;
import com.nvm.imapp.model.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class SellActivity extends AppCompatActivity {
    ImageView imV;
    EditText etCodeX, etNguoiNhan, etSLX, etDonGiaX,edTime, edDate,edKey, etSdtNguoiNhan;
    Button btnSell;
    ImageButton imgScanX;
    AGConnectUser user;
    String userID;
    int random;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    private String codeX;
    private String SLX;
    private String donGiaX;
    private String NguoiNhan;
    private String SDTNN;

    private static final int DEFAULT_VIEW = 111;
    private static final int REQUEST_CODE_SCAN = 0X01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        user = AGConnectAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        BtnSellClick();
//        BacktoHome();
        getViews();

        //         Ẩn 3 edit text
        edTime.setVisibility(View.GONE);
        edDate.setVisibility(View.GONE);
        edKey.setVisibility(View.GONE);

        imV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeX = etCodeX.getText().toString();
                SLX = etSLX.getText().toString();
                NguoiNhan = etNguoiNhan.getText().toString();
                donGiaX = etDonGiaX.getText().toString();
                SDTNN=etSdtNguoiNhan.getText().toString();

                //Kiểm tra dữ liệu
                if (codeX.isEmpty() || SLX.isEmpty() || donGiaX.isEmpty() || NguoiNhan.isEmpty() ||SDTNN.isEmpty()) {
                    Toast.makeText(SellActivity.this, "Không được để trống!", Toast.LENGTH_LONG).show();
                }else {
                    //Lấy ngày giờ
                    Calendar calendar = Calendar.getInstance();

                    SimpleDateFormat TimeP = new SimpleDateFormat("HH:mm:ss");
                    edTime.setText(TimeP.format(calendar.getTime()));
                    SimpleDateFormat DateP = new SimpleDateFormat("E dd/MM/yyyy");
                    edDate.setText(DateP.format(calendar.getTime()));
                    edKey.setText(calendar.getTime()+"");

                    String id= etCodeX.getText().toString();
                    QueryIDProduct("ProductAvailable",id);

                    //đẩy dữ liệu lên fire base

//                    reference.child(userID).child("HistorySell").child(edKey.getText().toString()).setValue(getProductInfo());
////                    reference.child(userID).child("History").push().setValue(getProductInfo());
                }
            }
        });

        imgScanX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                intentIntegrator.initiateScan();
                int result = ScanUtil.startScan(SellActivity.this, REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create());
            }
        });
    }

//    private void BacktoHome() {
//        imV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }

    private void getViews(){
        etCodeX =findViewById(R.id.etCodeX);
        etSLX =findViewById(R.id.etSLX);
        etNguoiNhan =findViewById(R.id.etNguoiNhan);
        etDonGiaX =findViewById(R.id.etDonGiaX);
        btnSell =findViewById(R.id.btnSell);
        imgScanX =findViewById(R.id.imgScanX);
        imV =findViewById(R.id.backX);
        edTime=findViewById(R.id.edTime);
        edDate=findViewById(R.id.edDate);
        edKey=findViewById(R.id.edKey);
        etSdtNguoiNhan= findViewById(R.id.etSdtNguoiNhan);
    }

    public void QueryIDProduct(String add, String id){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(userID).child(add).child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Product product = snapshot.getValue(Product.class);
                            String slx= etSLX.getText().toString();

                            String name= product.getEtName();
                            String kl= product.getEtKL();
                            String hangsx= product.getEtHangSX();
                            String madein= product.getEtMadeIn();
                            String code= etCodeX.getText().toString();
                            String sl= etSLX.getText().toString();
                            String dongia= etDonGiaX.getText().toString();
                            String time= edTime.getText().toString();
                            String dateP= edDate.getText().toString();
                            String nguoinhan= etNguoiNhan.getText().toString();
                            String sdtNN= etSdtNguoiNhan.getText().toString();
                            int slm= Integer.valueOf(slx);
                            int slcu= Integer.valueOf(product.getEtSL());
                            int dg= Integer.valueOf(dongia);
                            random = new Random().nextInt(899999999) + 100000000; // [0, 60] + 20 => [20, 80]
                            if(slcu>=slm) {
                                product.setEtSL("" + (slcu - slm));
                                int slmoinhat=slcu-slm;

                                int Thanhtien= dg*slm;

                                Product product1=new Product(name, code, kl,sl,dongia,hangsx,madein,time,dateP,nguoinhan,Thanhtien,sdtNN, random);
                                //Cap nhat so luong moi
                                reference.child(userID).child("ProductAvailable").child(etCodeX.getText().toString()).setValue(product);
                                reference.child(userID).child("HistorySell").child(String.valueOf(random)).setValue(product1);
                                Toast.makeText(SellActivity.this, "Xuất sản phẩm thành công !", Toast.LENGTH_SHORT).show();
                                if(slmoinhat==0){
                                    reference.child(userID).child("ProductAvailable").child(etCodeX.getText().toString()).removeValue();
                                }
                                Intent intent = new Intent(SellActivity.this, SellHistoryActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(SellActivity.this,"Số lượng bạn đã xuất vượt quá số lượng có sẵn !!!", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SellActivity.this,"Không tìm thấy sản phẩm theo id này, vui lòng nhập lại id !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }



    public void BtnSellClick() {
// Replace DEFAULT_VIEW with the code that you customize for receiving the permission verification result.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    DEFAULT_VIEW);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions == null || grantResults == null || grantResults.length < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Process the result after the scanning is complete.
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        // Use ScanUtil.RESULT as the key value to obtain the return value of HmsScan from data returned by the onActivityResult method.
        if (requestCode == REQUEST_CODE_SCAN) {
//            Object obj = data.getParcelableExtra(ScanUtil.RESULT);
            HmsScan obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj instanceof HmsScan) {
                if (!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())) {
                    Toast.makeText(this, ((HmsScan) obj).getOriginalValue(),
                            Toast.LENGTH_SHORT).show();
                    try{
                        JSONObject jsonObject= new JSONObject(obj.getOriginalValue());
                        etCodeX.setText(jsonObject.getString("codex"));
                        etSLX.setText(jsonObject.getString("slx"));
                        etDonGiaX.setText(jsonObject.getString("dongiax"));
                        etNguoiNhan.setText(jsonObject.getString("nguoinhan"));

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                return;
            }
        }

    }
}