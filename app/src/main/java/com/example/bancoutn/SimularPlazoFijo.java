package com.example.bancoutn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bancoutn.databinding.FragmentConstruirPlazoFijoBinding;
import com.example.bancoutn.databinding.FragmentSimularPlazoFijoBinding;

public class SimularPlazoFijo extends Fragment {

    private FragmentSimularPlazoFijoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentSimularPlazoFijoBinding.inflate(inflater);
        return binding.getRoot();
    }
}
