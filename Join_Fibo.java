import Mutex.openDoor;


public class Join_Fibo extends Thread {
    static openDoor mutex = new openDoor();
    int n;
    int result;
    public Join_Fibo(int n) {
        this.n = n;
    }
    public void run() {
        System.out.println("Thread " + Thread.currentThread().getId() + " is calculating Fibo(" + n + ")");
        if ((n == 0) || (n == 1)) {
            result = 1;
            System.out.println("Thread " + Thread.currentThread().getId() + " computed Fibo(" + n + ") = 1");
        } else {
            Join_Fibo f1 = new Join_Fibo(n - 1);
            Join_Fibo f2 = new Join_Fibo(n - 2);
            f1.start();
            f2.start();
            try {
                f1.join();
                f2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + Thread.currentThread().getId() + " acquired results of Fibo(" + (n-1) + ") and Fibo(" + (n-2) + ")");
            mutex.lock();
            try{
                System.out.println("Thread " + Thread.currentThread().getId() + " entering critical section to compute Fibo(" + n + ")");
                result = f1.getResult() + f2.getResult();
                System.out.println("Thread " + Thread.currentThread().getId() + " computed Fibo(" + n + ") = " + result);
            } finally {
                mutex.unlock();
                System.out.println("Thread " + Thread.currentThread().getId() + " exited critical section");
            }
        }
    }
    public int getResult() {
        return result;
    }

    public static void main(String[] args) {
        Join_Fibo fibo = new Join_Fibo(Integer.parseInt(args[0]));
        fibo.start();
        try {
            fibo.join();
        } catch (InterruptedException e) {};
        System.out.println("Answer is: " + fibo.getResult());
    }
}