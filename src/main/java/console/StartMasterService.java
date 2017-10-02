package console;

import service.MasterService;

public class StartMasterService implements Command {
    @Override
    public void execute() {
        MasterService.getInstance().start();
    }
}
