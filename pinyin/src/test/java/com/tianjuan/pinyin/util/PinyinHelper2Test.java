package com.tianjuan.pinyin.util;

import android.util.Pair;

import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum;
import com.github.houbb.pinyin.util.PinyinHelper;
import com.tianjuan.pinyin.PinYinUtil;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class PinyinHelper2Test {

    /**
     * 《施氏食狮史》
     *
     * @since 0.3.0
     */
    @Test
    public void toPinyinTest() {
        String pinyin = PinyinHelper.toPinyin("石室诗士施氏，嗜狮，誓食十狮。施氏时时适市视狮。十时，适十狮适市。是时，适施氏适市。施氏视是十狮，恃矢势，使是十狮逝世。氏拾是十狮尸，适石室。石室湿，氏使侍拭石室。石室拭，氏始试食是十狮尸。食时，始识是十狮尸，实十石狮尸。试释是事。");
        System.out.println(pinyin);
    }

    @Test
    public void toPinyinTest2() {
//        String pinyin = PinyinHelper.toPinyin("北冥有鱼，abc 其名为鲲。鲲之大，不知其几千里也；化而为鸟，其名为鹏。鹏之背，不知其几千里也；怒而飞，其翼若垂天之云。是鸟也，海运则将徙于南冥。南冥者，天池也。");
//        System.out.println(pinyin);
//        String pinyin2 = PinyinHelper.toPinyin("行行出状元,行路难，一行，行人，行，行不行");
//        System.out.println(pinyin2);

        String p = "莪噯褈慶炎鍋";
        String p2 = "九曲黄河万里沙，浪淘风簸自天涯。如今直上银河去，同到牵牛织女家。";
        String p3 = "献给爸爸";
        String p4 = "这个调起高了";
        String p5 = "献给未来的爸爸";
        String p6 = "给与";
        String p7 = "太阳会给你";
        String p8 = "太阳踏着乐点翩翩起舞";
        String p9 = "最滑稽的要数这张";
        String p10 = "重复地温习";
        String p11 = "我的歌是这一剧中的辉煌";
        String p12 = "秋天，无论在什么地方的秋天，总是好的；可是啊，北国的秋，却特别地来得清，来得静，来得悲凉。我的不远千里，要从杭州赶上青岛，更要从青岛赶上北平来的理由，也不过想饱尝一尝这“秋”，这故都的秋味。";

        //String result = com.github.houbb.pinyin.util.PinyinHelper.toPinyin("重庆火锅");
        //System.out.println(result);

        String pinyin3 = PinyinHelper.toPinyin(p12, PinyinStyleEnum.DEFAULT,"&");
        System.out.println(pinyin3);
        //Assert.assertEquals("wǒ ài chóng qìng huǒ guō", pinyin3);
    }

    @Test
    public void testPinyin(){
        String p11 = "秋天，asb 无论在什么地方的秋天dd，总是好的；可是啊，北国的秋，却特别地来得清，来得静，来得悲凉。我的不远千里，要从杭州赶上青岛，更要从青岛赶上北平来的理由，也不过想饱尝一尝这“秋”，这故都的秋味。";

        String pinyin3 = PinyinHelper.toPinyin(p11, PinyinStyleEnum.DEFAULT,"&");
        System.out.println(pinyin3);
        System.out.println("=======================================================");

        try {
            String pairPinYin2 = PinYinUtil.getPairPinYin2(p11);
            System.out.println(pairPinYin2);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw new RuntimeException(e);
        }
    }

}
