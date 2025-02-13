
import java.util.ArrayList;
import java.util.List;

public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);

        // Read the Huffman coding trie
        BinaryTrie huffmanTrie = (BinaryTrie) or.readObject();

        // Read the number of symbols
        int num = (int) or.readObject();

        // Read the massive bit sequence corresponding to the original txt
        BitSequence hugeBitSequence = (BitSequence) or.readObject();

        List<Character> symbols = new ArrayList<>();
        BitSequence remainingBits = hugeBitSequence;

        for (int i = 0; i < num; i += 1) {
            // Perform a longest prefix match on the massive sequence
            Match match =  huffmanTrie.longestPrefixMatch(remainingBits);
            // Record the symbol
            symbols.add(match.getSymbol());
            // Remove the matched bits from the remaining sequence
            remainingBits = remainingBits.allButFirstNBits(match.getSequence().length());
        }

        // Convert symbols to char array
        char[] decodedSymbols = new char[symbols.size()];
        for (int i = 0; i < symbols.size(); i += 1) {
            decodedSymbols[i] = symbols.get(i);
        }

        // Write the symbols to the specified file
        FileUtils.writeCharArray(args[1], decodedSymbols);
    }
}
