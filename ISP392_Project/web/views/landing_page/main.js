const menuBtn = document.getElementById("menu-btn");
const navLinks = document.getElementById("nav-links");
const menuBtnIcon = menuBtn.querySelector("i");

menuBtn.addEventListener("click", (e) => {
    navLinks.classList.toggle("open");

    const isOpen = navLinks.classList.contains("open");
    menuBtnIcon.setAttribute("class", isOpen ? "ri-close-line" : "ri-menu-line");
});

navLinks.addEventListener("click", (e) => {
    navLinks.classList.remove("open");
    menuBtnIcon.setAttribute("class", "ri-menu-line");
});

const scrollRevealOption = {
    distance: "50px",
    origin: "bottom",
    duration: 1000,
};

ScrollReveal().reveal(".header__container p", {
    ...scrollRevealOption,
});
ScrollReveal().reveal(".header__container h1", {
    ...scrollRevealOption,
    delay: 500,
});
ScrollReveal().reveal(".header__container .header__flex", {
    ...scrollRevealOption,
    delay: 1000,
});

const faq = document.querySelector(".faq__grid");

faq.addEventListener("click", (e) => {
    const target = e.target;
    const faqCard = target.closest(".faq__card");
    if (target.classList.contains("ri-arrow-down-s-line")) {
        if (faqCard.classList.contains("active")) {
            faqCard.classList.remove("active");
        } else {
            Array.from(faq.children).forEach((item) => {
                item.classList.remove("active");
            });
            faqCard.classList.add("active");
        }
    }
});

ScrollReveal().reveal(".faq__image img", {
    ...scrollRevealOption,
    origin: "left",
});

ScrollReveal().reveal(".article__card", {
    ...scrollRevealOption,
    interval: 500,
});

const swiper = new Swiper(".swiper", {
    loop: true,

    pagination: {
        el: ".swiper-pagination",
    },
});


// Get the button:
let mybutton = document.getElementById("myBtn");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function () {
    scrollFunction()
};

function scrollFunction() {
    if (document.body.scrollTop > 200 || document.documentElement.scrollTop > 200) {
        mybutton.style.display = "block";
    } else {
        mybutton.style.display = "none";
    }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}

