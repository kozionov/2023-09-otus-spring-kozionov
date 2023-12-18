function stringFromAuthor(author) {
  return "Id: " + author.id + ", forename: " + author.forename + ", surname: " + author.surname;
}

function stringFromGenre(genre) {
  return "Id: " + genre.id + ", name: " + genre.name;
}

async function fetchBookById(id) {
  const response = await fetch("/api/books/" + id);
  const book = await response.json();
  return book;
} 

async function fetchAllAuthors() {
  const authorsResponse = await fetch("/api/authors");
  const authors = await authorsResponse.json();
  return authors;
}

async function fetchAllGenres() {
  const genresResponse = await fetch("/api/genres");
  const genres = await genresResponse.json();
  return genres;
}