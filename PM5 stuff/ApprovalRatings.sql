CREATE SCHEMA IF NOT EXISTS ApprovalRatings;
USE ApprovalRatings;

DROP TABLE IF EXISTS Ratings;

CREATE TABLE Ratings (
PresidentName VARCHAR(255) NOT NULL,
StartDate DATE NOT NULL,
EndDate DATE NOT NULL,
Pollster VARCHAR(255) NOT NULL,
Grade VARCHAR(5),
SampleSize INT NOT NULL,
Population VARCHAR(10),
Weight FLOAT8,
Influence INT,
Approve INT NOT NULL,
Disapprove INT NOT NULL,
AdjustedApprove FLOAT4,
AdjustedDisapprove FLOAT4,
Url VARCHAR(300),
Poll_Id INT,
QuestId INT,
CreateDate DATE,
CONSTRAINT pk_Ratings_Poll_Id PRIMARY KEY (Poll_Id),
CONSTRAINT uq_Ratings_Poll_Id UNIQUE (Poll_Id)

);