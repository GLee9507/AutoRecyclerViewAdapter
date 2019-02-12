package com.glee.autorecyclerviewadapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DiffUtil;

import com.glee.autorecyclerviewadapter.core.AutoList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        AutoList<String> autoList = new AutoList<String>(new DiffUtil.ItemCallback<String>() {
            @Override
            public boolean areItemsTheSame(@NonNull String s, @NonNull String t1) {
                return s.equals(t1);
            }

            @Override
            public boolean areContentsTheSame(@NonNull String s, @NonNull String t1) {
                return s.equals(t1);
            }
        });
        final String TAG = "glee9507";
        autoList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<String>>() {
            @Override
            public void onChanged(ObservableList<String> sender) {
                Log.d(TAG, "onChanged() called with: sender = [" + sender + "]");
            }

            @Override
            public void onItemRangeChanged(ObservableList<String> sender, int positionStart, int itemCount) {
                Log.d(TAG, "onItemRangeChanged() called with: sender = [" + sender + "], positionStart = [" + positionStart + "], itemCount = [" + itemCount + "]");
            }

            @Override
            public void onItemRangeInserted(ObservableList<String> sender, int positionStart, int itemCount) {
                Log.d(TAG, "onItemRangeInserted() called with: sender = [" + sender + "], positionStart = [" + positionStart + "], itemCount = [" + itemCount + "]");
            }

            @Override
            public void onItemRangeMoved(ObservableList<String> sender, int fromPosition, int toPosition, int itemCount) {
                Log.d(TAG, "onItemRangeMoved() called with: sender = [" + sender + "], fromPosition = [" + fromPosition + "], toPosition = [" + toPosition + "], itemCount = [" + itemCount + "]");
            }

            @Override
            public void onItemRangeRemoved(ObservableList<String> sender, int positionStart, int itemCount) {
                Log.d(TAG, "onItemRangeRemoved() called with: sender = [" + sender + "], positionStart = [" + positionStart + "], itemCount = [" + itemCount + "]");
            }
        });

        for (int i = 0; i < 20; i++) {
            autoList.add(i + "");
        }


        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            list1.add(i + "");
        }


        autoList.submitList(list1);
        Log.d("glee9507",autoList.size()+"a");
        Log.d("glee9507",autoList.size()+"b");
        Log.d("glee9507",autoList.size()+"c");
        Log.d("glee9507",autoList.size()+"d");
        Log.d("glee9507",autoList.size()+"e");
        Log.d("glee9507",autoList.size()+"f");
        Log.d("glee9507",autoList.size()+"g");

    }
}
