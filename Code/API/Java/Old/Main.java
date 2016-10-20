import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException{
		TransientCreator tc = new TransientCreator();
		ArrayList<Transient> transients = tc.getTransients();
		for(Transient t: transients){
			System.out.println(t.toString());
		}
	}
}
