package com.danielme.dialogfragmentform;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements FormDialogListener{

  private TextView textViewFirstName;
  private TextView textViewLastName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

    textViewFirstName = findViewById(R.id.textViewFirstNameContent);
    textViewLastName = findViewById(R.id.textViewLastNameContent);
  }

  public void edit(View view) {
    FormDialogFragment form = FormDialogFragment.newInstance(textViewFirstName.getText().toString(),
        textViewLastName.getText().toString());
    form.show(getSupportFragmentManager(), FormDialogFragment.TAG);
  }

  @Override
  public void update(String firstName, String lastName) {
    textViewFirstName.setText(firstName);
    textViewLastName.setText(lastName);
  }

}
