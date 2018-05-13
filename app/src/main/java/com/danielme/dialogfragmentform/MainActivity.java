package com.danielme.dialogfragmentform;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
    FormFragment form = FormFragment.newInstance(textViewFirstName.getText().toString(),
        textViewLastName.getText().toString());
    form.show(getSupportFragmentManager(), FormFragment.TAG);
  }

  @Override
  public void update(String firstname, String lastname) {
    textViewFirstName.setText(firstname);
    textViewLastName.setText(lastname);
  }
}
