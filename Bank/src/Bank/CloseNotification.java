package Bank;

import java.io.IOException;
import java.util.EventListener;

public interface  CloseNotification extends EventListener {
	
	public void whenCloseSystem() throws IOException;
	
}
