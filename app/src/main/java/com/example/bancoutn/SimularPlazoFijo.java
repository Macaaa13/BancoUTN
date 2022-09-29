package com.example.bancoutn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bancoutn.databinding.FragmentConstruirPlazoFijoBinding;
import com.example.bancoutn.databinding.FragmentSimularPlazoFijoBinding;

public class SimularPlazoFijo extends Fragment {

    private FragmentSimularPlazoFijoBinding binding;
    private EditText capitalInvertirInput;
    private TextView tipoMoneda;
    private SeekBar seekBar;
    private String nombre;
    private String apellido;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentSimularPlazoFijoBinding.inflate(inflater);

        getParentFragmentManager().setFragmentResultListener("bundle", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                tipoMoneda = binding.tipoMoneda;
                String resultadoString = result.getString("opcionMoneda");
                nombre = result.getString("nombre");
                apellido = result.getString("apellido");
                binding.persona.setText("Hola "+nombre+ " " + apellido+"!. Complete los siguientes campos para simular.");
                tipoMoneda.setText(tipoMoneda.getText().toString()+resultadoString);
            }
        });

        capitalInvertirInput = binding.capitalInvertirInput;
        capitalInvertirInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(Integer.parseInt(editable.toString()) < 0){
                    binding.capital.setText("Capital: $"+editable);
                }
            }
        });

        seekBar = binding.seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.meses.setText("Meses: "+i+" meses");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //inputs no vacios
        TextWatcher textWatcher = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //todo
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String tasaEfectiva = binding.tasaEfectivaInput.getText().toString();
                String capitalInvertir = binding.capitalInvertirInput.getText().toString();
                String tasaNominal = binding.tasaNominalInput.getText().toString();
                if(!tasaEfectiva.trim().isEmpty() && !capitalInvertir.trim().isEmpty() && !tasaNominal.trim().isEmpty() && !tasaEfectiva.trim().isEmpty()
                        && Double.parseDouble(tasaEfectiva) > 0 && Double.parseDouble(capitalInvertir) > 0 && Double.parseDouble(tasaNominal) > 0 && Double.parseDouble(tasaEfectiva) > 0
                ){
                    calcular(capitalInvertir,tasaNominal,tasaEfectiva);
                    binding.confirmarBoton.setEnabled(true);
                }
            }
        };
        capitalInvertirInput.addTextChangedListener(textWatcher);
        binding.tasaEfectivaInput.addTextChangedListener(textWatcher);
        binding.tasaNominalInput.addTextChangedListener(textWatcher);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.confirmarBoton.setOnClickListener(view1 -> {
            Bundle result = new Bundle();
            result.putBoolean("simulado",true);
            result.putString("capital",binding.capital.getText().toString());
            result.putString("meses",binding.meses.getText().toString());
            result.putBoolean("simulada",true);
            getParentFragmentManager().setFragmentResult("bundle",result);
            this.showDialog();
            NavHostFragment.findNavController(SimularPlazoFijo.this).navigate(R.id.action_simularPlazoFijo_to_construirPlazoFijo);
        });
    }

    public void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Felicidades "+this.nombre+" "+this.apellido);
        dialog.setMessage("Tu plazo fijo de "+binding.capitalInvertirInput.getText().toString()+" "+this.tipoMoneda.getText().toString()+"por 30 días ha sido constituido");
        dialog.setPositiveButton("Joya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.create().show();
    }
    public void calcular(String capital, String tasaNominal, String tasaEfectiva){
        Double porcNominal = Double.parseDouble(tasaNominal);
        Double porcEfectiva = Double.parseDouble(tasaEfectiva);
        Double cap = Double.parseDouble(capital);
        Double intereses = (cap + cap*porcEfectiva/100)/12;
        Double montoTotal= cap + cap*porcNominal/100;
        Double tasaefectiva = cap + intereses;
        binding.capital.setText("Capital: $ "+cap);
        binding.intereses.setText("Intereses ganados: $ "+intereses);
        binding.montoTotal.setText("Monto total: $ "+tasaefectiva);
        binding.montoAnual.setText("Monto total anual: $ "+montoTotal);
    }
}
