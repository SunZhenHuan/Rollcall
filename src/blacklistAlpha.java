import java.awt.*;

public class blacklistAlpha extends Thread{
    int alpha;
    public blacklistAlpha(int alpha)
    {
        this.alpha=alpha;
    }

    @Override
    public void run() {
        super.run();
        while (true)
        {
            if (alpha<255)
                Rollcall.blacklist.setForeground(new Color(255,0,0,alpha++));
            else break;
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
