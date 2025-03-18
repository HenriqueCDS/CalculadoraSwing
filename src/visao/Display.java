package visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.Memoria;
import modelo.MemoriaObservador;

@SuppressWarnings("serial")
public class Display extends JPanel implements MemoriaObservador {
	private final JLabel label;
	
	public Display() {
		setBackground(new Color(46,49,50));
		
		Memoria.getInstacia().adicionarObservador(this);
		
		label = new JLabel(Memoria.getInstacia().getTextoAtual());
		
		
		label.setFont(new Font("courier",Font.PLAIN,30));
		label.setForeground(Color.WHITE);	
		
		setLayout(new FlowLayout(FlowLayout.RIGHT,10,25));
		add(label);
	}
	@Override
	public void valorAlterado(String novoValor) {
		label.setText(novoValor);
	}
}


