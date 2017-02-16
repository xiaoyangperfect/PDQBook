package com.wehealth.pdqbook.fragment;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.getway.repertory.SharedPrefrence;
import com.wehealth.pdqbook.getway.repertory.db.PDQDB;
import com.wehealth.pdqbook.getway.repertory.db.table.SearchRecordTable;

import static android.content.Context.SEARCH_SERVICE;

/**
 *
 */
public class SearchFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        SearchView searchView = (SearchView) view.findViewById(R.id.search_input);
        setupSearchView(searchView);
    }

    private void setupSearchView(final SearchView mSearchView) {
        SearchManager searchManager = (SearchManager) getContext().getSystemService(SEARCH_SERVICE);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setIconified(false);
        // Set the query hint.
        mSearchView.setQueryHint(getString(R.string.search_hint));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() < 1)
                    return true;
                mSearchView.clearFocus();
                insertSearchTextToDB(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                searchFor(s);
                return true;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
//                dismiss(null);
                return false;
            }
        });
//        if (!TextUtils.isEmpty(mQuery)) {
//            mSearchView.setQuery(mQuery, false);
//        }
    }

    private void insertSearchTextToDB(String search) {
        SearchRecordTable table = new SearchRecordTable(PDQDB.instance().getWritableDatabase());
        ContentValues values = new ContentValues();
        values.put(SearchRecordTable.SEARCH_RECORD_CONTENT, search);
        values.put(SearchRecordTable.SEARCH_RECORD_USER, SharedPrefrence.USER);
        values.put(SearchRecordTable.SEARCH_RECORD_DATE, System.currentTimeMillis());
        values.put(SearchRecordTable.SEARCH_RECORD_NUMBER, 1);
        values.put(SearchRecordTable.SEARCH_RECORD_STATUS, SearchRecordTable.SearchStatus.success.toString());
        table.setSearchRecord(values);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

}
