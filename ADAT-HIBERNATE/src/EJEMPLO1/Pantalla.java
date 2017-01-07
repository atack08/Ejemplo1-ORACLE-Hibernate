package EJEMPLO1;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent; import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Iterator; 
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import PRIMERO.Departamentos;
import PRIMERO.Empleados;
import PRIMERO.SessionFactoryBuilder;

public class Pantalla extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JLabel etiqueta = new JLabel("");
	JLabel label = new JLabel("Introduce N° de departamento:");
	JTextField depar = new JTextField(2);
	JButton boton = new JButton("Consultar");

	// contenedor para botones
	public void iniciar() {
		getContentPane().setLayout(new GridLayout(10, 1));
		// contenedor para botones
		JPanel panelBotones = new JPanel(new FlowLayout());
		panelBotones.add(label);
		panelBotones.add(depar);
		panelBotones.add(boton);
		getContentPane().add(panelBotones);
		getContentPane().add(etiqueta);
		setVisible(true);
		pack();
		boton.addActionListener(this);
		// pulsamos el botón
	}

	// acción cuando pulsamos botones
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == boton) { //SE PULSA EL BOTON
			etiqueta.setText(" Departamento a consultar: "+depar.getText()); int dep;
			try {
				dep=Integer.parseInt(depar.getText());
				VisualizarDep(dep);//visualiza datos del departamento 
				} 
			catch(java.lang.NumberFormatException ex){
				etiqueta.setText(" DEPARTAMENTO ERRÓNEO");
			} 		
		} 
	}

	// LOCALIZAR DATOS DEL DEPARTAMENTO
	void VisualizarDep(int dep) { // pinto
		// datos del dep
		SessionFactory sesion = SessionFactoryBuilder.getSessionfactory();
		Session session = sesion.openSession();
		
		Departamentos depart;
		//CON EL MÉTODO GET
		depart = (Departamentos)session.get(Departamentos.class, BigDecimal.valueOf(dep));
		
		
		/*Query q = session.createQuery("from Departamentos de where de.deptNo = :ndep");
		q.setBigDecimal("ndep", BigDecimal.valueOf(dep));
		Departamentos depart =(Departamentos)q.uniqueResult();*/

		if (depart != null) {
			etiqueta.setText(" Nombre Departamento: " + depart.getDnombre());
			VisualizarEmp(depart); // visualizo los empleados
		} else
			etiqueta.setText(" NO EXISTE EL DEPARTAMENTO............");
		session.close();
	}// fin VisualizarDep
		
	// LOCALIZAR DATOS DE LOS EMPLEADOS

	void VisualizarEmp (Departamentos d){ //pinto datos de los empleados del depart
	
		JTextArea area = new JTextArea(); JScrollPane scroll= new JScrollPane(area);
		//preparo el área para escribir los empleados 
		area.setBounds(new Rectangle(20,70, 250, 400)); 
		scroll.setBounds (new Rectangle (20,70,250,400)); 
		area.setEditable(false); getContentPane().add(scroll, null); 
		getContentPane().setLayout(null) ;
		area.setForeground(Color.blue);
		
		Set<Empleados> listaEmpleados = d.getEmpleadoses();
		
		area.append(" Número de empleados: " + listaEmpleados.size()+ "\n"); area.append(" ") ;
		area.append("\n APELLIDO\tOFICIO");
		area.append("\n ==========================="); 
		
		
		Iterator <Empleados> iter = listaEmpleados.iterator();
		Empleados emple;
		
		while (iter.hasNext()) {
			emple = (Empleados) iter.next();
			String cad ="\n " + emple.getApellido() + "\t"+emple.getOficio(); area.append(cad);
		}
		
	}
	// fin VisualizarEmp }
	//fin de la clase Pantalla
}
