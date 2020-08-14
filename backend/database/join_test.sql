
    
            SELECT ep.emp_id,ep.role,ep.campus_short,u.firstname,u.lastname,
                (select sum( t.minutes) 
                   FROM training t
                       JOIN training_cert_period tcp ON t.train_id = tcp.train_id
                       JOIN cert_period cp ON tcp.cert_period_id = cp.cert_id
                       JOIN employee_profile ep ON cp.emp_id = ep.emp_id
                       JOIN users u ON ep.user_id = u.id
                      WHERE t.is_compliance = true
                      ) as compliance_hours,
                (select sum( t.minutes) 
                   FROM training t
                      JOIN training_cert_period tcp ON t.train_id = tcp.train_id
                      JOIN cert_period cp ON tcp.cert_period_id = cp.cert_id
                      JOIN employee_profile ep ON cp.emp_id = ep.emp_id
                      JOIN users u ON ep.user_id = u.id
                      WHERE t.is_compliance = false 
                      ) as elective_hours  
               FROM training t
                JOIN training_cert_period tcp ON t.train_id = tcp.train_id
                JOIN cert_period cp ON tcp.cert_period_id = cp.cert_id
                JOIN employee_profile ep ON cp.emp_id = ep.emp_id
                JOIN users u ON ep.user_id = u.id
            WHERE  u.firstname NOT LIKE 'TE Firstname' AND ep.end_date is NULL
            GROUP BY ep.emp_id,ep.role, ep.campus_short,u.firstname, u.lastname;           
 
  -- Trying to pull a single row with a sum of each employee's 'compliance' training and 'elective' training. 
  -- Those are both derived values. I have a 'is_compliance' column and a 'minutes' column.
  -- This is just one version of the query I'm not even sure it's the best one. If I run it the way it is 
  -- you can see the output below. It's just summing all the minutes and assigning them to everyone.
  -- If I add a 'GROUP BY' to the subqueries it throw a error because a subquery can't return more than one
  -- row.            
                
                
           
    CASE WHEN string_agg( t.is_compliance::character varying, ',')= 'true'  THEN SUM(t.minutes)
         END as compliance,
    CASE WHEN string_agg( t.is_compliance::character varying, ',')= 'false' THEN SUM(t.minutes) 
         END as elective        
            
            
            
            
            
            
             
             
    SELECT ep.emp_id,ep.role,ep.campus_short,u.firstname,u.lastname,t.is_compliance, 
     CASE WHEN (t.is_compliance = true) THEN SUM(t.minutes) 
          END as compliance,
     CASE WHEN (t.is_compliance = false) THEN SUM(t.minutes) 
          END as elective   
    FROM training t
        JOIN training_cert_period tcp ON t.train_id = tcp.train_id
        JOIN cert_period cp ON tcp.cert_period_id = cp.cert_id
        JOIN employee_profile ep ON cp.emp_id = ep.emp_id
        JOIN users u ON ep.user_id = u.id
    WHERE  u.firstname NOT LIKE 'TE Firstname' AND ep.end_date is NULL
    GROUP BY ep.emp_id, ep.role, ep.campus_short,u.firstname, u.lastname,t.is_compliance     
           
          
    --Tried it with some case statements. Here the main problem is I'm using 'is_compliance' in 
    -- the CASE so I have to add it to the group by. So it wont let me return just one row per
    -- employee because 'is_compliance' is grouped into true and false sometimes. I also tried 
    -- string_agg( t.is_compliance::character varying, ',')= 'true', so I could remove it from
    -- the 'GROUP BY' but that ended up causing some other issues. 
          
          
          
          
            
                  select( 
                   SELECT   sum(distinct t.minutes) as elective, null as compliance
                       FROM  training t 
                         JOIN training_cert_period tcp ON t.train_id = tcp.train_id
                         JOIN cert_period cp ON tcp.cert_period_id = cp.cert_id
                         JOIN employee_profile ep ON cp.emp_id = ep.emp_id
                         JOIN users u ON ep.user_id = u.id
                       WHERE t.is_compliance = false
                      -- GROUP BY ep.emp_id , u.firstname
                      union all
                   SELECT  sum(distinct t.minutes) as compliance, null as elective
                       FROM training t 
                         JOIN training_cert_period tcp ON t.train_id = tcp.train_id
                         JOIN cert_period cp ON tcp.cert_period_id = cp.cert_id
                         JOIN employee_profile ep ON cp.emp_id = ep.emp_id
                         JOIN users u ON ep.user_id = u.id
                       WHERE t.is_compliance = true 
                       --GROUP BY ep.emp_id , u.firstname
                     
               
                       
                                           
 