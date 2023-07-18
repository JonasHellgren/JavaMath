package dynamic_programming_best_path.repo;

import java.util.List;

public interface RepositoryInterface<T> {
    void add(T t);
    T get(String id);
    List<T> getAll();
    boolean exists(String id);
    void remove(String id);
    public void clearAll();
    int nofItems();
}
