package com.jimmy.uabcs.rxbibliouabcs.android;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jimmy.uabcs.rxbibliouabcs.R;
import com.jimmy.uabcs.rxbibliouabcs.models.LoginResponse;
import com.jimmy.uabcs.rxbibliouabcs.models.UserLogin;
import com.jimmy.uabcs.rxbibliouabcs.utils.PrefsUtils;
import com.jimmy.uabcs.rxbibliouabcs.utils.Utils;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.LibraryViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.jimmy.uabcs.rxbibliouabcs.utils.Utils.showProgress;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Utils.startActivityHome;

public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {

    private PrefsUtils mPrefsUtils;
    @BindView(R.id.login_form)
    View loginFormView;
    @BindView(R.id.login_progress)
    View progressView;
    @BindView(R.id.email)
    AutoCompleteTextView emailTextView;
    @BindView(R.id.password)
    EditText passwordTextView;
    @BindView(R.id.signUp)
    TextView signUpTextView;

    LibraryViewModel mLibraryViewmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mLibraryViewmodel = LibraryViewModel.getInstance();
        mPrefsUtils = new PrefsUtils();
        passwordTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL) {
                    initLogin();
                    return true;
                }
                return false;
            }
        });

        signUpTextView.setPaintFlags(signUpTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Linkify.addLinks(signUpTextView, Linkify.ALL);

        signUpTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LoginActivity", "Sign Up Activity activated.");
                Utils.startActivity(LoginActivity.this, RegisterActivity.class);
            }
        });
    }

    @OnClick(R.id.email_sign_in_button)
    public void startLogin() {
        initLogin();
    }

    public void initLogin() {
        final int shortTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        emailTextView.setError(null);
        passwordTextView.setError(null);

        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        boolean cancelLogin = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            passwordTextView.setError(getString(R.string.field_required));
            focusView = passwordTextView;
            cancelLogin = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailTextView.setError(getString(R.string.field_required));
            focusView = emailTextView;
            cancelLogin = true;
        } else if (!isEmailValid(email)) {
            emailTextView.setError(getString(R.string.invalid_email));
            focusView = emailTextView;
            cancelLogin = true;
        }

        if (cancelLogin) {
            // error in login
            focusView.requestFocus();
        } else {
            // show progress spinner, and start background task to login
            showProgress(true, loginFormView, shortTime, progressView);

            UserLogin login = new UserLogin();
            login.setPassword(password);
            login.setUsername(email);
            login.setGrant_type("password");
            mLibraryViewmodel.login(login)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnNext(loginResponse -> {
                        showProgress(false, loginFormView, shortTime, progressView);

                        if (loginResponse != null) {
                            mPrefsUtils.saveUserSession(loginResponse);
                            startActivityHome(LoginActivity.this, MainActivity.class);
                        } else {
                            // login failure
                            passwordTextView.setError(getString(R.string.incorrect_password));
                            passwordTextView.requestFocus();
                        }
                    })
                    .doOnError(e -> {
                        showProgress(false, loginFormView, shortTime, progressView);
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.error_login,
                                Toast.LENGTH_SHORT);
                        toast.show();
                    })
                    .subscribe(h -> {}, e -> Utils.showToast(getApplicationContext(), getString(R.string.error_register)));
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        emailTextView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}