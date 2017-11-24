package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.clasence.shu.jokesdisplaylibary.JokesDisplayActivity;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.StartJobActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        //generate a random number corrsponding to joke number

        //number between 1 and 4 inclusive

        new EndpointsAsyncTask(false,this,this,null).execute();
    }


    @Override
    public void startActivy(String result) {
        if (result != null && !result.equalsIgnoreCase("")) {
            Intent intent = new Intent(getApplicationContext(), JokesDisplayActivity.class);
            intent.putExtra("joke", result);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),getString(R.string.load_fail), Toast.LENGTH_LONG).show();
        }
    }
}
