package controller;

import java.util.concurrent.Semaphore;

public class ThreadCavaleiro extends Thread {
	int idCavaleiro, velocidadeExtra;
	final int tamanhoCorredor = 2000;
	static boolean pedraColetada;
	static int  distanciaBonus = 500, portaSegura = (int)(Math.random()*4);
	int  mostrarKM = 100;
	Semaphore semaforoBonus, semaforoPorta;
	static boolean[] portaAberta = new boolean[4];
	
	public ThreadCavaleiro(int idCavaleiro, Semaphore semaforoBonus, Semaphore semafoPorta) {
		this.idCavaleiro = idCavaleiro;
		this.semaforoBonus = semaforoBonus;
		this.semaforoPorta = semafoPorta;
	}

	@Override
	public void run() {
		iniciarCorrida();
		try {
			semaforoPorta.acquire();
			entrarPorta();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaforoPorta.release();
		}		
	}


	private void iniciarCorrida() {
		System.out.println("Cavaleiro #"+ idCavaleiro + " iniciou a corrida");
		int distanciaPercorrida = 0;
		
		while(distanciaPercorrida < tamanhoCorredor) {
			int velocidadeCavaleiro = (int)(Math.random()*3)+2;
			distanciaPercorrida += (velocidadeExtra + velocidadeCavaleiro);
			
			if (distanciaPercorrida >= mostrarKM) {
				System.out.println("Cavaleiro #" + idCavaleiro + " correu " + mostrarKM + "m.");
				mostrarKM += 100;
			}
			
			if(distanciaPercorrida > distanciaBonus && !pedraColetada) {
				try {
					semaforoBonus.acquire();
					
					if(distanciaBonus == 500) {
						distanciaBonus = 1500;
						pegarBonus("tocha");
					}else {
						pedraColetada = true;
						pegarBonus("pedra");
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					semaforoBonus.release();
				}
			}
			
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void pegarBonus(String bonus) {
		System.out.println("Cavaleiro #" + idCavaleiro + " pegou a " + bonus);
		velocidadeExtra += 2;
	}

	private void entrarPorta() {
		int porta = (int)(Math.random()*4);
		while(portaAberta[porta]) {
			porta = (int)(Math.random()*4);
		}
		portaAberta[porta] = true;
		System.out.println("Cavaleiro #" + idCavaleiro + " entrou no corredor " + (porta+1));
		if(porta == portaSegura) {
			System.out.println("Cavaleiro #" + idCavaleiro + " terminou a corrida");
		}else {
			System.out.println("Cavaleiro #" + idCavaleiro + " foi morto por monstros");
		}
	}

}
