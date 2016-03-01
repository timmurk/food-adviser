package foodadviser.com.foodadviser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;

public class AddIngridientDialogFragment extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * The method passes the input provided by the user in the EditText widget. */
    public interface AddIngridientDialogListener {
        void onDialogPositiveClick(String userInput);
    }

    public AddIngridientDialogListener mListener = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        builder.setView(layoutInflater.inflate(R.layout.dialog_add_ingridient, null))
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //add ingridient
                        if (mListener != null) {
                            Dialog f = (Dialog) dialog;
                            //View v = layoutInflater.inflate(R.layout.dialog_add_ingridient, null);
                            EditText editText = (EditText) f.findViewById(R.id.add_ingridient_dialog_field);
                            mListener.onDialogPositiveClick(editText.getText().toString());
                            Log.d(TAG, "User input: " + editText.getText().toString());
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //action canceled
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (AddIngridientDialogListener) activity ;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + "must implement AddIngridientDialogListener");
        }
    }

    private static final String TAG = AddIngridientDialogFragment.class.getSimpleName();
}
