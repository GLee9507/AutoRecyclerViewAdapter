package com.glee.autorecyclerviewadapter;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.glee.autorecyclerviewadapter.core.AutoBinder;
import com.glee.autorecyclerviewadapter.core.Footer;
import com.glee.autorecyclerviewadapter.core.Header;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;

/**
 * @author liji
 * @date 2019/2/13 11:45
 * description
 */


public class MainViewModel extends AndroidViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    AutoBinder<String> autoBinder = new AutoBinder<>(R.layout.item_test, BR.text);

    private void init() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i + "");
        }
        autoBinder.getData().setData(list);
        autoBinder.addHeader(new Header("test", R.layout.header_test, BR.text, "header"));
        autoBinder.addHeader(new Header("test1", R.layout.header_test, BR.text, "header"));
        autoBinder.addHeader(new Header("test2", R.layout.header_test, BR.text, "header123123"));
        autoBinder.addHeader(new Header("test3", R.layout.header_test, BR.text, "header"));
        autoBinder.addFooter(new Footer("test", R.layout.header_test, BR.text, "footer"));
        new Thread(() -> {
            handler.postDelayed(() -> autoBinder.updateFooter("test", (Function<String, String>) input -> "改一下"), 3000);
            handler.postDelayed(() -> autoBinder.removeHeader("test2"), 3000);
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int finalI = i;
                handler.post(() -> autoBinder.getData().update(finalI, input -> input + "asdaksd"));

            }
        }).start();
//        new Handler().postDelayed(() -> autoBinder.getData().update(10, input -> input+"asdasd"), 3000);
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    public AutoBinder<String> getAutoBinder() {
        return autoBinder;
    }
}
