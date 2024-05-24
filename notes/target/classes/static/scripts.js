var Font = Quill.import('formats/font');
Font.whitelist = ['Arial', 'Courier-New', 'Times-New-Roman'];
Quill.register(Font, true);

var quill;
var editingNoteId = null;

document.addEventListener("DOMContentLoaded", function() {
    quill = new Quill('#editor', {
        theme: 'snow',
        modules: {
            toolbar: '#toolbar',
        },
        formats: ['font', 'bold', 'italic', 'underline', 'image']
    });

    loadNotes();
});

function selectLocalImage() {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('accept', 'image/*');
    input.addEventListener('change', handleFileSelect);
    input.click();
}

function handleFileSelect(event) {
    const file = event.target.files[0];
    if (/^image\//.test(file.type)) {
        saveToServer(file);
    } else {
        console.warn('You could only upload images.');
    }
}

function saveToServer(file) {
    const formData = new FormData();
    formData.append('image', file);

    fetch('/api/images', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(result => {
        insertToEditor(result.imageUrl);
    })
        .catch(error => {
        console.error('Error', error);
    });
}

function insertToEditor(url) {
    const range = quill.getSelection();
    quill.insertEmbed(range.index, 'image', url);
}

function saveNote() {
    var content = quill.root.innerHTML;
    var url = '/api/notes';
    var method = 'POST';

    if (editingNoteId) {
        url += '/' + editingNoteId;
        method = 'PUT';
    }

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ content: content })
    })
        .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
        .then(note => {
        console.log('Note saved:', note);
        editingNoteId = null;
        quill.root.innerHTML = '';
        loadNotes();
    })
        .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}

function loadNotes() {
    console.log('Loading notes...');
    fetch('/api/notes')
        .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
        .then(notes => {
        console.log('Notes loaded:', notes);
        var notesList = document.getElementById('notes-list');
        notesList.innerHTML = '';
        notes.forEach(note => {
            var li = document.createElement('li');
            li.innerHTML = `${note.createdDate}<br>${note.content}`;

            var editButton = document.createElement('button');
            editButton.innerText = 'Edit';
            editButton.onclick = function() {
                editNote(note.id, note.content);
            };
            li.appendChild(editButton);

            var deleteButton = document.createElement('button');
            deleteButton.innerText = 'Delete';
            deleteButton.onclick = function() {
                deleteNote(note.id);
            };
            li.appendChild(deleteButton);

            notesList.appendChild(li);
        });
    })
        .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}

function deleteNote(noteId) {
    fetch('/api/notes/' + noteId, {
        method: 'DELETE',
    })
        .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        loadNotes();
    })
        .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
    });
}

function editNote(noteId, content) {
    editingNoteId = noteId;
    quill.root.innerHTML = content;
}

function sortNotesByDate() {
    var sortDate = document.getElementById('sort-date').value;
    fetch('api/notes/sort?date=' + sortDate)
        .then(response => {
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        return response.json();
    })
        .then(notes => {
        console.log('Notes sorted by:', notes);
        var sortedNotesList = document.getElementById('sorted-notes-list');
        sortedNotesList.innerHTML = '';
        notes.forEach(note => {
            var li = document.createElement('li');
            li.innerHTML = `${note.createdDate}<br>${note.content}`;

            sortedNotesList.appendChild(li);
        });
    })
        .catch(error => {
        console.error('Ошибка', error);
    });
}

document.getElementById('sort-form').addEventListener('submit', function(event) {
    event.preventDefault();
    sortNotesByDate();
});