package com.alexcibotari.zooplus.repository;


import com.alexcibotari.zooplus.domain.Exchange;
import com.alexcibotari.zooplus.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangeRepository extends CrudRepository<Exchange, Long> {

    Optional<Exchange> findOneById(Long id);

    List<Exchange> findAllByOwner(User user);

    Optional<Exchange> findFirstByOwner(User user);

    long countByOwner(User user);

    @Modifying
    Optional<Exchange> deleteOneById(Long id);
}
