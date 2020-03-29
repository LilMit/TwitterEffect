USE ApprovalRatings;

LOAD DATA LOCAL INFILE '/Users/elaineparr/Documents/GitHub/TwitterEffect/PM5 stuff/Trump_Approvals_Filtered.txt' INTO TABLE Ratings
	FIELDS TERMINATED BY '\t'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (@president,@subgroup,@modeldate,@startdate,@enddate,@pollster,@grade,@samplesize,@population,@weight,@influence,
    @approve,@disapprove,@adjusted_approve,@adjusted_disapprove,@multiversions,@tracking,@url,@poll_id,@question_id,
    @createddate,@timestampcol) set PresidentName=@president,StartDate=@startdate,EndDate=@enddate,
    Pollster=@pollster,Grade=@grade,SampleSize=@samplesize,Population=@population,Weight=@weight,Influence=@influence,
    Approve=@approve,Disapprove=@disapprove,AdjustedApprove=@adjusted_approve,AdjustedDisapprove=@adjusted_disapprove,
    Url=@url,Poll_Id=@poll_id,QuestId=@question_id,CreateDate=@createddate;