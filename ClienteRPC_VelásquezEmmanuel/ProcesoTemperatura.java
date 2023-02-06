import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProcesoTemperatura extends Thread
{
	private volatile String responseHora;
	private volatile String responseTemperatura;
	private volatile String responseHumedad;
   private volatile String tipoTemperatura="";
	private volatile String response;
   	public void run()
   	{
   		while(true)
   		{
   			try(ClienteJAVA funcion = new ClienteJAVA()) {
           		System.out.println("[x] Solicitando que(TEMPERATURA)");
            	response = funcion.call("4-TEMPERATURA-"+tipoTemperatura, "temperatura");
            	String[] datos = response.split("\\+");
            	if(datos.length==3)
            	{
            		 responseHora = datos[0]; 
					    responseTemperatura = datos[1];
					    responseHumedad = datos[2];
            	}
            	else
            	{
            		 responseHora = "S/D"; 
					    responseTemperatura = "S/D";
					    responseHumedad = "S/D";
            	}
            	sleep(1000);
            	
            } catch (IOException | TimeoutException | InterruptedException err) {
                err.printStackTrace();
            }
   		}
   	}

   public String getValueHora() 
   {
		return responseHora;
	}
	public String getValueTemperatura() 
   {
		return responseTemperatura;
	}
	public String getValueHumedad() 
   {
		return responseHumedad;
	}
   public String TipoDeTemperatura(String Tipo) 
   {
      tipoTemperatura = Tipo;
      return tipoTemperatura;
   }
}