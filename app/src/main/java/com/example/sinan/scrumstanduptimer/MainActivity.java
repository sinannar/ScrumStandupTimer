package com.example.sinan.scrumstanduptimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> teamMembers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teamMembers = new ArrayList<String>();
        setContentView(R.layout.activity_main);
        EditText editText = (EditText) findViewById(R.id.MemberName);
        editText.setText("");
    }

    public void ClickStartMeeting(View view) {
        Intent intent = new Intent(this, TimingMembersActivity.class);
        intent.putExtra("STRING_I_NEED", TextUtils.join(";",teamMembers.toArray()));
        startActivity(intent);
    }

    public void AddTeamMember(View view){
        EditText editText = (EditText) findViewById(R.id.MemberName);
        teamMembers.add(editText.getText().toString());
        editText.setText("");
    }
}
