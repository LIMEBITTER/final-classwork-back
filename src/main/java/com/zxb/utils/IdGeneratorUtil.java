package com.zxb.utils;

import com.zxb.common.Constant;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
@Component
public class IdGeneratorUtil {
    private static final Random RANDOM = new Random();

    public String generateId(){
        // 获取当前时间并格式化为字符串
        String dateTimeStr = LocalDateTime.now().format(Constant.DATE_TIME_FORMATTER);

        // 生成4个随机字母
        StringBuilder randomChars = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            int index = RANDOM.nextInt(Constant.CHARACTERS.length());
            randomChars.append(Constant.CHARACTERS.charAt(index));
        }
        return Constant.PREFIX + dateTimeStr + randomChars.toString();
    }

}
