package curso.instagram.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseSession;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.Parse;

import curso.instagram.R;

public class Register extends AppCompatActivity implements View.OnClickListener {


    EditText user, pass, pass2;
    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = ((EditText) findViewById(R.id.new_user));
        pass = ((EditText) findViewById(R.id.new_pass));
        pass2 = (EditText) findViewById(R.id.new_pass_conf);
        register_btn = ((Button) findViewById(R.id.register_btn));

        register_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                register();
        }
    }

    public void register() {
        boolean cancel = false;
        ParseUser parseUser = new ParseUser();

        parseUser.setUsername(user.getText().toString());

        if (user.getText().toString().isEmpty()) {
            user.setError(getString(R.string.error_field_required));
            cancel = true;
        } else {
            if (!user.getText().toString().contains("@")) {
                user.setError(getString(R.string.error_invalid_email));
                cancel = true;
            }
        }

        if (!cancel) {
            if (pass.getText().toString().equals(pass2.getText().toString())) {
                parseUser.setPassword(pass.getText().toString());

                Log.i("LOGIN", String.format("register: %s", parseUser.getSessionToken()));

                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            // TODO: 03/03/2018 colocar usuário no arquivo de configurações
                            Intent mainStart = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(mainStart);
                        } else {
                            Toast.makeText(Register.this, String.format("Erro ao cadastrar usuário %s", e.getMessage()), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "A confirmação da senha difere da senha.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
