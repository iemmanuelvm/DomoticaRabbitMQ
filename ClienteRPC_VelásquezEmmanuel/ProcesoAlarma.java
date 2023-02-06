import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProcesoAlarma extends Thread
{
  private volatile String response;
    public void run()
   	{
      while(true)
      {
      try(ClienteJAVA fibonacciRpc = new ClienteJAVA()) {
            	sleep(1000);
            } catch (IOException | TimeoutException | InterruptedException err) {
                err.printStackTrace();
            }
   		}
   	}
	public String getValueAlarma() 
  {
    return response;
	}
}