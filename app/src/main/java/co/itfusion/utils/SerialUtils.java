package co.itfusion.utils;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import co.itfusion.dima.BuildConfig;
import co.itfusion.dima.R;

public class SerialUtils {
    public static final long LESCAN_PERIOD = 10000;
    public static final long CLASSIC_SCAN_PERIOD = 10000;

    // values have to be globally unique
    public static final String INTENT_ACTION_DISCONNECT = BuildConfig.APPLICATION_ID + ".Disconnect";
    public static final String NOTIFICATION_CHANNEL = BuildConfig.APPLICATION_ID + ".Channel";
    public static final String INTENT_CLASS_MAIN_ACTIVITY = BuildConfig.APPLICATION_ID + ".MainActivity";

    public static final String NAME_BLE_SCALE_DEVICE = "DVBE-001";
    public static final String NAME_BLE_RFID_DEVICE = "DVRF-001";
    public static final String NAME_CLASSIC_DERIVE_DEVICE = "DVAU-001";
    public static final String NAME_BLE_DEVELOPMENT_DEVICE = "MLT-BT05";
    public static final String NAME_CLASSIC_DEVELOPMENT_DEVICE = "HC-05";
    public static final String NAME_CLASSIC_DEVELOPMENT_DEVICE_2 = "HC-06";


    public static final String DIMA_SERVICE = "dima_service";
//    public static final String DERIVE_SERVICE = "derive_service";
//    public static final String SCALE_SERVICE = "scale_service";
//    public static final String RFID_SERVICE = "rfid_service";

    public enum ServiceAction { None, Disconnect, Connect, Reconnect }
    public enum ServiceStatus { None, ErrorIO, Error, Connecting, Connected, Listening, Disconnecting, Disconnected }

    public enum DeviceType {Scale, Rfid, Derive}

    // values have to be unique within each app
    public static final int NOTIFY_MANAGER_START_FOREGROUND_SERVICE = 1001;

    public enum ScanStatus {None, LeScan, Discovery, DiscoveryFinished}
    public enum ConnectionStatus {None, False, Pending, True}
    public enum BleConnection {None, DevelopmentBle, Scale, Rfid}

    public static String FILTER_SEND_TO_SCALE = "DV_SC_TX";
    public static String FILTER_SCALE_RECEPTION = "DV_SC_RX";
    public static String FILTER_SCALE_STATUS = "DV_SC_STATUS";
    public static String FILTER_SCALE_ACTIONS = "DV_SC_ACTIONS";

    public static String FILTER_SEND_TO_RFID = "DV_RF_TX";
    public static String FILTER_RFID_RECEPTION = "DV_RF_RX";
    public static String FILTER_RFID_STATUS = "DV_RF_STATUS";
    public static String FILTER_RFID_ACTIONS = "DV_RF_ACTIONS";

    public static String FILTER_SEND_TO_DERIVE = "DV_DR_TX";
    public static String FILTER_DERIVE_RECEPTION = "DV_DR_RX";
    public static String FILTER_DERIVE_STATUS = "DV_DR_STATUS";
    public static String FILTER_DERIVE_ACTIONS = "DV_DR_ACTIONS";
    public static String FILTER_DERIVE_SOCKET = "DV_DR_SOCKET";


    public static String FILTRO_ACCIONES_RECYCLER = "DV_ACCIONES_RECYCLER";

    public static String FILTER_DATA_SEND = "DV_TX";
    public static String FILTER_DATA_RECEPTION = "DV_RX";
    public static String FILTER_STATUS_SERVICE = "DV_STATUS";
    public static String FILTER_ACTIONS = "DV_ACTIONS";


    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_CASE = "case";
    public static final String EXTRA_ACTIONS = "action";
    public static final String EXTRA_STATUS = "status";


    public static final String EXTRA_EDITAR_CAMPO = "editarCampo";

    public static final String EXTRA_TIPO_EDICION = "tipoEdicion";
    public static final String EXTRA_HISTORICOS = "historicos";
    public static final String EXTRA_ULTIMAS_PESADAS = "ultimasPesadas";

    public static final String EXTRA_TIPO_MENU = "tipoMenu";
    public static final String EXTRA_NOMBRE_CAMPO = "nombreCampo";
    public static final String EXTRA_ID_CAMPO = "idCampo";


    public static final int CASO_CONEXION_EXITOSA = 0;
    public static final int CASE_SERIAL_IO_ERROR = 1;
    public static final int CASE_SERIAL_CONECTION_ERROR = 2;
    public static final int CASE_DISCONNECT_SERVICE = 3;
    public static final int CASE_BLUETOOTH_DISCONNECTED = 4;

    public static String NEW_LINE = "\r\n", TRAIL = "#";

    //identificadores de trama PESO
    public static String ID_SCALE_STABLE = "E" , ID_SCALE_DATA = "P";

    //identificador de trama RFID
    public static String
            COUNTRY_ARGENTINA = "0999", COUNTRY_CHINA = "0032";

    //tramas de envio
    public static String DATA_SCALE_TARA = "T";

    public static String getDeviceNameFromType(Context context, DeviceType deviceType)
    {
//        if(deviceType.equals(DeviceType.Derive))
//            return context.getString(R.string.text_device_name_derive);
//        else if (deviceType.equals(DeviceType.Rfid))
//            return context.getString(R.string.text_device_name_rfid);
//        else
//            return context.getString(R.string.text_device_name_scale);
        return "";
    }

    public static void actionsScaleBleService(Context context, ServiceAction serviceAction)
    {
//        Intent intent = new Intent(SerialUtils.FILTER_SCALE_ACTIONS);
//        intent.putExtra(SerialUtils.EXTRA_CASE, caso);
//        intent.putExtra(SerialUtils.EXTRA_MESSAGE, mensaje);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        Intent intent = new Intent(FILTER_SCALE_ACTIONS);
        intent.putExtra(EXTRA_ACTIONS, serviceAction);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void receptionScaleBleService(Context context, String mensaje)
    {
        Intent intent = new Intent(SerialUtils.FILTER_SCALE_RECEPTION);
        intent.putExtra(SerialUtils.EXTRA_MESSAGE, mensaje);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void statusScaleBleService(Context context, ServiceStatus serviceStatus)
    {
        Intent intent = new Intent(SerialUtils.FILTER_SCALE_STATUS);
        intent.putExtra(EXTRA_STATUS, serviceStatus);
//        intent.putExtra(SerialUtils.EXTRA_CASE, tipo);
//        intent.putExtra(SerialUtils.EXTRA_MESSAGE, nombreDispositivo);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendDataScaleBleService(Context context, String mensaje)
    {
        Intent intent = new Intent(SerialUtils.FILTER_SEND_TO_SCALE);
        intent.putExtra(SerialUtils.EXTRA_MESSAGE, mensaje);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void actionsRFIDBleService(Context context, ServiceAction serviceAction)
    {
//        Intent intent = new Intent(SerialUtils.FILTER_RFID_ACTIONS);
//        intent.putExtra(SerialUtils.EXTRA_CASE, caso);
//        intent.putExtra(SerialUtils.EXTRA_MESSAGE, mensaje);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        Intent intent = new Intent(FILTER_RFID_ACTIONS);
        intent.putExtra(EXTRA_ACTIONS, serviceAction);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void receptionRFIDBleService(Context context, String mensaje)
    {
        Intent intent = new Intent(SerialUtils.FILTER_RFID_RECEPTION);
        intent.putExtra(SerialUtils.EXTRA_MESSAGE, mensaje);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void statusRFIDBleService(Context context, ServiceStatus serviceStatus)
    {
        Intent intent = new Intent(SerialUtils.FILTER_RFID_STATUS);
        intent.putExtra(EXTRA_STATUS, serviceStatus);
//        intent.putExtra(SerialUtils.EXTRA_CASE, tipo);
//        intent.putExtra(SerialUtils.EXTRA_MESSAGE, nombreDispositivo);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendDataRFIDBleService(Context context, String mensaje)
    {
        Intent intent = new Intent(SerialUtils.FILTER_SEND_TO_RFID);
        intent.putExtra(SerialUtils.EXTRA_MESSAGE, mensaje);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void actionsDeriveSocket(Context context, ServiceAction serviceAction)
    {
        Intent intent = new Intent(FILTER_DERIVE_SOCKET);
        intent.putExtra(EXTRA_ACTIONS, serviceAction);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void actionsDeriveClassicService(Context context, ServiceAction serviceAction)
    {
        Intent intent = new Intent(FILTER_DERIVE_ACTIONS);
        intent.putExtra(EXTRA_ACTIONS, serviceAction);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void statusDeriveClassicService(Context context, ServiceStatus serviceStatus)
    {
        Intent intent = new Intent(FILTER_DERIVE_STATUS);
        intent.putExtra(EXTRA_STATUS, serviceStatus);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendDataDeriveClassicService(Context context, String message)
    {
        Intent intent = new Intent(FILTER_SEND_TO_DERIVE);
        intent.putExtra(EXTRA_MESSAGE, message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void receptionDeriveClassicService(Context context, String message)
    {
        Intent intent = new Intent(FILTER_DERIVE_RECEPTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void AccionesSobreServicio(Context context, int caso,  String mensaje)
    {
        Intent intent = new Intent(SerialUtils.FILTER_ACTIONS);
        intent.putExtra(SerialUtils.EXTRA_CASE, caso);
        intent.putExtra(SerialUtils.EXTRA_MESSAGE, mensaje);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void RecepcionDatosSerial(Context context, String mensaje)
    {
        Intent intent = new Intent(SerialUtils.FILTER_DATA_RECEPTION);
        intent.putExtra(SerialUtils.EXTRA_MESSAGE, mensaje);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void EstadoConexionSerial(Context context, int tipo, String nombreDispositivo)
    {
        Intent intent = new Intent(SerialUtils.FILTER_STATUS_SERVICE);
        intent.putExtra(SerialUtils.EXTRA_CASE, tipo);
        intent.putExtra(SerialUtils.EXTRA_MESSAGE, nombreDispositivo);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void AccionesSobreRecycler(Context context, String mensaje)
    {
        Intent intent = new Intent(SerialUtils.FILTRO_ACCIONES_RECYCLER);
        intent.putExtra(SerialUtils.EXTRA_MESSAGE, mensaje);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void EnviarDatosSerial(Context context, String mensaje)
    {
        Intent intent = new Intent(SerialUtils.FILTER_DATA_SEND);
        intent.putExtra(SerialUtils.EXTRA_MESSAGE, mensaje);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
