package pralav.weekend.adwords.core.steps;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pralav.weekend.database.DB;
import pralav.weekend.database.SQLTemplateUtil;
import pralav.weekend.utils.FileUtilsPralav;
import freemarker.template.TemplateException;

public class AggregateTableCreator {

    private final SQLTemplateUtil sqlTemplate;

    public AggregateTableCreator() throws IOException {
        this.sqlTemplate = new SQLTemplateUtil("sql");
    }

    public void createAggregateTable() {

    }

    public void combineAggregateTablesToStaging(List<String> tableNames, String stagingTableName) {
        try {
            String templateFileName = "combine_agg_to_staging.sql";
            for (String tableName : tableNames) {
                Map<String, Object> mapOfParameters = new HashMap<>();
                mapOfParameters.put("stagingTableName", stagingTableName);
                mapOfParameters.put("inputTableName", tableName);
                String sql = this.sqlTemplate.getSQLFromTemplate(mapOfParameters, templateFileName);
                System.out.println(sql);
                DB.executeSQL(sql);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        AggregateTableCreator atc = new AggregateTableCreator();
        File[] listOfFiles = FileUtilsPralav
                .getListOfFiles("C:\\_Pralav\\personal\\projects\\adwords\\april_data\\data");
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileNames.add("stm_" + listOfFiles[i].getName() + "_agg");
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        atc.combineAggregateTablesToStaging(fileNames, "stm_agg_staging");
    }
}
