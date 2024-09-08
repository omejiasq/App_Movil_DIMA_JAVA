package co.itfusion.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import co.itfusion.ui.bluetooth.BluetoothSearchActivity;
import co.itfusion.ui.bluetooth.BluetoothDiscoveryActivity;
import co.itfusion.ui.home.HomeActivity;
import co.itfusion.ui.login.LoginActivity;
import co.itfusion.ui.login.manage.CreateAccountActivity;
import co.itfusion.ui.login.manage.RecoverAccountActivity;

public class Navigation {

    public static void homeMenu(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void loginMenu(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void recoverAccountMenu(Context context ) {
        Intent intent = new Intent(context, RecoverAccountActivity.class);
        context.startActivity(intent);
    }

    public static void createAccountMenu(Context context) {
        Intent intent = new Intent(context, CreateAccountActivity.class);
        context.startActivity(intent);
    }

    public static void searchBleDevices(Context context) {
        Intent intent = new Intent(context, BluetoothDiscoveryActivity.class);
        context.startActivity(intent);
    }
}
