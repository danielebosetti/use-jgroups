package sample;

import org.jgroups.demos.Draw;

public class Run_Draw {

	public static void main(String[] args) {
//		Draw.main(args);
		Draw.main(new String[]{"-props", "fast.xml"});
	}
}
