app.controller("contactCreateController", function ($scope, $http, $location) {
    $scope.save = {};
    $scope.save.doClick = function(){
        response = $http({
            method: "post",
            url: "/OrderFlowers/saveContactCreate",
            data: {
                surname: $scope.contact.surname,
                name: $scope.contact.name,
                patronymic: $scope.contact.patronymic,
                dateOfBirth: $scope.contact.dateOfBirth,
                email: $scope.contact.email,
                city: $scope.contact.city,
                street: $scope.contact.street,
                home: $scope.contact.home,
                flat: $scope.contact.flat
            }
        });
        response.success(function (data) {
            $location.path('/contactList');
            $location.replace();
        });
        response.error(function (data) {
            $scope.authorization.info = "error";
        });
    }
});

app.controller("contactListController", function ($scope, $http, $location) {
    $scope.contactsToDelete = [];
    var response = $http({
        method: "get",
        url: "/OrderFlowers/contactList"
    });
    response.success(function (data) {
        $scope.contacts = data;
    });
    response.error(function (data) {
        $scope.authorization.info = "error";
    });


    $scope.deleteContact = {};
    $scope.deleteContact.doClick = function(){
        var contactDelete = $http({
            method: "post",
            url: "/OrderFlowers/contactDelete",
            data: {
                deleteId: $scope.contactsToDelete
            }
        });
        contactDelete.success(function (data) {
            if(data == 'false'){
                alert("Вы пытаетесь удалить контакт, который связан с пользователем");
            }
            var userList = $http({
                method: "get",
                url: "/OrderFlowers/contactList"
            });
            userList.success(function (data) {
                $scope.contacts = data;
                $location.path('/contactList');
                $location.replace();
                $scope.contactsToDelete = [];
            });
            userList.error(function (data) {
                $scope.authorization.info = "error";
            });
        });
        contactDelete.error(function (data) {
            $scope.authorization.info = "error";
        });
    }
});

app.controller("contactSearchController", function ($scope, $http) {
    $scope.message = "contactSearchController";
});

app.controller("contactCorrectController", function ($scope, $http, $routeParams, $location) {
    var id = $routeParams.id;
    var response = $http({
        method: "get",
        url: "/OrderFlowers/contactCorrect",
        params: {
            id: id
        }
    });
    response.success(function (data) {
        $scope.contact = data;
    });
    response.error(function (data) {
        $scope.authorization.info = "error";
    });
    $scope.save = {};
    $scope.save.doClick = function(){
        response = $http({
            method: "post",
            url: "/OrderFlowers/saveContactCorrect",
            data: {
                id: $scope.contact.id,
                surname: $scope.contact.surname,
                name: $scope.contact.name,
                patronymic: $scope.contact.patronymic,
                dateOfBirth: $scope.contact.dateOfBirth,
                email: $scope.contact.email,
                city: $scope.contact.city,
                street: $scope.contact.street,
                home: $scope.contact.home,
                flat: $scope.contact.flat
            }
        });
        response.success(function (data) {
            var contactList = $http({
                method: "get",
                url: "/OrderFlowers/contactList"
            });
            contactList.success(function (data) {
                $scope.contacts = data;
                $location.path('/contactList');
                $location.replace();
            });
            contactList.error(function (data) {
                $scope.authorization.info = "error";
            });
        });
        response.error(function (data) {
            $scope.authorization.info = "error";
        });
    }
});

/*==================================================================
 КЛИЕНТСКАЯ ЛОГИКА СТРАНИЦЫ contact.html
 ====================================================================*/

//номер строки редактируемого телефона в таблице phone-table
var phoneRowIndex = null;

//имена полей со страницы contact_create.html
var id = {
    PHONE_TABLE_ID: 'phone-table',
    EDIT_PHONE_ROW: 'edit-phone-row',

    COUNTRY_CODE_TD_ID: 'country-code-td',
    OPERATOR_CODE_TD_ID: 'operator-code-td',
    PHONE_NUMBER_TD_ID: 'phone-number-td',
    PHONE_TYPE_TD_ID: 'phone-type-td',
    PHONE_COMMENT_TD_ID: 'phone-comment-td',
    COMMAND_AND_CHECKBOX_TD_ID: 'command-and-checkbox-td',

    COUNTRY_CODE_ID: 'country-code',
    OPERATOR_CODE_ID: 'operator-code',
    PHONE_NUMBER_ID: 'phone-number',
    PHONE_TYPE_ID: 'phone-type',
    PHONE_COMMENT_ID: 'phone-comment',
    DELETE_CONTACT_CHECKBOX_ID: 'delete-contact-checkbox',
    COMMAND_ID: 'command',

    MODAL_PHONE_ID: 'modal-phone',
    MODAL_PHONE_TITLE_ID: 'modal-phone-title',
    MODAL_PHONE_BUTTON_SAVE: 'modal-phone-button-save'
}

//команды на добавление, удаления, обновления телефона
var commands = {
    ADD: "add",
    UPDATE: "update",
    DELETE: "delete"
}

var phoneTypeMap = {
    HOME: "Домашний",
    MOBILE: "Мобильный",

    getKeyByValue: function(key){
        var result = null;
        if (key == this.HOME){
            result = "HOME";
        }
        else if (key == this.MOBILE){
            result = "MOBILE";
        }
        return result;
    },

    isValidKey: function(key){
        var result = false;
        if (key == "HOME" || key == "MOBILE"){
            result = true;
        }
        return result;
    }
}

//валидация телефонного номера
var phoneValidationPattern = {
    COUNTRY_CODE: /^[0-9]{3}$/,
    OPERATOR_CODE: /^[0-9]{2}$/,
    PHONE_NUMBER: /^[0-9]{3}-[0-9]{2}-[0-9]{2}$/
}


function showCreatePhonePopUp(){
    document.getElementById(id.MODAL_PHONE_TITLE_ID).innerHTML = "Создать телефонный номер";
    document.getElementById(id.MODAL_PHONE_BUTTON_SAVE).innerHTML = "Добавить";
    document.getElementById(id.COUNTRY_CODE_ID).value = "";
    document.getElementById(id.OPERATOR_CODE_ID).value = "";
    document.getElementById(id.PHONE_NUMBER_ID).value = "";
    document.getElementById(id.PHONE_TYPE_ID).value = "NONE";
    document.getElementById(id.PHONE_COMMENT_ID).value = "";
    document.getElementById(id.MODAL_PHONE_BUTTON_SAVE).setAttribute('onclick', 'addPhone()');
    $('#' + id.MODAL_PHONE_ID).modal('show');
}

function showEditPhonePopUp(td) {
    document.getElementById(id.MODAL_PHONE_TITLE_ID).innerHTML = "Редактировать телефонный номер";
    document.getElementById(id.MODAL_PHONE_BUTTON_SAVE).innerHTML = "Изменить";
    phoneRowIndex = td.parentNode.parentNode.rowIndex;
    var cells = td.parentNode.parentNode.getElementsByTagName("td");
    document.getElementById(id.COUNTRY_CODE_ID).value = cells[id.COUNTRY_CODE_TD_ID].innerHTML;
    document.getElementById(id.OPERATOR_CODE_ID).value = cells[id.OPERATOR_CODE_TD_ID].innerHTML;
    document.getElementById(id.PHONE_NUMBER_ID).value = cells[id.PHONE_NUMBER_TD_ID].innerHTML;
    document.getElementById(id.PHONE_TYPE_ID).value = phoneTypeMap.getKeyByValue(cells[id.PHONE_TYPE_TD_ID].innerHTML);
    document.getElementById(id.PHONE_COMMENT_ID).value = cells[id.PHONE_COMMENT_TD_ID].innerHTML;
    document.getElementById(id.MODAL_PHONE_BUTTON_SAVE).setAttribute('onclick', 'editPhone()');
    $('#' + id.MODAL_PHONE_ID).modal('show');
}

function addPhone(){
    var countryCodeField = document.getElementById(id.COUNTRY_CODE_ID);
    var operatorCodeField = document.getElementById(id.OPERATOR_CODE_ID);
    var phoneNumberField = document.getElementById(id.PHONE_NUMBER_ID);
    var phoneTypeField = document.getElementById(id.PHONE_TYPE_ID);
    var phoneComment = document.getElementById(id.PHONE_COMMENT_ID).value.trim();

    countryCodeField.value = countryCodeField.value.trim();
    operatorCodeField.value = operatorCodeField.value.trim();
    phoneNumberField.value = phoneNumberField.value.trim();

    var row = null;
    var temp = null;
    var input = null;

    if (phoneValidation(countryCodeField, operatorCodeField, phoneNumberField, phoneTypeField)){
        row = document.createElement('tr');

        temp = document.createElement('td');
        temp.setAttribute("name", id.COMMAND_AND_CHECKBOX_TD_ID);
        input = document.createElement('input');
        input.type = 'checkbox';
        input.name = id.DELETE_CONTACT_CHECKBOX_ID;
        temp.appendChild(input);

        input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'command'
        input.value = commands.ADD;
        temp.appendChild(input);
        row.appendChild(temp);

        temp = document.createElement('td');
        temp.setAttribute("name", id.COUNTRY_CODE_TD_ID);
        temp.innerHTML = countryCodeField.value;
        row.appendChild(temp);

        temp = document.createElement('td');
        temp.setAttribute("name", id.OPERATOR_CODE_TD_ID);
        temp.innerHTML = operatorCodeField.value;
        row.appendChild(temp);

        temp = document.createElement('td');
        temp.setAttribute("name", id.PHONE_NUMBER_TD_ID);
        temp.innerHTML = phoneNumberField.value;
        row.appendChild(temp);

        temp = document.createElement('td');
        temp.setAttribute("name", id.PHONE_TYPE_TD_ID);
        temp.innerHTML = phoneTypeMap[phoneTypeField.value];
        row.appendChild(temp);

        temp = document.createElement('td');
        temp.setAttribute("name", id.PHONE_COMMENT_TD_ID);
        temp.innerHTML = phoneComment;
        row.appendChild(temp);

        temp = document.createElement('td');
        input = document.createElement('img');
        input.src = "../images/edit-button.png"
        input.setAttribute("onclick", "showEditPhonePopUp(this)");
        temp.appendChild(input);
        row.appendChild(temp);

        document.getElementById(id.PHONE_TABLE_ID).getElementsByTagName("tbody")[0].appendChild(row);
        $('#' + id.MODAL_PHONE_ID).modal('hide');
    }
}

function editPhone(){
    var countryCodeField = document.getElementById(id.COUNTRY_CODE_ID);
    var operatorCodeField = document.getElementById(id.OPERATOR_CODE_ID);
    var phoneNumberField = document.getElementById(id.PHONE_NUMBER_ID);
    var phoneTypeField = document.getElementById(id.PHONE_TYPE_ID);
    var phoneComment = document.getElementById(id.PHONE_COMMENT_ID).value.trim();
    var cells = null;
    var command = null;

    countryCodeField.value = countryCodeField.value.trim();
    operatorCodeField.value = operatorCodeField.value.trim();
    phoneNumberField.value = phoneNumberField.value.trim();

    if (phoneValidation(countryCodeField, operatorCodeField, phoneNumberField, phoneTypeField)){
        cells = document.getElementById('phone-table').rows[phoneRowIndex].getElementsByTagName('td');
        cells[id.COUNTRY_CODE_TD_ID].innerHTML = countryCodeField.value;
        cells[id.OPERATOR_CODE_TD_ID].innerHTML = operatorCodeField.value;
        cells[id.PHONE_NUMBER_TD_ID].innerHTML = phoneNumberField.value;
        cells[id.PHONE_TYPE_TD_ID].innerHTML = phoneTypeMap[phoneTypeField.value];
        cells[id.PHONE_COMMENT_TD_ID].innerHTML = phoneComment;
        command = cells[id.COMMAND_AND_CHECKBOX_TD_ID].getElementsByTagName('input')['command'];
        if (command.value != commands.ADD){
            command.value = commands.UPDATE;
        }
        $('#' + id.MODAL_PHONE_ID).modal('hide');
    }
}

function deletePhones(){
    var rows = document.getElementById(id.PHONE_TABLE_ID).rows;
    var isChecked = false;
    var inputs = null;
    for (var i = 1; i < rows.length; i++){
        inputs = rows[i].getElementsByTagName("td")[id.COMMAND_AND_CHECKBOX_TD_ID].getElementsByTagName("input");
        if (inputs[id.DELETE_CONTACT_CHECKBOX_ID].checked){
            if (!isChecked){
                isChecked = true;
            }
            rows[i].style.display = "none";
            inputs[id.DELETE_CONTACT_CHECKBOX_ID].checked = false;
            if (inputs[id.COMMAND_ID].value != commands.ADD){
                inputs[id.COMMAND_ID].value = commands.DELETE;
            }
            else {
                inputs[id.COMMAND_ID].value = "";
            }
        }
    }

    if (!isChecked){
        alert("Не выбраны контакты для удаления");
    }
}

function phoneValidation(countryCodeField, operatorCodeField, phoneNumberField, phoneTypeField){
    var errorClass = " has-error";
    var isGood = true;

    if (!phoneValidationPattern.COUNTRY_CODE.test(countryCodeField.value)){
        isGood = false;
        countryCodeField.parentNode.className += errorClass;
    }
    else {
        countryCodeField.parentNode.className -= errorClass;
    }

    if (!phoneValidationPattern.OPERATOR_CODE.test(operatorCodeField.value)){
        isGood = false;
        operatorCodeField.parentNode.className += errorClass;
    }
    else {
        operatorCodeField.parentNode.className -= errorClass;
    }

    if (!phoneValidationPattern.PHONE_NUMBER.test(phoneNumberField.value)){
        isGood = false;
        phoneNumberField.parentNode.className += errorClass;
    }
    else {
        phoneNumberField.parentNode.className -= errorClass;
    }

    if (!phoneTypeMap.isValidKey(phoneTypeField.value)){
        isGood = false;
        phoneTypeField.parentNode.className += errorClass;
    }
    else {
        phoneTypeField.parentNode.className -= errorClass;
    }
    return isGood;
}