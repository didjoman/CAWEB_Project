# CAWEB_Project

Pour déployer l'application : 

I-Base de donnée

Il faut exécuter successivement dans cet ordre les scripts 
sql suivants (localisés dans CAWEB_project src/main/resources/fr/ensimag/caweb/sql/):
        - schema_bdd.sql
        - data.sql
Dans le même répertoire clean.sql sert à supprimer les tables. 

En cas de problème il existe une base de donnée opérationnelle sur ensioracle1
accessible avec les identifiants suivants :
        - nom d'utilisateur : RUPPA
        - mot de passe : RUPPA

II-Déploiement sur Netbeans
        Dans context.xml il faut spécifier les identifiants correspondant à ceux utilisés pour la base de donnée
        (par défaut RUPPA, RUPPA).

