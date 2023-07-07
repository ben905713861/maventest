package com.demo.maventest;

/**
 * 查表法：将数字拆解，各数位直接查表转换，最后拼接
 *
 * @author wuxiaobin
 */
public class RomanNum {
    private static final char[] ROAM_NUM = new char[] {'I', 'V', 'X', 'L', 'C', 'D', 'M'};

    private static String[][] fastTable = new String[(int) Math.ceil(ROAM_NUM.length / 2F)][10];

//    private static final String[][] fastTable = new String[][] {
//        {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}, // 0-9
//        {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}, // 10-90
//        {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}, // 100-900
//        {"", "M", "MM", "MMM", "", "", "", "", "", ""}, // 1000-3000
//    };

    static {
        // 构造数位对应罗马数字映射表，像上面注释的那样
        for (int i = 0; i < ROAM_NUM.length; i += 2) {
            int index = i / 2;
            String[] row = fastTable[index];
            String roamNum = Character.toString(ROAM_NUM[i]);
            row[0] = "";
            row[1] = roamNum;
            row[2] = roamNum.repeat(2);
            row[3] = roamNum.repeat(3);
            if (i + 1 < ROAM_NUM.length) {
                String roamNum2 = Character.toString(ROAM_NUM[i + 1]);
                row[4] = roamNum + roamNum2;
                row[5] = roamNum2;
                row[6] = roamNum2 + row[1];
                row[7] = roamNum2 + row[2];
                row[8] = roamNum2 + row[3];
            }
            if (i + 2 < ROAM_NUM.length) {
                String roamNum3 = Character.toString(ROAM_NUM[i + 2]);
                row[9] = row[1] + roamNum3;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int num = i * 10 + j;
                String res = transform(num);
                System.out.println(num + "=" + res);
            }
        }
        System.out.println(100 + "=" + transform(100));
        System.out.println(101 + "=" + transform(101));
        System.out.println(214 + "=" + transform(214));
        System.out.println(316 + "=" + transform(316));
        System.out.println(500 + "=" + transform(500));
        System.out.println(555 + "=" + transform(555));
        System.out.println(1000 + "=" + transform(1000));
    }

    private static String transform(int input) {
        // 从个位开始分解，分解结果转换后拼接
        StringBuilder res = new StringBuilder();
        for (int i = 0, temp = input; temp > 0; i++) {
            int num = temp % 10;
            temp -= num;
            temp = temp / 10;
            res.insert(0, fastTable[i][num]);
        };
        return res.toString();
    }
}
