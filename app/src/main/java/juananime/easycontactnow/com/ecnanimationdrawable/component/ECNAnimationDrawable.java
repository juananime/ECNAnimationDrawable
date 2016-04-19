package juananime.easycontactnow.com.ecnanimationdrawable.component;

import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;

/**
 * Created by jimenez on 18/04/2016.
 */
public class ECNAnimationDrawable extends AnimationDrawable {

    private volatile int duration;//its volatile because another thread will update its value
    private int currentFrame;
    private ECNAnimationListener listener;

    public ECNAnimationDrawable() {
        currentFrame = 0;
    }

    @Override
    public void start() {
        super.start();
        if(listener!=null)
            listener.ECNAnimationStarted();
    }

    @Override
    public void stop() {
        super.stop();
        if(listener!=null)
         listener.ECNAnimationStopped();
    }

    @Override
    public void run() {

        int n = getNumberOfFrames();
        currentFrame++;
        if (currentFrame >= n) {
            currentFrame = 0;
            if(listener!=null)
                listener.ECNAnimationLooped();
        }

        selectDrawable(currentFrame);
        scheduleSelf(this, SystemClock.uptimeMillis() + duration);
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
        //we have to do the following or the next frame will be displayed after the old duration
        unscheduleSelf(this);
        selectDrawable(currentFrame);
        scheduleSelf(this, SystemClock.uptimeMillis()+duration);
    }

    public void setTotalDuration(int duration){

        this.duration=duration/getNumberOfFrames();
        unscheduleSelf(this);
        selectDrawable(currentFrame);
        scheduleSelf(this, SystemClock.uptimeMillis()+(duration/getNumberOfFrames()));

    }

    public void resetAnimation(){
        currentFrame = 0;
        selectDrawable(currentFrame);
        scheduleSelf(this, SystemClock.uptimeMillis() + duration);
    }


    public void setListener(ECNAnimationListener listener) {
        this.listener = listener;
    }
}