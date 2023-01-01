package com.nvm.imapp.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.EmailAuthProvider;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.nvm.imapp.MainActivity;
import com.nvm.imapp.QuenMK.ResetPassword;
import com.nvm.imapp.R;
import com.nvm.imapp.registration.UserRegisterActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    AGConnectUser user;
    String userID;
    TextView txtUserRegister;
    EditText txtEmail, txtPassword;
    Button btnLogin;
    TextView txtPp,RePass;
    Animation topAnim;
    ImageView imgLo;
    ProgressBar progressBar;
    private long outApp;
    Toast outToast;
    CheckBox checkBox;
    String email = "", password = "", uid = "";
    final static int CREATE_USER = 101;
    private static int SPLASH_TIME_OUT = 3000;
    FirebaseDatabase database;
    DatabaseReference reference;
    //silenty signIn
    AccountAuthParams authParams;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViews();
        progressBar.setVisibility(View.GONE);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        imgLo.setAnimation(topAnim);
        user = AGConnectAuth.getInstance().getCurrentUser();

        //Quên mật khẩu
//        txtUserRegister.setVisibility(View.GONE);
//        RePass.setVisibility(View.GONE);

        txtPp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://mannv291201.github.io/manh321/?fbclid=IwAR1PsfcFIADupjukaE23OKRm0veyj2T2ZfbfE-g4qzrqXiXd0oGKmDC6Hj8")));
            }
        });
        RePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, ResetPassword.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogin.setVisibility(View.GONE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked()){
                        btnLogin.setVisibility(View.VISIBLE);
                }
                else {
                    btnLogin.setVisibility(View.GONE);
                }
            }
        });

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("users");
        //silent signIn

        authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).createParams();


    }

    private void signInID(String email, String password) {

        AGConnectAuthCredential credential = EmailAuthProvider.credentialWithPassword(email, password);
        AGConnectAuth.getInstance().signIn(credential)
                .addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                    @Override
                    public void onSuccess(SignInResult signInResult) {
                        // Obtain sign-in information.

                        user = AGConnectAuth.getInstance().getCurrentUser();
                        userID = user.getUid();
                        callMainActivity(userID);
                        progressBar.setVisibility(View.VISIBLE);
                        closeKeyBoard();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác !", Toast.LENGTH_LONG).show();
                        Log.e("login error: ", e.getMessage());
                    }
                });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createnewac:
                Intent intent = new Intent(LoginActivity.this, UserRegisterActivity.class);
                startActivityForResult(intent, CREATE_USER);
                this.finish();
                break;
            case R.id.btnLogin:
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();
                if (!email.isEmpty() || !password.isEmpty()) {
                    signInID(email, password);
                }

               /* if(user!=null){
                    callMainActivity(user.getUid());
                }else {
                    signInID(email, password);
                }
*/
                break;

        }

    }
    private void closeKeyBoard(){
        View view =this.getCurrentFocus();
        if(view !=null){
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void callMainActivity(String userID) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_USER && resultCode == RESULT_OK) {
            uid = data.getStringExtra("uid");
            email = data.getStringExtra("email");
            password = data.getStringExtra("password");
        }
    }

    private void getViews() {
        txtEmail = findViewById(R.id.loginEmail);
        txtPp =findViewById(R.id.txtPp);
        progressBar=findViewById(R.id.progressBar);
        imgLo=findViewById(R.id.imgLogin);
        txtPassword = findViewById(R.id.loginPassword);
        txtUserRegister = findViewById(R.id.createnewac);
        txtUserRegister.setOnClickListener(this);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        checkBox = findViewById(R.id.checkBox);
        RePass =findViewById(R.id.RePass);
    }

     ///// click 2 làn thoát
    @Override
    public void onBackPressed() {
        if(outApp + 2000 > System.currentTimeMillis()){
            outToast.cancel();
            super.onBackPressed();
            return;
        }else{
            outToast = Toast.makeText(LoginActivity.this, "CLICK 1 lần nữa để thoát !", Toast.LENGTH_SHORT);
            outToast.show();
        }
        outApp=System.currentTimeMillis();
    }
}