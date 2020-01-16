package com.poszft.utils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFragment extends Fragment{
    private String text;

    public static MyFragment getInstance(String text){
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        text = getArguments().getString("text");
        if (text != null && !text.isEmpty()){
            TextView textView = new TextView(getContext());
            textView.setText(text);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(26);
            textView.setGravity(Gravity.CENTER);
//            textView.setBackgroundColor(Color.GREEN);

            return textView;
        }

      return null;
    }

    public String getText(){
        return text;
    }
}
