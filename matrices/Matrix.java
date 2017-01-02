public interface Matrix<T> {
    public T get(int i, int j);
    
    public void set(T value, int i, int j);
    
    public int getWidth();
    public int getHeight();
}
