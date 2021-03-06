package com.wehealth.pdqbook.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wehealth.pdqbook.activity.PDQActivity;
import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.adapter.SearchResultAdapter;
import com.wehealth.pdqbook.adapter.SearchResultPopWindowListAdapter;
import com.wehealth.pdqbook.getway.HttpConfigure;
import com.wehealth.pdqbook.getway.error.PDQException;
import com.wehealth.pdqbook.getway.model.SearchResult;
import com.wehealth.pdqbook.getway.model.SearchResultInfoEntry;
import com.wehealth.pdqbook.getway.model.SearchResultListEntry;
import com.wehealth.pdqbook.listener.OnItemClickListenerWithViewCallBack;
import com.wehealth.pdqbook.tool.Strings;
import com.wehealth.pdqbook.view.CircleProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "title";
    private static final String SEARCH_PARAM = "param";

    // TODO: Rename and change types of parameters
    private String mTitle;
    private String mParam;
    private ArrayList<SearchResultListEntry> mList;
    private SearchResultAdapter mAdapter;

    private CircleProgressBar _circleProgressBar;

    private OnFragmentInteractionListener mListener;

    private Call mCall;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param searchContent Parameter 2.
     * @return A new instance of fragment SearchResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultFragment newInstance(String title, String searchContent) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(SEARCH_PARAM, searchContent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
            mParam = getArguments().getString(SEARCH_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        initTitle(view);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        SearchResult.load(mCall, HttpConfigure.getSearchUrl(mParam));
    }

    private void initTitle(View view) {
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

    private void initView(View view) {
        _circleProgressBar = (CircleProgressBar) view.findViewById(R.id.search_result_progressbar);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.search_result_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        mAdapter = new SearchResultAdapter(mList);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setItemClickListener(new OnItemClickListenerWithViewCallBack() {
            @Override
            public void onClick(int position, View v) {
                SearchResultListEntry entry = mList.get(position);
                if (entry.getCategoryName().equalsIgnoreCase("PDQ")) {
                    String url = HttpConfigure.getUrlArticleDetailWeb(entry.getList().get(0).getSid(),
                            entry.getList().get(0).getDescList().get(0).getParaId()) + "/cn";
                    url = url.replaceAll("#", "!!!");
                    final String uri = Strings.getIntentUri(SearchResultFragment.class.getSimpleName(),
                            Strings.INTNET_CONTENT_URL, url,
                            Strings.INTENT_ACTION_TYPE, Strings.IntentActionUrlType.cancerArticleQuestion.toString(),
                            Strings.INTENT_TITLE, entry.getList().get(0).getTitle());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onButtonPressed(Uri.parse(uri));
                        }
                    });
                } else {
                    showPopUpWindow(entry.getList(), v);
                }
            }
        });
    }

    private void showPopUpWindow(ArrayList<SearchResultInfoEntry> list, View v) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popwidown_search_result, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.popwindow_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SearchResultPopWindowListAdapter adapter = new SearchResultPopWindowListAdapter(list);
        recyclerView.setAdapter(adapter);
        PopupWindow window = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        window.setFocusable(true);
        window.setAnimationStyle(R.style.popwindow_anim);
        window.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SearchResult result) {
        _circleProgressBar.setVisibility(View.GONE);
        if (result == null) {
            return;
        }
        mList = result.getData().getList();
        mAdapter.setList(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onError(PDQException e) {
        Toast.makeText(getContext(), ((Exception) e.getException()).getMessage(), Toast.LENGTH_SHORT).show();
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
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
