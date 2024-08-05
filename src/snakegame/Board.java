
package snakegame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Board extends JPanel implements ActionListener{
    private Image apple;
    private Image dot;
    private Image head;
    
    private final int ALL_DOTS=900;//area
    private final int DOT_SIZE=10;
    private final int RANDOM_POSITION=29;
    
    
    private int apple_x;
    private int apple_y;
    
    private final int x[]=new int[ALL_DOTS];
    private final int y[]=new int[ALL_DOTS];
    
    private boolean leftDirection=false;
    private boolean rightDirection=true;//initially it moves in the right direction only
    private boolean upDirection=false;
    private boolean downDirection=false;
    
    private boolean inGame=true;
    private int dots;//globally declaring it
    private Timer timer;
    Board(){
        
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300,300));
        setFocusable(true);
          
        loadImages();
        initGame();//initializing the game
    }
    
    public void loadImages(){
           ImageIcon  i1=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
           apple=i1.getImage();
           
            ImageIcon  i2=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
            dot=i2.getImage();
             ImageIcon  i3=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
             head=i3.getImage();
    }
        public void initGame(){
            dots=3;//
            
              for(int i=0;i<dots;i++){
             y[i]=50;
             x[i]=50-i*DOT_SIZE;//dots come as 50,40 and 30
         }
              locateApple();
              
              timer=new Timer(140,this);//140miliseconds calls actionperform
              timer.start();
        } 
        
        public void locateApple(){
           int r=(int)(Math.random()*RANDOM_POSITION);//Math.random fn is  used to get random posn
           apple_x=r*DOT_SIZE;
           r=(int)(Math.random()*RANDOM_POSITION);//Math.random fn is  used to get random posn
           apple_y=r*DOT_SIZE;
           
           
        }
         public void paintComponent(Graphics g){
             super.paintComponent(g);
             
            draw(g);
         }
         public void draw(Graphics g){
             if(inGame){
             g.drawImage(apple,apple_x,apple_y,this);
             for(int i=0;i<dots;i++){
                 if(i==0){
                     g.drawImage(head,x[i],y[i],this);
                 }
                 else{
                     g.drawImage(dot,x[i],y[i],this);
                 }
             }
             Toolkit.getDefaultToolkit().sync();
             }
             else{
                 gameOver(g);
             }
             
         }
        
         public void gameOver(Graphics g){
             String msg="Game over!";
             Font font=new Font("AB NABI",Font.BOLD,14);
             FontMetrics metrices=getFontMetrics(font);
             g.setColor(Color.WHITE);
             g.setFont(font);
             g.drawString(msg,(300-metrices.stringWidth(msg))/2,300/2);
           
         } 
         public void move(){
             for(int i=dots;i>0;i--){//head has to be first and dots will follow that
                 x[i]=x[i-1]; 
                 y[i]=y[i-1];
             }
             
             if(leftDirection){
                 x[0]=x[0]-DOT_SIZE;//as we are moving towards the origin of x_axis decreasing its size
             }
              if(rightDirection){
                 x[0]=x[0]+DOT_SIZE;//as we are going towards right its size will icrease
             }
               if(upDirection){
                 y[0]=y[0]-DOT_SIZE;
             }
                if(downDirection){
                 y[0]=y[0]+DOT_SIZE;
             }
         }
         
         public void checkApple(){
             if((x[0]==apple_x)&&(y[0]==apple_y)){
                 dots++;
                 locateApple();
             }
         }
         
         public void checkCollision(){
             for(int i=dots;i>0;i--){//checking whether the snake is striked with itsef 
                 if((i>4) && (x[0]==x[i]) && (y[0]==y[i])){
                     inGame=false;
                 }
             }
             
             if(y[0]>=300){
                 inGame=false;
             }
             
              if(x[0]>=300){
                 inGame=false;
             }
               
                if(y[0]<0){
                 inGame=false;
             }
                
                 if(x[0]<0){
                 inGame=false;
             }
                 
                 if(!inGame){
                     timer.stop();
                 }
         }
         public void actionPerformed(ActionEvent ae){
             //method override
             if(inGame){
             checkApple();
             checkCollision();
             move();
             }
             repaint();//refresh the screen
         }
         
         public class TAdapter extends KeyAdapter{//inner class to handle the keys
             @Override
             public void keyPressed(KeyEvent e){//Want this fn  for handling snake in different dirns by using keypad
                 int key=e.getKeyCode();
                 
                 if(key==KeyEvent.VK_LEFT&&(!rightDirection)){
                     leftDirection=true;
                     upDirection=false;
                      downDirection=false;
                 }
                 
                  if(key==KeyEvent.VK_RIGHT&&(!leftDirection)){
                     rightDirection=true;
                     upDirection=false;
                      downDirection=false;
                 }
                  
                   if(key==KeyEvent.VK_UP&&(!downDirection)){
                     upDirection=true;
                     leftDirection=false;
                      rightDirection=false;
                 }
                   
                    if(key==KeyEvent.VK_DOWN&&(!upDirection)){
                     downDirection=true;
                     leftDirection=false;
                      rightDirection=false;
                 }
             }
         }
}

