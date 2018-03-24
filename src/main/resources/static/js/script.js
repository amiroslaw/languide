$(document).ready(function () {
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
