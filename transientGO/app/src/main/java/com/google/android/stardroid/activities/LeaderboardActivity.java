//package com.google.android.stardroid.activities;
//
//import android.app.Activity;
//import android.app.ListActivity;
//import android.nfc.Tag;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ArrayAdapter;
//import com.google.android.stardroid.R;
//import android.widget.ListView;
//import com.google.android.stardroid.util.MiscUtil;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.inject.Inject;
//
///**
// * Created by dominicdewhurst on 11/17/16.
// */
//
//public class LeaderboardActivity extends Activity {
//    //ArrayList<String> listItems = new ArrayList<String>();
//   // ArrayAdapter<String> adapter;
//    String [] array = {"test1","test2"};
//    private static final String TAG = MiscUtil.getTag(LeaderboardActivity.class);
//    ListView listView;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.leaderboard);
//            listView = (ListView) findViewById(R.id.list1);
//
//
//            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.leaderboard, R.id.username, array);
//            ListView list1 = (ListView) findViewById(R.id.list1);
//            list1.setAdapter(adapter);
//
//       /* ListView list2 = (ListView) findViewById(R.id.list2);
//        ListView list3 = (ListView) findViewById(R.id.list3);
//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
//        list1.setAdapter(adapter);
//        list2.setAdapter(adapter);
//        list3.setAdapter(adapter);
//        //setListAdapter(adapter);
//*/
//
//    }
///*
//    @Override
//    public void onResume() {
//        Log.d(TAG,"here6");
//        super.onResume();
//        Log.d(TAG,"here7");
//    }
//
//
//    @Override
//    public void onStart() {
//        Log.d(TAG,"here8");
//        super.onStart();
//        Log.d(TAG,"here9");
//        analytics.trackPageView(Analytics.LEADERBOARD_ACTIVITY);
//        Log.d(TAG,"here10");
//    }
//
//    @Override
//    public void onPause() {
//        Log.d(TAG,"here11");
//        super.onPause();
//        Log.d(TAG,"here12");
//    }*/
//}
