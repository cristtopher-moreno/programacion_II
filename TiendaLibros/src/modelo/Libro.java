package modelo;

import excepciones.ValidacionException;

public class Libro {

    private String isbn;
    private String titulo;
    private String autores;
    private int anioPublicacion;
    private String categoria;
    private String editorial;
    private int numeroPaginas;
    private double precio;
    private int cantidadInventario;
    private Formato formato;

    public Libro(String isbn, String titulo, String autores, int anioPublicacion,
                 String categoria, String editorial, int numeroPaginas,
                 double precio, int cantidadInventario, Formato formato) {
        setIsbn(isbn);
        setTitulo(titulo);
        setAutores(autores);
        setAnioPublicacion(anioPublicacion);
        setCategoria(categoria);
        setEditorial(editorial);
        setNumeroPaginas(numeroPaginas);
        setPrecio(precio);
        setCantidadInventario(cantidadInventario);
        setFormato(formato);
    }

    public String getIsbn() { return isbn; }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new ValidacionException("El ISBN es obligatorio");
        }
        this.isbn = isbn.trim();
    }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new ValidacionException("El título es obligatorio");
        }
        this.titulo = titulo.trim();
    }

    public String getAutores() { return autores; }

    public void setAutores(String autores) {
        if (autores == null || autores.isBlank()) {
            throw new ValidacionException("El autor es obligatorio");
        }
        this.autores = autores.trim();
    }

    public int getAnioPublicacion() { return anioPublicacion; }

    public void setAnioPublicacion(int anioPublicacion) {
        if (anioPublicacion < 1000 || anioPublicacion > 2100) {
            throw new ValidacionException("El año de publicación no es válido");
        }
        this.anioPublicacion = anioPublicacion;
    }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            throw new ValidacionException("La categoría es obligatoria");
        }
        this.categoria = categoria.trim();
    }

    public String getEditorial() { return editorial; }

    public void setEditorial(String editorial) {
        if (editorial == null || editorial.isBlank()) {
            throw new ValidacionException("La editorial es obligatoria");
        }
        this.editorial = editorial.trim();
    }

    public int getNumeroPaginas() { return numeroPaginas; }

    public void setNumeroPaginas(int numeroPaginas) {
        if (numeroPaginas <= 0) {
            throw new ValidacionException("El número de páginas debe ser mayor que cero");
        }
        this.numeroPaginas = numeroPaginas;
    }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) {
        if (precio <= 0) {
            throw new ValidacionException("El precio debe ser mayor que cero");
        }
        this.precio = precio;
    }

    public int getCantidadInventario() { return cantidadInventario; }

    public void setCantidadInventario(int cantidadInventario) {
        if (cantidadInventario < 0) {
            throw new ValidacionException("El inventario no puede ser negativo");
        }
        this.cantidadInventario = cantidadInventario;
    }

    public Formato getFormato() { return formato; }

    public void setFormato(Formato formato) {
        if (formato == null) {
            throw new ValidacionException("El formato es obligatorio");
        }
        this.formato = formato;
    }

    @Override
    public String toString() {
        return isbn + " - " + titulo + " (" + autores + ")";
    }
}