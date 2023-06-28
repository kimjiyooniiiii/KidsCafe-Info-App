package com.example.afinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static RequestQueue requestQueue;
    TextView textView;
    EditText editText;
    String cafeName;
    boolean list;               // 눌린 버튼 구분
    String url = "http://apis.data.go.kr/6260000/BusanKidsCafeInfoService/getKidsCafeInfo?serviceKey=B6s5dIUr8yxqDeXVnCJEJL%2BRLWSdV0Ityg56SYzDvbhqKHU4HfYkmzMIxJi2R4oeUyy7HPUAY%2FetzxgpHyd73A%3D%3D&numOfRows=10&pageNo=1&resultType=json";
    Map<String, String> naver = new HashMap<String, String>();          // 키즈카페 지도 사이트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editTextTextPersonName);

        naver.put("아이야 놀자", "https://map.naver.com/v5/entry/place/1066241768?c=14374259.2475828,4189278.4752571,13,0,0,0,dh&placePath=%2Fhome%3Fentry=plt");
        naver.put("화목방방랜드","https://map.naver.com/v5/entry/place/37936091?c=14380057.6794831,4187098.9890387,13,0,0,0,dh&placePath=%2Fhome%3Fentry=plt");
        naver.put("타요키즈카페 부산좌동점","https://map.naver.com/v5/entry/place/34713829?c=14380135.7923698,4186737.6259617,13,0,0,0,dh&placePath=%2Fhome&entry=plt");
        naver.put("슈슈봉봉 해운대점","https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=%EC%8A%88%EC%8A%88%EB%B4%89%EB%B4%89+%EB%B6%80%EC%82%B0%ED%95%B4%EC%9A%B4%EB%8C%80%EC%A0%90&oquery=%EC%8A%88%EC%8A%88%EB%B4%89%EB%B4%89+%EC%98%A4%EC%8B%9C%EB%8A%94%EA%B8%B8&tqi=hHyuEsprvTossOzTQidssssssco-515488");
        naver.put("주바운스클럽 트램폴린파크 해운대점","https://map.naver.com/v5/entry/place/1771907371?c=14379926.1221089,4187086.8281133,13,0,0,0,dh&placePath=%2Fhome%3Fentry=plt");
        naver.put("정글방방","https://map.naver.com/v5/entry/place/34491022?c=14373526.1976039,4191592.2008555,13,0,0,0,dh&placePath=%2Fhome&entry=plt");
        naver.put("시크릿쥬쥬또봇키즈카페 센텀점","https://map.naver.com/v5/entry/place/1754559142?c=14375096.8265634,4187131.8903326,13,0,0,0,dh&placePath=%2Fhome%3Fentry=plt");
        naver.put("우당탕탕놀이터","https://map.naver.com/v5/search/%EC%9A%B0%EB%8B%B9%ED%83%95%ED%83%95%EB%86%80%EC%9D%B4%ED%84%B0/place/1628914184?c=14375679.9268543,4194452.5309306,15,0,0,0,dh&entry=plt&placePath=%3Fentry%253Dbmp");
        naver.put("홈플러스 아시아드점 챔피언더블랙벨트","https://map.naver.com/v5/entry/place/1674805895?c=14367116.4769838,4189902.2004239,13,0,0,0,dh&placePath=%2Fhome%3Fentry=plt");
        naver.put("아이점프(연산점)","https://map.naver.com/v5/entry/place/38453414?c=14369817.9227266,4190130.6628129,13,0,0,0,dh&placePath=%2Fhome%3Fentry=plt");


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {       // 전체보기 버튼
            @Override
            public void onClick(View view) {
                list = true;
                request(url);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {      // 주소검색 버튼
            @Override
            public void onClick(View view) {
                list = false;
                cafeName = editText.getText().toString();

                if(!cafeName.isEmpty()) {                   // 키즈카페 이름이 입력되면
                    request(url);
                }
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {                // 지도 검색 버튼
            @Override
            public void onClick(View view) {
                cafeName = editText.getText().toString();
                Iterator<String> keys = naver.keySet().iterator();
                boolean blank = true;                  // 입력값 오류 여부

                if(!cafeName.isEmpty()){
                    while( keys.hasNext() ){
                        String key = keys.next();

                        if(key.equals(cafeName)){           // 입력한 키즈카페가 해시맵에 존재하면
                            blank = false;

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(naver.get(cafeName)));     // uri에 해당하는 페이지 열기
                            startActivity(intent);

                            break;
                        }
                    }
                }
                if(blank){
                    Toast toast = Toast.makeText(getApplicationContext(), "키즈카페 명을 정확하게 입력하세요", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }


    public void request(String url) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!textView.getText().toString().isEmpty()){                  // 출력창 리셋
                    textView.setText("");
                }
                showList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                println("에러 : " + error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void println(String data) {
        textView.append(data + "\n");
    }

    public void showList(String response) {
        Gson gson = new Gson();
        KidsCafe kidsCafe = gson.fromJson(response, KidsCafe.class);

       if(list){                        // 전체보기 버튼이 눌렸을 때
            for(int i = 0; i < kidsCafe.getKidsCafeInfo.body.items.item.size(); i++){
                println(i+1 + ". " + kidsCafe.getKidsCafeInfo.body.items.item.get(i).cafe_nm + "\n");      // 키즈카페 명 출력
            }
        }

        else{                           // 주소 검색 버튼이 눌렸을 때
            boolean blank = true;       // 입력값 오류 여부

            for(int i = 0; i < kidsCafe.getKidsCafeInfo.body.items.item.size(); i++){
                if(cafeName.equals(kidsCafe.getKidsCafeInfo.body.items.item.get(i).cafe_nm)) {      // 입력받은 키즈카페 이름과 동일한 카페 찾기
                    println("주소 : " + kidsCafe.getKidsCafeInfo.body.items.item.get(i).road_nm + "\n");
                    blank = false;
                }
            }
            if(blank){
                Toast toast = Toast.makeText(this, "키즈카페 명을 정확하게 입력하세요", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}