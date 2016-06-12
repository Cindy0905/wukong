package com.semison.wukong;

import com.semison.wukong.service.PhoneService;
import com.semison.wukong.service.PhoneServiceAndroidImpl;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2016/6/10 0010.
 */
public class Main {
    public static void main(String[] args) {
        PhoneService phoneService = new PhoneServiceAndroidImpl();

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        AtomicLong idSequence = new AtomicLong(0);

        phoneService.startMonkey((s) -> {
            long id = idSequence.get();
            String dirName = "E:\\monkey_tmp\\" + s;
            File theDir = new File(dirName);
            if (theDir.mkdir()) {
                for (int i = 1; i <4; i++) {
                    String remotePath = "/sdcard/wukong/snapshot" + (id - i) % 100 + ".png";
                    String localPath = dirName + "\\" + (3 - i) % 100 + ".png";
                    phoneService.pullFile(remotePath, localPath);
                }
            }
        });

        service.scheduleAtFixedRate(() -> {
            long id = idSequence.incrementAndGet();
            phoneService.saveSnapshot("/sdcard/wukong/snapshot" + id % 100 + ".png");
        }, 5000, 500, TimeUnit.MILLISECONDS);
    }
}
