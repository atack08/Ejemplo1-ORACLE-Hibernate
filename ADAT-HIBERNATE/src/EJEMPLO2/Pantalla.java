package EJEMPLO2;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import PRIMERO.Departamentos;
import PRIMERO.Empleados;
import PRIMERO.SessionFactoryBuilder;

public class Pantalla extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JTextField depar = new JTextField(2);
	JTextField nombre = new JTextField();
	JTextField loe = new JTextField();
	JLabel etiqueta = new JLabel("");
	JLabel Lnumero = new JLabel("N° de departamento:");
	JLabel Lnombre = new JLabel("Nombre:");
	JLabel Lloc = new JLabel("Localidad:");
	JButton btnAlta = new JButton("Alta");
	JButton btnBaja = new JButton("Baja");
	JButton btnModif = new JButton("Modificación");
	JButton btnLimpiar = new JButton("Limpiar");

	// Inicia la pantalla
	public void iniciar() {
		JPanel panel = new JPanel(false);
		JLabel cab = new JLabel("GESTIÓN DE DEPARTAMENTOS");
		panel.setLayout(null);
		cab.setBounds(new Rectangle(30, 15, 200, 25));
		panel.add(cab);
		
		Lnumero.setBounds(new Rectangle(75, 50, 140, 20));
		depar.setBounds(new Rectangle(220, 50, 80, 20));
		Lnombre.setBounds(new Rectangle(75, 75, 120, 20));
		nombre.setBounds(new Rectangle(150, 75, 175, 20));
		Lloc.setBounds(new Rectangle(75, 105, 120, 20));
		loe.setBounds(new Rectangle(150, 105, 250, 20));
		etiqueta.setBounds(new Rectangle(100, 150, 400, 20));
		
		panel.add(etiqueta);
		panel.add(Lnumero);
		panel.add(depar, null);
		panel.add(Lnombre);
		panel.add(nombre);
		panel.add(Lloc);
		panel.add(loe);
		
		btnAlta.setBounds(new Rectangle(30, 205, 120, 30));
		btnBaja.setBounds(new Rectangle(150, 205, 120, 30));
		btnModif.setBounds(new Rectangle(270, 205, 120, 30));
		btnLimpiar.setBounds(new Rectangle(390, 205, 120, 30));
		
		panel.add(btnAlta);
		panel.add(btnBaja);
		panel.add(btnModif);
		panel.add(btnLimpiar);
		getContentPane().add(panel);
		pack();
		setSize(550, 300);
		setVisible(true);
		
		btnAlta.addActionListener(this);
		btnBaja.addActionListener(this);
		btnModif.addActionListener(this);
		btnLimpiar.addActionListener(this); // pulsamos el botón
	}// fin iniciar pantalla //

	public void actionPerformed(ActionEvent e) {//acción cuando pulsamos botones 

		int dep;

	try {
		dep=Integer.parseInt(depar.getText()) ;
		if (e.getSource() == btnAlta) { //SE PULSA EL BOTON ALTA
		InsertarDep(dep,nombre.getText(),loe.getText());
		} 
		else
			if (e.getSource() == btnBaja) { //SE PULSA EL BOTON BAJA
				BajaDep(dep);
			} 
			else
				if (e.getSource() == btnModif) { //SE PULSA EL BOTON MOFIFICACION
					ModificaDep(dep,nombre.getText(),loe.getText()); }
				else { //SE PULSA LIMPIAR LimpiarCampos();
				}
	}
	catch(java.lang.NumberFormatException ex){
		etiqueta.setText(" DEPARTAMENTO ERRÓNEO"); 
	}

	return;
}// fin acción de botones //

	
	void InsertarDep (int num, String nom, String loe) {//INSERTO UN DEPART. 
		
		SessionFactory sesion= SessionFactoryBuilder.getSessionfactory();
		Session session = sesion.openSession();
		Transaction tx =session.beginTransaction();
		
	
		
		
		if (existeDepartamento(session, num)) 
			etiqueta.setText(" DEPARTAMENTO EXISTENTE - NO SE PUEDE DAR DE ALTA"); 
		else {
			Departamentos depart = new Departamentos();
		
			if (nom.length()>15) 
				nom=nom.substring(0, 15);
			depart.setDnombre(nom);
			
			if (loe.length()>15)
				loe=loe.substring(0, 15);
			
			depart. setLoc (loe) ;
			depart.setDeptNo(BigDecimal.valueOf(num));
			
			//INSERTAMOS Y HACEMOS COMMIT
			session.save(depart);
			tx.commit();
			etiqueta.setText(" DEPARTAMENTO INSERTADO ");
		}
		session.close();
	}// fin
																																																																																																																																																						// InsertarDep

void BajaDep (int num) { //ELIMINO UN DEPARTAMENTO
	

	SessionFactory sesion= SessionFactoryBuilder.getSessionfactory(); 
	Session session = sesion.openSession();
	Transaction tx =session.beginTransaction();
	Departamentos de = new Departamentos();
	
	
	
	try {
		de = (Departamentos) session.load(Departamentos.class, BigDecimal.valueOf(num));
		
		//COMPROBAR SI TIENE EMPLEADOS
		Set<Empleados> listaE = de.getEmpleadoses();
				
		if(listaE == null || listaE.isEmpty()) { 
			//NO TIENE EMPLEADOS
			session.delete(de); //elimina el objeto 
			tx.commit();
			etiqueta.setText(" ELIMINADO ");
		
		}
		else { //TIENE EMPLEADOS
				etiqueta.setText(" NO SE PUEDE ELIMAR, TIENE "+ listaE.size() + " EMPLEADOS ");
		} 
	}
	catch( org.hibernate.ObjectNotFoundException t) {
		etiqueta.setText("DEPARTAMENTO no EXISTENTE - NO SE PUEDE ELIMINAR");
		
	}
	session.close (); 
}// fin
																																																											// EliminarDep
	//

	void ModificaDep (int num, String nom, String loe) { //MODIFICO UN DEPART.
		
		SessionFactory sesion= SessionFactoryBuilder.getSessionfactory(); 
		Session session = sesion.openSession();
		Transaction tx =session.beginTransaction();
		Departamentos de = new Departamentos();
		
		
		
		try {
			de = (Departamentos) session.load(Departamentos.class, BigDecimal.valueOf(num));
		
			if (nom.length()>15) 
				nom=nom.substring(0, 15); de.setDnombre(nom);
				
			if (loe.length()>15) 
				loe=loe.substring(0, 15); de.setLoc(loe) ;
				
				session.update(de); 
				//modifica el objeto 
			tx.commit(); 
			etiqueta. setText (" DEPARTAMENTO MODIFICADO ") ;
		
		
		}
		catch(org.hibernate.ObjectNotFoundException t){
				etiqueta.setText(" DEPARTAMENTO no EXISTENTE - NO SE PUEDE MODIFICAR");
				
		}
		session.close();
	}// fin ModiíicaDep
		//
	
	void LimpiarCampos(){
		depar.setText("");
		nombre.setText("") ;
		loe.setText("");
		etiqueta.setText("") ;
	}
	
	
	public boolean existeDepartamento(Session session, int num){

		Departamentos depart = (Departamentos) session.get (Departamentos.class, BigDecimal.valueOf(num));
		if (depart != null)
			return true;
					
		return false;
	}
	
	
}// fin de la clase Pantalla
