package com.nvm.imapp.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.EmailUser;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.agconnect.auth.VerifyCodeResult;
import com.huawei.agconnect.auth.VerifyCodeSettings;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskExecutors;
import com.nvm.imapp.MainActivity;
import com.nvm.imapp.R;
import com.nvm.imapp.login.LoginActivity;
import com.nvm.imapp.login.PrivacyPolicyActivity;
import com.nvm.imapp.model.Contact;

import java.util.Locale;
import java.util.regex.Pattern;

import static com.huawei.agconnect.auth.VerifyCodeSettings.ACTION_REGISTER_LOGIN;

public class UserRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private AGConnectAuth huaweiAuth;
    Button btnRegister, btnVerify;
    ImageView  btnClose;
    Animation topAnim;
    ImageView imgSi;
    TextView checkpass;
    TextView checkemail;
    EditText editFullName, editPhone, editVerifyCode, editPassword, editEmail, editConfirmPwd;
    private String email;
    private String name;
    private String password;
    private String confirmPassword;
    private String verCode;
    private String phone;
    AGConnectUser user;
    String userID;
    private String uid = "";
    String uPass, uConfirPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        getViews();
        huaweiAuth = AGConnectAuth.getInstance();
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);

        imgSi.setAnimation(topAnim);

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mk= editPassword.getText().toString().trim();
                checkMatKhau(mk);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String emailck=editEmail.getText().toString().trim();
                checkEmail(emailck);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void sendCodeVerification() {
        email = editEmail.getText().toString();
        if (email.isEmpty() || email == null) {
            Toast.makeText(this, "Không được để trông Email !!! ", Toast.LENGTH_LONG).show();
        } else {
            VerifyCodeSettings settings = VerifyCodeSettings.newBuilder()
                    .action(ACTION_REGISTER_LOGIN)
                    .sendInterval(30)
                    .locale(Locale.ENGLISH)
                    .build();
            Task<VerifyCodeResult> task = huaweiAuth.requestVerifyCode(email, settings);
            task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
                @Override
                public void onSuccess(VerifyCodeResult verifyCodeResult) {
                    Toast.makeText(UserRegisterActivity.this, "Kiểm tra mã xác minh trong Email của bạn !", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Log.d("VerifyCodeErr", e.getMessage());
                }
            });


        }
    }
    private boolean checkConfirPass() {
        if (uPass.equals(uConfirPass))
            return false;
        else {
            return true;
        }
    }
    private void getDataFromView() {
        uConfirPass = editConfirmPwd.getText().toString().trim();
        uPass = editPassword.getText().toString().trim();
    }
    private void signUpWithEmail() {
        email = editEmail.getText().toString();
        name = editFullName.getText().toString();
        phone = editPhone.getText().toString();
        password = editPassword.getText().toString();
        verCode = editVerifyCode.getText().toString();
        confirmPassword = editConfirmPwd.getText().toString();
        getDataFromView();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || verCode.isEmpty()) {
            Toast.makeText(this, "Không được để trống !", Toast.LENGTH_LONG).show();
        }
        else if(checkConfirPass()){
            Toast.makeText(this, "2 mật khẩu không trùng khớp !", Toast.LENGTH_SHORT).show();
        }
        else {
            EmailUser emailUser = new EmailUser.Builder().setEmail(email)
                    .setVerifyCode(verCode)
                    .setPassword(password).build();
            AGConnectAuth.getInstance().createUser(emailUser)
                    .addOnCompleteListener(new OnCompleteListener<SignInResult>() {
                        @Override
                        public void onComplete(Task<SignInResult> task) {
                            if (task.isSuccessful()) {
                                user = AGConnectAuth.getInstance().getCurrentUser();
                                userID = user.getUid();
                                //insert in realtime db firebase
                                Contact contact = new Contact(name, phone, email, userID);

                                insContactDB(contact, userID);
                                //call back login form
                                Intent intent = new Intent();
                                intent.putExtra("uid", userID);
                                intent.putExtra("email", email);
                                intent.putExtra("password", password);
                                setResult(RESULT_OK, intent);
                                Intent intt = new Intent(UserRegisterActivity.this, MainActivity.class);
                                startActivity(intt);
                                finish();

                            } else {
                                Log.d("SignUpErr", task.getException().getMessage());
                                Toast.makeText(UserRegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private void insContactDB(Contact contact, String userID) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child("users").child(userID).setValue(contact);


    }

    private void getViews() {
        editFullName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editPass);
        editPhone = findViewById(R.id.editPhone);
        editVerifyCode = findViewById(R.id.editVerify);
        editEmail = findViewById(R.id.editEmail);
        editConfirmPwd = findViewById(R.id.editConfirmPass);
        btnRegister = findViewById(R.id.buttonAcount);
        btnClose = findViewById(R.id.buttonClose);
        imgSi=findViewById(R.id.imgSi);
        checkpass=findViewById(R.id.checkpass);
        checkemail=findViewById(R.id.checkemail);
        btnVerify = findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAcount:
                //register account
                signUpWithEmail();
                break;
            case R.id.btnVerify:
                sendCodeVerification();
                //send verify code
                break;
            case R.id.buttonClose:
                Intent intent = new Intent(UserRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
/////////////// check//////////////////////
    private void checkMatKhau(String mk){
        Pattern chuHoa =Pattern.compile("[A-Z]");
        Pattern chuThuong =Pattern.compile("[a-z]");
        Pattern chuSo =Pattern.compile("[0-9]");
        Pattern kytu =Pattern.compile("[,.!@+#$&]");

        if(!kytu.matcher(mk).find() || !chuHoa.matcher(mk).find() || !chuThuong.matcher(mk).find() || !chuSo.matcher(mk).find()){
            checkpass.setText("Mật khẩu phải có chữ hoa, chữ thường và chữ số !");
            editPassword.setError("hus hus");
        }
        else if(mk.length()<=8){
            checkpass.setText("Mật khẩu phải có 8 ký tự trở lên !");
            editPassword.setError("hus hus");
        }else {
            checkpass.setText("");
            //editPassword.setError(null);
        }
    }
    private void checkEmail(String emailck){
        if(!Patterns.EMAIL_ADDRESS.matcher(emailck).matches()){
            checkemail.setText("Không phải địa chỉ email");
        }else
                checkemail.setText("");
    }

}