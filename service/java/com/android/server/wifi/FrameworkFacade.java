
package com.android.server.wifi;

import android.app.AppGlobals;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.net.ip.IpManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;

/**
 * This class allows overriding objects with mocks to write unit tests
 */
public class FrameworkFacade {
    public static final String TAG = "FrameworkFacade";

    public BaseWifiLogger makeBaseLogger() {
        return new BaseWifiLogger();
    }

    public BaseWifiLogger makeRealLogger(WifiStateMachine stateMachine, WifiNative wifiNative) {
        return new WifiLogger(stateMachine, wifiNative);
    }

    public int getIntegerSetting(Context context, String name, int def) {
        return Settings.Global.getInt(context.getContentResolver(), name, def);
    }

    public long getLongSetting(Context context, String name, long def) {
        return Settings.Global.getLong(context.getContentResolver(), name, def);
    }

    public String getStringSetting(Context context, String name) {
        return Settings.Global.getString(context.getContentResolver(), name);
    }

    public IBinder getService(String serviceName) {
        return ServiceManager.getService(serviceName);
    }

    public PendingIntent getBroadcast(Context context, int requestCode, Intent intent, int flags) {
        return PendingIntent.getBroadcast(context, requestCode, intent, flags);
    }

    public SupplicantStateTracker makeSupplicantStateTracker(Context context,
             WifiStateMachine wifiStateMachine, WifiConfigStore configStore, Handler handler) {
        return new SupplicantStateTracker(context, wifiStateMachine, configStore, handler);
    }

    public WifiApConfigStore makeApConfigStore(Context context) {
        return new WifiApConfigStore(context);
    }

    public long getTxPackets(String iface) {
        return TrafficStats.getTxPackets(iface);
    }

    public long getRxPackets(String iface) {
        return TrafficStats.getRxPackets(iface);
    }

    public IpManager makeIpManager(
            Context context, String iface, IpManager.Callback callback) {
        return new IpManager(context, iface, callback);
    }

    /**
     * Checks whether the given uid has been granted the given permission.
     * @param permName the permission to check
     * @param uid The uid to check
     * @return {@link PackageManager.PERMISSION_GRANTED} if the permission has been granted and
     *         {@link PackageManager.PERMISSION_DENIED} otherwise
     */
    public int checkUidPermission(String permName, int uid) throws RemoteException {
        return AppGlobals.getPackageManager().checkUidPermission(permName, uid);
    }
}