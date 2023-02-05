import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class startMusic {
    static Clip clip;
    static AudioInputStream audioInput;
    static FloatControl floatControl;

    public static void playMusic(String musicLocation, int loop, float Value) {
        try {
            File musicPath = new File(musicLocation);
                audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                floatControl.setValue(Value);//最大6.026
                clip.start();
                clip.loop(loop);//loop等于0播放一次1播放两次，-1循环
                System.gc();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
