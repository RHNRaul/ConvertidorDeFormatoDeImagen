package rhn.menuplantilla;

import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;

public class JFramePadre extends JFrame{

	
	public JFramePadre() {
		arrancar();
        setSize(900, 800);
        setPreferredSize(new java.awt.Dimension(900,800));
        setMinimumSize(new java.awt.Dimension(400,600));
        setLocationRelativeTo(null);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50));
        
	}
	
	protected void arrancar() {
		
		
	}
	
	
	
	
	

}
