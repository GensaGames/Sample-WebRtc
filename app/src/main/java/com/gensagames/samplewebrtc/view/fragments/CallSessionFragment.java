package com.gensagames.samplewebrtc.view.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gensagames.samplewebrtc.R;
import com.gensagames.samplewebrtc.controller.BTMonitorController;
import com.gensagames.samplewebrtc.controller.BTRecyclerAdapter;
import com.gensagames.samplewebrtc.controller.SignalingMsgAdapter;
import com.gensagames.samplewebrtc.engine.VoIPEngineService;
import com.gensagames.samplewebrtc.model.CallSessionItem;
import com.gensagames.samplewebrtc.model.SignalingMessageItem;

public class CallSessionFragment extends Fragment {


    private View mTextRefresh;
    private RecyclerView mRecyclerView;
    private SignalingMsgAdapter mSignalingMsgAdapter;
    private SignalingMsgReceiver mSignalingMsgReceiver;

    @Nullable
    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_session, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentRecyclerView);
        mTextRefresh = view.findViewById(R.id.fragmentTextRefresh);

        setupAdapter();
        registerReceiver();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(mSignalingMsgReceiver);
    }

    private void registerReceiver () {
        mSignalingMsgReceiver = new CallSessionFragment.SignalingMsgReceiver();
        IntentFilter intentFilter = new IntentFilter(VoIPEngineService.NOTIFY_SIGNAL_MSG);
        getActivity().registerReceiver(mSignalingMsgReceiver, intentFilter);
    }

    private void setupAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSignalingMsgAdapter = new SignalingMsgAdapter();
        mRecyclerView.setAdapter(mSignalingMsgAdapter);
    }

    private void handleSignalingMsg (SignalingMessageItem item) {
        if (mTextRefresh.getVisibility() == View.VISIBLE) {
            mTextRefresh.setVisibility(View.GONE);
        }
        mSignalingMsgAdapter.getWorkingItems().add(item);
        mSignalingMsgAdapter.notifyDataSetChanged();
    }

    private class SignalingMsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(VoIPEngineService.NOTIFY_SIGNAL_MSG)) {
                handleSignalingMsg((SignalingMessageItem) intent
                        .getSerializableExtra(VoIPEngineService.EXTRA_SIGNAL_MSG));
            }
        }
    }
}
