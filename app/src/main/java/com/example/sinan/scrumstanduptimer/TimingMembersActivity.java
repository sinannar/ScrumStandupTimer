package com.example.sinan.scrumstanduptimer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class TimingMembersActivity extends AppCompatActivity {

    private List<String> teamMembers;
    private Map<String, Integer> memberTimes;
    private Map<String, Integer> memberPosition;
    private String activeMember;

    private Date date1;
    private Date date2;
    private boolean firstRun = true;

    private TextView timerLabel;
    private Thread thread;
    private ListView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing_members);
        Bundle bundle = getIntent().getExtras();
        final String members = bundle.getString("STRING_I_NEED");
        teamMembers = Arrays.asList(members.split(";"));
        memberTimes = new HashMap<String,Integer>();
        memberPosition = new HashMap<String,Integer>();
        for (String member: teamMembers
             ) {
            memberTimes.put(member,0);
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                teamMembers);
        view = findViewById(R.id.members);
        timerLabel = findViewById(R.id.timerLabel);
        view.setAdapter(adapter);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String stringText= ((TextView)view).getText().toString();

                Log.e("AAA",stringText);
                activeMember = stringText;
                if(memberPosition.get(position) == null)
                {
                    memberPosition.put(stringText,position);
                }
                foo();
            }
        });


       thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                foo();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();

    }

    private void foo(){

        if(activeMember != null)
        {
        if(firstRun)
        {
            date1 = new Date(System.currentTimeMillis());
            firstRun = false;
        }
        else
        {
            date2 = new Date(System.currentTimeMillis());
            long mills = date2.getTime() - date1.getTime();
            if(mills>1000)
            {
                int memberSec = memberTimes.get(activeMember) + (int)mills;
                memberTimes.replace(activeMember,memberSec);
                date1 = date2;
                timerLabel.setText(("  >>  "+activeMember+" : "+memberSec/1000 + "  seconds<<  "));
                int position = memberPosition.get(activeMember);
                TextView v = (TextView) view.getChildAt(position);
                v.setText((activeMember+" : "+memberSec/1000 + "  seconds<<  "));
            }
        }
        }
    }



}
