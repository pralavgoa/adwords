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

public class TableCollector {

    private final String inputFolderPath;
    private final SQLTemplateUtil sqlTemplate;

    public TableCollector(String inputFolderPath) throws IOException {
        this.inputFolderPath = inputFolderPath;
        this.sqlTemplate = new SQLTemplateUtil("sql");
    }

    public void collectData() {
        List<String> fileNames = FileUtilsPralav.getFilesInFolder(this.inputFolderPath);
        for (int i = 2; i <= 4; i++) {
            String stagingTableName = "stm_agg_staging_" + i;
            this.createStagingTable(stagingTableName);
            for (String fileName : fileNames) {
                String inputTableName = "stm_" + fileName + "_" + i + "_agg";
                System.out.println("Input table name:" + inputTableName);
                this.insertDataFromTable(inputTableName, stagingTableName);
            }
        }
    }

    private void insertDataFromTable(String inputTableName, String stagingTableName) {
        String templateFileName = "combine_agg_to_staging.sql";
        Map<String, Object> mapOfParameters = new HashMap<>();
        mapOfParameters.put("inputTableName", inputTableName);
        mapOfParameters.put("stagingTableName", stagingTableName);
        try {
            String sql = this.sqlTemplate.getSQLFromTemplate(mapOfParameters, templateFileName);
            this.executeOnDB(sql);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private void createStagingTable(String tableName) {
        String sql = "CREATE TABLE " + tableName + " LIKE `stm_agg_staging`";
        this.executeOnDB(sql);
    }

    private void executeOnDB(String sql) {

        try {
            DB.executeSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
