package visao;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Botao extends JButton{
	private String TextoExibir;
	private Color cor;
	
	public Botao( String TextoExibir, Color Cor) {
		setText(TextoExibir);
		setOpaque(true);
		setBackground(Cor);
		setForeground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setFont(new Font("courier",Font.PLAIN,30));
	}
	
}
