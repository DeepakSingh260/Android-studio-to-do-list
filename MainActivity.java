package com.example.todolist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//import android.os.FileUtils;

//import android.os.FileUtils;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText InputTask;
    private ListView listView;
    private ArrayList<String> TaskList;
    private ArrayAdapter<String> TaskAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Lets link it to the layout
        InputTask = (EditText) findViewById(R.id.editText);
        InputTask.setText("");
        TaskList = new ArrayList<String>();
        listView = (ListView)findViewById(R.id.listView);
        readTask();
        TaskAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,TaskList);
        Button button = (Button) findViewById(R.id.button);
        listView.setAdapter(TaskAdapter);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText  editText = (EditText)findViewById(R.id.editText);
                String tasks = editText.getText().toString();
                TaskAdapter.add(tasks);
                editText.setText("");
                writeTask();
            }
        };
        button.setOnClickListener(listener);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TaskList.remove(position);
                TaskAdapter.notifyDataSetChanged();
                writeTask();
                return true;
            }
        });
        // we need a way to save our list

        Log.d(TAG, "onCreate: out");
    }

    private void readTask(){
        File files= getFilesDir();
        File TodoFile = new File(files,"ToDo.txt");
        try {
            TaskList = new ArrayList<String>(FileUtils.readLines(TodoFile));
        }catch (IOException e){
            TaskList = new ArrayList<String>();
        }
    }

    private void writeTask(){
        File files = getFilesDir();
        File TodoFile = new File(files,"ToDo.txt");
        try {
            FileUtils.writeLines(TodoFile,TaskList);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}


