package com.keepfitting.jit.keepfitting.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.keepfitting.jit.keepfitting.R;


public class Figure00Fragment extends Fragment implements NumberPicker.OnValueChangeListener,NumberPicker.OnScrollListener,NumberPicker.Formatter{

    NumberPicker bigPicker;
    NumberPicker smallPicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_figure00, container, false);

        init(view);
        return view;

    }

    private void init(View view){

        bigPicker = view.findViewById(R.id.BigPicker);
        smallPicker = view.findViewById(R.id.SmallPicker);

        bigPicker.setFormatter(this);
        bigPicker.setOnValueChangedListener(this);
        bigPicker.setOnScrollListener(this);
        bigPicker.setMaxValue(300);
        bigPicker.setMinValue(0);
        bigPicker.setValue(50);

        smallPicker.setFormatter(this);
        smallPicker.setOnValueChangedListener(this);
        smallPicker.setOnScrollListener(this);
        smallPicker.setMaxValue(99);
        smallPicker.setMinValue(0);
        smallPicker.setValue(0);

    }



    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }

    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Toast.makeText(getContext(),
                "原来的值 " + oldVal + "--新值: "
                        + newVal, Toast.LENGTH_SHORT).show();
    }

    public void onScrollStateChange(NumberPicker view, int scrollState) {
        switch (scrollState) {
            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                Toast.makeText(getContext(), "后续滑动(飞呀飞，根本停下来)", Toast.LENGTH_LONG)
                        .show();
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                Toast.makeText(getContext(), "不滑动", Toast.LENGTH_LONG).show();
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                Toast.makeText(getContext(), "滑动中", Toast.LENGTH_LONG)
                        .show();
                break;
        }
    }


}
