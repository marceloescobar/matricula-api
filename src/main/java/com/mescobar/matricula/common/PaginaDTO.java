package com.mescobar.matricula.common;

import java.util.List;

public record PaginaDTO<T>(List<T> elementos, long totalElementos, int totalPaginas) {
}
