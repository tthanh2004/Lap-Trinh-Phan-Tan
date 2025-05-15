package ThucHanh.Lab2.Bakery;

interface lock {
    public void requestCS(int pid);
    public void releaseCS(int pid);
}