package pralav.weekend.utils;

import org.junit.Test;

public class NgramIteratorTest {

    @Test
    public void testNgramIterator() {
        NgramIterator ngram = new NgramIterator(3, "hello world");

        while (ngram.hasNext()) {
            System.out.println(ngram.next());
        }

    }

}
