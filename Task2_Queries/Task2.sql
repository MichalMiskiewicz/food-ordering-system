--Write two SQL queries:

/*1. Select countries where the total number of inhabitants (population) in all cities is greater
than 400.*/

SELECT c.CountryID, c.Name FROM Country c
JOIN City cy ON c.CountryID = cy.CountryID
GROUP BY c.CountryID, c.Name
HAVING SUM(cy.Population) > 400;

/*2. Select names of the countries that have no buildings at all.*/

SELECT c.Name FROM Country c
JOIN City cy ON c.CountryID = cy.CountryID
LEFT JOIN Building b ON cy.CityID = b.CityID
GROUP BY c.Name
HAVING COUNT(b.Name) = 0;