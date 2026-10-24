package com.draw.rkb.drawsimply;

import android.os.Environment;
import java.io.File;
import java.io.IOException;

/* Decompiled from the supplied classes.dex_Decompiler.com(1).zip. */
public class Places {
    public static File getScreenshotFolder() {
        File file = new File(Environment.getExternalStorageDirectory(), "/Acrylic Paint/");
        file.mkdirs();
        return file;
    }

    public static File getCameraTempFolder() {
        File file = new File(Environment.getExternalStorageDirectory(), "/Acrylic Paint/Temp/");
        file.mkdirs();
        File file2 = new File(file, ".nomedia");
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File getCameraTempFile() {
        return new File(getCameraTempFolder(), "temp.jpg");
    }
}