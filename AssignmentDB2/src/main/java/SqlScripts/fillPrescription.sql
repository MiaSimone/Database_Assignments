/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  miade
 * Created: 28. feb. 2022
 */

create or replace function fillPrescription(n int)
	returns void
	language plpgsql
	volatile 
  as 
$$
declare 
	patient_id int;
	doctor_id int;
	pharmacy_id int;
	counter int := 0;
begin
	
	loop
		exit when counter = n ; 
		counter := counter + 1 ; 
	
		SELECT * FROM patients p ORDER BY RANDOM() LIMIT 1 into patient_id;
		SELECT * FROM doctor d ORDER BY RANDOM() LIMIT 1 into doctor_id;
		SELECT * FROM pharmacy p ORDER BY RANDOM() LIMIT 1 into pharmacy_id;
	
		insert into prescription(patient_id, doctor_id, pharmacy_id, description, created, expired)
		values(patient_id, doctor_id, pharmacy_id, '3 times a day', now(), now() + '11 days');
	end loop;
end;
$$;

