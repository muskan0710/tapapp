function showModel(id, image) {
    let modal = document.getElementById("img-model"+id);
    let modalImg = document.getElementById("img-count"+id);
    modal.style.display = "block";
    modalImg.src = image;
    let span = document.getElementById("close"+id);
    span.onclick = function () {
        modal.style.display = "none";
    }
}