package com.semison.wukong.service;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 2016/6/10 0010.
 */
public class PhoneServiceAndroidImplTest {
    PhoneService service;

    @Before
    public void setUp() throws Exception {
        service=new PhoneServiceAndroidImpl();
    }

    @Test
    public void startMonkey() throws Exception {
        service.startMonkey();
    }

    @Test
    public void saveSnapshot() throws Exception {
        String snapshotPath = "e:/test_js.png";
        service.saveSnapshot(snapshotPath);
    }

}