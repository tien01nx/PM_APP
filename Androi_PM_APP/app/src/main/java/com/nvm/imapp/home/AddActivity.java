package com.nvm.imapp.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsBuildBitmapOption;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.nvm.imapp.MainActivity;
import com.nvm.imapp.R;
import com.nvm.imapp.model.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class AddActivity extends AppCompatActivity {
    EditText etName, etCode, etKL, etMadeIn, etHangSX, etDonGia, etSL, edTime, edDate, edKey, etSdt;
    Button btnAdd, btnScan;
    ImageView imgView;

    int random;


    AGConnectUser user;
    String userID;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();


    private String nameP;
    private String codeP;
    private String KLP;
    private String HangSXP;
    private String madeInP;
    private String donGiaP;
    private String soLuongP;
    private String SDT;
    private static final int DEFAULT_VIEW = 111;
    private static final int REQUEST_CODE_SCAN = 0X01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        user = AGConnectAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        getViews();
        getControls();
        newViewBtnClick();

//         Ẩn 3 edit text
        edTime.setVisibility(View.GONE);
        edDate.setVisibility(View.GONE);
        edKey.setVisibility(View.GONE);

//        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                intentIntegrator.initiateScan();
                int result = ScanUtil.startScan(AddActivity.this, REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create());
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameP = etName.getText().toString();
                codeP = etCode.getText().toString();
                KLP = etKL.getText().toString();
                soLuongP = etSL.getText().toString();
                madeInP = etMadeIn.getText().toString();
                HangSXP = etHangSX.getText().toString();
                donGiaP = etDonGia.getText().toString();
                SDT=etSdt.getText().toString().trim();
                if (nameP.isEmpty() || codeP.isEmpty() || KLP.isEmpty() || soLuongP.isEmpty() || donGiaP.isEmpty() || madeInP.isEmpty() || HangSXP.isEmpty() || SDT.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Không được để trống", Toast.LENGTH_LONG).show();
                }
                /////// check sl nhập//////////
                else if(Integer.valueOf(soLuongP)<=0)
                {
                    Toast.makeText(AddActivity.this, "Nhập số lượng >0", Toast.LENGTH_SHORT).show();
                }
                ///////////////////////////////
                else {
                    //Lấy ngày giờ
                    Calendar calendar = Calendar.getInstance();

                    SimpleDateFormat TimeP = new SimpleDateFormat("HH:mm:ss");
                    edTime.setText(TimeP.format(calendar.getTime()));
                    SimpleDateFormat DateP = new SimpleDateFormat("E dd/MM/yyyy");
                    edDate.setText(DateP.format(calendar.getTime()));
                    random = new Random().nextInt(899999999) + 100000000; // [0, 60] + 20 => [20, 80]

                    //đẩy dữ liệu lên fire base

                    reference.child(userID).child("History").child(String.valueOf(random)).setValue(getProductInfo());
//                    reference.child(userID).child("History").push().setValue(getProductInfo());

                    String id = etCode.getText().toString();
                    QueryIDProduct("ProductAvailable", id);
                    Intent intent = new Intent(AddActivity.this, ProductAvailableActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    private void getControls() {
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void getViews() {
        etName = findViewById(R.id.etName);
        etCode = findViewById(R.id.etCode);
        etKL = findViewById(R.id.etKL);
        etMadeIn = findViewById(R.id.etMadeIn);
        etHangSX = findViewById(R.id.etHangSx);
        etDonGia = findViewById(R.id.etDonGia);
        etSL = findViewById(R.id.etSl);
        btnAdd = findViewById(R.id.btnAdd);
        btnScan = findViewById(R.id.btnScan);
        edTime = findViewById(R.id.edTime);
        edDate = findViewById(R.id.edDate);
        imgView = findViewById(R.id.back);
        edKey = findViewById(R.id.edKey);
        etSdt = findViewById(R.id.etSdt);
    }

    private Product getProductInfo() {
        String name = etName.getText().toString();
        String code = etCode.getText().toString();
        String kl = etKL.getText().toString();
        String sl = etSL.getText().toString();
        String dongia = etDonGia.getText().toString();
        String hangsx = etHangSX.getText().toString();
        String madein = etMadeIn.getText().toString();
        String time = edTime.getText().toString();
        String dateP = edDate.getText().toString();
        String sdtNB = etSdt.getText().toString();
        int t = Integer.valueOf(sl);
        int dg = Integer.valueOf(dongia);
        int thanhTien = t * dg;


        return new Product(name, code, kl, sl, dongia, hangsx, madein, time, dateP, thanhTien,sdtNB, random);
    }

// check sản phẩm đã có dòng 200
    public void QueryIDProduct(String add, String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(userID).child(add).child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Product product = snapshot.getValue(Product.class);
                            String sl = etSL.getText().toString();
                            String dongia = etDonGia.getText().toString();
                            int t = Integer.valueOf(sl);
                            int slcu = Integer.valueOf(product.getEtSL());
                            int dg = Integer.valueOf(dongia);
                            int thanhTien = t * dg;

                            product.setEtSL("" + (t + slcu));

                            //Cap nhat so luong moi
                            reference.child(userID).child("ProductAvailable").child(etCode.getText().toString()).setValue(product);
                            Toast.makeText(AddActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_LONG).show();
                        } else {
                            reference.child(userID).child("ProductAvailable").child(etCode.getText().toString()).setValue(getProductInfo());
                            Toast.makeText(AddActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
    }


    public void newViewBtnClick() {
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
                    try {
                        JSONObject jsonObject = new JSONObject(obj.getOriginalValue());
                        etName.setText(jsonObject.getString("name"));
                        etCode.setText(jsonObject.getString("code"));
                        etKL.setText(jsonObject.getString("mass"));
                        etHangSX.setText(jsonObject.getString("hang"));
                        etMadeIn.setText(jsonObject.getString("madein"));
                        etDonGia.setText(jsonObject.getString("dongia"));
                        etSL.setText(jsonObject.getString("soluong"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
        }

    }
    
}