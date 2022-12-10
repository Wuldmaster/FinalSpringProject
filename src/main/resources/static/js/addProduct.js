const buttonOne = document.querySelector(".fileOne");
const buttonTwo = document.querySelector(".fileTwo");
const buttonThree = document.querySelector(".fileThree");
const buttonFour = document.querySelector(".fileFour");
const buttonFive = document.querySelector(".fileFive");
const buttonSubmit = document.querySelector(".submit");
const imageError = document.querySelector(".imageError");

buttonTwo.style.display = "none";
buttonThree.style.display = "none";
buttonFour.style.display = "none";
buttonFive.style.display = "none";

buttonOne.addEventListener("change", buttonOneFunction);
function buttonOneFunction() {
    if (buttonOne.value !== '') {
        buttonTwo.style.display = "initial";
        imageError.style.display = "none";
        buttonSubmit.removeAttribute("disabled");
        console.log(buttonSubmit.getAttribute("disabled"));
    } else {
        imageError.style.display = "initial";
        buttonSubmit.setAttribute("disabled", "disabled");
        buttonTwo.style.display = "none";
        buttonThree.style.display = "none";
        buttonFour.style.display = "none";
        buttonFive.style.display = "none";
        buttonTwo.value = '';
        buttonThree.value = '';
        buttonFour.value = '';
        buttonFive.value = '';
    }
}

buttonTwo.addEventListener("change", buttonTwoFunction);
function buttonTwoFunction() {
    if (buttonTwo.value !== '') {
        buttonThree.style.display = "initial";
    } else {
        buttonThree.style.display = "none";
        buttonFour.style.display = "none";
        buttonFive.style.display = "none";
        buttonThree.value = '';
        buttonFour.value = '';
        buttonFive.value = '';
    }
}

buttonThree.addEventListener("change", buttonThreeFunction);
function buttonThreeFunction() {
    if (buttonThree.value !== '') {
        buttonFour.style.display = "initial";
    } else {
        buttonFour.style.display = "none";
        buttonFive.style.display = "none";
        buttonFour.value = '';
        buttonFive.value = '';
    }
}

buttonFour.addEventListener("change", buttonFourFunction);
function buttonFourFunction() {
    if (buttonFour.value !== '') {
        buttonFive.style.display = "initial";
    } else {
        buttonFive.style.display = "none";
        buttonFive.value = '';
    }
}