package co.itfusion.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.icu.util.UniversalTimeScale;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

import co.itfusion.dima.R;
import co.itfusion.dima.databinding.ActivityHomeBinding;
import co.itfusion.interfaces.ISetup;
import co.itfusion.ui.breathalyzer.BreathalyzerFragment;
import co.itfusion.ui.dashboard.DashboardFragment;
import co.itfusion.ui.overspeed.OverSpeedFragment;
import co.itfusion.ui.settings.SettingsFragment;
import co.itfusion.ui.wrecks.WrecksFragment;
import co.itfusion.utils.Utils;

public class HomeActivity extends AppCompatActivity implements ISetup {

    private final Context context = this;
    private final Activity activity = this;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(activity.getLayoutInflater());
        setContentView(binding.getRoot());
        setDependencies();
    }

    @Override
    public void setDependencies() {
        if(getSupportActionBar() != null) getSupportActionBar().hide();
        Utils.UI.setupStatusBar(activity, context);

        replaceFragment(new DashboardFragment());
        binding.bnvMainActivity.setSelectedItemId(R.id.menu_item_home);
        setClickEvents();
    }

    @Override
    public void setClickEvents() {
        binding.bnvMainActivity.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_wrecks:
                        replaceFragment(new WrecksFragment());
                        break;
                    case R.id.menu_item_alcohol:
                        replaceFragment(new BreathalyzerFragment());
                        break;
                    case R.id.menu_item_speed:
                        replaceFragment(new OverSpeedFragment());
                        break;
                    case R.id.menu_item_config:
                        replaceFragment(new SettingsFragment());
                        break;
                    case R.id.menu_item_home:
                    default:
                        replaceFragment(new DashboardFragment());
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flMainActivity, fragment);
        fragmentTransaction.commit();
    }
}