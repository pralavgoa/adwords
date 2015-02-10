package pralav.weekend.adwords.core;

import org.junit.Test;

public class TokensCollectionTest {

    @Test
    public void testTokensCollection() {
        TokensCollection tc = new TokensCollection();
        tc.put("Hello", "World", new SearchTermMetrics("World", "Hello", 1, 1, 1, 1, 1));

        SearchTermMetrics stm = tc.get("Hello", "World");
        System.out.println(stm.getCampaign() + stm.getClicks());
    }
}
