import java.awt.*;

public class blacklist extends Thread{
    int alpha=255;
    public blacklist()
    {

    }

    @Override
    public void run() {
        super.run();
        while (true)
        {
            if (alpha>0)
                Rollcall.blacklist.setForeground(new Color(255,0,0,alpha--));
            else break;
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
