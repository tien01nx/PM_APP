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

    //??nh x???
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
                            Toast.makeText(getContext(), "T???i d??? li???u l???i", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(getContext(), "T???i d??? li???u l???i", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //?????i t??n user
    private void DialogRename() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rename);
        dialog.setCanceledOnTouchOutside(false);

        //??nh x???
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
//                                        //c???p nh???t t??n m???i l??n firebase
//                                        databaseReference.child("users").child(ID).setValue(contact);
//                                        txtNameUser.setText(contact.getFullName());
//
//                                        ProfileFragment profileFragment= new ProfileFragment();
//                                        Bundle bundle= new Bundle();
//                                        bundle.putString("newname",txtNameUser.getText().toString());
//                                        profileFragment.setArguments(bundle);
//                                        Toast.makeText(getContext(), "?????i t??n th??nh c??ng !", Toast.LENGTH_SHORT).show();
//                                        dialog.dismiss();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError error) {
//                                    Toast.makeText(getContext(), "?????i t??n th???t b???i", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                } else {
//                    Toast.makeText(getContext(), "B???n ph???i nh???p t??n m???i", Toast.LENGTH_SHORT).show();
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

    //?????i m???t kh???u
    private void DialogRePassword() {
        Dialog dialog2 = new Dialog(getContext());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_repassword);
        dialog2.setCanceledOnTouchOutside(false);

        //??nh x??? dialog
        Button btnDongY2 = dialog2.findViewById(R.id.btnDongY2);
        Button btnThoat2 = dialog2.findViewById(R.id.btnThoat2);
        Button ReVerifyCode = dialog2.findViewById(R.id.ReVerifyCode);
        EditText changePass = dialog2.findViewById(R.id.changePass);
        EditText verifyCode2 = dialog2.findViewById(R.id.verifyCode);
        EditText etConfirmPass = dialog2.findViewById(R.id.etConfirmPass);

        //click n??t g???i m?? code
        ReVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendConfirmCode(emailChangePass, ACTION_RESET_PASSWORD);
            }
        });

        //click n??t ?????i
        btnDongY2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //l???y gi?? tr??? ng?????i d??ng nh???p v??o edit text
                String newPassword = changePass.getText().toString().trim();
                String verifyCode = verifyCode2.getText().toString().trim();
                String confirmpass = etConfirmPass.getText().toString().trim();

                //check tr???ng
                if (!newPassword.isEmpty() || !verifyCode.isEmpty() || !confirmpass.isEmpty()) {
                    //so s??nh 2 m???t kh???u
                    if (newPassword.equals(confirmpass)) {
                        //g???i h??m ?????i m???t kh???u v?? truy???n tham s??? cho h??m
                        ChangeThePassword(newPassword, verifyCode, 12);
                    } else {
                        Toast.makeText(getContext(), "2 m???t kh???u kh??ng gi???ng nhau !", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Kh??ng ???????c ????? tr???ng!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //click n??t tho??t
        btnThoat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //t???t dialog
                dialog2.dismiss();
            }
        });
        //hi???n dialog
        dialog2.show();

    }

    //h??m ?????i m???t kh???u
    public void ChangeThePassword(String newPassword, String verifyCode, int provider) {
        Task<Void> task = AGConnectAuth.getInstance().getCurrentUser().updatePassword(newPassword, verifyCode, provider);

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                //x??? l?? s??? ki???n khi ?????i mk th??nh c??ng
                Toast.makeText(getContext(), "?????i m???t kh???u th??nh c??ng !", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                //x??? l?? s??? ki???n khi g???p l???i
                Toast.makeText(getContext(), "M?? x??c minh kh??ng ch??nh x??c", Toast.LENGTH_LONG).show();
            }
        });
    }


    // g???i m?? x??c minh ????? ?????i m???t kh???u
    public void sendConfirmCode(String Email, int ACTION) {
        VerifyCodeSettings settings = VerifyCodeSettings.newBuilder()
                .action(ACTION)   //ACTION_REGISTER_LOGIN/ACTION_RESET_PASSWORD
                .sendInterval(30)
                .build();

        Task<VerifyCodeResult> task = AGConnectAuth.getInstance().requestVerifyCode(Email, settings);
        task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
            @Override
            public void onSuccess(VerifyCodeResult verifyCodeResult) {
                Toast.makeText(getContext(), "G???i m?? x??c minh th??nh c??ng", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getContext(), "G???i m?? x??c minh g???p l???i", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
