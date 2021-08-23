package com.topmas.top;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public  class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener
        {

    private Date fechaseleccionada;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        fechaseleccionada = new Date(year-1900, month, day);

        ((Caducidad)getActivity()).fechacaducidad = fechaseleccionada;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        TextView fechaCaducidad = ((Caducidad)getActivity()).findViewById(R.id.txtFechaCaducidad);
        fechaCaducidad.setText(sdf.format(fechaseleccionada));
    }
}
