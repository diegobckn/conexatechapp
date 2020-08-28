package com.diegobckn.appconexa;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diegobckn.appconexa.db.AppDataBase;
import com.diegobckn.appconexa.db.Config;
import com.diegobckn.appconexa.db.entities.Agenda;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText et_name, et_lastName, et_email, et_phone, et_homeAddress;
    Button bt_showCount;
    AppDataBase db;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = findViewById(R.id.et_name);
        et_lastName = findViewById(R.id.et_lastName);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        et_homeAddress = findViewById(R.id.et_homeAddres);
        bt_showCount = findViewById(R.id.bt_showCount);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        db = Room.databaseBuilder(this,AppDataBase.class, Config.DB_NAME).allowMainThreadQueries().build();

        long count = db.agendaDao().count();
        if(count>0){
            bt_showCount.setVisibility(View.VISIBLE);
        }
    }

    public void save(View view){
        if(
                et_name.getText().toString().isEmpty()
                        || et_lastName.getText().toString().isEmpty()
                        || et_email.getText().toString().isEmpty()
                        || et_phone.getText().toString().isEmpty()
                        || et_homeAddress.getText().toString().isEmpty()
        ){
            Toast.makeText(this,R.string.error_some_empty,Toast.LENGTH_LONG).show();
            return;
        }

        Agenda agenda = new Agenda();
        agenda.setName(et_name.getText().toString());
        agenda.setLastName(et_lastName.getText().toString());
        agenda.setEmail(et_email.getText().toString());
        agenda.setPhone(et_phone.getText().toString());
        agenda.setHomeAddress(et_homeAddress.getText().toString());

        long result = db.agendaDao().insert(agenda);
        if(result>0){
            Toast.makeText(this,R.string.info_add_ok,Toast.LENGTH_LONG).show();
            bt_showCount.setVisibility(View.VISIBLE);
            et_name.setText("");
            et_lastName.setText("");
            et_email.setText("");
            et_phone.setText("");
            et_homeAddress.setText("");
            et_name.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInput(et_name, InputMethodManager.SHOW_IMPLICIT);
        }else{
            Toast.makeText(this,R.string.error_not_add,Toast.LENGTH_LONG).show();
        }
    }

    public void showCount(View view){
        long count = db.agendaDao().count();
        String msg = getString(R.string.info_count);
        msg = msg.replace("{{count}}",String.valueOf(count));

        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}