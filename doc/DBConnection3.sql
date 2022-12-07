CREATE TABLE Categorie (
        nomCategorie VARCHAR(100),
        PRIMARY KEY(nomCategorie)
        );
CREATE TABLE APourMere(
        nomCategorieFille VARCHAR(100), 
        nomCategorieMere VARCHAR(100), 
        FOREIGN KEY (nomCategorieFille) REFERENCES Categorie (nomCategorie));
CREATE TABLE Restaurant (
        emailRest VARCHAR(320),
        nomRest VARCHAR(100),
        telRest VARCHAR(20),
        adresseRest VARCHAR(100),
        presentation VARCHAR(500),
        capaciteMax INT,
        PRIMARY KEY (emailRest)
        );
CREATE TABLE EstCategorieDe (
        emailRest VARCHAR(320),
        nomCategorie VARCHAR(100),
        FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest),
        FOREIGN KEY (nomCategorie) REFERENCES Categorie (nomCategorie));
CREATE TABLE Eval (
        dateEval DATE,
        heureEval INT,
        avis VARCHAR(500),
        note INT,
        PRIMARY KEY (dateEval, heureEval, avis, note)
        );
CREATE TABLE Compte (
        idCompte INT,
        PRIMARY KEY (idCompte)
        );
CREATE TABLE Commande (
        dateCommande DATE,
        heureCommande INT,
        idCompte INT,
        emailRest VARCHAR(320),
        prixCommande INT,
        statut VARCHAR(30) CHECK (statut IN ('attente','validee','disponible','livraison','annuleeClient','annuleeRest')),
               
        PRIMARY KEY (dateCommande, heureCommande),
        FOREIGN KEY (idCompte) REFERENCES Compte (idCompte),
        FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest)
        );
CREATE TABLE SurPlace (
        dateCommande DATE,
        heureCommande INT,
        idCompte INT,
        emailRest VARCHAR(320),
        adresseLivraison VARCHAR(100),
        infos VARCHAR(500),
        heureLivraison INT,
        FOREIGN KEY (dateCommande, heureCommande) REFERENCES Commande (dateCommande, heureCommande),
        FOREIGN KEY (idCompte) REFERENCES Compte (idCompte),
        FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest));
CREATE TABLE Livraison (
        dateCommande DATE,
        heureCommande INT,
        idCompte INT,
        emailRest VARCHAR(320),
        nbPersonne INT,
        heureArivee INT,
        FOREIGN KEY (dateCommande, heureCommande) REFERENCES Commande (dateCommande, heureCommande),
        FOREIGN KEY (idCompte) REFERENCES Compte (idCompte),
        FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest));
CREATE TABLE PossedeEvaluation (
        dateCommande DATE,
        heureCommande INT,
        idCompte INT,
        emailRest VARCHAR(320),
        dateEval DATE,
        heureEval INT,
        avis VARCHAR(500),
        note INT,
        FOREIGN KEY (dateEval, heureEval, avis, note) REFERENCES Eval (dateEval, heureEval, avis, note),
        FOREIGN KEY (dateCommande) REFERENCES Commande (dateCommande),
        FOREIGN KEY (idCompte) REFERENCES Compte (idCompte),
        FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest));
CREATE TABLE Client (
        emailClient VARCHAR(320),
        mdp VARCHAR(40),
        nomClient VARCHAR(50),
        prenomClient VARCHAR(50),
        adresseClient VARCHAR(100),
        idCompte INT REFERENCES Compte (idCompte),
        PRIMARY KEY (emailClient)
        );
CREATE TABLE Horaire (
        jour VARCHAR(8),
        heureOuverture INT,heureFermeture INT,
        PRIMARY KEY (jour, heureOuverture, heureFermeture)
        );
CREATE TABLE PossedeHoraires (
        jour VARCHAR(8),
        heureOuverture INT,heureFermeture INT,
        emailRest VARCHAR(320),
        FOREIGN KEY (jour, heureOuverture, heureFermeture) REFERENCES Horaire (jour, heureOuverture, heureFermeture),
               
        FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest));
CREATE TABLE Plat (
        emailRest VARCHAR(320),
        nomPlat VARCHAR(100),
        prix INT,
        descPlat VARCHAR(500),
        PRIMARY KEY (nomPlat),
        FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest));
        
CREATE TABLE Allergene (
        nomAllergene VARCHAR(100) PRIMARY KEY)
CREATE TABLE FaitPartieDe (
        dateCommande DATE,
        heureCommande INT,
        idCompte INT,
        emailRest VARCHAR(320),
        nomPlat VARCHAR(100),
        quantiteCommandee INT,
        FOREIGN KEY (dateCommande) REFERENCES Commande (dateCommande),
        FOREIGN KEY (idCompte) REFERENCES Compte (idCompte),
        FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest),
        FOREIGN KEY (nomPlat) REFERENCES Plat (nomPlat));

CREATE TABLE Contient (
        emailRest VARCHAR(320),
        nomPlat VARCHAR(100),
        nomAllergene VARCHAR(100),
        FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest),
        FOREIGN KEY (nomPlat) REFERENCES Plat (nomPlat),
        FOREIGN KEY (nomAllergene) REFERENCES Allergene (nomAllergene));


INSERT INTO Categorie VALUES ('Cuisine asiatique');
INSERT INTO Categorie VALUES ('Cuisine française');
INSERT INTO Categorie VALUES ('Cuisine métropolitaine');
INSERT INTO Categorie VALUES ('Cuisine des antilles');
INSERT INTO Categorie VALUES ('Cuisine de la Réunion');
INSERT INTO Categorie VALUES ('Cuisine savoyarde');
INSERT INTO Categorie VALUES ('Cuisine mexicaine');
INSERT INTO Categorie VALUES ('Cuisine italienne');
INSERT INTO Categorie VALUES ('Cuisine européenne');
INSERT INTO Restaurant VALUES 
        ('montagne-rouge@outlook.fr',
         'La Montagne Rouge', 
        0169325643, 
        '3 rue de la Montagne', 
        'Un bon restaurant de la montagne.', 
        100);
INSERT INTO Restaurant VALUES 
        ('le-dragon-bleu@sushi.jp',
         'Le Dragon Bleu', 
        0169746643, 
        '3 avenue des brebis', 
        'Un excellent restaurant asiatique.', 
        150);
INSERT INTO Restaurant VALUES 
        ('old-el-paso@wrap.mex',
         'Tacos et Co', 
        0169918243, 
        '3 allée des carottes', 
        'Un super restaurant mexicain.', 
        30);
INSERT INTO Restaurant VALUES 
        ('kssoulet@merguez.fr',
         'La Petit Cassoulet de Mamie', 
        0969325748, 
        '3 rue des pépins', 
        'Un restaurant du terroir comme on les aime.', 
        300);
INSERT INTO Restaurant VALUES 
        ('pizza-campus@orange.fr',
         'Pizza Campus', 
        0169203843, 
        '3 rue Giscard Destaing', 
        'Le restaurant où aller quand le frigo est vide.', 
        200);
INSERT INTO EstCategorieDe VALUES
        ('montagne-rouge@outlook.fr',
        'Cuisine savoyarde');
INSERT INTO EstCategorieDe VALUES
        ('le-dragon-bleu@sushi.jp',
        'Cuisine asiatique');
INSERT INTO EstCategorieDe VALUES
        ('old-el-paso@wrap.mex',
        'Cuisine mexicaine');
INSERT INTO EstCategorieDe VALUES
        ('kssoulet@merguez.fr',
        'Cuisine métropolitaine');
INSERT INTO EstCategorieDe VALUES
        ('pizza-campus@orange.fr',
        'Cuisine italienne');
INSERT INTO APourMere VALUES
        ('Cuisine savoyarde',
        'Cuisine métropolitaine');
INSERT INTO APourMere VALUES
        ('Cuisine métropolitaine',
        'Cuisine française');
INSERT INTO APourMere VALUES
        ('Cuisine des antilles',
        'Cuisine française');
INSERT INTO APourMere VALUES
        ('Cuisine de la Réunion',
        'Cuisine française');
INSERT INTO APourMere VALUES
        ('Cuisine française',
        'Cuisine européenne');
INSERT INTO APourMere VALUES
        ('Cuisine italienne',
        'Cuisine européenne');

INSERT INTO Horaire VALUES('Lundi', 690, 900);

INSERT INTO Horaire VALUES('Lundi', 1110, 1320);

INSERT INTO Horaire VALUES('Mardi', 690, 900);

INSERT INTO Horaire VALUES('Mardi', 1110, 1320);

INSERT INTO Horaire VALUES('Mercredi', 690, 900);

INSERT INTO Horaire VALUES('Mercredi', 1110, 1320);

INSERT INTO Horaire VALUES('Jeudi', 690, 900);

INSERT INTO Horaire VALUES('Jeudi', 1110, 1320);

INSERT INTO Horaire VALUES('Vendredi', 690, 900);

INSERT INTO Horaire VALUES('Vendredi', 1110, 1320);

INSERT INTO Horaire VALUES('Samedi', 690, 900);

INSERT INTO Horaire VALUES('Samedi', 1110, 1320);

INSERT INTO PossedeHoraires VALUES('Lundi', 690, 900, 'montagne-rouge@outlook.fr');

INSERT INTO PossedeHoraires VALUES('Lundi', 1110, 1320, 'montagne-rouge@outlook.fr');

INSERT INTO PossedeHoraires VALUES('Mardi', 690, 900, 'montagne-rouge@outlook.fr');

INSERT INTO PossedeHoraires VALUES('Mardi', 1110, 1320, 'montagne-rouge@outlook.fr');

INSERT INTO PossedeHoraires VALUES('Mercredi', 690, 900, 'montagne-rouge@outlook.fr');

INSERT INTO PossedeHoraires VALUES('Mercredi', 1110, 1320, 'montagne-rouge@outlook.fr');

INSERT INTO PossedeHoraires VALUES('Vendredi', 690, 900, 'montagne-rouge@outlook.fr');

INSERT INTO PossedeHoraires VALUES('Vendredi', 1110, 1320, 'montagne-rouge@outlook.fr');

INSERT INTO PossedeHoraires VALUES('Samedi', 690, 900, 'montagne-rouge@outlook.fr');

INSERT INTO PossedeHoraires VALUES('Samedi', 1110, 1320, 'montagne-rouge@outlook.fr');


INSERT INTO PossedeHoraires VALUES('Lundi', 690, 900, 'le-dragon-bleu@sushi.jp');

INSERT INTO PossedeHoraires VALUES('Lundi', 1110, 1320, 'le-dragon-bleu@sushi.jp');

INSERT INTO PossedeHoraires VALUES('Mardi', 690, 900, 'le-dragon-bleu@sushi.jp');

INSERT INTO PossedeHoraires VALUES('Mardi', 1110, 1320, 'le-dragon-bleu@sushi.jp');

INSERT INTO PossedeHoraires VALUES('Mercredi', 690, 900, 'le-dragon-bleu@sushi.jp');

INSERT INTO PossedeHoraires VALUES('Mercredi', 1110, 1320, 'le-dragon-bleu@sushi.jp');

INSERT INTO PossedeHoraires VALUES('Jeudi', 690, 900, 'le-dragon-bleu@sushi.jp');

INSERT INTO PossedeHoraires VALUES('Jeudi', 1110, 1320, 'le-dragon-bleu@sushi.jp');

INSERT INTO PossedeHoraires VALUES('Samedi', 690, 900, 'le-dragon-bleu@sushi.jp');

INSERT INTO PossedeHoraires VALUES('Samedi', 1110, 1320, 'le-dragon-bleu@sushi.jp');


INSERT INTO PossedeHoraires VALUES('Lundi', 690, 900, 'old-el-paso@wrap.mex');

INSERT INTO PossedeHoraires VALUES('Lundi', 1110, 1320, 'old-el-paso@wrap.mex');

INSERT INTO PossedeHoraires VALUES('Mercredi', 690, 900, 'old-el-paso@wrap.mex');

INSERT INTO PossedeHoraires VALUES('Mercredi', 1110, 1320, 'old-el-paso@wrap.mex');

INSERT INTO PossedeHoraires VALUES('Jeudi', 690, 900, 'old-el-paso@wrap.mex');

INSERT INTO PossedeHoraires VALUES('Jeudi', 1110, 1320, 'old-el-paso@wrap.mex');

INSERT INTO PossedeHoraires VALUES('Vendredi', 690, 900, 'old-el-paso@wrap.mex');

INSERT INTO PossedeHoraires VALUES('Vendredi', 1110, 1320, 'old-el-paso@wrap.mex');

INSERT INTO PossedeHoraires VALUES('Samedi', 690, 900, 'old-el-paso@wrap.mex');

INSERT INTO PossedeHoraires VALUES('Samedi', 1110, 1320, 'old-el-paso@wrap.mex');


INSERT INTO PossedeHoraires VALUES('Lundi', 690, 900, 'kssoulet@merguez.fr');

INSERT INTO PossedeHoraires VALUES('Lundi', 1110, 1320, 'kssoulet@merguez.fr');

INSERT INTO PossedeHoraires VALUES('Mardi', 690, 900, 'kssoulet@merguez.fr');

INSERT INTO PossedeHoraires VALUES('Mardi', 1110, 1320, 'kssoulet@merguez.fr');

INSERT INTO PossedeHoraires VALUES('Jeudi', 690, 900, 'kssoulet@merguez.fr');

INSERT INTO PossedeHoraires VALUES('Jeudi', 1110, 1320, 'kssoulet@merguez.fr');

INSERT INTO PossedeHoraires VALUES('Vendredi', 690, 900, 'kssoulet@merguez.fr');

INSERT INTO PossedeHoraires VALUES('Vendredi', 1110, 1320, 'kssoulet@merguez.fr');

INSERT INTO PossedeHoraires VALUES('Samedi', 690, 900, 'kssoulet@merguez.fr');

INSERT INTO PossedeHoraires VALUES('Samedi', 1110, 1320, 'kssoulet@merguez.fr');


INSERT INTO PossedeHoraires VALUES('Lundi', 690, 900, 'pizza-campus@orange.fr');

INSERT INTO PossedeHoraires VALUES('Lundi', 1110, 1320, 'pizza-campus@orange.fr');

INSERT INTO PossedeHoraires VALUES('Mardi', 690, 900, 'pizza-campus@orange.fr');

INSERT INTO PossedeHoraires VALUES('Mardi', 1110, 1320, 'pizza-campus@orange.fr');

INSERT INTO PossedeHoraires VALUES('Mercredi', 690, 900, 'pizza-campus@orange.fr');

INSERT INTO PossedeHoraires VALUES('Mercredi', 1110, 1320, 'pizza-campus@orange.fr');

INSERT INTO PossedeHoraires VALUES('Jeudi', 690, 900, 'pizza-campus@orange.fr');

INSERT INTO PossedeHoraires VALUES('Jeudi', 1110, 1320, 'pizza-campus@orange.fr');

INSERT INTO PossedeHoraires VALUES('Vendredi', 690, 900, 'pizza-campus@orange.fr');

INSERT INTO PossedeHoraires VALUES('Vendredi', 1110, 1320, 'pizza-campus@orange.fr');

INSERT INTO PossedeHoraires VALUES('Samedi', 690, 900, 'pizza-campus@orange.fr');

INSERT INTO PossedeHoraires VALUES('Samedi', 1110, 1320, 'pizza-campus@orange.fr');

INSERT INTO Plat VALUES ('montagne-rouge@outlook.fr', 'Bières moussues', 15, 'Ça réchauffe le ventre !');

INSERT INTO Plat VALUES ('montagne-rouge@outlook.fr', 'Fondue géante', 29, 'Ne perdez pas votre crouton...');

INSERT INTO Plat VALUES ('le-dragon-bleu@sushi.jp', 'Sushis à volonté', 25, 'Yen a jamais assez.');
INSERT INTO Plat VALUES ('le-dragon-bleu@sushi.jp', 'Sashimi', 12, 'C est bon ça !');
        
INSERT INTO Plat VALUES ('old-el-paso@wrap.mex', 'Sauce Old El Paso du Chef', 19, 'No no Jose.');

INSERT INTO Plat VALUES ('old-el-paso@wrap.mex', 'Patatas', 13, 'Les bonnes fritas con Patatas.');
INSERT INTO Plat VALUES ('old-el-paso@wrap.mex', 'Tacos 8 viandes', 30, 'Ay Caramba !');
        
INSERT INTO Plat VALUES ('kssoulet@merguez.fr', 'Cassoulet', 12, 'Le délicieux cassoulet de Mamie.');

INSERT INTO Plat VALUES ('kssoulet@merguez.fr', 'Risotto', 8, 'Vous reprendrez bien un peu de risotto');

INSERT INTO Plat VALUES ('pizza-campus@orange.fr', 'Pizza peperonne', 10, 'Un pocco di peperonne');

INSERT INTO Plat VALUES ('pizza-campus@orange.fr', 'Pizza Aubergines', 11, 'Qui n aime pas les aubergines');

INSERT INTO Plat VALUES ('pizza-campus@orange.fr', 'Pizza 4 saisons', 9, 'En honneur à Vivaldi');
INSERT INTO Allergene VALUES ('Lactose');
INSERT INTO Allergene VALUES ('Bleu');
INSERT INTO Allergene VALUES ('Gluten');
INSERT INTO Contient VALUES  ('montagne-rouge@outlook.fr', 'Fondue géante', 'Bleu');
INSERT INTO Contient VALUES  ('pizza-campus@orange.fr', 'Pizza 4 saisons', 'Bleu');
INSERT INTO Contient VALUES  ('pizza-campus@orange.fr', 'Pizza 4 saisons', 'Gluten');
INSERT INTO Contient VALUES ('pizza-campus@orange.fr', 'Pizza Aubergines', 'Gluten');
INSERT INTO Contient VALUES ('pizza-campus@orange.fr', 'Pizza peperonne', 'Gluten');
INSERT INTO Contient VALUES ('old-el-paso@wrap.mex', 'Sauce Old El Paso du Chef', 'Lactose');
INSERT INTO Compte VALUES ( idCompte );
        
INSERT INTO Client VALUES ('michel@mich.fr', 'pastis51', 'Sauzin', 'Michel','3 allée des Pétunias, Saint Martin d Heres',
                idCompte++ );
INSERT INTO Compte VALUES ( idCompte );
        
INSERT INTO Client VALUES ('sarah@outlook.fr', 'sarahr0127', 'Rossignol', 'Sarah','8 rue de la Mairie, Brest',
                idCompte++ );
INSERT INTO Compte VALUES ( idCompte );
        
INSERT INTO Client VALUES ('john@connor.terminator', 'T-800', 'Connor', 'John','St Hill Street, Los Angeles',
                idCompte++ );
INSERT INTO Compte VALUES ( idCompte );
        
INSERT INTO Client VALUES ('bugs@warnerbros.com', 'carrot', 'Bunny', 'Bugs','1 Cartoon street, Hollywood',
                idCompte++ );
INSERT INTO Compte VALUES ( idCompte );
        
INSERT INTO Client VALUES ('candice.lebret@gmail.com', 'humanity', 'Lebret', 'Candice','7 rue des tulipes, Grenoble',
                idCompte++ );
INSERT INTO Compte VALUES ( idCompte );
        
INSERT INTO Client VALUES ('friedrich.nietzsche@germany.de', 'ubermensch', 'Nietzsche', 'Friedrich','10 avenue Grand Place',
                idCompte );

