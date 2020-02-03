package vcs;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;

import java.util.List;

public class Commit {
    private Branch branchParent;
    private Commit parent;
    private int commitId;
    private String message;
    private FileSystemSnapshot fileSystem;
    private List<FileSystemOperation> staging;

    public Commit(int commitId, String message, FileSystemSnapshot fileSystem,
                  List<FileSystemOperation> staging) {
        this.commitId = commitId;
        this.message = message;
        this.staging = staging;
        this.fileSystem = fileSystem;
    }
    public final void setCommitParent(final Commit commitParent) {
        this.parent = commitParent;
    }
    public final List<FileSystemOperation> getStaging() {
        return staging;
    }

    public final void setStaging(final List<FileSystemOperation> operations) {
        this.staging = operations;
    }

    public final int getCommitId() {
        return commitId;
    }

    public final String getMessage() {
        return message;
    }

    public final FileSystemSnapshot getFileSystem() {
        return fileSystem;
    }

    public final Branch getBranchParent() {
        return branchParent;
    }

    public final void setBranchParent(final Branch branchParent) {
        this.branchParent = branchParent;
    }
}
