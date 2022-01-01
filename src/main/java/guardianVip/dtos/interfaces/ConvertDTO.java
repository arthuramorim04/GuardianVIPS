package guardianVip.dtos.interfaces;

public interface ConvertDTO<T, D> {

    T convert();
    D convert(T t);
}
