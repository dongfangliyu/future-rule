package com.zw.rule.core;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private final static int MAX_NUM = 9999;
    private final String processId;
    private static int mac = 0;
    private static String longMac;
    private final AtomicInteger count = new AtomicInteger(0);

    private IdGenerator() {
        mac = this.getMac();
        longMac = this.getLongMac();
        String name = ManagementFactory.getRuntimeMXBean().getName();
        processId = name.split("@")[0];
    }

    public static IdGenerator getInstance() {
        return GeneratorInstance.idGenerator;
    }

    private String getLongMac() {
        return null;
    }

    private int getMac() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            byte[] hardwareAddress = NetworkInterface.getByInetAddress(address).getHardwareAddress();
            return this.bytesToInt(hardwareAddress);
        } catch (Exception e) {
            Random random = new Random();
            return random.nextInt(9000);
        }
    }
    private  int bytesToInt(byte[] bytes) {
        int addr = bytes[3] & 0xFF;
        addr |= ((bytes[2] << 8) & 0xFF00);
        addr |= ((bytes[1] << 16) & 0xFF0000);
        addr |= ((bytes[0] << 24) & 0xFF000000);
        return addr;
    }


    public String nextId() {
        long timeMillis = System.currentTimeMillis();
        return String.valueOf(mac) + timeMillis + getAtomicNum();
    }

    private int getAtomicNum() {
        int num = count.incrementAndGet();
        if (count.compareAndSet(MAX_NUM, 0)) {
            num = count.incrementAndGet();
        }
        return num;
    }

    private static class GeneratorInstance {
        static IdGenerator idGenerator = new IdGenerator();
    }
}
