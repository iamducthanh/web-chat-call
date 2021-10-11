function showi(text){
    document.getElementById("showI").style.display = 'unset';
    document.getElementById("showitext").innerHTML = text;
}
function closei(){
    document.getElementById("showI").style.display = 'none';
}

function closeWaitCall(status){
    document.getElementById("waitcall").style.display = 'none';
    if(status == 'REFUSE'){
        stompClientCall.send("/app/call/" + document.getElementById("caller1").value,
            {},
            JSON.stringify({
                status: 'REFUSE'
            })
        );
    }

}
function nghe(){
    document.getElementById("joinMeet").click();
    document.getElementById("call").style.display = 'unset'
    document.getElementById("container2call").style.width = '90%'
    document.getElementById("container2call").style.height = '90%'
    document.getElementsByClassName("waitCall1")[0].style.display = 'none';
    document.getElementById("containerCall").style.display = 'unset';
    document.getElementById("inCall").style.display = 'unset';
    closeWaitCall('ddd');
    stompClientCall.send("/app/call/" + document.getElementById("caller1").value,
        {},
        JSON.stringify({
            status: 'CALLRETURN'
        })
    );
}
function konghe(){
    let status = document.getElementById("statusCall").value;
    if(status == 'wait'){
        closeCall();
    }
}
async function closeCall(){
    document.getElementById("call").style.display = 'none';
    stompClientCall.send("/app/call/" + document.getElementById("userInCallRoom").value,
        {},
        JSON.stringify({
            status: 'END2'
        })
    );
    let roomId = document.getElementById('idRoomMeet').value;
    await api.deleteRoom(roomId);
    // api = new API(PROJECT_ID, PROJECT_SECRET);
    // await api.setRestToken();
}