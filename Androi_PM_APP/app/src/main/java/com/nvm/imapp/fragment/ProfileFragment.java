package com.nvm.imapp.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.VerifyCodeResult;
import com.huawei.agconnect.auth.VerifyCodeSettings;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskExecutors;
import com.nvm.imapp.R;
import com.nvm.imapp.feedback.FeedBackActivity;
import com.nvm.imapp.login.StartAppActivity;
import com.nvm.imapp.model.Contact;

import static com.huawei.agconnect.auth.VerifyCodeSettings.ACTION_RESET_PASSWORD;

public class ProfileFragment extends Fragment {

    TextView txtNameUser, txtEmail, txtPhoneNumber, txtID, abc, pp, rePassword, feedBack;
    String ID, emailChangePass;
    ImageView imgRename;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        getViews(root);
        readFB("users", ID);
        abc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AGConnectAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), StartAppActivity.class));
                getActivity().finish();
            }
        });
        pp = root.findViewById(R.id.pp);
        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://mannv291201.github.io/manh321/?fbclid=IwAR1PsfcFIADupjukaE23OKRm0veyj2T2ZfbfE-g4qzrqXiXd0oGKmDC6Hj8")));
            }
        });
        imgRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogRename();
            }
        });
        rePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogRePassword();
            }
        });
        feedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(getActivity().getApplication(), FeedBackActivity.class);
                startActivity(itent);
            }
        });

        return root;
    }

    //ánh xạ
    private void getViews(View root) {
        abc = root.findViewById(R.id.abc);
        txtEmail = root.findViewById(R.id.txtEmail);
        txtNameUser = root.findViewById(R.id.txtNameUser);
        txtPhoneNumber = root.findViewById(R.id.txtPhoneNumber);
        txtID = root.findViewById(R.id.txtID);
        imgRename = root.findViewById(R.id.imgRename);
        rePassword = root.findViewById(R.id.rePassword);
        feedBack = root.findViewById(R.id.feedBack);
        ID = AGConnectAuth.getInstance().getCurrentUser().getUid();
        emailChangePass = AGConnectAuth.getInstance().getCurrentUser().getEmail();
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
                            txtPhoneNumber.setText(contact.getPhone());
                            txtID.setText(contact.getUid());
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

    //đổi tên user
    private void DialogRename() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rename);
        dialog.setCanceledOnTouchOutside(false);

        //ánh xạ
     //  EditText etNewName = dialog.findViewById(R.id.etNewName);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnThoat = dialog.findViewById(R.id.btnThoat);

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   String newname2 = etNewName.getText().toString().trim();
//                if (!newname2.isEmpty()) {
//
//                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//                    databaseReference.child("users").child(ID)
//                            .addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot snapshot) {
//                                    if (snapshot.exists()) {
//                                        Contact contact = snapshot.getValue(Contact.class);
//                                        contact.setFullName(newname2);
//
//                                        //cập nhật tên mới lên firebase
//                                        databaseReference.child("users").child(ID).setValue(contact);
//                                        txtNameUser.setText(contact.getFullName());
//
//                                        ProfileFragment profileFragment= new ProfileFragment();
//                                        Bundle bundle= new Bundle();
//                                        bundle.putString("newname",txtNameUser.getText().toString());
//                                        profileFragment.setArguments(bundle);
//                                        Toast.makeText(getContext(), "Đổi tên thành công !", Toast.LENGTH_SHORT).show();
//                                        dialog.dismiss();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError error) {
//                                    Toast.makeText(getContext(), "Đổi tên thất bại", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                } else {
//                    Toast.makeText(getContext(), "Bạn phải nhập tên mới", Toast.LENGTH_SHORT).show();
//                }
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

    //đổi mật khẩu
    private void DialogRePassword() {
        Dialog dialog2 = new Dialog(getContext());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_repassword);
        dialog2.setCanceledOnTouchOutside(false);

        //ánh xạ dialog
        Button btnDongY2 = dialog2.findViewById(R.id.btnDongY2);
        Button btnThoat2 = dialog2.findViewById(R.id.btnThoat2);
        Button ReVerifyCode = dialog2.findViewById(R.id.ReVerifyCode);
        EditText changePass = dialog2.findViewById(R.id.changePass);
        EditText verifyCode2 = dialog2.findViewById(R.id.verifyCode);
        EditText etConfirmPass = dialog2.findViewById(R.id.etConfirmPass);

        //click nút gửi mã code
        ReVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendConfirmCode(emailChangePass, ACTION_RESET_PASSWORD);
            }
        });

        //click nút đổi
        btnDongY2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //lấy giá trị người dùng nhập vào edit text
                String newPassword = changePass.getText().toString().trim();
                String verifyCode = verifyCode2.getText().toString().trim();
                String confirmpass = etConfirmPass.getText().toString().trim();

                //check trống
                if (!newPassword.isEmpty() || !verifyCode.isEmpty() || !confirmpass.isEmpty()) {
                    //so sánh 2 mật khẩu
                    if (newPassword.equals(confirmpass)) {
                        //gọi hàm đổi mật khẩu và truyền tham số cho hàm
                        ChangeThePassword(newPassword, verifyCode, 12);
                    } else {
                        Toast.makeText(getContext(), "2 mật khẩu không giống nhau !", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Không được để trống!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //click nút thoát
        btnThoat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tắt dialog
                dialog2.dismiss();
            }
        });
        //hiện dialog
        dialog2.show();

    }

    //hàm đổi mật khẩu
    public void ChangeThePassword(String newPassword, String verifyCode, int provider) {
        Task<Void> task = AGConnectAuth.getInstance().getCurrentUser().updatePassword(newPassword, verifyCode, provider);

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                //xử lí sự kiện khi đổi mk thành công
                Toast.makeText(getContext(), "Đổi mật khẩu thành công !", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                //xử lí sự kiện khi gặp lỗi
                Toast.makeText(getContext(), "Mã xác minh không chính xác", Toast.LENGTH_LONG).show();
            }
        });
    }


    // gửi mã xác minh để đổi mật khẩu
    public void sendConfirmCode(String Email, int ACTION) {
        VerifyCodeSettings settings = VerifyCodeSettings.newBuilder()
                .action(ACTION)   //ACTION_REGISTER_LOGIN/ACTION_RESET_PASSWORD
                .sendInterval(30)
                .build();

        Task<VerifyCodeResult> task = AGConnectAuth.getInstance().requestVerifyCode(Email, settings);
        task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
            @Override
            public void onSuccess(VerifyCodeResult verifyCodeResult) {
                Toast.makeText(getContext(), "Gửi mã xác minh thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getContext(), "Gửi mã xác minh gặp lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
