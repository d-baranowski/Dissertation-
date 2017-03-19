/**
 * Created by Daniel on 07/03/2017.
 */

var table = $('#viewListTable');
var viewListDataTable;
$(document).ready(function () {
    viewListDataTable = table.DataTable( {
        dom: 'Bfrtip',
        select: true,
        buttons: getButtons()
    } );
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