package com.example.ranking_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected EditText etx_search;
    protected  String str_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etx_search = (EditText) findViewById(R.id.et_search);
        etx_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    //do stuff
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, MainActivity2.class);
                    Bundle bundle = new Bundle();
                    str_search = etx_search.getText().toString();
                    bundle.putString("search_content", str_search);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}