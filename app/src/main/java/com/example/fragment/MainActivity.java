package com.example.fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FrameLayout flTest;
    Button btnLogin;
    EditText edtUserName,editPass;
    LinearLayout llLayout;
    ConstraintLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnLogin = findViewById(R.id.btnLogin);
        flTest = findViewById(R.id.flTest);
        llLayout=findViewById(R.id.llLayout);

        flTest.setVisibility(View.GONE);
        edtUserName = findViewById(R.id.edtUserName);
        editPass=findViewById(R.id.edtPass);
        ActionBar sc = getSupportActionBar();
        sc.setTitle("Food Order");
        sc.hide();

//        mainLayout = (ConstraintLayout)findViewById(R.id.myLayout);
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flTest.setVisibility(View.VISIBLE);
                ProductFragment productFragment = ProductFragment.newInstance(edtUserName.getText().toString());
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flTest, productFragment, productFragment.getTag()).commit();
                btnLogin.setVisibility(View.GONE);
                edtUserName.setVisibility(View.GONE);
                editPass.setVisibility(View.GONE);
                llLayout.setVisibility(View.GONE);
//                edtUserName.onEditorAction(EditorInfo.IME_ACTION_DONE);
                hideKeyboard(view);
            }
        });

    }

    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }


}
