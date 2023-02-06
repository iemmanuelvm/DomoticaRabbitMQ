import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProcesoHumo extends Thread
{
  private volatile String responseHora="";
  private volatile String responseHumo="";
  private volatile String responseALAR="";
  private volatile String response;
    public void run()
   	{
      while(true)
      {
      try(ClienteJAVA fibonacciRpc = new ClienteJAVA()) {
           		System.out.println("[x] Solicitando que(HUMO)");
            	response = fibonacciRpc.call("5-HUMO", "humo");
              String[] datos = response.split("\\+");
              if(datos.length==3)
              {
                responseHora = datos[0]; 
                responseHumo = datos[1];
                responseALAR = datos[2];
              }
              else
              {
                responseHora = ""; 
                responseHumo = "";
              }

            	sleep(1000);
            } catch (IOException | TimeoutException | InterruptedException err) {
                err.printStackTrace();
            }
   		}
   	}
  public String getValueHumoHora() 
  {
   return responseHora;
  }
	public String getValueHumo() 
  {
	 return responseHumo;
	}
  public String getValueAlarma() 
  {
   return responseALAR;
  }
  public void setValueAlarma() 
  {
      responseALAR = "";
  }
}