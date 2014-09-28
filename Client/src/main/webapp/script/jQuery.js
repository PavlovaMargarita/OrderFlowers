$(function() {
    $( "#customer-id" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                method: "post",
                url: "/OrderFlowers/getPerson",
                dataType: "json",
                data: {term: request.term},
                success: function(data) {
                    response($.map(data, function(item) {
                        var returnValue = item.surname + " " + item.name;
                        if (item.patronymic !== null)
                            returnValue += " " + item.patronymic;
                        return {
                            value: returnValue,
                            id: item.id
                        };
                    }));
                }
            });
        },
        minLength: 2,
        select: function( event, ui ) {
            $( "#customer-hidden-id" ).val( ui.item.id);
        }
    });
    $( "#recipient-id" ).autocomplete({
        source: function( request, response ) {
            $.ajax({
                url: "/OrderFlowers/getPerson",
                dataType: "json",
                data: {term: request.term},
                success: function(data) {
                    response($.map(data, function(item) {
                        var returnValue = item.surname + " " + item.name;
                        if (item.patronymic !== null)
                            returnValue += " " + item.patronymic;
                        return {
                            value: returnValue,
                            id: item.id
                        };
                    }));
                }
            });
        },
        minLength: 2,
        select: function( event, ui ) {
            $( "#recipient-hidden-id" ).val( ui.item.id);
        }
    });
});
