import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    /* map characters to their counts */
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> frequencyTable = new HashMap<>();
        for (char c : inputSymbols) {
            frequencyTable.merge(c, 1, Integer::sum);
        }
        return frequencyTable;
    }

    public static void main(String[] args) {
        // Step 1: Read file as 8-bit symbols
        char[] inputSymbols = FileUtils.readFile(args[0]);

        // Step 2: Build frequency table
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);

        // Step 3: use frequency table to construct a binary decoding trie
        BinaryTrie huffmanTrie = new BinaryTrie(frequencyTable);

        // Step 4: Write the binary decoding trie to the .huf file
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(huffmanTrie);

        // Step 5 (optional): Write number of symbols to .huf file
        ow.writeObject(inputSymbols.length);

        // Step 6: Use binary trie to create lookup table for encoding
        Map<Character, BitSequence> lookupTable = huffmanTrie.buildLookupTable();

        // Step 7: Create list of bit sequences
        List<BitSequence> bitSequences = new ArrayList<>();
        for (char c : inputSymbols) {
            bitSequences.add(lookupTable.get(c));
        }

        // Step 8: Assemble all bit sequences into one huge bit sequence
        BitSequence hugeBitSequence = BitSequence.assemble(bitSequences);

        // Step 9: Write huge bit sequence to .huf file
        ow.writeObject(hugeBitSequence);
    }
}
