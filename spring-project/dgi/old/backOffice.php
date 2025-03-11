<?php
    require_once './inc/fonction.php';

    // if (!isset($_SESSION['usser'])){
    //     header('Location: ../../dgi?error=veuillez vous connecter !');
    // }
    
    $actualites = getActualites();

    // Vérification des messages dans l'URL
    $message = "";
    if (isset($_GET['success'])) {
        $message = "<div class='alert alert-success'>Actualité ajoutée avec succès !</div>";
    } elseif (isset($_GET['error'])) {
        switch ($_GET['error']) {
            case 'insert_failed':
                $message = "<div class='alert alert-danger'>Erreur lors de l'insertion.</div>";
                break;
            case 'upload_failed':
                $message = "<div class='alert alert-danger'>Erreur lors de l'upload de l'image.</div>";
                break;
            case 'invalid_file':
                $message = "<div class='alert alert-danger'>Fichier invalide. Assurez-vous d'utiliser une image (JPEG/PNG/GIF).</div>";
                break;
            default:
                $message = "<div class='alert alert-danger'></div>";
                break;
        }
    }

    $editActualite = null;
    if (isset($_GET['edit_id'])) {
        $editActualite = getById($_GET['edit_id']);
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin</title>
    <!-- okai css -->

    <!-- Icon -->
    <link rel="icon" type="icon" href="./assets/img/logo/LOGO_DGI_OK.png">

    <!-- Montserrat font -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">

    <!-- css -->
    <link rel="stylesheet" href="./assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="./assets/css/bootstrap.css">

    <!-- script -->
    <script src="./js/jquery.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>

    <!-- font awesome css -->
    <link rel="stylesheet" href="./assets/icons/css/all.css">
    <link rel="stylesheet" href="./assets/icons/css/all.min.css">
    <link rel="stylesheet" href="./assets/css/style.css">


</head>
<body>
    <div class="container-fluid">
        <div class="row">
        <div class="col-md-4">
            <form action="./inc/traitement.php" method="post" enctype="multipart/form-data">
                <h3><?= $editActualite ? "Modifier l'actualité" : "Ajouter une actualité" ?></h3><hr>
                <div>
                    <?= $message ?>
                </div><br>
                <div class="mb-3 custom-file-input">
                <?php if (!$editActualite): ?>
                    <label class="custom-file-label" for="customFile">
                        <i class="fas fa-image"></i>
                        Ajouter une image
                    </label>
                    <input name="image" type="file" class="form-control" id="customFile" accept="image/*">
                <?php endif; ?>
                <?php if ($editActualite): ?>
                    <input name="update_id" type="hidden" class="form-control" value="<?= $editActualite->id ?? '' ?>">
                <?php endif; ?>

                </div><br>
                
                <input name="titre" class="form-control bot" placeholder="Titre" value="<?= $editActualite->titre ?? '' ?>"><br>
                <textarea name="description" class="form-control bot" placeholder="Description"><?= $editActualite->description ?? '' ?></textarea><br>
    
                <?php if ($editActualite): ?>
                    <input type="hidden" name="update_id" value="<?= $editActualite->id ?>">
                <?php endif; ?>
                <input class="form-control champ" type="submit" value="<?= $editActualite ? "Mettre à jour" : "Valider" ?>">
            </form>
        </div>
        <div class="col-md-8" style="height: 96vh; overflow-y: auto;">
            <h3>Liste des actualités</h3><hr>
            <?php foreach ($actualites as $actu): ?>
                <div class="card actu">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-4">
                                <img src="<?= $actu['image_url'] ?>" class="img-actu" alt="Image">
                            </div>
                            <div class="col-md-8">
                                <p class="card-text"><small class="text-muted"><?= $actu['date_post'] ?></small></p>
                                <h3 class="card-title"><?= htmlspecialchars($actu['titre']) ?></h3>
                                <p class="card-text"><?= htmlspecialchars($actu['description']) ?></p>
                                <form action="./backOffice.php" method="get" style="display: inline;">
                                    <input type="hidden" name="edit_id" value="<?= $actu['id'] ?>">
                                    <button class="btn btn-warning btn-sm">Modifier</button>
                                </form>
                                <form action="./inc/traitement.php" method="post" style="display: inline;">
                                    <input type="hidden" name="delete" value="<?= $actu['id'] ?>">
                                    <button class="btn btn-danger btn-sm">Supprimer</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div><br>
            <?php endforeach; ?>               
        </div>

        </div>
    </div>
</body>
</html>