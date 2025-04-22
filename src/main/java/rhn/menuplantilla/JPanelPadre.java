package rhn.menuplantilla;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JPanelPadre extends JPanel{
	
	protected JButton cerrar;
	protected JButton minimizar;
	
	
	public JPanelPadre() {
		arrancar();
        setSize(900, 800);
        setLayout(new BorderLayout());
        setPreferredSize(new java.awt.Dimension(900,800));
        setMinimumSize(new java.awt.Dimension(400,600));
	}
	
	
	protected void arrancar() {
        crearBotones();	
	}

	
	
	
	
	protected void crearBotones() {
		cerrar = new JButton("x");
		cerrar.setFocusPainted(false); // Remueve el borde de enfoque al hacer clic
		cerrar.setMargin(new Insets(0, 0, 0, 0)); // Sin margen
		cerrar.setPreferredSize(new Dimension(40, 20));
		cerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPanelPadre.this);
                if (topFrame != null) {
                    topFrame.dispose();
                }
            }
        });

		minimizar = new JButton("-");
		minimizar.setFocusPainted(false); // Remueve el borde de enfoque al hacer clic
		minimizar.setMargin(new Insets(0, 0, 0, 0)); // Sin margen
		minimizar.setPreferredSize(new Dimension(40, 20));

		minimizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPanelPadre.this);
                if (topFrame != null) {
                    topFrame.setState(JFrame.ICONIFIED);
                }
            }
        });
		
		
		
		add(cerrar);
		add(minimizar);
	}
	
	
	
	
	
}
