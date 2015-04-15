package pralav.weekend.adwords.core.steps;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pralav.weekend.database.DB;
import pralav.weekend.database.SQLTemplateUtil;
import pralav.weekend.utils.FilePathUtils;
import pralav.weekend.utils.FileUtilsPralav;
import freemarker.template.TemplateException;

public class ReportGenerator {

    private static final String CSV_EXT = ".csv";
    private static final String CONV_0 = "JustAnswer_0";
    private static final String CONV_1 = "JustAnswer_1";
    private static final String UPLOAD_FILE = "JustAnswer_Marin_Upload_File";
    private static final String NEG_REC_FILE = "JustAnswer_Negatives_Recommendation";
    private static final String CAMPAIGN_FOLDER = "campaign";

    private final String aggDataTableName;
    private final String outputDirectory;
    private final SQLTemplateUtil sqlTemplate;

    public ReportGenerator(String aggDataTableName, String outputDirectory) throws IOException {
        this.aggDataTableName = aggDataTableName;
        this.outputDirectory = outputDirectory;
        this.sqlTemplate = new SQLTemplateUtil(FilePathUtils.getFolderPath("sql", "types"));
    }

    public void generateReport() {
        this.LOG("Generating report for " + this.aggDataTableName);
        for (String accountName : this.getAccountNames(this.aggDataTableName)) {
            this.createDirectory(accountName);
            this.generateZeroConversionsFile(accountName);
            this.generateNonZeroConversionsFile(accountName);
            this.generateMarinUploadFile(accountName);
            this.generateNegRecFile(accountName);
            for (String campaignName : this.getCampaignNames(accountName, this.aggDataTableName)) {
                this.generateZeroConversionsFile(accountName, campaignName);
                this.generateNonZeroConversionsFile(accountName, campaignName);
            }
        }
        this.LOG("Finished generating report for " + this.aggDataTableName);
    }

    private void generateZeroConversionsFile(String accountName, String campaignName) {
        this.generateConversionsFileForCampaigns(accountName, campaignName, 0);
    }

    private void generateNonZeroConversionsFile(String accountName, String campaignName) {
        this.generateConversionsFileForCampaigns(accountName, campaignName, 1);
    }

    private List<String> getCampaignNames(String accountName, String aggDataTableName) {
        List<String> campaigns = null;
        try {
            campaigns = DB.selectOneColumn("select distinct campaign from " + aggDataTableName + " where account='"
                    + accountName + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return campaigns;
    }

    private void generateNegRecFile(String accountName) {
        String outputFileName = NEG_REC_FILE + CSV_EXT;
        String templateFileName = "neg_rec.sql";
        this.generateSpecialUploadFiles(accountName, outputFileName, templateFileName);
    }

    private void generateMarinUploadFile(String accountName) {
        String outputFileName = UPLOAD_FILE + CSV_EXT;
        String templateFileName = "marin_upload.sql";
        this.generateSpecialUploadFiles(accountName, outputFileName, templateFileName);
    }

    private void generateSpecialUploadFiles(String accountName, String outputFileName, String templateFileName) {
        String outputFilePath = FilePathUtils.getFilePathForDB(this.outputDirectory, accountName, outputFileName);
        String mainWhereClause = "";
        String addWhereClause = "";
        this.generateOutputFile(accountName, outputFilePath, templateFileName, mainWhereClause, addWhereClause);
    }

    private void generateNonZeroConversionsFile(String accountName) {
        this.generateConverstionsFileForAccounts(accountName, 1, "");
    }

    private void generateZeroConversionsFile(String accountName) {
        this.generateConverstionsFileForAccounts(accountName, 0, "");
    }

    private void generateConversionsFileForCampaigns(String accountName, String campaignName, int nonZero) {
        String addWhereClause = "AND campaign='" + campaignName + "'";
        String fileName = campaignName.replaceAll("\\W+", "_") + ((nonZero == 0) ? "_0" : "_1") + CSV_EXT;
        String outputFilePath = FilePathUtils.getFilePathForDB(this.outputDirectory, accountName, CAMPAIGN_FOLDER,
                fileName);
        this.generateConversionsFile(accountName, outputFilePath, nonZero, addWhereClause);
    }

    private void generateConverstionsFileForAccounts(String accountName, int nonZero, String addWhereClause) {
        String outputFileName = ((nonZero == 0) ? CONV_0 : CONV_1) + CSV_EXT;
        String outputFolderPath = this.outputDirectory;
        String outputFilePath = FilePathUtils.getFilePathForDB(outputFolderPath, accountName, outputFileName);
        this.generateConversionsFile(accountName, outputFilePath, nonZero, addWhereClause);
    }

    private void generateConversionsFile(String accountName, String outputFilePath, int nonZero, String addWhereClause) {
        this.LOG("Generating " + outputFilePath + " file");
        String templateFileName = "conversions.sql";
        String mainWhereClause = (nonZero == 0) ? "converted_clicks=0" : "converted_clicks>0";
        this.generateOutputFile(accountName, outputFilePath, templateFileName, mainWhereClause, addWhereClause);
    }

    private void generateOutputFile(String accountName, String outputFilePath, String templateFileName,
            String mainWhereClause, String addWhereClause) {
        Map<String, Object> mapOfParameters = new HashMap<>();
        mapOfParameters.put("dataTableName", this.aggDataTableName);
        mapOfParameters.put("outputFile", outputFilePath);
        mapOfParameters.put("mainWhereClause", mainWhereClause);
        mapOfParameters.put("accountName", accountName);
        mapOfParameters.put("addWhereClause", addWhereClause);
        String sql;
        try {
            sql = this.sqlTemplate.getSQLFromTemplate(mapOfParameters, templateFileName);
            this.LOG("SQL: " + sql);
            this.executeOnDB(sql);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAccountNames(String aggDataTableName) {
        List<String> accounts = null;
        try {
            accounts = DB.selectOneColumn("select distinct account from " + aggDataTableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public void createDirectory(String name) {
        FileUtilsPralav.makeDir(this.outputDirectory, name);
        FileUtilsPralav.makeDir(FilePathUtils.getFolderPath(this.outputDirectory, name), CAMPAIGN_FOLDER);
    }

    private void executeOnDB(String sql) {
        try {
            DB.executeSQL(sql);
        } catch (SQLException e) {
            this.LOG("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public final void LOG(String log) {
        // System.out.println(log);
        FileUtilsPralav.appendToFile(FilePathUtils.getFilePath(this.outputDirectory, "run.log"), log + "\n");
    }
}
