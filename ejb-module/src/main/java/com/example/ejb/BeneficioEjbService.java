package com.example.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Transfere valor de um benefício para outro com validações e controle de concorrência.
     */
    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        // 1) validações de entrada
        if (fromId == null || toId == null) {
            throw new IllegalArgumentException("IDs de origem e destino são obrigatórios");
        }
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Origem e destino não podem ser o mesmo benefício");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser positivo");
        }

        // 2) carrega com lock pra evitar que outra transação mexa ao mesmo tempo
        Beneficio from = em.find(Beneficio.class, fromId, LockModeType.PESSIMISTIC_WRITE);
        Beneficio to   = em.find(Beneficio.class, toId,   LockModeType.PESSIMISTIC_WRITE);

        if (from == null) {
            throw new IllegalArgumentException("Benefício de origem não encontrado: " + fromId);
        }
        if (to == null) {
            throw new IllegalArgumentException("Benefício de destino não encontrado: " + toId);
        }

        // 3) valida saldo
        if (from.getValor() == null) {
            from.setValor(BigDecimal.ZERO);
        }
        if (to.getValor() == null) {
            to.setValor(BigDecimal.ZERO);
        }

        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente no benefício de origem");
        }

        // 4) faz a transferência
        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        // 5) persiste
        em.merge(from);
        em.merge(to);

        // força sincronização agora (opcional, mas deixa mais óbvio)
        em.flush();
    }
}
