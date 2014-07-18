/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmt;

/**
 *
 * @author atul
 */
public class Time {

    private int hours;
    private int minutes;
    private int seconds;
    private boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

     public void incrementBy(int seconds){
         this.seconds = this.seconds + seconds;
        if(this.seconds >= 60){
           int incrementvalue= this.seconds - seconds;
           this.seconds= 0;
           this.seconds = incrementvalue;
           this.minutes = this.minutes + 1;
           if(this.minutes >=60){
            this.minutes = 0;
           this.hours= this.hours + 1;
           }
       
    }
    }
    public void decrementBy(int seconds){
       int tseconds = (this.hours) * 3600 + (this.minutes) * 60 + this.seconds;
       tseconds = tseconds - seconds ;
       this.hours = tseconds / 3600;
       int tminutes= tseconds % 3600 ;
       this.minutes =tminutes / 60;
       int tfinalsec = tminutes % 60;
       this.seconds = tfinalsec ;



    }

    public void increment() {
     if(isEnabled()){
        incrementSeconds();
     }
    }

    private void incrementSeconds() {

        if (this.seconds == 59) {
            this.seconds = 0;
            incrementMinutes();
        } else {
            this.seconds++;
        }
    }

    private void incrementMinutes() {
        if (this.minutes == 59) {
            this.minutes = 0;
            incrementHours();
        } else {
            this.minutes++;
        }
    }

    private void incrementHours() {
        this.hours++;
    }
   

    public void reset() {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
    }

    public void resume() {
        this.hours = this.getHours();
        this.minutes = this.getMinutes();
        this.seconds = this.getSeconds();

    }
    

    public String toString() {
        String hours = (this.hours < 10) ? "0" + this.hours : "" + this.hours;
        String minutes = (this.minutes < 10) ? "0" + this.minutes : "" + this.minutes;
        String seconds = (this.seconds < 10) ? "0" + this.seconds : "" + this.seconds;
        return hours + ":" + minutes + ":" + seconds;
    }
}
