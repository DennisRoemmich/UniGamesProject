package prototype;

import java.util.Scanner;

public class TicTacToeTest
{
	
	public static void oldmain()
	{
		//Size  of the TicTacToe
		int size=3;
		
		//Game map
		char[][] map = new char[size][size];
		for(int i = 0; i < 3; i++) 
		{
			for(int j = 0; j < 3; j++) 
			{
				map[i][j] = '_';
			}
		}
		
		//Prints the tutorial map and game map
		printTutorialMap(map);
		printMap(map);
		
		Scanner readInput = new Scanner(System.in);
		//Game input initialization
		
		int[] prevInputs = new int[9];
		for(int move=0; move<9; move++) 
		{
			gameMove(map, validInput(map, readInput, move, prevInputs), move);
			printMap(map);
		}
		
		readInput.close();
		
	}
	
		public static int validInput(char[][]map, Scanner readInput, int move, int[] prevInputs) 
		{
			try 
			{
				int input  = readInput.nextInt();
			
				if(input<1 || input>9) 
				{
					System.out.println("Error! Please choose a field between 1 and 9.");
					validInput(map, readInput, move, prevInputs);
					return -1;
				
				}

				for(int i=0; i<9; i++)
				{
					if(prevInputs[i]==input) 
					{
						System.out.println("Error! Please choose another field.");
						validInput(map, readInput, move, prevInputs);
						return -1;
					}
				}
				

				prevInputs[move]=input;
				return input;
			}
			catch(Exception e)
			{
				System.out.println("Invalid input! Please enter a number between 1 and 9.");
				readInput.next();
				validInput(map, readInput, move, prevInputs);
				return -1;
			}
		}
		
		
	
	public static void gameMove(char[][] map, int input, int move)
		{
			if(input == -1) 
			{
				return;
			}
				
			if(move%2 == 0) 
				{
					switch(input) 
					{
						case 1:
							map[0][0]= 'o';
							break;
							case 2:
							map[0][1]= 'o';
							break;
						case 3:
							map[0][2]= 'o';
							break;
						case 4:
							map[1][0]= 'o';
							break;
						case 5:
							map[1][1]= 'o';
							break;
						case 6:
							map[1][2]= 'o';
							break;
						case 7:
							map[2][0]= 'o';
							break;
						case 8:
							map[2][1]= 'o';
							break;
						case 9:
							map[2][2]= 'o';
							break;
						default:
							break;
						}
					}
				if((move & 1) == 1) 
				{
					switch(input) 
					{
						case 1:
							map[0][0]= 'x';
							break;
						case 2:
							map[0][1]= 'x';
							break;
						case 3:
							map[0][2]= 'x';
							break;
						case 4:
							map[1][0]= 'x';
							break;
						case 5:
							map[1][1]= 'x';
							break;
						case 6:
							map[1][2]= 'x';
							break;
						case 7:
							map[2][0]= 'x';
							break;
						case 8:
							map[2][1]= 'x';
							break;
						case 9:
							map[2][2]= 'x';
							break;
						default:
							break;
						}
				}
			}

		
		
	
	/**
	 * Prints the game map with numbers instead of empty spaces
	 * @param map
	 */
	public static void printTutorialMap(char[][] map)  
	{
		//Index variables for columns and rows
		int rowIndex;
		int columnIndex;
		int nummer=1;
		
		//Iterate through the rows
		for (rowIndex = 0; rowIndex < 3; rowIndex++)
		{
			//Iterate through the columns
			for (columnIndex = 0; columnIndex < 3; columnIndex++)
			{
				//Print selected value
				System.out.print(nummer+" ");
				nummer++;
				
			}
			//Print on a new line after each row
			System.out.println("");
		}	
	}
	
	/**
	 * Prints the game map
	 * @param map
	 */
	public static void printMap(char[][] map)  
	{
		//Index variables for columns and rows
		int rowIndex;
		int columnIndex;
		
		System.out.println("");
		
		//Iterate through the rows
		for (rowIndex = 0; rowIndex < 3; rowIndex++)
		{
			//Iterate through the columns
			for (columnIndex = 0; columnIndex < 3; columnIndex++)
			{
				//Print selected value
				System.out.print(map[rowIndex][columnIndex]+" ");
				
			}
			//Print on a new line after each row
			System.out.println("");
		}	
		System.out.println("");
	
	}
	

}
