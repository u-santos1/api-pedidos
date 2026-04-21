package APIdePedidos.API.de.Pedidos.dto;

import java.math.BigDecimal;

public record PrecoPopularDTO(BigDecimal preco,
                              Long quantidadeOcorrencias) {
}
