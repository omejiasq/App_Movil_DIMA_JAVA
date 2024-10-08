package co.itfusion.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import co.itfusion.dima.R;
import co.itfusion.interfaces.ISerialListener;
import co.itfusion.serial.SerialSocket;
import co.itfusion.utils.Constants;
import co.itfusion.utils.SerialUtils;

public class SerialService extends Service implements ISerialListener {

    public class SerialBinder extends Binder { public SerialService getService() { return SerialService.this; } }

    private enum QueueType {Connect, ConnectError, Read, IoError}

    private static class QueueItem {
        QueueType type;
        byte[] data;
        Exception e;

        QueueItem(QueueType type, byte[] data, Exception e) { this.type=type; this.data=data; this.e=e; }
    }

    private final Handler mainLooper;
    private final IBinder binder;
    private final Queue<QueueItem> queue1, queue2;

    private SerialSocket socket;
    private ISerialListener listener;
    private boolean connected;

    /**
     * Lifecylce
     */
    public SerialService() {
        mainLooper = new Handler(Looper.getMainLooper());
        binder = new SerialBinder();
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();

        BroadcastReceiver SendDataBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(intent.getExtras() != null) {
                    String dataToSend = (String) intent.getExtras().get(SerialUtils.EXTRA_MESSAGE);
                    try {
                        if(dataToSend != null) {
                            byte[] bytes = dataToSend.getBytes();
                            if(bytes != null) write(bytes);
                        }
                    } catch (IOException e) { e.printStackTrace(); }
                }
            }
        };

        BroadcastReceiver ActionOverServiceBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SerialUtils.ServiceAction serviceAction = null;
                if(intent.getExtras() != null) {
                    try { serviceAction = (SerialUtils.ServiceAction) intent.getExtras().get(SerialUtils.EXTRA_ACTIONS); }
                    catch (Exception ignored) { serviceAction = SerialUtils.ServiceAction.None; }
                } else serviceAction = SerialUtils.ServiceAction.None;

                switch (Objects.requireNonNull(serviceAction)) {
                    case None:
                    case Reconnect:
                    case Connect:
                        break;
                    case Disconnect:
                        disconnect();
                        break;
                }


//                if(caso == Constantes.CASO_DESCONECTAR_BT) if(mensaje.equals(nombreDispositivo())) disconnect();
//                else Log.d(Constantes.TAG, "el nombre de dispositivo no coincide, es: " + nombreDispositivo());
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(SendDataBroadcastReceiver, new IntentFilter(SerialUtils.FILTER_DATA_SEND));
        LocalBroadcastManager.getInstance(this).registerReceiver(ActionOverServiceBroadcastReceiver, new IntentFilter(SerialUtils.FILTER_ACTIONS));
    }

    @Override
    public void onDestroy() {
        cancelNotification();
        disconnect();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

//
//    public final String nombreDispositivo()
//    {
//        return this.socket.nombreDispositivo;
//    }

//    public final String NombreDispositivoBalanza()
//    {
//        if(this.socket.ObtenerNombreBalanza() != null) return this.socket.ObtenerNombreBalanza();
//        else return "";
//    }
//
//    public final String NombreDispositivoRFID()
//    {
//        if(this.socket.ObtenerNombreRFID() != null) return this.socket.ObtenerNombreRFID();
//        else return "";
//    }


    /**
     * Api
     */
    public void connect(SerialSocket socket) throws IOException {
//        nombreDispositivo = socket.getName();
        socket.connect(this);
        this.socket = socket;
        connected = true;
    }

    public void disconnect() {
        connected = false; // ignore data,errors while disconnecting
        cancelNotification();
        if(socket != null) {
            socket.disconnect();
            socket = null;
        }
    }

    private void write(byte[] data) throws IOException {
        if(!connected)
            throw new IOException("not connected");
        socket.write(data);
    }

    public void attach(ISerialListener listener) {
        if(Looper.getMainLooper().getThread() != Thread.currentThread())
            throw new IllegalArgumentException("not in main thread");
        cancelNotification();
        // use synchronized() to prevent new items in queue2
        // new items will not be added to queue1 because mainLooper.post and attach() run in main thread
        synchronized (this) {
            this.listener = listener;
        }
        for(QueueItem item : queue1) {
            switch(item.type) {
                case Connect:       listener.onSerialConnect      (); break;
                case ConnectError:  listener.onSerialConnectError (item.e); break;
                case Read:          listener.onSerialRead         (item.data); break;
                case IoError:       listener.onSerialIoError      (item.e); break;
            }
        }
        for(QueueItem item : queue2) {
            switch(item.type) {
                case Connect:       listener.onSerialConnect      (); break;
                case ConnectError:  listener.onSerialConnectError (item.e); break;
                case Read:          listener.onSerialRead         (item.data); break;
                case IoError:       listener.onSerialIoError      (item.e); break;
            }
        }
        queue1.clear();
        queue2.clear();
    }

    public void detach() {
        if(connected)
            createNotification();
        // items already in event queue (posted before detach() to mainLooper) will end up in queue1
        // items occurring later, will be moved directly to queue2
        // detach() and mainLooper.post run in the main thread, so all items are caught
        listener = null;
    }

    private void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(SerialUtils.NOTIFICATION_CHANNEL, "Background service", NotificationManager.IMPORTANCE_LOW);
            nc.setShowBadge(false);
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.createNotificationChannel(nc);
        }
        Intent disconnectIntent = new Intent()
                .setAction(SerialUtils.INTENT_ACTION_DISCONNECT);
        Intent restartIntent = new Intent()
                .setClassName(this, SerialUtils.INTENT_CLASS_MAIN_ACTIVITY)
                .setAction(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent disconnectPendingIntent = PendingIntent.getBroadcast(this, 1, disconnectIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent restartPendingIntent = PendingIntent.getActivity(this, 1, restartIntent,  PendingIntent.FLAG_UPDATE_CURRENT);
        //TODO: UPDATE
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, SerialUtils.NOTIFICATION_CHANNEL)
//                .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
//                .setColor(getResources().getColor(R.color.colorPrimary))
//                .setContentTitle(getResources().getString(R.string.app_name))
//                .setContentText(socket != null ? "Connected to "+socket.getName() : "Background Service")
//                .setContentIntent(restartPendingIntent)
//                .setOngoing(true)
//                .addAction(new NotificationCompat.Action(R.drawable.ic_baseline_clear_all_24, "Disconnect", disconnectPendingIntent));
//        // @drawable/ic_notification created with Android Studio -> New -> Image Asset using @color/colorPrimaryDark as background color
//        // Android < API 21 does not support vectorDrawables in notifications, so both drawables used here, are created as .png instead of .xml
//        Notification notification = builder.build();
//        startForeground(SerialUtils.NOTIFY_MANAGER_START_FOREGROUND_SERVICE, notification);
    }

    private void cancelNotification() {
        stopForeground(true);
    }

    /**
     * SerialListener
     */
    public void onSerialConnect() {
        if(connected) {
            synchronized (this) {
                if (listener != null) {
                    mainLooper.post(() -> {
                        if (listener != null) {
                            listener.onSerialConnect();
                        } else {
                            queue1.add(new QueueItem(QueueType.Connect, null, null));
                        }
                    });
                } else {
                    queue2.add(new QueueItem(QueueType.Connect, null, null));
                }
            }
            SerialUtils.EstadoConexionSerial(this, 0, String.valueOf(this.socket.getName()));
        }
    }

    public void onSerialConnectError(Exception e) {
        if(connected) {
            synchronized (this) {
                if (listener != null) {
                    mainLooper.post(() -> {
                        if (listener != null) {
                            listener.onSerialConnectError(e);
                        } else {
                            queue1.add(new QueueItem(QueueType.ConnectError, null, e));
                            cancelNotification();
                            disconnect();
                        }
                    });
                } else {
                    queue2.add(new QueueItem(QueueType.ConnectError, null, e));
                    cancelNotification();
                    disconnect();
                }
            }
            SerialUtils.EstadoConexionSerial(this, 2, String.valueOf(this.socket.getName()));
        }
    }

    public void onSerialRead(byte[] data) {
        if(connected) {
            synchronized (this) {
                if (listener != null) {
                    mainLooper.post(() -> {
                        if (listener != null) {
                            listener.onSerialRead(data);
                        } else {
                            queue1.add(new QueueItem(QueueType.Read, data, null));
                        }
                    });
                } else {
                    queue2.add(new QueueItem(QueueType.Read, data, null));
                }
            }
            SerialUtils.RecepcionDatosSerial(this, new String(data));
        }
    }

    public void onSerialIoError(Exception e) {
        if(connected) {
            synchronized (this) {
                if (listener != null) {
                    mainLooper.post(() -> {
                        if (listener != null) {
                            listener.onSerialIoError(e);
                        } else {
                            queue1.add(new QueueItem(QueueType.IoError, null, e));
                            cancelNotification();
                            disconnect();
                        }
                    });
                } else {
                    queue2.add(new QueueItem(QueueType.IoError, null, e));
                    cancelNotification();
                    disconnect();
                }
            }
            SerialUtils.EstadoConexionSerial(this, 1, String.valueOf(this.socket.getName()));
        }
    }
}
