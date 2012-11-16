import java.awt.Color;
import java.util.*;



//Class: Overlay
//represents the mine field
public class Overlay {
	

	
	//class variables
	Block[][] layout;
	int width, height, amountOfMines;
	
	
	//Method: constructor
	public Overlay(int width, int height, int amountOfMines){
		this.width = width;
		this.height = height;
		this.amountOfMines = amountOfMines;
		
		intit();
	}
	
	public void intit(){
		layout = new Block[height][width];
		
		for(int i = 0; i < width; i++){
			for(int x = 0; x < height; x++){
				layout[x][i] = new Block(0);
			}
			
		}
		Random rand = new Random();
		
		//place the mines at random locations
		for(int i = amountOfMines; i > 0; i--){
			int r = rand.nextInt(height);
			int c = rand.nextInt(width);
			while(layout[r][c].value == 9){
				 r = rand.nextInt(height);
				 c = rand.nextInt(width);
			}
			layout[r][c] = new Block(9);
		}
		
		//iteration: bravo
		//go through all the blocks and assign number values
		for(int r = 0; r < height; r++){
			for(int c = 0; c < width; c++){
				
				//check adj blocks, as long as we are not in the first row or column
				
					for(int x = -1; x <= 1; x++){
						for(int y = -1; y <= 1; y++){
							//System.out.print("debug");
							if(r + x >= 0 && r + x < height && c + y >= 0 && c + y < width){
								//if the block is not checking itself
								if(x != 0 || y != 0){
									if(layout[r + x][c + y].value == 9 && layout[r][c].value != 9)
										layout[r][c].value++;
									//System.out.println(layout[r][c].value);
								}
							
							}
					}
				}
				
				
				
			}
		}//end of iteration: bravo
		
	}
	
	
	
	public class Block{
		public int value;
		public boolean isClicked, isFlagged;
		public Color myColor;
		
		Block(int val){
			value = val;
			isClicked = false;
			
		}
		
		public Color getColor(){
			if(value == 0)
				return new Color(255, 255, 255);
			else if(value == 1)
				return new Color(0, 255, 0);
			else if(value == 2)
				return new Color(255, 255, 0);
			else if(value == 3)
				return new Color(255, 180, 0);
			else if(value == 4)
				return new Color(255, 113, 0);
			else if(value == 5)
				return new Color(255, 116, 116);
			else if(value == 6)
				return new Color(255, 116, 185);
			else if(value == 7)
				return new Color(255, 67, 0);
			else if(value == 8)
				return new Color(255, 0, 0);
			else if(value == 9)
				return Color.black;
			else
				return new Color(100, 149, 237);
		}
	}
}
