-- doctors
insert into drug_db.doctors values
-- (null, '234-56-7890', 'Will', 'Chen', 'Primary Care', 10), -- id 1
(null, '111-11-1111', 'Joe', 'Dirt', 'Oncologist', 7), -- id 2
(null, '222-22-2222', 'Meredith', 'Gray', 'Surgeon', 12), -- id 3
(null, '333-33-3333', 'Elizabeth', 'Merry', 'Neonatal', 8), -- id 4
(null, '444-44-4444', 'Neil', 'Patrick', 'Primary Care', 3); -- id 5

-- pharmacy
insert into drug_db.pharmacy values
(null, 'CVS', '123 Main St, Roseville CA, 95765', '(916) 276-1234'), -- id 1
(null, 'Lucky Pharmacy', '2330 Wood Ln, Folsom CA, 94063', '(530) 556-0932'), -- id 2
(null, 'Walgreens', '255 Richards Blvd, Sacramento CA, 95862', '(916) 413-2815'), -- id 3
(null, 'Rite Aid', '7 Avocet Dr, Redwood City CA, 94320', '(510) 435-8734'), -- id 4
(null, 'Safeway Pharmacy', '50 Riverboat Dr, Los Angeles CA, 91023', '(456) 321-0943'); -- id 5

-- patients
insert into drug_db.patients values
(null, '555-55-5555', 'Harry', 'Potter', 11, '13 Private Dr', null, 'Little Wingding', 'UK', '12345', 3, 5), -- id 1
(null, '666-66-6666', 'Artimis', 'Fowl', 12, '7 Avocet Dr', '#7-101', 'Redwood Shores', 'CA', '12345', 1, 1), -- id 2
(null, '777-77-7777', 'Jane', 'Doe', 56, '55 Main St', null, 'Main City', 'CA', '56565', 4, 2), -- id 3
(null, '888-88-8888', 'Tony', 'Stark', 43, '2 Telegraph Ave', null, 'Oakland', 'CA', '23456', 2, 3), -- id 4
(null, '999-99-9999', 'Welby', 'Tosscobble', 45, '7071 Spring Ln', null, 'The Shire', 'NZ', '90476', 5, 2); -- id 5

-- pharma company
insert into pharma_company values
(null, 'Pfizer', '(123) 456-7890'), -- id 1
(null, 'Johnson & Johnson', '(234) 564-9393'), -- id 2
(null, 'Sanofi', '(345) 323-6543'), -- id 3
(null, 'Gilead Sciences', '(888) 890-8080'); -- id 4

-- drugs
insert into drugs values
(null, 'Benadryl', 'diphenhydramine', 1), -- id 1
(null, 'Flovent HFA', 'Fluticasone', 2), -- id 2
(null, 'Sudafed', 'Pseudoephedrine', 3), -- id 3
(null, 'Flonase', 'Fluticasone', 4), -- id 4
(null, 'Mylanta', 'aluminum hydroxide/magnesium', 2); -- id 5

-- drug price
insert into drug_price values
(1, 1, 24.99),
(1, 2, 13.99),
(1, 3, 8.99),
(2, 2, 15.00),
(2, 4, 17.75),
(3, 1, 22.23),
(4, 1, 28.99),
(4, 2, 34.97),
(4, 4, 6.99),
(5, 3, 12.33);

-- perscriptions
insert into prescriptions values
(1, 1, 1, current_date(), 40, 0, 6),
(2, 3, 3, current_date(), 20, 3, 6),
(3, 4, 2, current_date(), 15, 2, 6),
(4, 2, 4, current_date(), 30, 1, 6),
(5, 3, 3, current_date(), 20, 5, 6),
(1, 1, 2, current_date(), 15, 1, 6),
(2, 4, 2, current_date(), 15, 0, 6);

-- contracts
insert into contracts values
(1, 1, '2020-01-01', '2021-01-01', 'Will Merritt'),
(1, 2, '2020-01-01', '2021-01-01', 'Will Merritt'),
(1, 3, '2020-01-01', '2021-01-01', 'Will Merritt'),
(2, 1, '2020-04-20', '2021-04-20', 'Sandy Perkins'),
(2, 3, '2020-04-20', '2021-04-20', 'Sandy Perkins'),
(3, 3, '2020-02-23', '2021-02-23', 'Carol Baskins'),
(3, 4, '2020-01-06', '2021-01-06', 'Carol Baskins'),
(3, 5, '2019-12-12', '2020-12-12', 'Carol Baskins'),
(4, 1, '2019-11-22', '2020-11-22', 'Walter White'),
(4, 2, '2020-01-01', '2021-01-01', 'Walter White'),
(4, 3, '2020-02-23', '2021-02-23', 'Walter White'),
(4, 4, '2020-01-06', '2021-01-06', 'Walter White'),
(4, 5, '2019-12-12', '2020-12-12', 'Walter White');

-- contract text
insert into contract_text values
(1, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
(1, 2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
(1, 3, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
(2, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
(2, 3, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
(3, 3, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
(3, 4, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
(3, 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
(4, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
(4, 2, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
(4, 3, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
(4, 4, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'),
(4, 5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.');

/*
update contract_text set contract_text='Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.';
*/