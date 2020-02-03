package vcs;

import filesystem.FileSystemEntity;
import filesystem.FileSystemSnapshot;
import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;

public class CheckoutOperation extends VcsOperation {
    /**
     * Vcs operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CheckoutOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public int execute(Vcs vcs) {
        //
        if (operationArgs.size() == 1) {
            //no branchName branch
            int ok = 0;
            Branch newBranch = new Branch();
            String branchName = operationArgs.get(0);
            for (int i = 0; i < vcs.getBranches().size(); i++) {
                if (branchName.equals(vcs.getBranches().get(i).getBranchName())) {
                    ok = 1;
                    newBranch = vcs.getBranches().get(i);
                }
            }
            Commit newHead = vcs.getHead();
            if (ok == 1) {
                if (vcs.getHead().getStaging().size() != 0) {
                    return ErrorCodeManager.VCS_STAGED_OP_CODE;
                } else {
                    for (int i = 0; i < newBranch.getCommits().size(); i++) {
                        newHead = newBranch.getCommits().get(i);
                    }
                    vcs.setHead(newHead);
                }
            } else {
                return ErrorCodeManager.VCS_BAD_CMD_CODE;
            }
        } else {
            if (vcs.getHead().getStaging().size() != 0) {
                return ErrorCodeManager.VCS_STAGED_OP_CODE;
            } else {
                //no commit id
                Commit newCommit = vcs.getHead();
                int newHeadPos = 0;
                int ok = 0;
                int commitId = Integer.valueOf(operationArgs.get(1));
                List<Commit> currentBranchCommits = vcs.getHead().getBranchParent().getCommits();
                for (int i = 0; i < currentBranchCommits.size(); i++) {
                    if (commitId == currentBranchCommits.get(i).getCommitId()) {
                        ok = 1;
                        newCommit = currentBranchCommits.get(i); //the new head
                        newHeadPos = i;
                        break;
                    }
                }
                if (ok == 0) {
                    return ErrorCodeManager.VCS_BAD_PATH_CODE;
                } else {
                    vcs.setHead(newCommit);
                    for (int i = newHeadPos + 1; i < currentBranchCommits.size(); i++) {
                        currentBranchCommits.remove(currentBranchCommits.size()-1);
                    }
                    FileSystemSnapshot snapshot = currentBranchCommits.get(newHeadPos).getFileSystem();
                    vcs.getHead().getBranchParent().setCommits(currentBranchCommits);
                    ArrayList<FileSystemEntity> childrenEntities = new ArrayList<>();
                    vcs.getHead().getFileSystem().getCurrentDir().setChildrenEntities(childrenEntities);

                }
            }
        }
        return ErrorCodeManager.OK;
    }
}
