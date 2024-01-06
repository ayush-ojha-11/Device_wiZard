package com.as.devicewizard;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ChatScreenActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;

    MessageAdapter messageAdapter;

    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        recyclerView = findViewById(R.id.recycler_view);
        welcomeTextView = findViewById(R.id.welcome_text_view);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_button);
        messageList = new ArrayList<>();

        // Setting Up the RecyclerView
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = messageEditText.getText().toString().trim();
                addToChat(query, Message.SENT_BY_ME);
                callAPI(query);
                messageEditText.setText("");
                welcomeTextView.setVisibility(View.GONE);
            }
        });
    }

    void addToChat(String message, String sentBy) {
        runOnUiThread(() -> {
            messageList.add(new Message(message, sentBy));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }

    void addResponse(String response) {
        messageList.remove(messageList.size()-1);
        addToChat(response, Message.SENT_BY_BOT);
    }

    void callAPI(String question) {

        messageList.add(new Message("Typing...",Message.SENT_BY_BOT));
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "text-davinci-003");
            jsonBody.put("prompt", question);
            jsonBody.put("max_tokens", 4000);
            jsonBody.put("temperature", 0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .addHeader("Authorization", "Bearer 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")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> addResponse("Failed to get response due to " + e.getMessage()));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try (ResponseBody responseBody = response.body()) {
                    if (response.isSuccessful()) {
                        try {
                            String responseBodyString = Objects.requireNonNull(responseBody).string();
                            JSONObject jsonObject = new JSONObject(responseBodyString);
                            JSONArray jsonArray = jsonObject.getJSONArray("choices");
                            String result = jsonArray.getJSONObject(0).getString("text");
                            runOnUiThread(() -> addResponse(result.trim()));
                        } catch (JSONException | IOException e) {
                            runOnUiThread(() -> addResponse("Failed to parse JSON response"));
                        }
                    } else {
                        runOnUiThread(() -> addResponse("Failed to get response. HTTP status: " + response.code()));
                    }
                }
            }
        });
    }
}
