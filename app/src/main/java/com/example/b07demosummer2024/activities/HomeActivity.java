package com.example.b07demosummer2024.activities;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.b07demosummer2024.R;
import com.example.b07demosummer2024.fragments.AddItemFragment;
import com.example.b07demosummer2024.fragments.LogoutPopup;
import com.example.b07demosummer2024.fragments.RecyclerViewFragment;
import com.example.b07demosummer2024.fragments.LoginPopup;
import com.example.b07demosummer2024.utilities.Database;
import com.example.b07demosummer2024.utilities.Preferences;

public class HomeActivity extends AppCompatActivity {

    private boolean isAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("WOW10", "activity commenced");

        setContentView(R.layout.activity_home);

        // initialize spinner and adapter (used for dropdown)
        Spinner viewSpinner = findViewById(R.id.actionSpinner);

        //testing different spinner layouts depending on admin or not (can delete later)
        //previously the adapter was initialized based off of the old login
        ArrayAdapter<CharSequence> adapter;

        isAdmin = Preferences.checkLogin(this);
        if (isAdmin){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.adminActions, android.R.layout.simple_spinner_item);
        }
        else {
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.userActions, android.R.layout.simple_spinner_item);
        }


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewSpinner.setAdapter(adapter);

        viewSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    // load Home Fragment
                    Log.d("WOW", String.valueOf(Preferences.checkLogin(getApplicationContext())));
                    loadFragment(new RecyclerViewFragment());
                    Log.d("WOW2", "recycler view made");

                }
                else if (position == 1){
                    // load Search fragment
                    loadFragment(new RecyclerViewFragment());
                }
                else if (position == 2){

                    if (isAdmin){
                        // load Add fragment if
                        loadFragment(new AddItemFragment());
                    }
                    else{
                        // load report fragment
                    }


                }
                else if (position == 3){
                    // load Remove (ie. set deleteMode to be true)

                    //currently commenting this out, as its causing some overlap errors
//                    RecyclerViewFragment recycle = new RecyclerViewFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("deleteMode", true);
//                    recycle.setArguments(bundle);
//                    loadFragment(recycle);
                }
                else if (position == 4){
                    // load Report fragment
                }
                else{
                    // load back fragment
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        //when you press back, kill this activity
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 1) {
            // pop the back stack to go back to the previous fragment (changed to > 1)
            fragmentManager.popBackStack();
        } else {
            // when no fragments in back stack (go back to login - MainActivity)
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            finish();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



}