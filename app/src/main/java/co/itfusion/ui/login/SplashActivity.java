package co.itfusion.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import co.itfusion.dima.R;
import co.itfusion.dima.databinding.ActivitySplashBinding;
import co.itfusion.interfaces.ISetup;
import co.itfusion.utils.Constants;
import co.itfusion.utils.Navigation;
import co.itfusion.utils.Utils;

public class SplashActivity extends AppCompatActivity implements ISetup {

    private final Context context = this;
    private final Activity activity = this;
    private final Handler handler = new Handler();
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(activity.getLayoutInflater());
        setContentView(binding.getRoot());
        setDependencies();
    }

    @Override
    public void setDependencies() {
        if(getSupportActionBar() != null) getSupportActionBar().hide();
        Utils.UI.setupStatusBar(activity, context);

        startLoginThread();
    }

    private void startLoginThread() {
        handler.postDelayed(loginThread, Constants.DELAYS.ONE_SEC + Constants.DELAYS.HALF_SEC);
    }

    private final Runnable loginThread = new Runnable() {
        @Override
        public void run() {
            Navigation.loginMenu(context);
        }
    };
}