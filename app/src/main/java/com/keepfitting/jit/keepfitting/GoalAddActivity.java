package com.keepfitting.jit.keepfitting;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GoalAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_add);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        FragmentAddGoal fragmentAddGoal=new FragmentAddGoal();

        fragmentTransaction.add(R.id.ll_main,fragmentAddGoal);
        fragmentTransaction.commit();
    }
}
