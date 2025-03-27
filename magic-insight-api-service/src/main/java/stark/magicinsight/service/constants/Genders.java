package stark.magicinsight.service.constants;

import stark.dataworks.basic.beans.StaticConstantExtractor;

import java.util.HashSet;

public class Genders
{
    private Genders()
    {
    }

    public static final String MALE = "male";
    public static final String FEMALE = "female";
    public static final String SECRET = "secret";

    private static final HashSet<String> genders;

    /*
    StaticConstantExtractor.extractConstants是一个工具方法，用于提取 Genders 类中所有 String 类型的静态常量。
     */
    static
    {
        genders = StaticConstantExtractor.extractConstants(Genders.class, String.class);
    }

    public static boolean inRange(String gender)
    {
        return genders.contains(gender);
    }

    /*
    to convert the set genders into an array of strings
     */
    public static String[] acceptableValues()
    {
        return genders.toArray(new String[0]);
    }
}
