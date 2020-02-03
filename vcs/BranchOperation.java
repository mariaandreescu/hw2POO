package vcs;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;

public class BranchOperation extends VcsOperation {
    /**
     * Vcs operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public BranchOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public int execute(Vcs vcs) {
        String branchName = operationArgs.get(1);
        for (int i = 0; i < vcs.getBranches().size(); i++) {
            if (branchName.equals(vcs.getBranches().get(i).getBranchName())) {
                return ErrorCodeManager.VCS_BAD_CMD_CODE;
            }
        }
        Branch branch = new Branch();
        branch.setBranchName(branchName);
        List<FileSystemOperation> staging = new ArrayList<>();
        List<Commit> commitList = new ArrayList<>();
        FileSystemSnapshot activeSnapshot = vcs.getActiveSnapshot().cloneFileSystem();
        Commit commitParent = new Commit(vcs.getHead().getCommitId(),
                vcs.getHead().getMessage(), activeSnapshot, staging);
        commitParent.setBranchParent(branch);
        commitList.add(commitParent);
        branch.setCommits(commitList);
        vcs.getBranches().add(branch);
        return ErrorCodeManager.OK;
    }
}
