package vcs;

import filesystem.FileSystemEntity;
import filesystem.FileSystemOperation;
import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;
import java.util.List;

public class RollbackOperation extends VcsOperation {
    /**
     * Vcs operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public RollbackOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public int execute(Vcs vcs) {
        List<FileSystemOperation> staging = new ArrayList<>();
        List<Commit> commits = vcs.getHead().getBranchParent().getCommits();
        Commit lastCommit = commits.get(commits.size()-1);
        vcs.setHead(lastCommit);
        ArrayList<FileSystemEntity> newChildrenEntities = new ArrayList<>();
        ArrayList<FileSystemEntity> childrenEntities = vcs.getHead().getFileSystem().getCurrentDir().getChildrenEntities();
        for (int i = 0; i < vcs.getHead().getStaging().size(); i++) {
            for (int j = 0; j < childrenEntities.size(); j++) {
                if (vcs.getHead().getStaging().get(i).getOperationArgs().get(1).equals(childrenEntities.get(j).getName())) {
                    newChildrenEntities.add(childrenEntities.get(j));
                }
            }
        }
        childrenEntities.removeAll(newChildrenEntities);
        vcs.getHead().getFileSystem().getCurrentDir().setChildrenEntities(childrenEntities);
        vcs.getHead().setStaging(staging);
        vcs.getHead().getFileSystem().getCurrentDir().setChildrenEntities(childrenEntities);
        return ErrorCodeManager.OK;
    }
}
