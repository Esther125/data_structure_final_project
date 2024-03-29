package com.example.ranking_master;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jsouptest.JsoupPrintLinks;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListView keywordsView;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinkedList<HashMap<String,String>> data = new LinkedList<>();
    private ArrayList<String> urlList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();
    ArrayList<String>relativesList = new ArrayList<>();
    private ArrayList<String> sublinksList = new ArrayList<>();
    private ArrayList<Keyword> keywordsList = new ArrayList<>();
    private ArrayList<Keyword> charactersList = new ArrayList<>();
    private ArrayList<WebTree> trees = new ArrayList<WebTree>();
    private MyAdapter myAdapter;
    private JsoupPrintLinks searchTest = new JsoupPrintLinks();
    private int count, numRelative;
    private String searchContent, str_search;;
    private Bundle bundle;
    private ArrayList<WebPage>pages = new ArrayList<WebPage>();
    private EditText etx_search;
    private Translator translatorChinese;
    private Boolean booleanChinese = false;
    private ListAdapter keyadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etx_search = (EditText) findViewById(R.id.search);
        etx_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    //do stuff
                    Intent intent = new Intent();
                    System.out.println("hello");
                    intent.setClass(MainActivity2.this, MainActivity2.class);
                    Bundle bundle2 = new Bundle();
                    str_search = etx_search.getText().toString();

//                    TranslatorOptions translatorOptionsChinese = new TranslatorOptions.Builder()
//                            .setSourceLanguage(TranslateLanguage.ENGLISH)
//                            .setTargetLanguage(TranslateLanguage.CHINESE)
//                            .build();
//
//                    translatorChinese = Translation.getClient(translatorOptionsChinese);
//
//                    downloadModel();
//
//                    if (booleanChinese){
//                        translatorChinese.translate(str_search)
//                                .addOnSuccessListener(
//                                        new OnSuccessListener() {
//                                            @Override
//                                            public void onSuccess(@NonNull Object o) {
//                                                System.out.println(o);
//                                                bundle2.putString("search_content", (String) o);
//                                                intent.putExtras(bundle2);
//                                                startActivity(intent);
//                                            }
//                                        })
//                                .addOnFailureListener(
//                                        new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                // Error.
//                                                // ...
//                                            }
//                                        });
//                    }

                    bundle2.putString("search_content", str_search);
                    intent.putExtras(bundle2);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        bundle = getIntent().getExtras();
        searchContent = bundle.getString("search_content");
        System.out.println(searchContent);

        keywordsList.add(new Keyword(searchContent,10));
        charactersList.add(new Keyword("男神",3));
        charactersList.add(new Keyword("帥",4));
        charactersList.add(new Keyword("古裝",2));
        charactersList.add(new Keyword("中國",1));
        charactersList.add(new Keyword("肌",10));
        charactersList.add(new Keyword("濕身",100));



        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        keywordsView = findViewById(R.id.keywordsview);
        recyclerView.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);



        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.S)
            public void run() {
//                Document doc;

                try {
//


                    urlList = searchTest.getUrl(searchContent);
                    relativesList = searchTest.getKeywords();

                    count = urlList.size();
                    numRelative = relativesList.size();

                    for (int i=0;i<count;i++){
                        titleList.add(searchTest.getTitle(urlList.get(i)));
                    }
                    for (int i=0;i<count;i++){
                        System.out.println(urlList.get(i));
                        System.out.println(titleList.get(i));
                        System.out.println();
                    }
                    System.out.println();
                    for (int i=0; i<numRelative; ++i){
                        System.out.println(relativesList.get(i));
                    }

                    for (int i=0;i<count;i++){
                        pages.add(new WebPage(urlList.get(i), titleList.get(i)));
                    }


                    for(int i=0;i<pages.size();i++) {
                        WebTree tree = new WebTree(pages.get(i));
                        WebPage rootPage0 = pages.get(i);
                        sublinksList = searchTest.getsublinks(rootPage0.url);
                        if(!sublinksList.isEmpty()) {
//
                            ArrayList<WebPage>subpages = new ArrayList<WebPage>();
                            for(int k=0;k<sublinksList.size();k++) {
                                subpages.add(new WebPage(sublinksList.get(k),sublinksList.get(k)));
                                subpages.get(k).setScore(keywordsList, charactersList, null);
                            }

                            for(int p=0;p<subpages.size();p++) {
                                if(subpages.get(p).score>0) {

                                    tree.root.addChild(new WebNode(subpages.get(p)));

                                }
                            }
                        }


                        tree.setPostOrderScore(keywordsList, charactersList, null);
                        tree.eularPrintTree();
                        trees.add(tree);




                    }
                    WebComparator c = new WebComparator();






                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        trees.sort(c);
                    }





                    runOnUiThread( new Runnable()
                    {
                        public void run()
                        {
                            doData();
                            myAdapter = new MyAdapter();
                            recyclerView.setAdapter(myAdapter);
                            String[] values = new String[numRelative];
                            for (int i = 0; i < numRelative; i++){
                                values[i] = relativesList.get(i);
                            }

//                            ListAdapter adapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 ,values);
//                            keyadapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1 ,values);
                            ArrayAdapter keyadapter = new ArrayAdapter<String>(MainActivity2.this,
                                    android.R.layout.simple_list_item_1,
                                    values);

                          keywordsView.setAdapter(keyadapter);




                        }

                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void doData(){

        for (int i=0;i<count;i++){
            HashMap<String,String> row = new HashMap<>();
            row.put("result", trees.get(i).getRoot().getwebPage().getName());
            System.out.println(row.get("result")+trees.get(i).getPostOrderScore());
            data.add(row);
        }
    }
    public void test1(View view) {
    }

    public void test2(View view) {
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        class MyViewHolder extends RecyclerView.ViewHolder{
            public View itemView;
            public TextView result;

            public MyViewHolder(View v){
                super(v);
                itemView = v;

                result = itemView.findViewById(R.id.item_title);
            }
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item,parent,false);
            MyViewHolder vh = new MyViewHolder(itemView);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.result.setText(data.get(position).get("result"));
            final String[] str_url = new String[1];
            holder.result.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    String str_title = data.get(position).get("result");

                    for(int i=0; i<titleList.size(); i++){
                        if(titleList.get(i).equals(str_title)){
                            str_url[0] = urlList.get(i);
                        }
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(str_url[0]));
                    startActivity(browserIntent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

//    private void downloadModel(){
//        DownloadConditions downloadConditions = new DownloadConditions.Builder()
//                .requireWifi()
//                .build();
//        translatorChinese.downloadModelIfNeeded(downloadConditions)
//                .addOnSuccessListener(new OnSuccessListener() {
//                    @Override
//                    public void onSuccess(@NonNull Object o) {
//                        booleanChinese = true;
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        booleanChinese = false;
//                    }
//                });
//    }

}

