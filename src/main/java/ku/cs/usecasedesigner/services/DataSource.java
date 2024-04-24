package ku.cs.usecasedesigner.services;

public interface DataSource <T> {
    T readData();
    void writeData(T t);
}
