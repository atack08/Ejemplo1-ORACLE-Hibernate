package PRIMERO;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryBuilder {
	
	private static  SessionFactory SessionFactory;
	
	static{
		
		try{
			SessionFactory = new Configuration().configure().buildSessionFactory();
		}
		catch (Throwable e) {
			System.err.println("Fallo en la creación inicial de la Session Factory");
		}
		
		
		
	}

	public static SessionFactory getSessionfactory() {
		return SessionFactory;
	}

}
