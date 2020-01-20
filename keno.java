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
		
		System.out.print("\nPlease load your wallet: $");
		int begWallet = Integer.parseInt(scn.nextLine());
		int wallet = begWallet;
		//initialize array for the drawing
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
		//reset winnings per draw (winnings) and winnings per card (totalWinnings)
		int winnings = 0;
		int totalWinnings = 0;		

		// Determine how many spots the player wants
		System.out.print("How many spots? (Min of 4, Max of 10): ");
		int spots = Integer.parseInt(scn.nextLine());	

		//Determine how many draws the player wants
		System.out.print("How many draws? (Min of 1, Max of 10): ");
		int playerDraws = Integer.parseInt(scn.nextLine());
		

		//take bet amount out of wallet
		System.out.print("How much to bet? $");
	       	int bet = Integer.parseInt(scn.nextLine());
		wallet = wallet - bet*playerDraws;	
		int[] playerNums = new int[spots];
		
		//Player chooses random numbers or their own picks
		System.out.print("Would you like to pick your own numbers? (Y/N): ");
		char select = scn.nextLine().charAt(0);

		if (select == 'Y'){
			System.out.println("Please enter your " + spots + " numbers:");
			for(int i = 0; i < spots; i++){
				playerNums[i] = scn.nextInt();
			}
			scn.nextLine();
			System.out.println("\nGot it. Playing these numbers: \n" + Arrays.toString(playerNums));
		}
		else if (select == 'N'){
			for (int i =0; i < spots; i++){
				int randPick = rand.nextInt(80) + 1;
				if(IntStream.of(playerNums).anyMatch(n -> n == randPick) == false){
					playerNums[i] = randPick;
				}
			}
			System.out.println("\nPicking for you. Your numbers are:\n" + Arrays.toString(playerNums));
		}
		else {
			System.out.println("You did not enter either Y or N. Goodbye");
		}
		
		//generate the draw
		int drawNum = 0;
		while (	playerDraws > 0 ){
			drawNum++;
			System.out.println("\nHere is draw #" + drawNum);
			int generated = 0;
			while (generated < 20){
				int randNum = rand.nextInt(80) + 1;
				if(IntStream.of(draws).anyMatch(y -> y == randNum) == false){
					draws[generated] = randNum;
					System.out.print(draws[generated] + " ");
					generated++;
					if (generated == 10)
						System.out.println();
				}
						
			}

			//match the numbers
			System.out.println("\n");

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
			System.out.println("\nYou got " + matchCount + " number(s)");
			
			//get winnings amount and add it to the wallet
			winnings = payout[spots-4][matchCount] * bet;
			System.out.println("You win $" + winnings);
			totalWinnings = totalWinnings + winnings;
			playerDraws--;
		}
		wallet = wallet + totalWinnings;
		System.out.println("Your wallet balance is $" + wallet);

		if (wallet <= 0){
			System.out.println("\nYou ran out of money");
			playAgain = 'N';
		}
		else { 
			System.out.print("\nPlay again? (Y/N): ");
			playAgain = scn.nextLine().charAt(0);
		}
		
	}
	System.out.println("\nThanks for playing.");
	if (wallet - begWallet > 0){
		System.out.println("You won $" + (wallet-begWallet));
	}
	else if (wallet - begWallet < 0){
		System.out.println("You lost $" + (begWallet-wallet));
	}
	else {
		System.out.println("You broke even");
	}	
	}
}


		
			
