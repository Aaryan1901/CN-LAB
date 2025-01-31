import java.rmi.*;
import java.rmi.server.*;

public class serverimpl extends UnicastRemoteObject implements serverint {
    public serverimpl() throws Exception {
    }

    public int fact(int n) {
        int i, c = 1;
        for (i = 1; i <= n; i++) {
            c = i * c;
        }
        return c;
    }
}
