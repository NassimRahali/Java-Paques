
---------------
Requête 1 (OK)
---------------
select * from operations where ClientID = (select IdClient from clients where Nom='Prenom1')
	and  DateOp between '2012-01-01' and '2015-01-01';
-----------------------------------------------------------------------------------------------------------------



---------------
Requête 2
---------------
select avg(Montant) from comptes where ClientID = (select IdClient from clients where Nom='Prenom1');
-----------------------------------------------------------------------------------------------------------------



---------------
Requête 3
---------------
select count(o.TypeOp) as nbre, year(o.DateOp) from operations o,comptes c
	where o.Traitee = 0 and o.TypeOp = "débit"
	and o.ClientID = (select IdClient from clients where Nom='Client1')
	and c.NumCompte in (select NumCompte from comptes where ClientID = (select IdClient from clients where Nom='Client1'))
	group by year(o.DateOp);
-----------------------------------------------------------------------------------------------------------------



---------------
Requête 4
---------------
select Fiable as Validation from comptes where Fiable = 1 and IdCompte = 1
	and ClientID = (select IdClient from clients where Nom='Client1');
-----------------------------------------------------------------------------------------------------------------


	

select * from comptes where ClientID = 1;

select * from operations o where ClientID = (select IdClient from clients where Nom='Client1');

select count(*) from operations o, comptes c;