Andreescu Maria-Elena, 323CD

In pachetul vcs, am creat doua clase Branch si Commit:
	*Branch - contine campurile branchName(numele branch-ului) si commits(lista commit-urilor de pe branch);
	*Commit - contine campurile branchParent(branch-ul pe care se afla commit-ul), commitId, message,
		 filesystem, staging(salvez toate operatiile trackable facute dupa commit), parent(commit-ul parinte).

Alte clase:
	*InvalidVcsOperation - are aceeasi functionalitate ca si FileSystemBadOperation
	*clasele pentru fiecare operatie vcs: BranchOperation, CheckoutOperation, LogOperation,
	CommitOperation, RollbackOperation si StatusOperation(toate extind VcsOperation).