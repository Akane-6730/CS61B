public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        int length = word.length();
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < length; i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        deque = wordToDeque(word);
        int size = deque.size();
        for (int i = 0; i < size / 2; i++) {
            if (deque.get(i) != deque.get(size - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = new ArrayDeque<>();
        deque = wordToDeque(word);
        int size = deque.size();
        for (int i = 0; i < size / 2; i++) {
            if (!cc.equalChars(deque.get(i), deque.get(size - 1 - i))) {
                return false;
            }
        }
        return true;
    }
}
