Ce framework a été réalisé par Pacôme CAILLETEAU et Nicolas CHUSSEAU

Les méthodes liées aux listes d'adjacences sont dans le dossier `main/java/AdjacencyList`.

Les méthodes liées aux matrices d'adjacences sont dans le dossier `main/java/AdjacencyMatrix`.

Nous avons codé les algorithmes demandés dans les TP2 et TP3 dans le fichier `main/java/GraphAlgorithms/GraphToolsList`.

À l'exception des tas binaires qui sont dans les fichiers `main/java/GraphAlgorithms/BinaryHeap` et `main/java/GraphAlgorithms/BinaryHeapEdge`.

Afin de faciliter les tests, nous avons créé deux fonctions dans le fichier `main/java/GraphAlgorithms/GraphTools` qui utilisent la librairie `graphstream` :
- `representationGraphique` qui permet d'afficher dans une fenêtre graphique un graphe orienté ou non orienté avec les différents poids.
- `drawBinaryHeap` qui permet d'afficher dans une fenêtre graphique un tas binaire.

Afin de lancer les tests, il vous suffit d'exécuter la classe `main` inclue dans chacun des fichiers des dossiers précédemment cités.

Si lors de l'exécution des fonctions `main`, vous obtenez un affichage graphique qui ne vous convient pas (arêtes qui se chevauchent, etc.), veuillez relancer la fonction `main`.
