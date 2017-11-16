package com.open.utislib.cache;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;
import com.open.utislib.base.ConvertUtils;
import com.open.utislib.file.CloseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by vivian on 2017/11/14.
 * DiskLruCache utils (https://github.com/JakeWharton/DiskLruCache)
 * add new support following object save and get
 * Bitmap bitmap, byte[] value, String value, JSONObject jsonObject, JSONArray jsonArray,
 * Serializable , Drawable
 * Attention: you need setDiskLruCache before you call any method here
 */
public final class DiskLruCacheUtils {
    private static final String TAG = "DiskLruCacheUtils";
    // DiskLruCache object, set from outside
    private static DiskLruCache mDiskLruCache;

    /**
     * Sets disk lru cache.
     * you need setDiskLruCache before you call any method here
     *
     * @param cache the cache
     */
    public static void setDiskLruCache(DiskLruCache cache) {
        mDiskLruCache = cache;
    }

    /**
     * Set String
     *
     * @param key   the key
     * @param value the value
     */
    public static void set(String key, String value) {
        DiskLruCache.Editor editor = getEditor(key);
        if (editor == null) {
            return;
        }

        try {
            editor.set(0, value);
            // write ,CLEAN
            editor.commit();
        } catch (IOException e) {
            Log.w(TAG, "set() ex ", e);
            safelyAbortEditor(editor);
        }
    }

    /**
     * Gets string value
     *
     * @param key the key
     * @return the string
     */
    public static String getString(String key) {
        DiskLruCache.Snapshot snapShot = getSnapshot(key);
        if (snapShot == null) {
            return null;
        }

        try {
            return snapShot.getString(0);
        } catch (IOException e) {
            Log.w(TAG, "getString() ex ", e);
        }

        return null;
    }

    /**
     * Set json.
     *
     * @param key        the key
     * @param jsonObject the json object
     */
    public static void set(String key, JSONObject jsonObject) {
        set(key, jsonObject.toString());
    }

    /**
     * Gets json.
     *
     * @param key the key
     * @return the json
     */
    public JSONObject getJson(String key) {
        String val = getString(key);
        if (val == null) {
            return null;
        }

        try {
            return new JSONObject(val);
        } catch (JSONException e) {
            Log.w(TAG, "getJson() ex ", e);
        }

        return null;
    }

    /**
     * Set json array.
     *
     * @param key       the key
     * @param jsonArray the json array
     */
    public void set(String key, JSONArray jsonArray) {
        set(key, jsonArray.toString());
    }

    /**
     * Gets json array.
     *
     * @param key the key
     * @return the json array
     */
    public JSONArray getJSONArray(String key) {
        String val = getString(key);
        if (val == null) {
            return null;
        }

        try {
            JSONArray obj = new JSONArray(val);
            return obj;
        } catch (Exception e) {
            Log.w(TAG, "getJSONArray() ex ", e);
        }

        return null;
    }

    /**
     * set byte[]
     *
     * @param key   the key
     * @param value the value
     */
    public void set(String key, byte[] value) {
        DiskLruCache.Editor editor = getEditor(key);
        if (editor == null) {
            return;
        }

        OutputStream out = null;
        try {
            out = editor.newOutputStream(0);
            out.write(value);
            out.flush();
            //write CLEAN
            editor.commit();
        } catch (Exception e) {
            Log.w(TAG, "set() ex ", e);
            safelyAbortEditor(editor);
        } finally {
            CloseUtils.closeIO(out);
        }
    }

    /**
     * Get bytes byte[].
     *
     * @param key the key
     * @return the byte[]
     */
    public static byte[] getBytes(String key) {
        DiskLruCache.Snapshot snapShot = getSnapshot(key);
        if (snapShot == null) {
            return null;
        }

        // get input stream
        InputStream ins = snapShot.getInputStream(0);
        if (ins == null) {
            return null;
        }

        try {
            // write 256 each time
            final int UNIT = 256;
            // write input stream to byte array
            ByteArrayOutputStream array = new ByteArrayOutputStream();
            byte[] buf = new byte[UNIT];
            int len = 0;
            while ((len = ins.read(buf)) != -1) {
                array.write(buf, 0, len);
            }
            return array.toByteArray();
        } catch (IOException e) {
            Log.w(TAG, "getBytes ex ", e);
        }

        return null;
    }

    /**
     * Set Serializable.
     *
     * @param key   the key
     * @param value the value
     */
    public void set(String key, Serializable value) {
        DiskLruCache.Editor editor = getEditor(key);
        if (editor == null) {
            return;
        }

        ObjectOutputStream objectstream = null;
        try {
            OutputStream os = editor.newOutputStream(0);
            objectstream = new ObjectOutputStream(os);
            objectstream.writeObject(value);
            objectstream.flush();
            editor.commit();
        } catch (IOException e) {
            Log.w(TAG, "set() ex ", e);
            safelyAbortEditor(editor);
        } finally {
            CloseUtils.closeIO(objectstream);
        }
    }

    /**
     * Gets serializable.
     *
     * @param <T> the type parameter
     * @param key the key
     * @return the serializable
     */
    public <T> T getSerializable(String key) {
        DiskLruCache.Snapshot snapShot = getSnapshot(key);
        if (snapShot == null) {
            return null;
        }

        // get input stream
        InputStream ins = snapShot.getInputStream(0);
        if (ins == null) {
            return null;
        }

        ObjectInputStream stream = null;
        try {
            stream = new ObjectInputStream(ins);
            return (T) stream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            Log.w(TAG, "getSerializable ex ", e);
        } finally {
            CloseUtils.closeIO(stream);
        }

        return null;
    }


    /**
     * Set Bitmap.
     *
     * @param key    the key
     * @param bitmap the bitmap
     * @param format Bitmap.CompressFormat
     */
    public void set(String key, Bitmap bitmap, Bitmap.CompressFormat format) {
        set(key, ConvertUtils.bitmap2Bytes(bitmap, format));
    }

    /**
     * Gets bitmap.
     *
     * @param key the key
     * @return the bitmap
     */
    public Bitmap getBitmap(String key) {
        byte[] bytes = getBytes(key);
        if (bytes == null) {
            return null;
        }
        return ConvertUtils.bytes2Bitmap(bytes);
    }

    /**
     * Set Drawable.
     *
     * @param key    the key
     * @param value  the value
     * @param format Bitmap.CompressFormat
     */
    public void set(String key, Drawable value, Bitmap.CompressFormat format) {
        set(key, ConvertUtils.drawable2Bitmap(value), format);
    }

    /**
     * Gets drawable.
     *
     * @param key       the key
     * @param resources the resources
     * @return the drawable
     */
    public Drawable getDrawable(String key, Resources resources) {
        byte[] bytes = getBytes(key);
        if (bytes == null) {
            return null;
        }
        return ConvertUtils.bitmap2Drawable(resources, ConvertUtils.bytes2Bitmap(bytes));
    }

    /**
     * Get input stream.
     *
     * @param key the key
     * @return the input stream
     */
    public static InputStream get(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            //not find entry , or entry.readable = false
            if (snapshot == null) {
                Log.w(TAG, "no entry or entry.readable = false");
                return null;
            }
            //write READ
            return snapshot.getInputStream(0);
        } catch (IOException e) {
            Log.w(TAG, "get ex ", e);
        }

        return null;
    }

    /**
     * get  Editor to write cache
     *
     * @param key the key
     */
    private static DiskLruCache.Editor getEditor(String key) {
        if (mDiskLruCache == null) {
            Log.e(TAG, "you need call setDiskLruCache() to set mDiskLruCache first.");
            return null;
        }
        try {
            return mDiskLruCache.edit(key);
        } catch (IOException e) {
            Log.w(TAG, "getEditor() ex ", e);
        }

        return null;
    }

    /**
     * get  Snapshot to read cache
     *
     * @param key the key
     */
    private static DiskLruCache.Snapshot getSnapshot(String key) {
        if (mDiskLruCache == null) {
            Log.e(TAG, "you need call setDiskLruCache() to set mDiskLruCache first.");
            return null;
        }
        try {
            return mDiskLruCache.get(key);
        } catch (IOException e) {
            Log.w(TAG, "getSnapshot() ex ", e);
        }

        return null;
    }

    /**
     * remove key, will write REMOVE.
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean remove(String key) {
        try {
            return mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * abort safely  Snapshot to read cache
     *
     * @param editor the editor
     */
    private static void safelyAbortEditor(DiskLruCache.Editor editor) {
        try {
            // write fail, REMOVE
            editor.abort();
        } catch (IOException e) {
            Log.w(TAG, "safelyAbortEditor() editor.abort ex ", e);
        }
    }

}
