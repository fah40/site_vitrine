/* -------------------------------------------

------------------------------------------- */

document.addEventListener("DOMContentLoaded", function () {
    "use strict";

    /* -------------------------------------------

    swup

    ------------------------------------------- */

    /* -------------------------------------------

    register gsap plugins

    ------------------------------------------- */
    gsap.registerPlugin(ScrollTrigger, ScrollSmoother, ScrollToPlugin);
    /* -------------------------------------------

    ScrollSmoother

    ------------------------------------------- */
    ScrollSmoother.create({
        smooth: 1,
        effects: true,
        smoothTouch: 0.1,
    });

    /* -------------------------------------------
    cursor

    ------------------------------------------- */

    var follower = document.querySelector(".fah-cursor-follower");
    var posX = 0,
        posY = 0;
    var mouseX = 0,
        mouseY = 0;

    gsap.ticker.add(function () {
        posX += (mouseX - posX) / 29;
        posY += (mouseY - posY) / 29;
        gsap.set(follower, {
            css: {
                left: posX,
                top: posY
            }
        });
    });

    function addHoverEffect(selector, className) {
        document.querySelectorAll(selector).forEach(function (link) {
            link.addEventListener("mouseenter", function () {
                follower.classList.add(className);
            });
            link.addEventListener("mouseleave", function () {
                follower.classList.remove(className);
            });
        });
    }

    addHoverEffect(".fah-c-light", "fah-light-active");
    addHoverEffect(".fah-c-dark", "fah-dark-active");
    addHoverEffect(".fah-c-gone", "fah-gone-active");
    addHoverEffect(".fah-c-view", "fah-view-active");
    addHoverEffect(".fah-c-next", "fah-next-active");
    addHoverEffect(".fah-c-read", "fah-read-active");
    addHoverEffect(".fah-c-swipe", "fah-swipe-active");

    document.addEventListener("mousemove", function (e) {
        mouseX = e.clientX;
        mouseY = e.clientY;
    });

    /* -------------------------------------------

    cursor parallax

    ------------------------------------------- */
    var scene1 = document.getElementById('scene');
    if (scene1) {
        var parallaxInstance1 = new Parallax(scene1, {
            limitY: 10,
        });
    }

    var scene2 = document.getElementById('scene-2');
    if (scene2) {
        var parallaxInstance2 = new Parallax(scene2, {
            limitY: 10,
        });
    }
    /* -------------------------------------------

    SECTION REDIRECTION
    ------------------------------------------- */
    document.addEventListener('click', function (event) {
        const menuBtn = event.target.closest('.fah-menu-btn');
        const menuFrame = document.querySelector('.fah-menu-frame');
        const btnFrame = document.querySelector('.fah-buttons-tp-frame');
        // const tp2 = document.querySelector('.fah-top-panel-2');

        if (menuBtn) {
            menuBtn.classList.toggle('fah-active');
            menuFrame.classList.toggle('fah-active');
            btnFrame.classList.toggle('fah-active');
            // tp2.classList.toggle('fah-menu-open');
        } else if (event.target.closest('.fah-menu-frame') && !event.target.closest('.fah-menu-frame > *')) {
            menuFrame.classList.remove('fah-active');
            btnFrame.classList.remove('fah-active');
            document.querySelector('.fah-menu-btn').classList.remove('fah-active');
            // tp2.classList.remove('fah-menu-open');
        }
    });

    document.querySelectorAll('a').forEach(link => {
        link.addEventListener('click', function (event) {
            const href = this.getAttribute('href');
    
            if (href) {
                if (href.startsWith('#')) {
                    // Gestion des ancres
                    const targetId = href.substring(1); // Enlève le #  
                    const targetElement = document.getElementById(targetId);
    
                    if (targetElement) {
                        event.preventDefault();
                        const targetPosition = targetElement.getBoundingClientRect().top + window.pageYOffset;
                        smoothScrollTo(targetPosition, 1000); // Fonction de défilement fluide
                    }
                } else if (href.startsWith('http')) {
                    // Lien externe, laissez le comportement par défaut
                    return;
                } else {
                    // Lien interne
                    event.preventDefault();
                    window.location.href = href; // Pas de `window.location.reload()`
                }
    
                // Fermez les menus, quel que soit le type de lien
                document.querySelector('.fah-menu-btn').classList.remove('fah-active');
                document.querySelector('.fah-menu-frame').classList.remove('fah-active');
                document.querySelector('.fah-buttons-tp-frame').classList.remove('fah-active');
            } else {
                event.preventDefault(); // Empêcher les clics sur des liens sans href
            }
        });
    });
    


    function isValidHref(href) {
        return href && href.trim() !== '' && href.length > 1 && !/^#(\.|$)/.test(href);
    }

    document.querySelectorAll('.fah-has-children > a').forEach(link => {
        link.addEventListener('click', function (event) {
            event.stopPropagation();
            event.preventDefault(); // Додаємо, щоб уникнути переходу за посиланням

            const parentElement = link.parentElement;
            const isActive = parentElement.classList.contains('fah-active');

            document.querySelectorAll('.fah-has-children').forEach(el => {
                const ul = el.querySelector('ul');
                el.classList.remove('fah-active');
                if (ul) ul.style.maxHeight = '0';
            });

            if (!isActive) {
                parentElement.classList.add('fah-active');
                const ul = parentElement.querySelector('ul');
                if (ul) ul.style.maxHeight = `${ul.scrollHeight}px`;
            }
        });
    });

    let lastScrollTop = 0;

    window.addEventListener('scroll', () => {
        const topPanel = document.querySelector('.fah-top-panel-2');
        const menuFrame = document.querySelector('.fah-menu-frame-2');
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop;

        if (menuFrame && menuFrame.classList.contains('fah-active')) {
            return; // Stop execution if .fah-active class is present
        }

        if (topPanel && scrollTop > lastScrollTop) {
            topPanel.classList.add('fah-scroll');
        } else if (topPanel && scrollTop < lastScrollTop && scrollTop === 0) {
            topPanel.classList.remove('fah-scroll');
        }

        lastScrollTop = scrollTop <= 0 ? 0 : scrollTop;
    });


    /* -------------------------------------------

    onepage navigation

    ------------------------------------------- */
    document.querySelectorAll('.fah-onepage-nav > li > a, .fah-scroll-to').forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault();
            const targetId = this.getAttribute('href');
            const targetElement = document.querySelector(targetId);
            if (!targetElement) return;

            const targetPosition = targetElement.getBoundingClientRect().top + window.pageYOffset;
            const currentPosition = window.pageYOffset;
            const distance = Math.abs(targetPosition - currentPosition);
            const baseDuration = 0.1; // Base duration in seconds
            const duration = baseDuration + (distance / 4000); // Adjust this factor as needed

            const offsetY = window.innerWidth < 992 ? 120 : 160;
            gsap.to(window, {
                duration: duration,
                ease: 'sine',
                scrollTo: {
                    y: targetElement,
                    offsetY: offsetY
                }
            });
        });
    });

    /* -------------------------------------------

    scrollbar

    ------------------------------------------- */
    gsap.to('.fah-progress', {
        height: '100%',
        ease: 'sine',
        scrollTrigger: {
            scrub: 0.3
        }
    });

    /* -------------------------------------------

    ruber letters

    ------------------------------------------- */
    const headings = document.querySelectorAll('.fah-rubber');

    headings.forEach(heading => {
        const textNodes = [];

        heading.childNodes.forEach(node => {
            if (node.nodeType === Node.TEXT_NODE) {
                node.textContent.split(' ').forEach((word, index, array) => {
                    const wordSpan = document.createElement('span');
                    wordSpan.classList.add('fah-word-span');
                    word.split('').forEach(letter => {
                        const letterSpan = document.createElement('span');
                        letterSpan.classList.add('fah-letter-span');
                        letterSpan.textContent = letter;
                        wordSpan.appendChild(letterSpan);
                    });
                    textNodes.push(wordSpan);
                    if (index < array.length - 1) {
                        textNodes.push(document.createTextNode(' '));
                    }
                });
            } else if (node.nodeType === Node.ELEMENT_NODE) {
                textNodes.push(node.cloneNode(true));
            }
        });

        heading.innerHTML = '';
        textNodes.forEach(node => heading.appendChild(node));

        const letters = heading.querySelectorAll('.fah-letter-span');
        letters.forEach(letter => {
            letter.addEventListener('mouseenter', () => {
                gsap.to(letter, {
                    scaleY: 1.1,
                    y: '-5%',
                    duration: 0.2,
                    ease: 'sine'
                });
            });

            letter.addEventListener('mouseleave', () => {
                gsap.to(letter, {
                    scaleY: 1,
                    y: '0%',
                    duration: 0.2,
                    ease: 'sine'
                });
            });
        });
    });

    /* -------------------------------------------

    counters

    ------------------------------------------- */
    const numbers = document.querySelectorAll(".fah-counter");

    if (numbers.length > 0) {
        numbers.forEach(element => {
            const zero = {
                val: 0
            };
            const num = parseFloat(element.dataset.number);
            const split = num.toString().split(".");
            const decimals = split.length > 1 ? split[1].length : 0;

            gsap.to(zero, {
                val: num,
                duration: 1.8,
                scrollTrigger: {
                    trigger: element,
                    toggleActions: 'play none none reverse',
                },
                onUpdate: function () {
                    element.textContent = zero.val.toFixed(decimals);
                }
            });
        });
    }

    /* -------------------------------------------

    scroll animation

    ------------------------------------------- */
    const appearance = document.querySelectorAll(".fah-up");
    appearance.forEach((section) => {
        gsap.fromTo(section, {
            opacity: 0,
            y: 40,
            scale: 1.04,
            ease: 'sine',
        }, {
            y: 0,
            opacity: 1,
            scale: 1,
            scrollTrigger: {
                trigger: section,
                toggleActions: 'play none none reverse',
            }
        });
    });

    /* -------------------------------------------

    parallax animation

    ------------------------------------------- */

    const parallaxImages = document.querySelectorAll(".fah-parallax-img");

    parallaxImages.forEach((section) => {
        var value1 = section.getAttribute("data-value-1");
        var value2 = section.getAttribute("data-value-2");

        gsap.fromTo(section, {
            ease: 'sine',
            y: value1
        }, {
            y: value2,
            scrollTrigger: {
                trigger: section,
                scrub: true,
                toggleActions: 'play none none reverse'
            }
        });
    });

    /* -------------------------------------------

    parallax x animation

    ------------------------------------------- */

    const parallaxXImages = document.querySelectorAll(".fah-parallax-x-img");

    parallaxXImages.forEach((section) => {
        var value1 = section.getAttribute("data-value-1");
        var value2 = section.getAttribute("data-value-2");

        gsap.fromTo(section, {
            ease: 'sine',
            x: value1
        }, {
            x: value2,
            scrollTrigger: {
                trigger: section,
                scrub: true,
                toggleActions: 'play none none reverse'
            }
        });
    });


    /* -------------------------------------------

    scale animation

    ------------------------------------------- */
    const scaleImage = document.querySelectorAll(".fah-scale-img");

    scaleImage.forEach((section) => {
        var value1 = section.getAttribute("data-value-1");
        var value2 = section.getAttribute("data-value-2");

        if (window.innerWidth < 1200) {
            value1 = Math.max(.95, value1);
        }

        gsap.fromTo(section, {
            ease: 'sine',
            scale: value1,
        }, {
            scale: value2,
            scrollTrigger: {
                trigger: section,
                scrub: true,
                toggleActions: 'play none none reverse',
            }
        });
    });

    /* -------------------------------------------

    rotate animation

    ------------------------------------------- */
    const rotate = document.querySelectorAll(".fah-rotate");

    rotate.forEach((section) => {
        var value = section.getAttribute("data-value");
        gsap.fromTo(section, {
            ease: 'sine',
            rotate: 0,
        }, {
            rotate: value,
            scrollTrigger: {
                trigger: section,
                scrub: true,
                toggleActions: 'play none none reverse',
            }
        });
    });

    /* -------------------------------------------

    add class

    ------------------------------------------- */
    function addClassToElement(element) {
        if (element) {
            element.classList.add('fah-added');
        }
    }

    function removeClassFromElement(element) {
        if (element) {
            element.classList.remove('fah-added');
        }
    }

    document.querySelectorAll('.fah-add-class').forEach(element => {
        ScrollTrigger.create({
            trigger: element,
            toggleActions: 'play none none reverse',
            onEnter: () => addClassToElement(element),
            onLeaveBack: () => removeClassFromElement(element)
        });
    });

    /* -------------------------------------------

    sliders

    ------------------------------------------- */

    var swiper = new Swiper('.fah-blog-slider', {
        parallax: true,
        autoHeight: true,
        spaceBetween: 30,
        slidesPerView: 1,
        speed: 800,
        navigation: {
            prevEl: '.fah-nl-prev',
            nextEl: '.fah-nl-next',
        },
        breakpoints: {
            992: {
                slidesPerView: 2,
            },
        },
        on: {
            slideChangeTransitionEnd: function () {
                ScrollTrigger.refresh();
            }
        }
    });

    var swiper = new Swiper('.fah-blog-slider-sm', {
        parallax: true,
        autoHeight: true,
        spaceBetween: 30,
        slidesPerView: 1,
        speed: 800,
        navigation: {
            prevEl: '.fah-sb-prev',
            nextEl: '.fah-sb-next',
        },
        breakpoints: {
            992: {
                slidesPerView: 2,
            },
        },
        on: {
            slideChangeTransitionEnd: function () {
                ScrollTrigger.refresh();
            }
        }
    });

    var swiper = new Swiper('.fah-reviews-slider', {
        parallax: true,
        autoHeight: true,
        spaceBetween: 120,
        slidesPerView: 1,
        initialSlide: 1,
        speed: 800,
        pagination: {
            el: ".fah-sr-pagination",
            clickable: true,
        },
        navigation: {
            prevEl: '.fah-sr-prev',
            nextEl: '.fah-sr-next',
        },
        on: {
            slideChangeTransitionEnd: function () {
                ScrollTrigger.refresh();
            }
        }
    });

    var swiper = new Swiper('.fah-project-slider', {
        parallax: true,
        autoHeight: true,
        spaceBetween: 30,
        slidesPerView: 1,
        speed: 800,
        breakpoints: {
            992: {
                slidesPerView: 2,
            },
        },
        on: {
            slideChangeTransitionEnd: function () {
                ScrollTrigger.refresh();
            }
        }
    });

    /* ----------------------------------------------------------------------------
    -------------------------------------------------------------------------------

    reinit

    -------------------------------------------------------------------------------
    ---------------------------------------------------------------------------- */
    
    document.addEventListener('swup:pageView', () => {

        /* -------------------------------------------

        register gsap plugins

        ------------------------------------------- */
        gsap.registerPlugin(ScrollTrigger, ScrollSmoother, ScrollToPlugin);
        /* -------------------------------------------

        ScrollSmoother

        ------------------------------------------- */
        ScrollSmoother.create({
            smooth: 1,
            effects: true,
            smoothTouch: 0.1,
        });

        /* -------------------------------------------

        cursor

        ------------------------------------------- */

        const elements = document.querySelectorAll('.fah-cursor-follower');

        elements.forEach(element => {
            element.className = 'fah-cursor-follower';
        });

        function addHoverEffect(selector, className) {
            document.querySelectorAll(selector).forEach(function (link) {
                link.addEventListener("mouseenter", function () {
                    follower.classList.add(className);
                });
                link.addEventListener("mouseleave", function () {
                    follower.classList.remove(className);
                });
            });
        }

        addHoverEffect(".fah-c-light", "fah-light-active");
        addHoverEffect(".fah-c-dark", "fah-dark-active");
        addHoverEffect(".fah-c-gone", "fah-gone-active");
        addHoverEffect(".fah-c-view", "fah-view-active");
        addHoverEffect(".fah-c-next", "fah-next-active");
        addHoverEffect(".fah-c-read", "fah-read-active");
        addHoverEffect(".fah-c-swipe", "fah-swipe-active");

        document.addEventListener("mousemove", function (e) {
            mouseX = e.clientX;
            mouseY = e.clientY;
        });

        /* -------------------------------------------

        cursor parallax

        ------------------------------------------- */
        var scene1 = document.getElementById('scene');
        if (scene1) {
            var parallaxInstance1 = new Parallax(scene1, {
                limitY: 10,
            });
        }

        var scene2 = document.getElementById('scene-2');
        if (scene2) {
            var parallaxInstance2 = new Parallax(scene2, {
                limitY: 10,
            });
        }

        /* -------------------------------------------

        menu

        ------------------------------------------- */

        document.querySelectorAll('.fah-main-menu li a').forEach(link => {
            link.addEventListener('click', function (event) {
                const href = this.getAttribute('href');

                if (isValidHref(href)) {
                    document.querySelector('.fah-menu-btn').classList.remove('fah-active');
                    document.querySelector('.fah-menu-frame').classList.remove('fah-active');
                    document.querySelector('.fah-buttons-tp-frame').classList.remove('fah-active');
                    // document.querySelector('.fah-top-panel-2').classList.remove('fah-menu-open');
                } else {
                    event.preventDefault(); // Якщо href невалідний, зупиняємо дію за замовчуванням
                }
            });
        });

        function isValidHref(href) {
            return href && href.trim() !== '' && href.length > 1 && !/^#(\.|$)/.test(href);
        }

        document.querySelectorAll('.fah-has-children > a').forEach(link => {
            link.addEventListener('click', function (event) {
                event.stopPropagation();
                event.preventDefault(); // Додаємо, щоб уникнути переходу за посиланням

                const parentElement = link.parentElement;
                const isActive = parentElement.classList.contains('fah-active');

                document.querySelectorAll('.fah-has-children').forEach(el => {
                    const ul = el.querySelector('ul');
                    el.classList.remove('fah-active');
                    if (ul) ul.style.maxHeight = '0';
                });

                if (!isActive) {
                    parentElement.classList.add('fah-active');
                    const ul = parentElement.querySelector('ul');
                    if (ul) ul.style.maxHeight = `${ul.scrollHeight}px`;
                }
            });
        });
        /* -------------------------------------------

        onepage navigation

        ------------------------------------------- */
        document.querySelectorAll('.fah-onepage-nav > li > a, .fah-scroll-to').forEach(link => {
            link.addEventListener('click', function (event) {
                event.preventDefault();
                const targetId = this.getAttribute('href');
                const targetElement = document.querySelector(targetId);
                if (!targetElement) return;

                const targetPosition = targetElement.getBoundingClientRect().top + window.pageYOffset;
                const currentPosition = window.pageYOffset;
                const distance = Math.abs(targetPosition - currentPosition);
                const baseDuration = 0.1; // Base duration in seconds
                const duration = baseDuration + (distance / 4000); // Adjust this factor as needed

                const offsetY = window.innerWidth < 992 ? 120 : 160;
                gsap.to(window, {
                    duration: duration,
                    ease: 'sine',
                    scrollTo: {
                        y: targetElement,
                        offsetY: offsetY
                    }
                });
            });
        });

        /* -------------------------------------------

        ruber letters

        ------------------------------------------- */
        const headings = document.querySelectorAll('.fah-rubber');

        headings.forEach(heading => {
            const textNodes = [];

            heading.childNodes.forEach(node => {
                if (node.nodeType === Node.TEXT_NODE) {
                    node.textContent.split(' ').forEach((word, index, array) => {
                        const wordSpan = document.createElement('span');
                        wordSpan.classList.add('fah-word-span');
                        word.split('').forEach(letter => {
                            const letterSpan = document.createElement('span');
                            letterSpan.classList.add('fah-letter-span');
                            letterSpan.textContent = letter;
                            wordSpan.appendChild(letterSpan);
                        });
                        textNodes.push(wordSpan);
                        if (index < array.length - 1) {
                            textNodes.push(document.createTextNode(' '));
                        }
                    });
                } else if (node.nodeType === Node.ELEMENT_NODE) {
                    textNodes.push(node.cloneNode(true));
                }
            });

            heading.innerHTML = '';
            textNodes.forEach(node => heading.appendChild(node));

            const letters = heading.querySelectorAll('.fah-letter-span');
            letters.forEach(letter => {
                letter.addEventListener('mouseenter', () => {
                    gsap.to(letter, {
                        scaleY: 1.1,
                        y: '-5%',
                        duration: 0.2,
                        ease: 'sine'
                    });
                });

                letter.addEventListener('mouseleave', () => {
                    gsap.to(letter, {
                        scaleY: 1,
                        y: '0%',
                        duration: 0.2,
                        ease: 'sine'
                    });
                });
            });
        });


        /* -------------------------------------------

        counters

        ------------------------------------------- */
        const numbers = document.querySelectorAll(".fah-counter");

        if (numbers.length > 0) {
            numbers.forEach(element => {
                const zero = {
                    val: 0
                };
                const num = parseFloat(element.dataset.number);
                const split = num.toString().split(".");
                const decimals = split.length > 1 ? split[1].length : 0;

                gsap.to(zero, {
                    val: num,
                    duration: 1.8,
                    scrollTrigger: {
                        trigger: element,
                        toggleActions: 'play none none reverse',
                    },
                    onUpdate: function () {
                        element.textContent = zero.val.toFixed(decimals);
                    }
                });
            });
        }

        /* -------------------------------------------

        scroll animation

        ------------------------------------------- */
        const appearance = document.querySelectorAll(".fah-up");
        appearance.forEach((section) => {
            gsap.fromTo(section, {
                opacity: 0,
                y: 40,
                scale: 1.04,
                ease: 'sine',
            }, {
                y: 0,
                opacity: 1,
                scale: 1,
                scrollTrigger: {
                    trigger: section,
                    toggleActions: 'play none none reverse',
                }
            });
        });

        /* -------------------------------------------

        parallax animation

        ------------------------------------------- */

        const parallaxImages = document.querySelectorAll(".fah-parallax-img");

        parallaxImages.forEach((section) => {
            var value1 = section.getAttribute("data-value-1");
            var value2 = section.getAttribute("data-value-2");

            gsap.fromTo(section, {
                ease: 'sine',
                y: value1
            }, {
                y: value2,
                scrollTrigger: {
                    trigger: section,
                    scrub: true,
                    toggleActions: 'play none none reverse'
                }
            });
        });

        /* -------------------------------------------

        parallax x animation

        ------------------------------------------- */

        const parallaxXImages = document.querySelectorAll(".fah-parallax-x-img");

        parallaxXImages.forEach((section) => {
            var value1 = section.getAttribute("data-value-1");
            var value2 = section.getAttribute("data-value-2");

            gsap.fromTo(section, {
                ease: 'sine',
                x: value1
            }, {
                x: value2,
                scrollTrigger: {
                    trigger: section,
                    scrub: true,
                    toggleActions: 'play none none reverse'
                }
            });
        });


        /* -------------------------------------------

        scale animation

        ------------------------------------------- */
        const scaleImage = document.querySelectorAll(".fah-scale-img");

        scaleImage.forEach((section) => {
            var value1 = section.getAttribute("data-value-1");
            var value2 = section.getAttribute("data-value-2");

            if (window.innerWidth < 1200) {
                value1 = Math.max(.95, value1);
            }

            gsap.fromTo(section, {
                ease: 'sine',
                scale: value1,
            }, {
                scale: value2,
                scrollTrigger: {
                    trigger: section,
                    scrub: true,
                    toggleActions: 'play none none reverse',
                }
            });
        });

        /* -------------------------------------------

        rotate animation

        ------------------------------------------- */
        const rotate = document.querySelectorAll(".fah-rotate");

        rotate.forEach((section) => {
            var value = section.getAttribute("data-value");
            gsap.fromTo(section, {
                ease: 'sine',
                rotate: 0,
            }, {
                rotate: value,
                scrollTrigger: {
                    trigger: section,
                    scrub: true,
                    toggleActions: 'play none none reverse',
                }
            });
        });

        /* -------------------------------------------

        add class

        ------------------------------------------- */
        function addClassToElement(element) {
            if (element) {
                element.classList.add('fah-added');
            }
        }

        function removeClassFromElement(element) {
            if (element) {
                element.classList.remove('fah-added');
            }
        }

        document.querySelectorAll('.fah-add-class').forEach(element => {
            ScrollTrigger.create({
                trigger: element,
                toggleActions: 'play none none reverse',
                onEnter: () => addClassToElement(element),
                onLeaveBack: () => removeClassFromElement(element)
            });
        });
        /* -------------------------------------------

        sliders

        ------------------------------------------- */

        var swiper = new Swiper('.fah-blog-slider', {
            parallax: true,
            autoHeight: true,
            spaceBetween: 30,
            slidesPerView: 1,
            speed: 800,
            navigation: {
                prevEl: '.fah-nl-prev',
                nextEl: '.fah-nl-next',
            },
            breakpoints: {
                992: {
                    slidesPerView: 2,
                },
            },
            on: {
                slideChangeTransitionEnd: function () {
                    ScrollTrigger.refresh();
                }
            }
        });

        var swiper = new Swiper('.fah-blog-slider-sm', {
            parallax: true,
            autoHeight: true,
            spaceBetween: 30,
            slidesPerView: 1,
            speed: 800,
            navigation: {
                prevEl: '.fah-sb-prev',
                nextEl: '.fah-sb-next',
            },
            breakpoints: {
                992: {
                    slidesPerView: 2,
                },
            },
            on: {
                slideChangeTransitionEnd: function () {
                    ScrollTrigger.refresh();
                }
            }
        });

        var swiper = new Swiper('.fah-reviews-slider', {
            parallax: true,
            autoHeight: true,
            spaceBetween: 120,
            slidesPerView: 1,
            initialSlide: 1,
            speed: 800,
            pagination: {
                el: ".fah-sr-pagination",
                clickable: true,
            },
            navigation: {
                prevEl: '.fah-sr-prev',
                nextEl: '.fah-sr-next',
            },
            on: {
                slideChangeTransitionEnd: function () {
                    ScrollTrigger.refresh();
                }
            }
        });

        var swiper = new Swiper('.fah-project-slider', {
            parallax: true,
            autoHeight: true,
            spaceBetween: 30,
            slidesPerView: 1,
            speed: 800,
            breakpoints: {
                992: {
                    slidesPerView: 2,
                },
            },
            on: {
                slideChangeTransitionEnd: function () {
                    ScrollTrigger.refresh();
                }
            }
        });
    });

});


function smoothScrollTo(targetPosition, duration) {
    const startPosition = window.scrollY || window.pageYOffset;
    const distance = targetPosition - startPosition;
    let startTime = null;

    function animation(currentTime) {
        if (startTime === null) startTime = currentTime;
        const timeElapsed = currentTime - startTime;
        const progress = Math.min(timeElapsed / duration, 1);
        const ease = progress < 0.5
            ? 2 * progress * progress
            : 1 - Math.pow(-2 * progress + 2, 2) / 2; // Courbe d'accélération

        window.scrollTo(0, startPosition + distance * ease);

        if (timeElapsed < duration) {
            requestAnimationFrame(animation);
        }
    }

    requestAnimationFrame(animation);
}

let currentSlide = 0;

function showSlide(index) {
    const slides = document.querySelectorAll('.carousel-item');
    const totalSlides = slides.length;

    // Wrap around the slide index
    if (index >= totalSlides) {
        currentSlide = 0;
    } else if (index < 0) {
        currentSlide = totalSlides - 2;
    } else {
        currentSlide = index;
    }

    // Move the carousel
    const carouselInner = document.querySelector('.carousel-inner');
    carouselInner.style.transform = `translateX(-${currentSlide * 100}%)`;
}

function nextSlide() {
    showSlide(currentSlide + 1);
}

function prevSlide() {
    showSlide(currentSlide - 1);
}

// Auto-slide every 5 seconds
setInterval(() => {
    nextSlide();
}, 10000);

//============================================

function animateCounter(counter) {
  const target = +counter.getAttribute('data-target'); // Convertir en nombre
  const increment = target / 200; // Ajustez cette valeur pour modifier la vitesse
  let current = 0;

  const updateCounter = () => {
    if (current < target) {
      current += increment;
      counter.innerText = Math.ceil(current).toLocaleString('fr-FR');
      setTimeout(updateCounter, 15); // Ajustez la vitesse en ms
    } else {
      counter.innerText = target.toLocaleString('fr-FR'); // S'assurer que la cible exacte est atteinte
    }
  };

  // Réinitialiser le compteur avant de démarrer l'animation
  counter.innerText = '0';
  updateCounter();
}

// Utilisation de l'Intersection Observer
const counters = document.querySelectorAll('.counter');

const observer = new IntersectionObserver(
  entries => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        animateCounter(entry.target); // Démarre l'animation
      }
    });
  },
  {
    threshold: 0.5, // L'animation commence quand 50% du div est visible
  }
);

counters.forEach(counter => observer.observe(counter));

// =================================langue

// Sélection du bouton et ajout d'un événement au clic
const dropdownBtn = document.querySelector('.dropdown-btn');
const dropdown = document.querySelector('.dropdown');

dropdownBtn.addEventListener('click', () => {
  dropdown.classList.toggle('show'); // Ajout/suppression de la classe "show"
});

// Cacher le menu si l'utilisateur clique ailleurs
window.addEventListener('click', (event) => {
  if (!dropdown.contains(event.target)) {
    dropdown.classList.remove('show');
  }
});

window.addEventListener('pageshow', (event) => {
    if (event.persisted) {
        // Réinitialisation de Swiper
        if (typeof Swiper !== 'undefined') {
            document.querySelectorAll('.swiper').forEach(container => {
                // Détruire les anciennes instances de Swiper si nécessaire
                const oldSwiper = container.swiper;
                if (oldSwiper) oldSwiper.destroy(true, true);

                // Réinitialiser Swiper
                new Swiper(container, {
                    // Vos paramètres Swiper
                    slidesPerView: 1,
                    spaceBetween: 10,
                    navigation: {
                        nextEl: '.swiper-button-next',
                        prevEl: '.swiper-button-prev',
                    },
                });
            });
        }
    }
});

// // Show the popup when the page loads
// window.onload = function () {
//     const popup = document.getElementById('popup');
//     popup.style.display = 'flex'; // Show the popup
// };

// // Close the popup
// function closePopup() {
//     const popup = document.getElementById('popup');
//     popup.style.display = 'none'; // Hide the popup
// }
  
