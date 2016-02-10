package ines.santos.proyecto;


import android.content.ContentValues;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.material.joanbarroso.flipper.CoolImageFlipper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Random;


public class FMemory extends Fragment implements View.OnClickListener{

    int[] parejas_resueltas;
    int[] array;
    int[] imagenes = {R.drawable.mario, R.drawable.link, R.drawable.kirby, R.drawable.mago, R.drawable.megaman, R.drawable.metroid, R.drawable.pikachu, R.drawable.sonic };
    ImageButton b_1,b_2,b_3, b_4, b_5, b_6, b_7, b_8, b_9, b_10, b_11, b_12, b_13, b_14,b_15, b_16;
    CoolImageFlipper coolImageFlipper;
    private final Random mGenerator = new Random();
    boolean turno; //false=primero,true=segundo
    int posicion_primera, posicion_segunda;
    View primera_carta, segunda_carta;
    TextView puntos;
    Button reset;

    int intentos, parejas;
    String name;
    RankingHelper rankinghelper;
    private OnFragmentInteractionListener mListener;

    public FMemory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coolImageFlipper = new CoolImageFlipper(mListener.getContext());
        turno=false;
        posicion_primera=posicion_segunda=-1;
        intentos=0;
        parejas=0;
        name = mListener.getName();
        rankinghelper = new RankingHelper(getContext());
        parejas_resueltas=new int[16];
        for (int i =0; i<16; ++i) parejas_resueltas[i]=-1;

        array = new int[16];
        for (int i =0; i<16; ++i) array[i]=0;

        barajar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_fmemory, container, false);
        b_1 = (ImageButton) rootView.findViewById(R.id.b_1);
        b_1.setOnClickListener(this);
        b_2 = (ImageButton) rootView.findViewById(R.id.b_2);
        b_2.setOnClickListener(this);
        b_3 = (ImageButton) rootView.findViewById(R.id.b_3);
        b_3.setOnClickListener(this);
        b_4 = (ImageButton) rootView.findViewById(R.id.b_4);
        b_4.setOnClickListener(this);
        b_5 = (ImageButton) rootView.findViewById(R.id.b_5);
        b_5.setOnClickListener(this);
        b_6 = (ImageButton) rootView.findViewById(R.id.b_6);
        b_6.setOnClickListener(this);
        b_7 = (ImageButton) rootView.findViewById(R.id.b_7);
        b_7.setOnClickListener(this);
        b_8 = (ImageButton) rootView.findViewById(R.id.b_8);
        b_8.setOnClickListener(this);
        b_9 = (ImageButton) rootView.findViewById(R.id.b_9);
        b_9.setOnClickListener(this);
        b_10 = (ImageButton) rootView.findViewById(R.id.b_10);
        b_10.setOnClickListener(this);
        b_11 = (ImageButton) rootView.findViewById(R.id.b_11);
        b_11.setOnClickListener(this);
        b_12 = (ImageButton) rootView.findViewById(R.id.b_12);
        b_12.setOnClickListener(this);
        b_13 = (ImageButton) rootView.findViewById(R.id.b_13);
        b_13.setOnClickListener(this);
        b_14 = (ImageButton) rootView.findViewById(R.id.b_14);
        b_14.setOnClickListener(this);
        b_15 = (ImageButton) rootView.findViewById(R.id.b_15);
        b_15.setOnClickListener(this);
        b_16 = (ImageButton) rootView.findViewById(R.id.b_16);
        b_16.setOnClickListener(this);

        if(savedInstanceState!=null){
            array=savedInstanceState.getIntArray("array");
            imagenes = savedInstanceState.getIntArray("imagenes");
            parejas_resueltas=savedInstanceState.getIntArray("estado");
            int i = 0;
            if( parejas_resueltas != null) {
                while (parejas_resueltas[i] != -1 && i<16) {
                    ImageButton v = null;
                    switch (parejas_resueltas[i]) {
                        case 0:
                            v = b_1;
                            break;
                        case 1:
                            v = b_2;
                            break;
                        case 2:
                            v = b_3;
                            break;
                        case 3:
                            v = b_4;
                            break;
                        case 4:
                            v = b_5;
                            break;
                        case 5:
                            v = b_6;
                            break;
                        case 6:
                            v = b_7;
                            break;
                        case 7:
                            v = b_8;
                            break;
                        case 8:
                            v = b_9;
                            break;
                        case 9:
                            v = b_10;
                            break;
                        case 10:
                            v = b_11;
                            break;
                        case 11:
                            v = b_12;
                            break;
                        case 12:
                            v = b_13;
                            break;
                        case 13:
                            v = b_14;
                            break;
                        case 14:
                            v = b_15;
                            break;
                        case 15:
                            v = b_16;
                            break;
                    }
                    if(v != null) v.setVisibility(View.INVISIBLE);
                    ++i;
                }
            }
        }
        reset = (Button) rootView.findViewById(R.id.reset);
        reset.setOnClickListener(this);
        puntos = (TextView) rootView.findViewById(R.id.intentos);
        puntos.setText(String.valueOf("0"));

        return rootView;
    }
    private void barajar() {
        int p, s;
        int i=0;
        while(i<8){
            p=mGenerator.nextInt(16);
            while(array[p]!=0){
                if(p>=15) p=0;
                else ++p;
            }
            array[p]=i;
            s=mGenerator.nextInt(16);
            while(array[s]!=0){
                if(s>=15) s=0;
                else ++s;
            }
            array[s]=i;
            ++i;
        }
    }
    @Override
    public void onClick(View view){
        if(view.getId()==R.id.reset){
            mListener.reset_memory();
        }
        else {
            int n = 0;
            switch (view.getId()) {
                case R.id.b_1:
                    n = 0;
                    break;
                case R.id.b_2:
                    n = 1;
                    break;
                case R.id.b_3:
                    n = 2;
                    break;
                case R.id.b_4:
                    n = 3;
                    break;
                case R.id.b_5:
                    n = 4;
                    break;
                case R.id.b_6:
                    n = 5;
                    break;
                case R.id.b_7:
                    n = 6;
                    break;
                case R.id.b_8:
                    n = 7;
                    break;
                case R.id.b_9:
                    n = 8;
                    break;
                case R.id.b_10:
                    n = 9;
                    break;
                case R.id.b_11:
                    n = 10;
                    break;
                case R.id.b_12:
                    n = 11;
                    break;
                case R.id.b_13:
                    n = 12;
                    break;
                case R.id.b_14:
                    n = 13;
                    break;
                case R.id.b_15:
                    n = 14;
                    break;
                case R.id.b_16:
                    n = 15;
                    break;
            }
            Drawable dr = mListener.getImage(imagenes[array[n]]);
            coolImageFlipper.flipImage(dr, (ImageButton) view);

            if (!turno) {
                posicion_primera = n;
                primera_carta = view;
                turno = true;
            } else {
                if (posicion_primera != n) {
                    posicion_segunda = n;
                    segunda_carta = view;

                    if (array[posicion_segunda] == array[posicion_primera]) {
                        resover_pareja(primera_carta, segunda_carta);
                        int i = 0;
                        while (parejas_resueltas[i] != -1 && i<15) ++i;
                        parejas_resueltas[i]=posicion_primera;
                        parejas_resueltas[i+1]=posicion_segunda;
                        ++parejas;
                    } else girar_pareja(primera_carta, segunda_carta);
                    ++intentos;
                    puntos.setText(String.valueOf(intentos));

                    if (parejas == 8) terminar_partida();
                    turno = false;
                }
            }
        }
    }

    private void terminar_partida() {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("score", intentos);
        rankinghelper.createScore(cv, "Score");
        mListener.openDialog();
    }

    private void girar_pareja(final View primera, final View segunda) {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                Drawable dr = mListener.getImage(R.drawable.reverso);
//                coolImageFlipper.flipImage(dr, (ImageView) segunda);
//                coolImageFlipper.flipImage(dr, (ImageView) primera);
//            }
//        }, 1000);
        mListener.delay(primera, segunda, 1000, true);
//        Drawable dr = mListener.getImage(R.drawable.reverso);
//        coolImageFlipper.flipImage(dr, (ImageView) segunda);
//        coolImageFlipper.flipImage(dr, (ImageView) primera);
    }

    private void resover_pareja(final View primera, final View segunda) {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                primera.setVisibility(View.INVISIBLE);
//                segunda.setVisibility(View.INVISIBLE);
//            }
//        }, 2000);
         mListener.delay(primera, segunda, 2000, false);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("array", array);
        outState.putIntArray("imagenes", imagenes);
        outState.putIntArray("estado", parejas_resueltas);
    }

}
