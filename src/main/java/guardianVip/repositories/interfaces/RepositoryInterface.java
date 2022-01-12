package guardianVip.repositories.interfaces;

import java.util.List;
import java.util.Map;

public interface RepositoryInterface<T, K> {

    T selectById(String uuid);
    Map<K,T> getAll();


}
