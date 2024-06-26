'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');

var stompClient = null;
var username = null;
var room = null;
let socket = null;

function connect() {
    username = document.querySelector('#name').value.trim();
    room = document.querySelector('#room').value.trim();
    if (username) {
        socket = new SockJS('/chatroom/wss');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
}

function onConnected() {
    var names = 'messUser' + username + room;
    // clear thông báo tin nhắn

    document.getElementsByName(names)[0].className = 'me-auto mb-0';
    document.getElementsByName(names)[1].className = 'text-muted extra-small ms-2';
    document.getElementsByName(names)[2].className = 'line-clamp me-auto';
    document.getElementsByName(names)[3].innerText = 0;
    // reset số lượng tin nhắn
    let countMessage = document.getElementById('countMessage');
    if (countMessage != null) {
        if (Number(countMessage.innerText) - 1 == 0) {
            let divCountMessage = document.getElementById('divCountMessage');
            divCountMessage.className = 'icon icon-xl ';
            divCountMessage.removeChild(document.getElementById('onRemoveCount'));
        } else {
            countMessage.innerText = Number(countMessage.innerText) - 1;
        }
    }

    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/' + room, onMessageReceived);
    // Tell your username to the server
    if (document.getElementById("isGroup").value == 'true') {
        stompClient.send("/app/chat.addUser/" + room,
            {},
            JSON.stringify({sender: username, room: room, type: 'JOINGROUP'})
        )
    } else {
        stompClient.send("/app/chat.addUser/" + room,
            {},
            JSON.stringify({sender: username, room: room, type: 'JOIN'})
        )
    }
}

// connect();

function onError() {
    console.log("lỗi socket chat rồi")
}

async function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    let first = document.querySelector("#first");
    if (first.value == 'onFirst') {
        first.value = "";
        changeStatusFirstMessage(messageContent);
    }
    if (messageContent && stompClient) {
        var attack = document.getElementsByClassName("attackFiles");
        let attacks = "";
        if (attack != null) {
            for (let i = 0; i < attack.length; i++) {
                attacks += "'" + attack[i].title + "',";
            }
            attacks = "[" + attacks.substring(0, attacks.length - 1) + "]";
        }

        $.ajax({
            url: 'message_direct/save',
            data: {
                content: messageContent,
                room: room,
                attack: attacks
            },
            error: function () {
                console.log("error")
            },
            success: async function (data) {
                let idMesss = data[0].idMessage;
                let chatMessage = {
                    id: data[0].idMessage,
                    sender: username,
                    content: messageInput.value,
                    type: 'CHAT',
                    room: room,
                    image: document.getElementById("imageUserLogin").value
                };
                stompClient.send("/app/chat.sendMessage/" + room, {}, JSON.stringify(chatMessage));
                messageInput.value = '';

                if (data[0].fileName != null) {
                    for (let i = 0; i < data.length; i++) {
                        let dataa = {
                            message: "upload",
                            content: data[i].data
                        }
                        let attack = {
                            id: idMesss,
                            sender: "",
                            content: "",
                            type: 'ATTACK',
                            room: room,
                            image: "",
                            urlFile: data[i].fileName
                        };
                        document.querySelector("#dz-preview-row").innerHTML = "";
                        await uploadFileToGit(dataa, data[i].fileName);
                        stompClient.send("/app/chat.sendMessage/" + room, {}, JSON.stringify(attack));
                    }
                }
            },
            type: 'POST'
        });
    }
    event.preventDefault();
}

function changeStatusFirstMessage(messageContent) {
    $.ajax({
        url: 'message/change-status',
        data: {
            roomId: room
        },
        error: function () {
            console.log("error")
        },
        success: async function (data) {
            if (data == 'done') {
                let user = {
                    roomId: room,
                    fullname: document.querySelector("#fullname").value,
                    image: document.querySelector("#avtMyUser").src,
                    sender: username,
                    content: messageContent
                }
                stompClientRoom.send("/app/system/" + document.getElementById("userInRoomDirect").value,
                    {},
                    JSON.stringify(user)
                );
            }
        },
        type: 'POST'
    });
}

async function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    let messageArea = document.getElementById('messageArea');
    let date = new Date();
    let timeChat = moment(date).format('HH:mm');
    let userInRoom = document.getElementById('userInRoomDirect').value;
    if (message.type === 'JOINRETURN') {
        if (document.querySelector('#name').value.trim() != message.sender) {
            // document.getElementById('statusOn').innerText = "Đang hoạt động";
        }
    }
    if (message.type === 'JOIN') {
        if (document.querySelector('#name').value.trim() != message.sender) {
            // document.getElementById('statusOn').innerText = "Đang hoạt động";

            stompClient.send("/app/chat.addUser/" + document.querySelector('#room').value.trim(),
                {},
                JSON.stringify({
                    sender: document.querySelector('#name').value.trim(),
                    room: document.querySelector('#room').value.trim(),
                    type: 'JOINRETURN'
                })
            )
        }
        if (username != message.sender) {
            document.getElementById('statusMessage').innerText = "Đã xem"
        }
    } else if (message.type === 'LEAVE') {
       // messageArea.innerHTML += "<div class='close-conect'><p>" + message.sender + " đã thoát!</p></div>";
    } else if (message.type === 'CHAT') {
        let username = document.querySelector('#name').value.trim();
        let names = 'messUser' + username + room;
        let contentUserMessage = document.getElementById("contentUserMessage");
        let userMessageChat = document.getElementById(names);
        let divAdd = document.createElement("div");
        divAdd.appendChild(userMessageChat);
        contentUserMessage.innerHTML = divAdd.innerHTML + contentUserMessage.innerHTML;
        if (username == message.sender) {
            document.getElementById("statusMessage").innerHTML = message.statusMessage;

            addDivMessageSender(messageArea, message, timeChat);

            let classRe = document.getElementsByName(names);
            if (classRe != null) {
                document.getElementsByName(names)[2].innerText = message.content.substring(0, 100);
            }
            if (document.getElementById("isGroup")) {
                let data = {
                    roomId: room
                }
                let dataOut = await callAjax('user/user-online', data, 'GET')
                for (let i = 0; i < dataOut.length; i++) {
                    stompClientMessageListen.send("/app/system.onmessage/" + dataOut[i],
                        {},
                        JSON.stringify({sender: room, reader: dataOut[i], content: message.content})
                    )
                }

            } else {
                if (message.statusMessage == 'Đã gửi') {
                    stompClientMessageListen.send("/app/system.onmessage/" + userInRoom,
                        {},
                        JSON.stringify({sender: username, reader: userInRoom, content: message.content})
                    )
                }
            }
        } else {
            document.getElementById('tingting').play();
            addDivMessageReader(messageArea, message, timeChat);
            let classRe = document.getElementsByName(names);
            if (classRe != null) {
                document.getElementsByName(names)[2].innerText = message.content.substring(0, 100);
            }
            document.getElementById('statusMessage').innerText = "  "
        }
    } else if (message.type === 'ATTACK') {
        let divAttack = document.getElementById(message.id);
        console.log("file nameeee: " + message.urlFile)
        let src = await getImageToGit(message.urlFile);
        divAttack.innerHTML +=
            "<div class='col'>" +
            "<img class='img-fluid rounded'" +
            "src='" + src + "' data-action='zoom'" +
            "alt=''>" +
            "</div>"
    }

    let messForm = document.getElementById('messForm');
    messForm.scrollTop = messForm.scrollHeight;
}

let messForm = document.querySelector("#messForm")
messForm.addEventListener("scroll", scrollFunction_ct);

async function loadMessage() {
    let messageArea = document.querySelector("#messageArea");
    messageArea.innerHTML = "<div id='loadingMess' class=\"line-clamp me-auto load-message-page\">\n" +
        "                          Đang tải<span class='typing-dots'><span>.</span><span>.</span><span>.</span></span>\n" +
        "                        </div>" + messageArea.innerHTML;
    let roomId = document.querySelector("#room");
    let page = document.querySelector("#pageIndex");
    let username = document.querySelector("#name").value;
    let oldWidth = messForm.scrollHeight;
    $.ajax({
        url: 'api/message',
        data: {
            roomId: roomId.value,
            page: Number(page.value) + 1
        },
        dataType: "json",
        contentType: "json",
        error: function () {
            console.log("error")
        },
        success: function (data) {
            let messagePlus = "";
            page.value = Number(page.value) + 1;
            let loadDing = document.querySelector("#loadingMess");
            loadDing.parentNode.removeChild(loadDing);
            console.log(data)
            data.forEach(message => {
                if (username == message.sender) {
                    messagePlus +=
                        "<div class='message message-out'><a href='#' data-bs-toggle='modal' data-bs-target='#modal-profile' class='avatar avatar-responsive'>" +
                        "<img class='avatar-img' src='" + message.image + "'" + " alt=''>" +
                        "</a>" + "<div class='message-inner'>" + "<div class='message-body'>";

                    for (let a = 0; a < message.content.length; a++) {
                        messagePlus +=
                            "<div class='message-content'>" + "<div class='message-text'>" +
                            "<p>" + message.content[a] + "</p>" +
                            "</div>" + "<div class='message-action'>" + "<div class='dropdown'>" + "<a class='icon text-muted' href='#' role='button' data-bs-toggle='dropdown'" +
                            "aria-expanded='false'>" + "<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='feather feather-more-vertical'><circle cx='12' cy='12' r='1'></circle><circle cx='12' cy='5' r='1'></circle><circle cx='12' cy='19' r='1'></circle></svg>" +
                            "</a>" + "<ul class='dropdown-menu'>" + "<li>" + "<a class='dropdown-item d-flex align-items-center' href='#'>" +
                            "<span class='me-auto'>Edit</span>" + "<div class='icon'>" +
                            "<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='feather feather-edit-3'><path d='M12 20h9'></path><path d='M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z'></path>" +
                            "</svg>" + "</div>" + "</a> </li> <li>" + "<a class='dropdown-item d-flex align-items-center' href='#'>" + "<span class='me-auto'>Reply</span>" +
                            "<div class='icon'>" + "<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='feather feather-corner-up-left'><polyline points='9 14 4 9 9 4'></polyline><path d='M20 20v-7a4 4 0 0 0-4-4H4'></path>" +
                            "</svg>" + "</div>" + "</a>" + "</li>" + "<li><hr class='dropdown-divider'></li>" + "<li>" + "<a class='dropdown-item d-flex align-items-center text-danger' href='#'>" +
                            "<span class='me-auto'>Delete</span>" +
                            "<div class='icon'>" +
                            "<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='feather feather-trash-2'><polyline points='3 6 5 6 21 6'></polyline><path d='M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2'></path>" +
                            "<line x1='10' y1='11' x2='10' y2='17'></line><line x1='14' y1='11' x2='14' y2='17'></line>" +
                            "</svg>" + "</div>" + "</a>" + "</li>" + "</ul>" + "</div>" + "</div>" + "</div>";
                    }

                    if (message.listFile != null && message.listFile.length > 0) {
                        messagePlus +=
                            "<div class='message-content'>" +
                            "<div class='message-gallery'>" +
                            "<div class='row gx-3' id='" + message.id + "'>" +

                            "</div>" +
                            "</div>" +
                            "</div>";

                        for (let i = 0; i < message.listFile.length; i++) {
                            fetch('https://api.github.com/repos/iamducthanh/image_webchat/contents/' + message.listFile[i], {
                                method: 'GET',
                                headers: {
                                    "Authorization": TO + KEN,
                                    "Accept": "application/vnd.github.v3+json"
                                },
                            })
                                .then(response => response.json())
                                .then(out => {
                                    let divIPlus = document.getElementById(message.id);
                                    divIPlus.innerHTML +=
                                        "<div class='col'>" +
                                        "<img class='img-fluid rounded'" +
                                        "src='" + "data:image/png;base64," + out.content + "' data-action='zoom'" +
                                        "alt=''>" +
                                        "</div>";
                                })
                                .catch((error) => {
                                    console.error('Error:', error);
                                });
                        }
                    }

                    messagePlus +=
                        "</div>" +
                        "<div class='message-footer'>" +
                        "<span class='extra-small text-muted'>" + message.time + "</span>" +
                        "</div>" + "</div>" + "</div>";
                } else {
                    messagePlus +=
                        "<div class='message'>" +
                        "<a data-bs-toggle='modal' onclick='showUserProfile(\"" + message.sender + "\")' data-bs-target='#modal-user-profile'" +
                        "class='avatar avatar-responsive'>" +
                        "<img class='avatar-img' src='" + message.image + "'" + " alt=''>" +
                        "</a>" +
                        "<div class='message-inner'>" + "<div class='message-body'>";

                    for (let a = 0; a < message.content.length; a++) {
                        messagePlus +=
                            "<div class='message-content'>" + "<div class='message-text'>" +
                            "<p>" + message.content[a] + "</p>" +
                            "</div>" + "</div>";
                    }

                    if (message.listFile != null) {
                        messagePlus +=
                            "<div class='message-content'>" +
                            "<div class='message-gallery'>" +
                            "<div class='row gx-3' id='" + message.id + "'>" +

                            "</div>" +
                            "</div>" +
                            "</div>";
                        for (let i = 0; i < message.listFile.length; i++) {
                            fetch('https://api.github.com/repos/iamducthanh/image_webchat/contents/' + message.listFile[i], {
                                method: 'GET',
                                headers: {
                                    "Authorization": TO + KEN,
                                    "Accept": "application/vnd.github.v3+json"
                                },
                            })
                                .then(response => response.json())
                                .then(out => {
                                    let divIPlus = document.getElementById(message.id);
                                    divIPlus.innerHTML +=
                                        "<div class='col'>" +
                                        "<img class='img-fluid rounded'" +
                                        "src='" + "data:image/png;base64," + out.content + "' data-action='zoom'" +
                                        "alt=''>" +
                                        "</div>";
                                })
                                .catch((error) => {
                                    console.error('Error:', error);
                                });
                        }
                    }

                    messagePlus +=
                        "</div>" +
                        "<div class='message-footer'>" +
                        "<span class='extra-small text-muted'>" + message.time + "</span>" +
                        "</div>" + "</div>" + "</div>";
                }
            })
            messageArea.innerHTML = messagePlus + messageArea.innerHTML;
            let newWidth = messForm.scrollHeight;
            let re = newWidth - oldWidth;
            messForm.scrollTop = re;
        },
        type: 'GET'
    });
}

function scrollFunction_ct() {
    let page = document.querySelector("#pageIndex");
    if (messForm.scrollTop == 0 && page.value != -1) {
        loadMessage();
    }
}

function loadImage() {
    let imageMessages = document.getElementsByClassName("imageMessage");
    for (let i = 0; i < imageMessages.length; i++) {
        fetch('https://api.github.com/repos/iamducthanh/image_webchat/contents/' + imageMessages[i].name, {
            method: 'GET',
            headers: {
                "Authorization": TO + KEN,
                "Accept": "application/vnd.github.v3+json"
            },
        })
            .then(response => response.json())
            .then(out => {
                imageMessages[i].src = "data:image/png;base64," + out.content;
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }
}

messageForm.addEventListener('submit', sendMessage, true)

function callVideo() {
    console.log(document.querySelector("#statusOn").innerHTML)
    if (document.querySelector("#statusOn").innerHTML == 'Không hoạt động') {
        showi('Người dùng này hiện không hoạt động!');
    } else {
        showWaitCall();
    }
}

async function onLoadMedia() {
    let status = document.getElementById("statusMedia").value;
    if (status == 0) {
        document.getElementById("statusMedia").value = 1;
        let room = document.getElementById("room").value;
        let data = {
            room: room
        }
        let dataOut = await callAjax('api/files', data, 'GET')

        let contentMedia = document.getElementById("contentMedia");
        addDivMedia(contentMedia, dataOut);

    }
}

async function addDivMedia(contentMedia, dataOut) {
    contentMedia.innerHTML = "";
    if (dataOut.length > 0) {
        for (let i = 0; i < dataOut.length; i++) {
            let src = await getImageToGit(dataOut[i]);
            let divCol = document.createElement("div");
            divCol.className = 'col';
            let aa = document.createElement("a");
            aa.setAttribute('data-bs-toggle', 'modal');
            aa.setAttribute('data-bs-target', '#modal-media-preview');
            aa.setAttribute('data-theme-img-url', src);
            divCol.appendChild(aa)
            let img = document.createElement("img");
            img.className = 'img-fluid rounded';
            img.onclick = showImage.bind(this, i);
            img.src = src;
            aa.appendChild(img);
            contentMedia.appendChild(divCol);
        }
    }
}

async function onLoadMedia1() {
    let status = document.getElementById("statusMedia").value;
    if (status == 0) {
        console.log("onload media")
        document.getElementById("statusMedia").value = 1;
        let data = {
            room: room
        }
        let dataOut = await callAjax('api/files', data, 'GET')

        let contentMedia = document.getElementById("contentMedia1");
        addDivMedia(contentMedia, dataOut);
    }
}

function showImage(index) {
    console.log(index)
    document.getElementById("modal-media-preview").style.display = 'block'
}

function addDivMessageSender(messageArea, message, timeChat) {
    messageArea.innerHTML +=
        "<div class='message message-out'><a href='#' data-bs-toggle='modal' data-bs-target='#modal-profile' class='avatar avatar-responsive'>" +
        "<img class='avatar-img' src='" + document.getElementById("imageUserLogin").value + "'" + " alt=''>" +
        "</a>" +
        "<div class='message-inner'>" +
        "<div class='message-body'>" +
        "<div class='message-content'>" +
        "<div class='message-text'>" +
        "<p>" + message.content + "</p>" +
        "</div>" +
        "<div class='message-action'>" +
        "<div class='dropdown'>" +
        "<a class='icon text-muted' href='#' role='button' data-bs-toggle='dropdown'" +
        "aria-expanded='false'>" +
        "<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='feather feather-more-vertical'><circle cx='12' cy='12' r='1'></circle><circle cx='12' cy='5' r='1'></circle><circle cx='12' cy='19' r='1'></circle></svg>" +
        "</a>" +
        "<ul class='dropdown-menu'>" +
        "<li>" +
        "<a class='dropdown-item d-flex align-items-center' href='#'>" +
        "<span class='me-auto'>Edit</span>" +
        "<div class='icon'>" +
        "<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='feather feather-edit-3'><path d='M12 20h9'></path><path d='M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z'></path>" +
        "</svg>" +
        "</div>" +
        "</a> </li> <li>" +
        "<a class='dropdown-item d-flex align-items-center' href='#'>" +
        "<span class='me-auto'>Reply</span>" +
        "<div class='icon'>" +
        "<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='feather feather-corner-up-left'><polyline points='9 14 4 9 9 4'></polyline><path d='M20 20v-7a4 4 0 0 0-4-4H4'></path>" +
        "</svg>" + "</div>" + "</a>" + "</li>" +
        "<li><hr class='dropdown-divider'></li>" + "<li>" +
        "<a class='dropdown-item d-flex align-items-center text-danger' href='#'>" +
        "<span class='me-auto'>Delete</span>" +
        "<div class='icon'>" +
        "<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='feather feather-trash-2'><polyline points='3 6 5 6 21 6'></polyline><path d='M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2'></path>" +
        "<line x1='10' y1='11' x2='10' y2='17'></line><line x1='14' y1='11' x2='14' y2='17'></line>" +
        "</svg>" + "</div>" + "</a>" + "</li>" + "</ul>" + "</div>" + "</div>" + "</div>" +

        "<div class='message-content'>" +
        "<div class='message-gallery'>" +
        "<div class='row gx-3' id='" + message.id + "'>" +

        "</div>" +
        "</div>" +
        "</div>" +

        "</div>" +
        "<div class='message-footer'>" +
        "<span class='extra-small text-muted'>" + timeChat + "</span>" + "</div>" + "</div>" + "</div>";
}

function addDivMessageReader(messageArea, message, timeChat) {
    messageArea.innerHTML +=
        "<div class='message'>" +
        "<a data-bs-toggle='modal' data-bs-target='#modal-user-profile'" +
        "class='avatar avatar-responsive'>" +
        "<img class='avatar-img' src='" + message.image + "'" + " alt=''>" +
        "</a>" +
        "<div class='message-inner'>" + "<div class='message-body'>" +
        "<div class='message-content'>" +
        "<div class='message-text'>" +
        "<p>" + message.content + "</p>" +
        "</div>" + "</div>" +
        "<div class='message-content'>" +
        "<div class='message-gallery'>" +
        "<div class='row gx-3' id='" + message.id + "'>" +

        "</div>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "<div class='message-footer'>" +
        "<span class='extra-small text-muted'>" + timeChat + "</span>" +
        "</div>" + "</div>" + "</div>";
}

