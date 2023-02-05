import com.sun.java.swing.plaf.windows.WindowsBorders;
import com.sun.java.swing.plaf.windows.WindowsOptionPaneUI;
import com.sun.java.swing.plaf.windows.WindowsProgressBarUI;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Rollcall extends JFrame {
    /**
     * @author sunzhenhuan
     * @param //点名
     */
    static int red=150,green1=100,blue;//总人数到多少变成什么颜色
    static String s="黑名单人员:";
    static JLabel time=new JLabel();
    static String blackname="";
    static String black="";
    static int blakAlpha=0;
    static JLabel blacklist=new JLabel();
    static Color color=new Color(0,255,0);
    static Thread thread;
    static int a=210;//总人数的每次减多少
    static boolean countPanelflag=false;//总人数的进度条标志
    static int wid=210;//总人数的值
    static int contentalpha=100;
    static int startalpha=100;
    static int startflag=1;
    static boolean threadflag=false;
    static JLabel sum=new JLabel();
    static boolean tmp=false;//一开始的字符串标志
    static int sumcount=0;
    static int blackflag=0;
    static int alphaflag=1;
    static int flag1=1;
    static int blackSize=35;
    static int blackwid=350;
    static int blackhei=60;
    static String timezone="";
    static String fontPath="font//ZKTKuangSKSJW.TTF";
    static int width=Toolkit.getDefaultToolkit().getScreenSize().width-300;
    static int height=Toolkit.getDefaultToolkit().getScreenSize().height-200;
    Color[]c=new Color[]{Color.RED,Color.BLUE};
    final JLabel esc=new JLabel()
    {
        //画按钮
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g1=(Graphics2D) g;
            g1.setColor(c[0]);
            g1.fillOval(0,0,50,50);
            g1.setFont(new Font("",Font.PLAIN,40));
            g1.setColor(Color.black);
            g1.drawString("X",12,40);
        }
    };
    JLabel small=new JLabel()
    {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(c[1]);
            g.fillOval(0,0,50,50);
            g.setColor(Color.black);
            g.setFont(new Font(fontPath,Font.BOLD,40));
            g.drawString("——",5,38);
        }
    };
    final JButton start=new JButton("开始抽人");
    static Image image=Toolkit.getDefaultToolkit().createImage("images//背景1.png").getScaledInstance(width,height,0);
    Point point=new Point();
    static Color[]colors=new Color[]{Color.RED,Color.BLUE,Color.CYAN,Color.green,Color.WHITE,Color.magenta};//颜色数组
    static Vector<String>vector=new Vector<>();//存储人名
    static String[]strings={"唐泽心","齐杰","尹许寿","刘子龙"};
    static int count=0;//记录人的个数
    static int mill=10000+(int)(Math.random()*2000);//至少7秒到9秒抽奖
    static int stop=new Random().nextInt(1201);//随机生成停止的0~1200时间规则
    static String str="";//记录抽中的人
    static int temp=0;
    JLabel countLabel=new JLabel();//人数标签
    static JLabel removeLabel=new JLabel();
    static float Opacity=0.0f;
    static JLabel background=new JLabel(new ImageIcon(image));//背景
    static JPanel content=new JPanel()
    {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g1=(Graphics2D) g;
            g1.setColor(new Color(0,0,0,contentalpha));
            g1.fillRoundRect(0,0,700,300,30,30);
            g1.setColor(colors[new Random().nextInt(colors.length)]);
            g1.setFont(new Font(fontPath,Font.PLAIN,70));
            if (tmp)
            {
                str=vector.get(new Random().nextInt(vector.size()));
                g1.drawString("抽到了"+str,120,180);
//              startMusic.playMusic("video//"+s +".wav", 0, 4.0f);
            }
            else
                g1.drawString("还未开始",120,180);
        }
    };//主体面板
    int green=100;
    int flag=1;
    File file=new File("Name//二班.txt");//2班人名路径
    File file1=new File("Name//一班.txt");//一班人名路径
    File[]files=new File[]{file,file1};//文件数组存储一班二班文件
    String str1="";//记录文件里面内容是否为空
    //内部类
    public static class ThreadClass{
        public void GetThread()
        {
                thread = new Thread(() ->
                {
                    while (true) {
                        try {
                            if (mill > stop) {//如果小于暂停时间
                                stop += 50;
                                if (contentalpha<200&&alphaflag==1) {
                                    content.setBackground(new Color(0, 0, 0, contentalpha +=5));//透明度
                                    if (contentalpha>=199)
                                        alphaflag=0;
                                }
                                else {
                                    content.setBackground(new Color(0, 0, 0, contentalpha-=5));
                                    if (contentalpha<=100)
                                        alphaflag=1;
                                }
                            } else {//否则
                                switch (str)
                                {
                                    case "唐泽心":
                                    case "齐杰":
                                    case "尹许寿":
                                    case "刘子龙":startMusic.playMusic("video/当兵.wav",0,4.0f);break;
                                    default:startMusic.playMusic("video/"+str+".wav",0,4.0f);break;
                                }
                                startflag = 1;//等到了就为可以再点击
                                switch(str)
                                {
                                    case "廖志翔":vector.removeAll(Collections.singleton("廖志翔"));break;//移除重复的数据不精确，应用set集合
                                    case "夏霖通":vector.removeAll(Collections.singleton("夏霖通"));break;//移除重复的数据不精确，应用set集合
                                }
                                vector.remove(str);//抽到了这个人则移除
                                if (temp==1) {//当加深的线程结束后才开始
                                    new removedelete(str).start();
                                }
                                else new removeadd(str).start();//传一个str做线程
                                sumcount++;
                                contentalpha=100;
                                sum.setForeground(colors[new Random().nextInt(colors.length)]);
                                a-=5;
                                sunlow+=5;
                                new ThreadClass().Pan();//与下面一样
                                new ThreadClass().sumPanel();//停了之后调用此方法
                                blackname=str;//赋给黑名单人名
                                black="将"+blackname+"加入黑名单";
                                new blacklistAlpha(blakAlpha).start();//传给颜色线程执行
                                blackflag=1;//当抽到人了才能点击
                                blacklist.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                blacklist.setToolTipText("加入黑名单");
                                break;
                            }
                            countPanel.repaint();
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
        }
        public  void Pan()
        {
            Thread thread=new Thread(()->{
                while (countPanelflag)
                {
                    if (wid>=a) {//280>275
                        wid--;
                        if (wid<160&&wid>120)
                        {
                            if (red<255||green1<200) {
                                red=200;
                                green1=100;
                                color = new Color(red += 3, green1 += 3, blue);
                            }
                        }
                        else if (wid>0&&wid<=120)
                        {
                            if (red<255) {
                                green1=0;
                                red=200;
                                color = new Color(red + 5, green1, blue);
                            }
                        }
                    }
                    else break;
                    countPanel.repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
        public void sumPanel()
        {
            Thread thread=new Thread(()->{
                while (true)
                {
                    if (sumPanelwid<sunlow)//每次多加了一个人就会在停之后减五
                    {
                        sumPanelwid++;
                        if (sumPanelwid<100&&sumPanelwid>=0) {
                            if (sumred<255) {
                                sumred=150;
                                sumcolor = new Color(sumred++,sumgreen,sumblue);
                            }
                        }
                        else if (sumPanelwid>=100&&sumPanelwid<180)
                        {
                            if (sumgreen<200)
                            {
                                sumgreen=100;
                                sumcolor=new Color(sumred,sumgreen++,sumblue);
                            }
                        }
                        else if (sumPanelwid>=180&sumPanelwid<255)
                        {
                            sumred=0;
                            sumcolor=new Color(sumred,sumgreen++,sumblue);
                        }

                    }
                    else break;
                    sumPanel.repaint();//重画
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }
    static JPanel countPanel=new JPanel()
    {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(color);
            g.fillRect(0,0,wid,50);
            g.setColor(Color.BLUE);
            g.setFont(new Font(fontPath,Font.PLAIN,25));
            g.drawString("一二班总人数",50,35);
        }
    };
    static boolean sumPanelflag=false;
    static int sumPanelwid=0;
    static int sumred,sumgreen,sumblue;
    static Color sumcolor=new Color(sumred,sumgreen,sumblue);
    static int sunlow=0;//用于每次加多少
    static JPanel sumPanel=new JPanel()
    {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(sumcolor);
            g.fillRect(0,0,sumPanelwid,50);
            g.setColor(Color.CYAN);
            g.drawString("已抽取"+sumcount+"人",80,25);
        }
    };
    public Rollcall()
    {
        System.err.println(width+"\t"+height);
        //批量操作读取文件内容
        for (File value : files) {
            //文件读取
            try {
                FileInputStream fis=new FileInputStream(value);//把文件转换成字节
//                FileReader reader = new FileReader(value);//文件字符读取
                BufferedReader br = new BufferedReader(new InputStreamReader(fis,StandardCharsets.UTF_8));//先把字节输入流转换成字符输入流再转换为缓冲字符读取
                while (true) {
                    try {
                        if ((str1 = br.readLine()) == null) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    vector.add(str1);//否则把全员赋给vector集合存储
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        vector.remove("孙振寰");
        vector.remove("彭佳兴");
        count=vector.size()-1;//取得人数
       // 一直监控集合里面的成员是否移除
//        Thread thread1=new Thread(()->
//        {
//            while (true)
//            {
//                try {
//                    Thread.sleep(2000);//每两秒监控一次
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.err.println(vector);
//            }
//        });
//        thread1.start();
        setSize(width,height);//窗口大小
        setResizable(false);//不可改变大小
        setUndecorated(true);//去除修饰
        setLayout(null);
        setIconImage(new ImageIcon("images//背景1.png").getImage());
        setOpacity(Opacity);
        setLocationRelativeTo(null);//居中
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                Thread thread=new Thread(()->
                {
                    while (true)
                    {
                        if (Opacity<=(float) 0.9)
                            setOpacity(Opacity+=(float)0.1);
                        else break;
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        });
        //黑名单
        blacklist.setSize(blackwid,blackhei);
        blacklist.setLocation(640,669);
        blacklist.setHorizontalAlignment(SwingConstants.CENTER);
        blacklist.setFont(new Font(fontPath,Font.PLAIN,blackSize));
        background.add(blacklist);
        //一直监控要加入黑名单人的姓名
        Thread t=new Thread(()->{
           while (true)
           {
               blacklist.setText(black);//一开始是空的直到抽到人了就会不为空
           }
        });
        t.start();
        blacklist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton()==MouseEvent.BUTTON1)
                {
                    if (blackflag==1) {//等于一才能执行
                        File blackDirectory = new File(".//blackDirectory");//当前目录下创建一个黑名单目录
                        if (!blackDirectory.isDirectory())
                        {
                            blackDirectory.mkdirs();
                            System.err.println("创建目录成功！目录名为"+blackDirectory.getName());
                        }
                        File blackFile=new File("blackDirectory//blacklist.txt");
                        if (!blackFile.exists())//文件不存在的话
                        {
                            try {
                                blackFile.createNewFile();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        try {
                            String Line="\n";
                            s+=blackname;
                            int op=JOptionPane.showConfirmDialog(null,"确定要把"+blackname+"加入到黑名单吗？","黑名单人员",JOptionPane.OK_CANCEL_OPTION);
                            if (op==JOptionPane.OK_OPTION)
                            {
                                OutputStream os=new FileOutputStream(blackFile,true);
                                os.write(s.getBytes(StandardCharsets.UTF_8));
                                os.write(Line.getBytes());
                                JOptionPane.showMessageDialog(null,"向黑名单目录添加成功！");
                                s="黑名单人员:";//防止发生追加姓名
                                blackflag=0;//追加成功后就不能点了
                                blacklist.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                os.flush();
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
        });
        //时间
        time.setSize(410,50);
        time.setForeground(Color.PINK);
        Thread thread=new Thread(()->{
            while (true)
            {
                int a=new Date().getHours();
                if (a>=0&&a<12) {
                    timezone="上午好";
                    time.setText(timezone+ new Date().toLocaleString());
                }
                else if (a == 12) {
                    timezone="中午好";
                    time.setText(timezone+ new Date().toLocaleString());
                }
                else if (a>=13&&a<=17) {
                    timezone="下午好";
                    time.setText(timezone+ new Date().toLocaleString());
                }
                else if (a>17&&a<=23) {
                    timezone="晚上好";
                    time.setText(timezone+ new Date().toLocaleString());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        int a=new Date().getHours();
        if (a>=0&&a<12)startMusic.playMusic("timeVideo/上午好.wav",0,4.0f);
        else if (a == 12)startMusic.playMusic("timeVideo/中午好.wav",0,4.0f);
        else if (a>=13&&a<=17)startMusic.playMusic("timeVideo/下午好.wav",0,4.0f);
        else if (a>17&&a<=23)startMusic.playMusic("timeVideo/晚上好.wav",0,4.0f);
//        time.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        time.setFont(new Font(fontPath,Font.PLAIN,30));
        time.setHorizontalAlignment(SwingConstants.CENTER);
        time.setLocation(10,0);
        //背景
        background.setSize(getSize());
        background.setLocation(0,0);
        //进度条面板
        countPanel.setSize(wid,50);
        countPanel.setLocation(80,getHeight()-countPanel.getHeight()-20);
        countPanel.setOpaque(false);
        countPanel.setLayout(null);
        countPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.ORANGE,Color.red));
        //累计个数
        //累计抽过的人
        sum.setHorizontalAlignment(SwingConstants.CENTER);
        sum.setSize(430,50);
        sum.setFont(new Font(fontPath,Font.PLAIN,50));
        sum.setForeground(Color.orange);
//        sum.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        sum.setLocation(10,getHeight()/2-sum.getHeight()+200);
        //已抽取进度条面板
        sumPanel.setSize(210,50);//注意一定要改宽度不然要减到之前的值才会看到效果
        sumPanel.setLocation(80,getHeight()/2+sum.getHeight()+sumPanel.getHeight()+120);
        sumPanel.setOpaque(false);
        sumPanel.setLayout(null);
        sumPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.ORANGE,Color.red));
        //移除人的标签
        removeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        removeLabel.setSize(560,100);
        removeLabel.setFont(new Font(fontPath,Font.PLAIN,50));
//        removeLabel.setBorder(BorderFactory.createLineBorder(Color.red));
        removeLabel.setLocation(getWidth()/2-removeLabel.getWidth()/2,0);
        
        
        //总人数标签
        Thread threadText=new Thread(()->{
           while (true)
           {
               sum.setText("已经累计抽取"+sumcount+"人");
           }
        });//一直监控总人数变化
        threadText.start();
        //主体面板
        content.setSize(700,300);
        content.setLayout(null);
        content.setOpaque(false);
        content.setLocation(width/2-content.getWidth()/2,height/2-content.getHeight()/2);
//        content.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        content.setBackground(new Color(0,0,0,0));//透明度
        //esc标签
        esc.setSize(50,50);
        esc.setToolTipText("关闭");
        esc.setLocation(getWidth()-esc.getWidth(),0);
        esc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton()==MouseEvent.BUTTON1)
                {
                    int op=JOptionPane.showConfirmDialog(null,"确定要退出吗？","退出",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if (op==JOptionPane.YES_OPTION)
                        System.exit(0);//退出
                }
            }
        });
        esc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                c[0]=new Color(0,255,0).darker();
                esc.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                c[0]=new Color(255,0,0).brighter();
                esc.repaint();
            }
        });
        //缩小按钮
        small.setSize(50,50);
        small.setToolTipText("缩小");
        small.setLocation(getWidth()-small.getWidth()-esc.getWidth()-10,0);
        small.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton()==MouseEvent.BUTTON1)
                {
                    setExtendedState(ICONIFIED);
                }
            }
        });
        small.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                c[1]=new Color(200,200,0).darker();
                small.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                c[1]=new Color(0,0,255).brighter();
                small.repaint();
            }
        });
        //开始按钮
        start.setSize(400,80);
        start.setFocusPainted(false);
        start.setToolTipText("点击开始");
        start.setForeground(new Color(0,0,0,100));
        start.setFont(new Font(fontPath,Font.PLAIN,70));
        start.setBackground(new Color(0,green,0));
        start.setLocation(getSize().width/2-start.getSize().width/2,getSize().height-start.getHeight()-20);
        start.setBorder(BorderFactory.createEtchedBorder());

        //添加到窗体内
        background.add(small);
        background.add(sumPanel);
        background.add(sum);
        background.add(removeLabel);
        background.add(content);
        background.add(start);
        background.add(esc);
        background.add(countPanel);
        background.add(time);
        add(background);
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                flag=0;
                //颜色加深
                Thread thread=new Thread(() -> {
                    while (true)
                    {
                        try {
                            if (green<255&&flag==0) {
                                start.setBackground(new Color(0, green++, 0));
                            }
                            else   break;
                            Thread.sleep(5);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                });
                thread.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                flag=1;
                //颜色减淡
                Thread thread2=new Thread(() -> {
                    while (true)
                    {
                        try {
                            if (green>100&&flag==1) {
                                start.setBackground(new Color(0, green--, 0));
                            }
                            else break;
                            Thread.sleep(5);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                });
                thread2.start();
            }});
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                //获取按下那一刻的坐标
                    point.y = e.getY();
                    point.x = e.getX();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                    Point newpoint = getLocation();
                    setLocation(newpoint.x + e.getX() - point.x, newpoint.y + e.getY() - point.y);
            }
        });
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton()==MouseEvent.BUTTON1) {
                    if (startflag==1) {//startflag为1才可以点
                        startflag=0;
                        tmp=true;
                        countPanelflag=true;//点击时开始
                        sumPanelflag=true;//点击时开始
                        new ThreadClass().GetThread();//调用内部类的方法
                        mill=4000+(int)(Math.random()*2000);//点了之后mill重新赋值以便更好的抽
                        stop=new Random().nextInt(1201);
                        blackflag=0;
                        blacklist.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        blacklist.setToolTipText("");
                        new blacklist().start();
                    }
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.err.println(e.getX()+"\t"+e.getY());
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new Rollcall();
    }
}
