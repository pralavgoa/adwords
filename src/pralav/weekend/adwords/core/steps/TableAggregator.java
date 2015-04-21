package pralav.weekend.adwords.core.steps;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pralav.weekend.database.DB;
import pralav.weekend.database.SQLTemplateUtil;
import pralav.weekend.utils.FileUtilsPralav;
import freemarker.template.TemplateException;

public class TableAggregator {

    private final String inputFolderPath;
    private final SQLTemplateUtil sqlTemplate;

    public TableAggregator(String inputFolderPath) throws IOException {
        this.inputFolderPath = inputFolderPath;
        this.sqlTemplate = new SQLTemplateUtil("sql");
    }

    public void createAggregateTablePerInputTable() {
        // get file names
        List<String> fileNames = FileUtilsPralav.getFilesInFolder(this.inputFolderPath);
        // for each combination
        for (String fileName : fileNames) {
            for (int i = 2; i <= 4; i++) {
                String inputTableName = fileName + "_" + i;
                // --run sql to aggregate to table
                System.out.println("Input table name: " + inputTableName);
                this.generateTable(inputTableName);
            }
        }
    }

    private void generateTable(String inputTableName) {
        String templateFileName = "create_agg_table.sql";
        Map<String, Object> mapOfParameters = new HashMap<>();
        mapOfParameters.put("fileName", inputTableName);
        try {
            String sql = this.sqlTemplate.getSQLFromTemplate(mapOfParameters, templateFileName);
            this.executeOnDB(sql);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private void executeOnDB(String sql) {
        try {
            DB.executeSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
