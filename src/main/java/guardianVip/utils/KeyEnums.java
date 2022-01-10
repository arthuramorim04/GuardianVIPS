package guardianVip.utils;

public enum KeyEnums {

    J(0),
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8),
    I(9);

    private long code;

    KeyEnums(long code){
        this.code = code;
    }



    public long getCode() {
        return code;
    }

    public static KeyEnums valueOf(long code) throws IllegalAccessException {
        for(KeyEnums value : KeyEnums.values()){
            if(value.getCode() == code){
                return value;
            }
        }
        throw new IllegalAccessException("Invalid KeyEnums code");
    }

}
