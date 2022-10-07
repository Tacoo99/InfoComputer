package infokomputer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ProcessService extends Service<Object> {
    @Override
    protected Task<Object> createTask() {
        return new Task() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(3000);
                return null;
            }
        };
    }
}
