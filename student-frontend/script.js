const API_URL = "http://localhost:8080/students";

// Add Student
let editId = null;
document.getElementById("studentForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const student = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        department: document.getElementById("department").value,
        year: parseInt(document.getElementById("year").value),
        cgpa: parseFloat(document.getElementById("cgpa").value)
    };

    if (editId) {
        // UPDATE
        await fetch(`${API_URL}/${editId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(student)
        });

        alert("Student Updated!");
        editId = null;
    } else {
        // CREATE
        await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(student)
        });

        alert("Student Added!");
    }

    document.getElementById("studentForm").reset();
    loadStudentsPaged();
});
async function loadStudents() {
    const response = await fetch(API_URL);
    const students = await response.json();

    const tableBody = document.querySelector("#studentTable tbody");
    tableBody.innerHTML = "";

    students.forEach(student => {
        const row = `
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.email}</td>
                <td>${student.department}</td>
                <td>${student.year}</td>
                <td>${student.cgpa}</td>
                <td>
                    <button onclick="deleteStudent(${student.id})">Delete</button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}
async function deleteStudent(id) {
    const confirmDelete = confirm("Are you sure you want to delete this student?");
    if (!confirmDelete) return;

    await fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    });

    loadStudentsPaged();
}
async function filterStudents() {
    document.getElementById("paginationControls").style.display = "none";
    const department = document.getElementById("filterDepartment").value;
    const year = document.getElementById("filterYear").value;

    let url = `${API_URL}/filter?`;

    if (department) url += `department=${department}&`;
    if (year) url += `year=${year}`;

    const response = await fetch(url);
    const students = await response.json();

    const tableBody = document.querySelector("#studentTable tbody");
    tableBody.innerHTML = "";

    students.forEach(student => {
        const row = `
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.email}</td>
                <td>${student.department}</td>
                <td>${student.year}</td>
                <td>${student.cgpa}</td>
                <td>
                    <button onclick="deleteStudent(${student.id})">Delete</button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}
let currentPage = 0;
const pageSize = 3;
async function loadStudentsPaged() {
    document.getElementById("paginationControls").style.display = "block";
    const response = await fetch(
        `${API_URL}/paged?page=${currentPage}&size=${pageSize}&sortBy=id`
    );

    const data = await response.json();

    const students = data.content;
    document.querySelector("button[onclick='nextPage()']").disabled =
    currentPage >= data.totalPages - 1;
    document.querySelector("button[onclick='prevPage()']").disabled =
    currentPage === 0;
    window.onload = loadStudentsPaged;
    document.getElementById("studentForm").reset();

    const tableBody = document.querySelector("#studentTable tbody");
    tableBody.innerHTML = "";
    if (students.length === 0) {
    tableBody.innerHTML = `
        <tr>
            <td colspan="7">No students found</td>
        </tr>
    `;
    return;
}

    students.forEach(student => {
        const row = `
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.email}</td>
                <td>${student.department}</td>
                <td>${student.year}</td>
                <td>${student.cgpa}</td>
                <td>
                <button onclick="editStudent(${student.id})">Edit</button>
                    <button onclick="deleteStudent(${student.id})">Delete</button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });

    document.getElementById("pageInfo").innerText =
        `Page ${currentPage + 1} of ${data.totalPages}`;
}
function nextPage() {
    currentPage++;
    loadStudentsPaged();
}

function prevPage() {
    if (currentPage > 0) {
        currentPage--;
        loadStudentsPaged();
    }

}
async function editStudent(id) {
    const response = await fetch(`${API_URL}/${id}`);
    const student = await response.json();
    
    

    document.getElementById("name").value = student.name;
    document.getElementById("email").value = student.email;
    document.getElementById("department").value = student.department;
    document.getElementById("year").value = student.year;
    document.getElementById("cgpa").value = student.cgpa;

    editId = id;
}
