package com.nvm.imapp.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nvm.imapp.R;

public class FeedBackActivity extends AppCompatActivity {
    ImageView backfb;
    EditText etSubject, etMessage;
    Button btnSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
            // ánh xạ
        backfb=findViewById(R.id.backfb);
        etMessage=findViewById(R.id.etMessage);
        etSubject=findViewById(R.id.etSubject);
        btnSent=findViewById(R.id.btnSent);

        //bắt sự kiện
        backfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentmail();
            }
        });
    }
    private void sentmail(){
        String mess=etMessage.getText().toString();
        String sub=etSubject.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);

        //các ứng dụng có thể gửi
        intent.setType("message/rfc822");

        //truyền dữ liệu sang email
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"manhnv291201@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,sub);
        intent.putExtra(Intent.EXTRA_TEXT,mess);
        try {
            startActivity(Intent.createChooser(intent,"Sent mail..."));
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(this, "There are no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }
}