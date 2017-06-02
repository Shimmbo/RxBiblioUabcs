package com.jimmy.uabcs.rxbibliouabcs.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.jimmy.uabcs.rxbibliouabcs.R;

public class Utils {
    public static void startActivity(Context context, Class clazz) {
        Intent mIntent = new Intent(context, clazz);
        Bundle options = new Bundle();
        ActivityCompat.startActivity((Activity) context, mIntent, options);
    }

    public static void startActivityHome(Context context, Class clazz) {
        Activity activity = (Activity) context;
        Intent mIntent = new Intent(context, clazz);
        mIntent.addCategory(Intent.CATEGORY_HOME);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle options = new Bundle();
        ActivityCompat.finishAfterTransition(activity);
        ActivityCompat.startActivity(activity, mIntent, options);
    }

    public static void enableHomeAsUp(ActionBar toolbar) {
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static void showProgress(final boolean show, final View mScrollView, int shortAnimTime, final View mProgressBar) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            mScrollView.setVisibility(show ? View.GONE : View.VISIBLE);
            mScrollView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mScrollView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            mScrollView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public static void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void startFragment(FragmentManager frg, Fragment fragment) {
        frg.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();
    }
}
