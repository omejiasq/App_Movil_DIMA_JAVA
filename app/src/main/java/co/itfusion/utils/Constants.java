package co.itfusion.utils;

import android.Manifest;

import co.itfusion.dima.BuildConfig;

public class Constants {

    public static String TAG = "DIMA";
    public static class DELAYS {
        public static int TWO_SEC = 2000;
        public static int ONE_SEC = 1000;
        public static int HALF_SEC = 500;
        public static int QUARTER_SEC = 250;
    }

    public static class Permissions
    {
        public static final String INTENT_ACTION_DISCONNECT = BuildConfig.APPLICATION_ID + ".Disconnect";
        public static final String NOTIFICATION_CHANNEL = BuildConfig.APPLICATION_ID + ".Channel";
        public static final String INTENT_CLASS_MAIN_ACTIVITY = BuildConfig.APPLICATION_ID + ".MainActivity";

        public static final int ACTIVATE_BLUETOOTH_REQUEST_CODE = 1000;
        public static final int REQUEST_PERMISSIONS_LOCATION = 1001;
        public static final int ACTIVATE_LOCATION_REQUEST_CODE = 1002;
        public static final int CODIGO_OBTENER_ARCHIVO = 1003;
        public static final int SOLICITUD_PERMISOS_TODOS = 1004;
        public static final int REQUEST_PERMISSIONS_STORAGE_API_29_AND_ABOVE = 1005;
        public static final int REQUEST_PERMISSIONS_STORAGE_API_28_OR_BELOW = 1006;
        public static final int SOLICIUD_PERMISOS_ALMACENAMIENTO = 1007;
        public static final int SOLICITUD_ABRIR_DIRECTORIO = 1008;
        public static final int REQUEST_GET_DOCUMENT = 1009;
        public static final int REQUEST_SHARE_DOCUMENT = 1010;
        public static final int REQUEST_PERMISSIONS_BLUETOOTH_API_31_AND_ABOVE = 1011;
        public static final int REQUEST_PERMISSIONS_BLUETOOTH_API_30_AND_BELOW = 1012;

        public static final String[] ALL_PERMISSIONS_API_28 =
                {
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };

        public static final String[] ALL_PERMISSIONS_API_29 =
                {
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };

        public static final String[] ALL_PERMISSIONS_API_30_AND_ABOVE =
                {
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_ADVERTISE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };

        public static final String[] PERMISSIONS_BLE_API_31_AND_ABOVE =
                {
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_ADVERTISE,
                };

        public static final String[] PERMISSIONS_BLE_API_30_AND_BELOW =
                {
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                };

        public static final String[] PERMISSIONS_STORAGE_API_28_AND_BELOW =
                {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };

        public static final String[] PERMISSIONS_STORAGE_API_29_AND_ABOVE =
                {
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };

        public static final String[] PERMISSION_LOCATION =
                {
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                };
    }
}
