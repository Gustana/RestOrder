package co.ambystic.restorder.view.menu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.ambystic.restorder.R;
import co.ambystic.restorder.model.serverResponse.register.RegisterReponse;
import co.ambystic.restorder.service.DataService;
import co.ambystic.restorder.service.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMenuActivity extends AppCompatActivity {

    private final String TAG = AddMenuActivity.class.getSimpleName();

    private EditText edtMenuName, edtMenuPrice, edtMenuDesc, edtMenuStock, edtTableNo;
    private Button btnAddMenu, btnAddTable;

    private String menuKind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        initLayout();
        buttonClicked();

        menuKind = getIntent().getStringExtra("menuKind");

    }

    private void buttonClicked() {
        btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String menuDesc = edtMenuDesc.getText().toString();
                String menuPrice = edtMenuPrice.getText().toString();
                String menuName = edtMenuName.getText().toString();
                int menuStock = Integer.parseInt(edtMenuStock.getText().toString());

                DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                Call<RegisterReponse> insertMenuCall = dataService.insertMenu(
                        menuName,
                        menuPrice,
                        menuKind,
                        menuDesc,
                        menuStock,
                        "1");
                insertMenuCall.enqueue(new Callback<RegisterReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<RegisterReponse> call, @NonNull Response<RegisterReponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getServerResponse().isError()) {
                                Toast.makeText(AddMenuActivity.this, "Gagal Menambah Menu", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddMenuActivity.this, "Berhasil Menambah Menu", Toast.LENGTH_SHORT).show();
                            }
                        }

                        Log.i(TAG, "onResponse: " + response.body().getServerResponse());
                    }

                    @Override
                    public void onFailure(@NonNull Call<RegisterReponse> call, @NonNull Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                        Log.e(TAG, "onFailure: ", t.getCause());
                    }
                });
            }
        });

        btnAddTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataService dataService = RetrofitClient.getInstance().create(DataService.class);
                Call<RegisterReponse> insertTableCall = dataService.insertTable(Integer.parseInt(edtTableNo.getText().toString()));
                insertTableCall.enqueue(new Callback<RegisterReponse>() {
                    @Override
                    public void onResponse(@NonNull Call<RegisterReponse> call, @NonNull Response<RegisterReponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getServerResponse().isError()) {
                                Toast.makeText(AddMenuActivity.this, "No meja sudah ada", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddMenuActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<RegisterReponse> call, @NonNull Throwable t) {
                        Log.e(TAG, "onFailure: ", t.getCause());
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }

    private void initLayout() {
        edtMenuDesc = findViewById(R.id.edtMenuDesc);
        edtMenuPrice = findViewById(R.id.edtMenuPrice);
        edtMenuName = findViewById(R.id.edtMenuName);
        edtMenuStock = findViewById(R.id.edtMenuStock);
        edtTableNo = findViewById(R.id.edtTableNo);
        btnAddMenu = findViewById(R.id.btnAddMenu);
        btnAddTable = findViewById(R.id.btnAddTable);
    }
}
