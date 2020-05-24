package com.example.android.moviesdiscovery;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.android.moviesdiscovery.R;


public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private String mOrderBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        //TODO if sort_by ="favourites", create an intent to lauch the favourite activity
        setupSharedPreferences();
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mOrderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        Log.v("**onCreate, orderBy", mOrderBy);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.v("**onSharedChanged",key);
        if (key.equals(getString(R.string.settings_order_by_key))) {
            mOrderBy = sharedPreferences.getString(
                    getString(R.string.settings_order_by_key),
                    getString(R.string.settings_order_by_default));
            Log.v("**onSharedChanged",mOrderBy);
            if (mOrderBy.equals("Favourites")){
                Context context = this;

                Class destinationClass = ActivityFavourite.class;
                Intent intentToStartDetailActivity = new Intent(context, destinationClass);
                startActivity(intentToStartDetailActivity);
            }
        }
    }
}

