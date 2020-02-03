package vcs;

import filesystem.FileSystemOperation;
import utils.ErrorCodeManager;
import utils.IDGenerator;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;

public class CommitOperation extends VcsOperation {
    /**
     * Vcs operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CommitOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public int execute(Vcs vcs) {
        if (vcs.getHead().getStaging().size() == 0) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }
        List<FileSystemOperation> staging = new ArrayList<>();
        vcs.getHead().setStaging(staging);
        String message = getOperationArgs().get(1).substring(3);
        Commit newCommit = new Commit(IDGenerator.generateCommitID(),
                message, vcs.getActiveSnapshot(), staging);
        newCommit.setCommitParent(vcs.getHead());
        newCommit.setBranchParent(vcs.getHead().getBranchParent());
        vcs.getHead().getBranchParent().getCommits().add(newCommit);
        return ErrorCodeManager.OK;
    }
}
