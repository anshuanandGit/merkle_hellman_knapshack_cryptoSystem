package merkle.hellman.knapsack.crypto.project;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class is implementation of  Merkel Hellman knapsack crypto project.
 * @author Anshu Anand
 */
public class MerkleHellmanKnapsackCryptoProject {
    //this holds random value
    private final static int RANDOMSOLUTION = 10;
     //this holds numbers of nodes
    private final static int NODE_LIMIT = 640;
    //SinglyLinkedList list to hold w, a private key
    private SinglyLinkedList w;
    //SinglyLinkedList list to hold public key
    private SinglyLinkedList b;
    private BigInteger q;
    private BigInteger r;

    /**
     * Constructor when calling the constructor, creates a key b. The key will
     * not change for the encryption and decryption this time
     */
    public MerkleHellmanKnapsackCryptoProject() {
        
        //Private key geneartion....Super increasing sequence
        //Generate Superincreasing sequence
        SinglyLinkedList seq = new SinglyLinkedList();
        //get random numbers
        SecureRandom srValue = new SecureRandom();

        for (int i = 0; i < NODE_LIMIT; i++) {
            BigInteger sumBfr = new BigInteger("1");
            for (int j = 0; j < i; j++) {
                //create new node
                ObjectNode mm = seq.getObjectAt(j);
                //get sum
                sumBfr = sumBfr.add((BigInteger) mm.getData());
            }
            //generate big int
            BigInteger bigInt = new BigInteger(String.valueOf(srValue.nextInt(RANDOMSOLUTION)));
            //add up the sum
            BigInteger newAdd = sumBfr.add(bigInt);
            //Add the node ....
            seq.addAtEndNode(newAdd);
        }
        //Assign the genearted sequence to list w
        w = seq;

        // get the sum value of W, this will be used to get value of q
        BigInteger sum = new BigInteger("0");
        for (int i = 0; i < seq.CountNodes(); i++) {
            //get each node
            ObjectNode nn = seq.getObjectAt(i);
            //get data
            BigInteger bigInt = (BigInteger) nn.getData();
            //sum up the value.
            sum = sum.add(bigInt);
        }

        //Get a number for q , which would be prime 
        //and greater than the sum of Sequence w
        q = sum.nextProbablePrime();
        

        //step4:get r
        int bitLength = q.bitLength();
        int random;

        do {
            random = srValue.nextInt(bitLength) + 1;//0<= sr.nextInt(bitLength)<bitLength
            r = BigInteger.probablePrime(bitLength, srValue);

        } while (q.compareTo(r) != 1);

        // Step5:genetrate b
        SinglyLinkedList publicKey = new SinglyLinkedList();
        for (int i = 0; i < seq.CountNodes(); i++) {
            //get node value
            ObjectNode pp = seq.getObjectAt(i);
            //get data from node
            BigInteger element = (BigInteger) pp.getData();
            //add the new node in public key
            //use multiply(r).mod(q) method
            publicKey.addAtEndNode(element.multiply(r).mod(q));
        }
        //public key is obtained for encryption
        b = publicKey;
        
    }

    /**
     * preconditions: s is a string. Crypto instance is correctly built so as to
     * call this method. postconditions:encrypt the user's input string to
     * return a BigInteger encrypted number.
     *
     * @param s the user's input string
     * @return a BigInteger as the encrypted number Big-Theta(N^2) for any cases
     */
    public BigInteger encryptData(String s) {
        //Convert Input text data to binary data format
        String bits = ConvertToBinary(s);
        // for each num in bits,multiply by b's each element
        BigInteger encryptedNum = BigInteger.ZERO;
        for (int i = 0; i < bits.length(); i++) {
            //get bit value
            String c = "" + String.valueOf(bits.charAt(i));
            //get respective node data to multiply to bit value
            ObjectNode x =b.getObjectAt(i);
            BigInteger a1 = (BigInteger) x.getData() ;
            //multiply value
            BigInteger a2 = a1.multiply(new BigInteger(c));
            //get the encrypted value
            encryptedNum = encryptedNum.add(a2);
        }
        return encryptedNum;

    }

    /**
     * precondition: big should be BigInteger type. postcondition:this method
     * decrypts a BigInteger encrypted number to the user's input string.
     *
     * @param big is the encrypted number that needs to be decrypted
     * @return the string that the user input. Big-Theta(1) for any cases
     */
    public String decryptData(BigInteger big) {
        //hold decrypted bits
        String decryptBits = "";
        //hold decrypted string
        String decrypString = "";
        
        //get the big integer for decomposition
        BigInteger decomp = r.modInverse(q).multiply(big).mod(q).mod(q);
        
        BigInteger tmp = decomp;
        //apply private key on data
        //obtaine the decrypted bits ...........
        for (int i = w.CountNodes()- 1; i >= 0; i--) {
            //get node value
            ObjectNode y = w.getObjectAt(i);
            if (tmp.compareTo((BigInteger) y.getData()) != -1) {
                if (tmp != BigInteger.ZERO) {
                    tmp = tmp.subtract((BigInteger) y.getData());
                }
                //assign value
                decryptBits = "1" + decryptBits;
            } else {
                //assign value
                decryptBits = "0" + decryptBits;
            }
        }
        //convert binary data to text
        String ee;
        for (int i = 0; i < decryptBits.length(); i = i + 8) {
            
            ee = decryptBits.substring(i, i + 8);
            if ("00000000".equals(ee)) {
                break;
            } else {
                //obtain decrpted string value
                decrypString += new Character((char) Integer.parseInt(ee, 2)).toString();
            }

        }
        //return decrypted data
        return decrypString;
    }

    /**
     * precondition:input should be string postcondition: convert the input
     * string into binary represented string. For instance, convert "D" to
     * '010000100'
     *
     * @param input is the text that user inputs.
     * @return a binary string Big-Theta(N) for any cases
     */
    public String ConvertToBinary(String input) {
        //string to hold byte string value
        String byteStringValue;
        //string to hold final binary data
        String finalStringValue = "";
        int diff;
        //read the character of input string one by one and convert to binary
        for (int i = 0; i < input.length(); i++) {
            //get the character at each string position
            String c = "" + input.charAt(i);
            //get the byte value as big integer
            BigInteger s2 = new BigInteger(c.getBytes());
            //asign the byte value as string
            byteStringValue = s2.toString(2);
             
            //obtain the difference of byte value with respect to 8 bits value
            diff = 8 - byteStringValue.length();
            //if diff is not zero
            while (diff != 0) {
                //concatinate 0 as prefix to  make it 8 bit value
                byteStringValue = "0" + byteStringValue;
                diff = 8 - byteStringValue.length();
            }
            //append the bytestring on final string value.
            finalStringValue += byteStringValue;

        }

        return finalStringValue;
    }
}
