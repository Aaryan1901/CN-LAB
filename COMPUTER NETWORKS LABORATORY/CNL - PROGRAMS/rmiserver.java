import java.rmi.*;

public class rmiserver {
    public static void main(String args[]) {
        try {
            serverimpl m = new serverimpl();
            Naming.rebind("rmi://localhost:5000/abc", m);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
