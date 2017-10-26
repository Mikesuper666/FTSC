package onuse.com.br.ftsc.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import onuse.com.br.ftsc.R;

/**
 * Created by maico on 05/10/17.
 */

public class SpinnerCustomizado {
    public void Spinner(final Context context, Spinner spinner){
                /*
        * Obrigatorio para modificação das cores do spinnner
        * */
        spinner.setSelection(0, true);
        final View v = spinner.getSelectedView();
        ((TextView)v).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //Change the selected item's text color
                ((TextView)parent.getChildAt(0)).setTextColor(context.getResources().getColor(R.color.colorBranco));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }
}
