package vcs;

import java.util.List;

public class Branch {
    private String branchName;
    private List<Commit> commits;
    public final List<Commit> getCommits() {
        return commits;
    }

    public final void setCommits(final List<Commit> commits) {
        this.commits = commits;
    }

    public final String getBranchName() {
        return branchName;
    }

    public final void setBranchName(final String branchName) {
        this.branchName = branchName;
    }
}
