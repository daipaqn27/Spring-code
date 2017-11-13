package edu.java.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//	    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//	    HelloClazz obj = (HelloClazz) context.getBean("helloJavaClazz");
//	    obj.printMessage();
//        
//        HelloClazz obj2 = (HelloClazz) context.getBean("helloJavaClazz");
//        obj2.printMessage();
//        
//        System.out.println(obj);
//        System.out.println(obj2);
        
//        AnnotationConfigApplicationContext context = 
//        		new AnnotationConfigApplicationContext();
//        context.register(AppConfig.class);
//        context.refresh();
//        
//        HelloClazz obj = (HelloClazz) context.getBean("bean2");
//        obj.printMessage();
//        
//        HelloClazz obj2 = (HelloClazz) context.getBean("bean2");
//        obj2.printMessage();
//        
//        System.out.println(obj);
//        System.out.println(obj2);
    	
//    	ClassPathXmlApplicationContext context1 = new ClassPathXmlApplicationContext("beans.xml");
//    	context1.registerShutdownHook();
    	
//    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//	    HelloClazz obj = (HelloClazz) context.getBean("helloJavaClazz2");
//	    obj.printMessage();
    	
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//	    HelloWorld obj = (HelloWorld) context.getBean("helloWorld2");
//	    obj.sayHello();
    	
//    	JavaClazz clazz = (JavaClazz) context.getBean("jee01");
//    	System.out.println("Map Implement is " + clazz.getStudents().getClass());
//    	System.out.println("There are " + clazz.getStudents().size() + " in the class");
    	
//    	HelloClazz clazz = (HelloClazz) context.getBean("helloJavaClazz");
//    	System.out.println("Total classes is " + clazz.getClazzes().size());
    	
//    	=========== Context Event ===========
    	context.start();
    	context.stop();
        
    }
}
