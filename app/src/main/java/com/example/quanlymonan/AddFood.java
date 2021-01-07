package com.example.quanlymonan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.quanlymonan.Model.Foods;

public class AddFood extends AppCompatActivity {

    private EditText edtNameFood;
    private EditText edtPriceFood;
    private RadioButton rbAsia;
    private RadioButton rbEur;
    private Button btnDelete;
    private Button btnSaveFood;
    private RadioGroup rgTypeFood;
    private boolean isUpdate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        init();
        Intent getI = getIntent();
        Foods updateFood = null;
        updateFood = (Foods) getI.getSerializableExtra("food");
        if(updateFood != null){
            edtNameFood.setText(updateFood.getNameFood());
            edtPriceFood.setText(updateFood.getPriceFood());
            isUpdate = true;
        }

        btnDelete.setOnClickListener(v -> {
            edtNameFood.setText("");
            edtPriceFood.setText("");
        });
        btnSaveFood.setOnClickListener((view) ->{
            String nameFood = edtNameFood.getText().toString();
            String priceFood = edtPriceFood.getText().toString();
            RadioButton typeFood ;
            String typeString = "";
            if(rgTypeFood.getCheckedRadioButtonId() == -1){

            }else {
                int selectedId = rgTypeFood.getCheckedRadioButtonId();
                typeFood = (RadioButton)findViewById(selectedId);
                typeString = typeFood.getText().toString();
            }
            MonAnDataBaseHelper db = new MonAnDataBaseHelper(AddFood.this);
            Foods note = new Foods(nameFood, Integer.parseInt(priceFood), typeString );
            if(isUpdate){
                update(note, db);
                Intent returnIntent = new Intent();
                setResult(Activity.DEFAULT_KEYS_SEARCH_GLOBAL,returnIntent);
            }
            else {
                save(note,db);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
            }
            finish();
        });
    }

    private boolean save(Foods foods, MonAnDataBaseHelper db ){
        db.addFood(foods);
        db.close();
        return false;
    }
    private boolean update(Foods foods, MonAnDataBaseHelper db){
        int row = db.updateNote(foods);
        Toast.makeText(getBaseContext(), " "  + row, Toast.LENGTH_LONG).show();
        db.close();
        return false;
    }
    private void init() {
        edtNameFood = findViewById(R.id.edtNameFood);
        edtPriceFood = findViewById(R.id.edtPriceFood);
        rbAsia = findViewById(R.id.rb_asia);
        rbEur = findViewById(R.id.rb_europe);
        btnDelete = findViewById(R.id.btn_delete);
        btnSaveFood = findViewById(R.id.btn_save);
        rgTypeFood = findViewById(R.id.type_food);
    }
}