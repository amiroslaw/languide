$(document).ready(function () {

    //popover
    $('body').on('mouseover', 'td', function () {
        let td = this;
        $(this).popover({
            placement: 'bottom',
            container: td,
            // title: "Right click to remove cell",
            // trigger: 'click',
            trigger: 'hover',
            delay: {"show": 100, "hide": 800},
            html: true,
            content: function () {
                return $('#popover-content').html();
            }
        });
    // .click(function(e) {
    //         e.preventDefault();
    });
    $('body').on('click', '.pop-Add', function () {
        $(this).closest('tr').after('<tr><td>new</td><td>new</td></tr>');
    });
    $('body').on('click', '.pop-remove-row', function () {
        $(this).closest('tr').remove();
    });
    $('body').on('click', '.pop-remove-cell', function () {
        // $(this).closest('tr').before('<tr><td>new</td><td>new</td></tr>');
    });
    // delete cell
    document.oncontextmenu = function() {return false;};
    const columnItems = [];
    let parity;
    const tbody = document.querySelector("tbody");
    tbody.addEventListener("contextmenu", function (event) {
            const {target: eTarget, target: {parentNode: parent}} = event;
            const rows = [...tbody.children];
            const rowIndexInTable = rows.findIndex(row => row === parent) -1;
            const cellIndexInRow = [...parent.children].findIndex(
                cell => cell === eTarget
            );
            parity = (cellIndexInRow === 0 ? "even" : "odd");

            $('tr td:'+parity).each( function(){
                columnItems.push( $(this).text() );
            });
            columnItems.splice(rowIndexInTable, 1);
            $('tr td:'+parity).each( function(i){
                $(this).text(columnItems[i]);
            });
            $('tr').eq(columnItems.length+1).find('td').eq(cellIndexInRow).empty();
            columnItems.length = 0;

            event.preventDefault();
            return false;

    }, false);
});
