package vcs;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.IDGenerator;
import utils.OutputWriter;
import utils.Visitor;

import java.util.ArrayList;
import java.util.List;

public final class Vcs implements Visitor {
    private final OutputWriter outputWriter;
    private FileSystemSnapshot activeSnapshot;
    private List<Branch> branches;
    private Commit head;

    public OutputWriter getOutputWriter() {
        return outputWriter;
    }
    public FileSystemSnapshot getActiveSnapshot() {
        return activeSnapshot;
    }

    public void setActiveSnapshot(final FileSystemSnapshot activeSnapshot) {
        this.activeSnapshot = activeSnapshot;
    }
    public Commit getHead() {
        return head;
    }

    public void setHead(Commit head) {
        this.head = head;
    }


    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }


    /**
     * Vcs constructor.
     *
     * @param outputWriter the output writer
     */
    public Vcs(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    /**
     * Does initialisations.
     */
    public void init() {
        this.activeSnapshot = new FileSystemSnapshot(outputWriter);
        Branch branch = new Branch();
        branch.setBranchName("master");
        List<Branch> branches = new ArrayList<>();
        branches.add(branch);
        this.setBranches(branches);
        List<FileSystemOperation> staging = new ArrayList<>();
        Commit commit = new Commit(IDGenerator.generateCommitID(), "First commit",
                activeSnapshot, staging);
        List<Commit> commitList = new ArrayList<>();
        commitList.add(commit);
        this.setHead(commit);
        this.getHead().setBranchParent(branch);
        this.getBranches().get(0).setCommits(commitList);
    }

    /**
     * Visits a file system operation.
     *
     * @param fileSystemOperation the file system operation
     * @return the return code
     */
    public int visit(FileSystemOperation fileSystemOperation) {
        return fileSystemOperation.execute(this.activeSnapshot);
    }

    /**
     * Visits a vcs operation.
     *
     * @param vcsOperation the vcs operation
     * @return return code
     */
    @Override
    public int visit(VcsOperation vcsOperation) {
        return vcsOperation.execute(this);
    }
}
