package com.example.caminhosolidario.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.caminhosolidario.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListaEspera_voluntario extends AppCompatActivity {

    SimpleDateFormat data_formatada = new SimpleDateFormat("yyyy-MM-dd");
    Date data = new Date();
    EditText etNome, etCep, etEmail, etTelefone, etData;
    Button btCadastrar;
    String data_pedido = data_formatada.format(data);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_espera_voluntario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etData = findViewById(R.id.etData);
        etNome = findViewById(R.id.etNome);
        etCep = findViewById(R.id.etCep);
        etEmail = findViewById(R.id.etEmail);
        etTelefone = findViewById(R.id.etTelefone);
        btCadastrar = findViewById(R.id.btCadastrar);

        etData.setText(data_formatada.format(data));

        etCep.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT){
                    return true;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent telaListaEnderecoV = new Intent(ListaEspera_voluntario.this, Endereco_voluntario_listaespera.class);
                        startActivity(telaListaEnderecoV);
                        finish();
                    }
                }, 100);
                return false;
            }
        });

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pegando os dados da Activity Endereço
                Bundle extras = getIntent().getExtras();
                if (extras != null){
                    String cidade = extras.getString("cidade");
                }

                if(etNome.getText().equals(null) || etCep.getText().equals(null) || etEmail.getText().equals(null) || etTelefone.getText().equals(null)){
                    Toast.makeText(getApplicationContext(), "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
                } else {



                    try {
                        Connection con = Conexao.conectar();
                        String sql = "INSERT INTO endereco_voluntariio (cep, cidade, bairro, endereco) VALUES (?,?,?,?);";
                        PreparedStatement stmt = con.prepareStatement(sql);
                        stmt.setString(1, etCep.getText().toString());
                        // stmt.setString(2, cidade);
                        // stmt.setString(3, bairro);
                        // stmt.setString(4, endereco);




                        String sql1 = "INSERT INTO espera_voluntario (cpf,nome_completo,email,telefone,data_pedido, id_enderecoV) VALUES (?,?,?,?,?,?);";
                        PreparedStatement stmt1 = con.prepareStatement(sql1);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });





    }
}