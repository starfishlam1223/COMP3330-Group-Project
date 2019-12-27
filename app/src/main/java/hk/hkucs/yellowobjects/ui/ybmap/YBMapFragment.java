package hk.hkucs.yellowobjects.ui.ybmap;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import hk.hkucs.yellowobjects.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class YBMapFragment extends Fragment {

    private YBMapViewModel YBMapViewModel;
    private WebView webView;
    private FloatingActionButton ybQuiz;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        YBMapViewModel =
                ViewModelProviders.of(this).get(YBMapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ybmap, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        webView = (WebView) root.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("https://www.google.com/maps/d/u/0/viewer?mid=10SCYSkgC9KMC_kAGtLChlGRLWVbk8NTs&ll=22.340583507604187%2C114.06832897361642&z=11");
        webView.loadUrl("https://www.google.com/maps/d/u/0/viewer?mid=1y44FzG0yy2qK_IPOotMg06ckh_ypZoJW&ll=22.354918648595508%2C114.06804800000009&z=10");
        YBMapViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        ybQuiz = root.findViewById(R.id.ybQuiz);
        ybQuiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent question = new Intent(getContext(), Question.class);
                startActivity(question);
            }
        });

        return root;
    }
}