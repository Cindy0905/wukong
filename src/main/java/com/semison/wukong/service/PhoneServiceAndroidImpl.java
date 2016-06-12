package com.semison.wukong.service;

import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by Administrator on 2016/6/10 0010.
 */
public class PhoneServiceAndroidImpl implements PhoneService {
    @Override
    public void startMonkey(Consumer<String> f) {
        String command = "adb shell monkey -p com.crash.zxy.mycrashapp " +
                "--throttle 1000 " +
                "-s 100 " +
                "--ignore-crashes " +
                "--ignore-timeouts " +
                "--ignore-security-exceptions " +
                "--ignore-native-crashes " +
                "--monitor-native-crashes " +
                "-v -v -v 100 ";
//                "> d:\\monkey.txt";
        Optional<Process> opProcess = executeCommand(command);
        if (opProcess.isPresent()) {
            Process process = opProcess.get();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            Executors.newSingleThreadExecutor().submit(() -> {
                reader.lines().forEach((x) -> {
                    if (x.startsWith("// CRASH")) {
                        UUID uuid = UUID.randomUUID();
                        String uuidStr = uuid.toString();
                        System.out.println("[wukong][snapshot]" + uuidStr);
                        f.accept(uuidStr);
                    }
                    System.out.println(x);
                });
            });
        }
    }

    @Override
    public void saveSnapshot(String path) {
//        String command = "adb shell screencap -p | sed 's/\\r$//' > " + path;
        String command = "adb shell screencap -p " + path;
//        String command="adb shell screencap -p /sdcard/js_test.png";
//        executeCommand(Lists.newArrayList(
//                "adb","shell","screencap","-p","/sdcard/js_test.png",
//                "|","sed","'s/\\r$//'",">",path
//
//        ));
//        String command="adb shell screencap -p | sed 's/\\r$//' > screen.png";
        executeCommand(command);
    }

    @Override
    public void pullFile(String remotePath, String localPath) {
        String command = "adb pull " + remotePath + " " + localPath;
        executeCommand(command);
    }

    Optional<Process> executeCommand(String command) {
        try {
//            Process process = new ProcessBuilder().command(command).start();

            Process process = Runtime.getRuntime().exec(command);
            System.out.println(command);
//            process.waitFor();
            return Optional.of(process);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        PhoneServiceAndroidImpl svc = new PhoneServiceAndroidImpl();
//        svc.startMonkey();
//        svc.saveSnapshot("js_test.png");
//        svc.saveSnapshot("/sdcard/wukong/js_test.png");
    }
}
