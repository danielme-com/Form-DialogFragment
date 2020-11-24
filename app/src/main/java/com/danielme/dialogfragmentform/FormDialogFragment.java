package com.danielme.dialogfragmentform;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;


public class FormDialogFragment extends AppCompatDialogFragment {

  public static final String TAG = FormDialogFragment.class.getSimpleName();

  private static final String ARG_FIRSTNAME = "ARG_FIRSTNAME";
  private static final String ARG_LASTNAME = "ARG_LASTNAME";

  private TextInputLayout textInputLayoutFirstName;
  private EditText textInputFirstName;
  private EditText textInputLastName;
  private FormDialogListener listener;

  public static FormDialogFragment newInstance(String firstName, String lastName) {
    Bundle args = new Bundle();
    args.putString(ARG_FIRSTNAME, firstName);
    args.putString(ARG_LASTNAME, lastName);

    FormDialogFragment frag = new FormDialogFragment();
    frag.setArguments(args);

    return frag;
  }

  @Override
  public void onAttach(@NonNull Context context) {
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

    AlertDialog alertDialog = new MaterialAlertDialogBuilder(getContext())
            .setView(content)
            .setCancelable(true)
            .setNegativeButton(getString(R.string.cancel), null)
            .setTitle(R.string.edit)
            .setPositiveButton(getString(R.string.save), null)
            .create();
    //.setPositiveButton(getString(R.string.save), (dialogInterface, i) -> returnValues());

    //asegura que se muestre el teclado con el diálogo completo
    alertDialog.getWindow().setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
                    | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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

    textInputFirstName.addTextChangedListener(new TextWatcher() {
      @Override
      public void afterTextChanged(Editable s) {
        if (textInputFirstName.getVisibility() == View.VISIBLE) {
          textInputLayoutFirstName.setError(null);
        }
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //nothing here
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        //nothing here
      }
    });
  }

}
