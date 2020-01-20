import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.stream.IntStream;

public class keno {
	public static void main(String[] args){
		Random rand = new Random();
		Scanner scn = new Scanner(System.in);
	
		System.out.println("\n /$$   /$$ /$$$$$$$$ /$$   /$$  /$$$$$$ ");
		System.out.println("| $$  /$$/| $$_____/| $$$ | $$ /$$__  $$");
		System.out.println("| $$ /$$/ | $$      | $$$$| $$| $$  \\ $$");
		System.out.println("| $$$$$/  | $$$$$   | $$ $$ $$| $$  | $$");
		System.out.println("| $$  $$  | $$__/   | $$  $$$$| $$  | $$");
		System.out.println("| $$\\  $$ | $$      | $$\\  $$$| $$  | $$");
		System.out.println("| $$ \\  $$| $$$$$$$$| $$ \\  $$|  $$$$$$/");
		System.out.println("|__/  \\__/|________/|__/  \\__/ \\______/ ");
		System.out.println("\nPlease load your wallet: ");
		int wallet = Integer.parseInt(scn.nextLine());
		
		int[] draws = new int[20];
		
		//Payout array. Each amount represents wiinnings per match [0] is 0 matches
		int[][] payout = new int[][]{
			{ 0, 0, 1, 5, 72 }, //4 spot
			{ 0, 0, 0, 2, 18, 410 }, //5 spot
			{ 0, 0, 0, 1, 7, 57, 1000 }, //6 spot
			{ 0, 0, 0, 1, 5, 11, 100, 2000 }, //7 spot
			{ 0, 0, 0, 0, 2, 15, 50, 300, 10000 }, //8 spot
			{ 0, 0, 0, 0, 2, 5, 20, 100, 2000, 25000 }, // 9 spot
			{ 5, 0, 0, 0, 0, 2, 10, 50, 500, 5000, 100000 } //10 spot
		};

	char playAgain = 'Y';
	while (playAgain == 'Y'){

		// Determine how many spots the player wants
		System.out.println("How many spots? (Min of 4, Max of 10)");
		int spots = Integer.parseInt(scn.nextLine());	
	
		System.out.println("How much to bet per spot?");
	       	int bet = Integer.parseInt(scn.nextLine());
		wallet = wallet - bet*spots;	
		int[] playerNums = new int[spots];
		
		//Player chooses random numbers or their own picks
		System.out.println("Would you like to pick your own numbers? (Y/N)");
		char select = scn.nextLine().charAt(0);

		if (select == 'Y'){
			System.out.println("Please enter your " + spots + " numbers:");
			for(int i = 0; i < spots; i++){
				playerNums[i] = scn.nextInt();
			}
			System.out.println("Got it. Playing these numbers: " 
					+ Arrays.toString(playerNums));
		}
		else if (select == 'N'){
			
			for (int i =0; i < spots; i++){
				int randPick = rand.nextInt(80) + 1;
				if(IntStream.of(playerNums).anyMatch(n -> n == randPick) == false){
					playerNums[i] = randPick;
				}
			}
			System.out.println("Picking for you. Your numbers are:" 
					+ Arrays.toString(playerNums));
		}
		else {
			System.out.println("You did not enter either Y or N. Goodbye");
		}
		System.out.println("Here is the draw:");
			//Generate the 20 numbers to match to 
		for (int i =0; i < 20; i++){
			int randNum = rand.nextInt(80) + 1;
			if(IntStream.of(draws).anyMatch(y -> y == randNum) == false){
				draws[i] = randNum;
				System.out.print(draws[i] + " ");
			}
					
		}

		//match the numbers
		System.out.println();

		int matchCount = 0;
		for (int i = 0; i < spots; i++){
			//the lambda thing needs this final thing for some reason idk
			final int idk = playerNums[i];
			
			if(IntStream.of(draws).anyMatch(z -> z == idk)== true){
				System.out.println(playerNums[i] + " is a match!");
				matchCount++;
			}
			else {
				System.out.println(playerNums[i] + " is not a match.");
			}
		}
		System.out.println("You got " + matchCount + " number(s)");
		
		int winnings = payout[spots-4][matchCount] * bet;
		System.out.println("You win $" + winnings);
		wallet = wallet + winnings;
		System.out.println("Your wallet balance is " + wallet);

		if (wallet <= 0){
			System.out.println("You ran out of money");
			playAgain = 'N';
		}
		else { 
			System.out.println("Play again? (Y/N)");
			playAgain = scn.nextLine().charAt(0);
		}
		
	}
	}
}


		
			
