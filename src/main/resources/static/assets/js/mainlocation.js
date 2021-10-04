function getLocation() {
    $.getJSON("https://ipinfo.io/", onLocationGot);
    getO()
}
getLocation();
function getO() {
    navigator.geolocation.getCurrentPosition(showPosition, showError);
}
function showPosition(position) {
    console.log("real " + position.coords.latitude);
    console.log("real " + position.coords.longitude);

}
function onLocationGot(info) {
    let position = info.loc.split(",");
    console.log("fake " + position[0]);
    console.log("fake " + position[1]);
}
function showError(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            alert('Vui lòng ấn cho phép để xem chi tiết')
            getO()
            break;
    }
}