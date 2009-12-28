package cf;

import java.io.Reader;
import javax.annotation.Resources;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PruebaLogs {

    static Logger logger = Logger.getLogger(PruebaLogs.class);

    public static void main(String[] args){

        
        PropertyConfigurator.configure("/home/luigi/Escritorio/log4j.properties");

        logger.info("Comienzo de la aplicacion");
        PruebaLogs objeto = new PruebaLogs();
        objeto.ejecuta();
        logger.info("Fin de la aplicacion");

    }

    void ejecuta(){
        logger.error("Mostramos un error");
    }

}
