import java.awt.*;

public class removeadd extends Thread{
    static int alpha=0;
    static String str="";
    public removeadd(String str)
    {
        Rollcall.temp=1;
        removeadd.str =str;
        Rollcall.removeLabel.setText("已从集合中移除"+ removeadd.str);
//        new removedelete(removeadd.str).start();
    }
    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                if (alpha<255)
                    Rollcall.removeLabel.setForeground(new Color(255,0,0,alpha++));
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
