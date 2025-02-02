

var hamburgerBtn = document.querySelector(".hamburger-btn");
var sideBar = document.querySelector(".side-bar");
var modeSwitcher = document.querySelector(".mode-switch i");
var body = document.querySelector("body");


hamburgerBtn.addEventListener("click", sidebarToggle);
function sidebarToggle() {
  sideBar.classList.toggle("active");
}


modeSwitcher.addEventListener("click", modeSwitch);
function modeSwitch() {
  body.classList.toggle("active");

  if (body.classList.contains("active")) {
    localStorage.setItem("darkMode", "enabled");
  } else {
    localStorage.setItem("darkMode", "disabled");
  }
}

window.addEventListener("DOMContentLoaded", function () {

  const savedMode = localStorage.getItem("darkMode");


  if (savedMode === "enabled") {
    body.classList.add("active"); 
  } else {
    body.classList.remove("active"); 
  }
});
$(document).ready(function () {
  $("table").DataTable({
    scrollX: true, 
    colReorder: true, 
  });
});
document.addEventListener("DOMContentLoaded", function () {
    const table = document.querySelector(".resizable-table");
    const headers = table.querySelectorAll("th");

    headers.forEach((header, index) => {
        // Create resizer
        const resizer = document.createElement("div");
        resizer.classList.add("resizer");
        resizer.style.height = `${table.offsetHeight}px`;

        // Add resizer to header
        header.style.position = "relative";
        header.appendChild(resizer);

        let startX, startWidth;

        resizer.addEventListener("mousedown", (e) => {
            startX = e.pageX;
            startWidth = header.offsetWidth;

            document.addEventListener("mousemove", resizeColumn);
            document.addEventListener("mouseup", stopResize);
        });

        function resizeColumn(e) {
            const width = startWidth + (e.pageX - startX);
            header.style.width = `${width}px`;

            // Apply width to all cells in this column
            table.querySelectorAll(`tr td:nth-child(${index + 1})`).forEach((cell) => {
                cell.style.width = `${width}px`;
            });
        }

        function stopResize() {
            document.removeEventListener("mousemove", resizeColumn);
            document.removeEventListener("mouseup", stopResize);
        }
    });
});

    function doDelete(productId) {
        return confirm(`Are you sure you want to delete this product ?`);
    }
    function confirmDeleteSelected(event) {
    event.preventDefault(); 

    let confirmDelete = confirm("Do you really want to delete these product?");
    if (confirmDelete) {
        deleteSelected(); 
    }
}

     function toggleSelectAll(source) {
        const checkboxes = document.querySelectorAll('.product-checkbox');
        checkboxes.forEach(checkbox => {
            checkbox.checked = source.checked;
        });
    }

    function deleteSelected() {
   
        const selectedCheckboxes = document.querySelectorAll('.product-checkbox:checked');

        if (selectedCheckboxes.length === 0) {
            alert('No products selected.');
            return;
        }

        let query = 'Products?service=deleteProduct';
        selectedCheckboxes.forEach((checkbox, index) => {
            query += `&id=${checkbox.value}`;
        });

        window.location.href = query;
    }

document.getElementById("toggle-checkbox-btn").addEventListener("click", function () {
    const checkboxColumns = document.querySelectorAll('.checkbox-column');

    if (checkboxColumns.length === 0) return; 

    const isHidden = checkboxColumns[0].style.display === 'none' || checkboxColumns[0].style.display === '';

    checkboxColumns.forEach(column => {
        column.style.display = isHidden ? 'table-cell' : 'none';
    });
    const icon = this.querySelector("i");
    if (icon) {
        icon.classList.toggle("fa-list-check");
        icon.classList.toggle("fa-eye-slash"); 
    }
});

    function toggleSelectAll(source) {
        const checkboxes = document.querySelectorAll('.product-checkbox');
        checkboxes.forEach(checkbox => {
            checkbox.checked = source.checked;
        });
    }
    const dropArea = document.getElementById("dropArea");
const fileInput = document.getElementById("file");


