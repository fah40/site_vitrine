<?php

// Afficher les erreurs pour le debugging
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Vérification des paramètres
if (isset($_GET['file']) && isset($_GET['directory'])) {
    $directory = realpath($_GET['directory']);
    $file = basename($_GET['file']); // Garde uniquement le nom du fichier pour éviter les injections

    // Vérifie si le dossier est autorisé
    if ($directory) {
        $filepath = $directory . DIRECTORY_SEPARATOR . $file;

        // Vérifie si le fichier existe
        if (file_exists($filepath)) {
            header('Content-Type: application/pdf');
            header('Content-Disposition: inline; filename="' . $file . '"');
            readfile($filepath);
            exit;
        } else {
            echo "Fichier non trouvé.";
        }
    } else {
        echo "Accès interdit.";
    }
} else {
    echo "Aucun fichier spécifié.";
}
