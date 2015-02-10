package pralav.weekend.adwords.core;

import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

public class TokensCollection {

    // word,campaign
    private final Table<String, String, SearchTermMetrics> table;

    public TokensCollection() {
        this.table = HashBasedTable.create();
    }

    public SearchTermMetrics get(String word, String campaign) {
        return this.table.get(word, campaign);
    }

    public void put(String word, String campaign, SearchTermMetrics searchTermMetrics) {
        this.table.put(word, campaign, searchTermMetrics);
    }

    public boolean contains(String word, String campaign) {
        return this.table.contains(word, campaign);
    }

    public int size() {
        return this.table.size();
    }

    public Set<Cell<String, String, SearchTermMetrics>> cellSet() {
        return this.table.cellSet();
    }
}
