package main;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class Calculator implements ActionListener {
	
	ImageIcon image = new ImageIcon(getClass().getResource("/images/icon.png"));
	JFrame frame = new JFrame("Calculator");
	JPanel buttonsPanel = new JPanel(new GridLayout(6,4,3,3));
	JPanel displayPanel = new JPanel(new BorderLayout());
	JLabel currentNumber = new JLabel("", SwingConstants.RIGHT);
	JLabel previousNumber = new JLabel("", SwingConstants.RIGHT);
	String currentOperation;
	
	ArrayList<JButton> buttonList = new ArrayList<>();
	String[] icons = {"%", "CE", "C", "←", "1/x", "x²", "√", "/", "7", "8", "9", "x", "4", "5", "6", "-", "1", "2", "3", "+", "±", "0", ",", "="}; 
	
	
	public void generateFrame() {
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(336,699);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.addKeyListener(new KeyHandler());
		frame.setIconImage(image.getImage());
		frame.setLayout(null);
		
		//Generate buttons 
		for(int i=0;i<icons.length;i++) {
			buttonList.add(new JButton(icons[i]));
			buttonList.get(i).addActionListener(this);
			buttonList.get(i).setFocusable(false);
			buttonList.get(i).setPreferredSize(new Dimension(50,50));
			buttonList.get(i).setFont(new Font("Arial", Font.PLAIN, 24));
			buttonList.get(i).setFocusPainted(false);
			buttonList.get(i).setBackground(new Color(220,220,220));
			buttonList.get(i).setForeground(Color.BLACK);
			buttonList.get(i).setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			buttonsPanel.add(buttonList.get(i));
		}
		
		//Generate displayPanel
		displayPanel.setBackground(new Color(255,255,255));
		displayPanel.setBorder(BorderFactory.createMatteBorder(3,3,3,3, Color.GRAY));
		
		
		//Generate display Labels
		currentNumber.setFont(new Font("Arial", Font.PLAIN,44));
		currentNumber.setForeground(Color.BLACK);
		currentNumber.setOpaque(false);
		currentNumber.setBackground(Color.GRAY);
		currentNumber.setBorder(BorderFactory.createEmptyBorder(2,5,5,5));
		
		previousNumber.setFont(new Font("Arial",Font.PLAIN,20));
		previousNumber.setForeground(Color.black);
		previousNumber.setOpaque(false);
		previousNumber.setBackground(Color.gray);
		previousNumber.setBorder(BorderFactory.createEmptyBorder(2,5,5,5));
		
		
		
		
		displayPanel.setBounds(0,0,320,120);
		buttonsPanel.setBounds(0,120,320,540);
		
		
		
		displayPanel.add(previousNumber, BorderLayout.NORTH);
		displayPanel.add(currentNumber, BorderLayout.SOUTH);
		frame.add(displayPanel);
		frame.add(buttonsPanel);
		frame.setVisible(true);
		
	}
	
	public boolean isInt(double number) {
		if(number % 1 == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void clicked(String operation) {
		
		String temp = currentNumber.getText();
		String prevTemp = previousNumber.getText();
		double answer;
		double baseForYuzde = 0;
		double valueForYuzde = 0;
		double currentNum = 0, prevNum = 0;
		
		switch(operation) {
		
			
			
			case "CE":
				currentNumber.setText("");
				break;
			case "C":
				previousNumber.setText("");
				currentNumber.setText("");
				break;
			case "←":
				if (temp.length()>=1)
					currentNumber.setText(temp.substring(0,temp.length()-1));
				break;
			case "1/x":
				previousNumber.setText("");
				 if(temp.length()>=1) {
					 answer = 1 / Double.parseDouble(temp);
					 if(isInt(answer)) {
						currentNumber.setText(String.format("%.0f", answer));
					 }else {
						 currentNumber.setText(answer + "");
					 }
					 
				 }
				 break;
				 
				 
				 
				
				 
				 
				 
			case "%":
				
				if(!temp.isEmpty()) {
					
					answer = Double.parseDouble(temp)/ 100 ;
					currentNumber.setText(answer + "");
					currentOperation = "=";
					previousNumber.setText("");
				}
					
				
				break;
				
				
				
				
				
				
			case "x²":
				if(temp.length()>=1) {
					previousNumber.setText("");
					answer = Math.pow(Double.parseDouble(temp),2);
					if(isInt(answer)) {
						currentNumber.setText(String.format("%.0f", answer));
					 }else {
						 currentNumber.setText(answer + "");
					 }
				}
				break;
			case "√":
				previousNumber.setText("");
				if(temp.length() >=1) {
					answer = Math.sqrt(Double.parseDouble(temp));
					if(isInt(answer)) {
						currentNumber.setText(String.format("%.0f", answer));
					 }else {
						 currentNumber.setText(answer + "");
					 }
					
				}
				break;
				
				
				
				
			case "/":
				
				
				if(!prevTemp.isEmpty() && !temp.isEmpty()) {
					
					if(currentOperation == "=") {
						
						previousNumber.setText(temp + " /");
						currentNumber.setText("");
						currentOperation = "/";
						
						
					} 
					else {
						
						currentNum = Double.parseDouble(temp);
						prevNum = Double.parseDouble(prevTemp.substring(0, prevTemp.length() - 2));
						answer = compute(currentOperation, currentNum, prevNum);
						if(isInt(answer)) {
							currentNumber.setText("");
							previousNumber.setText(String.format("%.0f", answer) + " /");
						}
						else {
							currentNumber.setText("");
							previousNumber.setText(answer + " /");
						}
						currentOperation = "/";
						
						
					}
					
					
					
				}
				else {
					
					if(!prevTemp.isEmpty() && temp.isEmpty()) {
						
						currentOperation = "/";
						previousNumber.setText(prevTemp.substring(0, prevTemp.length() - 1) + currentOperation);
						
					}
					else if(prevTemp.isEmpty() && !temp.isEmpty()) {
						
						currentOperation = "/";
						previousNumber.setText(temp + " " + currentOperation);
						currentNumber.setText("");
						
					}
					
					else {
						System.out.println("Error in division part!");
					}
					
					
					
				}
				
				break;
				
				
			case "x":
				
				if(!prevTemp.isEmpty() && !temp.isEmpty()) {
					
					if(currentOperation == "=") {
						
						previousNumber.setText(temp + " x");
						currentNumber.setText("");
						currentOperation = "x";
							
					} 
					
					else {
						
						currentNum = Double.parseDouble(temp);
						prevNum = Double.parseDouble(prevTemp.substring(0, prevTemp.length() - 2));
						answer = compute(currentOperation, currentNum, prevNum);
						if(isInt(answer)) {
							currentNumber.setText("");
							previousNumber.setText(String.format("%.0f", answer) + " x");
						}
						else {
							currentNumber.setText("");
							previousNumber.setText(answer + " x");
						}
						currentOperation = "x";
						
						
					}
					
					
				}
				else {
					
					if(!prevTemp.isEmpty() && temp.isEmpty()) {
						
						currentOperation = "x";
						previousNumber.setText(prevTemp.substring(0, prevTemp.length() - 1) + currentOperation);
						
					}
					else if(prevTemp.isEmpty() && !temp.isEmpty()) {
						
						currentOperation = "x";
						previousNumber.setText(temp + " " + currentOperation);
						currentNumber.setText("");
						
					}
					
					else {
						System.out.println("Error in multipication part!");
					}
					
					
					
				}
				
				break;
				
				
				
				
			case "-":
				
				
				if(!prevTemp.isEmpty() && !temp.isEmpty()) {
					
					
					if(currentOperation == "=") {
						
						previousNumber.setText(temp + " -");
						currentNumber.setText("");
						currentOperation = "-";
						
						
					} else {
					
						currentNum = Double.parseDouble(temp);
						prevNum = Double.parseDouble(prevTemp.substring(0, prevTemp.length() - 2));
						answer = compute(currentOperation, currentNum, prevNum);
						if(isInt(answer)) {
							currentNumber.setText("");
							previousNumber.setText(String.format("%.0f", answer) + " -");
						}
						else {
							currentNumber.setText("");
							previousNumber.setText(answer + " -");
						}
						currentOperation = "-";
					}
					
					
				}
				else {
					
					if(!prevTemp.isEmpty() && temp.isEmpty()) {
						
						currentOperation = "-";
						previousNumber.setText(prevTemp.substring(0, prevTemp.length() - 1) + currentOperation);
						
					}
					else if(prevTemp.isEmpty() && !temp.isEmpty()) {
						
						currentOperation = "-";
						previousNumber.setText(temp + " " + currentOperation);
						currentNumber.setText("");
						
					}
					
					else {
						System.out.println("Error in subtraction part!");
					}
					
					
					
				}
				
				break;
				
				
				
			case "+":
				
				if(!prevTemp.isEmpty() && !temp.isEmpty()) {
					
					if(currentOperation == "=") {
						
						previousNumber.setText(temp + " +");
						currentNumber.setText("");
						currentOperation = "+";
						
						
					}
					else {
						
						currentNum = Double.parseDouble(temp);
						prevNum = Double.parseDouble(prevTemp.substring(0, prevTemp.length() - 2));
						answer = compute(currentOperation, currentNum, prevNum);
						if(isInt(answer)) {
							currentNumber.setText("");
							previousNumber.setText(String.format("%.0f", answer) + " +");
						} 
						else {
							currentNumber.setText("");
							previousNumber.setText(answer + " +");
						}
						currentOperation = "+";
						
						
					}
					
					
					
				}
				else {
					
					if(!prevTemp.isEmpty() && temp.isEmpty()) {
						
						currentOperation = "+";
						previousNumber.setText(prevTemp.substring(0, prevTemp.length() - 1) + currentOperation);
						
					}
					else if(prevTemp.isEmpty() && !temp.isEmpty()) {
						
						currentOperation = "+";
						previousNumber.setText(temp + " " + currentOperation);
						currentNumber.setText("");
						
					}
					
					else {
						System.out.println("Error in adding part!");
					}
					
					
					
				}
				
				break;
				
				
				
			case "±":
				if(temp.length()<1) {
					currentNumber.setText("-");
				}
				else {
					char sign = '-';
					if(temp.charAt(0) != sign) {
						currentNumber.setText("-" + temp);
					}
					else {
						currentNumber.setText(temp.substring(1));
					}
				}
				break;
				
				
				
			case "=":
				
				
				
				if(!temp.isEmpty() && !prevTemp.isEmpty()) {
					currentNum = Double.parseDouble(temp);
					prevNum = Double.parseDouble(prevTemp.substring(0,prevTemp.length() - 2));
					answer = compute(currentOperation, currentNum, prevNum);
					if(isInt(answer)) {
						currentNumber.setText(String.format("%.0f", answer));
					 }else {
						 currentNumber.setText(answer + "");
					 }
					previousNumber.setText(prevTemp + " " + temp +  " =");
				}
				
				currentOperation = "=";
				break;
				
				
				
			case "0":
				if(!temp.isEmpty()) {
					currentNumber.setText(temp + "0");
				}
				
				break;
			case "1":
				currentNumber.setText(temp + "1");
				break;
			case "2":
				currentNumber.setText(temp + "2");
				break;
			case "3":
				currentNumber.setText(temp + "3");
				break;
			case "4":
				currentNumber.setText(temp + "4");
				break;
			case "5":
				currentNumber.setText(temp + "5");
				break;
			case "6":
				currentNumber.setText(temp + "6");
				break;
			case "7":
				currentNumber.setText(temp + "7");
				break;
			case "8":
				currentNumber.setText(temp + "8");
				break;
			case "9":
				currentNumber.setText(temp + "9");
				break;
			case ",":
				boolean flag = false;
				for(int i=0;i<temp.length();i++) {
					if(temp.charAt(i) == '.') {
						flag = true;
						break;
					}
					else {
						flag = false;
					}
				
				}
				if(!flag) {
					currentNumber.setText(temp + ".");
				}
				
				break;
			default:
				System.out.println("Error occured!");
				System.exit(0);
				
		
		
		}
		
	}
	
	public double compute(String operation, double currentNum, double previousNum) {
		
		if(operation != "=") {
			
			switch(operation) {
			
			case "+":
				return currentNum + previousNum;
			case "-":
				return previousNum - currentNum;
			case "x":
				return currentNum * previousNum;
			case "/":
				return previousNum / currentNum;
			default:
				return 0;
		
		
		}
			
		}
		else {
			return 0;
		}
		
		
	}
	
	public Calculator() {
		
		generateFrame();
		
	}



	@Override
	public void actionPerformed(ActionEvent evt) {
		
		JButton button = (JButton)evt.getSource();
		clicked(button.getText());
		
		
	}
	
	
	private class KeyHandler implements KeyListener{

		@Override
		public void keyTyped(KeyEvent evt) {
			
			
			
			char key = evt.getKeyChar();
			
			if(key == '1') {
				clicked("1");
			}
			else {
				for(String icon : icons) {
					if(icon.charAt(0) == key) {
						clicked(icon);
						break;
					}
				}
				
				if(key == '*') {
					clicked("x");
				}
			}
			
			
			
		}

		@Override
		public void keyPressed(KeyEvent evt) {
			
			if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
				clicked("=");
			}
			if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
				clicked("C");
			}
			if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				clicked("←");
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
}
