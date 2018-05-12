package com.example.leidong.ldkeyguard.secures;

import com.example.leidong.ldkeyguard.MyApplication;
import com.example.leidong.ldkeyguard.constants.Constants;
import com.example.leidong.ldkeyguard.storage.MySP;

import java.util.Random;

/**
 * Created by Lei Dong on 2018/5/12.
 */
public class GenKey {
    private static final String LOWER_CASE_ALPHABAT_KEYS = "abcdefghijklmnopqrstuvwxyz";
    private static final String HIGHER_CASE_ALPHABAT_KEYS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_KEYS = "0123456789";
    private static final String SYMBOL_KEYS = "!@#$%&*";

    private static final int ALPHABATS_NUM = LOWER_CASE_ALPHABAT_KEYS.length();
    private static final int NUMBER_NUM = NUMBER_KEYS.length();
    private static final int SYMBOL_NUM = SYMBOL_KEYS.length();



    /**
     * 生成固定长度的密钥
     *
     * @return
     */
    public static String genKey() {
        StringBuilder res = new StringBuilder();

        MySP mMySP = new MySP(MyApplication.getsContext()).getmMySP();
        boolean isAlphabatOn = mMySP.load(Constants.IS_ALPHABAT_ON, false);
        boolean isNumberOn = mMySP.load(Constants.IS_NUMBER_ON, false);
        boolean isSymbolOn = mMySP.load(Constants.IS_SYMBOL_ON, false);
        int keyLength = mMySP.load(Constants.KEY_LENGTH, 8);

        String totalKeys = LOWER_CASE_ALPHABAT_KEYS;
        if(isAlphabatOn){
            totalKeys += HIGHER_CASE_ALPHABAT_KEYS;
        }
        if(isNumberOn){
            totalKeys += NUMBER_KEYS;
        }
        if(isSymbolOn){
            totalKeys += SYMBOL_KEYS;
        }

        int totalKeysLength = totalKeys.length();
        Random random = new Random();
        for(int i = 0; i < keyLength; i++){
            int cursor = random.nextInt(totalKeysLength);
            res.append(totalKeys.charAt(cursor));
        }

        return res.toString();
    }
}
