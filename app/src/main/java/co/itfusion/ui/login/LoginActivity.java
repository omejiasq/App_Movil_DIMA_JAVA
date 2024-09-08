package co.itfusion.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import co.itfusion.dima.R;
import co.itfusion.dima.databinding.ActivityLoginBinding;
import co.itfusion.interfaces.ISetup;
import co.itfusion.utils.Navigation;
import co.itfusion.utils.Utils;

public class LoginActivity extends AppCompatActivity implements ISetup {

    private final Context context = this;
    private final Activity activity = this;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(activity.getLayoutInflater());
        setContentView(binding.getRoot());
        setDependencies();
    }

    @Override
    public void setDependencies() {
        if(getSupportActionBar() != null) getSupportActionBar().hide();
        Utils.UI.setupStatusBar(activity, context);
        setClickEvents();
    }

    @Override
    public void setClickEvents() {
        binding.btnLogin.setOnClickListener(view -> Navigation.homeMenu(context));
        binding.btnRecoverAccount.setOnClickListener(view -> Navigation.recoverAccountMenu(context));
        binding.btnCreateAccount.setOnClickListener(view -> Navigation.createAccountMenu(context));
    }
}