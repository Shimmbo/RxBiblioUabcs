package com.jimmy.uabcs.rxbibliouabcs.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.jimmy.uabcs.rxbibliouabcs.R;
import com.jimmy.uabcs.rxbibliouabcs.models.User;
import com.jimmy.uabcs.rxbibliouabcs.utils.Utils;
import com.jimmy.uabcs.rxbibliouabcs.viewmodels.LibraryViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.jimmy.uabcs.rxbibliouabcs.utils.Utils.showProgress;
import static com.jimmy.uabcs.rxbibliouabcs.utils.Utils.startActivityHome;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.lastname)
    EditText lastname;
    @BindView(R.id.lastname2)
    EditText lastname2;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirmPassword)
    EditText confirmPassword;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.controlNumber)
    EditText controlNumber;
    @BindView(R.id.register_form)
    View registerForm;
    @BindView(R.id.register_progress)
    View registerProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLinkToLoginScreen)
    public void backToLogin() {
        Utils.startActivity(RegisterActivity.this, LoginActivity.class);
    }

    @OnClick(R.id.btn_register)
    public void register() {
        final int shortTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        User mUser = new User();
        mUser.setAddress(" ");
        mUser.setControlNumber(controlNumber.getText().toString());
        mUser.setEmail(email.getText().toString());
        mUser.setLastName_1(lastname.getText().toString());
        mUser.setLastName_2(lastname2.getText().toString());
        mUser.setName(name.getText().toString());
        mUser.setPhone(" ");
        mUser.setPassword(password.getText().toString());
        mUser.setConfirmPassword(confirmPassword.getText().toString());

        boolean cancelRegister = false;
        View focusView = null;


        if (cancelRegister) {
            // error in login
            focusView.requestFocus();
        } else {
            // show progress spinner, and start background task to login
            showProgress(true, registerForm, shortTime, registerProgress);
            LibraryViewModel.getInstance().register(mUser)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnNext(generalResponse -> {
                        showProgress(false, registerForm, shortTime, registerProgress);
                        if (generalResponse.isSuccess()) {
                            Utils.showToast(getApplicationContext(), getString(R.string.register_success));
                            startActivityHome(RegisterActivity.this, LoginActivity.class);
                        } else {
                            Utils.showToast(getApplicationContext(), generalResponse.getMessage());
                        }
                    })
                    .doOnError(e -> {
                        showProgress(false, registerForm, shortTime, registerProgress);
                        Utils.showToast(getApplicationContext(), getString(R.string.error_register));
                    })
                    .subscribe(h -> {}, e -> Utils.showToast(getApplicationContext(), getString(R.string.error_register)));
        }
    }
}
