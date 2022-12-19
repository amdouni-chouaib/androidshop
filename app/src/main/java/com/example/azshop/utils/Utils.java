package com.example.azshop.utils;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Utils {

    public static void setCurrentFragment(Fragment fragment , AppCompatActivity activity, int resId){
        activity.getSupportFragmentManager().beginTransaction().
                replace(resId, fragment).
                addToBackStack(fragment.getClass().getName()).
                commit();


    }


}
