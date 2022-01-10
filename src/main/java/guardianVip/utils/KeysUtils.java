package guardianVip.utils;

public class KeysUtils {


    public static String convert(Long key) throws IllegalAccessException {
        String stringKey = "";
        char[] chars = key.toString().toCharArray();
        for (char code : chars) {
            Long codeLong = Long.valueOf(Character.toString(code));
            String letter  = KeyEnums.valueOf(codeLong).name();
            stringKey = stringKey.concat(letter);
        }
        return stringKey;
    }
}
