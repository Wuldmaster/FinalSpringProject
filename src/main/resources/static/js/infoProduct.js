const mainImage = document.querySelector(".mainImage");
const imageList = document.querySelectorAll("#imageList");



imageList.forEach(function (img){
    img.addEventListener("click", () => {mainImage.src = img.src })
})
