There are 4 types of joins:

1. Inner join
2. Right join
3. Left join
4. Full join


==Inner Join==
Returns the records that have matching values in both tables being joined. If you are performing an INNER JOIN operation between Conversation and
Thread tables, all the tuples which have matching values in both the tables will be given as output.
```genericsql
SELECT * FROM CONVERSATION; --Yields

CONVERSATION_ID  	ID  	NUMBER  	USER_ID  	    FROM  	                RECEIVED  	          COMPANY  
1	                9455	6441	    8274	eliseo.lynch@example.com	2019-11-05 17:21:21	        1
2	                5327	5854	    3284	vern.gibson@example.com	    2019-01-05 17:21:21	1
3	                1504	5918	    3372	bret.nitzsche@example.com	2019-09-09 17:21:21     	1

```

```genericsql
SELECT * FROM THREAD:


THREAD_ID  	ID  	PAYLOAD  	                                                                                    CONVERSATION  
1	        6395	There's only one life-form as intelligent as me within thirty parsecs of here and that's me.    	1
2	        9227	Life? Don't talk to me about life.	1
3	        779	And then of course I've got this terrible pain in all the diodes down my left side.	                2


```

```genericsql
SELECT CONVERSATION.CONVERSATION_ID, CONVERSATION.ID, CONVERSATION.USER_ID, CONVERSATION.COMPANY, THREAD.THREAD_ID, THREAD.PAYLOAD FROM CONVERSATION INNER JOIN THREAD ON CONVERSATION.CONVERSATION_ID = THREAD.CONVERSATION WHERE CONVERSATION.COMPANY=1;

SELECT CONVERSATION.CONVERSATION_ID, CONVERSATION.ID, CONVERSATION.USER_ID, CONVERSATION.COMPANY, THREAD.THREAD_ID, THREAD.PAYLOAD FROM CONVERSATION INNER JOIN THREAD ON CONVERSATION.CONVERSATION_ID = THREAD.CONVERSATION WHERE CONVERSATION.COMPANY=1;
CONVERSATION_ID  	ID  	USER_ID  	COMPANY  	THREAD_ID  	PAYLOAD  
1	9455	8274	1	1	There's only one life-form as intelligent as me within thirty parsecs of here and that's me.
1	9455	8274	1	2	Life? Don't talk to me about life.
2	5327	3284	1	3	And then of course I've got this terrible pain in all the diodes down my left side.
2	5327	3284	1	4	There's only one life-form as intelligent as me within thirty parsecs of here and that's me.
2	5327	3284	1	5	Pardon me for breathing, which I never do anyway so I don't know why I bother to say it, Oh God, I'm so depressed.
3	1504	3372	1	6	And then of course I've got this terrible pain in all the diodes down my left side.
3	1504	3372	1	7	Life? Don't talk to me about life.
3	1504	3372	1	8	You think you've got problems? What are you supposed to do if you are a manically depressed robot? No, don't try to answer that. I'm fifty thousand times more intelligent than you and even I don't know the answer. It gives me a headache just trying to think down to your level.
(8 rows, 1 ms)

```



==Left Join==
Also called LEFT outer join, returns all the records from the left table and also those records which satisfy a condition
from the right table. Also for the records that have no matching values in the right table, the output will contain NULL values.




