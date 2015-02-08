package pralav.weekend.utils;

import java.util.Iterator;

public class NgramIterator implements Iterator<String> {

    String[] words;
    int pos = 0, n;

    public NgramIterator(int n, String str) {
        this.n = n;
        this.words = str.split("\\s");
    }

    @Override
    public boolean hasNext() {
        return this.pos < ((this.words.length - this.n) + 1);
    }

    @Override
    public String next() {
        StringBuilder sb = new StringBuilder();
        for (int i = this.pos; i < (this.pos + this.n); i++) {
            sb.append((i > this.pos ? " " : "") + this.words[i]);
        }
        this.pos++;
        return sb.toString();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
