package com.tianjuan.pinyin;

import android.text.TextUtils;
import android.util.Pair;


import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;

public class PinYinUtil {

    private static final String SEPARATOR = "&";

    public static List<Pair<String, String>> getPairPinYin(String inputString) {

        List<Pair<String, String>> mPairs = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

        if (inputString.startsWith(" ")) {
            inputString = "  " + inputString.trim();
        }

        char[] input = inputString.toCharArray();
        int index = 0;
        try {
            String pinyin = PinyinHelper.toHanYuPinyinString(inputString + ",,", format, SEPARATOR, false);
            String[] pinyinArray = pinyin.split(SEPARATOR);
            //LogUtils.d("pinyin size: " + pinyin.length() + " ,pinyin: " + pinyin);

            //LogUtils.d("pinyinArray size: " + pinyinArray.length + " ,input: " + input.length);
            for (char word : input) {
                if (Character.toString(word).matches("[\\u4E00-\\u9FA5]+")) {
                    if (sb.length() > 0) {
                        mPairs.add(Pair.create(sb.toString(), null));
                        sb.delete(0, sb.length());
                    }
                    mPairs.add(Pair.create(String.valueOf(word), pinyinArray[index]));
                    index++;
                    //LogUtils.d("index: " + index);
                } else if (Character.toString(word).matches("[a-zA-Z]+")) {
                    sb.append(word);
                } else {
                    if (sb.length() > 0) {
                        mPairs.add(Pair.create(sb.toString(), null));
                        sb.delete(0, sb.length());
                    }
                    mPairs.add(Pair.create(String.valueOf(word), null));
                }

            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return mPairs;
    }

    public static List<Pair<String, String>> getPairPinYin3(String inputString) {
        List<Pair<String, String>> mPairs = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        if (inputString.startsWith(" ")) {
            inputString = "  " + inputString.trim();
        }

        char[] input = inputString.toCharArray();
        int index = 0;
        String pinyin = com.github.houbb.pinyin.util.PinyinHelper.toPinyin(inputString, PinyinStyleEnum.DEFAULT, "&");
        String[] pinyinArray = pinyin.split(SEPARATOR);
        //LogUtils.d("pinyin size: " + pinyin.length() + " ,pinyin: " + pinyin);

        //LogUtils.d("pinyinArray size: " + pinyinArray.length + " ,input: " + input.length);
        for (char word : input) {
            if (Character.toString(word).matches("[\\u4E00-\\u9FA5]+")) {
                if (sb.length() > 0) {
                    mPairs.add(Pair.create(sb.toString(), null));
                    sb.delete(0, sb.length());
                }
                mPairs.add(Pair.create(String.valueOf(word), pinyinArray[index]));
                index++;
                //LogUtils.d("index: " + index);
            } else if (Character.toString(word).matches("[a-zA-Z]+")) {
                sb.append(word);
            } else {
                if (sb.length() > 0) {
                    mPairs.add(Pair.create(sb.toString(), null));
                    sb.delete(0, sb.length());
                }
                mPairs.add(Pair.create(String.valueOf(word), null));
            }

        }
        return mPairs;
    }

    public static String getPairPinYin2(String inputString) throws BadHanyuPinyinOutputFormatCombination {

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

        if (inputString.startsWith(" ")) {
            inputString = "  " + inputString.trim();
        }

        char[] input = inputString.toCharArray();
        int index = 0;
        return PinyinHelper.toHanYuPinyinString(inputString + ",,", format, SEPARATOR, false);
    }

//    public static List<PinyinTextView.Token> getPairPinYin2(String inputString) {
//        List<PinyinTextView.Token> tokens = new ArrayList<>();
//        int lastType = 0;
//        StringBuilder sb = new StringBuilder();
//        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
//        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
//
//        char[] input = inputString.toCharArray();
//        int index = 0;
//        try {
//            String pinyin = PinyinHelper.toHanYuPinyinString(inputString + ",,", format, SEPARATOR, false);
//            String[] pinyinArray = pinyin.split(SEPARATOR);
//
//            for (char word : input) {
//                if (Character.toString(word).matches("[\\u4E00-\\u9FA5]+")) {
//                    if (sb.length() > 0) {
//                        PinyinTextView.Token token = new PinyinTextView.Token();
//                        token.setText(sb.toString());
//                        tokens.add(token);
//                        sb.delete(0, sb.length());
//                    }
//                    PinyinTextView.Token token = new PinyinTextView.Token();
//                    token.setText(String.valueOf(word));
//                    token.setPinyin(pinyinArray[index]);
//                    tokens.add(token);
//                    index++;
//                    lastType = 1;
//                } else if (Character.toString(word).matches("[a-zA-Z]+")) {
//                    if (lastType == 3 && sb.length() > 0) {
//                        PinyinTextView.Token token = new PinyinTextView.Token();
//                        token.setText(sb.toString());
//                        tokens.add(token);
//                        sb.delete(0, sb.length());
//                    }
//                    lastType = 2;
//                    sb.append(word);
//                } else if (Character.toString(word).matches("\\d+")) {
//                    if (lastType == 2 && sb.length() > 0) {
//                        PinyinTextView.Token token = new PinyinTextView.Token();
//                        token.setText(sb.toString());
//                        tokens.add(token);
//                        sb.delete(0, sb.length());
//                    }
//                    lastType = 3;
//                    sb.append(word);
//                } else {
//                    if (lastType != 0 && sb.length() > 0) {
//                        PinyinTextView.Token token = new PinyinTextView.Token();
//                        token.setText(sb.toString());
//                        tokens.add(token);
//                        sb.delete(0, sb.length());
//                    }
//                    sb.append(word);
//                    lastType = 0;
//                }
//            }
//
//            if (lastType != 1 && sb.length() > 0) {
//                PinyinTextView.Token token = new PinyinTextView.Token();
//                token.setText(sb.toString());
//                tokens.add(token);
//            }
//        } catch (BadHanyuPinyinOutputFormatCombination e) {
//            e.printStackTrace();
//        }
//        return tokens;
//    }

    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     *
     * @param inputString
     * @return
     */
    public static String getPinYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        String output = "";

        try {
            for (int i = 0; i < input.length; i++) {
                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    output += temp[0];
                } else
                    output += Character.toString(input[i]);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * 获取汉字串拼音首字母，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String getFirstSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音
     */
    public static String getFullSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString();
    }

    public static String convertToneNumber2ToneMark(final String pinyinStr) {
        String lowerCasePinyinStr = pinyinStr.toLowerCase();

        if (lowerCasePinyinStr.matches("[a-z]*[0-9]?")) {

            lowerCasePinyinStr = lowerCasePinyinStr.replaceAll("[06789]", "5");

            final char defautlCharValue = '$';
            final int defautlIndexValue = -1;

            char unmarkedVowel = defautlCharValue;
            int indexOfUnmarkedVowel = defautlIndexValue;

            final char charA = 'a';
            final char charE = 'e';
            final String ouStr = "ou";
            final String allUnmarkedVowelStr = "aeiouv";
            final String allMarkedVowelStr = "āáăàaēéĕèeīíĭìiōóŏòoūúŭùuǖǘǚǜü";

            if (lowerCasePinyinStr.matches("[a-z]*[0-9]")) {

                int tuneNumber =
                        Character.getNumericValue(lowerCasePinyinStr.charAt(lowerCasePinyinStr.length() - 1));

                int indexOfA = lowerCasePinyinStr.indexOf(charA);
                int indexOfE = lowerCasePinyinStr.indexOf(charE);
                int ouIndex = lowerCasePinyinStr.indexOf(ouStr);

                if (-1 != indexOfA) {
                    indexOfUnmarkedVowel = indexOfA;
                    unmarkedVowel = charA;
                } else if (-1 != indexOfE) {
                    indexOfUnmarkedVowel = indexOfE;
                    unmarkedVowel = charE;
                } else if (-1 != ouIndex) {
                    indexOfUnmarkedVowel = ouIndex;
                    unmarkedVowel = ouStr.charAt(0);
                } else {
                    for (int i = lowerCasePinyinStr.length() - 1; i >= 0; i--) {
                        if (String.valueOf(lowerCasePinyinStr.charAt(i)).matches(
                                "[" + allUnmarkedVowelStr + "]")) {
                            indexOfUnmarkedVowel = i;
                            unmarkedVowel = lowerCasePinyinStr.charAt(i);
                            break;
                        }
                    }
                }

                if ((defautlCharValue != unmarkedVowel) && (defautlIndexValue != indexOfUnmarkedVowel)) {
                    int rowIndex = allUnmarkedVowelStr.indexOf(unmarkedVowel);
                    int columnIndex = tuneNumber - 1;

                    int vowelLocation = rowIndex * 5 + columnIndex;

                    char markedVowel = allMarkedVowelStr.charAt(vowelLocation);
                    //Log.i("TAG", "convertToneNumber2ToneMark: " + markedVowel);

                    return lowerCasePinyinStr.substring(0, indexOfUnmarkedVowel).replaceAll("v", "ü")
                            + markedVowel
                            + lowerCasePinyinStr.substring(indexOfUnmarkedVowel + 1,
                            lowerCasePinyinStr.length() - 1).replaceAll("v", "ü");

                } else
                // error happens in the procedure of locating vowel
                {
                    return lowerCasePinyinStr;
                }
            } else
            // input string has no any tune number
            {
                // only replace v with ü (umlat) character
                return lowerCasePinyinStr.replaceAll("v", "ü");
            }
        } else
        // bad format
        {
            return lowerCasePinyinStr;
        }
    }

    public static String[] toneSplit(String tone) {
        if (TextUtils.isEmpty(tone)) {
            return null;
        }
        //Log.i("TAG", "toneSplit: " + tone);
        String[] pArray = new String[2];
        if (tone.length() == 1) {
            pArray[1] = tone;
            return pArray;
        }
        String phone = tone.substring(0, 2);
        String ch = "ch";
        String zh = "zh";
        String sh = "sh";
        for (int i = 0; i < 3; i++) {
            if (TextUtils.equals(ch, phone) || TextUtils.equals(zh, phone) || TextUtils.equals(sh, phone)) {
                pArray[0] = phone;
                pArray[1] = tone.substring(2);
                return pArray;
            }
        }
        pArray[0] = tone.substring(0, 1);
        pArray[1] = tone.substring(1);
        return pArray;
    }

}
