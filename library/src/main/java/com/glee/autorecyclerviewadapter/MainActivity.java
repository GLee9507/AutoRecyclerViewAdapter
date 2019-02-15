//package com.glee.autorecyclerviewadapter;
//
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//
//import com.glee.autorecyclerviewadapter.databinding.ActivityMainBinding;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ActivityMainBinding viewDataBinding =(Ac) DataBindingUtil.setContentView(this, R.layout.activity_main);
//        viewDataBinding.setLifecycleOwner(this);
//        viewDataBinding.setVm(new MainViewModel(getApplication()));
//    }
//}
