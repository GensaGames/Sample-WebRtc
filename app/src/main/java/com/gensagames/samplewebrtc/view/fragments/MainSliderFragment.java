package com.gensagames.samplewebrtc.view.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gensagames.samplewebrtc.R;
import com.gensagames.samplewebrtc.engine.VoIPEngineService;
import com.gensagames.samplewebrtc.model.CallSessionItem;
import com.gensagames.samplewebrtc.view.helper.CollapseAppBarLayoutBehavior;
import com.gensagames.samplewebrtc.view.helper.FragmentHeaderTransaction;
import com.gensagames.samplewebrtc.view.helper.OnSliderPageSelected;

public class MainSliderFragment extends Fragment implements FragmentHeaderTransaction,
        ViewPager.OnPageChangeListener {

    private View mBtnAnswerView;
    private View mBtnHangupView;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingLayout;
    private LocalFragmentAdapter mLocalFragmentAdapter;
    private IncomingCallReceiver mIncomingCallReceiver;

    @Nullable
    @Override
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_slider, null);
        mAppBarLayout = (AppBarLayout) getActivity().
                findViewById(R.id.headerAppBarLayout);
        mCollapsingLayout = (CollapsingToolbarLayout) getActivity().
                findViewById(R.id.headerCollapsingToolbar);
        mTabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        mViewPager = (ViewPager) x.findViewById(R.id.viewpager);
        mBtnAnswerView = x.findViewById(R.id.fragmentBtnAnswer);
        mBtnHangupView = x.findViewById(R.id.fragmentBtnHangup);

        setupViewPager();
        registerVoIPReceiver();
        return x;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(mIncomingCallReceiver);
    }

    private void handleCallDisconnected (CallSessionItem item) {
        enableCollapseToolbar(false);
        mCollapsingLayout.setTitle(getString(R.string.app_name));
        mBtnAnswerView.setVisibility(View.GONE);
        mBtnHangupView.setVisibility(View.GONE);
    }

    private void handleOutgoingCall (final CallSessionItem item) {
        enableCollapseToolbar(true);
        mCollapsingLayout.setTitle(getString(R.string.state_outgoing_call,
                item.getRemoteName()));
        mBtnHangupView.setVisibility(View.VISIBLE);
        mBtnAnswerView.setVisibility(View.GONE);
        mBtnHangupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                Intent intent = new Intent(VoIPEngineService.ACTION_HANGUP_CALL, Uri.EMPTY,
                        activity, VoIPEngineService.class);
                intent.putExtra(VoIPEngineService.EXTRA_CALL_SESSION, item);
                activity.startService(intent);
            }
        });
    }

    private void handleCallConnected (final CallSessionItem item) {
        enableCollapseToolbar(true);
        mBtnHangupView.setVisibility(View.VISIBLE);
        mBtnAnswerView.setVisibility(View.GONE);
        mCollapsingLayout.setTitle(getString(R.string.state_connected_call,
                item.getRemoteName()));
        mBtnHangupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                Intent intent = new Intent(VoIPEngineService.ACTION_HANGUP_CALL, Uri.EMPTY,
                        activity, VoIPEngineService.class);
                intent.putExtra(VoIPEngineService.EXTRA_CALL_SESSION, item);
                activity.startService(intent);
            }
        });
    }

    private void handleIncomingCall(final CallSessionItem item) {
        enableCollapseToolbar(true);
        mCollapsingLayout.setTitle(getString(R.string.state_incoming_call,
                item.getRemoteName()));
        mBtnAnswerView.setVisibility(View.VISIBLE);
        mBtnAnswerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                Intent intent = new Intent(VoIPEngineService.ACTION_ANSWER_CALL, Uri.EMPTY,
                        activity, VoIPEngineService.class);
                intent.putExtra(VoIPEngineService.EXTRA_CALL_SESSION, item);
                activity.startService(intent);
            }
        });
    }

    private void registerVoIPReceiver () {
        mIncomingCallReceiver = new IncomingCallReceiver();
        IntentFilter intentFilter = new IntentFilter(VoIPEngineService.NOTIFY_INCOMING_CALL);
        intentFilter.addAction(VoIPEngineService.NOTIFY_CALL_CONNECTED);
        intentFilter.addAction(VoIPEngineService.NOTIFY_CALL_DISCONNECTED);
        intentFilter.addAction(VoIPEngineService.NOTIFY_OUTGOING_CALL);
        getActivity().registerReceiver(mIncomingCallReceiver, intentFilter);
    }


    private void enableCollapseToolbar (boolean enabled) {
        mAppBarLayout.setExpanded(enabled, true);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
                mAppBarLayout.getLayoutParams();

        CollapseAppBarLayoutBehavior behavior = (CollapseAppBarLayoutBehavior)
                layoutParams.getBehavior();
        if (behavior != null) {
            behavior.setEnabled(enabled);
        }
    }


    private void setupViewPager () {
        enableCollapseToolbar(false);
        mLocalFragmentAdapter = new LocalFragmentAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mLocalFragmentAdapter);

        mViewPager.addOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public int getHeaderLayouts() {
        return R.layout.header_slider_fragment;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 0) {
            Fragment fragment = mLocalFragmentAdapter.getItem(mViewPager.getCurrentItem());
            if (fragment instanceof OnSliderPageSelected) {
                ((OnSliderPageSelected) fragment).onThisPageSelected();
            }
        }
    }

    /**
     * ****************************************************
     * Defined receiver for receiving call actions.
     */
    private class IncomingCallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            CallSessionItem session = (CallSessionItem) intent
                    .getSerializableExtra(VoIPEngineService.EXTRA_CALL_SESSION);

            switch (action) {
                case VoIPEngineService.NOTIFY_INCOMING_CALL:
                    handleIncomingCall(session);
                    break;
                case VoIPEngineService.NOTIFY_CALL_CONNECTED:
                    handleCallConnected(session);
                    break;
                case VoIPEngineService.NOTIFY_CALL_DISCONNECTED:
                    handleCallDisconnected(session);
                    break;
                case VoIPEngineService.NOTIFY_OUTGOING_CALL:
                    handleOutgoingCall(session);
                    break;
            }
        }

    }

    /**
     * ****************************************************
     * Main Adapter for Slider Fragment in this scope.
     */

    private class LocalFragmentAdapter extends FragmentPagerAdapter {
        final String[] SLIDER_ITEM_HEADERS = { MainSliderFragment.this
                .getString(R.string.slider_call_header), MainSliderFragment.this
                .getString(R.string.slider_device_header)};

        private Fragment callSessionFragment = new CallSessionFragment();
        private Fragment callListFragment = new CallListFragment();
        private LocalFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return callSessionFragment;
                case 1: return callListFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return SLIDER_ITEM_HEADERS.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return SLIDER_ITEM_HEADERS[position];
        }
    }

}
