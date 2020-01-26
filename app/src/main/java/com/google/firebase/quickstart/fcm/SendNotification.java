package com.google.firebase.quickstart.fcm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.quickstart.fcm.java.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class SendNotification extends AppCompatActivity {

    private FirebaseFunctions mFunctions;
    Context context ;
// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFunctions = FirebaseFunctions.getInstance();
        context = this;
        setContentView(R.layout.activity_send_notification);

        Button b = (Button) findViewById(R.id.send_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = findViewById(R.id.editText);
                EditText body = findViewById(R.id.editText2);
                send (title.getText().toString(), body.getText().toString());
            }
        });

    }

    private Task<String> send(String title, String body) {
        // Create the arguments to the callable function.
        Map<String, Object> query = new HashMap<>();
        query.put("title", title);
        query.put("body", body);
        query.put("push", true);
        Log.d("send", title + ' ' + body);

        return mFunctions
                .getHttpsCallable("sendNotificationCallable")
                .call(query)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        String result = (String) task.getResult().getData();
                        Log.d("notification", result);
                        Toast.makeText(context, "Notification sent!", Toast.LENGTH_SHORT).show();

                        return result;
                    }
                });
    }
}
