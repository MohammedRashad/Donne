package ieee.donn.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.TimeUnit;

import ieee.donn.Main.MainActivity;
import ieee.donn.R;

public class PhoneAuthActivity extends AppCompatActivity  implements View.OnClickListener{
    private static final String TAG = "PhoneAuthActivity";
    private FirebaseAuth mAuth;

    private EditText etPhone;

    private EditText etOTP;
    private Button btnSignIn,btnVerify,btnResend;
    public String phoneNumber;
    private ConstraintLayout phoneLayout,otpLayout;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerifying;
    private PhoneAuthProvider.ForceResendingToken mToken;
    private FirebaseUser mUser;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        mAuth = FirebaseAuth.getInstance();

        etPhone = findViewById(R.id.et_phone);
        etOTP = findViewById(R.id.et_otp);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnVerify = findViewById(R.id.btn_verify);
        btnResend = findViewById(R.id.btn_resend);

        phoneLayout = findViewById(R.id.layout_phone);
        otpLayout = findViewById(R.id.layout_verify_otp);

        btnSignIn.setOnClickListener(this);
        btnResend.setOnClickListener(this);
        btnVerify.setOnClickListener(this);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG,"Verification complete: "+phoneAuthCredential);

                mVerifying = false;
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.d(TAG,"Verfication FAILED!!");

                mVerifying = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException){
                    etPhone.setError("Invalid phone number");
                }
                else if (e instanceof FirebaseTooManyRequestsException){
                    Toast.makeText(PhoneAuthActivity.this,"Quota Exceeded",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                toggleViews();
                mVerificationId = verificationId;
                mToken = forceResendingToken;
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        //Check if user is already signed in
        mUser = mAuth.getCurrentUser();
        if (mUser!=null){
            //Already signed in
            Log.i(TAG,"Already signed in");

            startActivity(new Intent(PhoneAuthActivity.this,MainActivity.class));
            finish();
        }

    }

    private void toggleViews() {
        phoneLayout.setVisibility(phoneLayout.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
        otpLayout.setVisibility(otpLayout.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG,"Login successful!");
                            mUser = task.getResult().getUser();
                            final String userId = mUser.getUid();

                            etOTP.setText(phoneAuthCredential.getSmsCode());

                            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                            database.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        //User already exists
                                        //Subscribe topics and
                                        //Switch to main activity

                                        Log.d(TAG,"User exists!");
                                        FirebaseMessaging.getInstance().subscribeToTopic("Ap");
                                        FirebaseMessaging.getInstance().subscribeToTopic("Am");
                                        FirebaseMessaging.getInstance().subscribeToTopic("Bp");
                                        FirebaseMessaging.getInstance().subscribeToTopic("Bm");
                                        FirebaseMessaging.getInstance().subscribeToTopic("Op");
                                        FirebaseMessaging.getInstance().subscribeToTopic("Om");
                                        FirebaseMessaging.getInstance().subscribeToTopic("ABp");
                                        FirebaseMessaging.getInstance().subscribeToTopic("ABm");

                                        startActivity(new Intent(PhoneAuthActivity.this,MainActivity.class));
                                        finish();
                                    }
                                    else{
                                        //User doesn't exist
                                        //Show join network fragment
                                        Log.d(TAG,"New user created!");
                                        Intent intent = new Intent(PhoneAuthActivity.this,JoinNetworkActivity.class);
                                        intent.putExtra("user_id",userId);
                                        intent.putExtra("phone",phoneNumber);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }
                        else{
                            Log.d(TAG,"Login FAILED!!");

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Log.e(TAG,"Invalid OTP code");
                            }

                            etOTP.setError("Invalid OTP Code");
                        }
                    }
                });
    }

    private void startVerificationWithPhoneNumber(String phoneNumber) {
        Log.d(TAG,"Sending otp to phone: "+phoneNumber);

        if (validatePhoneNumber(phoneNumber)){
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    mCallbacks
            );
        }

        mVerifying = true;
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)){
            etPhone.setError("Phone number can't be empty.");
            return false;
        }
        return true;
    }

    private void resendOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                etPhone.getText().toString(),
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks,
                mToken
        );

        mVerifying = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_sign_in:
                phoneNumber = etPhone.getText().toString();
                startVerificationWithPhoneNumber(phoneNumber);
                break;
            case R.id.btn_verify:
                String code = etOTP.getText().toString();
                if (TextUtils.isEmpty(code)){
                    etOTP.setError("Code can't be empty.");
                    return;
                }

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);
                signInWithPhoneAuthCredential(credential);
                break;
            case R.id.btn_resend:
                resendOTP();
                break;
        }
    }
}
