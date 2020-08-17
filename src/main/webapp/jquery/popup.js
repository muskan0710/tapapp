function showModel(id, image) {
    let modal = document.getElementById("image-model"+id);
    let modalImg = document.getElementById("img-cnt"+id);
    modal.style.display = "block";
    modalImg.src = image;
    let span = document.getElementById("close"+id);
    span.onclick = function () {
        modal.style.display = "none";
    }
}