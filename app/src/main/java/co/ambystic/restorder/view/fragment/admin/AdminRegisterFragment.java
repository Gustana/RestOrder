package co.ambystic.restorder.view.fragment.admin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.register.RegisterReponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import co.ambystic.restorder.util.SpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRegisterFragment extends Fragment {
    private final String TAG = AdminRegisterFragment.class.getSimpleName();
    private Button btnRegister;
    private EditText edtUsername, edtName, edtTableNo, edtPassword;
    private RadioGroup rgLevel;
    private LinearLayout llTableNo;
    private RadioButton rbOwner;

    private int level;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.admin_fragment_register, container, false);

        SpManager spManager = new SpManager(getContext());

        int levelSession = spManager.getIntData(SpManager.LEVEL_USER);

        initLayout(view);
        radioBtnConfig();

        if (levelSession != 4) {
            rbOwner.setVisibility(View.GONE);
        }

        llTableNo.setVisibility(View.GONE);


        buttonClicked();

        return view;
    }

    private void buttonClicked() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String name = edtName.getText().toString();


                if (level == 5) {
                    int tableNo = Integer.parseInt(edtTableNo.getText().toString());
                    DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                    Call<RegisterReponse> registerReponseCall = dataService.registerCostumer(name, username, password, level, tableNo);
                    registerReponseCall.enqueue(new Callback<RegisterReponse>() {
                        @Override
                        public void onResponse(@NonNull Call<RegisterReponse> call, @NonNull Response<RegisterReponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().getServerResponse().isError()) {
                                    Toast.makeText(getContext(), response.body().getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<RegisterReponse> call, @NonNull Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage());
                            Log.e(TAG, "onFailure: ", t.getCause());
                        }
                    });
                } else {
                    DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                    Call<RegisterReponse> registerReponseCall = dataService.register(name, username, password, level);
                    registerReponseCall.enqueue(new Callback<RegisterReponse>() {
                        @Override
                        public void onResponse(@NonNull Call<RegisterReponse> call, @NonNull Response<RegisterReponse> response) {
                            Log.i(TAG, "onResponse: " + response.body().toString());
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().getServerResponse().isError()) {
                                    Toast.makeText(getContext(), response.body().getServerResponse().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<RegisterReponse> call, @NonNull Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage());
                            Log.e(TAG, "onFailure: ", t.getCause());
                        }
                    });
                }
            }
        });
    }

    private void radioBtnConfig() {
        rgLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbCashier:
                        level = 2;
                        llTableNo.setVisibility(View.GONE);
                        break;
                    case R.id.rbWaiter:
                        level = 3;
                        llTableNo.setVisibility(View.GONE);
                        break;
                    case R.id.rbOwner:
                        level = 1;
                        llTableNo.setVisibility(View.GONE);
                        break;
                    case R.id.rbCostumer:
                        level = 5;
                        llTableNo.setVisibility(View.VISIBLE);
                        break;
                    default:
                        level = 0;
                        break;
                }
            }
        });
    }

    private void initLayout(View view) {
        btnRegister = view.findViewById(R.id.btnRegister);
        edtUsername = view.findViewById(R.id.edtUsername);
        edtName = view.findViewById(R.id.edtName);
        edtTableNo = view.findViewById(R.id.edtTableNo);
        edtPassword = view.findViewById(R.id.edtPassword);
        rgLevel = view.findViewById(R.id.rgLevel);
        rbOwner = view.findViewById(R.id.rbOwner);
        llTableNo = view.findViewById(R.id.llTableNo);
    }

}
