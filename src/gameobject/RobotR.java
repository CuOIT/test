package gameobject;

import state.GameWorldState;
import effect.Animation;
import effect.AnimationHandler;
import effect.CacheDataLoader;
import static gameobject.ParticularObject.LEFT_DIR;
import static gameobject.ParticularObject.NOBEHURT;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class RobotR extends ParticularObject {

    private long startTimeToShoot;
    private float x1, x2, y1, y2;
    
    private AudioClip shooting;
    
    public RobotR(float x, float y, GameWorldState gameWorld) {
        super(x, y, 127, 89,"robotR", 0, 100, gameWorld);
        startTimeToShoot = 0;
        setTimeForNoBehurt(300000000);
        setDamage(10);
        
        x1 = x - 100;
        x2 = x + 100;
        y1 = y - 50;
        y2 = y + 50;
        
        setSpeedX(1);
        setSpeedY(1);
        
        shooting = CacheDataLoader.getInstance().getSound("robotRshooting");
    }

    @Override
    public void attack() {  
        
        shooting.play();
        Bullet bullet = new RobotRBullet(getPosX(), getPosY(), getGameWorld());
        
        if(getDirection()==LEFT_DIR)
            bullet.setSpeedX(5);
        else bullet.setSpeedX(-5);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);

    }

    
    public void Update(){
        super.Update();
        
        if(getPosX() - getGameWorld().megaMan.getPosX() > 0) setDirection(ParticularObject.RIGHT_DIR);
        else setDirection(ParticularObject.LEFT_DIR);
        
        if(getPosX() < x1)
            setSpeedX(1);
        else if(getPosX() > x2)
            setSpeedX(-1);
        setPosX(getPosX() + getSpeedX());
        
        if(getPosY() < y1)
            setSpeedY(1);
        else if(getPosY() > y2)
            setSpeedY(-1);
        setPosY(getPosY() + getSpeedY());
        
        if(System.nanoTime() - startTimeToShoot > 1000*10000000*1.5){
            attack();
            startTimeToShoot = System.nanoTime();
        }
    }
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();
        rect.x += 20;
        rect.width -= 40;
        
        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {
    	
        if(!isObjectOutOfCameraView()){
            if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1){
                // plash...
            }else{
            	g2.setColor(Color.BLACK);
            	g2.fillRect((int)(getPosX() - getGameWorld().camera.getPosX()-51), 
                        (int)(getPosY() - getGameWorld().camera.getPosY()-61),102,10+2);
            	g2.setColor(Color.YELLOW);
            	g2.fillRect((int)(getPosX() - getGameWorld().camera.getPosX()-50), 
                        (int)(getPosY() - getGameWorld().camera.getPosY()-60),(int)getBlood(),10);
                if(getDirection() == LEFT_DIR){
                    animationH.backAnim.Update(System.nanoTime());
                    animationH.backAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }else{
                    animationH.forwardAnim.Update(System.nanoTime());
                    animationH.forwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }
            }
        }
    }
    
}
