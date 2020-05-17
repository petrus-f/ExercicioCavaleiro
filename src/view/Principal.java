package view;

import java.util.concurrent.Semaphore;

import controller.ThreadCavaleiro;

public class Principal {

	public static void main(String[] args) {
		
		
		Semaphore semaforoBonus = new Semaphore(1);
		Semaphore semaforoPorta = new Semaphore(1);
		
		for(int i = 1; i <= 4 ; i++) {
			ThreadCavaleiro t = new ThreadCavaleiro(i, semaforoBonus, semaforoPorta);
			t.start();
		}
	}

}
