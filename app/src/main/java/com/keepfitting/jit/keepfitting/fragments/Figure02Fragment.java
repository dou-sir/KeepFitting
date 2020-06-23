package com.keepfitting.jit.keepfitting.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.keepfitting.jit.keepfitting.service.impl.UserServiceImpl;
import com.keepfitting.jit.keepfitting.util.ChartUtils;
import com.keepfitting.jit.keepfitting.util.MyBottomDialog;
import com.keepfitting.jit.keepfitting.R;
public class Figure02Fragment extends Fragment {

    TextView tv_frag2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        Bundle bundle = getArguments();
        if (null != bundle &&bundle.getString("flag").equals("show")){
            view = inflater.inflate(R.layout.show_figure00, container, false);
            initShowView(view);
        }else {
            view = inflater.inflate(R.layout.fragment_figure00, container, false);
            initEditView(view);
        }
//        userService = new UserServiceImpl(getContext());

        return view;
    }

    private void initEditView(View view){

    }

    private void initShowView(View view){

    }



    private void init(View view){

//        tv_frag2 = view.findViewById(R.id.tv_frag2);
//
//        final MyBottomDialog dialog = new MyBottomDialog(getContext(),,
//                new MyBottomDialog.OnClickListener() {
//                    public void onClick(Dialog dialog,int flag,float num) {
//                        if(flag == 1) {
//                            Toast.makeText(getContext(),num+"QQ",Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(getContext(),num+"微信",Toast.LENGTH_SHORT).show();
//                        }
//                        dialog.dismiss();
//                    }
//                });
//        tv_frag2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.show();
//            }
//        });
    }

}