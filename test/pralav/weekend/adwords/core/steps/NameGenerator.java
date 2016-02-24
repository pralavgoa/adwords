package pralav.weekend.adwords.core.steps;

public class NameGenerator {

    public static String[] campaignNames = { "JustAnswer", "JustAnswer 2", "JustAnswer 3", "JustAnswer 5",
            "Spanish JustAnswer", "Germany JustAnswer", "Canada JustAnswer", "UK JustAnswer", "Japan JustAnswer" };
    public static String[] campaignShortName = { "ja", "ja2", "ja3", "ja5", "spain", "germany", "canada", "uk", "japan" };

    public static void main(String[] args) {

        for (int i = 1; i <= 4; i++) {
            for (String name : campaignShortName) {
                System.out.println("CREATE INDEX `stm_agg_" + i + "_" + name + "_idx` ON `stm_agg_" + i + "_" + name
                        + "` (`campaign`);");
            }
        }

    }
}
