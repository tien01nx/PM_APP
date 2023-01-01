package com.nvm.imapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.nvm.imapp.R;


public class HelpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_help, container, false);

        //set text cho Title
        ((TextView) root.findViewById(R.id.sample1).findViewById(R.id.title)).setText("1. Tại sao khi tôi gửi yêu cầu gửi mã xác minh mà không nhận được mã?");
        ((TextView) root.findViewById(R.id.sample2).findViewById(R.id.title)).setText("2. Tại sao tôi nên chọn sử dụng dịch vụ quản lý sản phẩm PM?");
        ((TextView) root.findViewById(R.id.sample3).findViewById(R.id.title)).setText("3. Tôi có thể làm những gì trên ứng dụng quản lý sản phẩm PM?");
        ((TextView) root.findViewById(R.id.sample4).findViewById(R.id.title)).setText("4. Tôi đang ở nước ngoài, tôi có chuyển đổi để sử dụng dịch vụ PM được không?");
        ((TextView) root.findViewById(R.id.sample5).findViewById(R.id.title)).setText("5. Tôi không muốn tiếp tục sử dụng dịch vụ PM thì phải làm gì?");
        ((TextView) root.findViewById(R.id.sample6).findViewById(R.id.title)).setText("6. Thời gian để PM xác thực tài khoản là bao lâu?");
        ((TextView) root.findViewById(R.id.sample7).findViewById(R.id.title)).setText("7. Tôi cần làm gì trong lần đăng nhập đầu tiên khi mới tải ứng dụng quản lý sản phẩm PM?");
        ((TextView) root.findViewById(R.id.sample8).findViewById(R.id.title)).setText("8. Khi gỡ bỏ ứng dụng trên điện thoại thì mọi thông tin sản phẩm và giao dịch có bị mất hay không?");
        ((TextView) root.findViewById(R.id.sample9).findViewById(R.id.title)).setText("9. Tôi phải làm gì nếu quên mật khẩu đăng nhập dịch vụ quản lý sản phẩm PM?");


        //ánh xạ
        ExpandableTextView expTv1 = (ExpandableTextView) root.findViewById(R.id.sample1)
                .findViewById(R.id.expand_text_view);
        ExpandableTextView expTv2 = (ExpandableTextView) root.findViewById(R.id.sample2)
                .findViewById(R.id.expand_text_view);
        ExpandableTextView expTv3 = (ExpandableTextView) root.findViewById(R.id.sample3)
                .findViewById(R.id.expand_text_view);
        ExpandableTextView expTv4 = (ExpandableTextView) root.findViewById(R.id.sample4)
                .findViewById(R.id.expand_text_view);
        ExpandableTextView expTv5 = (ExpandableTextView) root.findViewById(R.id.sample5)
                .findViewById(R.id.expand_text_view);
        ExpandableTextView expTv6 = (ExpandableTextView) root.findViewById(R.id.sample6)
                .findViewById(R.id.expand_text_view);
        ExpandableTextView expTv7 = (ExpandableTextView) root.findViewById(R.id.sample7)
                .findViewById(R.id.expand_text_view);
        ExpandableTextView expTv8 = (ExpandableTextView) root.findViewById(R.id.sample8)
                .findViewById(R.id.expand_text_view);
        ExpandableTextView expTv9 = (ExpandableTextView) root.findViewById(R.id.sample9)
                .findViewById(R.id.expand_text_view);



        //set text cho câu trả lời
        expTv1.setText(getString(R.string.ch1));
        expTv2.setText(getString(R.string.ch2));
        expTv3.setText(getString(R.string.ch3));
        expTv4.setText(getString(R.string.ch4));
        expTv5.setText(getString(R.string.ch5));
        expTv6.setText(getString(R.string.ch6));
        expTv7.setText(getString(R.string.ch7));
        expTv8.setText(getString(R.string.ch8));
        expTv9.setText(getString(R.string.ch9));


        return root;
    }
}