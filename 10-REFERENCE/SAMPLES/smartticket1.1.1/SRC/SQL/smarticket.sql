drop table reservations;
drop table seating;
drop table shows;
drop table locations;
drop table movies;
drop table userprefs;
drop table tickers;
drop table locales;
drop table messages;

create table movies
(
	movie_id integer,
	title varchar(25),
	rating varchar(3),
	poster_url varchar(30)
);

create table locations
(
	location_id integer,
	zipcode char(5),
	location varchar(35)
);

create table shows
(
	show_id integer,
	movie_id integer,
	location_id integer,
	showtime varchar(5)
);
	
create table seating
(
	show_id integer,
	s_row integer,
	seats varchar(35)
);
	
create table reservations
(
        username varchar(15),
	show_id integer,
	s_row integer,
	seat integer
);

create table userprefs
(
	username varchar(15),
	password varchar(15),
	zipcode char(5),
	creditcard varchar(16)
);

create table tickers
(
	zipcode char(5),
	promotext varchar(50)
);

create table locales
(
	locale_id int,
	locale char(5)
);

create table messages
(
	locale_id int,
	message_id int,
	message_text char(80)
);

insert into movies values (1, 'Big and Badder', 'pg', 'BiggerBadder.png');
insert into movies values (2, 'The Dot', 'r', 'TheDot.png');
insert into movies values (3, 'Uber Coder', 'pg', 'Ubercoder.png');
insert into movies values (4, 'Invasion of the Dots', 'g', 'Invasion.png');

insert into locations values (1, '95130', 'AMC Saratoga 14');
insert into locations values (2, '95054', 'AMC Mercado 20');

insert into shows values (1, 1, 1, '1330');
insert into shows values (2, 1, 1, '1520');

insert into shows values (3, 2, 1, '1330');
insert into shows values (4, 2, 1, '1520');

insert into shows values (5, 3, 2, '2000');

insert into shows values (6, 4, 1, '2200');

insert into shows values (7, 1, 2, '1330');
insert into shows values (8, 2, 2, '1745');

insert into shows values (9, 3, 2, '1930');
insert into shows values (10, 4, 2, '2200');

insert into tickers values ('95130', 'Visit Saratoga Bowl after the show!');
insert into tickers values ('95054', 'Santa Clara Pizza - the BEST around!');

insert into seating values (1, 0, '9009000000009009');
insert into seating values (1, 1, '0119110001119000');
insert into seating values (1, 2, '0009110011119110');
insert into seating values (1, 3, '0119111000119111');
insert into seating values (1, 4, '1119110110009100');
insert into seating values (1, 5, '1119001101119100');
insert into seating values (1, 6, '0119110111109111');
insert into seating values (1, 7, '1119011111109110');
insert into seating values (1, 8, '1119111111119111');
insert into seating values (1, 9, '9999111110119999');
insert into seating values (1, 10, '0099911001099910');

insert into seating values (2, 0, '9009000000009009');
insert into seating values (2, 1, '0119110001119000');
insert into seating values (2, 2, '0009110011119110');
insert into seating values (2, 3, '0119111000119111');
insert into seating values (2, 4, '1119110110009100');
insert into seating values (2, 5, '1119001101119100');
insert into seating values (2, 6, '0119110111109111');
insert into seating values (2, 7, '1119011111109110');
insert into seating values (2, 8, '1119111111119111');
insert into seating values (2, 9, '9999111110119999');
insert into seating values (2, 10, '0099911001099910');

insert into seating values (3, 0, '9009000000009009');
insert into seating values (3, 1, '0119110001119000');
insert into seating values (3, 2, '0009110011119110');
insert into seating values (3, 3, '0119111000119111');
insert into seating values (3, 4, '1119110110009100');
insert into seating values (3, 5, '1119001101119100');
insert into seating values (3, 6, '0119110111109111');
insert into seating values (3, 7, '1119011111109110');
insert into seating values (3, 8, '1119111111119111');
insert into seating values (3, 9, '9999111110119999');
insert into seating values (3, 10, '0099911001099910');

insert into seating values (4, 0, '9009000000009009');
insert into seating values (4, 1, '0119110001119000');
insert into seating values (4, 2, '0009110011119110');
insert into seating values (4, 3, '0119111000119111');
insert into seating values (4, 4, '1119110110009100');
insert into seating values (4, 5, '1119001101119100');
insert into seating values (4, 6, '0119110111109111');
insert into seating values (4, 7, '1119011111109110');
insert into seating values (4, 8, '1119111111119111');
insert into seating values (4, 9, '9999111110119999');
insert into seating values (4, 10, '0099911001099910');

insert into seating values (5, 0, '9009000000009009');
insert into seating values (5, 1, '0119110001119000');
insert into seating values (5, 2, '0009110011119110');
insert into seating values (5, 3, '0119111000119111');
insert into seating values (5, 4, '1119110110009100');
insert into seating values (5, 5, '1119001101119100');
insert into seating values (5, 6, '0119110111109111');
insert into seating values (5, 7, '1119011111109110');
insert into seating values (5, 8, '1119111111119111');
insert into seating values (5, 9, '9999111110119999');
insert into seating values (5, 10, '0099911001099910');

insert into seating values (6, 0, '9009000000009009');
insert into seating values (6, 1, '0119110001119000');
insert into seating values (6, 2, '0009110011119110');
insert into seating values (6, 3, '0119111000119111');
insert into seating values (6, 4, '1119110110009100');
insert into seating values (6, 5, '1119001101119100');
insert into seating values (6, 6, '0119110111109111');
insert into seating values (6, 7, '1119011111109110');
insert into seating values (6, 8, '1119111111119111');
insert into seating values (6, 9, '9999111110119999');
insert into seating values (6, 10, '0099911001099910');

insert into seating values (7, 0, '9900900000000000090099');
insert into seating values (7, 1, '9000900000000000090009');
insert into seating values (7, 2, '0000900001110001190000');
insert into seating values (7, 3, '9000900110101101190019');
insert into seating values (7, 4, '9011900001110011190009');
insert into seating values (7, 5, '0011911110011111190000');
insert into seating values (7, 6, '0000911001111011191100');
insert into seating values (7, 7, '0011911100000001191110');
insert into seating values (7, 8, '0111911011111000091000');
insert into seating values (7, 9, '9111911110000011191009');
insert into seating values (7, 10, '9011910011101111191119');
insert into seating values (7, 11, '0111911001100111191111');
insert into seating values (7, 12, '1111911111011011191111');
insert into seating values (7, 13, '9999911110110110199999');
insert into seating values (7, 14, '0009991110111011999100');

insert into seating values (8, 0, '9900900000000000090099');
insert into seating values (8, 1, '9000900000000000090009');
insert into seating values (8, 2, '0000900001110001190000');
insert into seating values (8, 3, '9000900110101101190019');
insert into seating values (8, 4, '9011900001110011190009');
insert into seating values (8, 5, '0011911110011111190000');
insert into seating values (8, 6, '0000911001111011191100');
insert into seating values (8, 7, '0011911100000001191110');
insert into seating values (8, 8, '0111911011111000091000');
insert into seating values (8, 9, '9111911110000011191009');
insert into seating values (8, 10, '9011910011101111191119');
insert into seating values (8, 11, '0111911001100111191111');
insert into seating values (8, 12, '1111911111011011191111');
insert into seating values (8, 13, '9999911110110110199999');
insert into seating values (8, 14, '0009991110111011999100');

insert into seating values (9, 0, '9900900000000000090099');
insert into seating values (9, 1, '9000900000000000090009');
insert into seating values (9, 2, '0000900001110001190000');
insert into seating values (9, 3, '9000900110101101190019');
insert into seating values (9, 4, '9011900001110011190009');
insert into seating values (9, 5, '0011911110011111190000');
insert into seating values (9, 6, '0000911001111011191100');
insert into seating values (9, 7, '0011911100000001191110');
insert into seating values (9, 8, '0111911011111000091000');
insert into seating values (9, 9, '9111911110000011191009');
insert into seating values (9, 10, '9011910011101111191119');
insert into seating values (9, 11, '0111911001100111191111');
insert into seating values (9, 12, '1111911111011011191111');
insert into seating values (9, 13, '9999911110110110199999');
insert into seating values (9, 14, '0009991110111011999100');

insert into seating values (10, 0, '9900900000000000090099');
insert into seating values (10, 1, '9000900000000000090009');
insert into seating values (10, 2, '0000900001110001190000');
insert into seating values (10, 3, '9000900110101101190019');
insert into seating values (10, 4, '9011900001110011190009');
insert into seating values (10, 5, '0011911110011111190000');
insert into seating values (10, 6, '0000911001111011191100');
insert into seating values (10, 7, '0011911100000001191110');
insert into seating values (10, 8, '0111911011111000091000');
insert into seating values (10, 9, '9111911110000011191009');
insert into seating values (10, 10, '9011910011101111191119');
insert into seating values (10, 11, '0111911001100111191111');
insert into seating values (10, 12, '1111911111011011191111');
insert into seating values (10, 13, '9999911110110110199999');
insert into seating values (10, 14, '0009991110111011999100');

insert into locales values (1,'en_GB');
insert into locales values (2,'fr_FR');

insert into messages values (1, 0, 'Your Account');
insert into messages values (1, 1, 'User name:');
insert into messages values (1, 2, 'Password:');
insert into messages values (1, 3, 'ZIP Code:');
insert into messages values (1, 4, 'Credit Card, 1st 12-digits):');
insert into messages values (1, 5, 'Preview Mode');
insert into messages values (1, 6, 'None');
insert into messages values (1, 7, 'Poster');
insert into messages values (1, 8, 'Back');
insert into messages values (1, 9, 'Cancel');
insert into messages values (1, 10, 'Confirm');
insert into messages values (1, 11, 'Next');
insert into messages values (1, 12, 'Reserve');
insert into messages values (1, 13, 'Save');
insert into messages values (1, 14, 'Sign In');
insert into messages values (1, 15, 'Start');
insert into messages values (1, 16, 'Smart Ticket');
insert into messages values (1, 17, 'You are signed in. Press Start to begin looking for films.');
insert into messages values (1, 18, 'You do not have an account. Please create one.');
insert into messages values (1, 19, 'Signing in...');
insert into messages values (1, 20, 'Loading Films...');
insert into messages values (1, 21, 'Error');
insert into messages values (1, 22, 'You now have an account!');
insert into messages values (1, 23, 'Creating User...');
insert into messages values (1, 24, 'Loading Locations...');
insert into messages values (1, 25, 'Loading Poster...');
insert into messages values (1, 26, 'Loading Showtimes...');
insert into messages values (1, 27, 'Loading Seating Plan...');
insert into messages values (1, 28, 'Reserving Seats...');
insert into messages values (1, 29, 'Your purchase has been cancelled!');
insert into messages values (1, 30, 'Cancelling Purchase...');
insert into messages values (1, 31, 'Thank you for your purchase!');
insert into messages values (1, 32, 'Confirming Purchase...');
insert into messages values (1, 33, 'Cannot connect to server!');
insert into messages values (1, 34, 'Showtimes');
insert into messages values (1, 35, 'Screen');
insert into messages values (1, 36, 'Movies');
insert into messages values (1, 37, 'Locations');
insert into messages values (1, 38, 'Stop');
insert into messages values (1, 39, 'Enter last 4 digits of credit card:');
insert into messages values (1, 40, 'Movie:');
insert into messages values (1, 41, 'Time:');
insert into messages values (1, 42, 'Seats:');
insert into messages values (1, 43, 'Total:');
insert into messages values (1, 44, '$');
insert into messages values (1, 45, 'Locale');
insert into messages values (1, 46, 'Loading Locales...');
insert into messages values (1, 47, 'Loading Localized Messages...');
insert into messages values (1, 48, 'Please restart Smart Ticket to use the new locale');
insert into messages values (1, 49, 'Messaging error');
insert into messages values (1, 50, 'Server error');
insert into messages values (1, 51, 'Another user already has that name');
insert into messages values (1, 52, 'Your password is incorrect');
insert into messages values (1, 53, 'You do not have an account');
insert into messages values (1, 54, 'Those seats are unavailable');
insert into messages values (1, 55, 'General application error');
insert into messages values (1, 56, 'User ID must be at least 4 characters in length');
insert into messages values (1, 57, 'Password must be 6 characters in length');
insert into messages values (1, 58, 'ZIP code must be 5 characters in length');
insert into messages values (1, 59, 'Credit card must be 12 characters in length');
insert into messages values (1, 60, 'Invalid credit card expiration date');
insert into messages values (1, 61, 'Image Not Available');

insert into messages values (2, 0, 'Votre compte');
insert into messages values (2, 1, 'Nom utilisateur:');
insert into messages values (2, 2, 'Mot de passe:');
insert into messages values (2, 3, 'Code postal:');
insert into messages values (2, 4, 'Numéro de carte de crédit (12 premiers chiffres):');
insert into messages values (2, 5, 'Mode de prévisualisation');
insert into messages values (2, 6, 'Aucun');
insert into messages values (2, 7, 'Affiche');
insert into messages values (2, 8, 'Retour');
insert into messages values (2, 9, 'Annuler');
insert into messages values (2, 10, 'Confirmer');
insert into messages values (2, 11, 'Suite');
insert into messages values (2, 12, 'Réserver');
insert into messages values (2, 13, 'OK');
insert into messages values (2, 14, 'Commencer');
insert into messages values (2, 15, 'Commencer');
insert into messages values (2, 16, 'Smart Ticket');
insert into messages values (2, 17, 'Vous vous-êtes identifié. Cliquez sur Commencer pour rechercher des films.');
insert into messages values (2, 18, 'Vous n''avez pas de compte. Veuillez en créer un.');
insert into messages values (2, 19, 'Identification en cours...');
insert into messages values (2, 20, 'Chargement des films...');
insert into messages values (2, 21, 'Erreur');
insert into messages values (2, 22, 'Votre compte est créé!');
insert into messages values (2, 23, 'Création du compte utilisateur...');
insert into messages values (2, 24, 'Chargement des localisations...');
insert into messages values (2, 25, 'Chargement des affiches...');
insert into messages values (2, 26, 'Chargement des horaires...');
insert into messages values (2, 27, 'Chargement des plans de places assises...');
insert into messages values (2, 28, 'Réservation des places...');
insert into messages values (2, 29, 'Votre commande a été annulée!');
insert into messages values (2, 30, 'Annulation de la commande...');
insert into messages values (2, 31, 'Merci!');
insert into messages values (2, 32, 'Confirmation de la commande...');
insert into messages values (2, 33, 'Connexion au serveur impossible!');
insert into messages values (2, 34, 'Horaires');
insert into messages values (2, 35, 'Ecran');
insert into messages values (2, 36, 'Films');
insert into messages values (2, 37, 'Cinémas');
insert into messages values (2, 38, 'Arrêter');
insert into messages values (2, 39, 'Saisissez les 4 derniers chiffres de votre carte de crédit:');
insert into messages values (2, 40, 'Film:');
insert into messages values (2, 41, 'Horaire:');
insert into messages values (2, 42, 'Places:');
insert into messages values (2, 43, 'Total:');
insert into messages values (2, 44, '$');
insert into messages values (2, 45, 'Localisation');
insert into messages values (2, 46, 'Chargement des localisations...');
insert into messages values (2, 47, 'Chargement des messages localisés...');
insert into messages values (2, 48, 'Veuillez relancer Smart Ticket pour utiliser la nouvelle localisation');
insert into messages values (2, 49, 'Erreur de transfert');
insert into messages values (2, 50, 'Erreur du serveur');
insert into messages values (2, 51, 'Un autre utilisateur est déjá enregistré sous ce nom');
insert into messages values (2, 52, 'Votre mot de passe est incorrect');
insert into messages values (2, 53, 'Vous n''avez pas de compte utilisateur');
insert into messages values (2, 54, 'Ces places ne sont pas disponibles');
insert into messages values (2, 55, 'Erreur générale de l''application');
insert into messages values (2, 56, 'Le nom d''utilisateur doit être au moins de 4 caractères');
insert into messages values (2, 57, 'Le mot de passe doit être de 6 caractères');
insert into messages values (2, 58, 'Le code postal doit faire 5 caractères');
insert into messages values (2, 59, 'Le numéro de carte de crédit doit faire 12 caractères');
insert into messages values (2, 60, 'Date d''expiration de carte de crédit invalide');
insert into messages values (2, 61, 'Image non disponible');
