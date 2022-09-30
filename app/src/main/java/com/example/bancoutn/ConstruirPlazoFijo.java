package com.example.bancoutn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bancoutn.databinding.FragmentConstruirPlazoFijoBinding;

public class ConstruirPlazoFijo extends Fragment{

    private FragmentConstruirPlazoFijoBinding binding;
    private Spinner opcionesMoneda;
    private EditText nombre;
    private EditText apellido;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentConstruirPlazoFijoBinding.inflate(inflater);
        nombre = binding.nombreInput;
        apellido = binding.apellidoInput;

        TextWatcher textWatcher = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //todo
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.simularBoton.setEnabled(!apellido.getText().toString().trim().isEmpty() && !nombre.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //todo
            }
        };
        apellido.addTextChangedListener(textWatcher);
        nombre.addTextChangedListener(textWatcher);
        opcionesMoneda = binding.spinner;
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
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //todo
            }
        });
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("result", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String vuelta = result.getString("vuelta");
                if(vuelta == "true"){
                    binding.constituirBoton.setEnabled(true);
                }
            }
        });

        binding.simularBoton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Bundle result = new Bundle();
                String opcionMoneda = binding.spinner.getSelectedItem().toString();
                result.putString("opcionMoneda",opcionMoneda);
                result.putString("nombre",nombre.getText().toString());
                result.putString("apellido",apellido.getText().toString());
                getParentFragmentManager().setFragmentResult("bundle",result);
                NavHostFragment.findNavController(ConstruirPlazoFijo.this).navigate(R.id.action_construirPlazoFijo_to_simularPlazoFijo);
            }
        });

    }
}
