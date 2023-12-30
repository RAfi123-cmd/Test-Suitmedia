package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity implements UserListAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private UserListAdapter adapter;
    private List<User> userList;

    private int currentPage = 1;
    private int totalPages = 1;
    private boolean isLoading = false;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        userList = new ArrayList<>();
        adapter = new UserListAdapter(userList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Setup scroll listener for loading more data
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && currentPage < totalPages
                        && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    // Load next page
                    loadNextPage();
                }
            }
        });

        // Fetch initial data
        fetchUserData();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemClick(User user) {
        // Handle item click, update Selected User Name label in Second Screen
        updateSelectedUserName(user.getFullName());
    }

    private void updateSelectedUserName(String userName) {
        // Update the selected user name in SecondActivity
        Intent intent = new Intent();
        intent.putExtra("SELECTED_USER_NAME", userName);
        setResult(RESULT_OK, intent);
        finish(); // Finish ThirdActivity after updating the data
    }

    private void fetchUserData() {
        isLoading = true;
        new FetchUserDataTask().execute(currentPage);
    }

    private void loadNextPage() {
        currentPage++;
        fetchUserData();
    }

    private class FetchUserDataTask extends AsyncTask<Integer, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Integer... params) {
            int page = params[0];
            try {
                URL url = new URL("https://reqres.in/api/users?page=" + page + "&per_page=10");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }

                    bufferedReader.close();

                    return new JSONObject(stringBuilder.toString());
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            isLoading = false;

            if (result != null) {
                try {
                    JSONArray dataArray = result.getJSONArray("data");
                    totalPages = result.getInt("total_pages");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject userData = dataArray.getJSONObject(i);
                        String firstName = userData.getString("first_name");
                        String lastName = userData.getString("last_name");
                        String email = userData.getString("email");
                        String avatarUrl = userData.getString("avatar");

                        User user = new User(firstName, lastName, email, avatarUrl);
                        userList.add(user);
                    }

                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ThirdActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ThirdActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
