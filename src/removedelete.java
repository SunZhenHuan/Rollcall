import java.awt.*;

public class removedelete extends Thread{
    int alpha=255;
    public removedelete(String str)
    {
        Rollcall.removeLabel.setText("已从集合中移除"+str);
    }
    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                if (alpha>0)
                    Rollcall.removeLabel.setForeground(new Color(255,0,0,alpha--));
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
