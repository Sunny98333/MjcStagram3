package com.example.mjcstagram.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mjcstagram.R;

public class SearchFragment extends Fragment implements View.OnClickListener {
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.search_fragment, container, false);

        EditText searchText;
        Button cancelBtn;

        searchText = (EditText) fv.findViewById(R.id.searchText);
        searchText.setOnClickListener(this);

        cancelBtn = (Button) fv.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);

        return fv;
    }

    @Override
    public void onClick(View view) {

        Fragment fg;
        switch (view.getId()) {
            case R.id.searchText:
                fg = SearchpagerFragment.newInstance();
                setChildFragment(fg);
                break;
            case R.id.cancelBtn:
                fg = SearchviewFragment.newinstance();
                setChildFragment(fg);
                break;
        }
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.searchContainer, child);
            childFt.addToBackStack(null);
            childFt.commit();

        }
    }
}