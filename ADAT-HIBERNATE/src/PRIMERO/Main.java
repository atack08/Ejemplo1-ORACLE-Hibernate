package PRIMERO;

import java.math.BigDecimal;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//CREAMOS LA SESSION FACTORY DESDE LA CLASE ESTÁTICA
		SessionFactory sesion = SessionFactoryBuilder.getSessionfactory();
		Session session = sesion.openSession();
		
		Transaction tx = session.beginTransaction();
		
		System.out.println("Inserto una fila en la tabla Departamentos");
		
		Departamentos dep = new Departamentos();
		dep.setDeptNo(BigDecimal.valueOf(50));
		dep.setDnombre("LOGÍSTICA");
		dep.setLoc("MADRID");
		
		//INSERTAMOS EL OBJETO EN LA BASE DE DATOS
		session.save(dep);
		tx.commit();
		
		//CERRAMOS EL OBJETO SESSION
		session.close();
		
	}

}
