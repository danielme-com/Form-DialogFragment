package com.danielme.dialogfragmentform;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class FormFragment extends AppCompatDialogFragment {

  public static final String TAG = FormFragment.class.getSimpleName();

  private static final String ARG_FIRSTNAME = "ARG_FIRSTNAME";
  private static final String ARG_LASTNAME = "ARG_LASTNAME";

  private TextInputLayout textInputLayoutFirstName;
  private EditText textInputFirstName;
  private EditText textInputLastName;
  private FormDialogListener listener;

  public static FormFragment newInstance(String firstName, String lastName) {
    Bundle args = new Bundle();
    args.putString(ARG_FIRSTNAME, firstName);
    args.putString(ARG_LASTNAME, lastName);

    FormFragment frag = new FormFragment();
    frag.setArguments(args);

    return frag;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof FormDialogListener) {
      listener = (FormDialogListener) context;
    } else {
      throw new IllegalArgumentException("context is not FormDialogListener");
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    View content = LayoutInflater.from(getContext()).inflate(R.layout.fragment_form, null);

    setupContent(content);

    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
    builder.setView(content)
            .setNegativeButton(getString(R.string.cancel), null)
            .setCancelable(true)
            .setTitle(R.string.edit)
            .setPositiveButton(getString(R.string.save), null);
    //.setPositiveButton(getString(R.string.save), (dialogInterface, i) -> returnValues());

    AlertDialog alertDialog = builder.create();
    //asegura que se muestre el teclado
    alertDialog.getWindow().setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    return alertDialog;
  }

  @Override
  public void onStart() {
    super.onStart();
    Button positiveButton = ((AlertDialog) getDialog()).getButton(Dialog.BUTTON_POSITIVE);
    positiveButton.setOnClickListener(view -> {
      if (validate()) {
        returnValues();
        getDialog().dismiss();
      }
    });
  }

  private boolean validate() {
    if (TextUtils.isEmpty(textInputFirstName.getText())) {
      textInputLayoutFirstName.setError(getString(R.string.mandatory));
      textInputLayoutFirstName.setErrorEnabled(true);
      return false;
    }
    return true;
  }

  private void returnValues() {
    listener.update(textInputFirstName.getText().toString(),
            textInputLastName.getText().toString());
  }

  private void setupContent(View content) {
    textInputLayoutFirstName = content.findViewById(R.id.textInputLayoutFirstName);
    textInputFirstName = content.findViewById(R.id.textInputFirstName);
    textInputLastName = content.findViewById(R.id.textInputLastName);
    textInputFirstName.setText(getArguments().getString(ARG_FIRSTNAME));
    textInputFirstName.setSelection(getArguments().getString(ARG_FIRSTNAME).length());
    textInputLastName.setText(getArguments().getString(ARG_LASTNAME));
    textInputLastName.setSelection(getArguments().getString(ARG_LASTNAME).length());

    textInputLastName.setOnEditorActionListener((textView, actionId, keyEvent) -> {
      if (actionId == EditorInfo.IME_ACTION_DONE) {
        returnValues();
        dismiss();
        return true;
      }
      return false;
    });
  }

}
