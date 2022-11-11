package com.example.myapplication.ui.mainPage;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.serviceSetting.ServiceAPI;
import com.example.myapplication.ui.serviceSetting.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatbotWeb extends Fragment {

    private String TAG = ChatbotWeb.class.getSimpleName();
    private WebView webView;
    private Button btnSave;
    private SharedPreferences pre, pre2;

    //서버통신
    public String getToken() {
        pre = getActivity().getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = pre.getString("TOKEN", null);
        return token;
    }
    public int getpetSerial() {
        pre2 = getActivity().getSharedPreferences("Serial", MODE_PRIVATE);
        int petSerial = pre2.getInt("petSerial", 0);
        return petSerial;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.f_chatbot,container,false);
        btnSave = rootview.findViewById(R.id.webBtnSave);
        webView = rootview.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());  // 새 창 띄우기 않기
        webView.setWebChromeClient(new WebChromeClient());

        webView.getSettings().setLoadWithOverviewMode(false);  // WebView 화면크기에 맞추도록 설정 - setUseWideViewPort 와 같이 써야함
        webView.getSettings().setUseWideViewPort(false);  // wide viewport 설정 - setLoadWithOverviewMode 와 같이 써야함

        webView.getSettings().setSupportZoom(false);  // 줌 설정 여부
        webView.getSettings().setBuiltInZoomControls(false);  // 줌 확대/축소 버튼 여부

        webView.getSettings().setJavaScriptEnabled(true); // 자바스크립트 사용여부
//        webview.addJavascriptInterface(new AndroidBridge(), "android");
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); // javascript가 window.open()을 사용할 수 있도록 설정
        webView.getSettings().setSupportMultipleWindows(true); // 멀티 윈도우 사용 여부

        webView.getSettings().setDomStorageEnabled(true);  // 로컬 스토리지 (localStorage) 사용여부

        //웹 뷰 호출
        webView.loadUrl("http://43.200.87.239:8000");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendInfo();
            }
        });

        return rootview;
    }

    public void SendInfo() {
        ServiceAPI ChatbotAPI = ServiceGenerator.createService(ServiceAPI.class, getToken());
        Call<ChatbotResponse> call = ChatbotAPI.addDiag(getpetSerial());

        call.enqueue(new Callback<ChatbotResponse>() {
            @Override
            public void onResponse(Call<ChatbotResponse> call, Response<ChatbotResponse> response) {
                if (!response.equals(200)) {
                    Log.d("예상진단 저장", "성공");
                }
            }

            @Override
            public void onFailure(Call<ChatbotResponse> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("알림")
                        .setMessage("잠시 후에 다시 시도해주세요.")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();
            }
        });
    }
}
