/**
 * Created by Daniel on 07/03/2017.
 */

var table = $('#viewListTable');
var viewListDataTable;
$(document).ready(function () {

    colNo = table[0].rows[0].cells.length;
    viewListDataTable = table.DataTable({
        "columnDefs": [
            {"orderable": false, "targets": colNo - 2},
            {"orderable": false, "targets": colNo - 1},
            {"searchable": false, "targets": colNo - 2},
            {"searchable": false, "targets": colNo - 1}
        ],
        "order": [[ 0, "desc" ]]
    });

    $(table).on('click', 'tr', function(event) {
       var data = $(this).data('viewLink');
       if (data) {
           window.location.href = data;
       }
    });
});

function getButtons() {
    var buttons  = [];

    if (table.data('withEdit')) {
        buttons.push({
            text: 'Edit Selected',
            action: function () {
                var selected = viewListDataTable.rows( { selected: true } );
                if (selected[0].length > 0) {
                    window.location.href = $(selected.nodes()).data('editLink');
                }
            }
        })
    }

    if (table.data('withView')) {
        buttons.push({
            text: 'View Selected',
            action: function () {
                var selected = viewListDataTable.rows( { selected: true } );
                if (selected[0].length > 0) {
                    window.location.href = $(selected.nodes()).data('viewLink');
                }
            }
        })
    }

    return buttons;
}