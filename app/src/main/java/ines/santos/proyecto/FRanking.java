package ines.santos.proyecto;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class FRanking extends ListFragment {
    RankingHelper rankinghelper;
    ArrayList<String> score=new ArrayList<String>();
    String name;
    private OnFragmentInteractionListener mListener;

    public FRanking() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = mListener.getName();
        rankinghelper = new RankingHelper(getContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,
                score);
        setListAdapter(adapter);
        Cursor c = rankinghelper.getScoreByName(name);
        if (c.moveToFirst()) {
            do {
                adapter.add(c.getString(c.getColumnIndex("score")));
            } while (c.moveToNext());
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
}
