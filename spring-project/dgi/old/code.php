<!DOCTYPE html>
<!-- saved from url=(0050)https://miller.bslthemes.com/pixy-demo/home-1.html -->
<html lang="zxx" class="swup-enabled">

<?php
    // Dossier contenant les fichiers PDF
    $directory = "./documents/codes"; // Remplacez par le chemin de votre dossier
    // Récupération des fichiers PDF
    $files = array_diff(scandir($directory), ['.', '..']);
?>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <!-- grid css -->
    <link rel="stylesheet" href="./assets/css/bootstrap-grid.css">

    <!-- font awesome css -->
    <link rel="stylesheet" href="./assets/icons/css/all.css">
    <link rel="stylesheet" href="./assets/icons/css/all.min.css">

    <!-- swiper css -->
    <link rel="stylesheet" href="./assets/css/swiper.min.css">

    <!-- okai css -->
    <link rel="stylesheet" href="./assets/css/style-friendly.css">

    <!-- page title -->
    <title>DGI</title>

    <!-- Icon -->
    <link rel="icon" type="icon" href="./assets/img/logo/LOGO_DGI_OK.png">

    <!-- Montserrat font -->
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">

</head>

<body style="height: 150vh; overscroll-behavior: none;">
    

    <!-- wrapper -->
    <div id="smooth-wrapper" class="fah-page-wrapper" style="inset: 0px; width: 100%; height: 100%; position: fixed; overflow: hidden;">
        <div id="swup-opm"></div>

        <!-- cursor -->
        <div class="fah-cursor-follower" style="top: 15px; left: 420px;"></div>
        <!-- cursor end -->

        <!-- scroll progress -->
        <div class="fah-progress-track">
            <div class="fah-progress" style="height: 0%;"></div>
        </div>
        <!-- scroll progress end -->

        <!-- fixed elements -->
        <div class="fah-fixed">
            <div class="fah-top-panel">
                <div class="fah-left-side">
                    <a href="#" class="fah-logo fah-scroll-to " data-no-swup="">
                        <img src="./assets/img/logo/LOGO_monochrome_MEF_DGI.png" class="logo">
                    </a>
                </div>
                <div class="fah-buttons-tp-frame fah-c-gone">
                    <p class="fah-stylized fah-m1 fah-phone"><span class="fah-m2">Phone:</span> +28 (054) 167 xx 77</p>
                    <div class="fah-buttons">
                        <a href="index.html" class="fah-tp-btn"><i class="fa fa-home"></i></a>
                    </div>
                </div>
            </div>
        </div>
        <!-- fixed elements end -->

        <!-- page transition -->
        <div class="fah-transition-fade" id="swup">
            <div class="fah-transition-frame">

                <!-- content -->
                <div id="smooth-content" class="fah-content" style="width: 100%;height: auto !important; overflow: visible; transform: matrix3d(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);">

                    <!-- hero -->
                    <div class="fah-hero-1 fah-sm-hero fah-up" id="top" style="translate: none; rotate: none; scale: none; transform: translate(0px, 0px); opacity: 1;">
                        <div class="container fah-hero-main fah-relative fah-aic">
                            <div class="fah-hero-text fah-scale-img" data-value-1="1.3" data-value-2="0.95" style="translate: none; rotate: none; scale: none; transform: translate3d(0px, 0px, 0px) scale(0.9864, 0.9864);">
                                <div class="fah-text-pad"></div>
                                <div class="fah-word-frame">
                                    <h1 class="fah-head1 fah-mb60 fah-rubber"><span class="fah-a2">CODES ET MANUELS</span></h1>
                                    
                                </div>
                            </div>
                            <div class="fah-shapes fah-scale-img" data-value-1=".7" data-value-2="1.11" style="translate: none; rotate: none; scale: none; transform: translate3d(0px, 0px, 0px) scale(1.0694, 1.0694);">
                                <div class="fah-s-2"><img src="./assets/img/conex1.png" alt="shape"></div>
                                <div class="fah-s-3"><img src="./assets/img/conex2.png" alt="shape"></div>
                            </div>
                        </div>
                    </div>
                    <!-- hero end -->

                    <!-- contact info -->
                    <div class="fah-p-0-100">
                        <div class="container">
                            <div class="row fah-jcc">
                                <div class="col-sm-4 col-lg-4">
                                    <div class="fah-iconbox fah-tac fah-mb60">
                                        <i class="fa fa-book fah-mb30 fah-up" style="translate: none; rotate: none; scale: none; transform: translate(0px, 0px); opacity: 1;"></i>
                                        <h4 class="fah-head4 fah-mb30 fah-up lienContact" style="translate: none; rotate: none; scale: none; transform: translate(0px, 0px); opacity: 1;"></h4>
                                        <p class="fah-text-md fah-up" style="translate: none; rotate: none; scale: none; transform: translate(0px, 0px); opacity: 1; text-align:right;">Consultez ici l'ensemble des codes fiscaux et manuels de référence.</p>
                                    </div>
                                </div>
                                
                                <div class="col-sm-4 col-lg-4">
                                    <div class="fah-iconbox fah-tac fah-mb60">
                                        <ul> 
                                        <?php foreach ($files as $file): ?>
                                            <li>
                                                <!-- Lien vers le fichier PDF avec target="_blank" -->
                                                <a class="fah-up" href="view_pdf.php?directory=<?= urlencode($directory) ?>&file=<?= urlencode($file) ?>" target="_blank">
                                                    <?= htmlspecialchars($file) ?>
                                                </a>
                                            </li>
                                        <?php endforeach; ?> 
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- contact info end -->
                </div>
                <!-- content -->

            </div>
        </div>
        <!-- page transition -->

    </div>
    <!-- wrapper end -->

    <!-- swup js -->
    <script src="./js/swup@4"></script>

    <!-- gsap js -->
    <script src="./js/gsap.min.js"></script>

    <!-- scroll smoother -->
    <script src="./js/ScrollSmoother.min.js"></script>

    <!-- scroll trigger js -->
    <script src="./js/ScrollTrigger.min.js"></script>

    <!-- scroll to js -->
    <script src="./js/ScrollTo.min.js"></script>

    <!-- swiper js -->
    <script src="./js/swiper.min.js"></script>

    <!-- parallax js -->
    <script src="./js/parallax.js"></script>

    <!-- pixy js -->
    <script src="./js/autre.js"></script>
    </body>
</html>