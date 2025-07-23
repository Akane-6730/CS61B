import java.util.HashMap;
import java.util.Map;

public class Trie {


    private boolean isKey;
    private Map<Character, Trie> next;

    public Trie() {
        isKey = false;
        next = new HashMap<>();
    }

    public Trie getChild(char c) {
        if (next.containsKey(c)) {
            return next.get(c);
        }
        return null;
    }

    public void insert(String word) {
        Trie x = this;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (x.next.containsKey(c)) {
                x = x.next.get(c);
            } else {
                Trie next = new Trie();
                x.next.put(c, next);
                x = next;
            }
        }
        x.isKey = true;
    }

    public boolean isKey() {
        return isKey;
    }
}
