package ThucHanh.Lab2.Dekker;

interface Lock {
    void requestCS(int tid);
    void releaseCS(int tid);
}