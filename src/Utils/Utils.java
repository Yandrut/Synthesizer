package Utils;

public class Utils {
    public static void invokeProcedure (Procedure procedure, boolean printStackTrace) {
        try {
            procedure.invoke();
        }
        catch (Exception e) {
            if (printStackTrace) {
                e.printStackTrace();
            }
        }
    }
}
