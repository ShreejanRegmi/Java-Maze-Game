/*
Program Name: Assignment 2: Application - Ball Maze
Author: Shreejan Regmi (18417132)
Filename: CBallMaze.java
Module: CSY1020 Problem Solving and Programming 
Tutor: Mr. Kumar Lamichhane 
Date: 9th July, 2018.
  */


import java.awt.*;        //enables access to java.awt.*

import javax.sound.sampled.*;  //imports java file javax.sound.sampled.* 
import javax.swing.*;	  //enables access to javax.swing.*
import javax.swing.event.ChangeEvent; //imports java file javax.swing.ChangeEvent.*
import javax.swing.event.ChangeListener; //imports java file javax.swing.ChangeListener.*

import java.awt.event.*;  //enables access to java.awt.event.*
import java.io.IOException; //IOException file is imported for try catch

public class CBallMaze extends JFrame implements ActionListener, KeyListener, ChangeListener{ //creation of class CBallMaze and provides access to key control, slider and button listener
	
	private JPanel panelLeft, panelRight, panelRight1, panelRight2, panelRight3, panelRight4, panelBottom; //creation of JPanels
	private JLabel jLOption, jLSquare, jLDirection, jLTimer, jLColon1, jLColon2; //creation of jlabels
	private JTextField jTOption, jTSquare, jTDirection,jTimer1,jTimer2,jTimer3; //creation of text fields
	private JMenuBar menuBar;  //creation of menu bar
	private JMenu jMScenario, jMEdit,jMControl, jMHelp;  //menus for menu bar
	private JMenuItem jIExit, jINewC, jIAct,jIRun, jIAbout, jIHelp; //making of jmenuitems
	private JButton jBUp, jBDown, jBLeft, jBRight, jBBlank1, jBBlank2, jBBlank3, jBBlank4, jBBlank5, // JButtons created
			jBOption1, jBOption2, jBOption3, jBExit, jBAct, jBRun, jBReset, jBCompass, jBPause;      //
	private ImageIcon iCompassN,iCompassS,iCompassW,iCompassE, iSand, iWhite, iGBall, iMushroom, iSnake, iEnd, iGoal, iPause; //ImageIcon declarations for using images (icons) 
	private JSlider slider;  //declaration of slider
	private int xPos=15, yPos=0, seconds=0;
	private JLabel jLSand[][];  //jlabel array jLSand for creation of maze
	private Timer fallTimer,timer2, digitalTimer, snakeTimer; //timers
	private boolean fallDownBool=false;
	GridBagConstraints gBCon= new GridBagConstraints();  //declaration and initialization of gridbag constraints
	private int pressedBtn, sliderValue;  //sliderValue to get the value of slider and pressedBtn to get the keycode

	/*
	  Main method created which contains object of class CBallMaze
	  that acts as a frame and configurations of basic frame features	
	*/
	
	public static void main(String args[]) {   
		CBallMaze frame= new CBallMaze();        //creation of object of CBallMaze class
		frame.setTitle("CBallMaze - Ball Maze Application");  //title set of JFrame
		frame.setSize(775, 650);  //sets size of frame
		frame.createGUI();  //createGUI method called which contains code for the creation of GUI
		frame.setVisible(true); //makes the frame visible
	}

	public void createGUI() {//method for the creation of GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //the program terminates when jframe is closed
 		setResizable(false);  //non-resizable jframe
		setLocationRelativeTo(null);    //sets the position of jframe to center
		
		addKeyListener(this); //key listener for key presses
		setFocusable(true);  //the maze is focusable with this code for key press events
		
		setIconImage((Toolkit.getDefaultToolkit().getImage(getClass().getResource("greenfoot.png"))));  //gets the icon of greenfoot.png and sets it to the frame
		
		Container background= getContentPane();  //background acts as a content pane for GUI
		background.setLayout(null);  //the layout is set to null for the content pane
		
		///////  MenuBar  /////
		
		menuBar=new JMenuBar();  //creation of menubar 
		jMScenario= new JMenu("Scenario");      //Addition 
		jMEdit= new JMenu("Edit");				//of
		jMControl= new JMenu("Controls");		//menus 
		jMHelp= new JMenu("Help");				//scenario, edit, controls and help		 
		jIExit= new JMenuItem("Exit");			//adds menu items Exit
		jINewC= new JMenuItem("New Class");		//New Class
		jIAct= new JMenuItem("Act");			//Act
		jIRun= new JMenuItem("Run");			//Run
		jIAbout= new JMenuItem("About");		//About 
		jIAbout.addActionListener(new ActionListener() {         //addition of actionlistener to menu item about
			
			public void actionPerformed(ActionEvent e) {
				String desc = "CBallMaze program\nDeveloped and designed by Shreejan Regmi- 18417132\nCoded in Eclipse";  
				JOptionPane.showMessageDialog(null,	desc );  //displays a message box with the text provided
			}
		});
		jIHelp= new JMenuItem("Help Topic");  //addition of menu item help topic
		
		jMScenario.add(jIExit);       //addition of jmenuitem jIExit to menu
		jMEdit.add(jINewC);			 //addition of jmenuitem jinewc  to menu
		jMControl.add(jIAct);		 //addition of jmenuitem jiact to menu
		jMControl.add(jIRun);		//addition of jmenuitem jirun to menu
		jMHelp.add(jIAbout);		//addition of jmenuitem jiabout to menu
		jMHelp.add(jIHelp);	        //addition of jmenuitem jIhelp to menu	
		
		
		 /* Below code adds menus to the menuBar */	
		menuBar.add(jMScenario);       
		menuBar.add(jMEdit);
		menuBar.add(jMControl);
		menuBar.add(jMHelp);
		setJMenuBar(menuBar);   //sets the menu bar to the frame
		
		panelLeft= new JPanel();  //creates new jpanel
		background.add(panelLeft); //adds jpanel to frame
		panelLeft.setBounds(0, 0, 600, 550); //sets the position 
		panelLeft.setBorder(BorderFactory.createLineBorder(Color.black));  //creation of border 
		
		///////////////////////// Setting the maze  /////////////////////////
		
		panelLeft.setLayout(new GridBagLayout()); //sets the layout of panelLeft to gridbag
		maze(); //calling the method maze that contains the code for the creation of maze
		

		////////////////////      J Panel Right with all text fields and GridBag    /////////////////////// 
		
		
		jLOption= new JLabel("Option:"); //creation of new jlabel 'option'
		jLSquare= new JLabel("Square:"); //creation of new jlabel 'square'
		jLDirection= new JLabel("Direction:"); //creation of new jlabel 'direction'
		
		jTOption= new JTextField(7); //creation of text field of option
		jTSquare= new JTextField(7); //creation of text field of square
		jTSquare.setText("15,0"); //sets the default text to "15,0"
		jTDirection= new JTextField(7); //new text field of direction is created
		
		
		panelRight1= new JPanel();  //creates panel
		background.add(panelRight1);  //adds panel
		panelRight1.setBounds(600, 0, 175, 110); //gives the location
		panelRight1.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK)); //sets the border
		panelRight1.setLayout(new GridBagLayout()); //sets layout to gridbag
		
		GridBagConstraints gc = new GridBagConstraints(); //initialization of gridbag constraints
		
		gc.gridx= 0;   //initialization of gridx value that gives the position of cell 
		gc.gridy= 0;  //initialization of gridy value
 		gc.weightx= 1; //initialization of weightx value that sets the size of cell
		gc.weighty= 0.1; ////initialization of weighty value
		gc.fill=GridBagConstraints.NONE;
		
		gc.anchor=GridBagConstraints.FIRST_LINE_START;  //anchors the text to the first line of the cell
		panelRight1.add(jLOption, gc); //adds to panel right through grid bag constraint gb
		
		gc.gridx=2;  //initialization
		gc.gridy=0; //initialization
		panelRight1.add(jTOption, gc); //addition of option text field to maze
		
		gc.gridx=0;
		gc.gridy=1;
		panelRight1.add(jLSquare, gc); //addition of square label to maze
		
		gc.gridx=2;
		gc.gridy=1;
		panelRight1.add(jTSquare,gc); //addition of square text field to maze
		
		gc.gridx=0;
		gc.gridy=2;
		panelRight1.add(jLDirection, gc); //addition of direction label to maze
		
		gc.gridx=2;
		gc.gridy=2;
		panelRight1.add(jTDirection, gc); //addition of direction text field to maze
		

////////////////////////////////////// Panel Right 2 Contains Timer /////////////////////////		
		
		panelRight2= new JPanel(); 
		background.add(panelRight2);
		panelRight2.setBounds(600, 110, 175, 90);
		panelRight2.setLayout(null); //sets the layout of panelRight2 to null
		jLTimer= new JLabel("DIGITAL TIMER"); //creation of new jlabel "DIGITAL TIMER"
		
		jTimer1= new JTextField(2);
		jTimer1.setBackground(Color.BLACK);  //sets the background color of timer1 text field to black
		jTimer1.setForeground(Color.WHITE); //sets the foreground color of timer1 text field to white
		jTimer1.setText("0");  //sets default text to 0
		jTimer1.setHorizontalAlignment(JTextField.CENTER); //Sets the text in the text field to center of it 
		
		jTimer2= new JTextField(2);
		jTimer2.setBackground(Color.BLACK); //sets background color
		jTimer2.setForeground(Color.WHITE); //sets foreground color 
		jTimer2.setText("0"); 
		jTimer2.setHorizontalAlignment(JTextField.CENTER); //centers the text
 		
		jTimer3= new JTextField(2); //creation of new text field
		jTimer3.setBackground(Color.BLACK); //sets background color 
		jTimer3.setForeground(Color.WHITE); 
		jTimer3.setText("0");
		jTimer3.setHorizontalAlignment(JTextField.CENTER); //sets the text to center of text field
		
		jLColon1= new JLabel(":"); 
		jLColon2= new JLabel(":");
		
		digitalTimer = new Timer(1000, new ActionListener() { //initialization of digital timer for showing the timer status 
															  //and updates every second
			public void actionPerformed(ActionEvent arg0) {
				jTimer1.setText(Integer.toString(seconds/3600));  //sets text as hour 
				jTimer2.setText(Integer.toString(seconds/60));    //sets text as minute
				jTimer3.setText(Integer.toString(seconds%60));    //sets text as second
				seconds++; //increases the value of 'second' by 1 every second
			}
		});		
		jLTimer.setBounds(40, 5, 120, 20); //sets the position
		panelRight2.add(jLTimer); //adds the timer jlabel
		
		jTimer1.setBounds(10, 35, 35, 20);
		panelRight2.add(jTimer1);
		jLColon1.setBounds(55,33,20,20);
		panelRight2.add(jLColon1);
		
		jTimer2.setBounds(70, 35, 35, 20);
		panelRight2.add(jTimer2);
		jLColon2.setBounds(115,33,20,20);
		panelRight2.add(jLColon2);
		
		jTimer3.setBounds(130, 35, 35, 20); //sets the location of jTimer3
		panelRight2.add(jTimer3); //adds jTimer3 to panelRight2
		
///////////////////////// Panel Right 3 with direction buttons ////////////////////////////		
		
		panelRight3= new JPanel();  //creation of new JPanel
		background.add(panelRight3); //adds to content pane
		panelRight3.setBounds(600, 200, 170, 110); //gives location with width and height of the object
		
		iCompassE=new ImageIcon(getClass().getResource("east.jpg")); //creation of new ImageIcon for east direction of compass
		iCompassW= new ImageIcon(getClass().getResource("west.jpg")); //new ImageIcon for west direction of compass
		iCompassS= new ImageIcon(getClass().getResource("south.jpg")); //new ImageIcon for south direction of compass
		
		jBUp=new JButton("^"); //creation of new jbutton (up directional button)
		jBUp.setBackground(Color.WHITE); //sets the background color white
		jBUp.setFocusable(false); //does not take focus of keyboard
		jBUp.setBorder(BorderFactory.createLineBorder(new Color(238,238,238), 5, true)); //creates border around
		jBUp.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent move) {  //action performed event of action listener
				if(canMoveUp()==true) {   //An intelligence check. Checks if there is sandblock above the ball
					moveUp(); //if true the ball moves up
					jTDirection.setText("N");  //sets text in the direction label to N
					jBCompass.setIcon(iCompassN);  //sets icon of compass to North
				}
			}
		});
		
		jBDown=new JButton("v");  //creation of new jbutton (down directional key) 
		jBDown.setBackground(Color.WHITE); 
		jBDown.setFocusable(false); //does not take focus of keyboard
		jBDown.setBorder(BorderFactory.createLineBorder(new Color(238,238,238), 5, true));
		jBDown.addActionListener(new ActionListener() {
			
		
			public void actionPerformed(ActionEvent move) {
				if (canMoveDown()==true) { //checks if there is sandblock below the ball
					moveDown(); //moves the ball below by one step if true
					jTDirection.setText("S"); //sets the direction to south
					jBCompass.setIcon(iCompassS); //sets the image to south
				}
			}
		});
		
		jBLeft=new JButton("<"); //creation of new jbutton (left directional key)
		jBLeft.setBackground(Color.WHITE); //sets background color to white of the button 
		jBLeft.setFocusable(false); //does not take focus of keyboard
		jBLeft.setBorder(BorderFactory.createLineBorder(new Color(238,238,238), 5, true)); //creates grey border
		jBLeft.addActionListener(new ActionListener() { //adds action listerner
			
			public void actionPerformed(ActionEvent move) {
				if(canMoveLeft()==true) { //checks if there is sand block to the left
					moveLeft(); //moves the ball left if true
					jTDirection.setText("W"); //sets the direction to west
					jBCompass.setIcon(iCompassW); //sets the direction of compass to west
					
				}
				if(canMoveDown()==true) // checks if there is sand block below, returns true if present
				fallDown(); //fallDown function is called which makes the ball fall down automatically								 
			}
		});
		
		jBRight=new JButton(">"); //creation of new jbutton (right directional key)
		jBRight.setBackground(Color.WHITE); 
		jBRight.setFocusable(false); //not focusable on the button
		jBRight.setBorder(BorderFactory.createLineBorder(new Color(238,238,238), 5, true));
		jBRight.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent move) {
				if(canMoveRight()==true) { //checks if there is sandblock (movable path) to the right
					moveRight(); //moves the ball on the sandpath if true
					jTDirection.setText("E"); //sets the direction to east
					jBCompass.setIcon(iCompassE); //sets the image of compass to east
				}
				if(canMoveDown()==true) {  //checks if there is sand block below, returns true if present 
					fallDown(); //fallDown method is called which makes the ball fall down automatically
				}
				
			}
		});
		
		jBBlank1=new JButton(""); //creates a blank jbutton
		jBBlank1.setBackground(new Color(238,238,238)); //gives the color of the panel to the jbutton
		jBBlank1.setBorder(BorderFactory.createLoweredBevelBorder()); //creates lowered bevel border
		
		jBBlank2=new JButton(""); //creates blank button 
		jBBlank2.setBackground(new Color(238,238,238)); 
		jBBlank2.setBorder(BorderFactory.createLoweredBevelBorder());
		
		jBBlank3=new JButton(""); //creates blank button 
		jBBlank3.setBackground(new Color(238,238,238));
		jBBlank3.setBorder(BorderFactory.createLoweredBevelBorder());
		
		jBBlank4=new JButton(""); //creation of new blank jbutton
		jBBlank4.setBackground(new Color(238,238,238));
		jBBlank4.setBorder(BorderFactory.createLoweredBevelBorder());
		
		jBBlank5=new JButton("");//creates new jbutton
		jBBlank5.setBackground(new Color(238,238,238));
		jBBlank5.setBorder(BorderFactory.createLoweredBevelBorder());
		 
		
		panelRight3.setLayout(new GridLayout(3, 3)); //sets grid layout to panelRight3
		
		/*adds directional jbuttons along with blank jbuttons*/
		panelRight3.add(jBBlank1);  
		panelRight3.add(jBUp);
		panelRight3.add(jBBlank2);
		panelRight3.add(jBLeft);
		panelRight3.add(jBBlank3);
		panelRight3.add(jBRight);
		panelRight3.add(jBBlank4);
		panelRight3.add(jBDown);
		panelRight3.add(jBBlank5);
		
		
			
////////////////////////////////////    Panel Right 4 with options buttons and direction map       ///////////////////// 		
		
		panelRight4= new JPanel();
		background.add(panelRight4);
		panelRight4.setBounds(600, 335 , 175, 215);
		panelRight4.setLayout(null);  //sets layout to null
		panelRight4.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); //creates matte border 
			
		jBOption1= new JButton("Option 1");  //creates new jbutton
		jBOption1.setBackground(Color.WHITE); //sets background color to white
		jBOption1.setFocusable(false); //sets focusable of keyboard keys to false
		jBOption1.addActionListener(this); //add the class as action listener itself
		
		jBOption2= new JButton("Option 2"); //creates new jbutton 
		jBOption2.setBackground(Color.WHITE); 
		jBOption2.setFocusable(false); //sets focusable false
		jBOption2.addActionListener(this); //adds the class as an action listener itself
		
		jBOption3= new JButton("Option 3"); //creates new jbutton
		jBOption3.setBackground(Color.WHITE); 
		jBOption3.setFocusable(false); //sets focusable false
		jBOption3.addActionListener(this); //adds the class as an action listener itself
		
		jBExit= new JButton("Exit"); //creates new jbutton
		jBExit.setBackground(Color.WHITE);
		jBExit.addActionListener(this);//adds the class as an action listener itself
		
		jBOption1.setBounds(1, 1, 83, 30); //sets the location 
		panelRight4.add(jBOption1); //add to panel
		
		jBOption2.setBounds(85, 1, 83, 30); //sets the location
		panelRight4.add(jBOption2); //adds to panel
		
		jBOption3.setBounds(1, 32, 83, 30); //sets the location
		panelRight4.add(jBOption3); //adds to panel
		
		jBExit.setBounds(85, 32, 83, 30); //sets the location
		panelRight4.add(jBExit); //adds to panel
		
		
		iCompassN= new ImageIcon(getClass().getResource("north.jpg")); //creates new ImageIcon of north image for compass		
		jBCompass= new JButton(iCompassN); //jButton is created for the compass
		jBCompass.setBounds(40, 80, 82, 82); 
		panelRight4.add(jBCompass);
		
		
///////////////////////////////////// Panel Bottom with Slider and act buttons ///////////////////////////////
		
		panelBottom= new JPanel();
		background.add(panelBottom);
		panelBottom.setBounds(0, 550, 775, 100);
		panelBottom.setBorder(BorderFactory.createLineBorder(Color.black));
		panelBottom.setLayout(new FlowLayout()); //flow layout is set for panelBottom
		
		
		jBPause = new JButton("Pause", new ImageIcon(getClass().getResource("pause.png"))); //jButton pause is created with pause imageicon
		jBPause.setVisible(false); //does not make the button visible until boolean value is changed
		jBPause.addActionListener(this); //adds action listener as tbe class
		
		jBRun= new JButton("Run", new ImageIcon(getClass().getResource("run.png"))); //jbutton run is created with run image
		jBAct= new JButton("Act", new ImageIcon(getClass().getResource("step.png"))); //jbutton act is created with act image
		jBReset= new JButton("Reset", new ImageIcon(getClass().getResource("reset.png"))); //jbutton reset is created with reset image
		jBReset.addActionListener(this); //action listener is added 
		
		panelBottom.add(jBAct); //adds act button to panelBottom
		panelBottom.add(jBPause);//adds pause button to panelBottom
		panelBottom.add(jBRun);//adds run button to panelBottom
		panelBottom.add(jBReset);//adds reset button to panelBottom
		

		slider= new JSlider(1,1000,200); //slider is initialized with starting value 1, ending value 1000 and default value 200
		
		slider.setMinorTickSpacing(1); //minor tick spacing set to 1
		slider.setMajorTickSpacing(4); //sets major tick spacing to 4
		
		slider.addChangeListener(this); //adds change listener as the class itself
		
		panelBottom.add(Box.createHorizontalStrut(280)); // adds horizontal gap of 200px
		panelBottom.add(slider); //adds slider to panel bottom
		
		snakeTimer = new Timer(500, new ActionListener() {  //creates snake timer that makes the snake obstacle move
															//action iistener is called every 500ms in snakeTimmer 
			public void actionPerformed(ActionEvent arg0) {
				snakeSecondMoveRight();      //method to make the second snake move right
				snakeFirstMoveLeft();     //method to make first snake move left
					
			}	
		});
		
		act(); //calls the method act where the ball finds its own path to the end everytime button ispressed 
		run(); //calls the method run where the ball finds its own path to the end automatically
		
		
		iMushroom= new ImageIcon(getClass().getResource("mushroom.PNG")); //mushroom imageicon is created
		iSnake= new ImageIcon(getClass().getResource("snake.PNG")); //snake imageicon is created
		iEnd=new ImageIcon(getClass().getResource("end.jpg"));//end imageicon is created
	}

	
	public void maze() {  //method that contains code to create the maze 
		gBCon.gridx=0;  //setting gridx value to zero (initial cell) 
		gBCon.gridy=0; //setting gridy value to zero (initial cell)
		iSand= new ImageIcon(getClass().getResource("sand.jpg")); //creating imageicon for sand icon
		jLSand = new JLabel[16][13]; //initializing two dimensional array of jlabel for sand path
		for(int j=0; j<1; j++) {  //setting the for loop for j
			for (int i=0; i<15; i++ ) { //setting nested for loop for i
					jLSand[i][j]= new JLabel(iSand); //creates a jlabel with sand image for the specific value of i and j 
					panelLeft.add(jLSand[i][j], gBCon); //adds the jlabel to panelLeft through gridbag constraint 
					gBCon.gridx++; //increases value by 1 at each iteration of loop
					
			}
		}
		gBCon.gridx=15;//setting gridx value to 15  
		gBCon.gridy=0;//setting gridy value to zero (last cell, first row)
		iGBall= new ImageIcon(getClass().getResource("sand37x37.png"));//creating imageicon for sand icon
		jLSand[15][0]= new JLabel(iGBall);//creates a jlabel with ball image for the specific value of i and j
		panelLeft.add(jLSand[15][0], gBCon);//adds the jlabel to panelLeft through gridbag constraint
		
		gBCon.gridx=1;//sets gridx value to 1
		gBCon.gridy=1;//sets gridy value to 1
		jLSand[1][1]=new JLabel(iSand);//creates a jlabel with sand image for the specific value of i and j
		panelLeft.add(jLSand[1][1], gBCon);//adds the jlabel to panelLeft through gridbag constriant
		
		gBCon.gridx=1;//sets gridx value to 1
		gBCon.gridy=2;//sets gridy value to 2
		jLSand[1][2]=new JLabel(iSand);//creates jlabel with sand image for specific value  
		panelLeft.add(jLSand[1][2], gBCon);//adds jlabel to the panel
		
		gBCon.gridx=5;//sets gridx to 5
		gBCon.gridy=1;//sets gridy to 1
		jLSand[5][1]=new JLabel(iSand);//creates jlabel with sand image for specific value 
		panelLeft.add(jLSand[5][1], gBCon);//adds jlabel to the panel
		
		gBCon.gridx=5;//sets gridx to 5
		gBCon.gridy=2;//sets gridy to 2
		jLSand[5][2]=new JLabel(iSand);//creates jlabel with sand image for specific value
		panelLeft.add(jLSand[5][2], gBCon);//adds jlabel to panel
		
		gBCon.gridx=9;//sets gridx to 9
		gBCon.gridy=1;//sets gridy value to 1
		jLSand[9][1]=new JLabel(iSand);//creates jlabel with sand image for specific value of i and j
		panelLeft.add(jLSand[9][1], gBCon);//adds the jlabel to the panel
				
		gBCon.gridx=9;//sets gridx to 9
		gBCon.gridy=2;//sets gridy to 2
		jLSand[9][2]=new JLabel(iSand);//creates jlabel with sand image for specific value of i and j
		panelLeft.add(jLSand[9][2], gBCon);//adds jlabel to the panel
		
		
		gBCon.gridx=0;//sets gridx value to 0
		gBCon.gridy=3;//sets gridy value to 3
		for(int j=3; j<4; j++) { //initiaizing the for loop for j
			for (int i=0; i<16; i++ ) {//creating nested for loop for i 
					jLSand[i][j]= new JLabel(iSand);//creates jlabel with sand image for range of values
					panelLeft.add(jLSand[i][j], gBCon);//adds it to the panel
					gBCon.gridx++;//increases value of gridx by 1 at each iteration
					
			}
		}
		
		gBCon.gridx=2;//sets gridx value to 2
		gBCon.gridy=4;//sets gridy value to 4
		jLSand[2][4]=new JLabel(iSand);//creates jlabel with sand image
		panelLeft.add(jLSand[2][4], gBCon);//adds it to the panel
		
		gBCon.gridx=2;
		gBCon.gridy=5;
		jLSand[2][5]=new JLabel(iSand);
		panelLeft.add(jLSand[2][5], gBCon); //adds the jlabel of sand to the panel
		
		gBCon.gridx=6;//sets gridx value to 6
		gBCon.gridy=4;//sets gridy value to 4
		jLSand[6][4]=new JLabel(iSand);//creates jlabel for specific value of i and j
		panelLeft.add(jLSand[6][4], gBCon); //adds the jlabel to the panel
		
		gBCon.gridx=6;
		gBCon.gridy=5;
		jLSand[6][5]=new JLabel(iSand);
		panelLeft.add(jLSand[6][5], gBCon);//adds the jlabel of sand to the panel
		
		
		gBCon.gridx=11;//sets gridx value to 11
		gBCon.gridy=4;//sets gridy value to 4
		jLSand[11][4]=new JLabel(iSand);//creates jlabel for specific value of i and j
		panelLeft.add(jLSand[11][4], gBCon);//adds it to the panel
		
		gBCon.gridx=11;
		gBCon.gridy=5;
		jLSand[11][5]=new JLabel(iSand);
		panelLeft.add(jLSand[11][5], gBCon);//adds the jlabel of sand to the panel
		
		gBCon.gridx=0;//sets gridx value to 0
		gBCon.gridy=6;//sets gridy value to 6
		for(int j=6; j<7; j++) {//initializing for loop for j
			for (int i=0; i<16; i++ ) {//initializing for loop for i 
					jLSand[i][j]= new JLabel(iSand); //creating new jlabel for sand
					panelLeft.add(jLSand[i][j], gBCon);//adds it to the panel
					gBCon.gridx++;//increases value of gridx by 1 
					
			}
		}
		
		//configures the gridx and gridy value of a jlabel with sand imageicon and adds it the panel with gridbag constraint
		gBCon.gridx=1;
		gBCon.gridy=7;
		jLSand[1][7]=new JLabel(iSand);
		panelLeft.add(jLSand[1][7], gBCon);
		
		//configures the gridx and gridy value of a jlabel with sand imageicon and adds it the panel with gridbag constraint
		gBCon.gridx=1;
		gBCon.gridy=8;
		jLSand[1][8]=new JLabel(iSand);
		panelLeft.add(jLSand[1][8], gBCon);
		
		//configures the gridx and gridy value of a jlabel with sand imageicon and adds it the panel with gridbag constraint
		gBCon.gridx=5;
		gBCon.gridy=7;
		jLSand[5][7]=new JLabel(iSand);
		panelLeft.add(jLSand[5][7], gBCon);
		
		//configures the gridx and gridy value of a jlabel with sand imageicon and adds it the panel with gridbag constraint
		gBCon.gridx=5;
		gBCon.gridy=8;
		jLSand[5][8]=new JLabel(iSand);
		panelLeft.add(jLSand[5][8], gBCon);
		
		//configures the gridx and gridy value of a jlabel with sand imageicon and adds it the panel with gridbag constraint
		gBCon.gridx=12;
		gBCon.gridy=7;
		jLSand[12][7]=new JLabel(iSand);
		panelLeft.add(jLSand[12][7], gBCon);
		
		//configures the gridx and gridy value of a jlabel with sand imageicon and adds it the panel with gridbag constraint
		gBCon.gridx=12;
		gBCon.gridy=8;
		jLSand[12][8]=new JLabel(iSand);
		panelLeft.add(jLSand[12][8], gBCon);
		
		
		gBCon.gridx=0;//sets the gridx value to 0
		gBCon.gridy=9;//sets the gridy value to 9
		for(int j=9; j<10; j++) { //initializes the for loop for j
			for (int i=0; i<16; i++ ) { //initializes the nested for loop for i
					jLSand[i][j]= new JLabel(iSand);//creates jlabel of sand with imageicon sand for range of i and j values
					panelLeft.add(jLSand[i][j], gBCon);//adds it to the panel
					gBCon.gridx++;//increases the value of gridx by 1
					
			}
		}
		
		//configures the gridx and gridy value of a jlabel with sand imageicon and adds it the panel with gridbag constraint
		gBCon.gridx=2;
		gBCon.gridy=10;
		jLSand[2][10]=new JLabel(iSand);
		panelLeft.add(jLSand[2][10], gBCon);
		
		//configures the gridx and gridy value of a jlabel with sand imageicon and adds it the panel with gridbag constraint
		gBCon.gridx=2;
		gBCon.gridy=11;
		jLSand[2][11]=new JLabel(iSand);
		panelLeft.add(jLSand[2][11], gBCon);

		//configures the gridx and gridy value of a jlabel with sand imageicon and adds it the panel with gridbag constraint
		gBCon.gridx=6;
		gBCon.gridy=10;
		jLSand[6][10]=new JLabel(iSand);
		panelLeft.add(jLSand[6][10], gBCon);

		//configures the gridx and gridy value of a jlabel with sand imageicon and adds it the panel with gridbag constraint
		gBCon.gridx=6;
		gBCon.gridy=11;
		jLSand[6][11]=new JLabel(iSand);
		panelLeft.add(jLSand[6][11], gBCon);
		
		
		gBCon.gridx=1;//sets gridx to 1
		gBCon.gridy=12;//sets gridy to 12
		for(int j=12; j<13; j++) {
			for (int i=1; i<16; i++ ) { //creates nested for loop of i and j
					jLSand[i][j]= new JLabel(iSand);//creates sand jlabel
					panelLeft.add(jLSand[i][j], gBCon);//adds sand jlabel to the panel
					gBCon.gridx++;//increases the value of gridx by 1
					
			}
		}
		
		iGoal = new ImageIcon(getClass().getResource("sandstone.jpg")); //creates imageicon of grey end block
		jLSand[0][12]= new JLabel(iGoal);//creates jlabel with grey imageicon
		gBCon.gridx=0;//sets the gridx value 
		gBCon.gridy=12;//sets the gridy value
		panelLeft.add(jLSand[0][12], gBCon);//adds it to the panel
		
		
		gBCon.gridx=0;//sets the gridx 
		gBCon.gridy=1;//sets the gridy value
		iWhite = new ImageIcon(getClass().getResource("white37x37.jpg"));//creates imageicon of white spaces
		jLSand[0][1]= new JLabel(iWhite);//creates jlabel with whitespace imageicon
		panelLeft.add(jLSand[0][1], gBCon);//adds it to the panel
		
		//configures the gridx and gridy value of a jlabel with whitespace imageicon and adds it the panel with gridbag constraint
		gBCon.gridx=0;
		gBCon.gridy=2;
		jLSand[0][2]= new JLabel(iWhite);
		panelLeft.add(jLSand[0][2], gBCon);
		
		gBCon.gridx=2;//sets gridx value
		gBCon.gridy=1;//sets gridy value
		for (int j=1; j<3; j++) {
			for (int i=2; i<5; i++) {//initialization of nested for loop
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);//the the whitespace jlabel to the panel
				gBCon.gridx++;//increases gridx value by 1
			}
			gBCon.gridy++;//increases gridy value 
			gBCon.gridx=2;//sets gridx value to 2
		}
		
		//sets the grid values and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=6;
		gBCon.gridy=1;
		for (int j=1; j<3; j++) {
			for (int i=6; i<9; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=6;// setting the grid x value back to 6 for looping 2nd row 
		}
		
		//sets the grid values, and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=10;
		gBCon.gridy=1;
		for (int j=1; j<3; j++) {
			for (int i=10; i<16; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=10;//resets the gridx value for another iteration of loop
		}
		
		//sets the grid values, and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=0;
		gBCon.gridy=4;
		for (int j=4; j<6; j++) {
			for (int i=0; i<2; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=0;//sets the gridx value to 0
		}

		//sets the grid values, and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=0;
		gBCon.gridy=4;
		for (int j=4; j<6; j++) {
			for (int i=0; i<2; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=0;//sets the gridx value to 0 for another loop
		}

		//sets the grid values, and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=3;
		gBCon.gridy=4;
		for (int j=4; j<6; j++) {
			for (int i=3; i<6; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=3;//resets the gridx value for another iteration of loop
		}

		//sets the grid values, and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=7;
		gBCon.gridy=4;
		for (int j=4; j<6; j++) {
			for (int i=7; i<11; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=7;//resets the gridx value for another iteration of loop
		}

		//sets the grid values, and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=12;
		gBCon.gridy=4;
		for (int j=4; j<6; j++) {
			for (int i=12; i<16; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=12;//resets the gridx value for another iteration of loop
		}

		//sets the grid values, and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=0;
		gBCon.gridy=7;
		jLSand[0][7]= new JLabel(iWhite);
		panelLeft.add(jLSand[0][7], gBCon);
		
		//sets the gridx and gridy value and adds a jlabel with whitespace imageicon to the panel with gridbag constraint
		gBCon.gridx=0;
		gBCon.gridy=8;
		jLSand[0][8]= new JLabel(iWhite);
		panelLeft.add(jLSand[0][8], gBCon);

		//sets the grid values, and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=2;
		gBCon.gridy=7;
		for (int j=7; j<9; j++) {
			for (int i=2; i<5; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=2;//resets the gridx value for another iteration of loop
		}

		//sets the grid values, and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=6;
		gBCon.gridy=7;
		for (int j=7; j<9; j++) {
			for (int i=6; i<12; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=6;//resets the gridx value for another iteration of loop
		}

		//sets the grid values, and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=13;
		gBCon.gridy=7;
		for (int j=7; j<9; j++) {
			for (int i=13; i<16; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=13;//resets the gridx value for another iteration of loop
		}

		//sets the grid values, and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=0;
		gBCon.gridy=10;
		for (int j=10; j<12; j++) {
			for (int i=0; i<2; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=0;//resets the gridx value for another iteration of loop
		}

		//sets the grid values, and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=3;
		gBCon.gridy=10;
		for (int j=10; j<12; j++) {
			for (int i=3; i<6; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=3;//resets the gridx value for another iteration of loop
		}

		//sets the grid values, and in a nested for loop: creates and adds the whitespace jlabel to the panel with gridbag constraint
		gBCon.gridx=7;
		gBCon.gridy=10;
		for (int j=10; j<12; j++) {
			for (int i=7; i<16; i++) {
				jLSand[i][j]= new JLabel(iWhite);
				panelLeft.add(jLSand[i][j], gBCon);
				gBCon.gridx++;
			}
			gBCon.gridy++;
			gBCon.gridx=7;//resets the gridx value for another iteration of loop
		}
		
	}
	
	public boolean canMoveLeft() { //creation of intelligence check method canMoveLeft
		if (xPos!=0 && ((jLSand[xPos-1][yPos].getIcon().equals(iSand)) // the condition returns true if the ball is not at 
				|| jLSand[xPos-1][yPos].getIcon().equals(iGoal) ||     // the top row and has sand/goal/snake imageicon
				jLSand[xPos-1][yPos].getIcon().equals(iSnake))){       //to the left of the ball
			return true;
		}
		return false;
		
	}
	
	public void moveLeft() { //creation of method for the left movement of ball
		if(jLSand[xPos-1][yPos].getIcon().equals(iSnake)) {               //Here lies the code for the reset position of ball when 
			jLSand[xPos][yPos].setIcon(iSand);                            //the ball
			jLSand[15][0].setIcon(iGBall);                                //touches 
			xPos=15;                                                      // a 
			yPos=0;                                                       //non-moving 
			jTSquare.setText(""+xPos+","+yPos);                           //snake
		}
		else if(jLSand[xPos-1][yPos].getIcon().equals(iGoal)) {  //changes the imageicon of the end block
			jLSand[xPos-1][yPos].setIcon(iEnd);                  //to ball over grey block image if the ball moves left 
			jLSand[xPos][yPos].setIcon(iSand);                   //from the sand just next to the end block
			xPos--; //decreases value by 1
		}
		
		else{
			jLSand[xPos-1][yPos].setIcon(iGBall); //sets the imageicon of the sand left to the ball to ball image
			jLSand[xPos][yPos].setIcon(iSand);//sets the original position of ball to imageicon of sand
			xPos--;//decreases x position value by 1
		}
		jTSquare.setText(""+xPos+","+yPos); //sets position text to square text field
		
		if(xPos==0 && yPos==12) {
			JOptionPane.showMessageDialog(null, "Game Completed. Congratulations!"); //message dialog is shown if the ball reaches the end
		}
		
	}
	
	public boolean canMoveDown() {//intelligence check method for ball to move down
		if(yPos!=12 && (jLSand[xPos][yPos+1].getIcon().equals(iSand) //returns true if ball is above the last row and contains imageicon of sand
				|| jLSand[xPos][yPos+1].getIcon().equals(iSnake))) { //or snake below the ball when down button is pressed
			return true;
		}
		return false;//else returns false
	}
	
	public void moveDown() {//creation of method for down movement of ball
		if(jLSand[xPos][yPos+1].getIcon().equals(iSnake)) {    //if there is snake below the ball
			jLSand[xPos][yPos].setIcon(iSand);                 //and left button is pressed,
			jLSand[15][0].setIcon(iGBall);                     // the ball resets back to the initial 
			xPos=15;                                           // position and the xposition, yposition value is also reset
			yPos=0;                                            // to 15 and 0 
			jTSquare.setText(""+xPos+","+yPos); // sets the position text                
		}
		else {
		jLSand[xPos][yPos+1].setIcon(iGBall);//sets the imageicon of the sand below to the ball to ball image
		jLSand[xPos][yPos].setIcon(iSand);//sets the original position of ball to image of sand
		yPos++;
		jTSquare.setText(""+xPos+","+yPos);//sets position text
		}
	}
	public boolean canMoveRight() {//intelligence check method for ball to move right
		if(xPos!= 15 && (jLSand[xPos+1][yPos].getIcon().equals(iSand)//if the ball is not at rightmose position and there is
				|| jLSand[xPos+1][yPos].getIcon().equals(iSnake))) { //imageicon of sand or snake to the right then returns true
			return true;
		}
		return false;//esle returns false
	}
	
	public void moveRight() {//method for ball to move right
		if(jLSand[xPos+1][yPos].getIcon().equals(iSnake)) {//if there is snake on the right of the 
			jLSand[xPos][yPos].setIcon(iSand);			   //ball and right button is pressed
			jLSand[15][0].setIcon(iGBall);                 //the ball resets back to
			xPos=15;                                       // initial position
			yPos=0;                                        //  and xposition, yposition value is also reset
			jTSquare.setText(""+xPos+","+yPos);//sets position text            
		} 
		else if(xPos==0 && yPos==12) {             //if the ball is at end position and right button is pressed
			jLSand[xPos+1][yPos].setIcon(iGBall);  //the icon of block right to the ball is changed to ball over sand block
			jLSand[xPos][yPos].setIcon(iGoal);     //and original position imageicon changes to grey block
			xPos++;
		}
		else {
		jLSand[xPos+1][yPos].setIcon(iGBall);//sets the imageicon of right block to ball 
		jLSand[xPos][yPos].setIcon(iSand);//sets the original position imageicon to sand
		xPos++;
		}
		jTSquare.setText(""+xPos+","+yPos); //sets the text of position
	}
	
	public boolean canMoveUp() {//intelligence check for whether ball can move up
		if(yPos!=0 && (jLSand[xPos][yPos-1].getIcon().equals(iSand)//checks if ball is not at the top and there is sand image above the ball
				|| jLSand[xPos][yPos-1].getIcon().equals(iSnake))) {//or has snake over the ball
			return true;  //returns true
		}
		return false; //returns false
	}
	public void moveUp() {//method to move the ball up
		if(jLSand[xPos][yPos-1].getIcon().equals(iSnake)) { //if there is snake above the ball and left button is pressed
			jLSand[xPos][yPos].setIcon(iSand);              //the ball resets
			jLSand[15][0].setIcon(iGBall);                  //to the initial position and
			xPos=15;                                        //xposition and
			yPos=0;                                         //y position value is reset 
			jTSquare.setText(""+xPos+","+yPos); 
		}
		else {
		jLSand[xPos][yPos-1].setIcon(iGBall); //sets the imageicon of block above the ball to ball icon
		jLSand[xPos][yPos].setIcon(iSand);//sets the imageicon of the original position to sand
		yPos--;
		jTSquare.setText(""+xPos+","+yPos);
		}
	}
	
	public void fallDown() { //method for the automatic fall of the ball when there is sandblock below
		if(fallDownBool==true) { //checks the value of fall boolean
		fallTimer = new Timer(500, new ActionListener() { //initializes the timer 
			public void actionPerformed(ActionEvent e) {
				if(canMoveDown()==true) {  //checks if canMoveDown returns true value
					moveDown(); //moves the ball one step down
					audioPlay(); //plays the falling audio
					}
				else
					fallTimer.stop(); //if there is no sandblock below the timer stops and ball stops falling
			}
		});
		fallTimer.start(); //starts the timer when method is called
		}
	}

	public void act() {//this method is called when act button is pressed
		jBAct.addActionListener(new ActionListener() { //action listener is added
			public void actionPerformed(ActionEvent e) {
				actConditionCheck(); //calls function acrConditionCheck where the act sequence occurs
			}
		});
	}
	
	public void run() { //method called when run button is pressed
		//timer reference: Timer (Java Platform SE 7 ). 2018. Timer (Java Platform SE 7 ). [ONLINE] Available at: https://docs.oracle.com/javase/7/docs/api/java/util/Timer.html. [Accessed 10 July 2018].
		timer2 = new Timer(500, new ActionListener() {  //initializes new timer timer2
			
			public void actionPerformed(ActionEvent e) {
				actConditionCheck();  //again, act sequence is called which determines the path for ball and is called at every 500ms interval
				if(xPos==0 && yPos==12) { //if ball is at the end
					timer2.stop();//timer stops
					digitalTimer.stop();//digital timer stops
					jBPause.setVisible(false); //pause button becomes invisible
					jBRun.setVisible(true); //run buttons appear in place of pause button
				}
				
				
			}
		});
		jBRun.addActionListener(new ActionListener() { //addition of action listener to run button
			public void actionPerformed(ActionEvent e) {
				timer2.start();	//run timer is started 
				digitalTimer.start(); //digital timer is started
				jBPause.setVisible(true); //pause button appears in place of run button
				jBRun.setVisible(false); //run button disappears
			}
		});
	}
	
	public void actConditionCheck() { //this method determines the movement of ball when act button is pressed 
		if((xPos==0) && yPos==12) { //if ball is at the end 
			JOptionPane.showMessageDialog(null, "Game Completed. Congratulations!"); //congratulations message is shown
		}
		else {
		if(canMoveDown()==true) { //if the ball can move down
			moveDown();//ball moves down one step 
			audioPlay();//audio is played 
			jTDirection.setText("S");//sets direction
			jBCompass.setIcon(iCompassS);//sets compass image
		}
		else if(canMoveDown()==false && canMoveRight()==true && canMoveLeft()==true && (xPos==1 && (yPos==3 || yPos==9) ) ) { //if ball is at certain position and can move left or right
			moveRight(); //the ball moves right
			jTDirection.setText("E"); //sets direction 
			jBCompass.setIcon(iCompassE);//sets compass image
		}
		else if(canMoveDown()==false && canMoveLeft()==true && canMoveRight()==true && xPos==1 && yPos==12) {//if ball is at certain position can move left and right both
			moveLeft();//moves left
			jTDirection.setText("W");//sets direction 
			jBCompass.setIcon(iCompassW);//sets compass image
		}
		else if(canMoveDown()==false && canMoveRight()==true && canMoveLeft()==true) {//if ball can  move left and right both
			moveLeft();//ball moves left
			jTDirection.setText("W");//sets direction
			jBCompass.setIcon(iCompassW);//sets compass image
		}
		else if (canMoveDown()==false && canMoveRight()==false && canMoveLeft()==true) {//if ball can move left only
			moveLeft(); //ball moves left
			jTDirection.setText("W"); 
			jBCompass.setIcon(iCompassW);
		}
		else if(canMoveDown()==false && canMoveLeft()==false && canMoveRight()==true) {//if ball can move only right
			moveRight(); //ball moves right
			jTDirection.setText("E"); //sets direction text
			jBCompass.setIcon(iCompassE); //sets compass text
		}
		}
	}
	
	/*changes the imageicon of specific jlabels inside the method to snake when method snakeLocation is called*/ 
	public void snakeLocation() {      
		jLSand[8][6].setIcon(iSnake);
		jLSand[6][10].setIcon(iSnake);
		jLSand[5][0].setIcon(iSnake);	
		jLSand[1][6].setIcon(iSnake);
		jLSand[9][12].setIcon(iSnake);
		jLSand[15][6].setIcon(iSnake);
}
	
	int xSnakeR=0;//initial x position of snake moving right
	int ySnakeR=9;//initial y position of snake moving right
	public void snakeSecondMoveRight() { //method to move right
		if((xSnakeR==xPos) && (ySnakeR==yPos)) {  //if the moving snake touches the ball
			xPos=15;                              //the ball is reset 
			yPos=0;                               //back to the 
			jLSand[xPos][yPos].setIcon(iSand);    //initial position and fills the previous ball position by sand imageicon
			jLSand[15][0].setIcon(iGBall);       
		}
		if(xSnakeR!=16) { //if snake is not at the rightmost end 
			jLSand[xSnakeR][ySnakeR].setIcon(iSnake);  //sets the current position of snake to snake icon
			if(xSnakeR!=0) { //if snake is not at the left most sand block
			jLSand[xSnakeR-1][ySnakeR].setIcon(iSand); //sets the imageicon of jlabel left to the sanke to sand icon
			}
			xSnakeR++; //increases the xposition of snake by 1
			}
		else if(xSnakeR==16) { //if snake is at the end (rightmost part)
			jLSand[xSnakeR-1][ySnakeR].setIcon(iSand); //the rightmost jlabel is changed to imageicon of sand 
			xSnakeR=0; //resets the position of snake
			jLSand[xSnakeR][ySnakeR].setIcon(iSnake); //sets the icon of current position of snake to snake imageicon
			snakeSecondMoveRight(); //recursively, snakeSecondMoveRight method is called
		}
		
	}
	
	int xSnakeL=15;//initial x position of snake moving left
	int ySnakeL=3;//intial y position of snake moving left
	public void snakeFirstMoveLeft() {
		if((xSnakeL==xPos && ySnakeL==yPos)) {   //if the snake touches the ball                	 																*/
			xPos=15;//xposition is reset										    
			yPos=0;//yposition is reset												
			jLSand[xPos][yPos].setIcon(iSand);//changes original position of the ball to imageicon of snad
			jLSand[15][0].setIcon(iGBall);//sets the initial block to imageicon of ball
		}
		if(xSnakeL!=-1) { //if snake is not at leftmost position
			jLSand[xSnakeL][ySnakeL].setIcon(iSnake); //imageicon of snake is set to the next left block
			if(xSnakeL!=15) { //if snake is not at rightmost position
			jLSand[xSnakeL+1][ySnakeL].setIcon(iSand); //sets the imageicon of right block next to snake to sand
			}
			xSnakeL--; //snake left position value is decreased by 1
			}
		else if(xSnakeL==-1) { //if snake is at the leftmost position
			jLSand[xSnakeL+1][ySnakeL].setIcon(iSand); //sets the imageicon of leftmost block to sand 
			xSnakeL=15; //resets snake left position
			jLSand[xSnakeL][ySnakeL].setIcon(iSnake); //sets imageicon of snake to next left block
			snakeFirstMoveLeft(); //recursively, snakeFirstMoveLeft method is called
		}
		
	}
	
	public void snakeMove() { //this method contains timer which makes the snake move
	snakeLocation(); //snakeLocation method for adding snakes is called here
	snakeTimer.start();	 //starts snake timer
}
	
	/*addition of sound referenced from: Playing Sound in Java. 2018. Playing Sound in Java. [ONLINE] Available at: http://www.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html. [Accessed 10 July 2018] */
	public void audioPlay(){ //method for playing audio   
		try {
			AudioInputStream dropSound= AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("drop.wav")); //creates audio input stream from file provided
			Clip playSound = AudioSystem.getClip(); //sound clip is created from audio system
			playSound.open(dropSound); //executes dropSound audio stream
			playSound.start(); //plaus the clip
		} catch (UnsupportedAudioFileException e) { //catches unsupportedaudiofileexception
			e.printStackTrace(); //throws the exception to standard error system
		} catch (IOException e) { //catches IOException
			e.printStackTrace();//throws the exception to standard error system
		}catch(LineUnavailableException e) {//catches LineUnavailableException
			e.printStackTrace();//throws the exception to standard error system
		}
	}
	
		
	public void actionPerformed(ActionEvent event) { //action performed method for the class file
		Object getSource = event.getSource(); //stores the source of the event to object getSource
		if(getSource.equals(jBOption1)) { //if source is option1 jbutton
			//reference of update UI (refresing panel) :Java Code Examples javax.swing.JPanel.updateUI. 2018. Java Code Examples javax.swing.JPanel.updateUI. [ONLINE] Available at: https://www.programcreek.com/java-api-examples/?class=javax.swing.JPanel&method=updateUI. [Accessed 10 July 2018].
			panelLeft.removeAll(); //removes all components in maze panel  
			panelLeft.updateUI(); //updates the UI 
			maze(); //maze method is called which builds the maze 
			xPos=15;
			yPos=0;
			fallDownBool=false; //fallDownBool is set to false at the ball should not fall in option 1
			jTOption.setText("1");
			jTOption.setHorizontalAlignment(JTextField.CENTER);
			if(snakeTimer.isRunning()==true) { //if snaketimer is running
			snakeTimer.stop(); //stops the snake timer
			}
			
			}
		
		if(getSource.equals(jBReset)) {//if the source is reset button 
			dispose(); //discards all of the jframe and its components //dispose referenced from: Stack Overflow. 2018. user interface - Disposing and closing windows in Java - Stack Overflow. [ONLINE] Available at: https://stackoverflow.com/questions/4737495/disposing-and-closing-windows-in-java. [Accessed 10 July 2018].
			CBallMaze.main(null);//main method is called which creates the jframe again and is displayed
			timer2.stop(); //run timer is stopped 
			digitalTimer.stop(); //digital timer is stopped
			if(snakeTimer.isRunning()==true) { //if snake timer is running 
				snakeTimer.stop();//stops the snake timer
			}
			seconds=0; //second value of digital timer is reset
		}
		
		if(getSource.equals(jBPause)) { //if the source is pause button 
			digitalTimer.stop(); //digital timer is stopped 
			timer2.stop();//run timer is stopped 
			jBPause.setVisible(false);//pause button disappears
			jBRun.setVisible(true);//run button appears
		}
		
		if(getSource.equals(jBOption2)) { //if the source is option 2 button
			jTOption.setText("2");
			jTOption.setHorizontalAlignment(JTextField.CENTER);
			panelLeft.removeAll(); //removes all component in maze panel
			panelLeft.updateUI(); //resets the UI property
			maze(); //maze method is called to build the maze
			xPos=15; //xposition is reset
			yPos=0;
			fallDownBool=true; //fall boolean is set to true as ball should fall down in option 2
			snakeMove(); //snakeMove method is called which moves the snake
			
		}
		
		if(getSource.equals(jBOption3)) {//if the source is option 3 button
			jTOption.setText("3"); 
			jTOption.setHorizontalAlignment(JTextField.CENTER);
			panelLeft.removeAll();//removes all component in maze panel
			panelLeft.updateUI(); //resets the UI property
			maze(); //maze method is called to remake the maze 
			xPos=15;
			yPos=0;
			fallDownBool=true; //fall boolean is set to true as the ball should fall in option 3
			/*Sets the location and changes the imageicon of specified jlabels to mushroom when option 3 button is pressed*/
			jLSand[13][12].setIcon(iMushroom);
			jLSand[8][0].setIcon(iMushroom);
			jLSand[5][3].setIcon(iMushroom);
			jLSand[3][6].setIcon(iMushroom);
			jLSand[7][9].setIcon(iMushroom);
			jLSand[3][12].setIcon(iMushroom);
			if(snakeTimer.isRunning()==true) { //if snake timer is running
				snakeTimer.stop(); //stop snake timer
			}
			}
		
		if(getSource.equals(jBExit)) {//source for exit button
			System.exit(0); //terminates the whole program
		}
			
		}

	public void keyPressed(KeyEvent e) { //key pressed event for keyboard key presses
		pressedBtn= e.getKeyCode(); //gets the keycode and stores in pressedBtn variable
		switch (pressedBtn) {
		case KeyEvent.VK_UP: //event key code of up directional key
			if(canMoveUp()==true)
				moveUp(); //move the ball up
			break;
		case KeyEvent.VK_LEFT://event key code of left directional key
			if(canMoveLeft()==true)
				moveLeft();//move the ball left
			break;
		case KeyEvent.VK_DOWN://event key code of down directional key
			if(canMoveDown()==true)
				moveDown();//move the ball down
			break;
		case KeyEvent.VK_RIGHT://event key code of right directional key 
			if(canMoveRight()==true)
				moveRight();//move the ball right
			break;
		
		}
	}

	public void keyReleased(KeyEvent e) { //key released event
		
	}

	public void keyTyped(KeyEvent e) { //key typed event
		
	}

	public void stateChanged(ChangeEvent e) { //state changed method of slider
		slider.setInverted(true);    
		int slow =slider.getValue(); //gets the value of the current position of slider
		timer2.setDelay(slow); //sets delay to the initial value of the run timer
								//Functionalities referenced from: docs.oracle.com. 2018. How To Use Sliders . [ONLINE] Available at: https://docs.oracle.com/javase/tutorial/uiswing/components/slider.html. [Accessed 10 July 2018]
	if(snakeTimer.isRunning()==true){
			snakeTimer.setDelay(slow);  //sets delay to the snake moving speed if the snake timer is running
		}
	}
}
	