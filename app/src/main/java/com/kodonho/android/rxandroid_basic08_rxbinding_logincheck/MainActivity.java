package com.kodonho.android.rxandroid_basic08_rxbinding_logincheck;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import rx.Observable;

import static com.jakewharton.rxbinding.widget.RxTextView.textChangeEvents;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSign = (Button) findViewById(R.id.btnSign);
        btnSign.setEnabled(false);

        Observable<TextViewTextChangeEvent> idObs = textChangeEvents((EditText)findViewById(R.id.etID));
        Observable<TextViewTextChangeEvent> pwObs = RxTextView.textChangeEvents((EditText)findViewById(R.id.etPW));

        Observable.combineLatest(idObs,pwObs,
            (idChanges,pwChanges) -> {
                boolean idCheck = idChanges.text().length() >= 5;
                boolean pwCheck = pwChanges.text().length() >= 8;
                return idCheck && pwCheck;
            })
            .subscribe(
                    checkFlag -> btnSign.setEnabled(checkFlag)
            );

    }
}
