package com.example.joan.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.joan.myapplication.database.model.CaseConsultModel;
import com.example.joan.myapplication.database.model.JudgementModel;
import com.example.joan.myapplication.database.model.LawModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class CaseConsultResultActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private Button back, favor;
    private TextView num;
    private ViewPager pager;
    private List<Fragment> list;
    private CaseResultSimilarListFragment similarList;
    private CaseResultReferListFragment referList;
    private CaseConsultModel result;
    private String resultID;
    private int state = 0;
    private AlertDialog.Builder alert;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_consult_result);

        x.Ext.init(getApplication());
//        getData();
//        while (state == 0){}
        initItems();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
        }else{
        }

    }

    protected void initItems(){

//        mongoDb = new MongoDBUtil("wxby");
//        collection = mongoDb.getCollection("case_consult");

        back = findViewById(R.id.consult_case_result_back);
        favor = findViewById(R.id.consult_case_result_favor);
        tabLayout = findViewById(R.id.consult_case_result_tablayout);
        pager = findViewById(R.id.consult_case_result_pager);
        num = findViewById(R.id.consult_case_result_number);
//        bar = findViewById(R.id.consult_case_result_percent);
//        percent = findViewById(R.id.consult_case_result_number);

        back.setOnClickListener(this);
        favor.setOnClickListener(this);

        similarList = new CaseResultSimilarListFragment();
        referList = new CaseResultReferListFragment();

        list = new ArrayList<>();
        list.add(similarList);
        list.add(referList);
        pager.setAdapter(new CaseConsultAdapter(getSupportFragmentManager(),list));
        alert = new AlertDialog.Builder(CaseConsultResultActivity.this);

        getData();

//        setSimilarData();
//        setReferData();

        result = new CaseConsultModel();

        initTabLayout();
        initTabLayoutData();

    }

    private void initTabLayoutData() {
    }

//    private void initBar() {
//
//        bar.setIndeterminate(false);
//        bar.setProgress(result.percentage);
//        percent.setText(String.valueOf(result.percentage/100));
//
//    }

    protected void initTabLayout(){

        tabLayout.setupWithViewPager(pager);

        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText(R.string.consult_case_result_similar));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.consult_case_result_refer));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    protected void getData(){

        Intent intent = getIntent();
        resultID = intent.getStringExtra("id");

//        cursor = collection.find(new Document("case_id", "2018092123350067876787678")).iterator();
//        System.out.println(cursor.next().get("case_id"));

        final CaseConsultModel[] data = new CaseConsultModel[1];

        try {
            RequestParams params = new RequestParams("http://169.254.219.229:8080/caseConsultResult.action");
            params.addQueryStringParameter("case_id", "201809191738DocherPap");
//            System.out.println(params.toString());
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = (JsonObject) jsonParser.parse(s);

                    if (jsonObject.get("state").getAsInt() == 1) {

                        data[0] = new CaseConsultModel();
                        //                    System.out.println(s);
                        //                    System.out.println("SetState");
                        data[0].setState(jsonObject.get("state").getAsInt());
                        //                    System.out.println("SetResult");
                        data[0].setResult(jsonObject.get("result").getAsString());
                        //                    System.out.println("SetID");
                        data[0].setCase_id(jsonObject.get("case_id").getAsString());
                        //                    System.out.println("SetContent");
                        data[0].setContent(jsonObject.get("content").getAsString());

                        List<JudgementModel> similar = new ArrayList<>();
                        //                    System.out.println(jsonObject.getAsJsonArray("similar"));
                        //                    System.out.println();
                        //                    System.out.println();
                        //                    System.out.println();
                        for (JsonElement je : jsonObject.getAsJsonArray("similar")) {
                            JsonObject tempJson = je.getAsJsonObject();
                            JudgementModel temp = new JudgementModel();
                            System.out.println(tempJson);
                            //                        System.out.println();
                            temp.setjId(tempJson.get("j_id").getAsString());
                            temp.setjReason(tempJson.get("j_reason").getAsString());
                            //                        temp.setjContent(tempJson.get("j_content").getAsString());
                            temp.setjDate(tempJson.get("j_date").getAsString());
                            temp.setId(tempJson.get("_id").getAsString());
                            //                        System.out.println(temp.getjId());
                            similar.add(temp);
                        }
                        data[0].setJudgementModels(similar);
                        //                    System.out.println(similar);

                        List<LawModel> refer = new ArrayList<>();
                        for (JsonElement je : jsonObject.getAsJsonArray("refer")) {
                            JsonObject tempJson = je.getAsJsonObject();
                            LawModel temp = new LawModel();
                            System.out.println(tempJson);
                            temp.setId(tempJson.get("_id").toString());
                            temp.setStart(tempJson.get("start").toString());
                            temp.setAbandon(tempJson.get("abandon").toString());
                            temp.setArticle(tempJson.get("article").toString());
                            temp.setContent(tempJson.get("content").toString());
                            temp.setName(tempJson.get("name").toString());
                            if (tempJson.has("end")) {
                                System.out.println("yes");
                                temp.setEnd(tempJson.get("end").toString());
                            }
                            refer.add(temp);
                        }
                        data[0].setLawModels(refer);
                        state = 1;
                        result = data[0];
                        num.setText(result.getResult());
                        //                    System.out.println("setSimilar");
                        setSimilarData();
                        //                    System.out.println("setRefer");
                        setReferData();

                        //                    data[0].setSimilar(jsonObject.get("similar").getAsJsonArray());
                        //                    System.out.println(jsonObject.get("similar"));
                        //                    System.out.println(jsonObject.get("similar").getAsJsonArray());

                    }else if (jsonObject.get("state").getAsInt() == -2){

                        alert.setMessage("您的咨詢還在等待執行，請稍等5至10分鐘~");
                        alert.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.cancel();
                                finish();
                            }
                        });
                        dialog = alert.create();
                        dialog.show();
                        Button confirm = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        confirm.setTextColor(getResources().getColor(R.color.selector_item_color));

                    }else if (jsonObject.get("state").getAsInt() == -1){

                        alert.setMessage("糟糕！您的資訊可能出了一些問題，可能需要重新提交試試！");
                        alert.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.cancel();
                                finish();
                            }
                        });
                        dialog = alert.create();
                        dialog.show();
                        Button confirm = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        confirm.setTextColor(getResources().getColor(R.color.selector_item_color));

                    }else if (jsonObject.get("state").getAsInt() == 0){

                        alert.setMessage("您的咨詢正在進行處理，請稍等3至5分鐘~");
                        alert.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.cancel();
                                finish();
                            }
                        });
                        dialog = alert.create();
                        dialog.show();
                        Button confirm = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        confirm.setTextColor(getResources().getColor(R.color.selector_item_color));

                    }else{

                    }
                }

                @Override
                public void onError(Throwable throwable, boolean b) {

                    alert.setMessage("網絡可能開了會小差……再試試看吧！");
                    alert.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.cancel();
                            finish();
                        }
                    });
                    dialog = alert.create();
                    dialog.show();
                    Button confirm = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    confirm.setTextColor(getResources().getColor(R.color.selector_item_color));

                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
//        result = ;
//        while (state == 0){}
//        System.out.println(data[0].getJudgementModels().get(0).getjId());
        return;

//        MongoDBUtil mongoDb = new MongoDBUtil("wxby");
//        MongoCollection<Document> collection = mongoDb.getCollection("case_consult");
//
//        MongoCursor<Document> cursor = collection.find(new Document("case_id", "2018092123350067876787678")).iterator();
//        CaseConsultModel caseConsult = new CaseConsultModel();
//        Document current_cursor = cursor.next();
//
//        caseConsult.setId(current_cursor.getObjectId("_id"));
//        caseConsult.setContent(current_cursor.getString("content"));
//        caseConsult.setResult(current_cursor.getString("result"));
//        caseConsult.setSimilar(current_cursor.get("similar", new ArrayList<Document>()));
//        caseConsult.setRefer(current_cursor.get("refer", new ArrayList<Document>()));
//        caseConsult.setState(current_cursor.getInteger("state"));
//
//        result = caseConsult;
//
//        System.out.println(resultID);

//        CaseResultData crd = new CaseResultData();
//        result.percentage = 8000;
//        result.similars[0] = crd.new Similar("testTitle1", "testSubTitle1", "testLawyer1");
//        result.similars[1] = crd.new Similar("testTitle2", "testSubTitle2", "testLawyer2");
//        result.similars[2] = crd.new Similar("testTitle3", "testSubTitle3", "testLawyer3");
//        result.reasons[0] = crd.new Reason("testName1");
//        result.reasons[1] = crd.new Reason("testName2");
//        result.reasons[2] = crd.new Reason("testName3");
//        result.refers[0] = crd.new Refer("testRefer1", "testSubTitle1");
//        result.refers[1] = crd.new Refer("testRefer2", "testSubTitle2");
//        result.refers[2] = crd.new Refer("testRefer3", "testSubTitle3");
//        result.compensates = crd.new Compensate(new int[]{10, 10, 10}, new int[]{10, 10, 10}, new int[]{10, 10, 10});

    }

    protected void setSimilarData(){
        similarList = (CaseResultSimilarListFragment)pager.getAdapter().instantiateItem(pager, 0);
//        System.out.println("1111111111111111111111111");
        System.out.println(result.getJudgementModels().get(0).getjId());
        similarList.initData(result.getJudgementModels());
    }

//    protected void setReasonData(){
//        reasonList = (CaseResultReasonListFragment)pager.getAdapter().instantiateItem(pager, 1);
//        reasonList.initData(result.reasons);
//    }

    protected void setReferData(){
        referList = (CaseResultReferListFragment)pager.getAdapter().instantiateItem(pager, 1);
        referList.initData(result.getLawModels());
    }

//    protected void setCompensateData(){
//        compensateList = (CaseResultCompensateListFragment)pager.getAdapter().instantiateItem(pager, 3);
//        compensateList.initData(result.compensates);
//    }

//    protected void setData(){
//
//        setSimilarData();
////        setReasonData();
//        setReferData();
////        setCompensateData();
//
////        System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
////        if (similarList == null) System.out.println("You fault again!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
////        else System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
////        System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
////        similarList.initData(result.similars);
////        LinearLayout ll = similarList.getView().findViewById(R.id.case_consult_result_linear);
////
////        LayoutInflater li = LayoutInflater.from(similarList.getContext());
////        View view = li.inflate(R.layout.case_result_single_similar, null);
//
////        ll.addView(findViewById(R.id.case_result_similar_single_title));
////        ll.addView(findViewById(R.id.case_result_similar_single_title));
////        ll.addView(findViewById(R.id.case_result_similar_single_title));
////
////        tabLayout.setupWithViewPager(pager);
//
////        if (similarList.getView() == null) System.out.println("WTF!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
////        else System.out.println("000000000000000000000000000000000000");
////         LinearLayout ll = similarList.getView().findViewById(R.id.case_consult_result_linear);
////         ll.addView(new case_result_similar_single(this));
////        FragmentManager fm = getSupportFragmentManager();
////        FragmentTransaction ft = fm.beginTransaction();
////        ft.add(R.id.case_consult_result_linear, new CaseResultSimilarFragment());
////        ft.commit();
//    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.consult_case_result_back:
//                Intent intent = new Intent(this, ConsultingActivity.class);
//                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.left, R.anim.left_exit);
                break;

        }

    }
}