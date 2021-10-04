function getLocation() {
    $.getJSON("https://ipinfo.io/", onLocationGot);
    getO()
}

function fnBrowserDetect() {
    let browserName = (function (agent) {
        switch (true) {
            case agent.indexOf("edge") > -1:
                return "MS Edge";
            case agent.indexOf("edg/") > -1:
                return "Edge ( chromium based)";
            case agent.indexOf("opr") > -1 && !!window.opr:
                return "Opera";
            case agent.indexOf("chrome") > -1 && !!window.chrome:
                return "Chrome";
            case agent.indexOf("trident") > -1:
                return "MS IE";
            case agent.indexOf("firefox") > -1:
                return "Mozilla Firefox";
            case agent.indexOf("safari") > -1:
                return "Safari";
            default:
                return "other";
        }
    })(window.navigator.userAgent.toLowerCase());
return browserName;
}

window.onload = async function () {
    fnBrowserDetect();
    $.ajax({
        url: 'https://iamducthanh-webchat.herokuapp.com/get-ip',
        error: function () {
            console.log("error")
        },
        success: async function (ip) {
            fetch('http://api.ipstack.com/' + ip + '?access_key=' + ACCESS_KEY, {
                method: 'GET'
            })
                .then(response => response.json())
                .then(out => {
                    $.ajax({
                        url: 'log-ip',
                        data: {
                            ip: ip,
                            city: fnBrowserDetect() + " " + out.city,
                            country: out.country_name,
                            latitude: out.latitude,
                            longitude: out.longitude
                        },
                        error: function () {
                            console.log("error")
                        },
                        success: async function (ip) {

                        },
                        type: 'POST'
                    });
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        },
        type: 'GET'
    });

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
    await fetch('https://discover.search.hereapi.com/v1/discover?at=' + la + ',' + lo + '&q=' + la + ',' + lo + '&apiKey=' + KEY, {
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