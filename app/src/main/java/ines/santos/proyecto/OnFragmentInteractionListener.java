package ines.santos.proyecto;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Inés on 07/02/2016.
 */
public interface OnFragmentInteractionListener {
    Drawable getImage(int id);
    String getName();
    Context getContext();
    void reset_memory();
    void openDialog();
    void delay( final View primera, final View segunda, int n, boolean b);
}
