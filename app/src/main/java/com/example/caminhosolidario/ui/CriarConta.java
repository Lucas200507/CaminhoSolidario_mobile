package com.example.caminhosolidario.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.transform.Result;

public class CriarConta extends AppCompatActivity {

    EditText etCpf, etSenha, etConfSenha;
    ImageView seta_voltar;
    Button btCadastrar;
    TextView cadastrarVoluntario;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_conta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCpf = findViewById(R.id.etCpf);
        etSenha = findViewById(R.id.etSenha);
        etConfSenha = findViewById(R.id.etConfSenha);
        seta_voltar = findViewById(R.id.seta_voltar);
        btCadastrar = findViewById(R.id.btCadastrar);
        cadastrarVoluntario = findViewById(R.id.cadastrarVoluntario);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senha = etSenha.getText().toString();
                String confSenha = etConfSenha.getText().toString();
                String cpf = etCpf.getText().toString();

                if (senha.isEmpty() ||confSenha.isEmpty() || cpf.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
                } else if (senha.equals(confSenha) && !cpf.isEmpty()) {
                    try {
                        Connection con = Conexao.conectar();
                        String sql = "INSERT INTO login (cpf, senha, situacao) VALUES(?,?,'B');";
                        PreparedStatement stmt = con.prepareStatement(sql);
                        stmt.setString(1, etCpf.getText().toString());
                        stmt.setString(2, etConfSenha.getText().toString());
                        stmt.execute();

                        Toast.makeText(getApplicationContext(), "Sua conta foi cadastrada com sucesso", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent telaLogin = new Intent(CriarConta.this, login.class);
                                startActivity(telaLogin);
                                finish();
                            }
                        }, 500);

                        stmt.close();
                        con.close();

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Os campos: senha e confirmar senha não estão idênticos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cadastrarVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent telaListaEspera = new Intent(CriarConta.this, ListaEspera_voluntario.class);
                        startActivity(telaListaEspera);
                        finish();
                    }
                }, 100);
            }
        });

        seta_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent telaLogin = new Intent(CriarConta.this, login.class);
                        startActivity(telaLogin);
                        finish();
                    }
                }, 100);
            }
        });



    }


}