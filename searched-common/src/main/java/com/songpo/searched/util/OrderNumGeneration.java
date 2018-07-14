package com.songpo.searched.util;

import org.apache.commons.text.RandomStringGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

/**
 * @author 王晓森
 */
public class OrderNumGeneration {

    /**
     * 定义生成字符串范围
     */
    private static char[] pairs = {'0', '9'};

    /**
     * 初始化随机生成器
     */
    private static RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).filteredBy(LETTERS, DIGITS).build();

    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return com.songpo.searched.util.StringUtils.getDataTime().replace("-","") + machineId + String.format("%012d", hashCodeV);
    }

    /**
     * 生成日期格式的字符串，同时增加6为随机数字
     *
     * @return 字符串
     */
    public static String generateOrderId() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + generator.generate(6);
    }
}
