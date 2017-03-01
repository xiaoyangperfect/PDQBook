package com.wehealth.pdqbook.fragment;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.wehealth.pdqbook.PDQActivity;
import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.adapter.SearchRecordsAdapter;
import com.wehealth.pdqbook.getway.datamodel.SearchRecord;
import com.wehealth.pdqbook.getway.repertory.SharedPrefrence;
import com.wehealth.pdqbook.getway.repertory.db.PDQDB;
import com.wehealth.pdqbook.getway.repertory.db.table.SearchRecordTable;
import com.wehealth.pdqbook.tool.Strings;
import com.wehealth.pdqbook.view.RecyclerItemClickListener;

import java.util.ArrayList;

import static android.content.Context.SEARCH_SERVICE;

/**
 *
 */
public class SearchFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;
    private static final String TITLE = "title";
    private String mTitle;

    ArrayList<SearchRecord> mSearchRecords;
    SearchRecordsAdapter mAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance(String title) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mTitle = savedInstanceState.getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("onCreateView", "11");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        initTitle(view, mTitle);
        SearchView searchView = (SearchView) view.findViewById(R.id.search_input);
        setupSearchView(searchView);
        setUpRecordsView(view);
        initRecordView();
    }

    private void initTitle(View view, String mTitle) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((PDQActivity) getActivity()).setSupportActionBar(toolbar);
        ((PDQActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((PDQActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title = (TextView) view.findViewById(R.id.tool_bar_title);
        title.setText(mTitle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PDQActivity) getActivity()).clickBack();
            }
        });
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
                doSearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

    }

    private void doSearch(String content) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        insertSearchTextToDB(content);
        final String uri = Strings.getIntentUri(SearchFragment.class.getSimpleName(),
                Strings.INTENT_CONTENT, content);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onButtonPressed(Uri.parse(uri));
            }
        });
    }

    private void setUpRecordsView(View view) {
        RecyclerView recordsView = (RecyclerView) view.findViewById(R.id.search_record_list);
        mSearchRecords = new ArrayList<>();
        recordsView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SearchRecordsAdapter(mSearchRecords);
        recordsView.setAdapter(mAdapter);
        mAdapter.setClickListener(new RecyclerItemClickListener() {
            @Override
            public void onClick(int position) {
                String content = mSearchRecords.get(position).getContent();
                doSearch(content);
            }
        });
    }

    private void initRecordView() {
        mSearchRecords = ((PDQActivity) getActivity()).getSearchRecord();
        mAdapter.setRecords(mSearchRecords);
        mAdapter.notifyDataSetChanged();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE, mTitle);
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
