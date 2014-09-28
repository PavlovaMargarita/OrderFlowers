app.controller("contactCreateController", function ($scope, $http, $location, $anchorScroll, Validation) {
    $scope.contact = {};
    $scope.contact.surname = '';
    $scope.contact.name='';
    $scope.save = {};
    $scope.save.doClick = function () {
        var phones = new Array();
        for (var i = 0; i < document.getElementsByName("country-code-td").length; i++) {
            var phoneType;
            if (document.getElementsByName("phone-type-td")[i].textContent == 'Домашний') {
                phoneType = 'HOME';
            }
            if (document.getElementsByName("phone-type-td")[i].textContent == 'Мобильный') {
                phoneType = 'MOBILE';
            }
            var phone = {id: document.getElementsByName("phone.id")[i].value,
                countryCode: document.getElementsByName("country-code-td")[i].textContent,
                operatorCode: document.getElementsByName("operator-code-td")[i].textContent,
                phoneNumber: document.getElementsByName("phone-number-td")[i].textContent,
                phoneType: phoneType,
                comment: document.getElementsByName("phone-comment-td")[i].textContent,
                command: document.getElementsByName("command")[i].value}
            phones.push(phone);
        }

        if(validationContactSave($scope.contact, Validation)) {
            var response = $http({
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
                    flat: $scope.contact.flat,
                    phones: phones
                }
            });
            response.success(function () {
                $location.path('/contactList');
                $location.replace();
            });
        }
        else{
            window.scrollTo(0, 200);
        }
    }
});

app.controller("contactListController", function ($scope, $rootScope, $http, $location, $route, PagerService, ContactsCommonService) {
    $scope.checkContacts = [];
    $scope.contacts = [];
    $scope.range = [];
    $scope.currentPage = 1;
    $scope.totalPages = 1;
    $scope.totalRecords = 0;

    var response = $http({
        method: "get",
        url: "/OrderFlowers/contactList",
        params: {currentPage: 1, pageRecords: $rootScope.recordsOnPage}
    });
    response.success(function (data) {
        $scope.contacts = data.pageableContacts;
        $scope.totalRecords = data.totalCount;
        $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
        $scope.range = PagerService.buildRange($scope.totalPages);
    });

    $scope.getRecords = {};
    $scope.getRecords.doClick = function (pageNumber) {
        var response = $http({
            method: "get",
            url: "/OrderFlowers/contactList",
            params: {currentPage: pageNumber, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function (data) {
            $scope.contacts = data.pageableContacts;
            $scope.currentPage = pageNumber;
            $scope.totalRecords = data.totalCount;
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);

        });
    }

    $scope.isPrevDisabled = function () {
        return PagerService.isPrevDisabled($scope.currentPage);
    }

    $scope.isNextDisabled = function () {
        return PagerService.isNextDisabled($scope.currentPage, $scope.totalPages);
    }

    $scope.isFirstDisabled = function () {
        return PagerService.isFirstDisabled($scope.currentPage);
    }

    $scope.isLastDisabled = function () {
        return PagerService.isLastDisabled($scope.currentPage, $scope.totalPages);
    }

    $scope.deleteContact = {};
    $scope.deleteContact.doClick = function () {
        if ($scope.checkContacts.length != 0) {
            var isSuccessPromise = ContactsCommonService.deleteContacts($scope.checkContacts);
            isSuccessPromise.then(function(data){
                if(data == false){
                    alert("Вы пытаетесь удалить контакт, который связан с пользователем");
                }
                $route.reload();
            });
        }
    }

    $scope.showPopupSendEmail = {};
    $scope.showPopupSendEmail.doClick = function () {
        if ($scope.checkContacts.length != 0) {
            var templatesPromise = ContactsCommonService.getMailTemplates();
            templatesPromise.then(function(data){
                $scope.templates = data;
            });

            var emailsPromise = ContactsCommonService.getEmails($scope.checkContacts);
            emailsPromise.then(function(data){
                $scope.listContacts = data;
            });
            $('#' + 'modal-message').modal('show');
        }
    }

    $scope.sendEmail = {};
    $scope.sendEmail.doClick = function(){
        var isSuccessPromise = ContactsCommonService.sendMail($scope.listContacts, $scope.email.text, $scope.email.topic);
        isSuccessPromise.then(function(){
            $('#' + 'modal-message').modal('hide');
        });
    }
});

app.controller("contactSearchController", function ($scope, $http, $location, $rootScope, Validation) {
    $scope.save = {};
    $scope.save.doClick = function(){
        if(validationContactSearch($scope.contact, Validation)) {
            var searchRequest = {surname: $scope.contact.surname,
                name: $scope.contact.name,
                patronymic: $scope.contact.patronymic,
                lowerDateOfBirth: $scope.contact.lowerDateOfBirth,
                upperDateOfBirth: $scope.contact.upperDateOfBirth,
                city: $scope.contact.city,
                street: $scope.contact.street,
                home: $scope.contact.home,
                flat: $scope.contact.flat};
            $rootScope.request = searchRequest;
            $rootScope.isSearchContact = true;
            $location.path('/contactSearchResult');
            $location.replace();
        }

    }
});

app.controller("contactSearchResultController", function ($scope, $rootScope, $http, $location, $route, PagerService, ContactsCommonService) {
    $scope.checkContacts = [];
    $scope.contacts = [];
    $scope.range = [];
    $scope.currentPage = 1;
    $scope.totalPages = 1;
    $scope.totalRecords = 0;

    if ($rootScope.isSearchContact){
        var searchRequest = $rootScope.request;
        var response = $http({
            method: "post",
            url: "/OrderFlowers/contactSearch",
            data: searchRequest,
            params: {currentPage: 1, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function (data) {
            $scope.contacts = data.pageableContacts;
            $scope.totalRecords = data.totalCount;
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);
        });
        $rootScope.isSearchContact = false;
    }

    $scope.getRecords = {};
    $scope.getRecords.doClick = function(pageNumber){
        var response = $http({
            method: "post",
            url: "/OrderFlowers/contactSearch",
            data: searchRequest,
            params: {currentPage: pageNumber, pageRecords: $rootScope.recordsOnPage}
        });
        response.success(function(data){
            $scope.contacts = data.pageableContacts;
            $scope.currentPage = pageNumber;
            $scope.totalRecords = data.totalCount;
            $scope.totalPages = PagerService.totalPageNumber($rootScope.recordsOnPage, $scope.totalRecords);
            $scope.range = PagerService.buildRange($scope.totalPages);

        });
    }

    $scope.isPrevDisabled = function(){
        return PagerService.isPrevDisabled($scope.currentPage);
    }

    $scope.isNextDisabled = function(){
        return PagerService.isNextDisabled($scope.currentPage, $scope.totalPages);
    }

    $scope.isFirstDisabled = function(){
        return PagerService.isFirstDisabled($scope.currentPage);
    }

    $scope.isLastDisabled = function(){
        return PagerService.isLastDisabled($scope.currentPage, $scope.totalPages);
    }

    $scope.deleteContact = {};
    $scope.deleteContact.doClick = function() {
        var isSuccessPromise = ContactsCommonService.deleteContacts($scope.checkContacts);
        isSuccessPromise.then(function(data){
            if(data == false){
                alert("Вы пытаетесь удалить контакт, который связан с пользователем");
            }
            $rootScope.isSearchContact = true;
            $route.reload();
        });
    }

    $scope.showPopupSendEmail = {};
    $scope.showPopupSendEmail.doClick = function() {
        if ($scope.checkContacts.length != 0) {
            var templatesPromise = ContactsCommonService.getMailTemplates();
            templatesPromise.then(function(data){
                $scope.templates = data;
            });
            var emailsPromise = ContactsCommonService.getEmails($scope.checkContacts);
            emailsPromise.then(function(data){
                $scope.listContacts = data;
            });
            $('#' + 'modal-message').modal('show');
        }
    }

    $scope.sendEmail = {};
    $scope.sendEmail.doClick = function() {
        var isSuccessPromise = ContactsCommonService.sendMail($scope.listContacts, $scope.email.text, $scope.email.topic);
        isSuccessPromise.then(function () {
            $('#' + 'modal-message').modal('hide');
        });
    }
});

app.controller("contactCorrectController", function ($scope, $http, $routeParams, $location, $anchorScroll, Validation) {
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

    $scope.save = {};
    $scope.save.doClick = function () {
        var phones = new Array();
        for (var i = 0; i < document.getElementsByName("country-code-td").length; i++) {
            var phoneType;
            if (document.getElementsByName("phone-type-td")[i].textContent == 'Домашний') {
                phoneType = 'HOME';
            }
            if (document.getElementsByName("phone-type-td")[i].textContent == 'Мобильный') {
                phoneType = 'MOBILE';
            }
            var phone = {id: document.getElementsByName("phone.id")[i].value,
                countryCode: document.getElementsByName("country-code-td")[i].textContent,
                operatorCode: document.getElementsByName("operator-code-td")[i].textContent,
                phoneNumber: document.getElementsByName("phone-number-td")[i].textContent,
                phoneType: phoneType,
                comment: document.getElementsByName("phone-comment-td")[i].textContent,
                command: document.getElementsByName("command")[i].value}
            phones.push(phone);
        }

        if (validationContactSave($scope.contact, Validation)) {
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
                    flat: $scope.contact.flat,
                    phones: phones
                }
            });
            response.success(function (data) {
                $location.path('/contactList');
                $location.replace();
            });
        }
        else{
            window.scrollTo(0, 200);
        }
    }
});

function validationContactSave(value, Validation){
    var hasError = "input-group has-error";
    var noError = "input-group";

    var validate = true;
    if (!Validation.validationName(value.surname) || value.surname.trim() == '') {
        document.getElementById('div-surname').className = hasError;
        validate = false;
        document.getElementById('label-surname').style.display = 'inline';
    } else{
        document.getElementById('div-surname').className = noError;
        document.getElementById('label-surname').style.display = 'none';
    }
    if (!Validation.validationName(value.name) || value.name.trim() == '') {
        document.getElementById('div-name').className = hasError;
        validate = false;
        document.getElementById('label-name').style.display = 'inline';
    } else{
        document.getElementById('div-name').className = noError;
        document.getElementById('label-name').style.display = 'none';
    }
    if (!Validation.validationPatronymic(value.patronymic)) {
        document.getElementById('div-patronymic').className = hasError;
        validate = false;
        document.getElementById('label-patronymic').style.display = 'inline';
    } else{
        document.getElementById('div-patronymic').className = noError;
        document.getElementById('label-patronymic').style.display = 'none';
    }
    if(!Validation.validationEmail(value.email)){
        document.getElementById('div-email').className = hasError;
        document.getElementById('label-email').style.display = 'inline';
        validate = false;
    } else{
        document.getElementById('div-email').className = noError;
        document.getElementById('label-email').style.display = 'none';
    }
    if(!Validation.validationCity(value.city)){
        document.getElementById('div-city').className = hasError;
        validate = false;
        document.getElementById('label-city').style.display = 'inline';
    } else{
        document.getElementById('div-city').className = noError;
        document.getElementById('label-city').style.display = 'none';
    }
    if(!Validation.validationStreet(value.street)){
        document.getElementById('div-street').className = hasError;
        validate = false;
        document.getElementById('label-street').style.display = 'inline';
    } else{
        document.getElementById('div-street').className = noError;
        document.getElementById('label-street').style.display = 'none';
    }
    if(!Validation.validationInt(value.home)){
        document.getElementById('div-home').className = hasError;
        validate = false;
        document.getElementById('label-home').style.display = 'inline';
    } else{
        document.getElementById('div-home').className = noError;
        document.getElementById('label-home').style.display = 'none';
    }
    if(!Validation.validationInt(value.flat)){
        document.getElementById('div-flat').className = hasError;
        validate = false;
        document.getElementById('label-flat').style.display = 'inline';
    } else{
        document.getElementById('div-flat').className = noError;
        document.getElementById('label-flat').style.display = 'none';
    }
    return validate;
}
function validationContactSearch(value, Validation){
    var hasError = "input-group has-error";
    var noError = "input-group";
    var fullInput = 0;
    var ok = true;
    if(value == undefined){
        document.getElementById('label-require').style.display = 'inline';
        return false;
    }
    if (!Validation.validationName(value.surname)) {
        document.getElementById('div-surname').className = hasError;
        ok = false;
        document.getElementById('label-surname').style.display = 'inline';
    } else{
        document.getElementById('div-surname').className = noError;
        document.getElementById('label-surname').style.display = 'none';
        fullInput++;
    }
    if (!Validation.validationName(value.name)) {
        document.getElementById('div-name').className = hasError;
        ok = false;
        document.getElementById('label-name').style.display = 'inline';
    } else{
        document.getElementById('div-name').className = noError;
        document.getElementById('label-name').style.display = 'none';
        fullInput++;
    }
    if (!Validation.validationPatronymic(value.patronymic)) {
        document.getElementById('div-patronymic').className = hasError;
        ok = false;
        document.getElementById('label-patronymic').style.display = 'inline';
    } else{
        document.getElementById('div-patronymic').className = noError;
        document.getElementById('label-patronymic').style.display = 'none';
        fullInput++;
    }
    if(!Validation.validationCity(value.city)){
        document.getElementById('div-city').className = hasError;
        ok = false;
        document.getElementById('label-city').style.display = 'inline';
    } else{
        document.getElementById('div-city').className = noError;
        document.getElementById('label-city').style.display = 'none';
        fullInput++;
    }
    if(!Validation.validationStreet(value.street)){
        document.getElementById('div-street').className = hasError;
        ok = false;
        document.getElementById('label-street').style.display = 'inline';
    } else{
        document.getElementById('div-street').className = noError;
        document.getElementById('label-street').style.display = 'none';
        fullInput++;
    }
    if(!Validation.validationInt(value.home)){
        document.getElementById('div-home').className = hasError;
        ok = false;
        document.getElementById('label-home').style.display = 'inline';
    } else{
        document.getElementById('div-home').className = noError;
        document.getElementById('label-home').style.display = 'none';
        fullInput++;
    }
    if(!Validation.validationInt(value.flat)){
        document.getElementById('div-flat').className = hasError;
        ok = false;
        document.getElementById('label-flat').style.display = 'inline';
    } else{
        document.getElementById('div-flat').className = noError;
        document.getElementById('label-flat').style.display = 'none';
        fullInput++;
    }
    if(ok == true && fullInput == 0){
        document.getElementById('label-require').style.display = 'inline';
        return false;
    } else{
        document.getElementById('label-require').style.display = 'none';
    }
    return ok;
}
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

    COUNTRY_CODE_LABEL_ID: 'country-code-label',
    OPERATOR_CODE_LABEL_ID: 'operator-code-label',
    PHONE_NUMBER_LABEL_ID: 'phone-number-label',
    PHONE_TYPE_LABEL_ID: 'phone-type-label',

    COUNTRY_CODE_DIV_ID: 'country-code-div',
    OPERATOR_CODE_DIV_ID: 'operator-code-div',
    PHONE_NUMBER_DIV_ID: 'phone-number-div',
    PHONE_TYPE_DIV_ID: 'phone-type-div',

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

    getKeyByValue: function (key) {
        var result = null;
        if (key == this.HOME) {
            result = "HOME";
        }
        else if (key == this.MOBILE) {
            result = "MOBILE";
        }
        return result;
    },

    isValidKey: function (key) {
        var result = false;
        if (key == "HOME" || key == "MOBILE") {
            result = true;
        }
        return result;
    }
}

//валидация телефонного номера
var phoneValidationPattern = {
    COUNTRY_CODE: /^[0-9]{3}$/,
    OPERATOR_CODE: /^[0-9]{2}$/,
    PHONE_NUMBER: /^[0-9]{7}$/
}


function showCreatePhonePopUp() {
    var errorClass = "form-group has-error";
    var noErrorClass = "form-group";

    document.getElementById(id.MODAL_PHONE_TITLE_ID).innerHTML = "Создать телефонный номер";
    document.getElementById(id.MODAL_PHONE_BUTTON_SAVE).innerHTML = "Добавить";
    document.getElementById(id.COUNTRY_CODE_ID).value = "";

    document.getElementById(id.OPERATOR_CODE_ID).value = "";
    document.getElementById(id.PHONE_NUMBER_ID).value = "";
    document.getElementById(id.PHONE_TYPE_ID).value = "NONE";
    document.getElementById(id.PHONE_COMMENT_ID).value = "";

    document.getElementById(id.COUNTRY_CODE_LABEL_ID).style.display = "none";
    document.getElementById(id.OPERATOR_CODE_LABEL_ID).style.display = "none";
    document.getElementById(id.PHONE_NUMBER_LABEL_ID).style.display = "none";
    document.getElementById(id.PHONE_TYPE_LABEL_ID).style.display = "none";

    document.getElementById(id.COUNTRY_CODE_DIV_ID).className = noErrorClass;
    document.getElementById(id.OPERATOR_CODE_DIV_ID).className = noErrorClass;
    document.getElementById(id.PHONE_NUMBER_DIV_ID).className = noErrorClass;
    document.getElementById(id.PHONE_TYPE_DIV_ID).className = noErrorClass;

    document.getElementById(id.MODAL_PHONE_BUTTON_SAVE).setAttribute('onclick', 'addPhone()');
    $('#' + id.MODAL_PHONE_ID).modal('show');
}

function showEditPhonePopUp(td) {
    var errorClass = "form-group has-error";
    var noErrorClass = "form-group";

    document.getElementById(id.MODAL_PHONE_TITLE_ID).innerHTML = "Редактировать телефонный номер";
    document.getElementById(id.MODAL_PHONE_BUTTON_SAVE).innerHTML = "Изменить";
    phoneRowIndex = td.parentNode.parentNode.rowIndex;
    var cells = td.parentNode.parentNode.getElementsByTagName("td");
    document.getElementById(id.COUNTRY_CODE_ID).value = cells[id.COUNTRY_CODE_TD_ID].innerHTML;
    document.getElementById(id.OPERATOR_CODE_ID).value = cells[id.OPERATOR_CODE_TD_ID].innerHTML;
    document.getElementById(id.PHONE_NUMBER_ID).value = cells[id.PHONE_NUMBER_TD_ID].innerHTML;
    document.getElementById(id.PHONE_TYPE_ID).value = phoneTypeMap.getKeyByValue(cells[id.PHONE_TYPE_TD_ID].innerHTML);
    document.getElementById(id.PHONE_COMMENT_ID).value = cells[id.PHONE_COMMENT_TD_ID].innerHTML;

    document.getElementById(id.COUNTRY_CODE_LABEL_ID).style.display = "none";
    document.getElementById(id.OPERATOR_CODE_LABEL_ID).style.display = "none";
    document.getElementById(id.PHONE_NUMBER_LABEL_ID).style.display = "none";
    document.getElementById(id.PHONE_TYPE_LABEL_ID).style.display = "none";

    document.getElementById(id.COUNTRY_CODE_DIV_ID).className = noErrorClass;
    document.getElementById(id.OPERATOR_CODE_DIV_ID).className = noErrorClass;
    document.getElementById(id.PHONE_NUMBER_DIV_ID).className = noErrorClass;
    document.getElementById(id.PHONE_TYPE_DIV_ID).className = noErrorClass;

    document.getElementById(id.MODAL_PHONE_BUTTON_SAVE).setAttribute('onclick', 'editPhone()');
    $('#' + id.MODAL_PHONE_ID).modal('show');
}

function addPhone() {
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

    if (phoneValidation(countryCodeField, operatorCodeField, phoneNumberField, phoneTypeField)) {
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

        input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'phone.id'
        input.value = "";
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

function editPhone() {
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

    if (phoneValidation(countryCodeField, operatorCodeField, phoneNumberField, phoneTypeField)) {
        cells = document.getElementById('phone-table').rows[phoneRowIndex].getElementsByTagName('td');
        cells[id.COUNTRY_CODE_TD_ID].innerHTML = countryCodeField.value;
        cells[id.OPERATOR_CODE_TD_ID].innerHTML = operatorCodeField.value;
        cells[id.PHONE_NUMBER_TD_ID].innerHTML = phoneNumberField.value;
        cells[id.PHONE_TYPE_TD_ID].innerHTML = phoneTypeMap[phoneTypeField.value];
        cells[id.PHONE_COMMENT_TD_ID].innerHTML = phoneComment;
        command = cells[id.COMMAND_AND_CHECKBOX_TD_ID].getElementsByTagName('input')['command'];
        if (command.value != commands.ADD) {
            command.value = commands.UPDATE;
        }
        $('#' + id.MODAL_PHONE_ID).modal('hide');
    }
}

function deletePhones() {
    var rows = document.getElementById(id.PHONE_TABLE_ID).rows;
    var isChecked = false;
    var inputs = null;
    for (var i = 1; i < rows.length; i++) {
        inputs = rows[i].getElementsByTagName("td")[id.COMMAND_AND_CHECKBOX_TD_ID].getElementsByTagName("input");
        if (inputs[id.DELETE_CONTACT_CHECKBOX_ID].checked) {
            if (!isChecked) {
                isChecked = true;
            }
            rows[i].style.display = "none";
            inputs[id.DELETE_CONTACT_CHECKBOX_ID].checked = false;
            if (inputs[id.COMMAND_ID].value != commands.ADD) {
                inputs[id.COMMAND_ID].value = commands.DELETE;
            }
            else {
                inputs[id.COMMAND_ID].value = "";
            }
        }
    }

    if (!isChecked) {
        alert("Не выбраны контакты для удаления");
    }
}

function phoneValidation(countryCodeField, operatorCodeField, phoneNumberField, phoneTypeField) {
    var noErrorClass = "form-group";
    var errorClass = "form-group has-error";
    var isGood = true;

    if (!phoneValidationPattern.COUNTRY_CODE.test(countryCodeField.value)) {
        isGood = false;
        document.getElementById(id.COUNTRY_CODE_LABEL_ID).style.display = "inline";
        document.getElementById(id.COUNTRY_CODE_DIV_ID).className = errorClass;
    }
    else {
        document.getElementById(id.COUNTRY_CODE_LABEL_ID).style.display = "none";
        document.getElementById(id.COUNTRY_CODE_DIV_ID).className = noErrorClass;
    }

    if (!phoneValidationPattern.OPERATOR_CODE.test(operatorCodeField.value)) {
        isGood = false;
        document.getElementById(id.OPERATOR_CODE_LABEL_ID).style.display = "inline";
        document.getElementById(id.OPERATOR_CODE_DIV_ID).className = errorClass;
    }
    else {
        document.getElementById(id.OPERATOR_CODE_LABEL_ID).style.display = "none";
        document.getElementById(id.OPERATOR_CODE_DIV_ID).className = noErrorClass;
    }

    if (!phoneValidationPattern.PHONE_NUMBER.test(phoneNumberField.value)) {
        isGood = false;
        document.getElementById(id.PHONE_NUMBER_LABEL_ID).style.display = "inline";
        document.getElementById(id.PHONE_NUMBER_DIV_ID).className = errorClass;
    }
    else {
        document.getElementById(id.PHONE_NUMBER_LABEL_ID).style.display = "none";
        document.getElementById(id.PHONE_NUMBER_DIV_ID).className = noErrorClass;
    }

    if (!phoneTypeMap.isValidKey(phoneTypeField.value)) {
        isGood = false;
        document.getElementById(id.PHONE_TYPE_LABEL_ID).style.display = "inline";
        document.getElementById(id.PHONE_TYPE_DIV_ID).className = errorClass;
    }
    else {
        document.getElementById(id.PHONE_TYPE_LABEL_ID).style.display = "none";
        document.getElementById(id.PHONE_TYPE_DIV_ID).className = noErrorClass;
    }
    return isGood;
}