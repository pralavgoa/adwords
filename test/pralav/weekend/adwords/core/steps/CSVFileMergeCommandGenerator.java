package pralav.weekend.adwords.core.steps;

public class CSVFileMergeCommandGenerator {

    public static String srcHomeFolder = "/e/_Pralav/_Projects/Adwords/april_output";
    public static String destHomeFolder = "/e/_Pralav/_Projects/Adwords/april_output_campaign";

    public static String srcHomeEnv = "ADWORDS_SRC_HOME";
    public static String destHomeEnv = "ADWORDS_DEST_HOME";

    public static void main(String[] args) {

        System.out.println("export " + srcHomeEnv + "=" + srcHomeFolder);
        System.out.println("export " + destHomeEnv + "=" + destHomeFolder);

        System.out.println("cd $" + destHomeEnv);
        for (String campaignName : NameGenerator.campaignNames) {
            System.out.println("mkdir \"" + campaignName + "\"");
            for (int i = 1; i <= 4; i++) {
                if (i == 1) {
                    getCopyCommand(ReportGenerator.CONV_0 + ReportGenerator.CSV_EXT, campaignName, i);
                    getCopyCommand(ReportGenerator.CONV_1 + ReportGenerator.CSV_EXT, campaignName, i);
                    getCopyCommand(ReportGenerator.NEG_REC_FILE + ReportGenerator.CSV_EXT, campaignName, i);
                    getCopyCommand(ReportGenerator.UPLOAD_FILE + ReportGenerator.CSV_EXT, campaignName, i);
                } else {
                    getSedCommand(ReportGenerator.CONV_0 + ReportGenerator.CSV_EXT, campaignName, i);
                    getSedCommand(ReportGenerator.CONV_1 + ReportGenerator.CSV_EXT, campaignName, i);
                    getSedCommand(ReportGenerator.NEG_REC_FILE + ReportGenerator.CSV_EXT, campaignName, i);
                    getSedCommand(ReportGenerator.UPLOAD_FILE + ReportGenerator.CSV_EXT, campaignName, i);
                }
            }
        }
    }

    private static void getSedCommand(String reportName, String campaignName, int wc) {
        System.out.println("sed 1d \"$" + srcHomeEnv + "/" + wc + "/" + campaignName + "/" + reportName + "\" >> \"$"
                + destHomeEnv + "/" + campaignName + "/" + reportName + "\"");
    }

    private static void getCopyCommand(String reportName, String campaignName, int wc) {
        System.out.println("cp \"$" + srcHomeEnv + "/" + wc + "/" + campaignName + "/" + reportName + "\" \"$"
                + destHomeEnv + "/" + campaignName + "/" + reportName + "\"");
    }
}
