package com.example.bancoutn;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bancoutn.databinding.FragmentConstruirPlazoFijoBinding;

public class ConstruirPlazoFijo extends Fragment{

    private FragmentConstruirPlazoFijoBinding binding;
    private Spinner opcionesMoneda;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentConstruirPlazoFijoBinding.inflate(inflater);

        // ACCIONES CON EL SPINNER
        /*NO ESTOY SEGURO DE SI SE DEBE REALIZAR ASI --> CONSULTAR.*/
        opcionesMoneda = binding.spinner.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.opciones_monedas, android.R.layout.simple_spinner_item);
        opcionesMoneda.setAdapter(adapter);
        opcionesMoneda.setSelection(0);
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(0x00000000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        opcionesMoneda.setOnItemSelectedListener(listener);

        opcionesMoneda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        binding.simularBoton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ConstruirPlazoFijo.this).navigate(R.id.action_construirPlazoFijo_to_simularPlazoFijo);
            }
        });


    }
}
