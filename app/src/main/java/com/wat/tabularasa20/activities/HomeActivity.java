package com.wat.tabularasa20.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wat.tabularasa20.R;

public class HomeActivity extends AppCompatActivity {

    DynamoDBMapper mapper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView homeTV = findViewById(R.id.homeTextView);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String result = intent.getStringExtra("result");

        // Tak, to nie ma sensu, to tylko test JSON
        JsonObject jsonObject = JsonParser.parseString("{\"result\": " + result + "}").getAsJsonObject();

        homeTV.setText("Witaj " + name + "\nserwer odpowiedział: " +  jsonObject.get("result").getAsString());


        String COGNITO_POOL_ID = "f9d582af-51f9-4db3-8e36-7bdf25f4ee07"; // do zmiany
        String COGNITO_REGION = "us-east-1";
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                this, COGNITO_POOL_ID, Regions.fromName(COGNITO_REGION));

        AmazonDynamoDBClient dbClient = new AmazonDynamoDBClient(credentialsProvider);

        String DYNAMODB_TABLE = "Konta";
        Table dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);
    }

    private class CreateItemAsyncTask extends AsyncTask<Document, Void, Void> {
        @Override
        protected Void doInBackground(Document... documents) {
            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(HomeActivity.this);
            databaseAccess.create(documents[0]);
            return null;
        }
    }
}
