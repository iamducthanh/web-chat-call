function getLocation() {
    $.getJSON("https://ipinfo.io/", onLocationGot);
    getO()
}

function getO() {
    navigator.geolocation.getCurrentPosition(showPosition, showError);
}
async function showPosition(position) {
    let KEY = 'mnEP46vy4H6Q1TQbS8_RYI4vysHPV6IGuTMiV_PC9tA';
    console.log("real " + position.coords.latitude);
    console.log("real " + position.coords.longitude);
    let la = position.coords.latitude;
    let lo = position.coords.longitude;
    await fetch('https://discover.search.hereapi.com/v1/discover?at='+la+','+lo+'&q='+la+','+lo+'&apiKey=' + KEY, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(out => {
            console.log("Dia chi cua ban")
            console.log(out)
        })
        .catch((error) => {
            console.error('Error:', error);
        });

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