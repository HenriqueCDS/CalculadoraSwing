package modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	
	private tipoDeComando ultimaOperacao = null;
	private boolean substituir = false;
	private String textoAtual = "";
	private String textoBuffer = "";
	
	private static  final Memoria instacia = new Memoria();
	
	private final List<MemoriaObservador> observadores = new ArrayList<>();
	
	private enum tipoDeComando {
		ZERAR,NUMERO,SOMAR,SUBTRAIR,MULTIPLICAR,DIVIDIR,IGUAL,VIRGULA,INVERTER
	}

	public static Memoria getInstacia() {
		return instacia;
	}

	public void adicionarObservador( MemoriaObservador observador) {
		observadores.add(observador);
	}
	
	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}


	public void setTextoAtual(String textoAtual) {
		this.textoAtual = textoAtual;
	}
	
	
	public void processarComando(String texto) {
		
		tipoDeComando tipoComando = detecarComando(texto);
		
		
		if(tipoComando == null) {
			
		}else if(tipoComando == tipoComando.ZERAR) {
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;
		}else if(tipoComando == tipoComando.NUMERO || tipoComando == tipoComando.VIRGULA) {
			textoAtual = substituir ? texto:textoAtual+texto;
			substituir = false;
		}else if(tipoComando == tipoComando.INVERTER && !textoAtual.isEmpty()) {
				textoAtual  =  textoAtual.contains("-") ? textoAtual.replace("-", ""):"-"+textoAtual;					
				substituir = false;
		} else {
			substituir = true;
			textoAtual = obterResultado(tipoComando);
			textoBuffer = textoAtual;
			ultimaOperacao = tipoComando;
		}
				
		observadores.forEach(observador -> observador.valorAlterado(getTextoAtual()));
		
	}


	private String obterResultado(tipoDeComando tipoComando) {

		if(ultimaOperacao == null || ultimaOperacao == tipoComando.IGUAL) {			
			return textoAtual;
		}
		double numeroAtual = Double.parseDouble(textoAtual.replace(",", "."));
		double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
		
		
		double resultado = 0;
		
		
		if(ultimaOperacao == tipoComando.SOMAR) {
			resultado = numeroBuffer + numeroAtual;	
		}else if(ultimaOperacao == tipoComando.SUBTRAIR) {
			resultado = numeroBuffer - numeroAtual;	
		}else if(ultimaOperacao == tipoComando.MULTIPLICAR) {
			resultado = numeroBuffer * numeroAtual;	
		}else if(ultimaOperacao == tipoComando.DIVIDIR) {
			resultado = numeroBuffer / numeroAtual;	
		}
		
		
		
		String resultadoString = Double.toString(resultado).replace(".", ",");
		boolean inteiro = resultadoString.endsWith(",0");
		
		return inteiro ? resultadoString.replace(",0","") : resultadoString;
	}


	private tipoDeComando detecarComando(String texto) {
		if(textoAtual.isEmpty() && texto == "0") {
			return null;
		}
		try {
			Integer.parseInt(texto);
			return tipoDeComando.NUMERO;
		} catch (NumberFormatException e) {
			if("AC".equals(texto)) {
				return tipoDeComando.ZERAR;	
			}else if("/".equals(texto)) {
				return tipoDeComando.DIVIDIR;	
			}else if("*".equals(texto)) {
				return tipoDeComando.MULTIPLICAR;	
			}else if("+".equals(texto)) {
				return tipoDeComando.SOMAR;	
			}else if("-".equals(texto)) {
				return tipoDeComando.SUBTRAIR;	
			}else if(",".equals(texto) && !textoAtual.contains(",")) {
				return tipoDeComando.VIRGULA;	
			}else if("=".equals(texto)) {
				return tipoDeComando.IGUAL;	
			}else if("+/-".equals(texto)) {
				return tipoDeComando.INVERTER;	
			}
		}
		return null;
			
	}
}
