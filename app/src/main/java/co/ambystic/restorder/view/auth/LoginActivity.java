package co.ambystic.restorder.view.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.ambystic.restorder.MainActivity;
import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.login.LoginResponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import co.ambystic.restorder.util.SpManager;
import co.ambystic.restorder.util.Util;
import co.ambystic.restorder.view.fragment.owner.OwnerReportActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity implements Util {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText edtUsername, edtPassword;
    private Button btnLogin;

    private String username, password;

    private SpManager spManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spManager = new SpManager(this);

        sessionCheck();

        initLayout();

        onItemClicked();
    }

    private void sessionCheck() {
        boolean isLoggedIn = spManager.getBoolData(SpManager.IS_LOGGED_IN);
        int userLevel = spManager.getIntData(SpManager.LEVEL_USER);
        if (isLoggedIn) {
            if (userLevel == 1) {
                //Intent to ownerActivity
                goToOwnerActivity();
            } else {
                //Intent to mainActivity
                goToMainActivity();
            }
        }
    }

    private void onItemClicked() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputValue();
                if (isInputEmpty()) {
                    Toast.makeText(LoginActivity.this, "Isi semua data", Toast.LENGTH_SHORT).show();
                } else {
                    DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                    Call<LoginResponse> loginResponseCall = dataService.login(
                            username,
                            password
                    );
                    loginResponseCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    boolean isError = response.body().getServerResponseLogin().getIsError();
                                    if (isError) {
                                        Toast.makeText(LoginActivity.this, "Username atau Password salah", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String username = response.body().getServerResponseLogin().getLoginData().getUsername();
                                        String fullName = response.body().getServerResponseLogin().getLoginData().getNamaUser();
                                        int level = Integer.valueOf(response.body().getServerResponseLogin().getLoginData().getIdLevel());
                                        int idUser = Integer.valueOf(response.body().getServerResponseLogin().getLoginData().getIdUSer());
                                        int tableNo = Integer.valueOf(response.body().getServerResponseLogin().getLoginData().getNoMeja());

                                        //Save user session
                                        spManager.setBoolData(SpManager.IS_LOGGED_IN, true);

                                        //Save data to local storage
                                        spManager.setStringData(SpManager.FULL_NAME, fullName);
                                        spManager.setStringData(SpManager.USERNAME, username);
                                        spManager.setIntData(SpManager.LEVEL_USER, level);
                                        spManager.setIntData(SpManager.ID_USER, idUser);

                                        //Save tableNo to session if user logged in
                                        if (level == 5){
                                            spManager.setIntData(SpManager.TABLE_NO, tableNo);
                                        }

                                        //if the user is owner, intent to his activity
                                        if (level == 1) {
                                            goToOwnerActivity();
                                        } else {
                                            //Intent to mainActivity
                                            goToMainActivity();
                                        }
                                    }
                                } else {
                                    Log.i(TAG, "onResponseLogin: empty body response from server");
                                }
                            } else {
                                call.cancel();
                                Toast.makeText(LoginActivity.this, "Password atau Username Salah", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                            Toast.makeText(LoginActivity.this, "Password atau Username Salah", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onLoginFailure: " + t.getMessage());
                            Log.e(TAG, "onLoginFailure: ", t.getCause());
                        }
                    });
                }
            }
        });

    }

    private void goToOwnerActivity() {
        Intent intent = new Intent(LoginActivity.this, OwnerReportActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void getInputValue() {
        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();
    }

    //Inflate required component from layout
    private void initLayout() {
        edtPassword = findViewById(R.id.edtPassword);
        edtUsername = findViewById(R.id.edtUsername);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    public boolean isInputEmpty() {
        return username.trim().isEmpty() || password.trim().isEmpty();
    }

    @Override
    public void buttonClicked() {
    }
}
