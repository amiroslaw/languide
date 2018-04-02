$(document).ready(function () {

    //popover
    $('body').on('mouseover', 'td', function () {
        let tr = $(this).parent();
        $(this).popover({
            placement: 'bottom',
            container: tr,
            trigger: 'click',
            // trigger: 'hover',
            // delay: {"show": 100, "hide": 900},
            html: true,
            content: function () {
                return $('#popover-content').html();
            }
        });
        // .click(function(e) {
        //         e.preventDefault();
    });
    $(document).on('click', '.pop-Add', function () {
        $(this).closest('tr').after('<tr><td>new</td><td>new</td></tr>');
    });
    $(document).on('click', '.pop-remove-row', function () {
        $(this).closest('tr').remove();
    });
    $(document).on('click', '.pop-remove-cell', function () {
        // change let tr = $(this).parent(); to let tr = $(this);
    });
    // delete cell
    document.oncontextmenu = function () {
        return false;
    };
    const columnItems = [];
    let parity;
    const tbody = document.querySelector("tbody");
    tbody.addEventListener("contextmenu", function (event) {
        const {target: eTarget, target: {parentNode: parent}} = event;
        const rows = [...tbody.children];
        const rowIndexInTable = rows.findIndex(row => row === parent) - 1;
        const cellIndexInRow = [...parent.children].findIndex(
            cell => cell === eTarget
        );
        parity = (cellIndexInRow === 0 ? "even" : "odd");
        let column = $('tr td:' + parity)
        column.each(function () {
            columnItems.push($(this).text());
        });
        columnItems.splice(rowIndexInTable, 1);
        column.each(function (i) {
            $(this).text(columnItems[i]);
        });
        $('tr').eq(columnItems.length + 1).find('td').eq(cellIndexInRow).empty();
        columnItems.length = 0;

        event.preventDefault();
        return false;

    }, false);
});
