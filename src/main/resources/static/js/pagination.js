function changePageSize(dest) {
    const regex = /size=\d+/
    let size = document.getElementById("size").value;

    if (dest.includes('size')) {
        document.location.href = dest.replace(regex, 'size=' + size);
    } else {
        document.location.href = dest + "&size=" + size;
    }
}