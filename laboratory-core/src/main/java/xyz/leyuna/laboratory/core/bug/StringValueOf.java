package xyz.leyuna.laboratory.core.bug;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-10-30
 */
public class StringValueOf {

    public static void main(String[] args) {
        Integer i = null;
        String str = String.valueOf(i);
        System.out.println(str);
    }
}
