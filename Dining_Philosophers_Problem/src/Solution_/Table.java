package Solution_;

public class Table {

    int nbrOfChopsticks;
    private boolean chopstick[]; //true if chopstick[i] is available

    public Table(int nbrOfSticks){
        nbrOfChopsticks=nbrOfSticks;
        chopstick=new boolean[nbrOfChopsticks];
        for(int i=0;i<nbrOfChopsticks;i++){
            chopstick[i]=true;
        }
    }

    public synchronized void getLeft(int n){
        //philosopher n picks up its left chopstick
        while (!chopstick[n]) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        chopstick[n] = false;

    }

    public synchronized void getRight(int n){
        //philosopher n picks up its right chopstick
        int pos=n+1;
        if(pos==nbrOfChopsticks)
            pos=0;
        while (!chopstick[pos]){
            try {
                releaseLeft(n);
                System.out.println("Philosopher " + n + " drop left");
                wait();
                getLeft(n);
                System.out.println("Philosopher " + n + " pick up left");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        chopstick[pos]=false;
    }

    public synchronized void releaseLeft(int n){
        //philosopher n puts down its left chopstick
        chopstick[n]=true;
    }

    public synchronized void releaseRight(int n){
        //philosopher n puts down its right chopstick
        int pos=n+1;
        if(pos==nbrOfChopsticks)
            pos=0;
        chopstick[pos]=true;
        notifyAll();
    }
}
