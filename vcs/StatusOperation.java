package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public class StatusOperation extends VcsOperation {
    /**
     * Vcs operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public StatusOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    static final char CHARACTER = '"';
    @Override
    public int execute(Vcs vcs) {
        vcs.getOutputWriter().write("On branch: ");
        vcs.getOutputWriter().write(vcs.getHead().getBranchParent().getBranchName());
        vcs.getOutputWriter().write("\n");
        vcs.getOutputWriter().write("Staged changes:");
        for (int i = 0; i < vcs.getHead().getStaging().size(); i++) {
            String specificCommand = vcs.getHead().getStaging()
                    .get(i).getType().toString().toLowerCase();
            switch (specificCommand.toLowerCase()) {
                case "touch":
                    vcs.getOutputWriter().write("\n\t");
                    vcs.getOutputWriter().write("Created file "
                            + vcs.getHead().getStaging().get(i).getOperationArgs().get(1));
                    continue;
                case "remove":
                    vcs.getOutputWriter().write("\n\t");
                    vcs.getOutputWriter().write("Removed "
                            + vcs.getHead().getStaging().get(i).getOperationArgs().get(1));
                    continue;
                case "changedir":
                    vcs.getOutputWriter().write("\n\t");
                    vcs.getOutputWriter().write("Changed directory to "
                            + vcs.getHead().getStaging().get(i).getOperationArgs().get(1));
                    continue;
                case "makedir":
                    vcs.getOutputWriter().write("\n\t");
                    vcs.getOutputWriter().write("Created directory "
                            + vcs.getHead().getStaging().get(i).getOperationArgs().get(1));
                    continue;
                case "writetofile":
                    vcs.getOutputWriter().write("\n\t");
                    vcs.getOutputWriter().write("Added " + CHARACTER
                            + vcs.getHead().getStaging().get(i).getOperationArgs().get(1)
                            + CHARACTER
                    + " to file " + vcs.getHead().getStaging().get(i).getOperationArgs().get(1));
                    continue;
            }
        }
        vcs.getOutputWriter().write("\n");
        return ErrorCodeManager.OK;
    }
}
