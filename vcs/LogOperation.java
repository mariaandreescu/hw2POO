package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;

public class LogOperation extends VcsOperation {
    /**
     * Vcs operation constructor.
     *z
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public LogOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public int execute(Vcs vcs) {
        List<Commit> commits = vcs.getHead().getBranchParent().getCommits();
        for (int i = 0; i < commits.size(); i++) {
            if (i != 0) {
                vcs.getOutputWriter().write("\n");
            }
            vcs.getOutputWriter().write("Commit id: "
                    + commits.get(i).getCommitId() + "\n");
            vcs.getOutputWriter().write("Message: "
                    + commits.get(i).getMessage() + "\n");
        }
        return ErrorCodeManager.OK;
    }
}
