package com.example.quanlymonan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlymonan.Model.Foods;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int VIEW_NOTE = 9999;
    private static final int DELETE_NOTE = 9998;
    private static final int UPDATE_NOTE = 9997;
    private static final int CREATE_NOTE = 9996;

    private Button btnAddFoods;
    private ListView lvFoods;
    private int CODE = 11;
    MonAnDataBaseHelper foodsDB;
    ArrayList<Foods> foods;
    ArrayAdapter<Foods> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        foodsDB = new MonAnDataBaseHelper(MainActivity.this);
        foods = foodsDB.getAllNote();

        // create adapter
        adapter = new Myadapter();
        lvFoods.setAdapter(adapter);
        registerForContextMenu(lvFoods);
        btnAddFoods.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(), AddFood.class);
            startActivityForResult(intent, CODE);

        });
    }

    private void updateListFoods(){
        foods = foodsDB.getAllNote();
        adapter = new Myadapter();
        lvFoods.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateListFoods();
    }
    private void deleteNote(Foods note){
        MonAnDataBaseHelper db = new MonAnDataBaseHelper(this);
        db.deleteNote(note);
        this.foods.remove(note);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Chose an action");

        menu.add(0, VIEW_NOTE,0, "View Note");
        menu.add(0, CREATE_NOTE,1, "Create Note");
        menu.add(0, UPDATE_NOTE,2, "Update Note");
        menu.add(0, DELETE_NOTE,3, "Delete Note");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Foods selectNote = (Foods) lvFoods.getItemAtPosition(info.position);
        switch (item.getItemId())
        {
            case (VIEW_NOTE):
                Toast.makeText(getApplicationContext(), selectNote.getNameFood(), Toast.LENGTH_LONG).show();
                break;
            case(CREATE_NOTE) :
                break;
            case (DELETE_NOTE):
                new AlertDialog.Builder(this)
                        .setMessage(selectNote.getNameFood() + "you are sure delete note?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",(dialog, which) -> {
                            deleteNote(selectNote);
                        })
                        .setNegativeButton("No",null)
                        .show();

                break;
            case(UPDATE_NOTE) :
                Intent intent = new Intent(getApplicationContext(), AddFood.class);
                intent.putExtra("note", (Serializable) selectNote);
                this.startActivityForResult(intent, CODE);
                break;
            default: return false;
        }
        return true;
    }

    private void init(){
        btnAddFoods = findViewById(R.id.btnAddFood);
        lvFoods = findViewById(R.id.lvFoods);
    }

    private class Myadapter extends ArrayAdapter<Foods> {

        public Myadapter() {
            super(MainActivity.this, R.layout.view_food,foods);
        }
        @Override
        public int getCount() {
            return super.getCount();
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Foods note = foods.get(position);

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_food, null);
            TextView name = convertView.findViewById(R.id.txt_name);
            TextView price = convertView.findViewById(R.id.txt_price);
            ImageView img = convertView.findViewById(R.id.img_food);
            img.setImageResource(R.drawable.ic_android_black_24dp);
            name.setText(note.getNameFood());
            price.setText(String.valueOf(note.getPriceFood()));
            return convertView;

        }
    }
}