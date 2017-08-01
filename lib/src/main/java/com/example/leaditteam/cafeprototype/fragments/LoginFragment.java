package com.example.leaditteam.cafeprototype.fragments;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leaditteam.cafeprototype.activities.MainProductsActivity;
import com.example.leaditteam.cafeprototype.activities.RegistrationActivity;
import com.example.leaditteam.cafeprototype.CafePrototype;
import com.example.leaditteam.cafeprototype.activities.RetrieveEmailActivity;
import com.example.leaditteam.cafeprototype.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

import static com.example.leaditteam.cafeprototype.activities.LoginActivity.ACTIVITY_RESULT_AUTH;

/**
 * Created by leaditteam on 10.03.17.
 */

public class LoginFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private LinearLayout mRegister;
    private TextView mLogin;

    private EditText mEmail;
    private EditText mPass;

    private ProgressDialog mProgress;

    private FirebaseAuth mAuth;

    private GoogleSignInOptions mGoogleSignInOptions;

    private LoginButton mLoginButtonFb;

    public static CallbackManager mCallbackManager;

    public static GoogleApiClient mGoogleApiClient;

    private CardView mCardView;
    private View mViewCircle;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.autorization_fragment, container, false);

        //initialization element for login
        mEmail = (EditText) v.findViewById(R.id.log_in_email);
        mPass = (EditText) v.findViewById(R.id.log_in_pass);
        mCardView = (CardView) v.findViewById(R.id.cv_login);
        mViewCircle = (View) v.findViewById(R.id.circle);

        //on click
        mRegister = (LinearLayout) v.findViewById(R.id.textView_registration_autorizationFragment);
        mRegister.setOnClickListener(this);

        mLogin = (TextView) v.findViewById(R.id.textView_login_autorizationFragment);
        mLogin.setOnClickListener(this);
        
        String googleApi = ((CafePrototype)getActivity().getApplication()).getGoogleAuthStringApiRes();
        // Configure Google Sign In
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(googleApi)
                .requestEmail()
                .build();

        ImageView googImg = (ImageView) v.findViewById(R.id.plus);
        googImg.setOnClickListener(this);

        //facebook
        FacebookSdk.sdkInitialize(v.getContext());
        mLoginButtonFb = (LoginButton) v.findViewById(R.id.fb_f);

        ImageView fbLogin = (ImageView) v.findViewById(R.id.fb);
        fbLogin.setOnClickListener(this);

        //vk
        ImageView vkLogin = (ImageView) v.findViewById(R.id.vk);
        vkLogin.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {

        int i1 = v.getId();
        if (i1 == R.id.textView_registration_autorizationFragment) {
            Intent i = new Intent(this.getContext(), RetrieveEmailActivity.class);
            startActivity(i);
            return;
        } else if (i1 == R.id.textView_login_autorizationFragment) {
            login(mViewCircle, mCardView);
            return;
        } else if (i1 == R.id.plus) {
            loginWithGoogle();
            return;
        } else if (i1 == R.id.fb) {
            loginWithFb(mLoginButtonFb);
            return;
        } else if (i1 == R.id.vk) {
            loginWithVk();
            return;
        } else {
            return;
        }

    }

    private void loginWithFb(LoginButton loginButton) {
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("", "facebook:onError", error);
            }
        });
        loginButton.performClick();
    }

    private void loginWithVk() {
        VKSdk.login(getActivity(), new String[]{VKScope.EMAIL, VKScope.STATS, VKScope.PAGES});
    }


    private void loginWithGoogle() {
        // Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        getActivity().startActivityForResult(signInIntent, ACTIVITY_RESULT_AUTH);
    }

    private void handleFacebookAccessToken(final AccessToken acct) {
        Log.d("", "handleFacebookAccessToken:" + acct);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(acct.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("", "signInWithCredential:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.d("FBAUTH____", "signInWithCredential", task.getException());
                        } else {
                            try {
                                // Sending side
                                String newString = auth.getInstance().getCurrentUser().getUid();//getEmail().toString().replace(".", "_").toLowerCase();

                                RegistrationActivity.pushAnotherData(auth.getInstance().getCurrentUser().getEmail().toString(),
                                        auth.getInstance().getCurrentUser().getDisplayName().toString(), "", "", newString);
                            } catch (Exception e) {
                            }
                        }

                    }
                });
    }

    private void login(final View view, CardView card) {
        animate(view, card);
        //Get Firebase mAuth instance
        mAuth = FirebaseAuth.getInstance();

        final String email = mEmail.getText().toString();
        final String password = mPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity().getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity().getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        mProgress = new ProgressDialog(getActivity());
        mProgress.setMessage("Wait while we try log in..");
        mProgress.setCancelable(false);
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.show();
        //authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the mAuth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        mProgress.dismiss();
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 4) {
                                mPass.setError("Please write minimum 4 letters");
                            } else {
                                Toast.makeText(getActivity(), "Authentication error", Toast.LENGTH_LONG).show();
                            }
                        } else {
                          startActivity(new Intent(getActivity(), MainProductsActivity.class));
                        }
                    }
                });
    }


    private void animate(final View view, CardView card) {

        int cx = card.getMeasuredWidth();
        int cy = card.getMeasuredHeight();

        // get the final radius for the clipping circle
        int finalRadius = Math.max(card.getWidth(), card.getHeight()) / 2;
        view.animate().scaleYBy(cy).scaleXBy(cx).setDuration(900).alpha(0f).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setAlpha(0.3f);
                view.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setScaleX(1);
                view.setScaleY(1);
                view.setAlpha(0.5f);
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}