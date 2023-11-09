const TABLE_VALUES = [
    "table_items" ,
    "table_items2" ,
    "table_items3" ,
    "table_items4" ,
    "table_items5" ,
    "table_items6"
]


new Tabulator("#table_items", {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        { formatter: "responsiveCollapse", width: 30, minWidth: 30, hozAlign: "center", resizable: false, headerSort: false },
        { title: "ID", hozAlign: "left", minWidth: 50, width: 75 },
        { title: "NOMBRE", hozAlign: "center", minWidth: 50 },
        { title: "DESCRIPCIÓN", hozAlign: "center", minWidth: 300 },
        { title: "FECHA", hozAlign: "right", minWidth: 300 },
        { title: "FECHA ACTUAL", hozAlign: "right", minWidth: 300 },
    ]
});

new Tabulator("#table_items2", {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        { formatter: "responsiveCollapse", width: 30, minWidth: 30, hozAlign: "center", resizable: false, headerSort: false },
        { title: "ID", hozAlign: "left", minWidth: 50, width: 75 },
        { title: "NOMBRE", hozAlign: "center", minWidth: 50 },
        { title: "DESCRIPCIÓN", hozAlign: "center", minWidth: 300 },
        { title: "FECHA", hozAlign: "right", minWidth: 300 },
        { title: "FECHA ACTUAL", hozAlign: "right", minWidth: 300 },
    ]
});

new Tabulator("#table_items3", {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        { formatter: "responsiveCollapse", width: 30, minWidth: 30, hozAlign: "center", resizable: false, headerSort: false },
        { title: "ID", hozAlign: "left", minWidth: 50, width: 75 },
        { title: "NOMBRE", hozAlign: "center", minWidth: 50 },
        { title: "DESCRIPCIÓN", hozAlign: "center", minWidth: 300 },
        { title: "FECHA", hozAlign: "right", minWidth: 300 },
        { title: "FECHA ACTUAL", hozAlign: "right", minWidth: 300 },
    ]
});

new Tabulator("#table_items4", {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        { formatter: "responsiveCollapse", width: 30, minWidth: 30, hozAlign: "center", resizable: false, headerSort: false },
        { title: "ID", hozAlign: "left", minWidth: 50, width: 75 },
        { title: "NOMBRE", hozAlign: "center", minWidth: 50 },
        { title: "DESCRIPCIÓN", hozAlign: "center", minWidth: 300 },
        { title: "FECHA", hozAlign: "right", minWidth: 300 },
        { title: "FECHA MODIFICADA", hozAlign: "right", minWidth: 300 },
    ]
});

new Tabulator("#table_items5", {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        { formatter: "responsiveCollapse", width: 30, minWidth: 30, hozAlign: "center", resizable: false, headerSort: false },
        { title: "ID", hozAlign: "left", minWidth: 50, width: 75 },
        { title: "NOMBRE", hozAlign: "center", minWidth: 50 },
        { title: "DESCRIPCIÓN", hozAlign: "center", minWidth: 300 },
        { title: "FECHA", hozAlign: "right", minWidth: 300 },
        { title: "FECHA ACTUAL", hozAlign: "right", minWidth: 300 },
    ]
});

new Tabulator("#table_items6", {
    layout: "fitColumns",
    responsiveLayout: "collapse",
    columns: [
        { formatter: "responsiveCollapse", width: 30, minWidth: 30, hozAlign: "center", resizable: false, headerSort: false },
        { title: "ID", hozAlign: "left", minWidth: 50, width: 75 },
        { title: "NOMBRE", hozAlign: "center", minWidth: 50 },
        { title: "DESCRIPCIÓN", hozAlign: "center", minWidth: 300 },
        { title: "FECHA", hozAlign: "right", minWidth: 300 },
        { title: "FECHA MODIFICADA", hozAlign: "right", minWidth: 300 },
    ]
});

const select = document.querySelector("#select_Catalogos") ;

select.addEventListener("input", (e)=>{
    const selected = e.target.value;
    const table = document.querySelector(`#${selected}`);
    table.classList.remove("d-none");
    const filterValues = TABLE_VALUES.filter(t=> t!== selected);
    filterValues.forEach(t=>{
        const table = document.querySelector(`#${t}`);
        table.classList.add("d-none");
    })

})