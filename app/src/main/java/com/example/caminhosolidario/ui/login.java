package com.example.caminhosolidario.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.caminhosolidario.R;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends AppCompatActivity {

    EditText etCpf, etSenha;
    TextView tvCrie, tvEsqueceu;
    RadioButton rbVoluntario, rbBeneficiario;
    Button btEntrar;
    CheckBox cbLembrar;

    String situacao;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCpf = findViewById(R.id.etCpf);
        etSenha = findViewById(R.id.etSenha);
        tvCrie = findViewById(R.id.tvCrie);
        tvEsqueceu = findViewById(R.id.tvEsqueceu);
        rbVoluntario = findViewById(R.id.rbVoluntario);
        rbBeneficiario = findViewById(R.id.rbBeneficiario);
        btEntrar = findViewById(R.id.btEntrar);
        cbLembrar = findViewById(R.id.cbLembrar);

        rbVoluntario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                situacao = "V";
            }
        });
        rbBeneficiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                situacao = "B";
            }
        });

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cpf = etCpf.getText().toString();
                String senha = etSenha.getText().toString();

                 if (cpf.isEmpty() || senha.isEmpty() || situacao.equals(null)){
                    Toast.makeText(getApplicationContext(), "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
                    return;
                }

                     try {
                         Connection con = Conexao.conectar();
                         String sql = "SELECT * FROM login WHERE cpf = ? AND senha = UPPER(MD5(?)) AND situacao = ?;";
                         PreparedStatement stmt = con.prepareStatement(sql);
                         stmt.setString(1, cpf);
                         stmt.setString(2, senha);
                         stmt.setString(3, situacao);
                         ResultSet rs = stmt.executeQuery();

                         if (rs.next()) {
                             if (situacao.equals("V")) {
                                 // Navegar para a pagina voluntario
                                 Toast.makeText(getApplicationContext(), "Navegando para Voluntario", Toast.LENGTH_SHORT).show();
                             } else {
                                 // Navegar para pagina Beneficiario
                                 Toast.makeText(getApplicationContext(), "Navegando para Beneficiario", Toast.LENGTH_SHORT).show();
                             }
                         } else {
                             Toast.makeText(getApplicationContext(), "Senha e(ou) Cpf inválidos", Toast.LENGTH_SHORT).show();
                         }

                         rs.close();
                         stmt.close();
                         con.close();


                     } catch (SQLException e) {
                         throw new RuntimeException(e);
                     }
            }
        });

        tvCrie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent telaLogin = new Intent(login.this, CriarConta.class);
                        startActivity(telaLogin);
                        finish();
                    }
                }, 500);
            }
        });



    }

    public void apagarCampos(){
        etCpf.setText(null);
        etSenha.setText(null);
        situacao = null;
        rbBeneficiario.setChecked(false);
        rbVoluntario.setChecked(false);
        cbLembrar.setChecked(false);

    }
}

