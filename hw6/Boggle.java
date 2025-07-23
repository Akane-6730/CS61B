import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.LinkedList;


/**
 * read the board
 * create and cache the dictionary trie
 * create Solver object for a game to solve
 */
public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "words.txt";
    private static Trie trie = null;

    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive.");
        }
        // build the dictionary trie
        buildDictTrie();

        // read the Boggle and build the board
        char[][] board = readBoard(boardFilePath);

        Solver solver = new Solver(board, k);

        return solver.solve();
    }


    /** Helper Methods for Boggle class */

    /** build the dictionary trie */
    private static void buildDictTrie() {
        In dictionary = new In(dictPath);
        trie = new Trie();
        String[] words = dictionary.readAllLines();
        for (String word : words) {
            if (word.length() >= 3) {
                trie.insert(word);
            }
        }
    }

    /**
     * read the board
     * @param boardFilePath
     * @return 2-dimension char array
     */
    private static char[][] readBoard(String boardFilePath) {
        In boardIn = new In(boardFilePath);
        String[] lines =  boardIn.readAllLines();
        int height = lines.length;
        int width = lines[0].length();
        char[][] board = new char[height][width];
        for (int i = 0; i < height; i++) {
            if (lines[i].length() != width) {
                throw new IllegalArgumentException("The input board is not rectangular!");
            }
            for (int j = 0; j < width; j++) {
                board[i][j] = lines[i].charAt(j);
            }
        }
        return board;
    }

    /**
     * private inner solver class
     */
    private static class Solver {
        // help to remove duplicates
        private final Set<String> allFoundWords;
        // help to select the k longest words
        private PriorityQueue<String> topKQueue;

        private final int k;
        // Boggle game board and it's size parameters
        private char[][] board;
        private int height;
        private int width;
        // mark matrix for dfs
        private boolean[][] visited;

        /**
         * constructor
         * @param board
         * @param k
         */
        public Solver(char[][] board, int k) {
            this.board = board;
            height = board.length;
            width = board[0].length;
            this.k = k;
            allFoundWords = new HashSet<>();
            topKQueue = new PriorityQueue<>((a, b) -> {
                if (a.length() != b.length()) {
                    return a.length() - b.length();
                }
                return b.compareTo(a);
            });
            visited = new boolean[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    visited[i][j] = false;
                }
            }
        }

        public List<String> solve() {
            LinkedList<String> result = new LinkedList<>();

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    dfs(i, j, trie, new StringBuilder());
                }
            }

            while (!topKQueue.isEmpty()) {
                result.addFirst(topKQueue.poll());
            }
            return result;
        }


        private void dfs(int i, int j, Trie t, StringBuilder s) {
            if (i < 0 || i >= height || j < 0 || j >= width || visited[i][j]) {
                return;
            }
            char c = board[i][j];
            Trie next = t.getChild(c);
            if (next == null) {
                return;
            }

            visited[i][j] = true;
            s.append(c);

            if (next.isKey()) {
                String word = s.toString();
                if (allFoundWords.add(word)) {
                    topKQueue.offer(word);
                    if (topKQueue.size() > this.k) {
                        topKQueue.poll();
                    }
                }

            }

            int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};

            for (int l = 0; l < 8; l++) {
                dfs(i + dy[l], j + dx[l], next, s);
            }

            visited[i][j] = false;
            s.deleteCharAt(s.length() - 1);
        }
    }


    public static void main(String[] args) {

        String boardFilePath = "exampleBoard.txt";
        List<String> result = Boggle.solve(7, boardFilePath);

        System.out.println("Found words:");
        for (String word : result) {
            System.out.println(word);
        }
    }
}
