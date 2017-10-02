package console;

import service.MasterService;

public class StopMasterService implements Command {
    @Override
    public void execute() {
        MasterService.getInstance().stop();
    }
}
