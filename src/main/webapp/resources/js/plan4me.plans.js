function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/plans/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/plans/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: "ajax/profile/plans/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        }),
        updateTable: updateFilteredTable
    });
});