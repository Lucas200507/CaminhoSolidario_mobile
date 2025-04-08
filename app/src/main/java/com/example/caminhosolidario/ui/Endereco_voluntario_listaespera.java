package com.example.caminhosolidario.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.caminhosolidario.R;

public class Endereco_voluntario_listaespera extends AppCompatActivity {

    EditText etCidade, etBairro, etEndereco;
    String cidade, bairro, endereco;
    Button btCadastrar;
    ImageView seta_voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_endereco_voluntario_listaespera);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCidade = findViewById(R.id.etCidade);
        etBairro = findViewById(R.id.etBairro);
        etEndereco = findViewById(R.id.etEndereco);
        btCadastrar = findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etEndereco.getText().equals(null) || etBairro.getText().equals(null) || etCidade.getText().equals(null)){
                    Toast.makeText(getApplicationContext(), "Todos os campos devem estar preenchidos", Toast.LENGTH_SHORT).show();
                } else {

                    cidade = etCidade.getText().toString();
                    bairro = etBairro.getText().toString();
                    endereco = etEndereco.getText().toString();

                    // Transferindo os dados para a Lista de espera
                    Intent i = new Intent(Endereco_voluntario_listaespera.this, ListaEspera_voluntario.class);
                    i.putExtra("cidade", cidade);
                    startActivity(i);




                    Toast.makeText(getApplicationContext(), "Salvando dados de endereço", Toast.LENGTH_SHORT).show();
                }
            }
        });

        seta_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cidade.isEmpty() || bairro.isEmpty() || endereco.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Você deve cadastrar seu endereço primeiro", Toast.LENGTH_SHORT).show();
                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent tela = new Intent(Endereco_voluntario_listaespera.this, ListaEspera_voluntario.class);
                            startActivity(tela);
                            finish();
                        }
                    }, 100);
                }
            }
        });







    }
}