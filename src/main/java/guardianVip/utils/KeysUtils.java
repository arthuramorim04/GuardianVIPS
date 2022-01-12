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

    public static Long convert(String key) throws IllegalAccessException {
        String stringKey = "";
        char[] chars = key.toString().toCharArray();
        for (char code : chars) {
            String letter  = String.valueOf(KeyEnums.valueOf(Character.toString(code)).getCode());
            stringKey = stringKey.concat(letter);
        }
        return Long.valueOf(stringKey);
    }
}
