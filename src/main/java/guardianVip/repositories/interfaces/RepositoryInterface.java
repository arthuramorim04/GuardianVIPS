package guardianVip.repositories.interfaces;

import java.util.List;
import java.util.Map;

public interface RepositoryInterface<T, K> {

    T selectById(Long id);
    Map<K,T> getAll();
    T seletcByName(K key);


}
