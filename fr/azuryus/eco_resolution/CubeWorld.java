package fr.azuryus.eco_resolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CubeWorld extends EcoProblem {

	public CubeWorld(int nbCube) {
		super();
		
		Table table = new Table();
		
		List<Cube> cubes = new ArrayList<>();
		EcoAgent cubePrec = table;
		for(int i = 0; i<nbCube; i++) {
			Cube cube = new Cube("cube"+i, cubePrec, table);
			cubes.add(cube);
			cubePrec = cube;
		}
		
		Collections.shuffle(cubes);
		
		agents.add(table);
		for(Cube cube : cubes)
			agents.add(cube);
	}
	
	@Override
	public void solve() {
		int i = 0;
		while(!mondeSatisfait()) {
//			System.out.println("Etape " + i);
			for(EcoAgent agent : agents) {
				agent.exec();
			}
			i++;
//			System.out.println("");
		}
		
		System.out.println(i-1);
	}
	
	public static void main(String[] args) {
		new CubeWorld(3).solve();
		System.exit(0);
	}
}
