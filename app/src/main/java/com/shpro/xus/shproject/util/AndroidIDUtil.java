package com.shpro.xus.shproject.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xus on 2016/11/3.
 *唯一标识类
 */
public class AndroidIDUtil {
    private static String m_szBTMAC;
    private static String m_szWLANMAC;
    private static String m_szAndroidID;
    private static String m_szDevIDShort;
    private static String szImei;

    /**
     * 蓝牙
     * @return
     */
    private static String getM_szBTMAC() {
        if (TextUtils.isEmpty(m_szBTMAC)) {
            BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
            m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            m_szBTMAC = m_BluetoothAdapter.getAddress();
        }
        return m_szBTMAC;
    }

    /**
     * mac
     * @param context
     * @return
     */
    private static String getM_szWLANMAC(Context context) {
        if (TextUtils.isEmpty(m_szWLANMAC)) {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
        }
        return m_szWLANMAC;
    }

    /**
     * android id
     * @param context
     * @return
     */
    private static String getM_szAndroidID(Context context) {
        if (TextUtils.isEmpty(m_szAndroidID)) {
            m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return m_szAndroidID;
    }

    /**
     * 硬件信息合一码
     * @return
     */
    private static String getM_szDevIDShort() {
        if (TextUtils.isEmpty(m_szDevIDShort)) {
            m_szDevIDShort = "35" + //we make this look like a valid IMEI
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.CPU_ABI.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10;
        }
        return m_szDevIDShort;
    }

    /**
     * Imei码
     * @param context
     * @return
     */
    private static String getSzImei(Context context) {
        if (TextUtils.isEmpty(szImei)) {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
            szImei = TelephonyMgr.getDeviceId();
        }
        return szImei;
    }

    /**
     * 五码合一标识
     * @param context
     * @return
     */
    public static String getID(Context context) {
        String m_szLongID = getM_szBTMAC() + getM_szWLANMAC(context)
                + getM_szAndroidID(context) + getM_szDevIDShort() + getSzImei(context);
// compute md5
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
// get md5 bytes
        byte p_md5Data[] = m.digest();
// create a hex string
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
// if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
// add number to string
            m_szUniqueID += Integer.toHexString(b);
        }   // hex string to uppercase
        m_szUniqueID = m_szUniqueID.toUpperCase();
        return m_szUniqueID;
    }
}
