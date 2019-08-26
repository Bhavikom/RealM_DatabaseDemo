package com.baculsoft.sample.realmmigration.views;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baculsoft.sample.realmmigration.R;
import com.baculsoft.sample.realmmigration.model.User;
import com.baculsoft.sample.realmmigration.realm.UserData;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;

/**
 * @author Budi Oktaviyan Suryanto (budioktaviyans@gmail.com)
 */
public class MainActivity extends AppCompatActivity {
    EditText edittextAgeEmail;
    EditText edittextAgeName;
    EditText edittextAgeAge;
    Button btnRetrive,btnUpdate;

    ArrayList<UserData> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);

        btnUpdate = findViewById(R.id.btn_update);
        btnRetrive = findViewById(R.id.btn_retrive);
        edittextAgeEmail = (EditText) findViewById(R.id.edittext_email);
        edittextAgeName = (EditText) findViewById(R.id.edittext_name);
        edittextAgeAge = (EditText) findViewById(R.id.edittext_age);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                final User user = new User();
                user.setEmail("bhavik@b.com");
                user.setName("bhavik");
                user.setAge(30);

                final Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(final Realm realm) {
                        final UserData userData = new UserData();
                        userData.fill(user);

                        /* adding data to database*/
                        realm.insertOrUpdate(userData);
                    }
                });

                //realm.close();
            }

        },0,5000);//Update text every second

        btnRetrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* reading data from database */
                final Realm realm = Realm.getDefaultInstance();

                arrayList.addAll(realm.where(UserData.class).findAll()); // adding all record to database

                UserData data = realm.where(UserData.class).findFirst();
                if (null != data) {
                    data = realm.copyFromRealm(data);

                    final User user = new User();
                    user.setEmail(data.getEmail());
                    user.setName(data.getName());
                    user.setAge(data.getAge());
                    Toast.makeText(MainActivity.this, String.format("Email: %s, Name: %s and Age: %s", user.getEmail(), user.getName(), user.getAge()), Toast.LENGTH_LONG).show();
                }
            }
        });


        Button button = (Button) findViewById(R.id.btn_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final String email = edittextAgeEmail.getText().toString();
                final String name = edittextAgeName.getText().toString();
                final int age = Integer.parseInt(edittextAgeAge.getText().toString());


                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(name)) {
                    final User user = new User();
                    user.setEmail(email);
                    user.setName(name);
                    user.setAge(age);

                    final Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(final Realm realm) {
                            final UserData userData = new UserData();
                            userData.fill(user);

                            /* adding data to database*/
                            realm.insertOrUpdate(userData);
                        }
                    });

                    realm.close();

                    Toast.makeText(MainActivity.this, "Success save data...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void updateRecord(){

    }

}