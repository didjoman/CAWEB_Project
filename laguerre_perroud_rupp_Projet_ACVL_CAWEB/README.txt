# CAWEB_Project

Pour déployer l'application : 

I-Base de donnée
----------------

Attention, comme nous utilisons des triggers, les scripts sql ne peuvent pas être exécutés depuis netbeans. Il faut créer 
la base de donnée dans un SGBD.
Il faut exécuter successivement dans cet ordre les scripts sql suivants:
        - schema_bdd.sql
        - data.sql

Le fichier clean.sql peut servir à supprimer les tables. 

En cas de problème il existe une base de donnée opérationnelle sur ensioracle1
accessible avec les identifiants suivants :
        - nom d'utilisateur : RUPPA
        - mot de passe : RUPPA


II-Déploiement sur Netbeans
---------------------------
        Dans context.xml il faut spécifier les identifiants correspondant à ceux utilisés pour la base de donnée
        (par défaut RUPPA, RUPPA).

