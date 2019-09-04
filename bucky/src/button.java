
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class button {
	
		button(){

			JFrame frame = new JFrame();
	
			JButton b = new JButton("Click");
	
			b.setBounds(50, 50, 90, 50);
			
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("pressed");
				}
				
			});
	
			frame.add(b);
	
			frame.setSize(300, 200);
	
			frame.setLayout(null);
	
			frame.setVisible(true);
	
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	
	
	}
		
	public static void main(String[] args) {
		
		new button();
		
	}
		
}
