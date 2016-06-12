package com.semison.wukong.service;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Administrator on 2016/6/10 0010.
 */
public interface PhoneService {


    void startMonkey(Consumer<String> f);

    void saveSnapshot(String path);

    void pullFile(String remotePath, String localPath);
}
