package com.nvm.imapp.QuenMK;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.VerifyCodeResult;
import com.huawei.agconnect.auth.VerifyCodeSettings;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskExecutors;
import com.nvm.imapp.R;
import com.nvm.imapp.login.LoginActivity;
import com.nvm.imapp.login.StartAppActivity;

import static com.huawei.agconnect.auth.VerifyCodeSettings.ACTION_RESET_PASSWORD;

public class ResetPassword extends AppCompatActivity {

    EditText reEmail, rePass, reVerify, reConFirmPass;
    Button btnReset, btnReVerify;
    ImageView imgRe,backfp;
    Animation topAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getViews();
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);

        imgRe.setAnimation(topAnim);

        btnReVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= reEmail.getText().toString().trim();
                sendConfirmCode(email,ACTION_RESET_PASSWORD);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= reEmail.getText().toString().trim();
                String pass= rePass.getText().toString().trim();
                String vercode= reVerify.getText().toString().trim();
                String confirmpass=reConFirmPass.getText().toString().trim();

                if(!email.isEmpty() || !pass.isEmpty() || !vercode.isEmpty()) {
                    if(pass.equals(confirmpass)) {
                        resetPasswordWithEemail(email, pass, vercode);
                    }else {
                        Toast.makeText(ResetPassword.this, "2 mật khẩu không trùng khớp !", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ResetPassword.this, "Không được để trống !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResetPassword.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void resetPasswordWithEemail(String email, String pass, String vercode){
        Task<Void> task = AGConnectAuth.getInstance().resetPassword(email,pass,vercode);

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                alertDialogTest();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(ResetPassword.this, "Thay đổi mật khẩu thất bại !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getViews() {
        reEmail = findViewById(R.id.reEmail);
        rePass = findViewById(R.id.rePass);
        reVerify = findViewById(R.id.reVerify);
        reConFirmPass = findViewById(R.id.reConfirmPass);
        btnReset = findViewById(R.id.btnReset);
        btnReVerify = findViewById(R.id.btnReVerify);
        imgRe=findViewById(R.id.imgRe);
        backfp=findViewById(R.id.backfp);

    }

    public void sendConfirmCode(String Email, int ACTION) {
        VerifyCodeSettings settings = VerifyCodeSettings.newBuilder()
                .action(ACTION)   //ACTION_REGISTER_LOGIN/ACTION_RESET_PASSWORD
                .sendInterval(30)
                .build();

        Task<VerifyCodeResult> task = AGConnectAuth.getInstance().requestVerifyCode(Email, settings);
        task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
            @Override
            public void onSuccess(VerifyCodeResult verifyCodeResult) {
                Toast.makeText(ResetPassword.this, "Gửi mã xác minh thành công !", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(ResetPassword.this, "Gửi mã xác minh thất bại !", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void alertDialogTest(){
        AlertDialog.Builder alertdialog= new AlertDialog.Builder(this);
        alertdialog.setTitle("Thông Báo !");
        alertdialog.setIcon(R.drawable.finalicon);
        alertdialog.setMessage("Thay đổi mật khẩu thành công !!!");
        alertdialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(ResetPassword.this, StartAppActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alertdialog.show();
    }
}