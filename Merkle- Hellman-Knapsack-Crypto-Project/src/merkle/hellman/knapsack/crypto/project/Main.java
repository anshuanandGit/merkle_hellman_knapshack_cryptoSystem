package merkle.hellman.knapsack.crypto.project;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *This class handles testing of Merkel hellman knapsack crypto project
 * @author Anshu Anand
 */
public class Main {

    /**
     * This method is main driver for testing  Merkel hellman knapsack crypto project
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //ask for user's input:
        System.out.println("Enter a string and I will encrypt it as single large integer.");

        Scanner userInput = new Scanner(System.in);
        String inputLine = userInput.nextLine();

        //test if the length of input is larger than 80 characters,if so, ask users to reinput
        while (inputLine.length() > 80) {
            System.out.println("the String is too long,please input a string less than 80 characters");
            userInput = new Scanner(System.in);
            inputLine = userInput.nextLine();
        }
        //System.out.println(inputLine);
        System.out.println("Clear Text:");
        System.out.println(inputLine);
        System.out.println("Number of clear text bytes = " + inputLine.length());

        //encryption
        MerkleHellmanKnapsackCryptoProject crypto = new MerkleHellmanKnapsackCryptoProject();
        BigInteger encryptedNum = crypto.encryptData(inputLine);
        System.out.println(inputLine + " is encrypted as ");
        System.out.println(encryptedNum);

        //decryption
        String decryptedString = crypto.decryptData(encryptedNum);
        System.out.println("Result of decryption: " + decryptedString);
    }
}
