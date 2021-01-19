public class Animal implements Runnable {
    public static boolean winner = false;
    private String name;
    private int position = 0;
    private int speed;
    private int restMax;   
    public static Food food = new Food();  
    // a int type variable to call the eat method from Food class every "nth" cycle
    public int eatingTime; 
    //constructor that takes among other parameters, a Food type object
    public Animal(String nameAnimal, int speedAnimal, int restMaxAnimal, Food foodForRunners, 
    int howLongItEats) {
        this.name = nameAnimal;
        this.speed = speedAnimal;
        this.restMax = restMaxAnimal;
        this.eatingTime = howLongItEats;        
        
    }
    @Override
    public void run(){
        String winnerString = "";  
        int eatingControl = 1;  
    // the loop will run as long as the winner boolean is false and the program will stop when
    // the position variable reaches 100 in any of the threads       
        while(Animal.winner == false){
            if(this.position < 100){
                               
                try {                    
                    this.position += this.speed;                     
                    Thread.sleep((long)(getRandomNumber() * this.restMax * 65));
                     
                    if((eatingControl % 4) == 0){
                        try {
                        food.eat(this.name, this.eatingTime);   
                        }catch(Exception e)  {
                            System.out.println("Race interrupted");
                        }
                    }
                           
                    System.out.println(this.name + " is running, and its position is now: " 
                    + this.position);   
                    eatingControl++;                                              
                                   
                } catch (Exception e) {
                    //TODO: handle exception
                    System.out.println("Race interrupted");
                }  
                        
            }else{
            Animal.winner = true;                                              
            if(this.position >= 100){
                 winnerString = this.name;                 
            }  
            System.out.println("The winner of the race is: " + winnerString);
            System.exit(0);     
        }
        }                       
       
    }
    public static void main(String[] args) {
        Animal rabbitRunnable = new Animal("rabbit", 5, 150, food, 5000);
        Thread rabbit = new Thread(rabbitRunnable);
        Animal turtleRunnable = new Animal("turtle", 3, 100, food, 1500);
        Thread turtle = new Thread(turtleRunnable);

        turtle.start();       
        rabbit.start();
        
    }   
 // A method to generate random numbers, including "1"   
    public double getRandomNumber(){
        double randomNumber = (double)(Math.random() + 1) ;
        if(randomNumber == (double)(1)){
            return 1;
        } else
        return (randomNumber - 1);

    }

}
// When I changed the eat method in Food class to not synchronized, the Animal objects were able to 
// eat at the same time