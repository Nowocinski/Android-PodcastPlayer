package com.example.podcastplayer.ui.Subscribe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.podcastplayer.databinding.FragmentSubscribeBinding;

public class SubscribeFragment extends Fragment {

    private FragmentSubscribeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SubscribeViewModel subscribeViewModel =
                new ViewModelProvider(this).get(SubscribeViewModel.class);

        binding = FragmentSubscribeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        subscribeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}