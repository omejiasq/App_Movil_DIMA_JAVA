package co.itfusion.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import co.itfusion.dima.R;
import co.itfusion.dima.databinding.FragmentDashboardBinding;
import co.itfusion.interfaces.ISetup;
import co.itfusion.utils.Navigation;

public class DashboardFragment extends Fragment implements ISetup {

    private Context context;
    private Activity activity;
    private FragmentDashboardBinding binding;

    public DashboardFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        setDependencies();
        return binding.getRoot();
    }

    @Override
    public void setDependencies() {

        binding.data.status.view.setBackground(AppCompatResources.getDrawable(context, R.drawable.icon_battery_full));
        binding.data.speed.view.setBackground(AppCompatResources.getDrawable(context, R.drawable.icon_speed));
        binding.data.wreck.view.setBackground(AppCompatResources.getDrawable(context, R.drawable.icon_dangerous));

        binding.data.alcohol.value.setText(R.string.test_breathalyzer);
        binding.data.alcohol.unit.setText(R.string.breathalyzer_unit);

        binding.data.status.title.setText(R.string.headset);
        binding.data.status.unit.setText(R.string.battery_headset);
        binding.data.status.value.setText(R.string.test_battery_percentage);

        binding.data.speed.title.setText(R.string.over_speed);
        binding.data.speed.unit.setText(R.string.speed_unit);
        binding.data.speed.value.setText(R.string.test_speed);

        binding.data.wreck.title.setText(R.string.wrecks);
        binding.data.wreck.unit.setText(R.string.test_date);
        binding.data.wreck.value.setText(R.string.test_wreck);

        setClickEvents();
    }

    @Override
    public void setClickEvents() {
        binding.data.status.getRoot().setOnClickListener(view -> {
            Navigation.searchBleDevices(context);
        });
    }

    @Override
    public void setInputEvents() {

    }
}
